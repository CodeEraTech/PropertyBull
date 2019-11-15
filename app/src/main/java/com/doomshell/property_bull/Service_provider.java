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
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.doomshell.property_bull.adapter.Ourservices_Adapter;
import com.doomshell.property_bull.helper.CustomProgressDialog;
import com.doomshell.property_bull.helper.Serverapi;
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


public class Service_provider extends Fragment {
  Context context;
    View convertview;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    String Baseurl;
    RestAdapter restAdapter=null;
    Serverapi serverapi;
   // Dialog contactdialog;
    ProgressBar bar;
    AppCompatActivity appCompatActivity;
    ActionBar actionBar;

    ArrayList<String> consultant_name_list=new ArrayList<>();
    ArrayList<String> featureimage_list=new ArrayList<>();
    ArrayList<String> consultant_description_list=new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context=getActivity().getApplicationContext();
        convertview= inflater.inflate(R.layout.fragment_service_provider, container, false);

        appCompatActivity = (AppCompatActivity) getActivity();


        Toolbar toolbar=(Toolbar)getActivity().findViewById(R.id.toolbar);
        TextView screentitle=(TextView)toolbar.findViewById(R.id.screentitle);
        ImageView imageView=(ImageView) toolbar.findViewById(R.id.screenimageview);
        BottomNavigationView bottomnavView = getActivity().findViewById(R.id.bottom_nav_view);
        bottomnavView.setVisibility(View.GONE);
        screentitle.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
        screentitle.setText("Home Loan");

        Baseurl = getActivity().getResources().getString(R.string.myurl);
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(5, TimeUnit.MINUTES.MINUTES);
        okHttpClient.setReadTimeout(5, TimeUnit.MINUTES.MINUTES);
        //  myCustomProgress_dialogue=new MyCustomProgress_dialogue(getActivity(),R.color.colorPrimary);

        restAdapter = new RestAdapter.Builder().setEndpoint(Baseurl).setClient(new OkClient(okHttpClient)).build();
        serverapi = restAdapter.create(Serverapi.class);

        /*contactdialog = new Dialog(getActivity());
        contactdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        contactdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00ff1919")));
        bar = new ProgressBar(getActivity(), null, android.R.attr.progressBarStyleLarge);
//bar.setProgress()
        bar.getIndeterminateDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY);
        contactdialog.setContentView(bar);
        contactdialog.setCancelable(true);
*/

        recyclerView=(RecyclerView)convertview.findViewById(R.id.ourservice_recycleview);
        layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);


        return convertview;
    }
void  load_service_providers()
{
    final CustomProgressDialog dialog=new CustomProgressDialog(getActivity());


    serverapi.consltants(new Callback<Response>() {
        @Override
        public void success(Response response, Response response2) {
            String s=new String(((TypedByteArray) response.getBody()).getBytes());
            dialog.hide();
            try {
                JSONObject jsonObject = new JSONObject(s);
                int success = jsonObject.getInt("success");
                ArrayList<String> idlist=new ArrayList<String>();

                if (success==1) {
                    JSONArray products = jsonObject.getJSONArray("output");

                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);


                        String des=c.getString("consultant_description").trim();
                        des=stripHtml(des);
                        String name=c.getString("consultant_name").trim();
                        name=stripHtml(name);
                        consultant_name_list.add(name);
                        idlist.add(c.getString("id"));
                        featureimage_list.add(c.getString("featureimage"));
                        consultant_description_list.add(des);
                    }

                    adapter=new Ourservices_Adapter(context,idlist,featureimage_list,consultant_name_list,consultant_description_list,getActivity());
                   /* adapter=new Ourservices_Adapter(context,idlist,consultant_name_list,getActivity());*/
                    recyclerView.setAdapter(adapter);

                }else {
                    dialog.hide();
                    Toast.makeText(context,"No Provider Found",Toast.LENGTH_SHORT).show();
                }
                }catch (Exception e)
            {

                Toast.makeText(context,"Something went wrong on server",Toast.LENGTH_SHORT).show();
                dialog.hide();
                e.printStackTrace();
            }
            }

        @Override
        public void failure(RetrofitError error) {
            dialog.hide();
            Toast.makeText(context,"Check Internet Connection",Toast.LENGTH_SHORT).show();
        }
    });
}

/*    public void show_dialogue()
    {

        contactdialog.show();
    }

    public void dismiss_dialogue()
    {
        contactdialog.dismiss();
    }*/

    public String stripHtml(String html) {return Html.fromHtml(html).toString();}

}
