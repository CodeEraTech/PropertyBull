package com.doomshell.property_bull;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.TextView;

import com.doomshell.property_bull.adapter.Fav_Property_adapter;
import com.doomshell.property_bull.model.SharedPrefManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class Favourites extends Fragment {

    Context context;
    View convertview;
    RecyclerView fav_recycler;
    RecyclerView.Adapter adapter;

    SharedPreferences contentmanager;
   HashMap<String,String> contnet;

    SharedPreferences recent_contentmanager;
   HashMap<String,String> recent_contnet;

    ArrayList <String> contentlist;
    ArrayList <String> idlist;
    ArrayList <String> namelist;
    ArrayList <String> postedbylist;
    ArrayList <String> mobilelist;
    ArrayList <String> typelist;
    ArrayList <String> bedroomlist;
    ArrayList <String> pricelist;
    ArrayList <String> arealist;
    ArrayList <String> unitlist;
    ArrayList <String> imagelist;
    ArrayList <String> localitylist;
    ArrayList <String> addresslist;
    ArrayList <String> landmarklist;
    ArrayList <String> descriptionlist;

    ArrayList <String> depositlist;
    ArrayList <String> p_floorlist;
    ArrayList <String> floorlist;
    ArrayList <String> flooringlist;

    ArrayList <String> bathlist;
    ArrayList <String> urllist;
    ArrayList <String> recent_urllist;


    ArrayList <String> recent_contentlist;
    ArrayList <String> recent_idlist;
    ArrayList <String> recent_namelist;
    ArrayList <String> recent_postedbylist;
    ArrayList <String> recent_mobilelist;
    ArrayList <String> recent_typelist;
    ArrayList <String> recent_bedroomlist;
    ArrayList <String> recent_pricelist;
    ArrayList <String> recent_arealist;
    ArrayList <String> recent_unitlist;
    ArrayList <String> recent_imagelist;
    ArrayList <String> recent_locality;
    ArrayList <String> recent_address;
    ArrayList <String> recent_landmark;
    ArrayList <String> recent_bathroom;
    ArrayList <String> recent_floor;
    ArrayList <String> recent_lat;
    ArrayList <String> recent_logn;
    ArrayList <String> shortlisttype;
    AppCompatActivity appCompatActivity;
    ActionBar actionBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context=getActivity().getApplicationContext();
        appCompatActivity = (AppCompatActivity) getActivity();
        Toolbar toolbar=(Toolbar)getActivity().findViewById(R.id.toolbar);
        TextView screentitle=(TextView)toolbar.findViewById(R.id.screentitle);
        screentitle.setText("Shortlisted");


        convertview= inflater.inflate(R.layout.fragment_favourites, container, false);

        urllist=new ArrayList<>();
        recent_urllist=new ArrayList<>();
        depositlist=new ArrayList<>();
        p_floorlist=new ArrayList<>();
        floorlist=new ArrayList<>();
        flooringlist=new ArrayList<>();
        bathlist=new ArrayList<>();

        contentlist=new ArrayList<>();
        shortlisttype=new ArrayList<>();
        idlist=new ArrayList<>();
        namelist=new ArrayList<>();
        postedbylist=new ArrayList<>();
        mobilelist=new ArrayList<>();
       typelist=new ArrayList<>();
        bedroomlist=new ArrayList<>();
        pricelist=new ArrayList<>();
        arealist=new ArrayList<>();
        unitlist=new ArrayList<>();
        imagelist=new ArrayList<>();
        localitylist=new ArrayList<>();
        addresslist=new ArrayList<>();
        landmarklist=new ArrayList<>();
        descriptionlist=new ArrayList<>();
        recent_lat=new ArrayList<>();
        recent_logn=new ArrayList<>();

        recent_contentlist=new ArrayList<>();
        recent_idlist=new ArrayList<>();
       recent_namelist=new ArrayList<>();
        recent_postedbylist=new ArrayList<>();
       recent_mobilelist=new ArrayList<>();
        recent_typelist=new ArrayList<>();
        recent_bedroomlist=new ArrayList<>();
        recent_pricelist=new ArrayList<>();
        recent_arealist=new ArrayList<>();
        recent_unitlist=new ArrayList<>();
        recent_imagelist=new ArrayList<>();
        recent_locality=new ArrayList<>();
        recent_address=new ArrayList<>();
        recent_landmark=new ArrayList<>();
        recent_bathroom=new ArrayList<>();
        recent_floor=new ArrayList<>();

        fav_recycler=(RecyclerView)convertview.findViewById(R.id.fav_recycler);
        fav_recycler.setLayoutManager(new LinearLayoutManager(context));


        contentmanager= context.getSharedPreferences("fav_position",Context.MODE_PRIVATE);
       // contnet=new HashMap<>();
       // contnet= (HashMap<String, String>) contentmanager.getAll();
        Map<String,?> propertykeys = contentmanager.getAll();

        contnet=new LinkedHashMap<>();
        for(Map.Entry<String,?> entry : propertykeys.entrySet()) {
           /* Log.d("map values", entry.getKey() + ": " +
                    entry.getValue().toString());*/
            contnet.put(entry.getKey(),entry.getValue().toString());
        }


        recent_contentmanager= context.getSharedPreferences("recent_fav_position",Context.MODE_PRIVATE);
       // recent_contnet=new HashMap<>();
        //recent_contnet= (HashMap<String, String>) recent_contentmanager.getAll();

        Map<String,?> projectkeys = recent_contentmanager.getAll();

        recent_contnet=new LinkedHashMap<>();
        for(Map.Entry<String,?> entry : projectkeys.entrySet()) {
           /* Log.d("map values", entry.getKey() + ": " +
                    entry.getValue().toString());*/
            recent_contnet.put(entry.getKey(),entry.getValue().toString());
        }
        for(Map.Entry<String,String> entry : contnet.entrySet()){
            Log.d("map values",entry.getKey() + " : " +
                    entry.getValue().toString());

            contentlist.add(entry.getValue());
        }

        for(Map.Entry<String,String> entry : recent_contnet.entrySet()){
            Log.d("recent map values",entry.getKey() + " : " +
                    entry.getValue().toString());

            recent_contentlist.add(entry.getValue());
        }
if(!contentlist.isEmpty()) {

    for (int i = 0; i < contentlist.size(); i++) {
        String pos = contentlist.get(i);
        String id = SharedPrefManager.getInstance(context).getfav_property("id" + pos);
        String name = SharedPrefManager.getInstance(context).getfav_property("name" + pos);
        String postedby = SharedPrefManager.getInstance(context).getfav_property("postedby" + pos);
        String mobile = SharedPrefManager.getInstance(context).getfav_property("mobile" + pos);
        String type = SharedPrefManager.getInstance(context).getfav_property("type" + pos);
        String bedroom = SharedPrefManager.getInstance(context).getfav_property("bedroom" + pos);
        String area = SharedPrefManager.getInstance(context).getfav_property("area" + pos);
        String price = SharedPrefManager.getInstance(context).getfav_property("price" + pos);
        String unit = SharedPrefManager.getInstance(context).getfav_property("unit" + pos);
        String image = SharedPrefManager.getInstance(context).getfav_property("image" + pos);
        String locality = SharedPrefManager.getInstance(context).getfav_property("localitylist" + pos);
        String address = SharedPrefManager.getInstance(context).getfav_property("addresslist" + pos);
        String landmark = SharedPrefManager.getInstance(context).getfav_property("landmarklist" + pos);
        String description = SharedPrefManager.getInstance(context).getfav_property("descriptionlist" + pos);
        String deposit = SharedPrefManager.getInstance(context).getfav_property("depositlist" + pos);
        String p_floor = SharedPrefManager.getInstance(context).getfav_property("p_floorlist" + pos);
        String floor = SharedPrefManager.getInstance(context).getfav_property("floorlist" + pos);
        String flooring = SharedPrefManager.getInstance(context).getfav_property("flooringlist" + pos);
        String bath = SharedPrefManager.getInstance(context).getfav_property("bathlist" + pos);
        String url = SharedPrefManager.getInstance(context).getfav_property("urllist" + pos);
        String shortlist = SharedPrefManager.getInstance(context).getfav_property("shortlist" + pos);

if(id!=null) {
    idlist.add(id);
    namelist.add(name);
    urllist.add(url);
    postedbylist.add(postedby);
    mobilelist.add(mobile);
    typelist.add(type);
    bedroomlist.add(bedroom);
    arealist.add(area);
    pricelist.add(price);
    unitlist.add(unit);
    imagelist.add(image);
    localitylist.add(locality);
    addresslist.add(address);
    shortlisttype.add(shortlist);
    landmarklist.add(landmark);
    descriptionlist.add(description);
    bathlist.add(bath);
    flooringlist.add(flooring);
    floorlist.add(floor);
    p_floorlist.add(p_floor);
    depositlist.add(deposit);
}
    }
}

if(!recent_contentlist.isEmpty()) {
    for (int i = 0; i < recent_contentlist.size(); i++) {
        String pos = recent_contentlist.get(i);
        String id = SharedPrefManager.getInstance(context).get_recent_fav_property("id" + pos);
        String name = SharedPrefManager.getInstance(context).get_recent_fav_property("recent_" + "name" + pos);
        String postedby = SharedPrefManager.getInstance(context).get_recent_fav_property("recent_" + "postedby" + pos);
        String mobile = SharedPrefManager.getInstance(context).get_recent_fav_property("recent_" + "mobile" + pos);
        String type = SharedPrefManager.getInstance(context).get_recent_fav_property("recent_" + "type" + pos);
        String bedroom = SharedPrefManager.getInstance(context).get_recent_fav_property("recent_" + "bedroom" + pos);
        String area = SharedPrefManager.getInstance(context).get_recent_fav_property("recent_" + "area" + pos);
        String price = SharedPrefManager.getInstance(context).get_recent_fav_property("recent_" + "price" + pos);
        String unit = SharedPrefManager.getInstance(context).get_recent_fav_property("recent_" + "unit" + pos);
        String image = SharedPrefManager.getInstance(context).get_recent_fav_property("recent_" + "image" + pos);
       // String locality = SharedPrefManager.getInstance(context).get_recent_fav_property("recent_" + "locality_list" + pos);
       // String address = SharedPrefManager.getInstance(context).get_recent_fav_property("recent_" + "locality_list" + pos);
        //String landmark = SharedPrefManager.getInstance(context).get_recent_fav_property("recent_" + "landmark" + pos);
        String locality=SharedPrefManager.getInstance(context).get_recent_fav_property("recent_localitylist"+pos);
        String Lat=SharedPrefManager.getInstance(context).get_recent_fav_property("recent_latitudelist"+pos);
        String Lon=SharedPrefManager.getInstance(context).get_recent_fav_property("recent_longitudelist"+pos);
        String address=Lat+" "+Lon;
        String landmark =SharedPrefManager.getInstance(context).get_recent_fav_property("recent_landmarklist"+pos);
        String bathroom =SharedPrefManager.getInstance(context).get_recent_fav_property("recent_bathroomlist"+pos);
        String floorlist =SharedPrefManager.getInstance(context).get_recent_fav_property("recent_floorlist"+pos);
        String shortlist = SharedPrefManager.getInstance(context).get_recent_fav_property("shortlist" + pos);

        String urllist =SharedPrefManager.getInstance(context).get_recent_fav_property("recent_urllist"+pos);
        if(id!=null) {
            recent_idlist.add(id);
            recent_urllist.add(urllist);
            recent_namelist.add(name);
            recent_postedbylist.add(postedby);
            recent_mobilelist.add(mobile);
            recent_typelist.add(type);
            recent_bedroomlist.add(bedroom);
            recent_arealist.add(area);
            recent_pricelist.add(price);
            recent_unitlist.add(unit);
            recent_imagelist.add(image);
            recent_address.add(address);

            shortlisttype.add(shortlist);
            //recent_logn.add();
            recent_landmark.add(landmark);
recent_locality.add(locality);
            recent_floor.add(floorlist);
            recent_bathroom.add(bathroom);
        }
    }
}

        if(!recent_idlist.isEmpty()) {
            idlist.addAll(recent_idlist);
            namelist.addAll(recent_namelist);
            postedbylist.addAll(recent_postedbylist);
            mobilelist.addAll(recent_mobilelist);
            typelist.addAll(recent_typelist);
            bedroomlist.addAll(recent_bedroomlist);
            arealist.addAll(recent_arealist);
            pricelist.addAll(recent_pricelist);
            unitlist.addAll(recent_unitlist);
            imagelist.addAll(recent_imagelist);
            localitylist.addAll(recent_locality);
            addresslist.addAll(recent_address);
            landmarklist.addAll(recent_landmark);
            floorlist.addAll(recent_floor);
            bathlist.addAll(recent_bathroom);
            urllist.addAll(recent_urllist);

           // bathlist.addAll(recent_bathroom);

           // floorlist.addAll(recent_floor);
        }

        adapter = new Fav_Property_adapter(context, idlist,namelist,postedbylist,mobilelist,typelist,bedroomlist,
                arealist,pricelist,unitlist,imagelist,localitylist,addresslist,landmarklist,shortlisttype,descriptionlist,depositlist,p_floorlist,floorlist,flooringlist,bathlist,urllist,getActivity());

        fav_recycler.setAdapter(adapter);
        fav_recycler.setHasFixedSize(true);
        adapter.notifyDataSetChanged();



        return convertview;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (adapter != null) {
          Fav_Property_adapter adapter1= (Fav_Property_adapter) adapter;
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
            Fav_Property_adapter adapter1= (Fav_Property_adapter) adapter;
            adapter1.restoreStates(savedInstanceState);
        }
    }

}
