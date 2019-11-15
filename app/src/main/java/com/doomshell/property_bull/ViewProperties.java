package com.doomshell.property_bull;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class ViewProperties extends Fragment{

    View convertview;
    Context context;
   ImageView gallery_projectmonthIMG,gallery_propertonsaleIMG;
    AppCompatActivity appCompatActivity;
    ActionBar actionBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context=getActivity().getApplicationContext();
        convertview=inflater.inflate(R.layout.fragment_view_properties, container, false);



        appCompatActivity = (AppCompatActivity) getActivity();


        Toolbar toolbar=(Toolbar)getActivity().findViewById(R.id.toolbar);
        TextView screentitle=(TextView)toolbar.findViewById(R.id.screentitle);
        screentitle.setText("Properties");





        gallery_projectmonthIMG=(ImageView)convertview.findViewById(R.id.gallery_projectmonthIMG);
        gallery_propertonsaleIMG=(ImageView)convertview.findViewById(R.id.gallery_propertonsaleIMG);

        gallery_projectmonthIMG.setOnClickListener(new View.OnClickListener() {
                                                       @Override
                                                       public void onClick(View v) {
                                                           Projectofmonth projecthome=new Projectofmonth();
                                                           FragmentTransaction devicetrans=getActivity().getSupportFragmentManager().beginTransaction();
                                                           devicetrans.replace(R.id.frame_container,projecthome);
                                                           devicetrans.addToBackStack(projecthome.getClass().toString());
                                                           devicetrans.commit();

                                                       }
                                                   });

        gallery_propertonsaleIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Projects_Gallery projects_gallery=new Projects_Gallery();
                FragmentTransaction devicetrans=getActivity().getSupportFragmentManager().beginTransaction();
                devicetrans.replace(R.id.frame_container,projects_gallery);
               devicetrans.addToBackStack(projects_gallery.getClass().toString());
                devicetrans.commit();

            }
        });


        return convertview;
    }



}
