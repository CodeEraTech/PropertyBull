package com.doomshell.property_bull;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doomshell.property_bull.adapter.HomeAdapter;
import com.doomshell.property_bull.adapter.HomeRecentProject_MoreAdaptor;
import com.doomshell.property_bull.helper.CustomProgressDialog;
import com.doomshell.property_bull.helper.MyCustomProgress_dialogue;
import com.doomshell.property_bull.helper.Serverapi;
import com.doomshell.property_bull.model.ItemHome;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class MainHome_Frag extends Fragment implements View.OnClickListener {
    Context context;
    View convertView;
    LinearLayout button1, button2, button3, button4, button5, button6;
    String Baseurl;
    RestAdapter restAdapter = null;
    MyCustomProgress_dialogue myCustomProgress_dialogue = null;
    ArrayList<String> city_list = new ArrayList<>();
    ArrayList<String> city_id = new ArrayList<>();
    OkHttpClient okHttpClient;
    AppCompatActivity appCompatActivity;
    ActionBar actionBar;
    Toolbar toolbar;
    public static boolean isRecentCall = false;
    public static boolean isRecentCalldetais = false;
    public static boolean isRecentCallSearch = false;
    public static boolean isRecentCallSearch1 = false;
    public static boolean homes = false;

    private HomeAdapter homeAdapter;
    //recent_projects
    HomeRecentProject_MoreAdaptor mAdapter;
    Serverapi serverapi;
    RecyclerView homerecent_property_recycler;
    RecyclerView.Adapter adapter;
    private RecyclerView rVhome;
    ArrayList<String> bathroomlist = new ArrayList<>();
    ArrayList<String> pid_list = new ArrayList<>();
    ArrayList<String> pname_list = new ArrayList<>();
    ArrayList<String> pimage_list = new ArrayList<>();
    ArrayList<String> ptype_list = new ArrayList<>();
    ArrayList<String> ptotalarea_list = new ArrayList<>();
    ArrayList<String> pmobile_list = new ArrayList<>();
    ArrayList<String> pbedroom_list = new ArrayList<>();
    ArrayList<String> ptotalprice_list = new ArrayList<>();
    ArrayList<String> ppriceperunit_list = new ArrayList<>();
    ArrayList<String> latitude_list = new ArrayList<>();
    ArrayList<String> longitude_list = new ArrayList<>();
    ArrayList<String> locality_list = new ArrayList<>();
    ArrayList<String> landmark_list = new ArrayList<>();
    ArrayList<String> ppostedby_list = new ArrayList<>();
    ArrayList<String> type_list = new ArrayList<>();
    ArrayList<String> floor_list = new ArrayList<>();
    CustomProgressDialog dialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity().getApplicationContext();
        convertView = inflater.inflate(R.layout.fragment_main_home_, container, false);

        //bottom Navigation
        appCompatActivity = (AppCompatActivity) getActivity();


        // actionBar=getActivity().getActionBar();
        appCompatActivity = (AppCompatActivity) getActivity();
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        TextView screentitle = (TextView) toolbar.findViewById(R.id.screentitle);
        ImageView imageView = (ImageView) toolbar.findViewById(R.id.screenimageview);
        screentitle.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
        //getting bottom navigation view and attaching the listener

        Baseurl = getActivity().getResources().getString(R.string.myurl);
        okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(5, TimeUnit.MINUTES.MINUTES);
        okHttpClient.setReadTimeout(5, TimeUnit.MINUTES.MINUTES);

        restAdapter = new RestAdapter.Builder().setEndpoint(Baseurl).setClient(new OkClient(okHttpClient)).build();


        button1 = (LinearLayout) convertView.findViewById(R.id.button1);
        rVhome = (RecyclerView) convertView.findViewById(R.id.rVhome);
        homeAdapter = new HomeAdapter(getActivity(), getHome());
        rVhome.setNestedScrollingEnabled(false);
        rVhome.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        rVhome.setLayoutManager(layoutManager);
        rVhome.setLayoutManager(layoutManager);
        rVhome.setAdapter(homeAdapter);

        button2 = (LinearLayout) convertView.findViewById(R.id.button2);
        button3 = (LinearLayout) convertView.findViewById(R.id.button3);
        button4 = (LinearLayout) convertView.findViewById(R.id.button4);
        button5 = (LinearLayout) convertView.findViewById(R.id.button5);
        button6 = (LinearLayout) convertView.findViewById(R.id.button6);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);


        //  Toast.makeText(context, ""+ SharedPrefManager.getInstance(context).getuser_details("city_id"), Toast.LENGTH_SHORT).show();

        //recent projects

        Baseurl = getActivity().getResources().getString(R.string.myurl);
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(5, TimeUnit.MINUTES.MINUTES);
        okHttpClient.setReadTimeout(5, TimeUnit.MINUTES.MINUTES);
        //  myCustomProgress_dialogue=new MyCustomProgress_dialogue(getActivity(),R.color.colorPrimary);

        restAdapter = new RestAdapter.Builder().setEndpoint(Baseurl).setClient(new OkClient(okHttpClient)).build();
        serverapi = restAdapter.create(Serverapi.class);

        load_recentprojects();
        homerecent_property_recycler = (RecyclerView) convertView.findViewById(R.id.home_recent_property_recycler);
