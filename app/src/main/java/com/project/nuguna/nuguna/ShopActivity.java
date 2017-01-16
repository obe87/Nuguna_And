package com.project.nuguna.nuguna;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.NetworkOnMainThreadException;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by kwon on 2017-01-03.
 */

public class ShopActivity extends AppCompatActivity implements OnMapReadyCallback {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    static final LatLng SEOUL = new LatLng(37.56, 126.97);
    private GoogleMap googleMap;
    ListView boardList;
    ListView boardview;
    public ArrayList<AndroidVO> shoplist;
    static ArrayList<Boarditem> board;
    //Json 데이터 전송 관련 변수들
    String jsontext;
    ProgressDialog mProgress;
    String requestUri;
    private BoardAdapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_main);


     /*   MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
       mapFragment.getMapAsync(this);*/

        download();

        Intent intent = getIntent();
        String hosing_id = intent.getStringExtra("hosing_id");
        String shopname = intent.getStringExtra("shopname");

        Button telbutton = (Button) findViewById(R.id.shoptel);
        telbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                String shoptel = intent.getStringExtra("shoptel");
                shoptel = "tel:" + shoptel;
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse(shoptel)));

            }
        });

        // Initializing the TabLayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("정보"));
        tabLayout.addTab(tabLayout.newTab().setText("지도"));
        tabLayout.addTab(tabLayout.newTab().setText("리뷰"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Initializing ViewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        // Creating TabPagerAdapter adapter
        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // Set TabSelectedListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onMapReady(final GoogleMap map) {
        googleMap = map;

        Marker seoul = googleMap.addMarker(new MarkerOptions().position(SEOUL)
                .title("Seoul"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));
    }


   public void download() {
        mProgress = ProgressDialog.show(this, "로딩중...", "목록 가져오는중...");
        //TODO:추후 서버에 맞는 IP로 바꿔야함, 현재는 테스트중
        /*DownThread thread = new DownThread
                ("");*/
        requestUri = "http://10.10.17.8:8999/nugunaProj6/android_board";
        ShopActivity.DownThread thread = new ShopActivity.DownThread
                (requestUri);

        thread.start();
    }


    //json 정보를 다운받는 downThread
    class DownThread extends Thread {
        String mAddr;

        DownThread(String addr) {
            mAddr = addr;
        }

        @Override
        public void run() {
            String result = DownloadHtml(mAddr);
            Message message = mAfterDown.obtainMessage();
            message.obj = result;
            mAfterDown.sendMessage(message);
        }

        public String DownloadHtml(String addr) {
            StringBuilder sb = new StringBuilder();
            try {
                URL url = new URL(addr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                if (conn != null) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStreamReader in
                                = new InputStreamReader(conn.getInputStream());
                        int ch;
                        while ((ch = in.read()) != -1) {
                            sb.append((char) ch);
                        }
                        in.close();
                        jsontext = sb.toString();
                    }
                    conn.disconnect();
                }
            } catch (NetworkOnMainThreadException e) {
                return "Error : 메인 스레드 네트워크 작업 에러 - " + e.getMessage();
            } catch (Exception e) {
                return "Error : " + e.getMessage();
            }
            //TODO:
            parsing();

            return sb.toString();
        }


    }//DownThread

    public void parsing() {

        if (jsontext == null) return;

        JSONObject json = null;
        JSONArray jarray = null;
        JSONObject item = null;

        try {
            json = new JSONObject(jsontext);
            jarray = json.getJSONArray("boardlist");
            board = new ArrayList<>();
            StringBuilder sb2 = new StringBuilder();


            for (int i = 0; i < jarray.length(); i++) {
                item = jarray.getJSONObject(i);
                //JsonArray 정보를 파싱해서 FoodVO 객체를 생성
                Boarditem list = new Boarditem(item.getInt("wbno"), item.getString("email"), item.getString("writer"), item.getString("content"), item.getString("inputdate"), item.getString("wbtype"), item.getString("wbpass"),item.getString("templatename"), item.getInt("version"));
                board.add(list);
            }
            Log.v("보드", board.toString());
/*           BoardAdapter adapter = new BoardAdapter(this, R.layout.board_item, board) ;
            ListFragment menuListFrgmt = (ListFragment) getSupportFragmentManager().findFragmentById(android.R.id.list);
            menuListFrgmt.setListAdapter(adapter) ;*/


            // 리스트 뿌려줄 작업 1/2  ----------------------
           boardview = (ListView) findViewById(android.R.id.list);
            mAdapter = new BoardAdapter(ShopActivity.this, R.layout.tab_fragment3, board);
            boardview.setAdapter(mAdapter);
            //------------------------------------------------


            //Toast.makeText(this, sb2.toString(), Toast.LENGTH_SHORT).show();


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    Handler mAfterDown = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mProgress.dismiss();
            //textview.setText(msg.obj.toString());
            Toast.makeText(ShopActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
        }
    };



}
