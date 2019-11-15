package com.doomshell.property_bull.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doomshell.property_bull.R;

import java.util.ArrayList;

/**
 * Created by Anuj on 3/8/2017.
 */

public class Custom_Page_adapter extends PagerAdapter {
    Context mContext;
    Activity activity;
    LayoutInflater mLayoutInflater;
    ArrayList<String> pid_list = new ArrayList<>();
    ArrayList<String> pname_list = new ArrayList<>();
    ArrayList<String> ppostedby_list = new ArrayList<>();
    ArrayList<String> pmobile_list = new ArrayList<>();
    ArrayList<String> ptype_list = new ArrayList<>();
    ArrayList<String> pbedroom_list = new ArrayList<>();
    ArrayList<String> ptotalarea_list = new ArrayList<>();
    ArrayList<String> ptotalprice_list = new ArrayList<>();
    ArrayList<String> ppriceperunit_list = new ArrayList<>();
    ArrayList<String> pimage_list = new ArrayList<>();
    ArrayList<String> localitylist = new ArrayList<>();
    ArrayList<String> addresslist = new ArrayList<>();
    ArrayList<String> landmarklist = new ArrayList<>();
    int currentpos;
    String mprice;
    ViewPager viewPager;
    AlertDialog contact_alert;

    TextView price,discription,Builtup_Area,Postedby,PricePerUnit,Bedroom,Society;
    ImageView detail_callbtn;
    Button viewcontact,sendmsg,next,previous;
    public static Intent callIntent;
    public static int CallState=111;

    public Custom_Page_adapter(Context mContext, ArrayList<String> pid_list,
                               ArrayList<String> pname_list,
                               ArrayList<String> ppostedby_list,
                               ArrayList<String> pmobile_list,
                               ArrayList<String> ptype_list,
                               ArrayList<String> pbedroom_list,
                               ArrayList<String> ptotalarea_list,
                               ArrayList<String> ptotalprice_list,
                               ArrayList<String> ppriceperunit_list,
                               ArrayList<String> pimage_list,
                               ArrayList<String> localitylist,
                               ArrayList<String> addresslist,
                               ArrayList<String> landmarklist,
                               ViewPager viewPager,
                               Activity activity) {
        this.mContext = mContext;
        this.pid_list = pid_list;
        this.pname_list = pname_list;
        this.ppostedby_list = ppostedby_list;
        this.pmobile_list = pmobile_list;
        this.ptype_list = ptype_list;
        this.pbedroom_list = pbedroom_list;
        this.ptotalarea_list = ptotalarea_list;
        this.ptotalprice_list = ptotalprice_list;
        this.ppriceperunit_list = ppriceperunit_list;
        this.localitylist = localitylist;
        this.addresslist = addresslist;
        this.landmarklist = landmarklist;
        this.pimage_list = pimage_list;
        this.viewPager=viewPager;
        this.activity=activity;
        mLayoutInflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return pid_list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.detail_property_layout, container, false);

