package com.doomshell.property_bull.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Matrix;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.doomshell.property_bull.R;
import com.doomshell.property_bull.helper.FooterLoaderAdapter;
import com.doomshell.property_bull.model.User;

import java.util.ArrayList;

/**
 * Created by elanicdroid on 28/10/15.
 */
public class Projectgallery_MoreAdapter extends FooterLoaderAdapter<User> {
Context context;
    ArrayList<String> id_list=new ArrayList<>();
    ArrayList<String> name_list=new ArrayList<>();
    ArrayList<String> city_list=new ArrayList<>();
    ArrayList<String> image_list=new ArrayList<>();
    private Matrix matrix = new Matrix();
    private float scale = 1f;
    private ScaleGestureDetector SGD;
    DisplayMetrics displayMetrics;
    double screenHeight;
    double screenWidth;
    //ImageView imageView;
    int lhi;
    Activity activity;

    public Projectgallery_MoreAdapter(Activity activity, Context context, ArrayList<String> id_list, ArrayList<String> name_list, ArrayList<String> city_list, ArrayList<String> image_list) {
        super(context);
        this.context=context;
        this.id_list=id_list;
        this.name_list=name_list;
        this.city_list=city_list;
        this.image_list=image_list;
        this.activity=activity;

        if (context.getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT) {

            displayMetrics=context.getResources().getDisplayMetrics();
            screenHeight = displayMetrics.heightPixels;
            screenWidth = displayMetrics.widthPixels;

            lhi= (int) (screenHeight*0.50);


        } else {

            displayMetrics=context.getResources().getDisplayMetrics();
            screenHeight = displayMetrics.heightPixels;
            screenWidth = displayMetrics.widthPixels;

            lhi= (int) (screenHeight*0.60);

        }
    }

    @Override
    public long getYourItemId(int position) {
        return mItems.get(position).getId();
    }

    @Override
    public RecyclerView.ViewHolder getYourItemViewHolder(ViewGroup parent) {
        return new DemoViewHolder(mInflater.inflate(R.layout.poperty_list_gallery, parent, false));
    }

    @Override
    public void bindYourViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof DemoViewHolder) {

            final DemoViewHolder viewHolder = (DemoViewHolder)holder;

            viewHolder.mUsernameView.setSelected(true);
            viewHolder.imageView.getLayoutParams().height=lhi;
            viewHolder.imageView.requestLayout();

            viewHolder.mUsernameView.setText(id_list.get(position).toString()+"\n"+name_list.get(position).toString()+"\n"+city_list.get(position).toString());
           /* Glide.with(context).load(image_list.get(position)).asBitmap().placeholder(R.mipmap.ic_launcher_round).into(new SimpleTarget<Bitmap>(50,50) {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    ((DemoViewHolder) holder).imageView.setImageBitmap(resource);
                }
            });*/
         Glide.with(context).load(image_list.get(position)).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(viewHolder.imageView);
            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog dialog=new Dialog(activity);
                    dialog.getWindow().setBackgroundDrawableResource(R.color.dialogbackground);

                    dialog.setContentView(R.layout.imagedialog);
                   WebView imageView=(WebView) dialog.findViewById(R.id.imageView);
                    imageView.loadUrl(image_list.get(position));
                    imageView.getSettings().setBuiltInZoomControls(true);
                    imageView.getSettings().setDisplayZoomControls(false);
                    //imageView.getSettings().setUseWideViewPort(false);
                  //  Glide.with(context).load(image_list.get(position)).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
                    dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                   // SGD = new ScaleGestureDetector(context,new ScaleListener());

                    dialog.show();
                }
            });

        }
    }

    public class DemoViewHolder extends RecyclerView.ViewHolder {

      //  @Bind(R.id.username_view)
        TextView mUsernameView;
        ImageView imageView;

        public DemoViewHolder(View itemView) {
            super(itemView);
            //ButterKnife.bind(this, itemView);
            this.mUsernameView=(TextView)itemView.findViewById(R.id.property_gallery_title);
            this.imageView = (ImageView) itemView.findViewById(R.id.property_gallery_image);
        }
    }
    public boolean onTouchEvent(MotionEvent ev) {
        SGD.onTouchEvent(ev);
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.
            SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scale *= detector.getScaleFactor();
            scale = Math.max(0.1f, Math.min(scale, 5.0f));
            matrix.setScale(scale, scale);
            //imageView.setImageMatrix(matrix);
            return true;
        }
    }
}
