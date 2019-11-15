package com.doomshell.property_bull.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doomshell.property_bull.R;

import java.util.ArrayList;

/**
 * Created by Anuj on 1/11/2017.
 */

public class Recent_ProjectFeature_adapter extends RecyclerView.Adapter<Recent_ProjectFeature_adapter.MyViewHolder> {


     Context context;

    ArrayList<String> checklist;





    public Recent_ProjectFeature_adapter(Context context,ArrayList<String> checklist
                                         ) {
        this.context = context;
        this.checklist=checklist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recentprojectfeaturelistadaptor, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        TextView name_text = holder.name;
        name_text.setText(checklist.get(position));
    }

    @Override
    public int getItemCount() {
        return checklist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.name= (TextView) itemView.findViewById(R.id.name);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
