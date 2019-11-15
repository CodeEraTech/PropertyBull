package com.doomshell.property_bull.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.doomshell.property_bull.R;

import java.util.ArrayList;

/**
 * Created by Anuj on 1/11/2017.
 */

public class Project_Gallery_Adapter extends RecyclerView.Adapter<Project_Gallery_Adapter.MyViewHolder> {

    ArrayList<String> id=new ArrayList<>();
    ArrayList<String> name=new ArrayList<>();
    ArrayList<String> city=new ArrayList<>();
    ArrayList<String> image=new ArrayList<>();
    Context context;
    DisplayMetrics displayMetrics;
    double screenHeight;
    double screenWidth;
    int lhi;
    int lwi;


    public Project_Gallery_Adapter(Context context, ArrayList<String> id, ArrayList<String> name, ArrayList<String> city,ArrayList<String> image)
    {
        this.context=context;
        this.id=id;
        this.name=name;
        this.city=city;
        this.image=image;
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.poperty_list_gallery,parent,false);


        MyViewHolder myViewHolder=new MyViewHolder(view);

        if (context.getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT) {

            displayMetrics=context.getResources().getDisplayMetrics();
            screenHeight = displayMetrics.heightPixels;
            screenWidth = displayMetrics.widthPixels;

            lhi= (int) (screenHeight*0.20);


        } else {

            displayMetrics=context.getResources().getDisplayMetrics();
            screenHeight = displayMetrics.heightPixels;
            screenWidth = displayMetrics.widthPixels;

            lhi= (int) (screenHeight*0.60);

        }

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        TextView id_text=holder.id_textview;
        ImageView imageView=holder.imageView;
        RelativeLayout mainLayout=holder.mainlayout;
        //CardView cardView=holder.cardView;

        id_text.setSelected(true);
       /* displayMetrics=context.getResources().getDisplayMetrics();
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

         lhi= (int) (screenHeight*0.20);
         lwi= (int) screenWidth;
*/
        //RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(lwi,lhi);
        imageView.getLayoutParams().height=lhi;
        imageView.requestLayout();


        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent=new Intent(context, LoveAstrology_Details.class);
                intent.putExtra("love_title",love_title.get(position).toString());
                intent.putExtra("love_image",love_imageurl.get(position).toString());
                intent.putExtra("love_content",love_Content.get(position).toString());
                context.startActivity(intent);*/
                Toast.makeText(context,"main clicked"+position,Toast.LENGTH_LONG).show();
            }
        });

        Glide.with(context).load(image.get(position)).placeholder(R.drawable.no_image).into(imageView);

        id_text.setText(id.get(position).toString()+"\n"+name.get(position).toString()+"\n"+city.get(position).toString());

    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id_textview;
        ImageView imageView;
        RelativeLayout relativeLayout,mainlayout;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.id_textview = (TextView) itemView.findViewById(R.id.property_gallery_title);
            this.imageView = (ImageView) itemView.findViewById(R.id.property_gallery_image);
           // this.relativeLayout=(RelativeLayout)itemView.findViewById(R.id.);
            this.mainlayout=(RelativeLayout)itemView.findViewById(R.id.property_gallery_mainlayout);
            //this.cardView=(CardView)itemView.findViewById(R.id.article_cardview);
        }
    }
}
