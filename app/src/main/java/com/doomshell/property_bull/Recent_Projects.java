package com.doomshell.property_bull;

import android.content.Context;
import android.graphics.Color;
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

import com.doomshell.property_bull.helper.MyCustomProgress_dialogue;
import com.doomshell.property_bull.helper.Serverapi;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;


public class Recent_Projects extends Fragment {
    Context context;
    View convertview;
    RecyclerView recent_recycler;
    String Baseurl;
    RestAdapter restAdapter = null;
    Serverapi serverapi;
    MyCustomProgress_dialogue myCustomProgress_dialogue = null;
    AppCompatActivity appCompatActivity;
    ActionBar actionBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity().getApplicationContext();
        convertview = inflater.inflate(R.layout.fragment_recent__projects, container, false);
        appCompatActivity = (AppCompatActivity) getActivity();


        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        TextView screentitle = (TextView) toolbar.findViewById(R.id.screentitle);
        ImageView imageView = (ImageView) toolbar.findViewById(R.id.screenimageview);
        BottomNavigationView bottomnavView = getActivity().findViewById(R.id.bottom_nav_view);
        bottomnavView.setVisibility(View.GONE);
        screentitle.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
        screentitle.setText("Recent Projects");

        recent_recycler = (RecyclerView) convertview.findViewById(R.id.recent_recycler);
        recent_recycler.setLayoutManager(new LinearLayoutManager(context));

        myCustomProgress_dialogue = new MyCustomProgress_dialogue(getActivity(), Color.BLUE);

        Baseurl = getActivity().getResources().getString(R.string.myurl);
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(5, TimeUnit.MINUTES.MINUTES);
        okHttpClient.setReadTimeout(5, TimeUnit.MINUTES.MINUTES);

        restAdapter = new RestAdapter.Builder().setEndpoint(Baseurl).setClient(new OkClient(okHttpClient)).build();
        serverapi = restAdapter.create(Serverapi.class);


        return convertview;
    }


    void load_Recent_Projects() {
      /*  void load_list_of_properties()
        {

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject c = null;
                try {
                    c = jsonArray.getJSONObject(i);

                    String id = c.getString("id");
                    String name = c.getString("project_name");
                    String postedby=c.getString("posted_by");
                    String mobile=c.getString("mobile");
                    String propertyType=c.getString("Propertytype");
                    String Bedroom=c.getString("bedroom");
                    String totalarea = c.getString("totalarea");
                    String totalPrice=c.getString("totalprice");
                    String priceperUnit=c.getString("perunit");
                    String featureimage = c.getString("featureimage");

                    if(Bedroom==null || Bedroom.equalsIgnoreCase("null") || Bedroom.equals(""))
                    {
                        Bedroom="-";
                    }

                    if(totalarea==null || totalarea.equalsIgnoreCase("null") || totalarea.equals(""))
                    {
                        totalarea="-";
                    }

                    if(totalPrice==null || totalPrice.equalsIgnoreCase("null") || totalPrice.equals(""))
                    {
                        totalPrice="-";
                    }

                    if(priceperUnit==null || priceperUnit.equalsIgnoreCase("null") || priceperUnit.equals(""))
                    {
                        priceperUnit="-";
                    }

                    pid_list.add(id);
                    pname_list.add(name);
                    ppostedby_list.add(postedby);
                    pmobile_list.add(mobile);
                    ptype_list.add(propertyType);
                    pbedroom_list.add(Bedroom);
                    ptotalarea_list.add(totalarea);
                    ptotalprice_list.add(totalPrice);
                    ppriceperunit_list.add(priceperUnit);
                    pimage_list.add(featureimage);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            myCustomProgress_dialogue.dismiss_dialogue();
            adapter = new Search_Property_adapter(context, pid_list,pname_list,ppostedby_list,pmobile_list,ptype_list,pbedroom_list,
                    ptotalarea_list,ptotalprice_list,ppriceperunit_list,pimage_list,getActivity());

            filtered_recylcer.setAdapter(adapter);



        }
*/
    }

}
