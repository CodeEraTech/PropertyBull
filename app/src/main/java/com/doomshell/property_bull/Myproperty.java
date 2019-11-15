package com.doomshell.property_bull;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.doomshell.property_bull.adapter.My_Property_adapter;
import com.doomshell.property_bull.helper.CustomProgressDialog;
import com.doomshell.property_bull.helper.Serverapi;
import com.doomshell.property_bull.model.SharedPrefManager;
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


public class Myproperty extends Fragment {
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
    CustomProgressDialog dialog;

    RecyclerView my_property_recycler;
    RecyclerView.Adapter adapter;
    ArrayList<String> addresslist;
    ArrayList<String> statuslist;
    ArrayList<String> localitylist;
    ArrayList<String> landmarklist;
    ArrayList<String> pid_list;
    ArrayList<String> pname_list;
    ArrayList<String> pimage_list;
    ArrayList<String> ptype_list;
    ArrayList<String> ptotalarea_list;
    ArrayList<String> pmobile_list;
    ArrayList<String> pbedroom_list;
    ArrayList<String> ptotalprice_list;
    ArrayList<String> ppriceperunit_list;
    ArrayList<String> ppostedby_list;
    ArrayList<String> pdescription_list;
    ArrayList<String> depositlist;
    ArrayList<String> bathlist;
    ArrayList<String> p_floorlist;
    ArrayList<String> flooringlist;
    ArrayList<String> floorlist;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity().getApplicationContext();
        convertview = inflater.inflate(R.layout.fragment_myproperty, container, false);

        appCompatActivity = (AppCompatActivity) getActivity();

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        TextView screentitle = (TextView) toolbar.findViewById(R.id.screentitle);
        screentitle.setText("My Properties");

        addresslist = new ArrayList<>();
        statuslist = new ArrayList<>();
        localitylist = new ArrayList<>();
        landmarklist = new ArrayList<>();
        pid_list = new ArrayList<>();
        pname_list = new ArrayList<>();
        pimage_list = new ArrayList<>();
        ptype_list = new ArrayList<>();
        ptotalarea_list = new ArrayList<>();
        pmobile_list = new ArrayList<>();
        pbedroom_list = new ArrayList<>();
        ptotalprice_list = new ArrayList<>();
        ppriceperunit_list = new ArrayList<>();
        ppostedby_list = new ArrayList<>();
        pdescription_list = new ArrayList<>();
        depositlist = new ArrayList<>();
        bathlist = new ArrayList<>();
        p_floorlist = new ArrayList<>();
        flooringlist = new ArrayList<>();
        floorlist = new ArrayList<>();

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

        load_myporperty();

        my_property_recycler = (RecyclerView) convertview.findViewById(R.id.my_property_recycler);
        my_property_recycler.setLayoutManager(new LinearLayoutManager(context));


        return convertview;
    }

    private void load_myporperty() {
        show_dialogue();
        serverapi.myproperty(userid, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                String s = new String(((TypedByteArray) response.getBody()).getBytes());
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    // Toast.makeText(context,""+s,Toast.LENGTH_SHORT).show();
                    int success = jsonObject.getInt("success");
                    ArrayList<String> urllist = new ArrayList<String>();
                    if (success == 1) {
                        JSONArray products = jsonObject.getJSONArray("output");
                        for (int i = 0; i < products.length(); i++) {
                            JSONObject c = products.getJSONObject(i);
                            String id = c.getString("id");
                            String mobile = c.getString("mobile");
                            String description = c.getString("description");
                            String deposit = c.getString("deposit");
                            String bath = c.getString("bath");
                            String floor = c.getString("floor");
                            String p_floor = c.getString("p_floor");
                            String flooring = c.getString("flooring");
                            String name = c.getString("name");
                            String state = c.getString("state");
                            String city = c.getString("city");
                            String posted_by = c.getString("posted_by");
                            String room = c.getString("room");
                            String area = c.getString("area");
                            String area_unit = c.getString("area_unit");
                            String tot_price = c.getString("tot_price");
                            String Propertytype = c.getString("Propertytype");
                            String featureimage = c.getString("featureimage");
                            String address = c.getString("address").toString();
                            String locality = c.getString("locality").toString();
                            String landmark = c.getString("landmark").toString();
                            String status = c.getString("status").toString();
                            statuslist.add(status);
                            String url = c.getString("url").trim();
                            if (url.equalsIgnoreCase("") || url.equalsIgnoreCase("null")) {
                                urllist.add("");
                            } else {
                                urllist.add(url);
                            }
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
                            if (room == null || room.equalsIgnoreCase("null") || room.equals("")) {
                                room = "-";
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
                            if (landmark == null || landmark.equalsIgnoreCase("null") || landmark.equals("")) {
                                landmark = "-";
                            }
                            if (address == null || address.equalsIgnoreCase("null") || address.equals("")) {
                                address = "-";
                            }
                            if (locality == null || locality.equalsIgnoreCase("null") || locality.equals("")) {
                                locality = "-";
                            }

                            pid_list.add(id);
                            pname_list.add(name);
                            ppostedby_list.add(posted_by);
                            pmobile_list.add(mobile);
                            ptype_list.add(Propertytype);
                            pbedroom_list.add(room);
                            ptotalarea_list.add(area);
                            ptotalprice_list.add(tot_price);
                            ppriceperunit_list.add(area_unit);
                            pimage_list.add(featureimage);
                            addresslist.add(address);
                            localitylist.add(locality);
                            landmarklist.add(landmark);
                            pdescription_list.add(description);
                            depositlist.add(deposit);
                            flooringlist.add(flooring);
                            floorlist.add(floor);
                            bathlist.add(bath);
                            p_floorlist.add(p_floor);

                        }

                        dismiss_dialogue();
                        Log.e("pid", pid_list.get(0));
                        adapter = new My_Property_adapter(context, pid_list, pname_list, ppostedby_list, pmobile_list, ptype_list, pbedroom_list,
                                ptotalarea_list, ptotalprice_list, ppriceperunit_list, pimage_list, addresslist, localitylist, landmarklist, statuslist, pdescription_list, depositlist, flooringlist, floorlist, bathlist, p_floorlist, urllist, getActivity());

                        my_property_recycler.setAdapter(adapter);


                    } else {
                        dismiss_dialogue();
                        Toast.makeText(context, "No property added yet from your side", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    dismiss_dialogue();
                    Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
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

}
