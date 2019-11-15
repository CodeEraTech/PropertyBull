package com.doomshell.property_bull.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.doomshell.property_bull.R;

import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {

    private Context context;
    private int layoutResourceId;
    private ArrayList<String> pro_name = new ArrayList<String>();
    private ArrayList<Integer> Image = new ArrayList<>();

    DisplayMetrics displayMetrics;
    double screenHeight;
    double screenWidth;
    int lhi,lwi;
  //public static SparseBooleanArray sparseArray=new SparseBooleanArray();

    public GridViewAdapter(Context context, ArrayList<String> pro_name,ArrayList<Integer> Image) {
     //   this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.pro_name=pro_name;
        this.Image=Image;
    }


    @Override
    public int getCount() {
        return pro_name.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();

        if (context.getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT) {

            displayMetrics=context.getResources().getDisplayMetrics();
            screenHeight = displayMetrics.heightPixels;
            screenWidth = displayMetrics.widthPixels;

            lhi= (int) (screenHeight*0.30);
            lwi= (int) (screenWidth*0.50);


        } else {

            displayMetrics=context.getResources().getDisplayMetrics();
            screenHeight = displayMetrics.heightPixels;
            screenWidth = displayMetrics.widthPixels;

            lhi= (int) (screenHeight*0.80);
            lwi= (int) (screenWidth*0.50);

        }

        if (convertView == null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.add_residencial, null);


            holder.p_name = (TextView) convertView.findViewById(R.id.add_pro_text);
            holder.imageView=(ImageView)convertView.findViewById(R.id.addp_icon);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }


        holder.p_name.setText(""+pro_name.get(position));
//        holder.imageView.setImageDrawable(context.getResources().getDrawable(Image.get(position)));
        Glide.with(context).load(Image.get(position)).placeholder(R.mipmap.ic_launcher).into(holder.imageView);

       /* if(sparseArray.get(position))
        {
            convertView.setActivated(true);
        }
        else {
            convertView.setActivated(false);
        }*/


        return convertView;
    }

    @Override
    public int getViewTypeCount() {

        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    static class ViewHolder {
        TextView p_name;
        ImageView imageView;
    }
}