//        homerecent_property_recycler.setLayoutManager(new LinearLayoutManager(context));
        homerecent_property_recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        homerecent_property_recycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRecentCall = true;
                RecentPropertyDetails details = new RecentPropertyDetails();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, details);
                transaction.addToBackStack(details.getClass().toString());
                transaction.commit();
            }
        });

        return convertView;
    }

    @Override
    public void onClick(View v) {

        if (v == button1) {
            Search_Property_Frag search_property_frag = new Search_Property_Frag();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, search_property_frag);
            fragmentTransaction.addToBackStack(search_property_frag.getClass().toString());
            fragmentTransaction.commit();
        }

        if (v == button2) {
            AddProperty_screen1 addProperty_screen1 = new AddProperty_screen1();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, addProperty_screen1);
            fragmentTransaction.addToBackStack(addProperty_screen1.getClass().toString());
            fragmentTransaction.commit();
        }

        if (v == button3) {
            Myproperty viewProperties = new Myproperty();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, viewProperties);
            fragmentTransaction.addToBackStack(viewProperties.getClass().toString());
            fragmentTransaction.commit();

        }

        if (v == button4) {
            HomeLoan_Fag homeLoanNew = new HomeLoan_Fag();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, homeLoanNew);
            fragmentTransaction.addToBackStack(homeLoanNew.getClass().toString());
            fragmentTransaction.commit();
            /*Service_provider service_provider=new Service_provider();
            FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container,service_provider);
            fragmentTransaction.addToBackStack(service_provider.getClass().toString());
            fragmentTransaction.commit();*/
        }

        if (v == button5) {
            Recent_Projectlist recent_property = new Recent_Projectlist();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, recent_property);
            fragmentTransaction.addToBackStack(recent_property.getClass().toString());
            fragmentTransaction.commit();
        }

        if (v == button6) {
            Show_Requirement add_requirement = new Show_Requirement();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, add_requirement);
            fragmentTransaction.addToBackStack(add_requirement.getClass().toString());
            fragmentTransaction.commit();

        }


    }

    void load_recentprojects() {
        show_dialogue();
        serverapi.gallerys(new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                String s = new String(((TypedByteArray) response.getBody()).getBytes());
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int success = jsonObject.getInt("success");
                    if (success == 1) {
                        ArrayList<String> urllist = new ArrayList<String>();
                        JSONArray products = jsonObject.getJSONArray("output");
                        for (int i = 0; i < products.length(); i++) {
                            JSONObject c = products.getJSONObject(i);
                            String id = c.getString("id").trim();
                            String mobile = c.getString("mobile").trim();
                            String name = c.getString("name").trim();
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
                            String locality = c.getString("locality").trim();
                            String landmark = c.getString("landmark").trim();
                            String type = c.getString("type").trim();
                            String floor = c.getString("floor").trim();

                            String url = c.getString("url").trim();
                            if (url.equalsIgnoreCase("") || url.equalsIgnoreCase("null")) {
                                urllist.add("");
                            } else {
                                urllist.add(url);
                            }
                            String bathroom = c.getString("bathroom").trim();
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
                            type_list.add(type);
                            landmark_list.add(landmark);
                            locality_list.add(locality);
                            latitude_list.add(latitude);
                            longitude_list.add(longitude);
                            pid_list.add(id);
                            pname_list.add(name);
                            ppostedby_list.add(posted_by);
                            pmobile_list.add(mobile);

                            ptype_list.add("" + state + " " + city);

                            pbedroom_list.add(room);
                            ptotalarea_list.add(area);
                            ptotalprice_list.add(tot_price);
                            ppriceperunit_list.add(area_unit);
                            pimage_list.add(featureimage);
                            floor_list.add(floor);


                        }

                        dismiss_dialogue();
                    /*    adapter = new Recent_Property_adapter(context, pid_list,pname_list,ppostedby_list,pmobile_list,ptype_list,pbedroom_list,
                                ptotalarea_list,ptotalprice_list,ppriceperunit_list,pimage_list,getActivity());*/

                        mAdapter = new HomeRecentProject_MoreAdaptor(context, pid_list, pname_list, ppostedby_list, pmobile_list, ptype_list, pbedroom_list,
                                ptotalarea_list, ptotalprice_list, ppriceperunit_list, pimage_list, latitude_list, longitude_list, locality_list, landmark_list, type_list, floor_list, bathroomlist, urllist, getActivity());

                        /* mAdapter.setHasStableIds(true);*/
                   /*     mItems = getData(0);
                        mAdapter.setItems(mItems);*/
                        homerecent_property_recycler.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();

                    }

                    autoScroll();
                } catch (JSONException e) {
                    dismiss_dialogue();
                    Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
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

    public ArrayList<ItemHome> getHome() {
        ArrayList<ItemHome> itemHomes = new ArrayList<>();
        itemHomes.add(new ItemHome("Search\n Properties", R.drawable.searchproperty));
        itemHomes.add(new ItemHome("Post Property \nFor Free", R.drawable.addproperty));
        itemHomes.add(new ItemHome("Recent Projects", R.drawable.recentproject));
        itemHomes.add(new ItemHome("My\n Requirements", R.drawable.myrequirement));
        itemHomes.add(new ItemHome("My\n Properties", R.drawable.viewproperty));
        itemHomes.add(new ItemHome("Register For \nHome Loan", R.drawable.homeloan));

        return itemHomes;
    }

    public void autoScroll() {
        final int speedScroll = 3000;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = 0;

            @Override
            public void run() {
                if (count == mAdapter.getItemCount())
                    count = 0;
                if (count < mAdapter.getItemCount()) {
                    homerecent_property_recycler.smoothScrollToPosition(++count);
                    handler.postDelayed(this, speedScroll);
                }
            }
        };
        handler.postDelayed(runnable, speedScroll);
    }
}

