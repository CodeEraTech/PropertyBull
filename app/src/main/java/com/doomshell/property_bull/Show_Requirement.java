package com.doomshell.property_bull;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.doomshell.property_bull.adapter.ShowRequirement_Adapter;
import com.doomshell.property_bull.helper.CustomProgressDialog;
import com.doomshell.property_bull.helper.Serverapi;
import com.doomshell.property_bull.model.SharedPrefManager;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;


public class Show_Requirement extends Fragment {
  Context context;
    View convertview;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    String Baseurl;
    RestAdapter restAdapter=null;
    Serverapi serverapi;
    Dialog contactdialog;
    ProgressBar bar;
    AppCompatActivity appCompatActivity;
    ActionBar actionBar;

    RadioGroup rg_option;
    RadioButton sellbutton,rentbutton;
    int sellcount,rentcount;
     ArrayList<String> idlist;
    ArrayList<String> Propertytype;

    ArrayList<String> area;
    ArrayList<String> rooms;
    ArrayList<String> location;
    ArrayList<String> locationid;
    ArrayList<String> propertytypeid;
    ArrayList<String> min_budgetsearch,max_budgetsearch;
    ArrayList<String> budget;
    ArrayList<String> rentidlist;
    ArrayList<String> rentPropertytype;

    ArrayList<String> rentarea;
   ArrayList<String> rentrooms;
    ArrayList<String> createddate;
   ArrayList<String> rentlocation;

   ArrayList<String> rentbudget;
    CustomProgressDialog dialog;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context=getActivity().getApplicationContext();
        convertview= inflater.inflate(R.layout.fragment_showrequirement, container, false);
        idlist=new ArrayList<>();
        Propertytype=new ArrayList<>();
        area=new ArrayList<>();
        rooms=new ArrayList<>();
        location=new ArrayList<>();
        budget=new ArrayList<>();
        rentidlist=new ArrayList<>();
        rentPropertytype=new ArrayList<>();
        rentarea=new ArrayList<>();
        rentrooms=new ArrayList<>();
        rentlocation=new ArrayList<>();
        rentbudget=new ArrayList<>();
        locationid=new ArrayList<>();
        propertytypeid=new ArrayList<>();
        min_budgetsearch=new ArrayList<>();
        max_budgetsearch=new ArrayList<>();
        appCompatActivity = (AppCompatActivity) getActivity();


        Toolbar toolbar=(Toolbar)getActivity().findViewById(R.id.toolbar);
        TextView screentitle=(TextView)toolbar.findViewById(R.id.screentitle);
        ImageView imageView=(ImageView) toolbar.findViewById(R.id.screenimageview);
        BottomNavigationView bottomnavView = getActivity().findViewById(R.id.bottom_nav_view);
        bottomnavView.setVisibility(View.GONE);
        screentitle.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
        screentitle.setText("My Requirements");
        rg_option=(RadioGroup)convertview.findViewById(R.id.rg_option);
        /*sellbutton=(RadioButton)convertview.findViewById(R.id.buy);
        rentbutton=(RadioButton)convertview.findViewById(R.id.rent);*/

        Baseurl = getActivity().getResources().getString(R.string.myurl);
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(5, TimeUnit.MINUTES.MINUTES);
        okHttpClient.setReadTimeout(5, TimeUnit.MINUTES.MINUTES);
        //  myCustomProgress_dialogue=new MyCustomProgress_dialogue(getActivity(),R.color.colorPrimary);

        restAdapter = new RestAdapter.Builder().setEndpoint(Baseurl).setClient(new OkClient(okHttpClient)).build();
        serverapi = restAdapter.create(Serverapi.class);

