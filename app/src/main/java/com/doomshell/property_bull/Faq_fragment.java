package com.doomshell.property_bull;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.doomshell.property_bull.adapter.AdapterHelp;
import com.doomshell.property_bull.helper.CustomProgressDialog;
import com.doomshell.property_bull.helper.Serverapi;
import com.doomshell.property_bull.model.ItemHelp;

import java.util.ArrayList;

import retrofit.RestAdapter;

public class Faq_fragment extends Fragment {
    View convertview;
    Context context;
    TextView faqtxt;
    WebView web_faq;
    Dialog contactdialog;
    ProgressBar bar;
    String Baseurl;
    RestAdapter restAdapter = null;
    Serverapi serverapi;
    ArrayList<String> idlist = new ArrayList<>();
    ArrayList<String> contentlist = new ArrayList<>();
    AppCompatActivity appCompatActivity;
    ActionBar actionBar;
    CustomProgressDialog dialog;
    ProgressDialog pd;
    private RecyclerView rvHelp;
    private AdapterHelp adapterHelp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        getActivity().getWindow().requestFeature(Window.FEATURE_PROGRESS);

        convertview = inflater.inflate(R.layout.fragment_faq_fragment, container, false);
        context = getActivity().getApplicationContext();
        rvHelp = convertview.findViewById(R.id.rv_help);

        adapterHelp = new AdapterHelp(getActivity(), getHelp());
        rvHelp.setNestedScrollingEnabled(false);
        rvHelp.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        rvHelp.setLayoutManager(layoutManager);
        rvHelp.setAdapter(adapterHelp);

//        appCompatActivity=(AppCompatActivity) getActivity();
//
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        TextView screentitle = (TextView) toolbar.findViewById(R.id.screentitle);
        screentitle.setText("Help");
        ImageView imageView = (ImageView) toolbar.findViewById(R.id.screenimageview);
        BottomNavigationView bottomnavView = getActivity().findViewById(R.id.bottom_nav_view);
        bottomnavView.setVisibility(View.GONE);
        screentitle.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);


        return convertview;
    }

//    void load_faq()
//    {
//
//        serverapi.faq_fetch(new Callback<Response>() {
//            @Override
//            public void success(Response response, Response response2) {
//                String s = new String(((TypedByteArray) response.getBody()).getBytes());
//                //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
//                try {
//                    JSONObject jsonObject = new JSONObject(s);
//                    int success = jsonObject.getInt("success");
//                    if (success == 1) {
//                        JSONArray products = jsonObject.getJSONArray("output");
//
//                        for (int i = 0; i < products.length(); i++) {
//                            JSONObject c = products.getJSONObject(i);
//
//                            idlist.add(c.getString("id"));
//                            contentlist.add(c.getString("content"));
//
//                        }
//                        dismiss_dialogue();
////                        Toast.makeText(context,""+contentlist,Toast.LENGTH_SHORT).show();
//                        for(int w=0;w<contentlist.size();w++) {
//                            web_faq.loadData(contentlist.get(w).toString(), "text/html", "UTF-8");
//                        }
//
//                    }
//                } catch (Exception e) {
//                    dismiss_dialogue();
//                    Toast.makeText(context,"Check Internet Connection",Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                dismiss_dialogue();
//                Toast.makeText(context,"Check Internet Connection",Toast.LENGTH_SHORT).show();
//                error.printStackTrace();
//            }
//        });
//
//    }



    public ArrayList<ItemHelp> getHelp() {
        ArrayList<ItemHelp> getHelp = new ArrayList<>();
        getHelp.add(new ItemHelp("1. How can I register myself on propertybull.com?", "You can register as an Individual, Agent or as a Builder by following these simple steps:-\n" +
                "On the homepage click on  “REGISTER FREE”.\n" +
                "Fill all fields, and click on  “CREATE ACCOUNT”.\n" +
                "You will receive an Email and SMS notification about your profile creation on propertybull.com."));
        getHelp.add(new ItemHelp("2.  Do indeed to pay to post property listings on your site?", "No, posting your property on propertybull is absolutely FREE."));
        getHelp.add(new ItemHelp("3. How to post my property on propertybull.com?", "To begin with you must be a registered member of propertybull.com, once you are a registered member you can post your property. To post your property on our site, please follow these simple steps:-\n" +
                "Login to your propertybull.com account.\n" +
                "Go to user panel and click on “MY PROPERTIES”.\n" +
                "Enter all the details of your property in the search form and click on “ADD PROPERTY”.\n" +
                "You can also add property by clicking on BUY, SELL OR RENT found on Home Page. After you add your details, it will be screened for validation. After your property listing is validated by admin, it will be visible to all Propertybull.com users."));
        getHelp.add(new ItemHelp("4.  How long is my Property listing valid?", "Your property will be valid and visible for infinite period of time. You can update or remove it as per your need."));
        getHelp.add(new ItemHelp("5. Can I find property posted by me under search results?", "Yes definitely, after you have posted your property, it will be screened and will start appearing in the search results after validation by admin within 24 hours."));

        getHelp.add(new ItemHelp("6. After I have posted my property, how will I know if someone is interested in my property?", "Following are the three easy mediums to check the number of responses against each of your property listings:-\n" +
                "E-MAIL - Each time a prospective property buyer is interested in your property, you will be notified through an e-mail.\n" +
                "SMS - You will also be sent a SMS each time a prospective buyer is interested in your property.\n" +
                "USER PANEL - In addition, all the contacts received will be recorded and stored in your user panel. You can see the option of contacted properties also in “MY PROPERTIES”\n"));
        getHelp.add(new ItemHelp("7. What would be a safe and sound approach to buy property?", "Do your complete research on the web and physical survey of the projects. Invest in projects which are at least 25-30 per cent complete as this will be comfortable in terms of approvals.\n Brokers may sometime offer better rates than the developer's sales team. Bank approved projects are preferred since they give comfort in terms of the approvals."));
        getHelp.add(new ItemHelp("8. How can i see my shortlisted property?", "Go to user panel click on “My Properties”, there you see an option of”Shortlisted Properties”. There is also an option of shortlist on Home Page."));
        getHelp.add(new ItemHelp("9. Can I get updates on property without registration on Propertybull.com?", "If you want daily updates on property and newsletters you can simply click on “Alert” option on Home Page and fill up your details.\n You can subscribe for FREE for newsletter option found on Homepage"));
        getHelp.add(new ItemHelp("10. I want to sell my property. What are the documents a buyer would need from me?", "A buyer could ask you for the original Sale Deed, Title Deed, relevant tax receipts and Encumbrance Certificate."));

        return getHelp;
    }
}
