package com.doomshell.property_bull;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANConstants;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.doomshell.property_bull.adapter.RecentProjectAdaptor;
import com.doomshell.property_bull.adapter.RecentSliderAdaptor;
import com.doomshell.property_bull.adapter.Recent_ProjectFeature_adapter;
import com.doomshell.property_bull.adapter.SimilarRecentProject_MoreAdaptor;
import com.doomshell.property_bull.helper.CustomProgressDialog;
import com.doomshell.property_bull.helper.Serverapi;
import com.doomshell.property_bull.model.SharedPrefManager;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.validation.Validator;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * Created by Vipin on 6/24/2017.
 */

public class RecentPropertyDetails extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, OnMapReadyCallback, GoogleMap.OnMapClickListener {
    ViewPager slider;
    RecyclerView projecttetails;
    String coordinatesHolder = "";
    RecyclerView.Adapter adapter;
    private MapView mapView;
    private GoogleMap googleMap;
    PopupWindow popupWindow;
    RadioButton radioButton;
    TextView projectname, builder, landmark, area, price, type;
    String latitude, longitude, project_id;
    public static Intent callIntent;
    public static int CallState = 111;
    AlertDialog contact_alert;

    AppCompatActivity appCompatActivity;
    ActionBar actionBar;
    String Baseurl;
    RestAdapter restAdapter = null;
    Serverapi serverapi;
    Dialog contactdialog;
    ProgressBar bar;
    ArrayList<String> floor_slider, photo_slider;
    RadioGroup photorg;
    RadioButton r_photo;
    ArrayList<String> typelist;


    ArrayList<String> arealist;
    ArrayList<String> floorplan;
    String areaunit;
    String pmobile_list;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.

    RecyclerView similar_projects_recycler;
    //Popup iteems
    Button btnSubmit, btn_contact_now;
    EditText detailName, detailMob, detailEmail;
    RadioGroup radioGroup;
    CheckBox checkboxterm;
    TextView builderName, propertyName, detailtermsandcond;
    String buildername, propName;
    String ispropertyDealer;
    ImageView closePopButton;

    FloatingActionButton share_recent;
    ImageButton leftNav, rightNav;
    TextView floor, bathroom, room;
    LinearLayout floorlyt, roomlyt, bathroomlyt;
    String floordata = "", bathroomdata = "", roomdata = "";
    CustomProgressDialog dialog;

    CardView featurelyt;
    RecyclerView featurerecycler;
    String url = "";
    CoordinatorLayout recentprojectdetaillayout;
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
    private String id = "";
    private String message = "";

