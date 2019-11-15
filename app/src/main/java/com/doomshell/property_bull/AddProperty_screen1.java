package com.doomshell.property_bull;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class AddProperty_screen1 extends Fragment implements View.OnClickListener{

    Context context;
    View convertview;
    RadioGroup option1,option2;
    RadioButton rb1,rb2;
    Button next;
    DisplayMetrics displayMetrics;
    double screenHeight;
    double screenWidth;
    String sop1,sop2;
    int checkid1,checkid2;
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
        screentitle.setText("Add Property");

     //   Toast.makeText(context, ""+ SharedPrefManager.getInstance(context).getuser_details("city_id"), Toast.LENGTH_SHORT).show();

        convertview= inflater.inflate(R.layout.fragment_buy_sell_rent, container, false);
        displayMetrics=context.getResources().getDisplayMetrics();
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        int lwi= (int) (screenWidth*0.40);
        next=(Button)convertview.findViewById(R.id.add_sc1_next);
        option1=(RadioGroup)convertview.findViewById(R.id.rg_postSC1_option1);
        option2=(RadioGroup)convertview.findViewById(R.id.rg_postSC1_option2);


        next.getLayoutParams().width= lwi;
        next.requestLayout();
        next.setOnClickListener(this);

        return convertview;
    }

    @Override
    public void onClick(View v) {
        if(v==next)
        {

            if(option1.getCheckedRadioButtonId()==-1)
            {
                sop1="";
            }else {

                checkid1=option1.getCheckedRadioButtonId();
                rb1=(RadioButton)convertview.findViewById(checkid1);
                sop1=rb1.getText().toString().toUpperCase();

            }

            if(option2.getCheckedRadioButtonId()==-1)
            {
                sop2="";
            }else {

                checkid2=option2.getCheckedRadioButtonId();
                rb2=(RadioButton)convertview.findViewById(checkid2);
                sop2=rb2.getText().toString().toUpperCase();

            }


            if(!sop1.equals("") && !sop2.equals("")) {
                Addproperty_screen_Location addProperty_screen2 = new Addproperty_screen_Location();
                Bundle bundle = new Bundle();
                bundle.putString("option1", sop1);
                bundle.putString("option2", sop2);
                addProperty_screen2.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, addProperty_screen2);
                fragmentTransaction.addToBackStack(addProperty_screen2.getClass().toString());
                fragmentTransaction.commit();
            }else {
                Toast.makeText(context,"Please Choose Options",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
