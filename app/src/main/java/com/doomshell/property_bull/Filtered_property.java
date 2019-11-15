package com.doomshell.property_bull;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.doomshell.property_bull.adapter.Search_Property_usertypeadapter;
import com.doomshell.property_bull.helper.CustomProgressDialog;
import com.doomshell.property_bull.helper.Serverapi;
import com.doomshell.property_bull.model.S_property;
import com.doomshell.property_bull.adapter.Search_MoreAdapter;
import com.doomshell.property_bull.model.SharedPrefManager;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONArray;
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

import static com.doomshell.property_bull.MainHome_Frag.isRecentCallSearch;

public class Filtered_property extends Fragment implements View.OnClickListener {
    Context context;
    View convertview;
    RecyclerView filtered_recylcer;
    RecyclerView.Adapter adapter;
    String Baseurl;
    String s = "";
    RestAdapter restAdapter = null;
    // MyCustomProgress_dialogue myCustomProgress_dialogue=null;
    CustomProgressDialog dialog;
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
    ArrayList<String> addresslist;
    ArrayList<String> localitylist;
    ArrayList<String> landmarklist;
    ArrayList<String> descriptionlist;
    ArrayList<String> depositlist;
    ArrayList<String> bathlist;
    ArrayList<String> p_floorlist;
    ArrayList<String> flooringlist;
    ArrayList<String> floorlist;
    ArrayList<String> addedusertype;
    ArrayList<String> addedusername;
    SharedPreferencesDatabase sharedPreferencesDatabase;
    OkHttpClient okHttpClient;
    AppCompatActivity appCompatActivity;
    ActionBar actionBar;
    JSONArray jsonArray;
    Search_MoreAdapter mAdapter;
    private List<S_property> mItems;
    private Handler mHandler = new Handler();

    Button savesearch;
    String option_name = "", cityid = "", sLocalityid = "", sprojectname = "", ptypeID = "", minprice = "", maxprice = "", option_numbeds = "", minarea = "", maxarea = "", roletxt = "", agebuildingsearch = "";

    String isrequirement = "0";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        addedusertype = new ArrayList<>();
        addedusername = new ArrayList<>();
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
        addresslist = new ArrayList<>();
        localitylist = new ArrayList<>();
        landmarklist = new ArrayList<>();
        descriptionlist = new ArrayList<>();
        depositlist = new ArrayList<>();
        bathlist = new ArrayList<>();
        p_floorlist = new ArrayList<>();
        flooringlist = new ArrayList<>();
        floorlist = new ArrayList<>();
        sharedPreferencesDatabase = new SharedPreferencesDatabase(getActivity());
        sharedPreferencesDatabase.createDatabase();



        convertview = inflater.inflate(R.layout.fragment_filtered_property, container, false);
        context = getActivity().getApplicationContext();

        appCompatActivity = (AppCompatActivity) getActivity();

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ImageView share = (ImageView) toolbar.findViewById(R.id.share_recent);
        share.setVisibility(View.GONE);
        isRecentCallSearch = true;
        MainHome_Frag.isRecentCallSearch1 = false;
        TextView screentitle = (TextView) toolbar.findViewById(R.id.screentitle);
        savesearch = (Button) toolbar.findViewById(R.id.savesearch);
        savesearch.setVisibility(View.VISIBLE);
        savesearch.setOnClickListener(this);

        if (sharedPreferencesDatabase != null) {
            s = sharedPreferencesDatabase.getData("s");
            option_name = sharedPreferencesDatabase.getData("option_name");
            cityid = sharedPreferencesDatabase.getData("cityid");
            sLocalityid = sharedPreferencesDatabase.getData("sLocalityid");
            sprojectname = sharedPreferencesDatabase.getData("sprojectname");
            minprice = sharedPreferencesDatabase.getData("minprice");
            ptypeID = sharedPreferencesDatabase.getData("ptypeID");
            maxprice = sharedPreferencesDatabase.getData("maxprice");
            option_numbeds = sharedPreferencesDatabase.getData("option_numbeds");
            minarea = sharedPreferencesDatabase.getData("minarea");
            maxarea = sharedPreferencesDatabase.getData("maxarea");
            roletxt = sharedPreferencesDatabase.getData("roletxt");
            agebuildingsearch = sharedPreferencesDatabase.getData("agebuildingsearch");
            isrequirement = sharedPreferencesDatabase.getData("isrequirement");
            if (isrequirement.equalsIgnoreCase("0")) {
                savesearch.setVisibility(View.VISIBLE);
            } else {
                savesearch.setVisibility(View.GONE);
            }
        }

