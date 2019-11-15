package com.doomshell.property_bull;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.doomshell.property_bull.adapter.Show_SavedSearch_Adapter;
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


public class Saved_Search extends Fragment {
  Context context;
    View convertview;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    String Baseurl;
    RestAdapter restAdapter=null;
    Serverapi serverapi;

    AppCompatActivity appCompatActivity;
    ActionBar actionBar;

    CustomProgressDialog dialog;
    ArrayList<String> idlist,location,Propertytype,category,name,ptypeid,location_id,owner,age,max_price,min_price,min_area,max_area,min_room,max_room,city_id;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context=getActivity().getApplicationContext();
        convertview= inflater.inflate(R.layout.fragment_savedsearch, container, false);
        idlist=new ArrayList<>();
        Propertytype=new ArrayList<>();

        location=new ArrayList<>();

        appCompatActivity = (AppCompatActivity) getActivity();


        Toolbar toolbar=(Toolbar)getActivity().findViewById(R.id.toolbar);
        TextView screentitle=(TextView)toolbar.findViewById(R.id.screentitle);
        ImageView imageView=(ImageView) toolbar.findViewById(R.id.screenimageview);
        BottomNavigationView bottomnavView = getActivity().findViewById(R.id.bottom_nav_view);
        bottomnavView.setVisibility(View.GONE);
        screentitle.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
        screentitle.setText("Saved Search");


        Baseurl = getActivity().getResources().getString(R.string.myurl);
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(5, TimeUnit.MINUTES.MINUTES);
        okHttpClient.setReadTimeout(5, TimeUnit.MINUTES.MINUTES);
        //  myCustomProgress_dialogue=new MyCustomProgress_dialogue(getActivity(),R.color.colorPrimary);

        restAdapter = new RestAdapter.Builder().setEndpoint(Baseurl).setClient(new OkClient(okHttpClient)).build();
        serverapi = restAdapter.create(Serverapi.class);



        recyclerView=(RecyclerView)convertview.findViewById(R.id.ourservice_recycleview);
        layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        load_search();

        return convertview;
    }
void  load_search()
{

    show_dialogue();
    //SharedPrefManager.getInstance(getActivity()).getuser_details("id");
    serverapi.viewsavesearch(SharedPrefManager.getInstance(getActivity()).getuser_details("id"),new Callback<Response>() {
        @Override
        public void success(Response response, Response response2) {

            dismiss_dialogue();
            try {
                String s=new String(((TypedByteArray) response.getBody()).getBytes());
                JSONObject jsonObject = new JSONObject(s);
                int success = jsonObject.getInt("success");
                name=new ArrayList<String>();
                idlist=new ArrayList<String>();
                category=new ArrayList<String>();
                location=new ArrayList<String>();
                Propertytype=new ArrayList<String>();
                name=new ArrayList<String>();

                ptypeid=new ArrayList<String>();
                min_price=new ArrayList<String>();
                max_price=new ArrayList<String>();
                city_id=new ArrayList<String>();
                min_area=new ArrayList<String>();
                max_area=new ArrayList<String>();

                age=new ArrayList<String>();
                min_room=new ArrayList<String>();

                max_room=new ArrayList<String>();



                owner=new ArrayList<String>();

                location_id=new ArrayList<String>();



                if (success==1) {
                    JSONArray products = jsonObject.getJSONArray("output");

                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);


                        if(!c.getString("name").equalsIgnoreCase("null")) {

                            if (!c.getString("ptypeid").equalsIgnoreCase("null") || !c.getString("ptypeid").equalsIgnoreCase("")) {
                                ptypeid.add(c.getString("ptypeid"));
                            } else {
                                ptypeid.add("");
                            }
                            if (!c.getString("location_id").equalsIgnoreCase("null")  || !c.getString("location_id").equalsIgnoreCase("")) {
                                location_id.add(c.getString("location_id"));
                            } else {
                                location_id.add("");
                            }
                            if (!c.getString("owner").equalsIgnoreCase("null") || !c.getString("owner").equalsIgnoreCase("")) {
                                owner.add(c.getString("owner"));
                            } else {
                                owner.add("");
                            }
                            if (!c.getString("age").equalsIgnoreCase("null") || !c.getString("age").equalsIgnoreCase("")) {
                                age.add(c.getString("age"));
                            } else {
                                age.add("");
                            }
                            if (!c.getString("min_room").equalsIgnoreCase("null")  || !c.getString("min_room").equalsIgnoreCase("")) {
                                min_room.add(c.getString("min_room"));
                            } else {
                                min_room.add("");
                            }

                            if (!c.getString("max_room").equalsIgnoreCase("null")  || !c.getString("max_room").equalsIgnoreCase("")) {
                                max_room.add(c.getString("max_room"));
                            } else {
                                max_room.add("");
                            }

                            if (!c.getString("max_area").equalsIgnoreCase("null")  || !c.getString("max_area").equalsIgnoreCase("")) {
                                max_area.add(c.getString("max_area"));
                            } else {
                                max_area.add("");
                            }

                            if (!c.getString("min_area").equalsIgnoreCase("null")  || !c.getString("min_area").equalsIgnoreCase("")) {
                                min_area.add(c.getString("min_area"));
                            } else {
                                min_area.add("");
                            }

                            if (!c.getString("min_price").equalsIgnoreCase("null")  || !c.getString("min_price").equalsIgnoreCase("")) {
                                min_price.add(c.getString("min_price"));
                            } else {
                                min_price.add("");
                            }

                            if (!c.getString("max_price").equalsIgnoreCase("null")  || !c.getString("max_price").equalsIgnoreCase("")) {
                                max_price.add(c.getString("max_price"));
                            } else {
                                max_price.add("");
                            }


                            if (!c.getString("name").equalsIgnoreCase("null")) {
                                name.add(c.getString("name"));
                            } else {
                                name.add("");
                            }
                            idlist.add(c.getInt("id") + "");
                            if (!c.getString("category").equalsIgnoreCase("null")) {
                                category.add(c.getString("category"));
                            } else {
                                category.add("");
                            }
                            if (!c.getString("location").equalsIgnoreCase("null")) {
                                location.add(c.getString("location"));
                            } else {
                                location.add("");
                            }
                            if (!c.getString("p_typeid").equalsIgnoreCase("null")) {
                                Propertytype.add(c.getString("p_typeid"));
                            }

                        }



                    }

                    Show_SavedSearch_Adapter adapter=new Show_SavedSearch_Adapter(getActivity(),idlist,name,category,location,Propertytype,ptypeid,location_id,max_area,min_area,max_price,min_price,max_room,min_room,age,owner,(AppCompatActivity)getActivity());
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



}
