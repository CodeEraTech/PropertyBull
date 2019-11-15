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
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.doomshell.property_bull.helper.CustomProgressDialog;
import com.doomshell.property_bull.helper.Serverapi;
import com.doomshell.property_bull.model.SharedPrefManager;
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

public class Addproperty_screen_Location extends Fragment implements View.OnClickListener {
    Context context;
    View convertview;
    EditText p3_title, p3_landmark, p3_pincode, p3_address;
    Spinner p3_location;
    ArrayList<String> localitynameList;
    ArrayList<String> localityidList;
    String sLocalityid;
    String slocation, stitle, slandmark, spincode, saddress;
    boolean islocation, islandmark, isaddress, isprojectid;
    boolean istitle, ispincode;
    Button p3_next_btn;
    String num_rooms = "", bathroom = "", totalFloor = "", propertonFloor = "", Flooring = "", propertyface = "", ageofproperty = "", option1 = "", option2 = "", proid = "";
    Dialog contactdialog;
    ProgressBar bar;
    String Baseurl;
    RestAdapter restAdapter = null;
    Serverapi serverapi;
    DisplayMetrics displayMetrics;
    double screenHeight;
    double screenWidth;
    Spinner projectidspinner;
    ArrayList<String> proname;
    CustomProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity().getApplicationContext();
        convertview = inflater.inflate(R.layout.fragment_addproperty_screen__location, container, false);
        proname = new ArrayList<>();

        savedInstanceState = getArguments();
   /*     num_rooms=savedInstanceState.getString("num_rooms");
        bathroom=savedInstanceState.getString("bathroom");
        totalFloor=savedInstanceState.getString("totalFloor");
        propertonFloor=savedInstanceState.getString("propertonFloor");
        Flooring=savedInstanceState.getString("Flooring");
        propertyface=savedInstanceState.getString("propertyface");
        ageofproperty=savedInstanceState.getString("ageofproperty");*/
        option1 = savedInstanceState.getString("option1");
        option2 = savedInstanceState.getString("option2");
        //  proid=savedInstanceState.getString("proid");

        displayMetrics = context.getResources().getDisplayMetrics();
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        int lwi = (int) (screenWidth * 0.40);

        Baseurl = getActivity().getResources().getString(R.string.myurl);
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(5, TimeUnit.MINUTES.MINUTES);
        okHttpClient.setReadTimeout(5, TimeUnit.MINUTES.MINUTES);
        //  myCustomProgress_dialogue=new MyCustomProgress_dialogue(getActivity(),R.color.colorPrimary);

        restAdapter = new RestAdapter.Builder().setEndpoint(Baseurl).setClient(new OkClient(okHttpClient)).build();
        serverapi = restAdapter.create(Serverapi.class);
        localitynameList = new ArrayList<>();
        localityidList = new ArrayList<>();
        contactdialog = new Dialog(getActivity());
        contactdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        contactdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00ff1919")));
        bar = new ProgressBar(getActivity(), null, android.R.attr.progressBarStyleLarge);
//bar.setProgress()
        bar.getIndeterminateDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY);
        contactdialog.setContentView(bar);
        contactdialog.setCancelable(false);


        /*bundle.putString("num_rooms", num_rooms);
        bundle.putString("bathroom", bathroom);
        bundle.putString("totalFloor", totalFloor);
        bundle.putString("propertonFloor", propertonFloor);
        bundle.putString("Flooring", Flooring);
        bundle.putString("propertyface", propertyface);
        bundle.putString("ageofproperty", ageofproperty);
        bundle.putString("option1", option1);
        bundle.putString("option2", option2);
        bundle.putString("proid", proid);*/

        projectidspinner = (Spinner) convertview.findViewById(R.id.projectid);
        getprojectname();
        //ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item,proname);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_item, proname) {
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);

                ((TextView) v).setGravity(Gravity.LEFT);
                if (position == 0)
                    ((TextView) v).setTextColor(getResources().getColor(R.color.dropback));

                else {
                    ((TextView) v).setTextColor(Color.BLACK);
                }

                return v;

            }
        };

        projectidspinner.setAdapter(adapter);

        projectidspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                proid = get_ptype_id(proname.get(i));


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        p3_location = (Spinner) convertview.findViewById(R.id.p3_location);
        p3_title = (EditText) convertview.findViewById(R.id.p3_title);
        p3_landmark = (EditText) convertview.findViewById(R.id.p3_landmark);
        p3_pincode = (EditText) convertview.findViewById(R.id.p3_pincode);
        p3_address = (EditText) convertview.findViewById(R.id.p3_address);
        p3_next_btn = (Button) convertview.findViewById(R.id.p3_next_btn);

        p3_next_btn.getLayoutParams().width = lwi;
        p3_next_btn.requestLayout();

        // show_dialogue();
        load_locality();

        p3_next_btn.setOnClickListener(this);

