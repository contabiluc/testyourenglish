package com.example.testyourenglish;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toolbar;

public class SetsAdapter extends BaseAdapter {


    private  int numOfSets; //am creat o variabila dupa ce am implementat automat metodele

    public SetsAdapter (int numOfSets){ //constructor
        this.numOfSets = numOfSets;
    } //constructor


    @Override
    public int getCount() {
        return numOfSets;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;

        if(convertView == null){
                                                                            //ia vezi aici jos daca nu e set_item_layout
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_item_layout,parent,false);
        }
        else{

            view = convertView;
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(),QuestionsActivity.class);
                intent.putExtra("SETNO", position + 1);
                parent.getContext().startActivity(intent);
            }
        });

        /* view.findViewById(R.id.SetNr_tv);

        aici mai trebuie sa scriem un stetext posittion +1 din textview
        linia de cod originala dar care imi da crash:*/

        ((TextView) view.findViewById(R.id.SetNr_tv)).setText(String.valueOf(position+1));


        return view;
    }
}
