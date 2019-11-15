package com.doomshell.property_bull;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doomshell.property_bull.adapter.Projectgallery_MoreAdapter;
import com.doomshell.property_bull.model.User;
import com.doomshell.property_bull.helper.MyCustomProgress_dialogue;
import com.doomshell.property_bull.helper.RecyclerViewScrollListener;
import com.doomshell.property_bull.helper.Serverapi;
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


public class Projects_Gallery extends Fragment {
    Context context;
    View convertview;
    String Baseurl;
    RestAdapter restAdapter=null;
    Serverapi serverapi;
    MyCustomProgress_dialogue myCustomProgress_dialogue=null;
    ArrayList<String> id_list=new ArrayList<>();
    ArrayList<String> name_list=new ArrayList<>();
    ArrayList<String> city_list=new ArrayList<>();
    ArrayList<String> image_list=new ArrayList<>();
    RecyclerView recyclerView;

    Projectgallery_MoreAdapter mAdapter;
    private List<User> mItems;
    private Handler mHandler=new Handler();

    RecyclerView.LayoutManager layoutManager;
    AppCompatActivity appCompatActivity;
    ActionBar actionBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        convertview= inflater.inflate(R.layout.fragment_projects__gallery, container, false);
        context=getActivity().getApplicationContext();

        appCompatActivity = (AppCompatActivity) getActivity();

        Toolbar toolbar=(Toolbar)getActivity().findViewById(R.id.toolbar);
        TextView screentitle=(TextView)toolbar.findViewById(R.id.screentitle);
        screentitle.setText("Properties For Sale");

        Baseurl=getActivity().getResources().getString(R.string.myurl);
        OkHttpClient okHttpClient=new OkHttpClient();
        okHttpClient.setReadTimeout(5, TimeUnit.MINUTES.MINUTES);
        okHttpClient.setReadTimeout(5,TimeUnit.MINUTES.MINUTES);

        restAdapter=new RestAdapter.Builder().setEndpoint(Baseurl).setClient(new OkClient(okHttpClient)).build();
        serverapi=restAdapter.create(Serverapi.class);

        recyclerView=(RecyclerView)convertview.findViewById(R.id.project_list_gallery_recyclerview);
        layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setOnScrollListener(new RecyclerViewScrollListener() {
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
        });

        load_list_properties();

        return convertview;
    }

    void load_list_properties()
    {
        myCustomProgress_dialogue=new MyCustomProgress_dialogue(getActivity(), Color.BLUE);
        myCustomProgress_dialogue.show_dialogue();

        serverapi.propertylist(new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {


                String s=new String(((TypedByteArray) response.getBody()).getBytes());
//                Log.d("ok",s);
           //     Toast.makeText(context,""+s,Toast.LENGTH_LONG).show();

                try {
                    JSONObject jsonObject=new JSONObject(s);
                    int success=jsonObject.getInt("success");

                    if(success==1)
                    {
                        JSONArray products = jsonObject.getJSONArray("output");

                        for (int i = 0; i < products.length(); i++) {
                            JSONObject c = products.getJSONObject(i);

                            id_list.add(c.getString("id"));
                            name_list.add(c.getString("name"));
                            city_list.add(c.getString("city"));
                            image_list.add(c.getString("featureimage"));

                        }

                        }


                    mAdapter = new Projectgallery_MoreAdapter((AppCompatActivity)getActivity(),context,id_list,name_list,city_list,image_list);
                    mAdapter.setHasStableIds(true);
                    mItems = getData(0);
                    mAdapter.setItems(mItems);

                 recyclerView.setAdapter(mAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                myCustomProgress_dialogue.dismiss_dialogue();
            }

            @Override
            public void failure(RetrofitError error) {
                myCustomProgress_dialogue.dismiss_dialogue();


            }
        });

    }

    private void loadMoreData() {

        mAdapter.showLoading(true);
        mAdapter.notifyDataSetChanged();

        // Load data after delay
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<User> newItems = getData(mItems.size());
                mItems.addAll(newItems);
                mAdapter.setItems(mItems); // No need of this
                mAdapter.showLoading(false);
                mAdapter.notifyDataSetChanged();
            }
        }, 1500);

    }
    private List<User> getData(int start) {
        List<User> items = new ArrayList<>();
        for (int i=start; i<start+6; i++) {
            items.add(new User(i));
        }

        return items;
    }
}
