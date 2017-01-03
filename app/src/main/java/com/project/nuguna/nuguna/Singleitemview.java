package com.project.nuguna.nuguna;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by kwon on 2016-12-28.
 */
//View객체는 기본생성자가 없음
public class Singleitemview extends LinearLayout {
    TextView name;
    TextView address;
    TextView talk;
    public Singleitemview(Context context) {
        super(context);
        init(context);
    }

    public Singleitemview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    // inflate 시켜서 초기화 시키는 메소드
    public void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                inflater.inflate(R.layout.single_item, this, true);

        name = (TextView) findViewById(R.id.name);
        address = (TextView) findViewById(R.id.address);
        talk = (TextView) findViewById(R.id.talk);

    }

    public void setName(String name){
        this.name.setText(name);
    }
    public void setAddress(String address){
        this.address.setText(address);
    }
    public void setTalk(String talk){
        this.talk.setText(talk);
    }
}
