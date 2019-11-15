package com.doomshell.property_bull;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.doomshell.property_bull.adapter.GridViewAdapter;

import java.util.ArrayList;


public class AddProperty_screen2 extends Fragment implements View.OnClickListener{
    Context context;
    View convertview;
    GridView gridView;
    GridViewAdapter gridViewAdapter;
    ArrayList<String> proname=new ArrayList<>();
    ArrayList<Integer> Image=new ArrayList<>();
    String option1,option2,grid_p_name;
    DisplayMetrics displayMetrics;
    double screenHeight;
    double screenWidth;
    Button add_sc2_next;
    String proid;
    String num_rooms,bathroom,totalFloor,propertonFloor,Flooring,propertyface,ageofproperty;
    int previousposition=-1;
    TextView pre_text,current_text;
    SparseBooleanArray sparseBooleanArray=new SparseBooleanArray();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context=getActivity().getApplicationContext();
        convertview= inflater.inflate(R.layout.fragment_add_property_screen2, container, false);
        displayMetrics=context.getResources().getDisplayMetrics();
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        int lwi= (int) (screenWidth*0.40);
        gridView=(GridView)convertview.findViewById(R.id.add_ptype_grid);
        add_sc2_next=(Button)convertview.findViewById(R.id.add_sc2_next);
        add_sc2_next.getLayoutParams().width= lwi;
        add_sc2_next.requestLayout();
        add_sc2_next.setOnClickListener(this);

        savedInstanceState=getArguments();
        option1=savedInstanceState.getString("option1");
        option2=savedInstanceState.getString("option2");


        if(option2.equals("RESIDENTIAL"))
        {
            proname.add("Housing flat");
            Image.add(R.drawable.ic_action_backarrow);
            proname.add("Basement");
            Image.add(R.drawable.p_wn_basement);
            proname.add("Floor Apartment");
            Image.add(R.drawable.p_wn_floor_apartment);
            proname.add("Business Center");
            Image.add(R.drawable.ic_action_backarrow);
            proname.add("Duplex");
            Image.add(R.drawable.p_wn_duplex);
            proname.add("Farm House");
            Image.add(R.drawable.p_wn_farm_house);
            proname.add("Hostel");
            Image.add(R.drawable.p_wn_hostel);
            proname.add("Hotel");
            Image.add(R.drawable.ic_action_backarrow);
            proname.add("Paying Guest");
            Image.add(R.drawable.p_wn_paying_guest);
            proname.add("Penthouse");
            Image.add(R.drawable.p_wn_pent_house);
            proname.add("Residential Farm house");
            Image.add(R.drawable.p_wn_resendential_farm_house);
            proname.add("Residential Flat");
            Image.add(R.drawable.p_wn_flat);
            proname.add("Residential House");
            Image.add(R.drawable.ic_action_backarrow);
            proname.add("Residential Plot");
            Image.add(R.drawable.p_wn_plot);
            proname.add("Residential Villa");
            Image.add(R.drawable.ic_action_backarrow);
            proname.add("Skeleton");
            Image.add(R.drawable.ic_action_backarrow);
            proname.add("Studio Apartment");
            Image.add(R.drawable.ic_action_backarrow);
            proname.add("Warehouse / Godown");
            Image.add(R.drawable.p_wn_warehouse_godown);
        }else {
            proname.add("Commercial Land");
            Image.add(R.drawable.p_wn_commercialland);
            proname.add("Commercial Office");
            Image.add(R.drawable.p_wn_commercial_office_space);
            proname.add("Commercial Shop");
            Image.add(R.drawable.p_wn_commercial_shop);
            proname.add("Commercial Showroom");
            Image.add(R.drawable.p_wn_commercial_showroom);
            proname.add("Industrial Building");
            Image.add(R.drawable.p_wn_industrial_building);
            proname.add("Industrial Land");
            Image.add(R.drawable.p_wn_industrial_land);

        }