    private FrameLayout mframe;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle bundle) {
        final View view = inflater.inflate(R.layout.recentprojectdetails, group, false);
        appCompatActivity = (AppCompatActivity) getActivity();

        id = SharedPrefManager.getInstance(getActivity()).getuser_details("id");

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        TextView screentitle = (TextView) toolbar.findViewById(R.id.screentitle);
        ImageView imageView = (ImageView) toolbar.findViewById(R.id.screenimageview);
        ImageView share = (ImageView) toolbar.findViewById(R.id.share_recent);
        btnLocation = (Button) view.findViewById(R.id.btnLocation);
        mframe = (FrameLayout) view.findViewById(R.id.mapframe);
        share.setVisibility(View.VISIBLE);
        BottomNavigationView bottomnavView = getActivity().findViewById(R.id.bottom_nav_view);
        bottomnavView.setVisibility(View.GONE);
        screentitle.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
        screentitle.setText("Recent Project Details");
        initialize_dialog_connection();
        /* slider=(SliderLayout)view.findViewById(R.id.slider);*/
        bundle = getArguments();
        slider = (ViewPager) view.findViewById(R.id.slider);
        floor = (TextView) view.findViewById(R.id.floor);
        floorlyt = (LinearLayout) view.findViewById(R.id.floorlyt);
        room = (TextView) view.findViewById(R.id.room);
        roomlyt = (LinearLayout) view.findViewById(R.id.roomlyt);
        bathroom = (TextView) view.findViewById(R.id.bathroom);
        bathroomlyt = (LinearLayout) view.findViewById(R.id.bathroomlyt);
        recentprojectdetaillayout = view.findViewById(R.id.recentprojectdetaillayout);

        projectname = (TextView) view.findViewById(R.id.projectname);
        builder = (TextView) view.findViewById(R.id.builder);
        landmark = (TextView) view.findViewById(R.id.landmark);
        area = (TextView) view.findViewById(R.id.area);
        price = (TextView) view.findViewById(R.id.price);
        type = (TextView) view.findViewById(R.id.type);
        r_photo = (RadioButton) view.findViewById(R.id.r_photo);
        share_recent = (FloatingActionButton) view.findViewById(R.id.share_recent);

        ImageView detail_callbtn = (ImageView) view.findViewById(R.id.detail_callbtn);
        leftNav = (ImageButton) view.findViewById(R.id.left_nav);
        rightNav = (ImageButton) view.findViewById(R.id.right_nav);
        btn_contact_now = (Button) view.findViewById(R.id.btn_contact_now);

        similar_projects_recycler = (RecyclerView) view.findViewById(R.id.similar_projects_recycler);
        similar_projects_recycler.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        pmobile_list = bundle.getString("pmobile_list");

        r_photo.setChecked(true);

        detail_callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_fillDetailPopUp();
            }
        });
        btn_contact_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog_contact(getActivity());
            }
        });
        Button detail_viewcontact_btn = (Button) view.findViewById(R.id.detail_viewcontact_btn);
        final Bundle finalBundle = bundle;
        detail_viewcontact_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showdialogue(finalBundle.getString("ppostedby_list").toString(), pmobile_list);

            }
        });
        Button detail_sendmsg_btn = (Button) view.findViewById(R.id.detail_sendmsg_btn);
        detail_sendmsg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Intent i=new Intent(Intent.ACTION_SENDTO);
                String data="smsto:"+pmobile_list;

                i.setData(Uri.parse(data));
                i.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                startActivity(i);*/

                String number = pmobile_list;
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null));// The number on which you want to send SMS
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addCategory(Intent.CATEGORY_DEFAULT);
                startActivity(i);

            }
        });
        floordata = bundle.getString("floor");
        if (floordata.equalsIgnoreCase("")) {
            floorlyt.setVisibility(View.GONE);
        } else {
            floor.setText(floordata);
        }
        bathroomdata = bundle.getString("bathroom");
        if (bathroomdata.equalsIgnoreCase("")) {
            bathroomlyt.setVisibility(View.GONE);
        } else {
            bathroom.setText(bathroomdata);
        }
        roomdata = bundle.getString("pbedroom_list");
        if (roomdata.equalsIgnoreCase("")) {
            roomlyt.setVisibility(View.GONE);
        } else {
            room.setText(roomdata);
        }
        project_id = bundle.getString("pid_list");
        areaunit = bundle.getString("ppriceperunit_list").toString();
        projectname.setText(bundle.getString("pname_list").toString() + " (" + bundle.getString("pid_list") + ")");
        propName = bundle.getString("pname_list").toString() + " (" + bundle.getString("pid_list") + ")";
        builder.setText(bundle.getString("ppostedby_list").toString());
        buildername = (bundle.getString("ppostedby_list").toString());
        area.setText(bundle.getString("ptotalarea_list").toString() + " " + bundle.getString("ppriceperunit_list").toString());
        url = bundle.getString("url").toString();
        if (bundle.getString("ptotalprice_list").toString().equalsIgnoreCase("0"))
            price.setText("On Request");
        else {
            price.setText(bundle.getString("ptotalprice_list").toString());
        }
        type.setText(bundle.getString("type").toString());
        landmark.setText(bundle.getString("landmark").toString());
        latitude = bundle.getString("latitude").toString();
        longitude = bundle.getString("longitude").toString();

        loaddata();


        photorg = (RadioGroup) view.findViewById(R.id.photorg);


        photorg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                RadioButton radioButton = (RadioButton) view.findViewById(i);
                if (radioButton.getText().toString().equalsIgnoreCase("Photos")) {


                    if (photo_slider.size() == 0) {
                        leftNav.setVisibility(View.GONE);
                        rightNav.setVisibility(View.GONE);
                        RecentSliderAdaptor myCustomPagerAdapter = new RecentSliderAdaptor(getActivity(), photo_slider, getActivity());
                        slider.setAdapter(myCustomPagerAdapter);
                    } else if (photo_slider.size() == 1) {
                        leftNav.setVisibility(View.GONE);
                        rightNav.setVisibility(View.GONE);
                        RecentSliderAdaptor myCustomPagerAdapter = new RecentSliderAdaptor(getActivity(), photo_slider, getActivity());
                        slider.setAdapter(myCustomPagerAdapter);
                    } else if (photo_slider.size() > 1) {
                        leftNav.setVisibility(View.VISIBLE);
                        rightNav.setVisibility(View.VISIBLE);
                        RecentSliderAdaptor myCustomPagerAdapter = new RecentSliderAdaptor(getActivity(), photo_slider, getActivity());
                        slider.setAdapter(myCustomPagerAdapter);
                    }
                    //This will scroll page-by-page so that you can view scroll happening

                    //loadslider(photo_slider);
                } else {
                    if (floor_slider.size() == 0) {
                        leftNav.setVisibility(View.GONE);
                        rightNav.setVisibility(View.GONE);
                        RecentSliderAdaptor myCustomPagerAdapter = new RecentSliderAdaptor(getActivity(), floor_slider, getActivity());
                        slider.setAdapter(myCustomPagerAdapter);
                    } else if (floor_slider.size() == 1) {
                        leftNav.setVisibility(View.GONE);
                        rightNav.setVisibility(View.GONE);
                        RecentSliderAdaptor myCustomPagerAdapter = new RecentSliderAdaptor(getActivity(), floor_slider, getActivity());
                        slider.setAdapter(myCustomPagerAdapter);
                    } else if (floor_slider.size() > 1) {
                        leftNav.setVisibility(View.VISIBLE);
                        rightNav.setVisibility(View.VISIBLE);
                        RecentSliderAdaptor myCustomPagerAdapter = new RecentSliderAdaptor(getActivity(), floor_slider, getActivity());
                        slider.setAdapter(myCustomPagerAdapter);
                    }

                    //loadslider(floor_slider);
                }


            }
        });


