package com.project.nuguna.nuguna;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kwon on 2017-01-11.
 */

public class BoardAdapter extends ArrayAdapter<Boarditem> {
    private Context mContext;
    private int mResource;
    private ArrayList<Boarditem> mList;
    private LayoutInflater mInflater;

    public BoardAdapter(Context context, int resource, ArrayList<Boarditem> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.mList = objects;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Boarditem boarditem = mList.get(position);

        if(convertView == null)
        {
            convertView = mInflater.inflate(mResource, null);
        }

        if(boarditem != null)
        {
/*                ImageView ivThumbnail = (ImageView) convertView.findViewById(R.id.list_view_row_profile_thumbnail);*/
            TextView tvScreenName = (TextView) convertView.findViewById(R.id.writer);
            TextView tvCreatedAt = (TextView) convertView.findViewById(R.id.content);
         /*   TextView tvText = (TextView) convertView.findViewById(R.id.title);*/

            // 이미지 세팅은 예제를 넘어선 범위이므로 생략한다.

            // 스크린네임 세팅
            tvScreenName.setText(boarditem.getWriter());

            // 작성 시간 세팅, UTC 기준시간으로  되어있지만, 우리나라 시간으로 바꾸는 것은 예제에 포함되지 않는 내용이므로 생략한다.
            tvCreatedAt.setText(boarditem.getContent());
            // 텍스트 세팅
     /*       tvText.setText(boarditem.);*/
        }

        return convertView;
    }
}