        gridViewAdapter=new GridViewAdapter(context,proname,Image);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            gridView.setScrollBarSize(10);
        }
     //   gridViewAdapter.notifyDataSetChanged();
        gridView.setAdapter(gridViewAdapter);

 gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             //   Toast.makeText(context,""+gridView.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
                String selectedItem = parent.getItemAtPosition(position).toString();
           //   view=(ViewGroup)gridView.getChildAt(position);
              // current_text=(TextView)view.findViewById(R.id.add_pro_text);

                //LinearLayout backposition = (LinearLayout) gridView.getChildAt(previousSelectedPosition);



                if (previousposition != -1)
                {

                   ViewGroup myview=(ViewGroup)gridView.getChildAt(position);
                    current_text=(TextView)myview.findViewById(R.id.add_pro_text);

                    ViewGroup cg=(ViewGroup)gridView.getChildAt(previousposition);
                    pre_text=(TextView)cg.findViewById(R.id.add_pro_text);

                    pre_text.setBackgroundColor(Color.WHITE);
                    current_text.setBackgroundColor(getResources().getColor(R.color.button_color));
       //             sparseBooleanArray.put(position,true);
                }else {
        //            sparseBooleanArray.put(position,true);
                    ViewGroup myview=(ViewGroup)gridView.getChildAt(position);
                    current_text=(TextView)myview.findViewById(R.id.add_pro_text);
                    current_text.setBackgroundColor(getResources().getColor(R.color.button_color));
                }

                grid_p_name=current_text.getText().toString();
                previousposition=position;

             //  Toast.makeText(context,""+grid_p_name,Toast.LENGTH_SHORT).show();
            }
        });


        return convertview;
    }


    @Override
    public void onClick(View v) {

        if (v==add_sc2_next) {
            if (grid_p_name == null || grid_p_name.equals("")) {
                Toast.makeText(context, "Please select Property Type", Toast.LENGTH_SHORT).show();
            } else {
                proid = get_ptype_id(grid_p_name);
             //   Toast.makeText(context, "" + proid, Toast.LENGTH_SHORT).show();

                if (proid.equals("321") || proid.equals("350") || proid.equals("322") || proid.equals("336") || proid.equals("339") || proid.equals("347")
                        || proid.equals("327") || proid.equals("338")) {

                    Addproperty_extraDetails addproperty_extraDetails=new Addproperty_extraDetails();
                    Bundle bundle = new Bundle();

                    bundle.putString("option1", option1);
                    bundle.putString("option2", option2);
                    bundle.putString("proid", proid);
                    addproperty_extraDetails.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_container, addproperty_extraDetails);
                    fragmentTransaction.addToBackStack(addproperty_extraDetails.getClass().toString());
                    proname.clear();
                    Image.clear();
                    fragmentTransaction.commit();


                } else {

                    num_rooms = "";
                    bathroom = "";
                    totalFloor = "";
                    propertonFloor = "";
                    Flooring = "";
                    propertyface = "";
                    ageofproperty = "";

                    Addproperty_screen_Location addproperty_screen_location = new Addproperty_screen_Location();
                    Bundle bundle = new Bundle();
                    bundle.putString("num_rooms", num_rooms);
                    bundle.putString("bathroom", bathroom);
                    bundle.putString("totalFloor", totalFloor);
                    bundle.putString("propertonFloor", propertonFloor);
                    bundle.putString("Flooring", Flooring);
                    bundle.putString("propertyface", propertyface);
                    bundle.putString("ageofproperty", ageofproperty);
                    bundle.putString("option1", option1);
                    bundle.putString("option2", option2);
                    bundle.putString("proid", proid);
                    addproperty_screen_location.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_container, addproperty_screen_location);
                    fragmentTransaction.addToBackStack(addproperty_screen_location.getClass().toString());
                    proname.clear();
                    Image.clear();
                    fragmentTransaction.commit();

                }

            }
        }

    }



    String get_ptype_id(String rid)
    {
        String id;
        switch (rid)
        {
            case "Housing flat":
                id= "343";
                break;
            case "Basement":
                id= "346";
                break;
            case "Floor Apartment":
                id= "322";
                break;
            case "Business Center":
                id= "328";
                break;
            case "Commercial Land":
                id= "324";
                break;
            case "Commercial Office":
                id= "325";
                break;
            case "Commercial Shop":
                id= "326";
                break;
            case "Commercial Showroom":
                id= "327";
                break;
            case "Duplex":
                id= "345";
                break;
            case "Farm House":
                id= "335";
                break;
            case "Hostel":
                id= "339";
                break;
            case "Hotel":
                id= "338";
                break;
            case "Industrial Building":
                id= "331";
                break;
            case "Industrial Land":
                id= "330";
                break;
            case "Paying Guest":
                id= "336";
                break;
            case "Penthouse":
                id= "341";
                break;
            case "Residential Farm house":
                id= "350";
                break;
            case "Residential Flat":
                id= "321";
                break;
            case "Residential House":
                id= "320";
                break;
            case "Residential Plot":
                id= "319";
                break;
            case "Residential Villa":
                id= "342";
                break;
            case "Skeleton":
                id= "340";
                break;
            case "Studio Apartment":
                id= "347";
                break;
            case "Warehouse / Godown":
                id= "329";
                break;
            default:
                id= "";
                break;
        }
        return id;
    }
}
