package com.doomshell.property_bull.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doomshell.property_bull.R;
import com.doomshell.property_bull.helper.CallBack;
import com.doomshell.property_bull.model.ItemHome;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewholder> {

    Context context;
    ArrayList<ItemHome> itemHomes;
    CallBack callBack;


    public HomeAdapter(Context context, ArrayList<ItemHome> itemHomes) {
        this.context = context;
        this.itemHomes = itemHomes;
        callBack = (CallBack) context;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home, viewGroup, false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewholder myViewholder, final int i) {
        final ItemHome itemHome = itemHomes.get(i);
        myViewholder.tvName.setText(itemHome.getName());

        if (itemHome.getName().equals("Search\n Properties")) {
            myViewholder.button1.setBackgroundColor(context.getResources().getColor(R.color.btn1));
        } else if (itemHome.getName().equals("Post Property \nFor Free")) {

            myViewholder.button1.setBackgroundColor(context.getResources().getColor(R.color.btn2));
        } else if (itemHome.getName().equals("Recent Projects")) {

            myViewholder.button1.setBackgroundColor(context.getResources().getColor(R.color.btn3));
        } else if (itemHome.getName().equals("My\n Requirements")) {
            myViewholder.button1.setBackgroundColor(context.getResources().getColor(R.color.btn4));
        } else if (itemHome.getName().equals("My\n Properties")) {

            myViewholder.button1.setBackgroundColor(context.getResources().getColor(R.color.btn5));
        } else if (itemHome.getName().equals("Register For \nHome Loan")) {

            myViewholder.button1.setBackgroundColor(context.getResources().getColor(R.color.btn6));
        }
        Picasso.get().load(itemHome.getImage()).into(myViewholder.ivHome, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {

            }
        });
        myViewholder.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemHome.getName().equals("Search\n Properties")) {
                    callBack.CallFragment("Search\n Properties");
                } else if (itemHome.getName().equals("Post Property \nFor Free")) {
                    callBack.CallFragment("Post Property \nFor Free");
                } else if (itemHome.getName().equals("Recent Projects")) {
                    callBack.CallFragment("Recent Projects");

                } else if (itemHome.getName().equals("My\n Requirements")) {

                    callBack.CallFragment("My\n Requirements");
                } else if (itemHome.getName().equals("My\n Properties")) {

                    callBack.CallFragment("My\n Properties");
                } else if (itemHome.getName().equals("Register For \nHome Loan")) {
                    callBack.CallFragment("Register For \nHome Loan");

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return itemHomes.size();
    }


    public class MyViewholder extends RecyclerView.ViewHolder {
        private LinearLayout button1;
        private TextView tvName;
        private ImageView ivHome;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            button1 = (LinearLayout) itemView.findViewById(R.id.button1);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            ivHome = (ImageView) itemView.findViewById(R.id.ivHome);
        }
    }
}
