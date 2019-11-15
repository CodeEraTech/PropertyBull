package com.doomshell.property_bull;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.doomshell.property_bull.helper.Serverapi;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;


public class Addproperty_extraDetails extends Fragment implements View.OnClickListener {

    Context context;
    View convertview;
    TextView room_minus,add_room_txt,room_plus;
    TextView bath_minus,add_bathroom_txt,bathroom_plus;
    TextView totalfloor_minus,add_totalfloors_txt,totalfloor_plus;
    TextView pon_floor_minus,add_pon_floor_txt,pon_floor_plus;
    Spinner add_propertyface_spiner,add_flooring_spiner;
    TextView age_minus,add_age_txt,age_plus;
    Button add_extra_btn;
    DisplayMetrics displayMetrics;
    double screenHeight;
    double screenWidth;

     int room_inc,bathrooninc,totalfloor_inc,pon_floor_inc,flooring_inc,propertface_inc,age_inc;
    String num_rooms,bathroom,totalFloor,propertonFloor,Flooring,propertyface,ageofproperty,option1,option2,proid;
String sroom_inc,sbathrooninc,stotalfloor_inc,spon_floor_inc,sage_inc;
    ArrayList<String> list_property_face;
    ArrayList<String> list_property_face_ID;
    ArrayList<String> list_flooring;
    String selected_faceid,selected_flooring;
    Dialog contactdialog;
    ProgressBar bar;

    RestAdapter restAdapter=null;
    Serverapi serverapi;
    String Baseurl;
    String location,title,landmark,pincode,address;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context=getActivity().getApplicationContext();
        list_property_face=new ArrayList<>();
        list_property_face_ID=new ArrayList<>();
        list_flooring=new ArrayList<>();
        convertview= inflater.inflate(R.layout.fragment_addproperty_extra_details, container, false);


        savedInstanceState=getArguments();


        option1=savedInstanceState.getString("option1");
        option2=savedInstanceState.getString("option2");
        proid=savedInstanceState.getString("proid");
        location=savedInstanceState.getString("location");
        title=savedInstanceState.getString("title");
        landmark=savedInstanceState.getString("landmark");
        pincode=savedInstanceState.getString("pincode");
        address=savedInstanceState.getString("address");


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

        displayMetrics=context.getResources().getDisplayMetrics();
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        int lwi= (int) (screenWidth*0.40);

        room_minus=(TextView)convertview.findViewById(R.id.room_minus);
        add_room_txt=(TextView)convertview.findViewById(R.id.add_room_txt);
        room_plus=(TextView)convertview.findViewById(R.id.room_plus);

        bath_minus=(TextView)convertview.findViewById(R.id.bathroom_minus);
        add_bathroom_txt=(TextView)convertview.findViewById(R.id.add_bathroom_txt);
        bathroom_plus=(TextView)convertview.findViewById(R.id.bathroom_plus);

        totalfloor_minus=(TextView)convertview.findViewById(R.id.totalfloor_minus);
        add_totalfloors_txt=(TextView)convertview.findViewById(R.id.add_totalfloors_txt);
        totalfloor_plus=(TextView)convertview.findViewById(R.id.totalfloor_plus);

        pon_floor_minus=(TextView)convertview.findViewById(R.id.pon_floor_minus);
        add_pon_floor_txt=(TextView)convertview.findViewById(R.id.add_pon_floor_txt);
        pon_floor_plus=(TextView)convertview.findViewById(R.id.pon_floor_plus);

        add_propertyface_spiner=(Spinner) convertview.findViewById(R.id.add_propertyface_spiner);
        add_flooring_spiner=(Spinner) convertview.findViewById(R.id.add_flooring_spiner);

        age_minus=(TextView)convertview.findViewById(R.id.age_minus);
        add_age_txt=(TextView)convertview.findViewById(R.id.add_age_txt);
        age_plus=(TextView)convertview.findViewById(R.id.age_plus);

        add_extra_btn=(Button)convertview.findViewById(R.id.add_extra_btn);

        add_extra_btn.getLayoutParams().width= lwi;
        add_extra_btn.requestLayout();



