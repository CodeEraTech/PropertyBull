package com.doomshell.property_bull;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.doomshell.property_bull.adapter.MySpinnerAdapter;
import com.doomshell.property_bull.helper.CustomProgressDialog;
import com.doomshell.property_bull.helper.Serverapi;
import com.doomshell.property_bull.model.SharedPrefManager;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;


public class Add_Requirement extends Fragment {

    Context context;
    View convertview;
    RadioGroup rg;
    RadioButton rb,buy_rb_req;
    String option;
    TextView btn;
    Spinner rq_type_spiner;
    Spinner bedroomnospinner,unit_spiner;
    ArrayList<String> ptype_list,unit_list;
    String pname,pid;
    boolean ispid,iscity,isoption;

    Spinner rq_autocity;
    ArrayList<String> citynameList;
    ArrayList<String> cityidList;
    String scity;
    String cid;
    ArrayList<String> numOfBed_list;
    CustomProgressDialog dialog;

    CrystalRangeSeekbar rq_price_rangeSeekbar;
    TextView rq_price_seek_min_text,rq_price_seek_max_text;
    String minprice="";
    String maxprice="";

    CrystalRangeSeekbar rq_area_rangeSeekbar;
    TextView rq_area_seek_min_text,rq_area_seek_max_text;
    String minarea="";
    String maxarea="",unit="";

    //CrystalRangeSeekbar rq_room_rangeSeekbar;
    TextView rq_room_seek_min_text,rq_room_seek_max_text;
    String minrooms="";
    String maxrooms="";
    boolean isroom,isunit;

    Dialog contactdialog;
    ProgressBar bar;
    String Baseurl;
    RestAdapter restAdapter=null;
    Serverapi serverapi;
    AppCompatActivity appCompatActivity;
    ActionBar actionBar;
    boolean isrent,issell;
    LinearLayout addR_rooms_layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        context=getActivity().getApplicationContext();
        appCompatActivity = (AppCompatActivity) getActivity();
        Toolbar toolbar=(Toolbar)getActivity().findViewById(R.id.toolbar);
        TextView screentitle=(TextView)toolbar.findViewById(R.id.screentitle);
        ImageView imageView=(ImageView) toolbar.findViewById(R.id.screenimageview);
        BottomNavigationView bottomnavView = getActivity().findViewById(R.id.bottom_nav_view);
        bottomnavView.setVisibility(View.GONE);
        screentitle.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
        screentitle.setText("Add Requirement");
        ptype_list=new ArrayList<>();

        cityidList=new ArrayList<>();
        citynameList=new ArrayList<>();
        cityidList.add(0,"");
        citynameList.add(0,"Select Locality");

        convertview=inflater.inflate(R.layout.fragment_add__requirement, container, false);

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
        contactdialog.setCancelable(false);


        rg = (RadioGroup) convertview.findViewById(R.id.rq_rg_option);
        btn=(TextView)convertview.findViewById(R.id.rq_btn);
        rq_type_spiner=(Spinner)convertview.findViewById(R.id.rq_type_spiner);
        rq_autocity= (Spinner) convertview.findViewById(R.id.rq_autocity);
        rq_price_rangeSeekbar = (CrystalRangeSeekbar) convertview.findViewById(R.id.rq_price_rangeSeekbar);
        rq_area_rangeSeekbar = (CrystalRangeSeekbar) convertview.findViewById(R.id.rq_area_rangeSeekbar);
      //  rq_room_rangeSeekbar = (CrystalRangeSeekbar) convertview.findViewById(R.id.rq_room_rangeSeekbar);
        rq_price_seek_min_text=(TextView)convertview.findViewById(R.id.rq_price_seek_min_text);
        rq_price_seek_max_text=(TextView)convertview.findViewById(R.id.rq_price_seek_max_text);
        rq_area_seek_min_text=(TextView)convertview.findViewById(R.id.rq_area_seek_min_text);
        rq_area_seek_max_text=(TextView)convertview.findViewById(R.id.rq_area_seek_max_text);
        addR_rooms_layout=(LinearLayout) convertview.findViewById(R.id.addR_rooms_layout);
        buy_rb_req=(RadioButton) convertview.findViewById(R.id.buy_rb_req);

        buy_rb_req.setChecked(true);


     //   rq_autocity.setSelected(false);
        //rq_autocity.setFocusable(false);

