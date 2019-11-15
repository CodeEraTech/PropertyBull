package com.doomshell.property_bull;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class ProviderDetails extends Fragment {
View convertview;
    Context context;



    ImageView imageView;
    Intent callIntent;
    public static int CallState=123;
    ProgressBar recent_img_progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        convertview= inflater.inflate(R.layout.provider_details, container, false);
        context=getActivity().getApplicationContext();
        savedInstanceState=getArguments();


        recent_img_progress=(ProgressBar)convertview.findViewById(R.id.recent_img_progress);
        imageView=(ImageView)convertview.findViewById(R.id.deatails_loveastro_image);
        TextView title=(TextView)convertview.findViewById(R.id.deatail_Loveastro_title);
       TextView content=(TextView)convertview.findViewById(R.id.detail_loveastro_content);
        TextView name=(TextView)convertview.findViewById(R.id.name);
        TextView url=(TextView)convertview.findViewById(R.id.url);

        TextView email=(TextView)convertview.findViewById(R.id.email);
        TextView address=(TextView)convertview.findViewById(R.id.address);
        TextView mobile=(TextView)convertview.findViewById(R.id.mobile);

        LinearLayout overcard_layout=(LinearLayout)convertview.findViewById(R.id.overcard_layout);

         title.setSelected(true);

        DisplayMetrics displayMetrics=new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int screenHeight = displayMetrics.heightPixels;
        int screenWidth = displayMetrics.widthPixels;

        int lhi= (int) (screenHeight*0.25);
        int lwi= (int) screenWidth;


        overcard_layout.getLayoutParams().height=lhi;
        overcard_layout.requestLayout();


       String imageurl= savedInstanceState.getString("love_image");
        String stitle= savedInstanceState.getString("love_title");
        String scontent= savedInstanceState.getString("love_content");


            Glide.with(context).load(imageurl).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
        if (!imageurl.equalsIgnoreCase("")){
        Glide.with(context).load(imageurl).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                recent_img_progress.setVisibility(View.GONE);
                imageView.setBackground(context.getResources().getDrawable(R.drawable.no_image));

                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
               recent_img_progress.setVisibility(View.GONE);
                return false;
            }
        }).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
    }
        else {
       recent_img_progress.setVisibility(View.GONE);
    }
          title.setText(stitle);
        content.setText(scontent);
        name.setText(savedInstanceState.getString("name"));
        final String mobiledata=savedInstanceState.getString("mobile");
        mobile.setText(savedInstanceState.getString("mobile"));
        address.setText(savedInstanceState.getString("address"));
        email.setText(savedInstanceState.getString("email"));
        final String urldata=savedInstanceState.getString("companyurl");
        url.setText(savedInstanceState.getString("companyurl"));
        url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urldata));
                startActivity(browserIntent);
            }
        });

        mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mobiledata == null || mobiledata.equals("")) {
                    Toast.makeText(getActivity(), "Number not provided", Toast.LENGTH_SHORT).show();
                } else {
                    callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + mobiledata));

                    int myAPI = Build.VERSION.SDK_INT;

                    if (myAPI >= 23) {
                        int result = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CALL_PHONE);
                        if (result == PackageManager.PERMISSION_GRANTED) {
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(callIntent);
                        }else {
                            //      Toast.makeText(context, "permission required to make call, Please give permission", Toast.LENGTH_LONG).show();

                            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.CALL_PHONE))
                            {
                                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.CALL_PHONE},
                                        CallState);
                            }else {
                                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.CALL_PHONE},
                                        CallState);
                            }
                        }

                    }else {
                        callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(callIntent);
                    }
                }
            }
        });



        return convertview;
    }






}