        savedInstanceState = getArguments();
        if (savedInstanceState != null) {
            s = savedInstanceState.getString("jarray", null);
            isrequirement = savedInstanceState.getString("isrequirement");
            if (isrequirement.equalsIgnoreCase("0")) {
                savesearch.setVisibility(View.VISIBLE);
            } else {
                savesearch.setVisibility(View.GONE);
            }


            option_name = savedInstanceState.getString("option_name");
            cityid = savedInstanceState.getString("cityid");
            sLocalityid = savedInstanceState.getString("sLocalityid");
            sprojectname = savedInstanceState.getString("sprojectname");
            ptypeID = savedInstanceState.getString("ptypeID");
            minprice = savedInstanceState.getString("minprice");
            maxprice = savedInstanceState.getString("maxprice");
            option_numbeds = savedInstanceState.getString("option_numbeds");
            minarea = savedInstanceState.getString("minarea");
            maxarea = savedInstanceState.getString("maxarea");
            roletxt = savedInstanceState.getString("roletxt");
            agebuildingsearch = savedInstanceState.getString("agebuildingsearch");
            sharedPreferencesDatabase.addData("s", s);
            sharedPreferencesDatabase.addData("option_name", option_name);
            sharedPreferencesDatabase.addData("cityid", cityid);
            sharedPreferencesDatabase.addData("sLocalityid", sLocalityid);
            sharedPreferencesDatabase.addData("sprojectname", sprojectname);
            sharedPreferencesDatabase.addData("ptypeID", ptypeID);
            sharedPreferencesDatabase.addData("minprice", minprice);
            sharedPreferencesDatabase.addData("maxprice", maxprice);
            sharedPreferencesDatabase.addData("option_numbeds", option_numbeds);
            sharedPreferencesDatabase.addData("minarea", minarea);
            sharedPreferencesDatabase.addData("maxarea", maxarea);
            sharedPreferencesDatabase.addData("roletxt", roletxt);
            sharedPreferencesDatabase.addData("agebuildingsearch", agebuildingsearch);
            sharedPreferencesDatabase.addData("isrequirement", isrequirement);
        }
        screentitle.setText("Property List");
        MainHome_Frag.isRecentCallSearch = true;
        Baseurl = getActivity().getResources().getString(R.string.myurl);
        okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(5, TimeUnit.MINUTES.MINUTES);
        okHttpClient.setReadTimeout(5, TimeUnit.MINUTES.MINUTES);

        restAdapter = new RestAdapter.Builder().setEndpoint(Baseurl).setClient(new OkClient(okHttpClient)).build();

        filtered_recylcer = (RecyclerView) convertview.findViewById(R.id.filtered_recylcer);
        filtered_recylcer.setLayoutManager(new LinearLayoutManager(context));

