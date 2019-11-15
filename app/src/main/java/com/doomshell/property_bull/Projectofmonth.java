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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.doomshell.property_bull.adapter.Project_Of_Month_Adapter;
import com.doomshell.property_bull.helper.Serverapi;
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


public class Projectofmonth extends Fragment {
    Dialog contactdialog;
    ProgressBar bar;

    Context context;
    View convertview;
    String Baseurl;
    RestAdapter restAdapter=null;
    Serverapi serverapi;
   // MyCustomProgress_dialogue myCustomProgress_dialogue=null;
    RecyclerView.Adapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<String> id_list=new ArrayList<>();
    ArrayList<String> name_list=new ArrayList<>();
    ArrayList<String> city_list=new ArrayList<>();
    ArrayList<String> state_list=new ArrayList<>();
    ArrayList<String> featureimage_list=new ArrayList<>();
    ArrayList<String> buildername=new ArrayList<>();
    ArrayList<String> rooms=new ArrayList<>();
    AppCompatActivity appCompatActivity;
    ActionBar actionBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        convertview= inflater.inflate(R.layout.fragment_projectofmonth, container, false);
        context=getActivity().getApplicationContext();

        appCompatActivity = (AppCompatActivity) getActivity();

        Toolbar toolbar=(Toolbar)getActivity().findViewById(R.id.toolbar);
        TextView screentitle=(TextView)toolbar.findViewById(R.id.screentitle);
        screentitle.setText("Projects Of Month");

        recyclerView=(RecyclerView)convertview.findViewById(R.id.projectmonth_recycleview);
        layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        Baseurl=getActivity().getResources().getString(R.string.myurl);
        OkHttpClient okHttpClient=new OkHttpClient();
        okHttpClient.setReadTimeout(5, TimeUnit.MINUTES.MINUTES);
        okHttpClient.setReadTimeout(5,TimeUnit.MINUTES.MINUTES);

        restAdapter=new RestAdapter.Builder().setEndpoint(Baseurl).setClient(new OkClient(okHttpClient)).build();
        serverapi=restAdapter.create(Serverapi.class);
        contactdialog = new Dialog(getActivity());
        contactdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        contactdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00ff1919")));
        bar = new ProgressBar(getActivity(), null, android.R.attr.progressBarStyleLarge);
//bar.setProgress()
        bar.getIndeterminateDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY);
        contactdialog.setContentView(bar);
        contactdialog.setCancelable(false);

        load_project_of_month();


        return convertview;
    }

    void load_project_of_month()
    {
        show_dialogue();
    //    myCustomProgress_dialogue=new MyCustomProgress_dialogue(getActivity(),R.color.colorPrimary);
      //  myCustomProgress_dialogue.show_dialogue();


        serverapi.project_month(new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
dismiss_dialogue();
                String s=new String(((TypedByteArray) response.getBody()).getBytes());
  //             Log.d("ok",s);
      //
                //          Toast.makeText(context,""+s,Toast.LENGTH_LONG).show();

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
                            state_list.add(c.getString("state"));
                            featureimage_list.add(c.getString("featureimage"));
                            buildername.add(c.getString("user_name"));
                            rooms.add(c.getString("rooms"));


                        }

                    }
                    adapter = new Project_Of_Month_Adapter(context, id_list, name_list,city_list,state_list,featureimage_list,buildername,rooms);
                    recyclerView.setAdapter(adapter);


                } catch (JSONException e) {
                    dismiss_dialogue();

                }

             //   myCustomProgress_dialogue.dismiss_dialogue();
            }

            @Override
            public void failure(RetrofitError error) {
                dismiss_dialogue();
              //  myCustomProgress_dialogue.dismiss_dialogue();

            }
        });
    }
    public void show_dialogue()
    {

        contactdialog.show();
    }

    public void dismiss_dialogue()
    {
        contactdialog.dismiss();
    }
}
