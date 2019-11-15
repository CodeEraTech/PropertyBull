package com.doomshell.property_bull.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doomshell.property_bull.R;

import java.util.ArrayList;

/**
 * Created by Anuj on 1/11/2017.
 */

public class Filter_Checkbox_Adapter extends RecyclerView.Adapter<Filter_Checkbox_Adapter.MyViewHolder> {

    ArrayList<String> checkname=new ArrayList<>();

    Context context;
    DisplayMetrics displayMetrics;
    double screenHeight;
    double screenWidth;
    int lhi;
    int lwi;
    private static int lastCheckedPos = 0;


    public Filter_Checkbox_Adapter(Context context, ArrayList<String> checkname)
    {
        this.context=context;
        this.checkname=checkname;

    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.checkboxes_filter,parent,false);


        MyViewHolder myViewHolder=new MyViewHolder(view);


        if (context.getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT) {

            displayMetrics=context.getResources().getDisplayMetrics();
            screenHeight = displayMetrics.heightPixels;
            screenWidth = displayMetrics.widthPixels;

            lwi= (int) (screenWidth*0.20);


        } else {

            displayMetrics=context.getResources().getDisplayMetrics();
            screenHeight = displayMetrics.heightPixels;
            screenWidth = displayMetrics.widthPixels;

            lwi= (int) (screenWidth*0.40);
        }


        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final TextView textfilter;

        textfilter =holder.textfilter;

        holder.horicard.getLayoutParams().width=lwi;
        holder.horicard.requestLayout();



        textfilter.setText(""+checkname.get(position));


        holder.textfilter.setTag(new Integer(position));

        //for default check in first item
        if(position == 0 && textfilter.isSelected() )
        {
            lastCheckedPos = holder.textfilter.getId();
            lastCheckedPos = 0;
        }

    }

    @Override
    public int getItemCount() {
        return checkname.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        TextView textfilter;
        CardView horicard;


        public MyViewHolder(View itemView) {
            super(itemView);
             this.textfilter = (TextView) itemView.findViewById(R.id.textfilter);
            this.horicard=(CardView)itemView.findViewById(R.id.horicard);
        }
    }
}