        //rq_autocity.clearFocus();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rg.getCheckedRadioButtonId()==-1)
                {
                    isoption=false;
                    option="";

                }else {
                    int optionid=rg.getCheckedRadioButtonId();

                    rb=(RadioButton)convertview.findViewById(optionid);

                    option=rb.getText().toString();
                    isoption=true;

                }


                if (pname.equals("Please Select Property Type"))
                {
                    ispid=false;
                    pid="";
                    // Toast.makeText(context,"Please Select Any Property Type",Toast.LENGTH_SHORT).show();
                }else {
                    pid=get_ptype_id(pname);
                    ispid=true;
                }
                if (maxrooms.equals("Select Number Of Bedroom"))
                {
                    isroom=false;

                    // Toast.makeText(context,"Please Select Rooms",Toast.LENGTH_SHORT).show();
                }else {

                    isroom=true;
                }
                if(unit.equalsIgnoreCase("Select Unit Of Area"))
                {
                    isunit=false;
                }
                else
                {
                    isunit=true;
                }


                if(cid==null || cid.equals(""))
                {
                    iscity=false;
                    // Toast.makeText(context,"Please Select Locality",Toast.LENGTH_SHORT).show();
                }else {
                    iscity=true;
                }

                if (minprice.equals("900000"))
                {
                    minprice="";
                }

                if (maxprice.equals("100100000"))
                {
                    maxprice="";
                }

                if(ispid && iscity && isoption && isunit)
                {
                    show_dialogue();
                    load_addproperty();
                }
                else
                {
                    if(!isoption) {
                        Toast.makeText(context, "Please Select Any Option(Buy Or Rent)", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        rq_price_rangeSeekbar.setMaxValue(100000000);
        // rq_price_rangeSeekbar.setmi
        rq_price_seek_min_text.setText("10 lacs");
        rq_price_seek_max_text.setText("10 crore");
        rq_price_rangeSeekbar.setMinValue(1000000);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rg.getCheckedRadioButtonId()==-1)
                {
                    isoption=false;
                    option="";
                }else {
                    int optionid=rg.getCheckedRadioButtonId();

                    rb=(RadioButton)convertview.findViewById(optionid);

                    option=rb.getText().toString();
                    if(option.equalsIgnoreCase("buy"))
                    {
                    //    issell=true;
                        isrent=false;
                        rq_price_rangeSeekbar.setMaxValue(100000000);
                        // rq_price_rangeSeekbar.setmi
                        rq_price_seek_min_text.setText("10 lacs");
                        rq_price_seek_max_text.setText("10 crore");
                        rq_price_rangeSeekbar.setMinValue(1000000);

                    }
                    else{
                        rq_price_rangeSeekbar.setMaxValue(100000);
                        rq_price_rangeSeekbar.setMinValue(5000);
                        rq_price_seek_min_text.setText("5 thousand");
                        rq_price_seek_max_text.setText("10 lacs");
                      //  issell=false;
                        maxprice="1000000";
                        minprice="5000";

                        isrent=true;
                    }


                }
            }
        });

        ptype_list.add("Please Select Property Type");
        /*ptype_list.add("Affordable Housing Flat");
        ptype_list.add("Basement");
        ptype_list.add("Builder Floor Apartment");
        ptype_list.add("Business Centre");*/
        ptype_list.add("Residential Plot");
        ptype_list.add("Residential House");
        ptype_list.add("Residential Flat");
        ptype_list.add("Commercial Land");
        ptype_list.add("Commercial Office Space");
        ptype_list.add("Commercial Shop");
        ptype_list.add("Commercial Showroom");
        /*ptype_list.add("Duplex");
        ptype_list.add("Farm House");*/
        ptype_list.add("Hostel");
       /* ptype_list.add("Hotel");
        ptype_list.add("Industrial Building");
        ptype_list.add("Industrial Land");
        ptype_list.add("Paying Guest");
        ptype_list.add("PentHouse");
        ptype_list.add("Residential Farm House");*/



        ptype_list.add("Residential Villa");
        ptype_list.add("Industrial Plot");
       /* ptype_list.add("Skeleton");
        ptype_list.add("Studio Apartment");
        ptype_list.add("Warehouse\\/ Godown");*/

        rq_type_spiner.setAdapter(new MySpinnerAdapter(context,ptype_list));

        unit_spiner=(Spinner)convertview.findViewById(R.id.unit_spiner);

        unit_list=new ArrayList<>();
        unit_list.add("Select Unit Of Area");
        unit_list.add("Sq Ft");
        unit_list.add("Sq Mtr");
        unit_list.add("Sq Yard");
        unit_list.add("Bigha");
        unit_list.add("Hectare");

        unit_spiner.setAdapter(new MySpinnerAdapter(context,unit_list));

        unit_spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                unit=unit_list.get(i).toString();
                if(unit.equalsIgnoreCase("Hectare"))
                {
                    unit="hec";
                }
                else
                {
                    unit=unit_list.get(i).toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        bedroomnospinner=(Spinner)convertview.findViewById(R.id.noroom_spiner);



        numOfBed_list=new ArrayList<>();
        numOfBed_list.add("Select Number Of Bedroom");
        numOfBed_list.add("1");
        numOfBed_list.add("2");
        numOfBed_list.add("3");
        numOfBed_list.add("4");
        numOfBed_list.add("5");
        numOfBed_list.add("6");
        numOfBed_list.add("7");
        numOfBed_list.add("8");
        numOfBed_list.add("9");
        numOfBed_list.add("10");

        bedroomnospinner.setAdapter(new MySpinnerAdapter(context,numOfBed_list));

        bedroomnospinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maxrooms=numOfBed_list.get(i).toString();
                minrooms=numOfBed_list.get(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            maxrooms="";
                minrooms="";
            }
        });



        rq_type_spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    pname = ptype_list.get(position).toString();
                if(pname.equals("Residential Plot") || pname.equals("Commercial Land") ||
                        pname.equals("Commercial Office Space") || pname.equals("Commercial Shop")
                        ||pname.equals("Residential Villa")
                        ||pname.equals("Commercial Showroom")||pname.equals("Industrial Plot"))
                {
                    addR_rooms_layout.setVisibility(View.GONE);
                }else {
                    addR_rooms_layout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                pname="";
            }
        });


          //  show_dialogue();