//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        return convertview;
    }

    @Override
    public void onClick(View v) {
        if (v == p3_next_btn) {

            stitle = p3_title.getText().toString();
            slandmark = p3_landmark.getText().toString();
            //spincode=p3_pincode.getText().toString();
            saddress = p3_address.getText().toString();

            if (sLocalityid == null || sLocalityid.equals("")) {
                islocation = false;
                //  Toast.makeText(getActivity(),"Please Select Locality",Toast.LENGTH_SHORT).show();

            } else {
                islocation = true;

            }

            if (stitle == null || stitle.equals("")) {
                istitle = false;
                //  Toast.makeText(getActivity(),"Please Fill Title",Toast.LENGTH_SHORT).show();
            } else {
                istitle = true;
            }

            if (slandmark == null || slandmark.equals("")) {
                islandmark = false;
                //  Toast.makeText(getActivity(),"Please Fill Landmark",Toast.LENGTH_SHORT).show();
            } else {
                islandmark = true;
            }

            /*if (spincode==null || spincode.equals(""))
            {
                ispincode=false;
              //  Toast.makeText(getActivity(),"Please Fill Pincode",Toast.LENGTH_SHORT).show();
            }
            else {
                ispincode=true;
            }*/

            if (saddress == null || saddress.equals("")) {
                isaddress = false;
                //   Toast.makeText(getActivity(),"Please Fill Address",Toast.LENGTH_SHORT).show();
            } else {

                isaddress = true;

            }
            if (proid.equals("")) {
                isprojectid = false;
                //    Toast.makeText(context,"Please Select Project",Toast.LENGTH_SHORT).show();
            } else {
                isprojectid = true;
            }

            if (islocation && islandmark && isaddress && isprojectid && istitle) {

                if (proid.equals("321") || proid.equals("320") || proid.equals("339") || proid.equals("327")) {

                    Addproperty_extraDetails addproperty_extraDetails = new Addproperty_extraDetails();
                    Bundle bundle = new Bundle();

                    bundle.putString("option1", option1);
                    bundle.putString("option2", option2);
                    bundle.putString("proid", proid);


                    bundle.putString("location", sLocalityid);
                    bundle.putString("title", stitle);
                    bundle.putString("landmark", slandmark);
                    bundle.putString("pincode", spincode);
                    bundle.putString("address", saddress);
                    addproperty_extraDetails.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_container, addproperty_extraDetails);
                    fragmentTransaction.addToBackStack(addproperty_extraDetails.getClass().toString());
                    proname.clear();

                    fragmentTransaction.commit();


                } else {
                    AddProperty_discription addProperty_discription = new AddProperty_discription();
                    Bundle bundle = new Bundle();
                    bundle.putString("num_rooms", num_rooms);
                    bundle.putString("bathroom", bathroom);
                    bundle.putString("totalFloor", totalFloor);
                    bundle.putString("propertonFloor", propertonFloor);
                    bundle.putString("Flooring", Flooring);
                    bundle.putString("propertyface", propertyface);
                    bundle.putString("ageofproperty", ageofproperty);
                    bundle.putString("option1", option1);
                    bundle.putString("option2", option2);
                    bundle.putString("proid", proid);

                    bundle.putString("location", sLocalityid);
                    bundle.putString("title", stitle);
                    bundle.putString("landmark", slandmark);
                    bundle.putString("pincode", spincode);
                    bundle.putString("address", saddress);
                    addProperty_discription.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_container, addProperty_discription);
                    fragmentTransaction.addToBackStack(addProperty_discription.getClass().toString());
                    fragmentTransaction.commit();
                }

            } else {
                Toast.makeText(context, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void show_dialogue() {

        dialog = new CustomProgressDialog(getActivity());
    }

    public void dismiss_dialogue() {
        dialog.hide();
    }


    private void load_locality() {
        //    Toast.makeText(context, ""+SharedPrefManager.getInstance(context).getuser_details("city_id"), Toast.LENGTH_SHORT).show();
        show_dialogue();
        serverapi.locality(SharedPrefManager.getInstance(context).getuser_details("city_id"), new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {


                try {
                    String s = new String(((TypedByteArray) response.getBody()).getBytes());
                    JSONObject jsonObject = new JSONObject(s);
                    int success = jsonObject.getInt("success");
                    localityidList.add("");
                    localitynameList.add("Select Locality");
                    if (success == 1) {
                        JSONArray cityarray = jsonObject.getJSONArray("output");

                        for (int i = 0; i < cityarray.length(); i++) {
                            JSONObject c = cityarray.getJSONObject(i);
                            String cityid = c.getString("id");
                            String cityname = c.getString("name");

                            localityidList.add(cityid);
                            localitynameList.add(cityname);
                        }
                        ArrayAdapter<String> locationadaptor = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, localitynameList) {
                            public View getView(int position, View convertView, ViewGroup group) {
                                View view = super.getView(position, convertView, group);
                                ((TextView) view).setGravity(Gravity.LEFT);
                                if (position == 0) {
                                    ((TextView) view).setTextColor(getResources().getColor(R.color.dropback));
                                } else {
                                    ((TextView) view).setTextColor(Color.BLACK);
                                }

                                return view;

                            }
                        };
                        p3_location.setAdapter(locationadaptor);
                        p3_location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                sLocalityid = localityidList.get(i);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                                sLocalityid = "";

                            }
                        });
                        dismiss_dialogue();

                    } else {
                        dismiss_dialogue();
                        //   Log.d()
                        Toast.makeText(context, "Something went wrong on server", Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    dismiss_dialogue();

                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                dismiss_dialogue();
                Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    String get_ptype_id(String rid) {
        String id;
        switch (rid) {
            case "Select Property Type":
                id = "";
                break;
            case "Housing flat":
                id = "343";
                break;
            case "Industrial Plot":
                id = "353";
                break;
            case "Basement":
                id = "346";
                break;
            case "Floor Apartment":
                id = "322";
                break;
            case "Business Center":
                id = "328";
                break;
            case "Commercial Land":
                id = "324";
                break;
            case "Commercial Office Space":
                id = "325";
                break;
            case "Commercial Shop":
                id = "326";
                break;
            case "Commercial Showroom":
                id = "327";
                break;
            case "Duplex":
                id = "345";
                break;
            case "Farm House":
                id = "335";
                break;
            case "Hostel":
                id = "339";
                break;
            case "Hotel":
                id = "338";
                break;
            case "Industrial Building":
                id = "331";
                break;
            case "Industrial Land":
                id = "330";
                break;
            case "Paying Guest":
                id = "336";
                break;
            case "Penthouse":
                id = "341";
                break;
            case "Residential Farm house":
                id = "350";
                break;
            case "Residential Flat":
                id = "321";
                break;
            case "Residential House":
                id = "320";
                break;
            case "Residential Plot":
                id = "319";
                break;
            case "Residential Villa":
                id = "342";
                break;
            case "Skeleton":
                id = "340";
                break;
            case "Studio Apartment":
                id = "347";
                break;
            case "Warehouse / Godown":
                id = "329";
                break;
            default:
                id = "";
                break;
        }
        return id;
    }

    public void getprojectname() {
        proname.add("Select Property Type");

        if (option2.equals("RESIDENTIAL") && option1.equals("SELL")) {
           /* proname.add("Housing flat");

            proname.add("Basement");

            proname.add("Floor Apartment");

            proname.add("Business Center");

            proname.add("Duplex");

            proname.add("Farm House");



            proname.add("Hotel");*/

            /*proname.add("Paying Guest");

            proname.add("Penthouse");

            proname.add("Residential Farm house");*/

            proname.add("Residential Flat");

            proname.add("Residential House");

            proname.add("Residential Plot");

            proname.add("Hostel");

            proname.add("Residential Villa");

            proname.add("Industrial Plot");

           /* proname.add("Skeleton");

            proname.add("Studio Apartment");

            proname.add("Warehouse / Godown");*/

        } else if (option2.equals("COMMERCIAL") && option1.equals("SELL")) {
            proname.add("Commercial Land");

            proname.add("Commercial Office Space");

            proname.add("Commercial Shop");

            proname.add("Commercial Showroom");

           /* proname.add("Industrial Building");

            proname.add("Industrial Land");*/


        }
        if (option2.equals("RESIDENTIAL") && option1.equals("RENT")) {
           /* proname.add("Housing flat");

            proname.add("Basement");

            proname.add("Floor Apartment");

            proname.add("Business Center");

            proname.add("Duplex");

            proname.add("Farm House");



            proname.add("Hotel");*/

            /*proname.add("Paying Guest");

            proname.add("Penthouse");

            proname.add("Residential Farm house");*/

            proname.add("Residential Flat");

            proname.add("Residential House");

            proname.add("Residential Plot");

            proname.add("Hostel");

            proname.add("Residential Villa");

            proname.add("Industrial Plot");

           /* proname.add("Skeleton");

            proname.add("Studio Apartment");

            proname.add("Warehouse / Godown");*/

        } else if (option2.equals("COMMERCIAL") && option1.equals("RENT")) {
            proname.add("Commercial Land");

            proname.add("Commercial Office Space");

            proname.add("Commercial Shop");

            proname.add("Commercial Showroom");



           /* proname.add("Industrial Building");

            proname.add("Industrial Land");*/


        }


    }


}
