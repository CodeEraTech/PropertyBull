package com.doomshell.property_bull;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.doomshell.property_bull.adapter.Auto_pronames_Adapter;
import com.doomshell.property_bull.helper.CustomProgressDialog;
import com.doomshell.property_bull.helper.Serverapi;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

import static com.doomshell.property_bull.MainHome_Frag.homes;

public class Search_Property_Frag extends Fragment {
    View convertview;
    Context context;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    String Baseurl;
    RestAdapter restAdapter = null;
    Serverapi serverapi;

    Button srach_property_btn;

    ArrayList<String> numOfBed_list;
    ArrayList<String> pronameList;
    ArrayList<String> pronameidList;
    ArrayList<String> localitynameList;
    ArrayList<String> localityidList;
    ArrayList<String> projecttype;

    String sporjectid, sLocalityid, spropertyid, sintention, snumofbeds;
    ArrayList<String> budgetlist;
    //   CustomProgressDialog dialog;

    ArrayList<String> cityname;
    ArrayList<String> cityid;

    AppCompatActivity appCompatActivity;
    ActionBar actionBar;

    RadioGroup rg_option, rg_ptype;
    RadioButton rb_option, rb_option_numbeds;
    ToggleButton rb_ptype;
    int optionid, optionID_numbeds, ptype_checkedid;
    String option_name, option_numbeds, ptypeID, s_cityid;
    Spinner ps_spiner, propertytypespinner, bedroomnospinner;

    String minprice, maxprice = "";

    RadioButton bedr1, bedr2, bedr3, bedr4, bedr5, bedr6, bedr7, bedr8, bedr9, bedr10;
    /*ToggleButton rb_p1,rb_p2,rb_p3,rb_p4,rb_p5,rb_p6,rb_p7,rb_p8,rb_p9,rb_p10,
            rb_p11,rb_p12,rb_p13,rb_p14,rb_p15,rb_p16,rb_p17,rb_p18,rb_p19,rb_p20,rb_p21,rb_p22,rb_p23,rb_p24;*/
    DisplayMetrics displayMetrics;
    double screenHeight;
    double screenWidth;
    String sprojectname;
    int lhi;
    int lwi, lwi2;
    Spinner auto_city_dd;
    boolean islocality;
    AutoCompleteTextView auto_projectnames;
    Drawable p_background;
    // MyCustomProgress_dialogue myCustomProgress_dialogue;

    Dialog contactdialog;
    ProgressBar bar;
    //MultiSelectionSpinner auto_localities;
    Spinner auto_localities, p_agebuilding_spiner;
    LinearLayout bedroom_layout;

    ArrayList<String> agebuiling;
    String agebuildingsearch = "";
    CrystalRangeSeekbar rq_price_rangeSeekbar, rq_area_rangeSeekbar;
    TextView rq_area_seek_min_text, rq_area_seek_max_text;
    TextView rq_price_seek_min_text, rq_price_seek_max_text;
    String minarea = "";
    String maxarea = "";
    boolean isrent, issell;
    Spinner rolespinner;
    ArrayList<String> rolelist;
    String roletxt = "";
    SharedPreferencesDatabase sharedPreferencesDatabase;
    String strStatus = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        convertview = inflater.inflate(R.layout.fragment_search__property_, container, false);
        context = getActivity().getApplicationContext();

        appCompatActivity = (AppCompatActivity) getActivity();
        sharedPreferencesDatabase = new SharedPreferencesDatabase(getActivity());
        sharedPreferencesDatabase.createDatabase();
        agebuildingsearch = "";
        roletxt = "";
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        TextView screentitle = (TextView) toolbar.findViewById(R.id.screentitle);
        ImageView imageView = (ImageView) toolbar.findViewById(R.id.screenimageview);
        BottomNavigationView bottomnavView = getActivity().findViewById(R.id.bottom_nav_view);
        bottomnavView.setVisibility(View.VISIBLE);
        screentitle.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
        screentitle.setText("Search Properties");

        numOfBed_list = new ArrayList<>();
        MainHome_Frag.isRecentCallSearch = false;

        projecttype = new ArrayList<>();
        budgetlist = new ArrayList<>();
        Baseurl = getActivity().getResources().getString(R.string.myurl);
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(5, TimeUnit.MINUTES.MINUTES);
        okHttpClient.setReadTimeout(5, TimeUnit.MINUTES.MINUTES);

        ImageView share = (ImageView) toolbar.findViewById(R.id.share_recent);
        share.setVisibility(View.GONE);

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
        MainHome_Frag.isRecentCallSearch1 = true;
        MainHome_Frag.isRecentCalldetais = true;
        homes = true;
        MainHome_Frag.isRecentCallSearch = false;

        sharedPreferencesDatabase.addData("status", "true");
        //    price_rangeSeekbar = (CrystalRangeSeekbar) convertview.findViewById(R.id.price_rangeSeekbar);
        //    priceseek_min_text = (TextView) convertview.findViewById(R.id.price_seek_min_text);
        //  priceseek_max_text = (TextView) convertview.findViewById(R.id.price_seek_max_text);
        srach_property_btn = (Button) convertview.findViewById(R.id.srach_property_btn);

        auto_localities = (Spinner) convertview.findViewById(R.id.auto_loc);
        propertytypespinner = (Spinner) convertview.findViewById(R.id.propertytypespinner);
        auto_city_dd = (Spinner) convertview.findViewById(R.id.auto_city_dd);
        ps_spiner = (Spinner) convertview.findViewById(R.id.ps_spiner);
        p_agebuilding_spiner = (Spinner) convertview.findViewById(R.id.p_agebuilding_spiner);
        rolespinner = (Spinner) convertview.findViewById(R.id.rolespinner);
        bedroomnospinner = (Spinner) convertview.findViewById(R.id.bedroomnospinner);
        bedroom_layout = (LinearLayout) convertview.findViewById(R.id.bedroom_layout);
        rq_price_rangeSeekbar = (CrystalRangeSeekbar) convertview.findViewById(R.id.rq_price_rangeSeekbar);
        rq_area_rangeSeekbar = (CrystalRangeSeekbar) convertview.findViewById(R.id.rq_area_rangeSeekbar);
        rq_price_seek_min_text = (TextView) convertview.findViewById(R.id.rq_price_seek_min_text);
        rq_price_seek_max_text = (TextView) convertview.findViewById(R.id.rq_price_seek_max_text);
        rq_area_seek_min_text = (TextView) convertview.findViewById(R.id.rq_area_seek_min_text);
        rq_area_seek_max_text = (TextView) convertview.findViewById(R.id.rq_area_seek_max_text);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        rq_price_rangeSeekbar.setMaxValue(100000000);
        // rq_price_rangeSeekbar.setmi
        // rq_price_seek_min_text.setText("10 lacs");
        rq_price_seek_min_text.setText("0");
        rq_price_seek_max_text.setText("10 crore");
        // rq_price_rangeSeekbar.setMinValue(1000000);
        rq_price_rangeSeekbar.setMinValue(0);