       //ImageView detail_img=(ImageView)itemView.findViewById(R.id.detail_img);
        price=(TextView)itemView.findViewById(R.id.detail_price);
        discription=(TextView)itemView.findViewById(R.id.detail_description);
        Builtup_Area=(TextView)itemView.findViewById(R.id.detail_builtup);
        Postedby=(TextView)itemView.findViewById(R.id.detail_postedby);
        PricePerUnit=(TextView)itemView.findViewById(R.id.detail_priceper_unit);
        Bedroom=(TextView)itemView.findViewById(R.id.detail_bedroom);
        Society=(TextView)itemView.findViewById(R.id.detail_society);
        detail_callbtn=(ImageView) itemView.findViewById(R.id.detail_callbtn);
        viewcontact=(Button) itemView.findViewById(R.id.detail_viewcontact_btn);
        sendmsg=(Button) itemView.findViewById(R.id.detail_sendmsg_btn);
       // next=(Button) itemView.findViewById(R.id.detail_next);
       // previous=(Button) itemView.findViewById(R.id.detail_previous);
        Button detail_sendmsg_btn=(Button)itemView.findViewById(R.id.detail_sendmsg_btn);
        detail_sendmsg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = pmobile_list.get(position);
                Intent i=new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null));// The number on which you want to send SMS
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addCategory(Intent.CATEGORY_DEFAULT);
                mContext.startActivity(i);
            }
        });



        if(ptotalprice_list.get(position).toString().equals("-") || ptotalprice_list.get(position).toString().equalsIgnoreCase("null")
                || ptotalprice_list.get(position).toString().equals(""))
        {
            mprice="-";


        }else {
            mprice=ptotalprice_list.get(position).toString();
        }

        /*else {
            int p=Integer.parseInt(ptotalprice_list.get(position));
            float f=p/100000;
            mprice=""+f;
        }*/

       // Glide.with(mContext).load(pimage_list.get(position)).placeholder(R.drawable.no_image).into(detail_img);
        price.setText("\u20B9 "+mprice);
        if(pbedroom_list.get(position).equalsIgnoreCase("0") || pbedroom_list.get(position).equalsIgnoreCase("-")) {
            discription.setText(pname_list.get(position) + "\u25CF" + " " + ptype_list.get(position));

        }
        else
        {
            discription.setText("" + pbedroom_list.get(position) + " BHK " + "\u25CF" + " " + pname_list.get(position) + "\u25CF" + " " + ptype_list.get(position));
        }
        //Builtup_Area.setText(""+ptotalarea_list.get(position));
        Builtup_Area.setText( pid_list.get(position).toString());
        Postedby.setText(""+ppostedby_list.get(position));
        PricePerUnit.setText(ptotalarea_list.get(position).toString()+" "+ppriceperunit_list.get(position));
        Bedroom.setText(""+landmarklist.get(position));
        Society.setText(addresslist.get(position).toString());

        detail_callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pmobile_list.get(position) == null || pmobile_list.get(position).equals("")) {
                    Toast.makeText(mContext, "Number not provided", Toast.LENGTH_SHORT).show();
                } else {
                    callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + pmobile_list.get(position)));

                    int myAPI = Build.VERSION.SDK_INT;

                    if (myAPI >= 23) {
                        int result = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE);
                        if (result == PackageManager.PERMISSION_GRANTED) {
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(callIntent);
                        }else {
                            //      Toast.makeText(context, "permission required to make call, Please give permission", Toast.LENGTH_LONG).show();

                            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CALL_PHONE))
                            {
                                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE},
                                        CallState);
                            }else {
                                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE},
                                        CallState);
                            }
                        }

                    }else {
                        mContext.startActivity(callIntent);
                    }
                }
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPage();
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousPage();
            }
        });

        viewcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showdialogue(ppostedby_list.get(position).toString(),pmobile_list.get(position).toString());
            }
        });


        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }


    private void nextPage() {
        int currentPage = viewPager.getCurrentItem();
        int totalPages = viewPager.getAdapter().getCount();

        int nextPage = currentPage+1;
        if (nextPage >= totalPages) {
            // We can't go forward anymore.
            // Loop to the first page. If you don't want looping just
            // return here.
            nextPage = 0;
        }

        viewPager.setCurrentItem(nextPage, true);
    }

    private void previousPage() {
        int currentPage = viewPager.getCurrentItem();
        int totalPages = viewPager.getAdapter().getCount();

        int previousPage = currentPage-1;
        if (previousPage < 0) {
            // We can't go back anymore.
            // Loop to the last page. If you don't want looping just
            // return here.
            previousPage = totalPages - 1;
        }

        viewPager.setCurrentItem(previousPage, true);
    }

    void showdialogue(String name, String contact)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(activity,R.style.myDialog));
        LayoutInflater inflater = activity.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alert_layout_viewcontact, null);
        dialogBuilder.setView(dialogView);

        TextView vcName=(TextView)dialogView.findViewById(R.id.vc_name);
        TextView vcContact=(TextView)dialogView.findViewById(R.id.vc_contact);

        vcName.setText(name);
        vcContact.setText(contact);

       // dialogBuilder.setTitle("Custom dialog");
      //  dialogBuilder.setMessage("Enter text below");

        dialogBuilder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                contact_alert.dismiss();
            }
        });
        contact_alert = dialogBuilder.create();

        contact_alert.show();

    }

}