/*
        ArrayAdapter<String> locationadaptor=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item,citynameList){
            public View getView(int position,View convertView,ViewGroup group)
            {
                View view=super.getView(position,convertView,group);
                ((TextView)view).setGravity(Gravity.LEFT);
                if(position==0)
                {
                    ((TextView)view).setTextColor(getResources().getColor(R.color.dropback));
                }
                else
                {
                    ((TextView)view).setTextColor(Color.BLACK);
                }

                return  view;

            }
        };*/
        rq_autocity.setAdapter(new MySpinnerAdapter(context,citynameList));
        rq_autocity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cid=cityidList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                cid="";

            }
        });


        rq_price_rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {

                float max=maxValue.intValue();
                float min=minValue.intValue();
                if(isrent) {

                    if (minValue.intValue() < 100000) {
                    //   max = max / 1000;
                        min = min / 1000;

                        rq_price_seek_min_text.setText((int)min+" thousand");
                    } else if (minValue.intValue() >= 100000 && minValue.intValue() <= 9999999) {
                   //     max = max / 100000;
                        min = min / 100000;

                        DecimalFormat df=new DecimalFormat("##.##");
                      //  rq_price_seek_min_text.setText("" + df.format(min) + " lakh");
                        rq_price_seek_min_text.setText("" +(int)min + " lacs");
                    } else {
                    //    max = max / 100000;
                        min = min / 100000;
                        DecimalFormat df=new DecimalFormat("##.##");
                        //rq_price_seek_min_text.setText("" + df.format(min) + " crore");
                        rq_price_seek_min_text.setText("" +(int)min + " crore");
                    }

                    if(maxValue.intValue()==100000)
                    {
                        max = max / 100000;
                     //   min = min / 100000;
                        DecimalFormat df=new DecimalFormat("##.##");
                       // rq_price_seek_max_text.setText("" + df.format(max) + " lakh");
                        rq_price_seek_max_text.setText("" +(int)max + " lacs");
                    }else if (maxValue.intValue() < 100000) {
                        max = max / 1000;
                      //  min = min / 10000;

                        DecimalFormat df=new DecimalFormat("##.##");
                        // rq_price_seek_max_text.setText("" + df.format(max)+ " thousand");
                        rq_price_seek_max_text.setText("" + (int)max+ " thousand");
                    } else if (maxValue.intValue() >= 100000 && maxValue.intValue() <= 9999999) {
                        max = max / 100000;
                     //   min = min / 100000;
                        DecimalFormat df=new DecimalFormat("##.##");
                     //   rq_price_seek_max_text.setText("" + df.format(max) + " lakh");
                        rq_price_seek_max_text.setText("" +(int)max + " lakh");
                    }
                    minprice = "" + minValue.intValue();
                    maxprice = "" + maxValue.intValue();


                    Log.d("min :",""+minValue.intValue());
                    Log.d("max :",""+maxValue.intValue());

                }
                else
                {

                   if (minValue.intValue() < 10000000) {

                       min = min / 100000;

                       DecimalFormat df=new DecimalFormat("##.##");
                        rq_price_seek_min_text.setText(df.format(min)+" lacs");
                    } else if (minValue.intValue() >= 10000000  && minValue.intValue() <= 9999999) {

                       min = min / 1000000;

                       DecimalFormat df=new DecimalFormat("##.##");
                       rq_price_seek_min_text.setText(df.format(min)+" crore");
                    } else {
                       min = min / 10000000;

                       DecimalFormat df=new DecimalFormat("##.##");
                       rq_price_seek_min_text.setText(df.format(min)+" crore");
                    }


                    if (maxValue.intValue() == 100000000) {
                        max = max / 10000000;
                        DecimalFormat df=new DecimalFormat("##.##");
                        rq_price_seek_max_text.setText(df.format(max)+ " crore");
                    } else if ( maxValue.intValue() >=9999999) {
                        max = max / 10000000;
                        DecimalFormat df=new DecimalFormat("##.##");
                        rq_price_seek_max_text.setText("" + df.format(max) + " crore");
                    } else {
                        max = max / 100000;
                        DecimalFormat df=new DecimalFormat("##.##");
                        rq_price_seek_max_text.setText(df.format(max) + " lacs");
                    }
                    minprice = "" + minValue.intValue();
                    maxprice = "" + maxValue.intValue();
                }
            }
        });


        rq_area_rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {

                rq_area_seek_min_text.setText(""+minValue);
                rq_area_seek_max_text.setText(""+maxValue);

                minarea=""+minValue;
                maxarea=""+maxValue;

            }
        });


        load_city();

        return convertview;
    }

 /*   @Override
    public void onClick(View v) {
        if(v==btn)
        {


            if (rg.getCheckedRadioButtonId()==-1)
            {
                isoption=false;
                option="";

            }else {
                int optionid=rg.getCheckedRadioButtonId();

                rb=(RadioButton)convertview.findViewById(optionid);

                option=rb.getText().toString();
                isoption=true;

            }


            if (pname.equals("Please Select Property Type"))
            {
                ispid=false;
                pid="";
               // Toast.makeText(context,"Please Select Any Property Type",Toast.LENGTH_SHORT).show();
            }else {
                pid=get_ptype_id(pname);
                ispid=true;
            }
            if (maxrooms.equals("Select Number Of Bedroom"))
            {
                isroom=false;

               // Toast.makeText(context,"Please Select Rooms",Toast.LENGTH_SHORT).show();
            }else {

                isroom=true;
            }


            if(cid==null || cid.equals(""))
            {
                iscity=false;
               // Toast.makeText(context,"Please Select Locality",Toast.LENGTH_SHORT).show();
            }else {
                iscity=true;
            }

            if (minprice.equals("900000"))
            {
                minprice="";
            }

            if (maxprice.equals("100100000"))
            {
                maxprice="";
            }

if(ispid && iscity && isoption)
{
    show_dialogue();
    load_addproperty();
}
else
{
    if(!isoption) {
        Toast.makeText(context, "Please Select Any Option(Buy Or Rent)", Toast.LENGTH_SHORT).show();
    }else {
        Toast.makeText(getActivity(), "Please Fill All Fields", Toast.LENGTH_SHORT).show();
    }
}


//            Toast.makeText(context,""+option,Toast.LENGTH_SHORT).show();
        }

    }*/


    void load_addproperty()
    {
        if(option.equalsIgnoreCase("buy"))
        {
         //   Toast.makeText(context, "changing "+option +" to Sell", Toast.LENGTH_SHORT).show();
            option="Sell";
        }
        if(maxrooms.equalsIgnoreCase("Select Number Of Bedroom"))
        {
            maxrooms="0";
            minrooms="0";
        }
        serverapi.add_requirement(option,
                SharedPrefManager.getInstance(context).getuser_details("id"),
                pid,
                "27",
                cid,
                maxarea,
                minarea,
                minrooms,
                maxrooms,
                minprice,
                maxprice,
                unit,
                new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        String s=new String(((TypedByteArray) response.getBody()).getBytes());

                        try {
                            JSONObject jsonObject=new JSONObject(s);
                            int success=jsonObject.getInt("success");
                            if(success==1){

                               // SharedPrefManager.getInstance(context).saveuser_details("city_id",cid);
                                Toast.makeText(context,"Requirement Added Successfully",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(context,Home_Activity.class);
                                startActivity(intent);
                                getActivity().finish();
                     //           Toast.makeText(context,""+jsonObject,Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(context,"Requirement Not Added",Toast.LENGTH_SHORT).show();

                            }

                            dismiss_dialogue();
                          //  Toast.makeText(context,""+jsonObject,Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            dismiss_dialogue();
                            e.printStackTrace();
                            //Toast.makeText(context,"Exception is "+e.toString(),Toast.LENGTH_SHORT).show();
                           // Toast.makeText(context,"Something went wrong on server",Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        dismiss_dialogue();
                       // Toast.makeText(context,"Error is "+error.toString(),Toast.LENGTH_SHORT).show();
                      //  Toast.makeText(context,"Something went wrong on server : "+error.toString(),Toast.LENGTH_SHORT).show();


                    }
                });


    }

    void load_city()
    {
        show_dialogue();
        String id=SharedPrefManager.getInstance(getActivity()).getuser_details("city_id");
        serverapi.locality(id, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                String s=new String(((TypedByteArray) response.getBody()).getBytes());
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    JSONArray cityarray = jsonObject.getJSONArray("output");

                    for (int i=0;i<cityarray.length();i++)
                    {
                        JSONObject c = cityarray.getJSONObject(i);
                        String cityid=c.getString("id");
                        String cityname=c.getString("name");

                        cityidList.add(cityid);
                        citynameList.add(cityname);
                    }
                    dismiss_dialogue();

                } catch (Exception e) {
                //    Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
                    Log.e("req citycity", e.toString());
                    dismiss_dialogue();
                    e.printStackTrace();

                }

            }

            @Override
            public void failure(RetrofitError error) {
                dismiss_dialogue();
              //  Toast.makeText(context, ""+error, Toast.LENGTH_SHORT).show();
                Log.e("req city", error.toString());
                Toast.makeText(context,"Check Internet Connection",Toast.LENGTH_SHORT).show();

            }
        });
    }


    String get_ptype_id(String rid)
    {

        String id;
        switch (rid)
        {
            case "Affordable Housing Flat":
                id= "343";
                break;
            case "Industrial Plot":
                id= "353";
                break;
            case "Basement":
                id= "346";
                break;
            case "Builder Floor Apartment":
                id= "322";
                break;
            case "Business Centre":
                id= "328";
                break;
            case "Commercial Land":
                id= "324";
                break;
            case "Commercial Office Space":
                id= "325";
                break;
            case "Commercial Shop":
                id= "326";
                break;
            case "Commercial Showroom":
                id= "327";
                break;
            case "Duplex":
                id= "345";
                break;
            case "Farm House":
                id= "335";
                break;
            case "Hostel":
                id= "339";
                break;
            case "Hotel":
                id= "338";
                break;
            case "Industrial Building":
                id= "331";
                break;
            case "Industrial Land":
                id= "330";
                break;
            case "Paying Guest":
                id= "336";
                break;
            case "PentHouse":
                id= "341";
                break;
            case "Residential Farm House":
                id= "350";
                break;
            case "Residential Flat":
                id= "321";
                break;
            case "Residential House":
                id= "320";
                break;
            case "Residential Plot":
                id= "319";
                break;
            case "Residential Villa":
                id= "342";
                break;
            case "Skeleton":
                id= "340";
                break;
            case "Studio Apartment":
                id= "347";
                break;
            case "Warehouse\\/ Godown":
                id= "329";
                break;
            default:
                id= "";
                break;
        }
        return id;
    }

    public void show_dialogue()
    {

        dialog=new CustomProgressDialog(getActivity());
    }

    public void dismiss_dialogue()
    {
        dialog.hide();
    }

}