        rq_price_rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {

                float max = maxValue.intValue();
                float min = minValue.intValue();
                if (isrent) {

                    if (minValue.intValue() < 100000) {
                        //   max = max / 1000;
                        min = min / 1000;

                        rq_price_seek_min_text.setText((int) min + " thousand");
                    } else if (minValue.intValue() >= 100000 && minValue.intValue() <= 9999999) {
                        //     max = max / 100000;
                        min = min / 100000;

                        DecimalFormat df = new DecimalFormat("##.##");
                        //  rq_price_seek_min_text.setText("" + df.format(min) + " lakh");
                        rq_price_seek_min_text.setText("" + (int) min + " lacs");
                    } else {
                        //    max = max / 100000;
                        min = min / 100000;
                        DecimalFormat df = new DecimalFormat("##.##");
                        //rq_price_seek_min_text.setText("" + df.format(min) + " crore");
                        rq_price_seek_min_text.setText("" + (int) min + " crore");
                    }

                    if (maxValue.intValue() == 100000) {
                        max = max / 100000;
                        //   min = min / 100000;
                        DecimalFormat df = new DecimalFormat("##.##");
                        // rq_price_seek_max_text.setText("" + df.format(max) + " lakh");
                        rq_price_seek_max_text.setText("" + (int) max + " lacs");
                    } else if (maxValue.intValue() < 100000) {
                        max = max / 1000;
                        //  min = min / 10000;

                        DecimalFormat df = new DecimalFormat("##.##");
                        // rq_price_seek_max_text.setText("" + df.format(max)+ " thousand");
                        rq_price_seek_max_text.setText("" + (int) max + " thousand");
                    } else if (maxValue.intValue() >= 100000 && maxValue.intValue() <= 9999999) {
                        max = max / 100000;
                        //   min = min / 100000;
                        DecimalFormat df = new DecimalFormat("##.##");
                        //   rq_price_seek_max_text.setText("" + df.format(max) + " lakh");
                        rq_price_seek_max_text.setText("" + (int) max + " lakh");
                    }
                    minprice = "" + minValue.intValue();
                    maxprice = "" + maxValue.intValue();


                    Log.d("min :", "" + minValue.intValue());
                    Log.d("max :", "" + maxValue.intValue());

                } else {

                    if (minValue.intValue() < 10000000) {

                        min = min / 100000;

                        DecimalFormat df = new DecimalFormat("##.##");
                        rq_price_seek_min_text.setText(df.format(min) + " lacs");
                    } else if (minValue.intValue() >= 10000000 && minValue.intValue() <= 9999999) {

                        min = min / 1000000;

                        DecimalFormat df = new DecimalFormat("##.##");
                        rq_price_seek_min_text.setText(df.format(min) + " crore");
                    } else {
                        min = min / 10000000;

                        DecimalFormat df = new DecimalFormat("##.##");
                        rq_price_seek_min_text.setText(df.format(min) + " crore");
                    }


                    if (maxValue.intValue() == 100000000) {
                        max = max / 10000000;
                        DecimalFormat df = new DecimalFormat("##.##");
                        rq_price_seek_max_text.setText(df.format(max) + " crore");
                    } else if (maxValue.intValue() >= 9999999) {
                        max = max / 10000000;
                        DecimalFormat df = new DecimalFormat("##.##");
                        rq_price_seek_max_text.setText("" + df.format(max) + " crore");
                    } else {
                        max = max / 100000;
                        DecimalFormat df = new DecimalFormat("##.##");
                        rq_price_seek_max_text.setText(df.format(max) + " lacs");
                    }
                    minprice = "" + minValue.intValue();
                    maxprice = "" + maxValue.intValue();
                }
            }
        });


        rq_area_rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {

                rq_area_seek_min_text.setText("" + minValue);
                rq_area_seek_max_text.setText("" + maxValue);

                minarea = "" + minValue;
                maxarea = "" + maxValue;

            }
        });


        auto_projectnames = (AutoCompleteTextView) convertview.findViewById(R.id.auto_projectname);
        rg_option = (RadioGroup) convertview.findViewById(R.id.rg_option);
        rg_option.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int optionid = rg_option.getCheckedRadioButtonId();

                RadioButton rb = (RadioButton) convertview.findViewById(optionid);

                option_name = rb.getText().toString();
                if (option_name.equalsIgnoreCase("buy")) {
                    option_name = "Sell";
                    //    issell=true;
                    isrent = false;
                    rq_price_rangeSeekbar.setMaxValue(100000000);
                    // rq_price_rangeSeekbar.setmi
                    //rq_price_seek_min_text.setText("10 lacs");
                    rq_price_seek_min_text.setText("0");
                    rq_price_seek_max_text.setText("10 crore");
                    // rq_price_rangeSeekbar.setMinValue(1000000);
                    rq_price_rangeSeekbar.setMinValue(0);
                    maxprice = "100000000";
                    minprice = "0";

                } else {
                    option_name = "Rent";
                    rq_price_rangeSeekbar.setMaxValue(1000000);
                    rq_price_rangeSeekbar.setMinValue(0);
                    // rq_price_rangeSeekbar.setMinValue(5000);

                    // rq_price_seek_min_text.setText("5 thousand");
                    rq_price_seek_min_text.setText("0");
                    rq_price_seek_max_text.setText("10 lacs");
                    //  issell=false;
                    maxprice = "1000000";
                    minprice = "0";

                    isrent = true;
                }


            }
        });
        RadioButton rb_buy = (RadioButton) convertview.findViewById(R.id.rb_buy);
        rb_buy.setSelected(true);
        rb_buy.setChecked(true);
        // rg_numBeds = (RadioGroup) convertview.findViewById(R.id.rg_numbeds);


        budgetlist.add("Select Your Budget");
        budgetlist.add("10 LACS");
        budgetlist.add("20 LACS");
        budgetlist.add("30 LACS");
        budgetlist.add("40 LACS");
        budgetlist.add("50 LACS");
        budgetlist.add("60 LACS");
        budgetlist.add("70 LACS");
        budgetlist.add("80 LACS");
        budgetlist.add("90 LACS");
        budgetlist.add("1 CRORE");
        budgetlist.add("5 CRORE");
        budgetlist.add("10 CRORE");

        //  ArrayAdapter<String> spineradapter=new ArrayAdapter<String>(context,R.layout.support_simple_spinner_dropdown_item,budgetlist);

       /* ArrayAdapter<String> spineradapter = new ArrayAdapter<String>(context, R.layout.spinner_item, budgetlist) {
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);

                ((TextView) v).setGravity(Gravity.LEFT);

                if(position==0) {
                    ((TextView) v).setTextColor(getResources().getColor(R.color.dropback));
                }
                else
                {
                    ((TextView) v).setTextColor(Color.BLACK);
                }

                return v;

            }};*/

        //  loadpro();
        ArrayAdapter<String> priceadaptor = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, budgetlist) {
            public View getView(int position, View convertView, ViewGroup group) {
                View view = super.getView(position, convertView, group);
                ((TextView) view).setGravity(Gravity.LEFT);
                if (position == 0) {
                    ((TextView) view).setTextColor(getResources().getColor(R.color.dropback));
                } else {
                    ((TextView) view).setTextColor(Color.BLACK);
                }

                return view;

            }
        };
      /*  ps_spiner.setAdapter(priceadaptor);
        minprice="";
        ps_spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=0) {
                    minprice = getprice(i);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                minprice="";
            }
        });*/

        agebuiling = new ArrayList<>();
        agebuiling.add("Select Age Of Building");
        agebuiling.add("Under Construction");
        agebuiling.add("1");
        agebuiling.add("2");
        agebuiling.add("5");
        agebuiling.add("5-10");
        ArrayAdapter<String> locationadaptor = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, agebuiling) {
            public View getView(int position, View convertView, ViewGroup group) {
                View view = super.getView(position, convertView, group);
                ((TextView) view).setGravity(Gravity.LEFT);
                if (position == 0) {
                    ((TextView) view).setTextColor(getResources().getColor(R.color.dropback));
                } else {
                    ((TextView) view).setTextColor(Color.BLACK);
                }

                return view;

            }
        };
        p_agebuilding_spiner.setAdapter(locationadaptor);

        p_agebuilding_spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    agebuildingsearch = agebuiling.get(i);
                } else {
                    agebuildingsearch = "";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                agebuildingsearch = "";
            }
        });

        rolelist = new ArrayList<>();
        rolelist.add("Select Role");
        rolelist.add("All");
        rolelist.add("Individual");
        rolelist.add("Agent");
        rolelist.add("Builder");


        ArrayAdapter<String> roleadaptor = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, rolelist) {
            public View getView(int position, View convertView, ViewGroup group) {
                View view = super.getView(position, convertView, group);
                ((TextView) view).setGravity(Gravity.LEFT);
                if (position == 0) {
                    ((TextView) view).setTextColor(getResources().getColor(R.color.dropback));
                } else {
                    ((TextView) view).setTextColor(Color.BLACK);
                }

                return view;

            }
        };
        rolespinner.setAdapter(roleadaptor);

        rolespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    if (i == 1) {
                        roletxt = "";
                    }
                    if (i == 2) {
                        roletxt = "2";
                    }
                    if (i == 3) {
                        roletxt = "3";
                    }
                    if (i == 4) {
                        roletxt = "4";
                    }

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                roletxt = "";
            }
        });


        auto_city_dd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                s_cityid = cityid.get(i);
                //  Toast.makeText(context, i+" \n"+s_cityid, Toast.LENGTH_SHORT).show();
                load_locality();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                s_cityid = cityid.get(auto_city_dd.getSelectedItemPosition());
            }
        });


        projecttype = new ArrayList<>();
        projecttype.add("Select Property Type");
        projecttype.add("Residential Flat");
        projecttype.add("Residential House");
        projecttype.add("Residential Plot");
        projecttype.add("Commercial Land");
        projecttype.add("Commercial Office Space");
        projecttype.add("Commercial Shop");
        projecttype.add("Commercial Showroom");
        projecttype.add("Hostel");
        projecttype.add("Residential Villa");
        projecttype.add("Industrial Plot");

      /*  ArrayAdapter<String> spineradapterpropertytypespinner = new ArrayAdapter<String>(context, R.layout.spinner_item, projecttype) {
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);

                ((TextView) v).setGravity(Gravity.LEFT);

                if(position==0) {
                    ((TextView) v).setTextColor(getResources().getColor(R.color.dropback));
                }
                else
                {
                    ((TextView) v).setTextColor(Color.BLACK);
                }
                return v;

            }};
*/
        ArrayAdapter<String> projectadaptor = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, projecttype) {
            public View getView(int position, View convertView, ViewGroup group) {
                View view = super.getView(position, convertView, group);
                ((TextView) view).setGravity(Gravity.LEFT);
                if (position == 0) {
                    ((TextView) view).setTextColor(getResources().getColor(R.color.dropback));
                } else {
                    ((TextView) view).setTextColor(Color.BLACK);
                }

                return view;

            }
        };
        propertytypespinner.setAdapter(projectadaptor);

        propertytypespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String pname = parent.getSelectedItem().toString();
                //   Toast.makeText(context, ""+pname, Toast.LENGTH_SHORT).show();

                if (pname.equals("Residential Plot") || pname.equals("Commercial Land") ||
                        pname.equals("Commercial Office Space") || pname.equals("Commercial Shop") ||
                        pname.equals("Residential Villa") || pname.equalsIgnoreCase("Commercial Showroom")) {
                    bedroom_layout.setVisibility(View.GONE);
                } else {
                    bedroom_layout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        numOfBed_list = new ArrayList<>();
        numOfBed_list.add("Select Number Of Bedroom");
        numOfBed_list.add("1");
        numOfBed_list.add("2");
        numOfBed_list.add("3");
        numOfBed_list.add("4");
        numOfBed_list.add("5");
        numOfBed_list.add("6");
        numOfBed_list.add("7");
        numOfBed_list.add("8");
        numOfBed_list.add("9");
        numOfBed_list.add("9+");

       /* ArrayAdapter<String> spineradapterbedroomnospinner = new ArrayAdapter<String>(context, R.layout.spinner_item, numOfBed_list) {
            public  View getDropDownView(int position, View convertView, ViewGroup parent)
            {
                View drop=super.getDropDownView(position,convertView,parent);

                return drop;
            }

            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
if(position==0) {
    ((TextView) v).setTextColor(getResources().getColor(R.color.dropback));
}
else
{
    ((TextView) v).setTextColor(Color.BLACK);
}


                ((TextView) v).setGravity(Gravity.LEFT);

                //

                return v;

            }};*/

        ArrayAdapter<String> bedroomadaptor = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, numOfBed_list) {
            public View getView(int position, View convertView, ViewGroup group) {
                View view = super.getView(position, convertView, group);
                ((TextView) view).setGravity(Gravity.LEFT);
                if (position == 0) {
                    ((TextView) view).setTextColor(getResources().getColor(R.color.dropback));
                } else {
                    ((TextView) view).setTextColor(Color.BLACK);
                }

                return view;

            }
        };
        bedroomnospinner.setAdapter(bedroomadaptor);


        /*final RadioGroup.OnCheckedChangeListener ToggleListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final RadioGroup radioGroup, final int i) {
                for (int j = 0; j < radioGroup.getChildCount(); j++) {
                    final ToggleButton view = (ToggleButton) radioGroup.getChildAt(j);
                    view.setChecked(view.getId() == i);
                }
            }
        };*/

        /*((RadioGroup)convertview.findViewById(R.id.rg_ptype)).setOnCheckedChangeListener(ToggleListener);*/


        //   rg_ptype = (RadioGroup) convertview.findViewById(R.id.rg_ptype);

        /*rg_ptype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                ptype_checkedid = rg_ptype.getCheckedRadioButtonId();
                rb_ptype = (RadioButton) convertview.findViewById(ptype_checkedid);
                rb_ptype.setTextColor(Color.WHITE);
            }
        });*/

        if (context.getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT) {

            displayMetrics = context.getResources().getDisplayMetrics();
            screenHeight = displayMetrics.heightPixels;
            screenWidth = displayMetrics.widthPixels;

            lwi = (int) (screenWidth * 0.20);
            lwi2 = (int) (screenWidth * 0.40);


        } else {

            displayMetrics = context.getResources().getDisplayMetrics();
            screenHeight = displayMetrics.heightPixels;
            screenWidth = displayMetrics.widthPixels;

            lwi = (int) (screenWidth * 0.40);
            lwi2 = (int) (screenWidth * 0.60);
        }

       /* bedr1 = (RadioButton) convertview.findViewById(R.id.textfilter1);
        bedr2 = (RadioButton) convertview.findViewById(R.id.textfilter2);
        bedr3 = (RadioButton) convertview.findViewById(R.id.textfilter3);
        bedr4 = (RadioButton) convertview.findViewById(R.id.textfilter4);
        bedr5 = (RadioButton) convertview.findViewById(R.id.textfilter5);
        bedr6 = (RadioButton) convertview.findViewById(R.id.textfilter6);
        bedr7 = (RadioButton) convertview.findViewById(R.id.textfilter7);
        bedr8 = (RadioButton) convertview.findViewById(R.id.textfilte8);
        bedr9 = (RadioButton) convertview.findViewById(R.id.textfilter9);
        bedr10 = (RadioButton) convertview.findViewById(R.id.textfilter10);*/


       /* rb_p1 = (ToggleButton) convertview.findViewById(R.id.ptype1);
        rb_p2 = (ToggleButton) convertview.findViewById(R.id.ptype2);
        rb_p3 = (ToggleButton) convertview.findViewById(R.id.ptype3);
        rb_p4 = (ToggleButton) convertview.findViewById(R.id.ptype4);
        rb_p5 = (ToggleButton) convertview.findViewById(R.id.ptype5);
        rb_p6 = (ToggleButton) convertview.findViewById(R.id.ptype6);
        rb_p7 = (ToggleButton) convertview.findViewById(R.id.ptype7);
        rb_p8 = (ToggleButton) convertview.findViewById(R.id.ptype8);
        rb_p9 = (ToggleButton) convertview.findViewById(R.id.ptype9);
        rb_p10 = (ToggleButton) convertview.findViewById(R.id.ptype10);
        rb_p11 = (ToggleButton) convertview.findViewById(R.id.ptype11);
        rb_p12 = (ToggleButton) convertview.findViewById(R.id.ptype12);
        rb_p13 = (ToggleButton) convertview.findViewById(R.id.ptype13);
        rb_p14 = (ToggleButton) convertview.findViewById(R.id.ptype14);
        rb_p15 = (ToggleButton) convertview.findViewById(R.id.ptype15);
        rb_p16 = (ToggleButton) convertview.findViewById(R.id.ptype16);
        rb_p17 = (ToggleButton) convertview.findViewById(R.id.ptype17);
        rb_p18 = (ToggleButton) convertview.findViewById(R.id.ptype18);
        rb_p19 = (ToggleButton) convertview.findViewById(R.id.ptype19);
        rb_p20 = (ToggleButton) convertview.findViewById(R.id.ptype20);
        rb_p21 = (ToggleButton) convertview.findViewById(R.id.ptype21);
        rb_p22 = (ToggleButton) convertview.findViewById(R.id.ptype22);
        rb_p23 = (ToggleButton) convertview.findViewById(R.id.ptype23);
        rb_p24 = (ToggleButton) convertview.findViewById(R.id.ptype24);
*/
       /* bedr1.getLayoutParams().width = lwi;
        bedr2.getLayoutParams().width = lwi;
        bedr3.getLayoutParams().width = lwi;
        bedr4.getLayoutParams().width = lwi;
        bedr5.getLayoutParams().width = lwi;
        bedr6.getLayoutParams().width = lwi;
        bedr7.getLayoutParams().width = lwi;
        bedr8.getLayoutParams().width = lwi;
        bedr9.getLayoutParams().width = lwi;
        bedr10.getLayoutParams().width = lwi;*/

        /*rb_p1.getLayoutParams().width = lwi;
        rb_p2.getLayoutParams().width = lwi;
        rb_p3.getLayoutParams().width = lwi;
        rb_p4.getLayoutParams().width = lwi;
        rb_p5.getLayoutParams().width = lwi;
        rb_p6.getLayoutParams().width = lwi;
        rb_p7.getLayoutParams().width = lwi;
        rb_p8.getLayoutParams().width = lwi;
        rb_p9.getLayoutParams().width = lwi;
        rb_p10.getLayoutParams().width = lwi;
        rb_p11.getLayoutParams().width = lwi;
        rb_p12.getLayoutParams().width = lwi;
        rb_p13.getLayoutParams().width = lwi;
        rb_p14.getLayoutParams().width = lwi;
        rb_p15.getLayoutParams().width = lwi;
        rb_p16.getLayoutParams().width = lwi;
        rb_p17.getLayoutParams().width = lwi;
        rb_p18.getLayoutParams().width = lwi;
        rb_p19.getLayoutParams().width = lwi;
        rb_p20.getLayoutParams().width = lwi;
        rb_p21.getLayoutParams().width = lwi;
        rb_p22.getLayoutParams().width = lwi;
        rb_p23.getLayoutParams().width = lwi;
        rb_p24.getLayoutParams().width = lwi;*/

      /*  bedr1.requestLayout();
        bedr2.requestLayout();
        bedr3.requestLayout();
        bedr4.requestLayout();
        bedr5.requestLayout();
        bedr6.requestLayout();
        bedr7.requestLayout();
        bedr8.requestLayout();
        bedr9.requestLayout();
        bedr10.requestLayout();*/

       /* rb_p1.requestLayout();
        rb_p2.requestLayout();
        rb_p3.requestLayout();
        rb_p4.requestLayout();
        rb_p5.requestLayout();
        rb_p6.requestLayout();
        rb_p7.requestLayout();
        rb_p8.requestLayout();
        rb_p9.requestLayout();
        rb_p10.requestLayout();
        rb_p11.requestLayout();
        rb_p12.requestLayout();
        rb_p13.requestLayout();
        rb_p14.requestLayout();
        rb_p15.requestLayout();
        rb_p16.requestLayout();
        rb_p17.requestLayout();
        rb_p18.requestLayout();
        rb_p19.requestLayout();
        rb_p20.requestLayout();
        rb_p21.requestLayout();
        rb_p22.requestLayout();
        rb_p23.requestLayout();
        rb_p24.requestLayout();*/


        //     load_locality();

       /* ArrayAdapter<String> locationadaptor=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item,localitynameList){
            public View getView(int position,View convertView,ViewGroup group)
            {
                View view=super.getView(position,convertView,group);
                ((TextView)view).setGravity(Gravity.LEFT);
                if(position==0)
                {
                    ((TextView)view).setTextColor(getResources().getColor(R.color.dropback));
                }
                else
                {
                    ((TextView)view).setTextColor(Color.BLACK);
                }

                return  view;

            }
        };*/


        auto_localities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sLocalityid = localityidList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                sLocalityid = "";

            }
        });

        loadpro();

        loadCity();


      /*  auto_projectnames.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (auto_projectnames.hasFocus()) {

                    if (pronameList.isEmpty()) {
                     //   show_dialogue();
                        loadpro();

                    } else {


                    }
                } else {
                    String pn[] = new String[pronameList.size()];
                    pn = pronameList.toArray(pn);

                    show_dialogue();
                    ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, pn) {
                        public View getView(int position, View convertView, ViewGroup parent) {

                            View v = super.getView(position, convertView, parent);

                            ((TextView) v).setGravity(Gravity.LEFT);
                            ((TextView) v).setTextColor(Color.BLACK);

                            return v;

                        }

                        public View getDropDownView(int position, View convertView, ViewGroup parent) {

                            View v = super.getDropDownView(position, convertView, parent);

                            ((TextView) v).setGravity(Gravity.LEFT);
                            ((TextView) v).setTextColor(Color.BLACK);
                            return v;

                        }
                    };
                    auto_projectnames.setThreshold(1);
                    auto_projectnames.setAdapter(stringArrayAdapter);
                    dismiss_dialogue();
                }

            }
        });
*/


        srach_property_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sprojectname = auto_projectnames.getText().toString();



           /*    if(rg_ptype.getCheckedRadioButtonId()==-1)
               {
                   ptypeID="";
             //      Toast.makeText(context, "null select", Toast.LENGTH_SHORT).show();
               }else {

                   ptype_checkedid=rg_ptype.getCheckedRadioButtonId();
                   rb_ptype=(ToggleButton)convertview.findViewById(ptype_checkedid);
                //   rb_ptype.setBackgroundDrawable(getResources().getDrawable(R.drawable.nav_ic_add_property));
                  // rb_ptype.setTextColor(getResources().getColor(R.color.button_color));
                   String pname=rb_ptype.getText().toString();
                   ptypeID=get_ptype_id(pname);
                   Toast.makeText(context, ""+pname+"\n"+ptypeID, Toast.LENGTH_SHORT).show();
               }*/
                //   Pname_setter pname_setter=new Pname_setter();
                String pname = projecttype.get(propertytypespinner.getSelectedItemPosition());
                if (pname.equals("Select Project type")) {
                    ptypeID = "";
                } else {
                    //Toast.makeText(context, ""+pname+"\n"+ptypeID, Toast.LENGTH_SHORT).show();
                    ptypeID = get_ptype_id(pname);
                }

                s_cityid = cityid.get(auto_city_dd.getSelectedItemPosition());

                //     Toast.makeText(context, ""+s_cityid, Toast.LENGTH_SHORT).show();


                if (rg_option.getCheckedRadioButtonId() == -1) {
                    option_name = "";
                } else {
                    optionid = rg_option.getCheckedRadioButtonId();

                    rb_option = (RadioButton) convertview.findViewById(optionid);

                    option_name = rb_option.getText().toString();

                    if (option_name.equalsIgnoreCase("buy")) {
                        option_name = "Sell";
                    }
                }
