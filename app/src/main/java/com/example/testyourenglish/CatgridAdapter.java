package com.example.testyourenglish;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

public class CatgridAdapter extends BaseAdapter {

    private List<String> catList;

    public CatgridAdapter(List<String> catList) {
        this.catList = catList;
    }

    @Override
    public int getCount() {
        return catList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

       View view;

       if(convertView == null){
           view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item_layout,parent,false);
       }
       else{
           view = convertView;
       }
        ((TextView) view.findViewById(R.id.catName)).setText(catList.get(position));

        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(255), rnd.nextInt(255),rnd.nextInt(255));
        view.setBackgroundColor(color);


        return view;
    }
}