// Images left navigation
        leftNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = slider.getCurrentItem();
                if (tab > 0) {
                    tab--;
                    slider.setCurrentItem(tab);
                } else if (tab == 0) {
                    slider.setCurrentItem(tab);
                }
            }
        });

        // Images right navigatin
        rightNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = slider.getCurrentItem();
                tab++;
                slider.setCurrentItem(tab);
            }
        });



        /*++++++++++++++++++++Map Work++++++++++++++++++++++++++++*/
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(bundle);
        mapView.onResume();
        mapView.getMapAsync(this);
       /* mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                if (googleMap != null) {
                    LatLng latLng = new LatLng(26.912434, 75.787271);
                    googleMap.addMarker(new MarkerOptions().position(latLng));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                }
            }
        });*/
        /*++++++++++++++++++++Map Work++++++++++++++++++++++++++++*/
        projecttetails = (RecyclerView) view.findViewById(R.id.projecttetails);
        projecttetails.setLayoutManager(new LinearLayoutManager(getActivity()));


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_and_showpermission();
            }
        });
        mapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coordinatesHolder = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(coordinatesHolder));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Toast.makeText(getActivity(), "dd", Toast.LENGTH_SHORT).show();

            }
        });

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "ff", Toast.LENGTH_SHORT).show();
                String geoUri = "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude + " (" + "Proerty" + ")";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


            }
        });


        //=====================Feature list code======================================//

        featurelyt = (CardView) view.findViewById(R.id.featurelyt);
        featurerecycler = (RecyclerView) view.findViewById(R.id.featurerecycler);

        featurerecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        loadfeature();
        return view;
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

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

    public void initialize_dialog_connection() {
        appCompatActivity = (AppCompatActivity) getActivity();

        Baseurl = getActivity().getResources().getString(R.string.myurl);
        /*OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(5, TimeUnit.MINUTES.MINUTES);
        okHttpClient.setReadTimeout(5, TimeUnit.MINUTES.MINUTES);*/
        //  myCustomProgress_dialogue=new MyCustomProgress_dialogue(getActivity(),R.color.colorPrimary);

        restAdapter = new RestAdapter.Builder().setEndpoint(Baseurl).build();
        serverapi = restAdapter.create(Serverapi.class);

        contactdialog = new Dialog(getActivity());
        contactdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        contactdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00ff1919")));
        bar = new ProgressBar(getActivity(), null, android.R.attr.progressBarStyleLarge);
//bar.setProgress()
        bar.getIndeterminateDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY);
        contactdialog.setContentView(bar);
        contactdialog.setCancelable(false);
    }

    public void loaddata() {
        show_dialogue();
        //   Toast.makeText(getActivity(), ""+project_id, Toast.LENGTH_SHORT).show();
        serverapi.single_recentproject(project_id, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                String s = new String(((TypedByteArray) response.getBody()).getBytes());
                try {
                    dismiss_dialogue();
                    JSONObject jsonObject = new JSONObject(s);
                    int success = 1;
                    ArrayList<String> SSpid_list = new ArrayList<>();
                    ArrayList<String> SSpname_list = new ArrayList<>();
                    ArrayList<String> SSpimage_list = new ArrayList<>();
                    ArrayList<String> SSptype_list = new ArrayList<>();
                    ArrayList<String> SSptotalarea_list = new ArrayList<>();
                    ArrayList<String> SSpmobile_list = new ArrayList<>();
                    ArrayList<String> SSpbedroom_list = new ArrayList<>();
                    ArrayList<String> SSptotalprice_list = new ArrayList<>();
                    ArrayList<String> SSppriceperunit_list = new ArrayList<>();
                    ArrayList<String> SSlatitude_list = new ArrayList<>();
                    ArrayList<String> SSlongitude_list = new ArrayList<>();
                    ArrayList<String> SSlocality_list = new ArrayList<>();
                    ArrayList<String> SSlandmark_list = new ArrayList<>();
                    ArrayList<String> SSppostedby_list = new ArrayList<>();
                    ArrayList<String> SStype_list = new ArrayList<>();
                    ArrayList<String> floor_list = new ArrayList<>();
                    ArrayList<String> bathroomlist = new ArrayList<>();
                    ArrayList<String> urllist = new ArrayList<>();
                    floor_slider = new ArrayList<String>();
                    photo_slider = new ArrayList<String>();
                    floorplan = new ArrayList<String>();
                    arealist = new ArrayList<String>();
                    typelist = new ArrayList<String>();
                    if (success == 1) {
                        JSONArray sliderarray = jsonObject.getJSONArray("output1");
                        for (int i = 0; i < sliderarray.length(); i++) {
                            JSONObject jsonObject1 = sliderarray.getJSONObject(i);
                            floor_slider.add(jsonObject1.getString("floor_image").toString());

                        }
                        JSONArray sliderarray1 = jsonObject.getJSONArray("output2");
                        for (int i = 0; i < sliderarray1.length(); i++) {
                            JSONObject jsonObject1 = sliderarray1.getJSONObject(i);
                            photo_slider.add(jsonObject1.getString("photo_image").toString());

                        }
                        JSONArray sliderarray2 = jsonObject.getJSONArray("output3");
                        for (int i = 0; i < sliderarray2.length(); i++) {
                            JSONObject jsonObject1 = sliderarray2.getJSONObject(i);
                            if (!(jsonObject1.getString("room").toString().equalsIgnoreCase("null") || jsonObject1.getString("room").toString().equalsIgnoreCase(""))) {
                                typelist.add(jsonObject1.getString("room").toString().trim());
                                arealist.add(jsonObject1.getString("area").toString().trim());
                                floorplan.add(jsonObject1.getString("floor_image").toString().trim());
                                projecttetails.setAdapter(new RecentProjectAdaptor(
                                        getActivity(), typelist, arealist, areaunit, floorplan, (AppCompatActivity) getActivity()));

                            }

                        }
                        if (photo_slider.size() == 0) {
                            leftNav.setVisibility(View.GONE);
                            rightNav.setVisibility(View.GONE);
                            RecentSliderAdaptor myCustomPagerAdapter = new RecentSliderAdaptor(getActivity(), photo_slider, getActivity());
                            slider.setAdapter(myCustomPagerAdapter);
                        } else if (photo_slider.size() == 1) {
                            leftNav.setVisibility(View.GONE);
                            rightNav.setVisibility(View.GONE);
                            RecentSliderAdaptor myCustomPagerAdapter = new RecentSliderAdaptor(getActivity(), photo_slider, getActivity());
                            slider.setAdapter(myCustomPagerAdapter);
                        } else if (photo_slider.size() > 1) {
                            RecentSliderAdaptor myCustomPagerAdapter = new RecentSliderAdaptor(getActivity(), photo_slider, getActivity());
                            slider.setAdapter(myCustomPagerAdapter);
                        }


                        //similar starts here
                        JSONArray similarArray = jsonObject.getJSONArray("output4");
                        for (int i = 0; i < similarArray.length(); i++) {
                            JSONObject c = similarArray.getJSONObject(i);
                            String id = c.getString("id").trim();
                            String mobile = c.getString("mobile").trim();
                            String name = c.getString("name").trim();
                            String type = c.getString("Propertytype").trim();
                            String locality = c.getString("locality").trim();
                            String landmark = c.getString("landmark").trim();
                            String state = c.getString("state").trim();
                            String city = c.getString("city").trim();
                            String posted_by = c.getString("posted_by").trim();
                            String room = c.getString("rooms").trim();
                            String area = c.getString("area").trim();
                            String area_unit = c.getString("area_unit").trim();
                            String tot_price = c.getString("tot_price").trim();
                            String featureimage = c.getString("featureimage");
                            String latitude = c.getString("latitude").trim();
                            String longitude = c.getString("longitude").trim();
                            String floor = c.getString("floor").trim();
                            String bathroom = c.getString("bathroom").trim();
                            String url = c.getString("url").trim();
                            if (url.equalsIgnoreCase("") || url.equalsIgnoreCase("null")) {
                                urllist.add("");
                            } else {
                                urllist.add(url);
                            }
                            if (bathroom.equalsIgnoreCase("")) {
                                bathroomlist.add("");
                            } else {
                                bathroomlist.add(bathroom);
                            }


                            if (type == null || type.equalsIgnoreCase("null") || type.equals("")) {
                                type = "-";
                            }
                            if (posted_by == null || posted_by.equalsIgnoreCase("null") || posted_by.equals("")) {
                                posted_by = "-";
                            }
                            if (locality == null || locality.equalsIgnoreCase("null") || locality.equals("")) {
                                locality = "-";
                            }
                            if (landmark == null || landmark.equalsIgnoreCase("null") || landmark.equals("")) {
                                landmark = "-";
                            }
                            if (mobile == null || mobile.equalsIgnoreCase("null") || mobile.equals("")) {
                                mobile = "-";
                            }
                            if (name == null || name.equalsIgnoreCase("null") || name.equals("")) {
                                name = "-";
                            }
                            if (state == null || state.equalsIgnoreCase("null") || state.equals("")) {
                                state = "-";
                            }
                            if (city == null || city.equalsIgnoreCase("null") || city.equals("")) {
                                city = "-";
                            }

                            if (area == null || area.equalsIgnoreCase("null") || area.equals("")) {
                                area = "-";
                            }

                            if (area_unit == null || area_unit.equalsIgnoreCase("null") || area_unit.equals("")) {
                                area_unit = "-";
                            }


                            if (tot_price == null || tot_price.equalsIgnoreCase("null") || tot_price.equals("")) {
                                tot_price = "-";
                            }


                            SStype_list.add(type);
                            SSlandmark_list.add(landmark);
                            SSlocality_list.add(locality);
                            SSlatitude_list.add(latitude);
                            SSlongitude_list.add(longitude);
                            SSpid_list.add(id);
                            SSpname_list.add(name);
                            SSppostedby_list.add(posted_by);
                            SSpmobile_list.add(mobile);
                            SSptype_list.add("" + state + " " + city);
                            SSpbedroom_list.add(room);
                            SSptotalarea_list.add(area);
                            SSptotalprice_list.add(tot_price);
                            SSppriceperunit_list.add(area_unit);
                            SSpimage_list.add(featureimage);
                            floor_list.add(floor);

                        }

                        SimilarRecentProject_MoreAdaptor mAdapter = new SimilarRecentProject_MoreAdaptor(getActivity().getApplicationContext(),
                                SSpid_list, SSpname_list, SSppostedby_list, SSpmobile_list, SSptype_list, SSpbedroom_list,
                                SSptotalarea_list, SSptotalprice_list, SSppriceperunit_list, SSpimage_list, SSlatitude_list, SSlongitude_list, SSlocality_list,
                                SSlandmark_list, SStype_list, floor_list, bathroomlist, urllist, getActivity());
                        /* mAdapter.setHasStableIds(true);*/
                   /*     mItems = getData(0);
                        mAdapter.setItems(mItems);*/
                        similar_projects_recycler.setAdapter(mAdapter);


                    }
                } catch (Exception e) {
//                    Toast.makeText(getActivity(),"Something went wrong",Toast.LENGTH_SHORT).show();
                    Log.e("sim error", e.toString());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                dismiss_dialogue();
            }
        });
    }

    public void show_dialogue() {

        dialog = new CustomProgressDialog(getActivity());
    }

    public void dismiss_dialogue() {
        dialog.hide();
    }

    private void initView() {

    }

    @Override
    public void onMapClick(LatLng latLng) {
        coordinatesHolder = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(coordinatesHolder));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        Toast.makeText(getActivity(), "dd", Toast.LENGTH_SHORT).show();

    }
  /*  public void loadslider(ArrayList<String> list)
    {



        for(String name : list){
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description("")
                    .image(name)
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            slider.addSlider(textSliderView);
        }
        slider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        slider.setCustomAnimation(new DescriptionAnimation());
        slider.setDuration(4000);
        slider.addOnPageChangeListener(this);
    }*/
  /*public void autoslide(final int NUM_PAGES)
  {
      //This will scroll page-by-page so that you can view scroll happening
      /*//*After setting the adapter use the timer *//**//*
      final Handler handler = new Handler();
      final Runnable Update = new Runnable() {
          public void run() {
              if (currentPage == NUM_PAGES-1) {
                  slider.setCurrentItem(0, true);
              }
              else {
                  slider.setCurrentItem(currentPage++, true);
              }
          }
      };

      timer = new Timer(); // This will create a new Thread
      timer .schedule(new TimerTask() { // task to be scheduled

          @Override
          public void run() {
              handler.post(Update);
          }
      }, 500, 3000);

  }*/

    class Load_shareimage_recent extends AsyncTask<Integer, Integer, Integer> {
        final CustomProgressDialog pd = new CustomProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {
            pd.show();


            super.onPreExecute();
        }

        Bitmap myBitmap;

        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                URL url = null;
                if (!photo_slider.get(0).equalsIgnoreCase("")) {
                    url = new URL(photo_slider.get(0));
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    myBitmap = BitmapFactory.decodeStream(input);
                } else {
                    url = new URL("http://www.property_bull.com/images/no-image.jpg");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    myBitmap = BitmapFactory.decodeStream(input);
                }


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
            pd.hide();
            try {


                String title = "" + projectname.getText().toString() + "\u25CF" + "" + type.getText().toString() + "\u25CF" + "" + landmark.getText().toString() + "\n" + "Google Play Store Link" + "https://play.google.com/store/apps/details?id=com.doomshell.property_bull";
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_SUBJECT, title);
                intent.putExtra(Intent.EXTRA_TITLE, title);
                intent.putExtra(Intent.EXTRA_TEXT, "http://property_bull.com/projectshow/" + url + "\n" + title);
                String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), myBitmap, "", null);

                Uri screenshotUri = Uri.parse(path);
                intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(intent, "Share image via..."));
            } catch (Exception e) {
                // Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_SHORT).show();
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

    public void loadfeature() {
        serverapi.recentprojectfeature(project_id, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                String s = new String(((TypedByteArray) response.getBody()).getBytes());
                try {
                    dismiss_dialogue();
                    JSONObject jsonObject = new JSONObject(s);
                    int success = jsonObject.getInt("success");
                    ArrayList<String> checklist = new ArrayList<String>();
                    if (success == 1) {
                        featurelyt.setVisibility(View.VISIBLE);
                        JSONArray array = jsonObject.getJSONArray("output");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            checklist.add(object.getString("checklist"));

                        }
                        featurerecycler.setAdapter(new Recent_ProjectFeature_adapter(getActivity(), checklist));
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    public void check_and_showpermission() {
        int myAPI = Build.VERSION.SDK_INT;      // find android version 19
        if (myAPI >= 23) {
            int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);   // check for permision

            if (result == PackageManager.PERMISSION_GRANTED) {
                new Load_shareimage_recent().execute(101);
            } else {

                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,},
                            120);

                    //     Toast.makeText(activity, "Microphone permission needed for recording. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            120);

                }


            }
        } else {
            new Load_shareimage_recent().execute(101);
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
                    new Load_shareimage_recent().execute(101);
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

    public void open_fillDetailPopUp() {

        //instantiate the popup.xml layout file
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.fill_detail_fragment, null);

        propertyName = (TextView) customView.findViewById(R.id.propertyName);
        closePopButton = (ImageView) customView.findViewById(R.id.closepopup);
        builderName = (TextView) customView.findViewById(R.id.builderName);
        detailtermsandcond = (TextView) customView.findViewById(R.id.detailtermsandcond);
        btnSubmit = (Button) customView.findViewById(R.id.btnSubmit);
        detailName = (EditText) customView.findViewById(R.id.detailName);
        detailMob = (EditText) customView.findViewById(R.id.detailMob);
        detailEmail = (EditText) customView.findViewById(R.id.detailEmail);
        checkboxterm = customView.findViewById(R.id.checkboxterm);
        radioGroup = customView.findViewById(R.id.radioGroup);
        propertyName.setText(propName);
        builderName.setText(buildername);

        detailName.requestFocus();
        detailMob.requestFocus();
        detailEmail.requestFocus();


        detailtermsandcond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.property_bull.com/static/privacy"));
                startActivity(browserIntent);
            }
        });
        // get selected radio button from radioGroup
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = customView.findViewById(selectedId);

        if (radioButton.getText().equals("Yes")) {
            ispropertyDealer = "1";
        } else if (radioButton.getText().equals("No")) {
            ispropertyDealer = "0";
        }

        closePopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detailName.getText().toString().equals("")) {
                    detailName.setError("Please Enter  Name");

                } else if (detailMob.getText().toString().equals("")) {
                    detailMob.setError("Please Enter Valid Mobile No");
                } else if (detailEmail.getText().toString().equals("")) {
                    detailEmail.setError("Please Enter Valid Email");
                } else if (checkboxterm.isChecked() == false) {
                    checkboxterm.setError("Please Accept PropertyBull Terms and Condition");
                } else {

                    set_details(project_id, ispropertyDealer, detailName.getText().toString(), detailMob.getText().toString()
                            , detailEmail.getText().toString());
                }
            }
        });

        //instantiate popup window
        popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.lightWhite));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setAnimationStyle(R.anim.rotate);
        popupWindow.update();
        //display the popup window
        popupWindow.showAtLocation(recentprojectdetaillayout, Gravity.CENTER, 0, 0);


    }

    public void call_Intent() {
        if (pmobile_list == null || pmobile_list.equals("")) {
            Toast.makeText(getActivity(), "Number not provided", Toast.LENGTH_SHORT).show();
        } else {
            callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + pmobile_list));

            int myAPI = Build.VERSION.SDK_INT;

            if (myAPI >= 23) {
                int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE);
                if (result == PackageManager.PERMISSION_GRANTED) {
                    callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    callIntent.addCategory(Intent.CATEGORY_DEFAULT);
                    startActivity(callIntent);
                } else {
                    //      Toast.makeText(context, "permission required to make call, Please give permission", Toast.LENGTH_LONG).show();

                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CALL_PHONE)) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},
                                CallState);
                    } else {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},
                                CallState);
                    }
                }

            } else {
                callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                callIntent.addCategory(Intent.CATEGORY_DEFAULT);
                startActivity(callIntent);
            }
        }
    }

    public void set_details(String id, String isdealer, String name, String mobile, String cemail) {
        Toast.makeText(appCompatActivity, id + " " + isdealer + " " + name + " " + mobile + " " + cemail, Toast.LENGTH_SHORT).show();
        show_dialogue();
        serverapi.set_detail(id, isdealer, name, mobile, cemail, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                String s = new String(((TypedByteArray) response.getBody()).getBytes());
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int success = jsonObject.getInt("success");
                    if (success == 1) {
//                        String msg=jsonObject.getString("msg");
//                        Toast.makeText(appCompatActivity, msg, Toast.LENGTH_SHORT).show();
                        dismiss_dialogue();
                        popupWindow.dismiss();
                        call_Intent();
                    }
                } catch (JSONException e) {
                    dismiss_dialogue();
                    popupWindow.dismiss();
                    Toast.makeText(appCompatActivity, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                dismiss_dialogue();

            }
        });
    }


    public void Dialog_contact(final Context context) {
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.dialog_contact_us, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        llMainContactus = (LinearLayout) promptsView.findViewById(R.id.ll_main_contactus);
        llContactus2 = (LinearLayout) promptsView.findViewById(R.id.ll_contactus2);
        edtName = (EditText) promptsView.findViewById(R.id.edt_name);
        edtEmail = (EditText) promptsView.findViewById(R.id.edt_email);
        edtMobile = (EditText) promptsView.findViewById(R.id.edt_Mobile);
        edtAddress = (EditText) promptsView.findViewById(R.id.edt_address);
        checkBox1 = (CheckBox) promptsView.findViewById(R.id.checkBox1);
        ImageView imgClose = (ImageView) promptsView.findViewById(R.id.imgClose);
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
        final AlertDialog alertDialog = alertDialogBuilder.create();
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
                .addMultipartParameter("cus_id", id)
                .addMultipartParameter("pro_id", project_id)
                .addMultipartParameter("fname", name)
                .addMultipartParameter("email", email)
                .addMultipartParameter("phone", mobileno)
                .addMultipartParameter("message", "1")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if (response.has("success") && response.getString("success").equals("1")) {
                                Toast.makeText(getActivity(), "Property Inquiry Sent Successfully", Toast.LENGTH_SHORT).show();
                                //    get_profile();
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
}