        contactdialog = new Dialog(getActivity());
        contactdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        contactdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00ff1919")));
        bar = new ProgressBar(getActivity(), null, android.R.attr.progressBarStyleLarge);
//bar.setProgress()
        bar.getIndeterminateDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY);
        contactdialog.setContentView(bar);
        contactdialog.setCancelable(true);


        recyclerView=(RecyclerView)convertview.findViewById(R.id.ourservice_recycleview);
        layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        load_requirement();

        rg_option.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                RadioButton radioButton=(RadioButton)convertview.findViewById(i);
                if(radioButton.getText().toString().equals("Buy"))
                {
                    ShowRequirement_Adapter adapter=new ShowRequirement_Adapter(getActivity(),idlist,Propertytype,area,rooms,location,budget,createddate,"Sell",locationid,propertytypeid,max_budgetsearch,min_budgetsearch,(AppCompatActivity)getActivity());
                   /* adapter=new Ourservices_Adapter(context,idlist,consultant_name_list,getActivity());*/
                    recyclerView.setAdapter(adapter);
                    recyclerView.setHasFixedSize(true);
                    adapter.notifyDataSetChanged();

                    //This will scroll page-by-page so that you can view scroll happening

                    //loadslider(photo_slider);
                }
                else if(radioButton.getText().toString().equalsIgnoreCase("Rent"))
                {
                    ShowRequirement_Adapter adapter=new ShowRequirement_Adapter(getActivity(),rentidlist,rentPropertytype,rentarea,rentrooms,rentlocation,rentbudget,createddate,"Rent",locationid,propertytypeid,max_budgetsearch,min_budgetsearch,(AppCompatActivity)getActivity());
                   /* adapter=new Ourservices_Adapter(context,idlist,consultant_name_list,getActivity());*/
                    recyclerView.setAdapter(adapter);
                    recyclerView.setHasFixedSize(true);
                    adapter.notifyDataSetChanged();


                    //loadslider(floor_slider);
                }

            }
        });


        return convertview;
    }
