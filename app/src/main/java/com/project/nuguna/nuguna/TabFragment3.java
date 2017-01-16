package com.project.nuguna.nuguna;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/*
public class TabFragment3 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_fragment3, null);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.board_item, ShopActivity.board);
        ListView listview = (ListView) view.findViewById(R.id.boardview);
        listview.setAdapter(adapter);

        return listview;
    }
}
*/


public class TabFragment3 extends ListFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.tab_fragment3, container, false);

    }
}

