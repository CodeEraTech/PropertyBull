package com.doomshell.property_bull;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANConstants;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.doomshell.property_bull.adapter.RecentSliderAdaptor;
import com.doomshell.property_bull.adapter.Search_Property_adapter;
import com.doomshell.property_bull.helper.CustomProgressDialog;
import com.doomshell.property_bull.helper.Serverapi;
import com.doomshell.property_bull.model.SharedPrefManager;
import com.google.android.gms.internal.nu;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.barcode.Barcode;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class PropertyDetails extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private MapView mapView;
    String latitude, longitude;
    private LinearLayout llMainContactus;
    private LinearLayout llContactus2;
    private EditText edtName;
    private EditText edtEmail;
    private EditText edtMobile;
    private EditText edtAddress;
    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private EditText edtSecurityCode;
    private CheckBox checkBox4;
    private ProgressBar pbSubmitContact;
    private Button btnSubmitContactus, btnLocation;

    /*    ArrayList<String> pid_list = new ArrayList<>();
        ArrayList<String> pname_list = new ArrayList<>();
        ArrayList<String> ppostedby_list = new ArrayList<>();
        ArrayList<String> pmobile_list = new ArrayList<>();
        ArrayList<String> ptype_list = new ArrayList<>();
        ArrayList<String> pbedroom_list = new ArrayList<>();
        ArrayList<String> ptotalarea_list = new ArrayList<>();
        ArrayList<String> ptotalprice_list = new ArrayList<>();
        ArrayList<String> ppriceperunit_list = new ArrayList<>();
        ArrayList<String> pimage_list = new ArrayList<>();
        ArrayList<String> localitylist = new ArrayList<>();
        ArrayList<String> addresslist = new ArrayList<>();
        ArrayList<String> landmarklist = new ArrayList<>();*/
    AlertDialog alertDialog;
    ArrayList<String> Sim_pid_list;
    ArrayList<String> Sim_pname_list;
    ArrayList<String> Sim_pimage_list;
    ArrayList<String> Sim_ptype_list;
    ArrayList<String> Sim_ptotalarea_list;
    ArrayList<String> Sim_pmobile_list;
    ArrayList<String> Sim_pbedroom_list;
    ArrayList<String> Sim_ptotalprice_list;
    ArrayList<String> Sim_ppriceperunit_list;
    ArrayList<String> Sim_ppostedby_list;
    ArrayList<String> Sim_addresslist;
    ArrayList<String> Sim_localitylist;
    ArrayList<String> Sim_landmarklist;
    ArrayList<String> Sim_descriptionlist;
    ArrayList<String> Sim_depositlist;
    ArrayList<String> Sim_bathlist;
    ArrayList<String> Sim_p_floorlist;
    ArrayList<String> Sim_flooringlist;
    ArrayList<String> Sim_floorlist;


    String pid_list, pname_list, ppostedby_list, pmobile_list, ptype_list, pbedroom_list, ptotalarea_list, ptotalprice_list, ppriceperunit_list, pimage_list, localitylist, addresslist, landmarklist, descriptionlist, depositlist;

    int current_position;
    ViewPager details_pager;
    PagerAdapter pagerAdapter;
    Context context;
    View convertView;
    AppCompatActivity appCompatActivity;
    ActionBar actionBar;

    AlertDialog contact_alert;

    TextView price, discription, Builtup_Area, Postedby, PricePerUnit, Bedroom, Society, detail_desc;
    ImageView detail_callbtn;
    Button viewcontact, sendmsg, next, previous;
    public static Intent callIntent;
    public static int CallState = 111;
    String mprice;

    String Baseurl;
    RestAdapter restAdapter = null;
    Serverapi serverapi;
    String coordinatesHolder = "";
    RecyclerView similar_recycler;
    RelativeLayout property_img_relative_layout;

    DisplayMetrics displayMetrics;
    double screenHeight;
    double screenWidth;
    int lhi, whi;
    private String id = "";
    FloatingActionButton share_fab_search;
    String split[];
    ArrayList<String> photo_slider = new ArrayList<>();
    URI imageuri;
    TextView descrioption;
    LinearLayout depositlyt;
    TextView deposit, p_floor, floor, bathroom, flooring;
    String flooringlist, bathlist, floorlist, p_floorlist;
    LinearLayout flooringlyt, bathlyt, pfloorlyt, tfloorlyt, bathfloorlyttop, bathfloorlyt, floorpfloor, floorpfloortop;
    Button topbutton, btn_contact_now;
    String url;
    TextView similartext;
    private ImageView imageView_share;
    private FrameLayout mapframe;
    private RelativeLayout rlframe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getActivity().getApplicationContext();
        //convertView= inflater.inflate(R.layout.activity_property_details, container, false);
        convertView = inflater.inflate(R.layout.detail_property_layout, container, false);
        appCompatActivity = (AppCompatActivity) getActivity();

        id = SharedPrefManager.getInstance(getActivity()).getuser_details("id");

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        TextView screentitle = (TextView) toolbar.findViewById(R.id.screentitle);
        topbutton = (Button) toolbar.findViewById(R.id.savesearch);
        mapframe = (FrameLayout) convertView.findViewById(R.id.mapframe);
        rlframe = (RelativeLayout) convertView.findViewById(R.id.rlframe);
        btnLocation = (Button) convertView.findViewById(R.id.btnLocation);
        topbutton.setVisibility(View.GONE);
        screentitle.setText("Property Detail");
        imageView_share = (ImageView) toolbar.findViewById(R.id.share_recent);
        imageView_share.setVisibility(View.VISIBLE);
        imageView_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_and_showpermission();
            }
        });

        similartext = (TextView) convertView.findViewById(R.id.similartext);
        descrioption = (TextView) convertView.findViewById(R.id.descrioption);
        depositlyt = (LinearLayout) convertView.findViewById(R.id.depositlyt);
        deposit = (TextView) convertView.findViewById(R.id.deposit);
        p_floor = (TextView) convertView.findViewById(R.id.p_floor);
        floor = (TextView) convertView.findViewById(R.id.floor);
        bathroom = (TextView) convertView.findViewById(R.id.bathroom);
        flooring = (TextView) convertView.findViewById(R.id.flooring);
        flooringlyt = (LinearLayout) convertView.findViewById(R.id.flooringlyt);
        bathlyt = (LinearLayout) convertView.findViewById(R.id.bathlyt);
        pfloorlyt = (LinearLayout) convertView.findViewById(R.id.pfloorlyt);
        tfloorlyt = (LinearLayout) convertView.findViewById(R.id.tfloorlyt);
        bathfloorlyttop = (LinearLayout) convertView.findViewById(R.id.bathfloorlyttop);
        bathfloorlyt = (LinearLayout) convertView.findViewById(R.id.bathfloorlyt);
        floorpfloor = (LinearLayout) convertView.findViewById(R.id.floorpfloor);
        floorpfloortop = (LinearLayout) convertView.findViewById(R.id.floorpfloortop);
        Bundle extras = this.getArguments();
        if (extras != null) {
            if (!TextUtils.isEmpty(latitude)) {

                latitude = extras.getString("latitude").toString();


            } else {

                Barcode.GeoPoint p1 = getLocationFromAddress(extras.getString("addresslist").trim() + "" + extras.getString("localitylist").trim());
                if (p1 != null) {
                    latitude = String.valueOf(p1.lat);
                } else {
                    latitude = "9.877";
                }


            }
            if (!TextUtils.isEmpty(longitude)) {
                longitude = extras.getString("longitude").toString();

            } else {
                Barcode.GeoPoint p1 = getLocationFromAddress(extras.getString("addresslist").trim() + "" + extras.getString("localitylist").trim());
                if (p1 != null) {
                    longitude = String.valueOf(p1.lng);

                } else {
                    longitude = "9.877";
                }
            }
            pid_list = extras.getString("pid_list").trim();
            pname_list = extras.getString("pname_list").trim();
            ppostedby_list = extras.getString("ppostedby_list").trim();
            pmobile_list = extras.getString("pmobile_list").trim();
            ptype_list = extras.getString("ptype_list").trim();
            pbedroom_list = extras.getString("pbedroom_list").trim();
            ptotalarea_list = extras.getString("ptotalarea_list").trim();
            ptotalprice_list = extras.getString("ptotalprice_list").trim();
            ppriceperunit_list = extras.getString("ppriceperunit_list").trim();
            pimage_list = extras.getString("pimage_list").trim();
            localitylist = extras.getString("localitylist").trim();
            addresslist = extras.getString("addresslist").trim();
            landmarklist = extras.getString("landmarklist").trim();
            descriptionlist = extras.getString("descriptionlist").trim();
            depositlist = extras.getString("depositlist").trim();
            p_floorlist = extras.getString("p_floorlist").trim();
            floorlist = extras.getString("floorlist").trim();
            flooringlist = extras.getString("flooringlist").trim();
            bathlist = extras.getString("bathlist").trim();
            url = extras.getString("url").trim();

            current_position = extras.getInt("currentpos");
            split = pimage_list.split("\\,");


            if (bathlist.equalsIgnoreCase("-")) {
                bathlyt.setVisibility(View.GONE);
            } else {
                bathroom.setText(bathlist);
            }
            if (p_floorlist.equalsIgnoreCase("-")) {
                pfloorlyt.setVisibility(View.GONE);
            } else {
                p_floor.setText(p_floorlist);
            }
            if (floorlist.equalsIgnoreCase("-")) {
                tfloorlyt.setVisibility(View.GONE);
            } else {
                floor.setText(floorlist);
            }
            if (flooringlist.equalsIgnoreCase("-")) {
                flooringlyt.setVisibility(View.GONE);
            } else {
                flooring.setText(flooringlist);
            }
            if (flooringlist.equalsIgnoreCase("-") && bathlist.equalsIgnoreCase("-")) {
                bathfloorlyt.setVisibility(View.GONE);
                bathfloorlyttop.setVisibility(View.GONE);
            }
            if (p_floorlist.equalsIgnoreCase("-") && floorlist.equalsIgnoreCase("-")) {
                floorpfloortop.setVisibility(View.GONE);
                floorpfloor.setVisibility(View.GONE);
            }
            if (depositlist.equalsIgnoreCase("-")) {
                depositlyt.setVisibility(View.GONE);
            } else {
                depositlyt.setVisibility(View.VISIBLE);
                deposit.setText("\u20B9" + depositlist);
            }
            if (addresslist.equalsIgnoreCase("null")) {
                addresslist = "-";
            }
            if (landmarklist.equalsIgnoreCase("null")) {
                landmarklist = "-";
            }
            if (ptotalprice_list.equalsIgnoreCase("null")) {
                ptotalprice_list = "-";
            }
            if (ppriceperunit_list.equalsIgnoreCase("null")) {
                ppriceperunit_list = "-";
            }
            if (localitylist.equalsIgnoreCase("null")) {
                localitylist = "-";
            }
            if (pid_list.equalsIgnoreCase("null")) {
                pid_list = "-";
            }
            if (pname_list.equalsIgnoreCase("null")) {
                pname_list = "-";
            }
            if (ppostedby_list.equalsIgnoreCase("null")) {
                ppostedby_list = "-";
            }
            if (pmobile_list.equalsIgnoreCase("null")) {
                pmobile_list = "";
            }
            if (ptype_list.equalsIgnoreCase("null")) {
                ptype_list = "-";
            }
            if (pbedroom_list.equalsIgnoreCase("null")) {
                pbedroom_list = "-";
            }
            if (ptotalarea_list.equalsIgnoreCase("null")) {
                ptotalarea_list = "-";
            }

            if (descriptionlist.equalsIgnoreCase("-")) {
                descriptionlist = "-";
            }

        }
        btn_contact_now = (Button) convertView.findViewById(R.id.btn_contact_now);

        btn_contact_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog_contact(getActivity());
            }
        });

        mapView = (MapView) convertView.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        Baseurl = getActivity().getResources().getString(R.string.myurl);
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(5, TimeUnit.MINUTES.MINUTES);
        okHttpClient.setReadTimeout(5, TimeUnit.MINUTES.MINUTES);
        rlframe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "ff", Toast.LENGTH_SHORT).show();
                coordinatesHolder = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(coordinatesHolder));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "ff", Toast.LENGTH_SHORT).show();
                String geoUri = "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude + " (" + "Proerty" + ")";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


            }
        });
        restAdapter = new RestAdapter.Builder().setEndpoint(Baseurl).setClient(new OkClient(okHttpClient)).build();
        serverapi = restAdapter.create(Serverapi.class);

        for (int i = 0; i < split.length; i++) {
            photo_slider.add(split[i].toString());
        }

        displayMetrics = getActivity().getApplicationContext().getResources().getDisplayMetrics();
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        //  whi= (int) (screenWidth*0.50);
        lhi = (int) (screenHeight * 0.30);


        property_img_relative_layout = (RelativeLayout) convertView.findViewById(R.id.property_img_relative_layout);
        details_pager = (ViewPager) convertView.findViewById(R.id.slider);

        property_img_relative_layout.getLayoutParams().height = (int) lhi;
        //  property_img_relative_layout.getLayoutParams().width= (int) whi;
        property_img_relative_layout.requestLayout();



       /* pagerAdapter=new Custom_Page_adapter(context,pid_list,pname_list,ppostedby_list,pmobile_list,ptype_list,pbedroom_list,
                ptotalarea_list,ptotalprice_list,ppriceperunit_list,pimage_list,localitylist,addresslist,landmarklist,details_pager,getActivity());*/

        ImageButton leftNav = (ImageButton) convertView.findViewById(R.id.left_nav);
        ImageButton rightNav = (ImageButton) convertView.findViewById(R.id.right_nav);

        if (photo_slider.size() == 0) {
            leftNav.setVisibility(View.GONE);
            rightNav.setVisibility(View.GONE);
            RecentSliderAdaptor myCustomPagerAdapter = new RecentSliderAdaptor(getActivity(), photo_slider, getActivity());
            //slider.setAdapter(myCustomPagerAdapter);
            details_pager.setAdapter(myCustomPagerAdapter);
        } else if (photo_slider.size() == 1) {
            leftNav.setVisibility(View.GONE);
            rightNav.setVisibility(View.GONE);
            RecentSliderAdaptor myCustomPagerAdapter = new RecentSliderAdaptor(getActivity(), photo_slider, getActivity());
            //slider.setAdapter(myCustomPagerAdapter);
            details_pager.setAdapter(myCustomPagerAdapter);
        } else if (photo_slider.size() > 1) {

            RecentSliderAdaptor myCustomPagerAdapter = new RecentSliderAdaptor(getActivity(), photo_slider, getActivity());
            //slider.setAdapter(myCustomPagerAdapter);
            details_pager.setAdapter(myCustomPagerAdapter);
        }