       /* filtered_recylcer.setOnScrollListener(new RecyclerViewScrollListener() {
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

        //myCustomProgress_dialogue=new MyCustomProgress_dialogue(getActivity(), Color.BLUE);
        try {
            show_dialogue();
            jsonArray = new JSONArray(s);
            load_list_of_properties();

        } catch (Exception e) {
            e.printStackTrace();
        }
        //  Toast.makeText(context,s,Toast.LENGTH_SHORT).show();

        return convertview;
    }


    void load_list_of_properties() {
        ArrayList<String> urllist = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject c = null;
            try {
                c = jsonArray.getJSONObject(i);
                String deposit = c.getString("deposit");
                String bath = c.getString("bath");
                String floor = c.getString("floor");
                String p_floor = c.getString("p_floor");
                String flooring = c.getString("flooring");
                String usertype;
                String url = c.getString("url").trim();
                if (url.equalsIgnoreCase("") || url.equalsIgnoreCase("null")) {
                    urllist.add("");
                } else {
                    urllist.add(url);
                }
                if (c.has("usertype")) {
                    usertype = c.getString("usertype");
                } else {
                    usertype = "";
                }
                String username;
                if (c.has("username")) {
                    username = c.getString("username");
                } else {
                    username = "";
                }


                if (usertype == null || usertype.equalsIgnoreCase("null") || usertype.equals("")) {
                    usertype = "-";
                }
                if (username == null || username.equalsIgnoreCase("null") || username.equals("")) {
                    username = "-";
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

                String id = c.getString("id").trim();
                String name = c.getString("project_name").trim();

                // String postedby=c.getString("posted_by").trim();
                String postedby = c.getString("posted_by").trim().substring(0, 1).toUpperCase() + c.getString("posted_by").trim().substring(1);
                String mobile = c.getString("mobile").trim();
                String propertyType = c.getString("Propertytype").trim();
                String Bedroom = c.getString("bedroom").trim();
                String totalarea = c.getString("totalarea").trim();
                String totalPrice = c.getString("totalprice").trim();
                String priceperUnit = c.getString("perunit").trim();
                String featureimage = c.getString("featureimage").trim();
                String address = c.getString("address").toString().trim();
                String locality = c.getString("locality").toString().trim();
                String landmark = c.getString("landmark").toString().trim();
                String description = c.getString("description").toString().trim();
                if (id == null || id.equalsIgnoreCase("null") || id.equals("")) {
                    id = "-";
                }
                if (name == null || name.equalsIgnoreCase("null") || name.equals("")) {
                    name = "-";
                }
                if (postedby == null || postedby.equalsIgnoreCase("null") || postedby.equals("")) {
                    postedby = "-";
                }
                if (propertyType == null || propertyType.equalsIgnoreCase("null") || propertyType.equals("")) {
                    propertyType = "-";
                }

                if (Bedroom == null || Bedroom.equalsIgnoreCase("null") || Bedroom.equals("")) {
                    Bedroom = "-";
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

                if (totalarea == null || totalarea.equalsIgnoreCase("null") || totalarea.equals("")) {
                    totalarea = "-";
                }

                if (totalPrice == null || totalPrice.equalsIgnoreCase("null") || totalPrice.equals("")) {
                    totalPrice = "-";
                }

                if (priceperUnit == null || priceperUnit.equalsIgnoreCase("null") || priceperUnit.equals("")) {
                    priceperUnit = "-";
                }

                if (description == null || description.equalsIgnoreCase("null") || description.equals("")) {
                    description = "-";
                }
                //Toast.makeText(context,ppostedby_list+"" , Toast.LENGTH_LONG).show();
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
                addresslist.add(address);
                localitylist.add(locality);
                landmarklist.add(landmark);
                descriptionlist.add(description);

                depositlist.add(deposit);
                flooringlist.add(flooring);
                floorlist.add(floor);
                bathlist.add(bath);
                p_floorlist.add(p_floor);

                addedusertype.add(usertype);
                addedusername.add(username);

            } catch (Exception e) {
                //   Toast.makeText(context, "error is"+e.toString(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        }

        dismiss_dialogue();
      /*  adapter = new Search_Property_adapter(context, pid_list,pname_list,ppostedby_list,pmobile_list,ptype_list,pbedroom_list,
                ptotalarea_list,ptotalprice_list,ppriceperunit_list,pimage_list,getActivity());
*/
     /*   mAdapter = new Search_MoreAdapter(context, pid_list,pname_list,ppostedby_list,pmobile_list,ptype_list,pbedroom_list,
                ptotalarea_list,ptotalprice_list,ppriceperunit_list,pimage_list,getActivity());
        mAdapter.setHasStableIds(true);
        mItems = addData(0);
        mAdapter.setItems(mItems);*/