        room_minus.setOnClickListener(this);
        room_plus.setOnClickListener(this);
        bath_minus.setOnClickListener(this);
        bathroom_plus.setOnClickListener(this);
        totalfloor_minus.setOnClickListener(this);
        totalfloor_plus.setOnClickListener(this);
        pon_floor_minus.setOnClickListener(this);
        pon_floor_plus.setOnClickListener(this);
        age_minus.setOnClickListener(this);
        age_plus.setOnClickListener(this);
        add_extra_btn.setOnClickListener(this);

        show_dialogue();
        loadpropertyface();
        loadflooring();



        add_propertyface_spiner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (list_property_face.isEmpty() || list_property_face_ID.isEmpty()) {
                    show_dialogue();
                    loadpropertyface();
                }

                return false;
            }
        });

        add_flooring_spiner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(list_flooring.isEmpty())
                {
                    loadflooring();
                }

                return false;
            }
        });


        return convertview;
    }

    @Override
    public void onClick(View v) {
        if (v==room_minus)
        {
            String stotal=add_room_txt.getText().toString();
            room_inc=Integer.parseInt(stotal);

           decreaseInteger(add_room_txt,room_inc);

        }

        if (v==room_plus)
        {
            String stotal=add_room_txt.getText().toString();
            room_inc=Integer.parseInt(stotal);

            if(room_inc<5)
           increaseInteger(add_room_txt,room_inc);

        }

        if(v==bath_minus)
        {
            String stotal=add_bathroom_txt.getText().toString();
            bathrooninc=Integer.parseInt(stotal);

            decreaseInteger(add_bathroom_txt,bathrooninc);
        }
        if(v==bathroom_plus)
        {
            String stotal=add_bathroom_txt.getText().toString();
            bathrooninc=Integer.parseInt(stotal);

            if(bathrooninc<5)
            increaseInteger(add_bathroom_txt,bathrooninc);
        }
        if(v==totalfloor_minus)
        {
            String stotal=add_totalfloors_txt.getText().toString();
             totalfloor_inc=Integer.parseInt(stotal);
            decreaseInteger(add_totalfloors_txt,totalfloor_inc);
        }
        if(v==totalfloor_plus)
        {
            String stotal=add_totalfloors_txt.getText().toString();
            totalfloor_inc=Integer.parseInt(stotal);
            if(totalfloor_inc<25)
            increaseInteger(add_totalfloors_txt,totalfloor_inc);
        }

        if(v==pon_floor_minus)
        {
            String stotal=add_pon_floor_txt.getText().toString();
            pon_floor_inc=Integer.parseInt(stotal);
            decreaseInteger(add_pon_floor_txt,pon_floor_inc);
        }
        if(v==pon_floor_plus)
        {
            String stotal=add_pon_floor_txt.getText().toString();
            pon_floor_inc=Integer.parseInt(stotal);
            if(pon_floor_inc<=totalfloor_inc)
            increaseInteger(add_pon_floor_txt,pon_floor_inc);
        }

        if(v==age_minus)
        {
            String stotal=add_age_txt.getText().toString();
            age_inc=Integer.parseInt(stotal);
            decreaseInteger(add_age_txt,age_inc);
        }
        if(v==age_plus)
        {
            String stotal=add_age_txt.getText().toString();
            age_inc=Integer.parseInt(stotal);
            increaseInteger(add_age_txt,age_inc);
        }

        if(v==add_extra_btn)
        {
            sroom_inc=add_room_txt.getText().toString();
            sbathrooninc=add_bathroom_txt.getText().toString();
            stotalfloor_inc=add_totalfloors_txt.getText().toString();
            spon_floor_inc=add_pon_floor_txt.getText().toString();
            selected_flooring=add_flooring_spiner.getSelectedItem().toString();
            int selected_face=add_propertyface_spiner.getSelectedItemPosition();
            selected_faceid=list_property_face_ID.get(selected_face);

            sage_inc=add_age_txt.getText().toString();

            if(sroom_inc.equals("0"))
            {
                sroom_inc="";

            }
            if (sbathrooninc.equals("0"))
            {
                sbathrooninc="";
            }
            if (stotalfloor_inc.equals("0"))
            {
                stotalfloor_inc="";
            }
            if(spon_floor_inc.equals("0"))
            {
                spon_floor_inc="";
            }

            if (sage_inc.equals("0"))
            {
                sage_inc="";
            }

            AddProperty_discription addproperty_screen_location = new AddProperty_discription();
            Bundle bundle = new Bundle();
            bundle.putString("num_rooms", sroom_inc);
            bundle.putString("bathroom", sbathrooninc);
            bundle.putString("totalFloor", stotalfloor_inc);
            bundle.putString("propertonFloor", spon_floor_inc);
            bundle.putString("Flooring", selected_flooring);
            bundle.putString("propertyface", selected_faceid);
            bundle.putString("ageofproperty", sage_inc);
            bundle.putString("location", location);
            bundle.putString("title", title);
            bundle.putString("landmark", landmark);
            bundle.putString("pincode", pincode);
            bundle.putString("address", address);

            bundle.putString("option1", option1);
            bundle.putString("option2", option2);
            bundle.putString("proid", proid);

            addproperty_screen_location.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, addproperty_screen_location);
            fragmentTransaction.addToBackStack(addproperty_screen_location.getClass().toString());
            fragmentTransaction.commit();
        }


    }

    public void increaseInteger(View v,int num)
    {
        num = num+ 1;
        if(num<0)
        {
            num=0;
        }

        TextView txt= (TextView) v;
        txt.setText(""+num);

    }

    public void decreaseInteger(View view,int num) {
        num = num- 1;
        if (num<0)
        {
            num=0;
        }

        TextView txt= (TextView) view;
        txt.setText(""+num);
    }

    public void show_dialogue()
    {

        contactdialog.show();
    }

    public void dismiss_dialogue()
    {
        contactdialog.dismiss();
    }

    void loadflooring()
    {
        show_dialogue();
        list_flooring.add("Laminite");
        list_flooring.add("Stones");
        list_flooring.add("Ceramic");
        list_flooring.add("Parquet");
        list_flooring.add("Carpet");

        ArrayAdapter<String> flooring = new ArrayAdapter<String>(context, R.layout.spinner_item, list_flooring) {
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);

                ((TextView) v).setGravity(Gravity.CENTER);
                ((TextView) v).setTextColor(Color.BLACK);
               // ((TextView) v).setTextSize(15);

                return v;

            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                View v = super.getDropDownView(position, convertView, parent);

                ((TextView) v).setGravity(Gravity.CENTER);

                return v;

            }
        };
        add_flooring_spiner.setAdapter(flooring);
        dismiss_dialogue();
    }


     void loadpropertyface()
    {
        serverapi.propertyface(new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                String s=new String(((TypedByteArray) response.getBody()).getBytes());
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    int success=jsonObject.getInt("success");
                    if (success==1) {
                        JSONArray proarray = jsonObject.getJSONArray("output");
                        for (int i=0;i<proarray.length();i++) {
                            JSONObject c = proarray.getJSONObject(i);

                            String id=c.getString("id");
                            String name=c.getString("Propertyface");

                            list_property_face.add(name);
                            list_property_face_ID.add(id);
                        }


                        dismiss_dialogue();
                        String pn[] = new String[list_property_face.size()];
                        pn = list_property_face.toArray(pn);

                        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, pn) {
                            public View getView(int position, View convertView, ViewGroup parent) {

                                View v = super.getView(position, convertView, parent);

                                ((TextView) v).setGravity(Gravity.CENTER);
                                ((TextView) v).setTextColor(Color.BLACK);
                               // ((TextView) v).setTextSize(15);

                                return v;

                            }

                            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                                View v = super.getDropDownView(position, convertView, parent);

                                ((TextView) v).setGravity(Gravity.CENTER);

                                return v;

                            }
                        };
                        add_propertyface_spiner.setAdapter(stringArrayAdapter);
                    }
                    } catch (Exception e) {
                    dismiss_dialogue();
                    Toast.makeText(context,"Something went wrong on server",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }



            }

            @Override
            public void failure(RetrofitError error) {
                dismiss_dialogue();
                Toast.makeText(context,"Server error \n"+error,Toast.LENGTH_SHORT).show();
            }
        });
    }

}
