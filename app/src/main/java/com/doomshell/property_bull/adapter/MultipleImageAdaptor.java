package com.doomshell.property_bull.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.darsh.multipleimageselect.models.Image;
import com.doomshell.property_bull.R;
import com.doomshell.property_bull.helper.ImageListBean;
import com.jsibbold.zoomage.ZoomageView;

import java.util.ArrayList;

/**
 * Created by Vipin on 03-Feb-18.
 */

public class MultipleImageAdaptor extends RecyclerView.Adapter<MultipleImageAdaptor.MyViewHolder> {
    Context context;
    ArrayList<Image> images=new ArrayList<>();
    ArrayList<String> imagelist=new ArrayList<>();
    int lhi,lwi;
    AppCompatActivity activity;
    boolean iscamera;
    TextView noimagetext;
    RecyclerView reyclerimagelist;


    public MultipleImageAdaptor(AppCompatActivity activity, Context context, TextView noimagetext, RecyclerView reyclerimagelist) {
        this.context=context;
        this.imagelist= ImageListBean.getInstance().getImages();
        this.activity=activity;
        this.noimagetext=noimagetext;
        this.reyclerimagelist=reyclerimagelist;


    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.multipleimageadaptor,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        DisplayMetrics displayMetrics;

        displayMetrics=context.getResources().getDisplayMetrics();
        int screenHeight = displayMetrics.heightPixels;
        int screenWidth = displayMetrics.widthPixels;

         lhi= (int) (screenHeight*0.08);
        lwi=(int)( screenWidth*0.18);


        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.card.getLayoutParams().width = lwi;
        holder.card.getLayoutParams().height = lhi;
        holder.imageView.getLayoutParams().width = lwi;
        holder.imageView.getLayoutParams().height = lhi;

            holder.imageView.setImageURI(Uri.parse(imagelist.get(position)));
            holder.deleteimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position==0) {
                        imagelist.remove(position);

                    } else {
                        imagelist.remove(position - 1);
                    }


                    notifyItemRemoved(position);

                }
            });
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog alertDialog = null;

                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    LayoutInflater inflater = activity.getLayoutInflater();
                    View alerview = inflater.inflate(R.layout.imagezoom, null);
                    ZoomageView imageViewTouch = (ZoomageView) alerview.findViewById(R.id.imageView);
                    imageViewTouch.setImageURI(Uri.parse(imagelist.get(position)));
                    builder.setView(alerview);

                    alertDialog = builder.create();


                /*ImageViewTouchViewPager imageView=(ImageViewTouchViewPager)alerview.findViewById(R.id.slider);
                //DialogSliderAdaptor myCustomPagerAdapter = new DialogSliderAdaptor(context, images,activity);

                imageView.setAdapter(myCustomPagerAdapter);
                imageView.setCurrentItem(position);*/

                    //  imageView.setOffscreenPageLimit(myCustomPagerAdapter.getCount());
                    // imageView.setPageMargin(15);
                    //   imageView.setClipChildren(true);

                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    alertDialog.show();
                }
            });


    }

    @Override
    public int getItemCount() {
        if(imagelist.size()==0)
        {
            noimagetext.setVisibility(View.VISIBLE);
            reyclerimagelist.setVisibility(View.GONE);
            return 0;
        }
        else {
            return imagelist.size();
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        ImageView deleteimage;
        CardView card;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.card=(CardView)itemView.findViewById(R.id.card);
            this.imageView=(ImageView)itemView.findViewById(R.id.imageView2);
            this.deleteimage=(ImageView)itemView.findViewById(R.id.delete);
        }
    }

}
