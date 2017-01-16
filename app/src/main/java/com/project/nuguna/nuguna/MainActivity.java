package com.project.nuguna.nuguna;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.NetworkOnMainThreadException;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ListView listview; // 업체 리스트 담음 1/2 작업
    private ItemAdapter mAdapter;
    ArrayList<AndroidVO> shoplist;
    Intent intent;

    //Json 데이터 전송 관련 변수들
    String jsontext;
    ProgressDialog mProgress;
    String requestUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        download();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
           super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void download() {
        mProgress = ProgressDialog.show(this, "로딩중...", "목록 가져오는중...");
        //TODO:추후 서버에 맞는 IP로 바꿔야함, 현재는 테스트중
        /*DownThread thread = new DownThread
                ("");*/
        requestUri = "http://10.10.17.8:8999/nugunaProj6/android_list";
        DownThread thread = new DownThread
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
            jarray = json.getJSONArray("al");
            shoplist = new ArrayList<>();
            StringBuilder sb2 = new StringBuilder();


            for (int i = 0; i < jarray.length(); i++) {
                item = jarray.getJSONObject(i);
                //JsonArray 정보를 파싱해서 FoodVO 객체를 생성
                AndroidVO list = new AndroidVO(item.getString("owner"), item.getString("title"), item.getString("shopname"), item.getString("shopaddr"), item.getString("shoptel"), item.getString("shopnumber"), item.getString("templatename"),item.getInt("version"), item.getString("domain"), item.getInt("hosting_id"));

                shoplist.add(list);
            }

            // 리스트 뿌려줄 작업 1/2  ----------------------
            listview = (ListView) findViewById(R.id.listview);
            mAdapter = new ItemAdapter(MainActivity.this, R.layout.single_item, shoplist);
            listview.setAdapter(mAdapter);
            //------------------------------------------------
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getApplicationContext(),ShopActivity.class);

                    TextView shopname = (TextView)view.findViewById(R.id.shopname);
                    TextView shoptel = (TextView)view.findViewById(R.id.shoptel);
                    TextView shopaddr = (TextView)view.findViewById(R.id.shopaddr);
                    TextView hosting_id = (TextView)view.findViewById(R.id.hosting_id);

                    intent.putExtra("shopname",shopname.getText());
                    intent.putExtra("shoptel",shoptel.getText());
                    intent.putExtra("shopaddr",shopaddr.getText());
                    intent.putExtra("hosting_id",hosting_id.getText());

                    startActivity(intent);
                }
            });

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
            Toast.makeText(MainActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
        }
    };
}

