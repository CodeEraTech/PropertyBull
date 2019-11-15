package com.doomshell.property_bull;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.doomshell.property_bull.adapter.RecentProject_MoreAdaptor;
import com.doomshell.property_bull.helper.CustomProgressDialog;
import com.doomshell.property_bull.helper.Serverapi;
import com.doomshell.property_bull.model.Recent_pro;
import com.doomshell.property_bull.model.SharedPrefManager;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;


public class Recent_Projectlist extends Fragment {
    Context context;
    View convertview;
    String userid;
    AppCompatActivity appCompatActivity;
    ActionBar actionBar;
    String Baseurl;
    RestAdapter restAdapter = null;
    Serverapi serverapi;
    Dialog contactdialog;
    ProgressBar bar;
    RecentProject_MoreAdaptor mAdapter;
    private List<Recent_pro> mItems;
    private Handler mHandler = new Handler();
    RecyclerView recent_property_recycler;
    RecyclerView.Adapter adapter;

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
        convertview = inflater.inflate(R.layout.fragment_recent_property, container, false);

        appCompatActivity = (AppCompatActivity) getActivity();
        MainHome_Frag.isRecentCalldetais = false;

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        TextView screentitle = (TextView) toolbar.findViewById(R.id.screentitle);
        screentitle.setText("Recent Projects");

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
        userid = SharedPrefManager.getInstance(context).getuser_details("id");
        load_recentprojects();

        recent_property_recycler = (RecyclerView) convertview.findViewById(R.id.recent_property_recycler);
        recent_property_recycler.setLayoutManager(new LinearLayoutManager(context));
        recent_property_recycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecentPropertyDetails details = new RecentPropertyDetails();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, details);
                transaction.addToBackStack(details.getClass().toString());
                transaction.commit();
            }
        });

       /* recent_property_recycler.setOnScrollListener(new RecyclerViewScrollListener() {
            @Override
            public void onScrollUp() {

            }

            @Override
            public void onScrollDown() {

            }

            @Override
            public void onLoadMore() {
loadMoreData();
            }
        });*/

        return convertview;
    }


    void load_recentprojects() {

        show_dialogue();
        serverapi.recent_project(new Callback<Response>() {
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

                        mAdapter = new RecentProject_MoreAdaptor(context, pid_list, pname_list, ppostedby_list, pmobile_list, ptype_list, pbedroom_list,
                                ptotalarea_list, ptotalprice_list, ppriceperunit_list, pimage_list, latitude_list, longitude_list, locality_list, landmark_list, type_list, floor_list, bathroomlist, urllist, getActivity());
                        /* mAdapter.setHasStableIds(true);*/
                   /*     mItems = getData(0);
                        mAdapter.setItems(mItems);*/
                        recent_property_recycler.setAdapter(mAdapter);


                    }
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

/*    private void loadMoreData() {

        mAdapter.showLoading(true);
        mAdapter.notifyDataSetChanged();

        // Load data after delay
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Recent_pro> newItems = getData(mItems.size());
                mItems.addAll(newItems);
                mAdapter.setItems(mItems); // No need of this
                mAdapter.showLoading(false);
                mAdapter.notifyDataSetChanged();
            }
        }, 1500);

    }
    private List<Recent_pro> getData(int start) {
        List<Recent_pro> items = new ArrayList<>();
        for (int i=start; i<start+8; i++) {
            items.add(new Recent_pro(i));
        }

        return items;
    }*/

    public void show_dialogue() {

        dialog = new CustomProgressDialog(getActivity());
    }

    public void dismiss_dialogue() {
        dialog.hide();
    }
}
