package com.doomshell.property_bull.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.doomshell.property_bull.R;

import java.util.ArrayList;

/**
 * Created by Anuj on 1/11/2017.
 */

public class Provider_Adapter extends RecyclerView.Adapter<Provider_Adapter.MyViewHolder> {

    ArrayList<String> logourl=new ArrayList<>();
    ArrayList<String> companyname=new ArrayList<>();
    ArrayList<String> contactdetails=new ArrayList<>();
    ArrayList<String> company_url=new ArrayList<String>();
    ArrayList<String> email=new ArrayList<String>();
    ArrayList<String> mobile=new ArrayList<String>();
    ArrayList<String> address=new ArrayList<String>();
    ArrayList<String> about=new ArrayList<String>();
    ArrayList<String> start,created,lat,lng;
    static Context context;
    Activity activity;
    DisplayMetrics displayMetrics;
    double screenHeight;
    double screenWidth;
    int lhi;
    int lwi;

   public Provider_Adapter(Context context, ArrayList<String> logourl, ArrayList<String> companyname, ArrayList<String> contactdetails, ArrayList<String> company_url, ArrayList<String> email, ArrayList<String> mobile, ArrayList<String> address, ArrayList<String> about,ArrayList<String> start,ArrayList<String> created,ArrayList<String> lat,ArrayList<String> lng, Activity activity) {
       this.context = context;
       this.logourl = logourl;
       this.companyname = companyname;
       this.contactdetails = contactdetails;
       this.company_url = company_url;
       this.email = email;
       this.mobile = mobile;
       this.address = address;
       this.about = about;
       this.start=start;
       this.lat=lat;
       this.lng=lng;
       this.created=created;
       this.activity = activity;
   }
    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.provideradaptor,parent,false);


        Provider_Adapter.MyViewHolder myViewHolder = new Provider_Adapter.MyViewHolder(view);

        if (context.getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT) {

            displayMetrics = context.getResources().getDisplayMetrics();
            screenHeight = displayMetrics.heightPixels;
            screenWidth = displayMetrics.widthPixels;

            lhi = (int) (screenHeight * 0.30);


        } else {

            displayMetrics = context.getResources().getDisplayMetrics();
            screenHeight = displayMetrics.heightPixels;
            screenWidth = displayMetrics.widthPixels;

            lhi = (int) (screenHeight * 0.65);

        }

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        CardView cardView = holder.cardview;



