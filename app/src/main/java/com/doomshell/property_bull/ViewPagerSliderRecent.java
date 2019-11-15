package com.doomshell.property_bull;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doomshell.property_bull.adapter.RecentFloorAdaptor;
import com.doomshell.property_bull.helper.ImageViewTouchViewPager;

import java.util.ArrayList;

/**
 * Created by Vipin on 6/27/2017.
 */

public class ViewPagerSliderRecent extends Fragment {
    String list;
    int position;
    ArrayList<String> listdata;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle bundle)
    {
        View view =inflater.inflate(R.layout.recntfloorplanslider,group,false);
        bundle=getArguments();
        getActivity().setTitle("Floor Plan");
        ImageViewTouchViewPager viewPager = (ImageViewTouchViewPager)view.findViewById(R.id.viewPager);
        list=bundle.getString("pid_list");
        listdata=new ArrayList<>();
        position=bundle.getInt("currentpos");

        String[] images=list.split("\\,");
        for(int i=0;i<images.length;i++)
        {
            listdata.add(images[0]);
        }
        RecentFloorAdaptor myCustomPagerAdapter = new RecentFloorAdaptor((AppCompatActivity)getActivity(),getActivity(), listdata);
        viewPager.setAdapter(myCustomPagerAdapter);
        return view;
    }
}