// Images left navigation
        leftNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = details_pager.getCurrentItem();
                if (tab > 0) {
                    tab--;
                    details_pager.setCurrentItem(tab);
                } else if (tab == 0) {
                    details_pager.setCurrentItem(tab);
                }
            }
        });

        // Images right navigatin
        rightNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = details_pager.getCurrentItem();
                tab++;
                details_pager.setCurrentItem(tab);
            }
        });
        //details_pager.setCurrentItem(current_position);

        price = (TextView) convertView.findViewById(R.id.detail_price);
        discription = (TextView) convertView.findViewById(R.id.detail_description);
        Builtup_Area = (TextView) convertView.findViewById(R.id.detail_builtup);
        Postedby = (TextView) convertView.findViewById(R.id.detail_postedby);
        PricePerUnit = (TextView) convertView.findViewById(R.id.detail_priceper_unit);
        Bedroom = (TextView) convertView.findViewById(R.id.detail_bedroom);
        Society = (TextView) convertView.findViewById(R.id.detail_society);
        detail_desc = (TextView) convertView.findViewById(R.id.detail_desc);
        detail_callbtn = (ImageView) convertView.findViewById(R.id.detail_callbtn);
        viewcontact = (Button) convertView.findViewById(R.id.detail_viewcontact_btn);
        sendmsg = (Button) convertView.findViewById(R.id.detail_sendmsg_btn);
        similar_recycler = (RecyclerView) convertView.findViewById(R.id.similar_recycler);
        share_fab_search = (FloatingActionButton) convertView.findViewById(R.id.share_fab_search);
        similar_recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));


        Button detail_sendmsg_btn = (Button) convertView.findViewById(R.id.detail_sendmsg_btn);


        detail_sendmsg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = pmobile_list;
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null));// The number on which you want to send SMS
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addCategory(Intent.CATEGORY_DEFAULT);
                startActivity(i);
            }
        });


        if (ptotalprice_list.toString().equals("-") || ptotalprice_list.toString().equalsIgnoreCase("null")
                || ptotalprice_list.toString().equals("0")) {
            mprice = "Price On Request";
            price.setText(mprice);

        } else {

            mprice = ptotalprice_list.toString();
            price.setText("\u20B9 " + mprice);
        }

        /*else {
            int p=Integer.parseInt(ptotalprice_list.get(position));
            float f=p/100000;
            mprice=""+f;
        }*/


        //   Toast.makeText(context, ""+ptype_list, Toast.LENGTH_SHORT).show();
        if (ptype_list.equalsIgnoreCase("Residential Plot") || ptype_list.equalsIgnoreCase("Commercial Land") ||
                ptype_list.equalsIgnoreCase("Commercial Office Space") || ptype_list.equalsIgnoreCase("Commercial Shop")
                || ptype_list.equalsIgnoreCase("Residential Villa")
                || ptype_list.equalsIgnoreCase("Commercial Showroom")) {
            discription.setText(pname_list + "\u25CF" + " " + ptype_list);
        } else {

            if (pbedroom_list.equalsIgnoreCase("0") || pbedroom_list.equalsIgnoreCase("-")) {
                discription.setText(pname_list + "\u25CF" + " " + ptype_list);

            } else {
                discription.setText("" + pbedroom_list + " BHK " + "\u25CF" + " " + pname_list + "\u25CF" + " " + ptype_list);
            }
        }

        //Builtup_Area.setText(""+ptotalarea_list.get(position));
        Builtup_Area.setText(pid_list.toString());
        Postedby.setText("" + ppostedby_list);
        PricePerUnit.setText(ptotalarea_list.toString() + " " + ppriceperunit_list);
        Bedroom.setText("" + landmarklist + " ( " + localitylist + " )");
        Society.setText(addresslist.toString());
        try {
            if (descriptionlist.equalsIgnoreCase("-")) {
                detail_desc.setVisibility(View.GONE);
                descrioption.setVisibility(View.GONE);
            } else {
                detail_desc.setText(descriptionlist.toString());
            }


        } catch (Exception e) {

        }
        detail_callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pmobile_list == null || pmobile_list.equals("")) {
                    Toast.makeText(getActivity(), "Number not provided", Toast.LENGTH_SHORT).show();
                } else {
                    callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + pmobile_list));

                    int myAPI = Build.VERSION.SDK_INT;

                    if (myAPI >= 23) {
                        int result = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CALL_PHONE);
                        if (result == PackageManager.PERMISSION_GRANTED) {
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(callIntent);
                        } else {
                            //      Toast.makeText(context, "permission required to make call, Please give permission", Toast.LENGTH_LONG).show();

                            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.CALL_PHONE)) {
                                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.CALL_PHONE},
                                        CallState);
                            } else {
                                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.CALL_PHONE},
                                        CallState);
                            }
                        }

                    } else {
                        callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(callIntent);
                    }
                }
            }
        });


        viewcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialogue(ppostedby_list.toString(), pmobile_list.toString());
            }
        });

        //container.addView(itemView);

        share_fab_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_and_showpermission();
            }
        });

        load_similar_property();

        return convertView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap != null) {

            LatLng latLng = new LatLng(Float.parseFloat(latitude), Float.parseFloat(longitude));
            googleMap.addMarker(new MarkerOptions().position(latLng));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            CameraUpdate zoom = CameraUpdateFactory.zoomTo(9);

            //  googleMap.moveCamera(center);
            googleMap.animateCamera(zoom);

        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Toast.makeText(context, "ff", Toast.LENGTH_SHORT).show();
        coordinatesHolder = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(coordinatesHolder));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    class Load_shareimage extends AsyncTask<Integer, Integer, Integer> {
        final CustomProgressDialog dialog = new CustomProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        Bitmap myBitmap;

        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                URL url = null;
                if (!photo_slider.get(0).equalsIgnoreCase("")) {
                    url = new URL(photo_slider.get(0));
                } else {
                    url = new URL("http://www.property_bull.com/images/no-image.jpg");
                }

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                myBitmap = BitmapFactory.decodeStream(input);

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer result) {
// TODO Auto-generated method stub
            super.onPostExecute(result);
            dialog.hide();
            try {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_SUBJECT, discription.getText().toString());
                intent.putExtra(Intent.EXTRA_TITLE, discription.getText().toString());
                intent.putExtra(Intent.EXTRA_TEXT, "http://property_bull.com/propertyshow/" + url + "\n" + discription.getText().toString());
                String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), myBitmap, "", null);
                Uri screenshotUri = Uri.parse(path);
                intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(intent, "Share image via..."));

            } catch (Exception e) {

                Toast.makeText(context, "Unable to share property due to unavailability of image", Toast.LENGTH_SHORT).show();
            }
        }

    }


    void showdialogue(String name, String contact) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.myDialog));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alert_layout_viewcontact, null);
        dialogBuilder.setView(dialogView);


        TextView vcName = (TextView) dialogView.findViewById(R.id.vc_name);
        TextView vcContact = (TextView) dialogView.findViewById(R.id.vc_contact);

        vcName.setText(name);
        vcContact.setText(contact);

        // dialogBuilder.setTitle("Custom dialog");
        //  dialogBuilder.setMessage("Enter text below");


        contact_alert = dialogBuilder.create();

        contact_alert.show();

    }

    void load_similar_property() {
        Sim_pid_list = new ArrayList<>();
        Sim_pname_list = new ArrayList<>();
        Sim_pimage_list = new ArrayList<>();
        Sim_ptype_list = new ArrayList<>();
        Sim_ptotalarea_list = new ArrayList<>();
        Sim_pmobile_list = new ArrayList<>();
        Sim_pbedroom_list = new ArrayList<>();
        Sim_ptotalprice_list = new ArrayList<>();
        Sim_ppriceperunit_list = new ArrayList<>();
        Sim_ppostedby_list = new ArrayList<>();
        Sim_addresslist = new ArrayList<>();
        Sim_localitylist = new ArrayList<>();
        Sim_landmarklist = new ArrayList<>();
        Sim_descriptionlist = new ArrayList<>();
        Sim_depositlist = new ArrayList<>();
        Sim_bathlist = new ArrayList<>();
        Sim_p_floorlist = new ArrayList<>();
        Sim_flooringlist = new ArrayList<>();
        Sim_floorlist = new ArrayList<>();

        serverapi.similar_property(pid_list, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                String s = new String(((TypedByteArray) response.getBody()).getBytes());

                try {
                    JSONObject jsonObject = new JSONObject(s);

                    JSONArray jsonArray = jsonObject.getJSONArray("output1");
                    if (jsonArray.length() != 0) {
                        similartext.setVisibility(View.VISIBLE);
                        ArrayList<String> urllist = new ArrayList<String>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject c = jsonArray.getJSONObject(i);
                            String description = c.getString("description");
                            String deposit = c.getString("deposit");
                            String bath = c.getString("bath");
                            String floor = c.getString("floor");
                            String p_floor = c.getString("p_floor");
                            String flooring = c.getString("flooring");
                            if (bath == null || bath.equalsIgnoreCase("null") || bath.equals("") || bath.equals("0")) {
                                bath = "-";
                            }
                            if (floor == null || floor.equalsIgnoreCase("null") || floor.equals("") || floor.equals("0")) {
                                floor = "-";
                            }
                            if (p_floor == null || p_floor.equalsIgnoreCase("null") || p_floor.equals("") || p_floor.equals("0")) {
                                p_floor = "-";
                            }
                            if (flooring == null || flooring.equalsIgnoreCase("null") || flooring.equals("") || flooring.equals("0")) {
                                flooring = "-";
                            }
                            if (deposit == null || deposit.equalsIgnoreCase("null") || deposit.equals("1") || deposit.equals("0")) {
                                deposit = "-";
                            }
                            if (description == null || description.equalsIgnoreCase("null") || description.equals("")) {
                                description = "-";
                            }
                            //ok
                            String id = c.getString("id");
                            //ok
                            String name = c.getString("name");
                            //ok
                            String mobile = c.getString("mobile");
                            //ok
                            String propertyType = c.getString("Propertytype");
                            //ok
                            String address = c.getString("address").toString();
                            //ok
                            String locality = c.getString("locality").toString();
                            //ok
                            String landmark = c.getString("landmark").toString();
                            //ok
                            String postedby = c.getString("posted_by").toString();
                            //ok
                            String Bedroom = c.getString("room").toString();
                            //ok
                            String totalarea = c.getString("area").toString();

                            String area_unit = c.getString("area_unit").toString();
                            //ok
                            String priceperUnit = c.getString("tot_price").trim();

                            // String description=c.getString("description").trim();

                            //ok
                            // String totalPrice=c.getString("tot_price").trim();

                            String featureimage = c.getString("featureimage");


                            if (url.equalsIgnoreCase("") || url.equalsIgnoreCase("null")) {
                                urllist.add("");
                            } else {
                                urllist.add(url);
                            }
                            if (id == null || id.equalsIgnoreCase("null") || id.equals("")) {
                                id = "-";
                            }
                            if (name == null || name.equalsIgnoreCase("null") || name.equals("")) {
                                name = "-";
                            }
                            if (postedby == null || postedby.equalsIgnoreCase("null") || postedby.equals("")) {
                                postedby = "-";
                            }
                            if (propertyType == null || propertyType.equalsIgnoreCase("null") || propertyType.equals("")) {
                                propertyType = "-";
                            }

                            if (Bedroom == null || Bedroom.equalsIgnoreCase("null") || Bedroom.equals("")) {
                                Bedroom = "-";
                            }
                            if (landmark == null || landmark.equalsIgnoreCase("null") || landmark.equals("")) {
                                landmark = "-";
                            }
                            if (address == null || address.equalsIgnoreCase("null") || address.equals("")) {
                                address = "-";
                            }
                            if (locality == null || locality.equalsIgnoreCase("null") || locality.equals("")) {
                                locality = "-";
                            }

                            if (totalarea == null || totalarea.equalsIgnoreCase("null") || totalarea.equals("")) {
                                totalarea = "";
                            }

                       /* if(totalPrice==null || totalPrice.equalsIgnoreCase("null") || totalPrice.equals(""))
                        {
                            totalPrice="";
                        }*/

                            if (priceperUnit == null || priceperUnit.equalsIgnoreCase("null") || priceperUnit.equals("")) {
                                priceperUnit = "-";
                            }

                            if (description == null || description.equalsIgnoreCase("null") || description.equals("")) {
                                description = "-";
                            }
                            if (area_unit == null || area_unit.equalsIgnoreCase("null") || area_unit.equals("")) {
                                area_unit = "";
                            }


                            Sim_pid_list.add(id);
                            Sim_pname_list.add(name);
                            Sim_ppostedby_list.add(postedby);
                            Sim_pmobile_list.add(mobile);
                            Sim_ptype_list.add(propertyType);
                            Sim_pbedroom_list.add(Bedroom);
                            Sim_ptotalarea_list.add(totalarea);
                            Sim_ptotalprice_list.add(priceperUnit);
                            Sim_ppriceperunit_list.add(area_unit);
                            Sim_pimage_list.add(featureimage);
                            Sim_addresslist.add(address);
                            Sim_localitylist.add(locality);
                            Sim_landmarklist.add(landmark);
                            Sim_descriptionlist.add(description);

                            Sim_depositlist.add(deposit);
                            Sim_flooringlist.add(flooring);
                            Sim_floorlist.add(floor);
                            Sim_bathlist.add(bath);
                            Sim_p_floorlist.add(p_floor);
                        }

                        //Log.e("sim",s.toString());
                        Search_Property_adapter adapter;
                        adapter = new Search_Property_adapter(context, Sim_pid_list, Sim_pname_list, Sim_ppostedby_list,
                                Sim_pmobile_list, Sim_ptype_list, Sim_pbedroom_list, Sim_ptotalarea_list,
                                Sim_ptotalprice_list, Sim_ppriceperunit_list, Sim_pimage_list, Sim_addresslist,
                                Sim_localitylist, Sim_landmarklist, Sim_descriptionlist, Sim_depositlist, Sim_flooringlist, Sim_floorlist, Sim_bathlist, Sim_p_floorlist, urllist, getActivity());
//        filtered_recylcer.setAdapter(mAdapter);
                        similar_recycler.setAdapter(adapter);
                    } else {
                        similartext.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
                    //  Log.e("similar pro :",e.toString());
                    similartext.setVisibility(View.GONE);
                }

                //    Toast.makeText(context, ""+s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(RetrofitError error) {
                similartext.setVisibility(View.GONE);
                //  Toast.makeText(context, ""+error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void check_and_showpermission() {
        int myAPI = Build.VERSION.SDK_INT;      // find android version 19
        if (myAPI >= 23) {
            int result = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);   // check for permision

            if (result == PackageManager.PERMISSION_GRANTED) {
                new Load_shareimage().execute(101);
            } else {

                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,},
                            120);

                    //     Toast.makeText(activity, "Microphone permission needed for recording. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            120);

                }


            }
        } else {
            new Load_shareimage().execute(101);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 120) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0) {

                //       boolean aaeptcon=grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new Load_shareimage().execute(101);
                    Toast.makeText(getActivity(), "Thanks for Allow permission",
                            Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(getActivity(), "permission required for share the project", Toast.LENGTH_LONG).show();


                }
                // permission was granted, yay! Do the
                // contacts-related task you need to do.

            } else {
                Toast.makeText(getActivity(), "permission required for share the project", Toast.LENGTH_LONG).show();


            }


        }
    }


    public void Dialog_contact(final Context context) {
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.dialog_contact_us, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        llMainContactus = (LinearLayout) promptsView.findViewById(R.id.ll_main_contactus);
        llContactus2 = (LinearLayout) promptsView.findViewById(R.id.ll_contactus2);
        edtName = (EditText) promptsView.findViewById(R.id.edt_name);
        ImageView imgClose = (ImageView) promptsView.findViewById(R.id.imgClose);
        edtEmail = (EditText) promptsView.findViewById(R.id.edt_email);
        edtMobile = (EditText) promptsView.findViewById(R.id.edt_Mobile);
        edtAddress = (EditText) promptsView.findViewById(R.id.edt_address);
        checkBox1 = (CheckBox) promptsView.findViewById(R.id.checkBox1);
        checkBox2 = (CheckBox) promptsView.findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox) promptsView.findViewById(R.id.checkBox3);
        edtSecurityCode = (EditText) promptsView.findViewById(R.id.edt_security_code);
        checkBox4 = (CheckBox) promptsView.findViewById(R.id.checkBox4);
        pbSubmitContact = (ProgressBar) promptsView.findViewById(R.id.pb_submit_contact);
        btnSubmitContactus = (Button) promptsView.findViewById(R.id.btn_submit_contactus);

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);

                }

            }
        });


        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBox1.setChecked(false);
                    checkBox3.setChecked(false);
                }

            }
        });
        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBox2.setChecked(false);
                    checkBox1.setChecked(false);
                }

            }
        });


        alertDialogBuilder.setView(promptsView);
        alertDialog = alertDialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.setCancelable(true);
        // show it
        alertDialog.show();
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        btnSubmitContactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_name = edtName.getText().toString();
                String str_email = edtEmail.getText().toString();
                String str_mobile = edtMobile.getText().toString();
                if (TextUtils.isEmpty(str_name)) {
                    Toast.makeText(getActivity(), "Please Enter Name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(str_email)) {
                    Toast.makeText(getActivity(), "Please Select Email", Toast.LENGTH_SHORT).show();
                } else if (!emailValidator(str_email)) {
                    Toast.makeText(getActivity(), "Please Enter Valid Email", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(str_mobile)) {
                    Toast.makeText(getActivity(), "Please Enter Mobile", Toast.LENGTH_SHORT).show();
                } else if (str_mobile.length() < 10) {
                    Toast.makeText(getActivity(), "Please Enter valid Mobile", Toast.LENGTH_SHORT).show();
                } else if (!checkBox4.isChecked()) {
                    Toast.makeText(getActivity(), "Please Accept Terms And Condition", Toast.LENGTH_SHORT).show();
                } else {
                    //
                    contactus(id, str_name, str_mobile, str_email);
                }


            }
        });
    }


    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public void contactus(String id, String name, String mobileno, String email) {
        btnVisiblity(false, pbSubmitContact, btnSubmitContactus);
        AndroidNetworking.upload("https://www.propertybull.com/mobile/propertyContactForm")
                .addMultipartParameter("id", id)
                .addMultipartParameter("name", name)
                .addMultipartParameter("fname", "0")
                .addMultipartParameter("email", email)
                .addMultipartParameter("pro_id", mobileno)
                .addMultipartParameter("phone", mobileno)
                .addMultipartParameter("message", mobileno)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if (response.has("success") && response.getString("success").equals("1")) {
                                Toast.makeText(getActivity(), "Property Inquiry Sent Successfully", Toast.LENGTH_SHORT).show();
                                //    get_profile();
                                alertDialog.dismiss();
                            }

                        } catch (JSONException e) {

                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                        btnVisiblity(true, pbSubmitContact, btnSubmitContactus);
                    }

                    @Override
                    public void onError(ANError error) {
                        if (TextUtils.equals(error.getErrorDetail(), ANConstants.CONNECTION_ERROR)) {

                            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();

                        } else {

                            Toast.makeText(getActivity(), error.getErrorDetail(), Toast.LENGTH_LONG).show();
                        }


                        btnVisiblity(true, pbSubmitContact, btnSubmitContactus);

                    }

                });
    }

    public void btnVisiblity(boolean status, ProgressBar pb_bar, Button btn) {
        if (status) {
            btn.setVisibility(View.VISIBLE);
            pb_bar.setVisibility(View.GONE);
        } else {
            btn.setVisibility(View.INVISIBLE);
            pb_bar.setVisibility(View.VISIBLE);
        }
    }

    public Barcode.GeoPoint getLocationFromAddress(String strAddress) {
        Geocoder coder = new Geocoder(getActivity());
        List<Address> address;
        Barcode.GeoPoint p1 = null;
        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;

            } else {

                Address location = address.get(0);
                location.getLatitude();
                location.getLongitude();

                p1 = new Barcode.GeoPoint((double) (location.getLatitude() * 1E6),
                        (double) (location.getLongitude() * 1E6));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return p1;
    }

}