if(!start.get(position).equalsIgnoreCase("-"))
    holder.companyname.setText(companyname.get(position)+" ("+start.get(position)+")");
        else {
    holder.companyname.setText(companyname.get(position));
    }
        holder.mobile.setText(mobile.get(position));
        holder.contact.setText(contactdetails.get(position));
        holder.emailid.setText(email.get(position));
        holder.adress.setText(address.get(position));
        holder.memeber_since.setText(created.get(position));
      /*  if(holder.description.getVisibility()==View.VISIBLE)
        {
            holder.description.setVisibility(View.GONE);
        }*/
        holder.description.setText(context.getResources().getString(R.string.description)+" : "+about.get(position).toString());
       holder.rightdata.getLayoutParams().height = lhi;
        holder.rightdata.requestLayout();
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /* Dialog dialog=new Dialog(activity);
                dialog.setTitle("About "+companyname.get(position).toString());
                ScrollView scrollView=new ScrollView(context);
                TextView text=new TextView(context);
                text.setTextColor(Color.parseColor("#000000"));
                text.setPadding(5,5,5,5);
                text.setText(about.get(position).toString());
                scrollView.addView(text);
                dialog.setContentView(scrollView);
                dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                dialog.show();
                holder.description.setVisibility(View.VISIBLE);*/
           //  holder.description.setVisibility(View.VISIBLE);
                if(holder.description.isShown())
                {
                    holder.description.setVisibility(View.GONE);
                }
                else
                {
                    holder.description.setVisibility(View.VISIBLE);
                }

            }
        });
        if(lat.get(position).equalsIgnoreCase("-"))
        {
            holder.location.setVisibility(View.INVISIBLE);
        }
        holder.location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            /*    String geoUri = "http://maps.google.com/maps?q=loc:" + lat + "," + lng + " (" + "Propertybull" + ")";
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", lat.get(position), lng.get(position));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);*/
                Uri gmmIntentUri = Uri.parse("google.streetview:cbll="+lat+","+lng);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);
            }
        });
        //Glide.with(activity.getApplicationContext()).load(logourl.get(position)).into(holder.Provider_logo);
     /*   holder.cardview.getLayoutParams().height = lhi;
        holder.cardview.requestLayout();*/

        if (!logourl.get(position).equalsIgnoreCase("")){

            Glide.with(context).load(logourl.get(position)).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    holder.customdialog.setVisibility(View.GONE);
                    holder.Provider_logo.setBackground(context.getResources().getDrawable(R.drawable.no_image));

                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    holder.customdialog.setVisibility(View.GONE);
                    return false;
                }
            }).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.Provider_logo);
        }
        else {
            holder.recent_img_progress.setVisibility(View.GONE);
        }
        holder.website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(company_url.get(position)));
                browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(browserIntent);
            }
        });

        holder.mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent;
                if (mobile.get(position) == null || mobile.get(position).equals("")) {
                    Toast.makeText(context, "Number not provided", Toast.LENGTH_SHORT).show();
                } else {
                    callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + mobile.get(position)));

                    int myAPI = Build.VERSION.SDK_INT;

                    if (myAPI >= 23) {
                        int result = ContextCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE);
                        if (result == PackageManager.PERMISSION_GRANTED) {
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(callIntent);
                        }else {
                            //      Toast.makeText(context, "permission required to make call, Please give permission", Toast.LENGTH_LONG).show();

                            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.CALL_PHONE))
                            {
                                ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.CALL_PHONE},
                                        1);
                            }else {
                                ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.CALL_PHONE},
                                        1);
                            }
                        }

                    }else {
                        callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(callIntent);
                    }
                }
            }
        });
       /* holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProviderDetails service_provider_details=new ProviderDetails();
                Bundle bundle=new Bundle();

                bundle.putString("love_title",companyname.get(position).toString());

                bundle.putString("love_image",logourl.get(position).toString());
                bundle.putString("love_content",about.get(position).toString());
                bundle.putString("address",address.get(position).toString());
                bundle.putString("email",email.get(position).toString());
                bundle.putString("mobile",mobile.get(position).toString());
                bundle.putString("companyurl",company_url.get(position).toString());
                bundle.putString("name",contactdetails.get(position).toString());
                service_provider_details.setArguments(bundle);

                FragmentTransaction fragmentTransaction=((AppCompatActivity) activity).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, service_provider_details);

                fragmentTransaction.addToBackStack(service_provider_details.getClass().toString());
                fragmentTransaction.commit();
            }
        });*/

    /* Glide.with(context).load(logourl.get(position).toString()).into(holder.logo);
        Glide.with(context).load(logourl.get(position).toString()).asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.logo) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                holder.logo.setImageDrawable(circularBitmapDrawable);
            }
        });*/


    }

    @Override
    public int getItemCount() {
        return companyname.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

     //  ImageView logo;
        TextView contact,companyname,mobile,emailid,memeber_since,adress;
        LinearLayout header;
        ImageView Provider_logo,website,location;
        ProgressBar recent_img_progress;
        CardView cardview;
        TextView more,description;
        LinearLayout rightdata;
        RelativeLayout customdialog;

        public MyViewHolder(View itemView) {
            super(itemView);
            //this.logo = (ImageView) itemView.findViewById(R.id.logo);
            this.adress= (TextView) itemView.findViewById(R.id.adress);
            this.more= (TextView) itemView.findViewById(R.id.more);
            this.memeber_since= (TextView) itemView.findViewById(R.id.memeber_since);
            this.contact = (TextView) itemView.findViewById(R.id.contactperson);
            this.emailid = (TextView) itemView.findViewById(R.id.emailid);
            this.companyname= (TextView) itemView.findViewById(R.id.companyname);
            this.mobile= (TextView) itemView.findViewById(R.id.mobile);
            this.description=(TextView)itemView.findViewById(R.id.description);
            this.header= (LinearLayout) itemView.findViewById(R.id.header);
            this.Provider_logo= (ImageView) itemView.findViewById(R.id.search_propert_image);
            this.website= (ImageView) itemView.findViewById(R.id.website);
            this.location= (ImageView) itemView.findViewById(R.id.location);
            this.rightdata=(LinearLayout)itemView.findViewById(R.id.rightdata);
            this.recent_img_progress=(ProgressBar)itemView.findViewById(R.id.recent_img_progress);
            this.cardview=(CardView)itemView.findViewById(R.id.filtered_propert_card);

            this.customdialog=(RelativeLayout)itemView.findViewById(R.id.customdialog);
            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView1);
            Animation a = AnimationUtils.loadAnimation(context, R.anim.rotate);
            a.setDuration(2000);
            imageView.startAnimation(a);

            a.setInterpolator(new Interpolator()
            {
                private final int frameCount = 8;

                @Override
                public float getInterpolation(float input)
                {
                    return (float)Math.floor(input*frameCount)/frameCount;
                }
            });

        }
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
