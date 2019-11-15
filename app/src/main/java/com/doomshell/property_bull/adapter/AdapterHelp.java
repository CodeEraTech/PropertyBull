package com.doomshell.property_bull.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doomshell.property_bull.R;
import com.doomshell.property_bull.model.ItemHelp;

import java.util.ArrayList;

public class AdapterHelp extends RecyclerView.Adapter<AdapterHelp.myViewHolder> {
    Context context;
    ArrayList<ItemHelp> itemHelpArrayList;

    public AdapterHelp(Context context, ArrayList<ItemHelp> itemHelpArrayList) {
        this.context = context;
        this.itemHelpArrayList = itemHelpArrayList;

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_help, viewGroup, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i) {
        ItemHelp itemHelp = itemHelpArrayList.get(i);
        myViewHolder.title.setText(itemHelp.getTitle());
        myViewHolder.description.setText(itemHelp.getDescription());


    }

    @Override
    public int getItemCount() {
        return itemHelpArrayList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            description = itemView.findViewById(R.id.tvDescription);


        }
    }
}