        //   Log.e("bhole",pbedroom_list.toString());

        adapter = new Search_Property_usertypeadapter(context, pid_list, pname_list, ppostedby_list, pmobile_list, ptype_list, pbedroom_list,
                ptotalarea_list, ptotalprice_list, ppriceperunit_list, pimage_list, addresslist, localitylist, landmarklist, descriptionlist, depositlist, flooringlist, floorlist, bathlist, p_floorlist, addedusername, addedusertype, urllist, getActivity());
//        filtered_recylcer.setAdapter(mAdapter);
        filtered_recylcer.setAdapter(adapter);

    }

    /*

        @Override
        public void onRequestPermissionsResult(int requestCode,
                                               String permissions[], int[] grantResults) {
            // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if(requestCode==Search_Property_adapter.CallState){
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0) {

                    boolean aaeptcon=grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if(aaeptcon) {
                        Toast.makeText(context,"Thanks for permission granted",Toast.LENGTH_SHORT).show();
                        if(Search_Property_adapter.callIntent!=null) {
                            context.startActivity(Search_Property_adapter.callIntent);
                        }else {
                            Toast.makeText(context,"Null",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(context, "permission required to make call, Please give permission", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, "permission required to make call, Please give permission", Toast.LENGTH_LONG).show();
                }
            }
        }
    */
    public void show_dialogue() {

        dialog = new CustomProgressDialog(getActivity());
    }

    public void dismiss_dialogue() {
        dialog.hide();
    }

    private void loadMoreData() {

        mAdapter.showLoading(true);
        mAdapter.notifyDataSetChanged();

        // Load data after delay
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<S_property> newItems = getData(mItems.size());
                mItems.addAll(newItems);
                mAdapter.setItems(mItems); // No need of this
                mAdapter.showLoading(false);
                mAdapter.notifyDataSetChanged();
            }
        }, 1500);

    }

    private List<S_property> getData(int start) {
        List<S_property> items = new ArrayList<>();
        for (int i = start; i < start + 8; i++) {
            items.add(new S_property(i));
        }

        return items;
    }

    @Override
    public void onClick(View view) {
        if (view == savesearch) {
            savesearchDialog();
        }
    }

    public void savesearchDialog() {
        final AlertDialog contact_alert;
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.myDialog));
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.searchdialog, null);


        dialogBuilder.setView(dialogView);

        ImageView imageView = (ImageView) dialogView.findViewById(R.id.cross);

        final TextView title = (TextView) dialogView.findViewById(R.id.title);
        TextView submit = (TextView) dialogView.findViewById(R.id.submit);


        contact_alert = dialogBuilder.create();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contact_alert.dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (title.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(context, "Title is required for save property search", Toast.LENGTH_SHORT).show();
                } else {
                    savedatasearch(title.getText().toString());
                    contact_alert.dismiss();
                }
            }
        });

        // dialogBuilder.setTitle("Custom dialog");
        //  dialogBuilder.setMessage("Enter text below");


        contact_alert.show();
    }

    public void savedatasearch(String s) {
        restAdapter = new RestAdapter.Builder().setEndpoint(Baseurl).setClient(new OkClient(okHttpClient)).build();
        Serverapi serverapi = restAdapter.create(Serverapi.class);
        final CustomProgressDialog dialog = new CustomProgressDialog(getActivity());
        serverapi.savesearch(option_name, ptypeID, "66", minprice, maxprice, cityid, minarea, maxarea, "", agebuildingsearch, "", option_numbeds, "", "", roletxt, "", sLocalityid, "", SharedPrefManager.getInstance(context).getuser_details("id"), s, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                dialog.hide();
                String s = new String(((TypedByteArray) response.getBody()).getBytes());

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int success = jsonObject.getInt("success");
                    if (success == 1) {
                        Toast.makeText(context, "Property search successfully saved", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Property search not saved", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                    dialog.hide();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                dialog.hide();
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
