package com.doomshell.property_bull;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.doomshell.property_bull.adapter.Provider_Adapter;
import com.doomshell.property_bull.helper.CustomProgressDialog;
import com.doomshell.property_bull.helper.Serverapi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;


public class Service_provider_details extends Fragment {
    Context context;
    View convertview;
    ImageView imageView;
    TextView title,content;
    LinearLayout overcard_layout;
    String stitle,scotent,imageurl,id;
    DisplayMetrics displayMetrics;
    double screenHeight;
    double screenWidth;
  //  Dialog contactdialog;
    ProgressBar bar;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    RestAdapter restAdapter;
    Serverapi serverapi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context=getActivity().getApplicationContext();
        convertview= inflater.inflate(R.layout.fragment_service_provider_details, container, false);

        savedInstanceState=getArguments();
     //   String s=savedInstanceState.getString("jarray",null);


        //imageView=(ImageView)convertview.findViewById(R.id.deatails_loveastro_image);
       // title=(TextView)convertview.findViewById(R.id.deatail_Loveastro_title);
       // content=(TextView)convertview.findViewById(R.id.detail_loveastro_content);
       // overcard_layout=(LinearLayout)convertview.findViewById(R.id.overcard_layout);

      //  title.setSelected(true);

      /*  displayMetrics=new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        int lhi= (int) (screenHeight*0.20);
        int lwi= (int) screenWidth;

        //RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(lwi,lhi);
        overcard_layout.getLayoutParams().height=lhi;
        overcard_layout.requestLayout();*/


        imageurl= savedInstanceState.getString("love_image");
        stitle= savedInstanceState.getString("love_title");
        id= savedInstanceState.getString("id");

    //    Glide.with(context).load(imageurl).into(imageView);
    //    title.setText(stitle);
        //content.setText(scotent);
        recyclerView=(RecyclerView)convertview.findViewById(R.id.providers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

       /* contactdialog = new Dialog(getActivity());
        contactdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        contactdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00ff1919")));
        bar = new ProgressBar(getActivity(), null, android.R.attr.progressBarStyleLarge);
//bar.setProgress()
        bar.getIndeterminateDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY);
        contactdialog.setContentView(bar);
        contactdialog.setCancelable(true);*/

        restAdapter=new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.myurl)).build();
        serverapi = restAdapter.create(Serverapi.class);

        load_service_providers();






        return convertview;
    }
    void  load_service_providers()
    {

        //show_dialogue();
        final CustomProgressDialog dialog=new CustomProgressDialog(getActivity());
        serverapi.consltants_post(id,new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                String s=new String(((TypedByteArray) response.getBody()).getBytes());
                dialog.hide();

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int success = jsonObject.getInt("success");
                    ArrayList<String> logourl=new ArrayList<String>();
                    ArrayList<String> companyname=new ArrayList<String>();
                    ArrayList<String> contactdetails=new ArrayList<String>();
                    ArrayList<String> company_url=new ArrayList<String>();
                    ArrayList<String> email=new ArrayList<String>();
                    ArrayList<String> mobile=new ArrayList<String>();
                    ArrayList<String> address=new ArrayList<String>();
                    ArrayList<String> about=new ArrayList<String>();
                    ArrayList<String> created=new ArrayList<String>();
                    ArrayList<String> start=new ArrayList<String>();
                    ArrayList<String> lat=new ArrayList<String>();
                    ArrayList<String> lng=new ArrayList<String>();
                  //  ArrayList<String> companyurl=new ArrayList<String>();
                    if (success==1) {
                        JSONArray products = jsonObject.getJSONArray("output");

                        for (int i = 0; i < products.length(); i++) {
                            JSONObject c = products.getJSONObject(i);

                            logourl.add(c.getString("company_logo").toString());
                            if(c.getString("lat").toString().trim().equalsIgnoreCase("null") ) {
                                lat.add("-");
                            }
                            else
                            {
                                lat.add(c.getString("lat").toString().trim());
                            }
                            if(c.getString("lng").toString().trim().equalsIgnoreCase("null") ) {
                                lng.add("-");
                            }
                            else
                            {
                                lng.add(c.getString("lng").toString().trim());
                            }
                            if(c.getString("start").toString().trim().equalsIgnoreCase("null") || c.getString("start").toString().trim().equalsIgnoreCase("") ) {
                                start.add("-");
                            }
                            else
                            {
                                start.add(c.getString("start").toString().trim());
                            }
                            if(c.getString("created").toString().trim().equalsIgnoreCase("null") ) {
                                created.add("-");
                            }
                            else
                            {
                                Date newateformat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(c.getString("created"));
                                String dd=new SimpleDateFormat("MMMM dd, yyyy").format(newateformat);
                               // date.add(dd);
                                created.add(dd);
                            }
                            if(c.getString("company_name").toString().trim().equalsIgnoreCase("null") ) {
                                companyname.add("-");
                            }
                            else
                            {
                                companyname.add(c.getString("company_name").toString().trim());
                            }

                            contactdetails.add(c.getString("contact_person").toString().trim());
                            company_url.add(Html.fromHtml(c.getString("company_url").toString()).toString());
                            email.add(c.getString("email").toString().trim());
                            mobile.add(c.getString("mobile").toString().trim());

                            if(c.getString("location").toString().equalsIgnoreCase("null") ) {
                                if(c.getString("state").toString().equalsIgnoreCase("null") ) {
                                    address.add(c.getString("address").toString());
                                }
                                else
                                {
                                    address.add(c.getString("address").toString() + " " + c.getString("state").toString() );
                                }
                            }
                            else {
                                address.add(c.getString("address").toString() + " " + c.getString("location").toString() + " " + c.getString("state").toString());
                            }
                            about.add(Html.fromHtml(c.getString("description").toString()).toString());


                        }
                        dialog.hide();
                        adapter=new Provider_Adapter(context,logourl,companyname,contactdetails,company_url,email,mobile,address,about,start,created,lat,lng,getActivity());
                   /* adapter=new Ourservices_Adapter(context,idlist,consultant_name_list,getActivity());*/
                        recyclerView.setAdapter(adapter);

                    }else {
                        dialog.hide();
                        Toast.makeText(context,"No Provider Found",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e)
                {
                    Toast.makeText(context,"Something went wrong on server",Toast.LENGTH_SHORT).show();
                    dialog.hide();
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
              //  dismiss_dialogue();
                dialog.hide();
                Toast.makeText(context,"Check Internet Connection",Toast.LENGTH_SHORT).show();
            }
        });
    }



   /* public void dismiss_dialogue()
    {
        contactdialog.dismiss();
    }*/

    public String stripHtml(String html) {return Html.fromHtml(html).toString();}
}
