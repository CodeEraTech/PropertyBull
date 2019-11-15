package com.doomshell.property_bull.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Anuj on 6/23/2017.
 */

public class MySpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    private final Context activity;
    private ArrayList<String> asr;
    Typeface font = Typeface.create("sans-serif", Typeface.NORMAL);

    public MySpinnerAdapter(Context context,ArrayList<String> asr) {
        this.asr=asr;
        activity = context;
    }



    public int getCount()
    {
        return asr.size();
    }

    public Object getItem(int i)
    {
        return asr.get(i);
    }

    public long getItemId(int i)
    {
        return (long)i;
    }



    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView txt = new TextView(activity);
        txt.setPadding(16, 16, 16, 16);
        txt.setBackgroundColor(Color.WHITE);
        txt.setGravity(Gravity.CENTER_VERTICAL);
        txt.setTypeface(font);
        txt.setTextSize(14);


        if(position==0)
        {
            txt.setText(asr.get(position));
            txt.setTextColor(Color.parseColor("#FF989898"));
        }
        else
        {
            txt.setText(asr.get(position));
            txt.setTextColor(Color.parseColor("#000000"));
        }
        return  txt;
    }

    public View getView(int i, View view, ViewGroup viewgroup) {
        TextView txt = new TextView(activity);
        txt.setBackgroundColor(Color.WHITE);
       // txt.setPadding(5, 5, 10, 10);
        txt.setPadding(16, 16, 16, 16);
        txt.setGravity(Gravity.CENTER_VERTICAL);
        txt.setTypeface(font);
        txt.setTextSize(14);
        txt.setText(asr.get(i));
        if(i==0)
        {
            txt.setText(asr.get(i));
            txt.setTextColor(Color.parseColor("#FF989898"));
        }else {

            txt.setTextColor(Color.parseColor("#5a5a5a"));
        }
            return  txt;
    }
}