void  load_requirement()
{

    show_dialogue();
    //SharedPrefManager.getInstance(getActivity()).getuser_details("id");
    serverapi.view_requirement(SharedPrefManager.getInstance(getActivity()).getuser_details("id"),new Callback<Response>() {
        @Override
        public void success(Response response, Response response2) {
            String s=new String(((TypedByteArray) response.getBody()).getBytes());
            dismiss_dialogue();
            try {
                JSONObject jsonObject = new JSONObject(s);
                int success = jsonObject.getInt("success");
                createddate=new ArrayList<String>();


                if (success==1) {
                    JSONArray products = jsonObject.getJSONArray("output");

                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);



                        if(!c.getString("location_id").equalsIgnoreCase("null"))
                        {
                            locationid.add(c.getString("location_id"));
                        }
                        else
                        {
                            locationid.add("");
                        }
                        if(!c.getString("p_typeid").equalsIgnoreCase("null"))
                        {
                            propertytypeid.add(c.getString("p_typeid"));
                        }
                        else
                        {
                            propertytypeid.add("");
                        }
                        if(!c.getString("min_budgetsearch").equalsIgnoreCase("null"))
                        {
                            min_budgetsearch.add(c.getString("min_budgetsearch"));
                        }
                        else
                        {
                            min_budgetsearch.add("");
                        }
                        if(!c.getString("max_budgetsearch").equalsIgnoreCase("null"))
                        {
                            max_budgetsearch.add(c.getString("max_budgetsearch"));
                        }
                        else
                        {
                            max_budgetsearch.add("");
                        }
                        if(!c.getString("created").equalsIgnoreCase("null"))
                        {
                            Date newateformat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(c.getString("created"));
                            String dd=new SimpleDateFormat("dd MMMM yyyy").format(newateformat);
                            createddate.add(dd);
                        }
                        else
                        {
                            createddate.add("");
                        }
                        if(c.getString("category").equals("Sell")) {
                            sellcount=sellcount+1;
                            Propertytype.add(c.getString("Propertytype"));
                            idlist.add(c.getString("id"));

                            location.add(c.getString("location"));
                            if(!c.getString("min_room").equalsIgnoreCase("0")) {
                                if(!c.getString("max_room").equalsIgnoreCase("null")) {
                                    if (c.getString("min_room").equals(c.getString("max_room"))) {
                                        rooms.add(c.getString("max_room") + " BHK");
                                    } else {
                                        rooms.add(c.getString("max_room") + " BHK");
                                    }
                                }
                                else
                                {
                                    rooms.add("");
                                }
                            }
                            else
                            {
                                rooms.add("");
                            }
                            if (c.getString("min_area").equals(c.getString("max_area"))) {
                                if(!c.getString("unit").equalsIgnoreCase("")) {
                                    if (!c.getString("unit").equalsIgnoreCase("null")) {
                                        area.add(c.getString("max_area") + " "+c.getString("unit"));
                                    }
                                    else
                                    {
                                        area.add(c.getString("max_area"));
                                    }
                                }
                            } else {
                                if(!c.getString("unit").equalsIgnoreCase("")) {
                                    if (!c.getString("unit").equalsIgnoreCase("null")) {
                                        area.add(c.getString("min_area") + " - " + c.getString("max_area")+ " "+c.getString("unit"));
                                    }
                                    else
                                    {
                                        area.add(c.getString("min_area") + " - " + c.getString("max_area"));
                                    }
                                }
                                else {
                                    area.add(c.getString("min_area") + " - " + c.getString("max_area"));
                                }
                                }
                            if (c.getString("min_budget").equals(c.getString("max_budget"))) {
                                budget.add(c.getString("min_budget"));
                            } else {
                                budget.add(c.getString("min_budget") + " to " + c.getString("max_budget"));
                            }
                        }
                        else
                        {
                            rentcount=rentcount+1;
                            rentPropertytype.add(c.getString("Propertytype"));
                            rentidlist.add(c.getString("id"));

                            rentlocation.add(c.getString("location"));
                            if(!c.getString("max_room").equalsIgnoreCase("0")) {
                                if(!c.getString("max_room").equalsIgnoreCase("null")) {
                                    if (c.getString("min_room").equals(c.getString("max_room"))) {
                                        rentrooms.add(c.getString("max_room") + " BHK");
                                    } else {
                                        rentrooms.add(c.getString("max_room") + " BHK");
                                    }
                                }
                                else
                                {
                                    rentrooms.add("");
                                }
                            }
                            else
                            {
                                rentrooms.add("");
                            }
                            if (c.getString("min_area").equals(c.getString("max_area"))) {
                                if(!c.getString("unit").equalsIgnoreCase("")) {
                                    if (!c.getString("unit").equalsIgnoreCase("null")) {
                                        rentarea.add(c.getString("max_area") + " "+c.getString("unit"));
                                    }
                                    else
                                    {
                                        rentarea.add(c.getString("max_area"));
                                    }
                                }
                            } else {
                                if(!c.getString("unit").equalsIgnoreCase("")) {
                                    if (!c.getString("unit").equalsIgnoreCase("null")) {
                                        rentarea.add(c.getString("min_area") + " - " + c.getString("max_area")+ " "+c.getString("unit"));
                                    }
                                    else
                                    {
                                        rentarea.add(c.getString("min_area") + " - " + c.getString("max_area"));
                                    }
                                }
                                else {
                                    rentarea.add(c.getString("min_area") + " - " + c.getString("max_area"));
                                }
                            }
                            if (c.getString("min_budget").equals(c.getString("max_budget"))) {
                                rentbudget.add(c.getString("min_budget"));
                            } else {
                                rentbudget.add(c.getString("min_budget") + " to " + c.getString("max_budget"));
                            }
                        }



                    }

                    ShowRequirement_Adapter adapter=new ShowRequirement_Adapter(getActivity(),idlist,Propertytype,area,rooms,location,budget,createddate,"Sell",locationid,propertytypeid,max_budgetsearch,min_budgetsearch,(AppCompatActivity)getActivity());
                   /* adapter=new Ourservices_Adapter(context,idlist,consultant_name_list,getActivity());*/
                    recyclerView.setAdapter(adapter);
                    recyclerView.setHasFixedSize(true);
                    adapter.notifyDataSetChanged();

                   // sellbutton.setText("Buy ("+sellcount+")");
                   // rentbutton.setText("Rent ("+rentcount+")");


                }else {
                    dismiss_dialogue();
                    Toast.makeText(context,"No Requirement Found",Toast.LENGTH_SHORT).show();
                }
                }catch (Exception e)
            {

                Toast.makeText(context,"Check Internet Connection",Toast.LENGTH_SHORT).show();
                dismiss_dialogue();

            }
            }

        @Override
        public void failure(RetrofitError error) {
            dismiss_dialogue();
            Toast.makeText(context,"Check Internet Connection",Toast.LENGTH_SHORT).show();
        }
    });
}

    public void show_dialogue()
    {

        dialog=new CustomProgressDialog(getActivity());
    }

    public void dismiss_dialogue()
    {
        dialog.hide();
    }

    public String stripHtml(String html) {return Html.fromHtml(html).toString();}
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (adapter != null) {
            ShowRequirement_Adapter adapter1= (ShowRequirement_Adapter) adapter;
            adapter1.saveStates(outState);

        }
    }
/*
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (adapter != null) {
            Fav_Property_adapter adapter1= (Fav_Property_adapter) adapter;
            adapter1.restoreStates(savedInstanceState);
        }
    }*/

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (adapter != null) {
            ShowRequirement_Adapter adapter1= (ShowRequirement_Adapter) adapter;
            adapter1.restoreStates(savedInstanceState);
        }
    }
}