//                    rg_option.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
//                            optionid=rg_option.getCheckedRadioButtonId();
//
//                            rb_option=(RadioButton)convertview.findViewById(optionid);
//                            //rb_option.setBackgroundColor(getResources().get);
//
//                            option_name=rb_option.getText().toString();
//
//                            if (option_name.equalsIgnoreCase("buy"))
//                            {
//                                option_name="Sell";
//                            }
//                        }
//                    });


               /* if (rg_numBeds.getCheckedRadioButtonId()==-1)
                {
                    option_numbeds="";
                }else {
                    optionID_numbeds=rg_numBeds.getCheckedRadioButtonId();
                    rb_option_numbeds=(RadioButton)convertview.findViewById(optionID_numbeds);

                    option_numbeds=rb_option_numbeds.getText().toString();
                }*/
                option_numbeds = numOfBed_list.get(bedroomnospinner.getSelectedItemPosition());
                if (option_numbeds.equals("Select Number Of Bedroom")) {
                    option_numbeds = "";
                }
                //Toast.makeText(context, ""+pname+"\n"+option_numbeds, Toast.LENGTH_SHORT).show();


                if (sprojectname == null || sprojectname.equals("")) {
                    sporjectid = "";
                } else {
                    int pos = pronameList.indexOf(sprojectname);

                    try {
                        if (pronameidList.contains(pronameidList.get(pos))) {
                            sporjectid = pronameidList.get(pos);
                        } else {
                            sporjectid = "";
                            Toast.makeText(context, "Project not found", Toast.LENGTH_SHORT).show();
                        }

                    } catch (ArrayIndexOutOfBoundsException e) {
                        sporjectid = "";
                        Toast.makeText(context, "Project not found", Toast.LENGTH_SHORT).show();
                    }
                }

                if (sLocalityid == null || sLocalityid.equals("")) {

                    islocality = false;

                } else {

                    islocality = true;


                }

             /*   if (minprice.equals("900000"))
                {
                    minprice="";
                }

                if (maxprice.equals("100100000"))
                {
                    maxprice="";
                }*/

                //    show_dialogue();

                load_search();

                //        Toast.makeText(context,""+minprice,Toast.LENGTH_SHORT).show();


            }
        });


              /*  price_rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
                    @Override
                    public void valueChanged(Number minValue, Number maxValue) {

                        float max=maxValue.intValue();
                        float min=minValue.intValue();
                        max=max/100;
                        min=min/100;

                        if(minValue.intValue()<10) {
                            priceseek_min_text.setText("0 Lakh");
                        }else if(minValue.intValue()>=10 && minValue.intValue()<=99) {

                            priceseek_min_text.setText("" + minValue+ " Lakh");
                        }else {
                            priceseek_min_text.setText("" + min + " Crore");
                        }


                        if (maxValue.intValue()<=99) {
                            priceseek_max_text.setText("" + maxValue + " Lakh");
                        }else if(maxValue.intValue()>=100 && maxValue.intValue()<1000){
                            priceseek_max_text.setText("" + max + " Crore");
                        }else {
                            priceseek_max_text.setText("0" + " Crore");
                        }
                        minprice=""+minValue.intValue()*100000;
                        maxprice=""+maxValue.intValue()*100000;
           }
                });
*/


        //    recyclerView=(RecyclerView)convertview.findViewById(R.id.search_no_bedroom_recycler);
        //  layoutManager=new LinearLayoutManager(context,LinearLayout.HORIZONTAL,false);
        // recyclerView.setLayoutManager(layoutManager);


        //   adapter=new Filter_Checkbox_Adapter(context,numOfBed_list);

        //    recyclerView.setAdapter(adapter);

        return convertview;

    }

    private void load_locality() {

        localitynameList = new ArrayList<>();
        localityidList = new ArrayList<>();

        localityidList.add(0, "");
        localitynameList.add(0, "Select Locality");

        //    Toast.makeText(context, "in local  "+s_cityid, Toast.LENGTH_SHORT).show();

        serverapi.locality(s_cityid, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                String s = new String(((TypedByteArray) response.getBody()).getBytes());

                //     Toast.makeText(context, ""+SharedPrefManager.getInstance(context).getuser_details("city_id"), Toast.LENGTH_SHORT).show();

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int success = jsonObject.getInt("success");
                    if (success == 1) {

                        JSONArray cityarray = jsonObject.getJSONArray("output");

                        for (int i = 0; i < cityarray.length(); i++) {
                            JSONObject c = cityarray.getJSONObject(i);
                            String cityid = c.getString("id");
                            String cityname = c.getString("name");

                            localityidList.add(cityid);
                            localitynameList.add(cityname);
                        }

                        ArrayAdapter<String> localityadaptor = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, localitynameList) {
                            public View getView(int position, View convertView, ViewGroup group) {
                                View view = super.getView(position, convertView, group);
                                ((TextView) view).setGravity(Gravity.LEFT);
                                if (position == 0) {
                                    ((TextView) view).setTextColor(getResources().getColor(R.color.dropback));
                                } else {
                                    ((TextView) view).setTextColor(Color.BLACK);
                                }

                                return view;

                            }
                        };
                        auto_localities.setAdapter(localityadaptor);
                        //  auto_localities.setItems(localitynameList);
                        //  auto_localities.setSelection(new int[]{0});
                        //    auto_localities.setListener(Search_Property_Frag.this);


                    } else {

                        Toast.makeText(context, "Something went wrong on server", Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {

                    // Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
                    Log.e("Local error ", e.toString());
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {

                //  Toast.makeText(context, "in local "+error, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {


        inflater.inflate(R.menu.filtermenu_for_searchproperty, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    void loadpro() {
        pronameList = new ArrayList<>();
        pronameidList = new ArrayList<>();

        final CustomProgressDialog dialog = new CustomProgressDialog(getActivity());
        serverapi.project_list(new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                String s = new String(((TypedByteArray) response.getBody()).getBytes());
                dialog.hide();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int success = jsonObject.getInt("success");

                    if (success == 1) {

                        JSONArray pronameArray = jsonObject.getJSONArray("output");
                        //   Toast.makeText(getApplicationContext(),""+ptypearray,Toast.LENGTH_LONG).show();

                        for (int i = 0; i < pronameArray.length(); i++) {
                            JSONObject c = pronameArray.getJSONObject(i);
                            String name = c.getString("name");
                            String id = c.getString("id");

                            Log.e("pro data", "name : " + name + "\t id : " + id);

                            pronameList.add(name);
                            pronameidList.add(id);
                        }



                        /*String pn[] = new String[pronameList.size()];
                        pn = pronameList.toArray(pn);

                       // show_dialogue();
                     /*   ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, pn) {

                            public View getView(int position, View convertView, ViewGroup parent) {

                                View v = super.getView(position, convertView, parent);

                                ((TextView) v).setGravity(Gravity.LEFT);
                                ((TextView) v).setGravity(Gravity.CENTER_VERTICAL);
                                ((TextView) v).setTextColor(Color.BLACK);

                                return v;

                            }

                            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                                View v = super.getDropDownView(position, convertView, parent);

                                ((TextView) v).setGravity(Gravity.LEFT);
                                ((TextView) v).setGravity(Gravity.CENTER_VERTICAL);
                                ((TextView) v).setTextColor(Color.BLACK);
                                return v;

                            }
                        };*/

                        //auto_projectnames.setThreshold(1);
                        // auto_projectnames.setAdapter(stringArrayAdapter);

                        //new implementation here


                        Auto_pronames_Adapter ap = new Auto_pronames_Adapter(context, pronameList);
                        auto_projectnames.setAdapter(ap);


                    } else {
                        //                 Toast.makeText(context, "Something wnt wrong on server", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    dialog.hide();
                    //            Toast.makeText(context, "in pronames "+e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                dialog.hide();
                // Toast.makeText(context, "in pronames "+error, Toast.LENGTH_SHORT).show();

            }
        });
    }

    ///city search

    void loadCity() {
        cityid = new ArrayList<>();
        cityname = new ArrayList<>();
        final CustomProgressDialog dialog = new CustomProgressDialog(getActivity());

        serverapi.citylist(new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                dialog.hide();
                String s = new String(((TypedByteArray) response.getBody()).getBytes());

                //        Toast.makeText(context, ""+s, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("output");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jo = jsonArray.getJSONObject(i);
                        cityid.add(jo.getString("id"));
                        cityname.add(jo.getString("name"));
                    }

                    ArrayAdapter<String> cityadaptor = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, cityname) {
                        public View getView(int position, View convertView, ViewGroup group) {
                            View view = super.getView(position, convertView, group);
                            ((TextView) view).setGravity(Gravity.LEFT);
                            if (position == 0) {
                                ((TextView) view).setTextColor(getResources().getColor(R.color.dropback));
                            } else {
                                ((TextView) view).setTextColor(Color.BLACK);
                            }

                            return view;

                        }
                    };
                    auto_city_dd.setAdapter(cityadaptor);
                    s_cityid = cityid.get(auto_city_dd.getSelectedItemPosition());

                    // load_locality();
                } catch (Exception e) {
                    // Toast.makeText(context, "in load city : "+e, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                dialog.hide();
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void onDestroyView() {
        super.onDestroyView();
        //   Toast.makeText(context, "resume", Toast.LENGTH_SHORT).show();
        if (pronameList != null && pronameidList != null) {
            pronameList.clear();
            pronameidList.clear();
        }

        if (localityidList != null && localitynameList != null) {
            localityidList.clear();
            localitynameList.clear();
        }

    }

    void load_search() {
        final CustomProgressDialog dialog = new CustomProgressDialog(getActivity());
        //  Toast.makeText(context, "ok "+option_name, Toast.LENGTH_SHORT).show();
        if (option_name.equalsIgnoreCase("Sell")) {
            if (minprice.equalsIgnoreCase("0") & maxprice.equalsIgnoreCase("100000000")) {
                minprice = "";
                maxprice = "";

            }
            if (minarea.equalsIgnoreCase("0") & maxarea.equalsIgnoreCase("5000")) {
                minarea = "";
                maxarea = "";
            }

        } else {
            if (minprice.equalsIgnoreCase("0") & maxprice.equalsIgnoreCase("1000000")) {
                minprice = "";
                maxprice = "";

            }
            if (minarea.equalsIgnoreCase("0") & maxarea.equalsIgnoreCase("5000")) {
                minarea = "";
                maxarea = "";
            }
        }
        serverapi.search(
                option_name,
                s_cityid,
                sLocalityid,
                sprojectname,
                ptypeID,
                minprice,
                maxprice,
                option_numbeds,
                agebuildingsearch,
                minarea,
                maxarea,
                roletxt,
                new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        dialog.hide();
                        String s = new String(((TypedByteArray) response.getBody()).getBytes());

                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int success = jsonObject.getInt("success");
                            if (success == 1) {
                                JSONArray jsonArray = jsonObject.getJSONArray("output");
                                Bundle bundle = new Bundle();
                                bundle.putString("jarray", jsonArray.toString());
                                bundle.putString("option_name", option_name);
                                bundle.putString("isrequirement", "0");
                                bundle.putString("cityid", s_cityid);
                                bundle.putString("sLocalityid", sLocalityid);
                                bundle.putString("sprojectname", sprojectname);
                                bundle.putString("ptypeID", ptypeID);
                                bundle.putString("minprice", minprice);
                                bundle.putString("maxprice", maxprice);
                                bundle.putString("option_numbeds", option_numbeds);
                                bundle.putString("minarea", minarea);
                                bundle.putString("maxarea", maxarea);
                                bundle.putString("roletxt", roletxt);
                                bundle.putString("agebuildingsearch", agebuildingsearch);
                                //Toast.makeText(context,agebuildingsearch,Toast.LENGTH_SHORT).show();


                                Filtered_property filtered_property = new Filtered_property();
                                filtered_property.setArguments(bundle);
                                FragmentTransaction devicetrans = getActivity().getSupportFragmentManager().beginTransaction();
                                devicetrans.replace(R.id.frame_container, filtered_property);
                                devicetrans.addToBackStack(filtered_property.getClass().toString());
                                devicetrans.commit();
                            } else if (success == 0) {
                                Toast.makeText(context, "No Record Found", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Something went wrong on server", Toast.LENGTH_SHORT).show();

                            }

                        } catch (Exception e) {
                            dialog.hide();
                            Log.d("Error is :", e.toString());
                            e.printStackTrace();
                        }

                        dialog.hide();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        dialog.hide();
                        Log.d("resl", "" + error);
                        Toast.makeText(context, "Check Internet Connection " + error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }
        );
    }


    String getprice(int pos) {
        String price = "";
        switch (pos) {
            case 0:
                price = "";
                break;
            case 1:
                price = "1000000";
                break;
            case 2:
                price = "2000000";
                break;
            case 3:
                price = "3000000";
                break;
            case 4:
                price = "4000000";
                break;
            case 5:
                price = "5000000";
                break;

            case 6:
                price = "6000000";
                break;

            case 7:
                price = "7000000";
                break;

            case 8:
                price = "8000000";
                break;

            case 9:
                price = "9000000";
                break;

            case 10:
                price = "10000000";
                break;

            case 11:
                price = "50000000";
                break;
            case 12:
                price = "100000000";
                break;
            default:
                price = "";
        }
        return price;
    }

    String get_ptype_id(String rid) {
        String id;
        switch (rid) {
            case "Select Project Type":
                id = "0";
                break;
            case "Industrial Plot":
                id = "353";
                break;
            case "Affordable Housing Flat":
                id = "343";
                break;
            case "Basement":
                id = "346";
                break;
            case "Builder Floor Apartment":
                id = "322";
                break;
            case "Business Centre":
                id = "328";
                break;
            case "Commercial Land":
                id = "324";
                break;
            case "Commercial Office Space":
                id = "325";
                break;
            case "Commercial Shop":
                id = "326";
                break;
            case "Commercial Showroom":
                id = "327";
                break;
            case "Duplex":
                id = "345";
                break;
            case "Farm House":
                id = "335";
                break;
            case "Hostel":
                id = "339";
                break;
            case "Hotel":
                id = "338";
                break;
            case "Industrial Building":
                id = "331";
                break;
            case "Industrial Land":
                id = "330";
                break;
            case "Paying Guest":
                id = "336";
                break;
            case "PentHouse":
                id = "341";
                break;
            case "Residential Farm House":
                id = "350";
                break;
            case "Residential Flat":
                id = "321";
                break;
            case "Residential House":
                id = "320";
                break;
            case "Residential Plot":
                id = "319";
                break;
            case "Residential Villa":
                id = "342";
                break;
            case "Skeleton":
                id = "340";
                break;
            case "Studio Apartment":
                id = "347";
                break;
            case "Warehouse\\/ Godown":
                id = "329";
                break;
            default:
                id = "";
                break;
        }
        return id;
    }

    /*Drawable get_ptype_getdrawable(String rid)
    {
         Drawable dd;
        switch (rid)
        {
            case "Affordable Housing Flat":
                dd=getResources().getDrawable(R.drawable.ic_p_floor);
                break;
            case "Basement":
                dd=getResources().getDrawable(R.drawable.ic_p_floor);
                break;
            case "Builder Floor Apartment":
                dd=getResources().getDrawable(R.drawable.nav_ic_add_property);
                break;
            case "Business Centre":
                dd=getResources().getDrawable(R.drawable.nav_ic_add_property);
                break;
            case "Commercial Land":
                dd=getResources().getDrawable(R.drawable.nav_ic_add_property);
                break;
            case "Commercial Office Space":
                dd=getResources().getDrawable(R.drawable.nav_ic_add_property);
                break;
            case "Commercial Shop":
                dd=getResources().getDrawable(R.drawable.nav_ic_add_property);
                break;
            case "Commercial Showroom":
                dd=getResources().getDrawable(R.drawable.nav_ic_add_property);
                break;
            case "Duplex":
                dd=getResources().getDrawable(R.drawable.nav_ic_add_property);
                break;
            case "Farm House":
                dd=getResources().getDrawable(R.drawable.nav_ic_add_property);
                break;
            case "Hostel":
                dd=getResources().getDrawable(R.drawable.nav_ic_add_property);
                break;
            case "Hotel":
                dd=getResources().getDrawable(R.drawable.nav_ic_add_property);
                break;
            case "Industrial Building":
                dd=getResources().getDrawable(R.drawable.nav_ic_add_property);
                break;
            case "Industrial Land":
                dd=getResources().getDrawable(R.drawable.nav_ic_add_property);
                break;
            case "Paying Guest":
                dd=getResources().getDrawable(R.drawable.nav_ic_add_property);
                break;
            case "PentHouse":
                dd=getResources().getDrawable(R.drawable.nav_ic_add_property);
                break;
            case "Residential Farm House":
                dd=getResources().getDrawable(R.drawable.nav_ic_add_property);
                break;
            case "Residential Flat":
                dd=getResources().getDrawable(R.drawable.nav_ic_add_property);
                break;
            case "Residential House":
                dd=getResources().getDrawable(R.drawable.nav_ic_add_property);
                break;
            case "Residential Plot":
                dd=getResources().getDrawable(R.drawable.nav_ic_add_property);
                break;
            case "Residential Villa":
                dd=getResources().getDrawable(R.drawable.nav_ic_add_property);
                break;
            case "Skeleton":
                dd=getResources().getDrawable(R.drawable.nav_ic_add_property);
                break;
            case "Studio Apartment":
                dd=getResources().getDrawable(R.drawable.nav_ic_add_property);
                break;
            case "Warehouse\\/ Godown":
                dd=getResources().getDrawable(R.drawable.nav_ic_add_property);
                break;
            default:
                dd=getResources().getDrawable(R.drawable.nav_ic_add_property);
                break;
        }
        return dd;
    }*/





   /* @Override
    public void selectedIndices(List<Integer> indices) {
     //   Toast.makeText(context, ""+indices, Toast.LENGTH_SHORT).show();



        ArrayList<String> loc=new ArrayList<>();
        for(int i:indices)
        {

            loc.add(localityidList.get(i));

        }


        Toast.makeText(context, "ind: "+loc.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void selectedStrings(List<String> strings) {
        //sLocalityid=localityidList.get(i);
      //  int i=localitynameList.indexOf(strings);
//        sLocalityid=localityidList.get(i);
  //      Toast.makeText(context, strings.toString()+"\n"+i, Toast.LENGTH_LONG).show();


      *//*  ArrayList<String> loc=new ArrayList<>();
        for(String i:strings)
        {
            if(i.equals("Select Locality"))
            {
                strings.remove("Select Locality");

            }
         loc.add(i);
        }
auto_localities.setSelection(loc);*//*
        Toast.makeText(context, "st: "+strings.toString(), Toast.LENGTH_SHORT).show();

    }*/
}
