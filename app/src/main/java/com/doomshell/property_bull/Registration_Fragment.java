package com.doomshell.property_bull;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.doomshell.property_bull.helper.CustomProgressDialog;
import com.doomshell.property_bull.helper.Serverapi;
import com.squareup.okhttp.OkHttpClient;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class Registration_Fragment extends Fragment implements View.OnClickListener {

    EditText name, lname, email, mobile, landline;
    AutoCompleteTextView reg_city;
    TextView tnc_text;
    CheckBox reg_accept_checkbox;
    Button createbtn;
    View convertview;
    Context context;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    boolean isname, islastname, ismail, isphone, istnc_accept, iscity;
    String sname, slastname, semail, smobile, slandline, scity, cid;
    String Baseurl;
    RestAdapter restAdapter = null;
    Serverapi serverapi;
    CustomProgressDialog dialog;
    final public static int READ_PHONE_CONTACT = 110;
    TextInputLayout placelayout;
    ArrayList<String> citynameList = new ArrayList<>();
    ArrayList<String> cityidList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        convertview = inflater.inflate(R.layout.fragment_registration, container, false);
        context = getActivity().getApplicationContext();

        Baseurl = getActivity().getResources().getString(R.string.myurl);


        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(5, TimeUnit.MINUTES.MINUTES);
        okHttpClient.setReadTimeout(5, TimeUnit.MINUTES.MINUTES);

        restAdapter = new RestAdapter.Builder().setEndpoint(Baseurl).setClient(new OkClient(okHttpClient)).build();
        serverapi = restAdapter.create(Serverapi.class);

        /*myCustomProgress_dialogue=new MyCustomProgress_dialogue(getActivity(), Color.BLUE);*/

        name = (EditText) convertview.findViewById(R.id.reg_firstname);
        lname = (EditText) convertview.findViewById(R.id.reg_lastname);
        email = (EditText) convertview.findViewById(R.id.reg_email);
        mobile = (EditText) convertview.findViewById(R.id.regmobile);
        landline = (EditText) convertview.findViewById(R.id.reg_landline);
        reg_city = (AutoCompleteTextView) convertview.findViewById(R.id.reg_city);
        //  placelayout = (TextInputLayout) convertview.findViewById(R.id.reg_placelayout);
        //  locationimg = (ImageView) convertview.findViewById(R.id.reg_ic_locationimg);
      /*  password=(EditText)convertview.findViewById(R.id.reg_password);
        confirmpassword=(EditText)convertview.findViewById(R.id.reg_confirm_password);*/
        createbtn = (Button) convertview.findViewById(R.id.reg_create_account_btn);
        tnc_text = (TextView) convertview.findViewById(R.id.tnc_text);
        reg_accept_checkbox = (CheckBox) convertview.findViewById(R.id.reg_accept_checkbox);

        createbtn.setOnClickListener(this);
        tnc_text.setOnClickListener(this);
        // locationimg.setOnClickListener(this);


        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean b = validate(email.getText().toString());
                if (!b) {
                    email.setError("Email address is not valid");
                    ismail = false;
                } else {
                    ismail = true;
                }
            }
        });

        mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!mobile.getText().toString().startsWith("7") &&
                        !mobile.getText().toString().startsWith("8") &&
                        !mobile.getText().toString().startsWith("9")) {
                    mobile.setError("Enter a valid mobile number");
                    isphone = false;
                } else if (mobile.getText().toString().trim().length() < 10) {
                    mobile.setError("Enter a valid mobile number");
                    isphone = false;

                } else if (mobile.getText().toString().trim().length() > 10) {
                    mobile.setError("Enter a valid mobile number");
                    isphone = false;
                } else {

                    isphone = true;
                }


            }
        });


     /*   if (citynameList.isEmpty()) {
            //  myCustomProgress_dialogue.show_dialogue();
            show_dialogue();
            load_city();
        }*/

        reg_city.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (reg_city.hasFocus()) {

                    if (citynameList.isEmpty()) {
                        // myCustomProgress_dialogue.show_dialogue();
                        show_dialogue();
                        load_city();
                    } else {
                        String pn[] = new String[citynameList.size()];
                        pn = citynameList.toArray(pn);

                        // myCustomProgress_dialogue.show_dialogue();
                        show_dialogue();
                        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, pn) {
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

                        };
                        reg_city.setThreshold(1);
                        reg_city.setAdapter(stringArrayAdapter);
                        // myCustomProgress_dialogue.dismiss_dialogue();
                        dismiss_dialogue();
                    }
//
                } else {
                    String pn[] = new String[citynameList.size()];
                    pn = citynameList.toArray(pn);

                    //  myCustomProgress_dialogue.show_dialogue();
                    show_dialogue();
                    ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, pn) {
                        public View getView(int position, View convertView, ViewGroup parent) {

                            View v = super.getView(position, convertView, parent);

                            ((TextView) v).setGravity(Gravity.LEFT);
                            ((TextView) v).setGravity(Gravity.CENTER_VERTICAL);
                            ((TextView) v).setTextColor(Color.BLACK);

                            return v;

                        }

                        public View getDropDownView(int position, View convertView, ViewGroup parent) {

                            View v = super.getDropDownView(position, convertView, parent);

                            //     ((TextView) v).setPadding(5,5,0,0);
                            ((TextView) v).setGravity(Gravity.LEFT);
                            ((TextView) v).setGravity(Gravity.CENTER_VERTICAL);
                            ((TextView) v).setTextColor(Color.BLACK);


                            return v;

                        }

                    };
                    reg_city.setThreshold(1);
                    reg_city.setAdapter(stringArrayAdapter);
                    dismiss_dialogue();
                    //regplace.setEnabled(true);

                }

            }

        });


        return convertview;
    }


    @Override
    public void onClick(View v) {

        if (v == tnc_text) {
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.MyAlertDialogStyle));
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View alerview = inflater.inflate(R.layout.bull_policy_dialogue, null);
            //LinearLayout dialogue_layout=(LinearLayout) alerview.findViewById(R.id.dialogue_layout);
            Button bull_policy_dismiss = (Button) alerview.findViewById(R.id.bull_policy_dismiss);

            builder.setView(alerview);
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();

            bull_policy_dismiss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

        }

        if (v == createbtn) {
            sname = name.getText().toString();
            slastname = lname.getText().toString();
            semail = email.getText().toString();
            smobile = mobile.getText().toString();
            slandline = landline.getText().toString();
            scity = reg_city.getText().toString();


            if (sname == null || sname.equals("")) {
                name.setError("Enter name");
                isname = false;
            } else {
                isname = true;
            }

            if (slastname == null || slastname.equals("")) {
                lname.setError("Enter name");
                islastname = false;
            } else {
                islastname = true;
            }
            if (semail == null || semail.equals("")) {
                email.setError("Enter email");
                ismail = false;
            }

            if (smobile == null || smobile.equals("")) {
                mobile.setError("Enter mobilenumber");
                isphone = false;
            }

            if (scity == null || scity.equals("")) {
                reg_city.setError("Please choose city");
                iscity = false;
            } else {
                int pos = citynameList.indexOf(scity);

                try {
                    if (cityidList.contains(cityidList.get(pos))) {
                        cid = cityidList.get(pos);
                        iscity = true;
                    } else {
                        iscity = false;
                        Toast.makeText(context, "City not found", Toast.LENGTH_SHORT).show();
                    }

                } catch (ArrayIndexOutOfBoundsException e) {
                    iscity = false;
                    Toast.makeText(context, "City not found", Toast.LENGTH_SHORT).show();
                }


            }
            if (cid == null || cid.equals("")) {
                iscity = false;
                reg_city.setError("Please choose city.");
            } else {
                iscity = true;
            }

            if (reg_accept_checkbox.isChecked()) {
                istnc_accept = true;
            } else {
                istnc_accept = false;
            }

            if (isname && islastname && ismail && isphone && iscity && istnc_accept == false) {
                Toast.makeText(getActivity().getApplicationContext(), "Please accept Term and conditions", Toast.LENGTH_SHORT).show();
            }

            if (isname && islastname && ismail && isphone && istnc_accept && iscity) {
                //    Toast.makeText(context, "dfsdafasf", Toast.LENGTH_SHORT).show();


                int myAPI = Build.VERSION.SDK_INT;
                if (myAPI >= 23) {
                    int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS);

                    if (result == PackageManager.PERMISSION_GRANTED) {
                        register_retro();
                    } else {

                        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_CONTACTS)) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS},
                                    READ_PHONE_CONTACT);

                            //     Toast.makeText(activity, "Microphone permission needed for recording. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
                        } else {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS},
                                    READ_PHONE_CONTACT);

                        }


                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    View alerview = inflater.inflate(R.layout.policy_dialogue, null);
                    //LinearLayout dialogue_layout=(LinearLayout) alerview.findViewById(R.id.dialogue_layout);
                    Button accept = (Button) alerview.findViewById(R.id.policy_accepted);
                    Button decline = (Button) alerview.findViewById(R.id.policy_decline);


                    builder.setView(alerview);
                    final AlertDialog alertDialog = builder.create();
                    alertDialog.show();


                    accept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            register_retro();
                            alertDialog.dismiss();

                        }
                    });

                    decline.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getActivity().getApplicationContext(), "Please accept permission to access app", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }
                    });

                }


                //   Register_pojo registerPojo=new Register_pojo(sname,slastname,semail,slandline,smobile,"anuj_try","9024383238");


            } else {
                Toast.makeText(context, "Check fields", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public void register_retro() {

        // myCustomProgress_dialogue.show_dialogue();
        show_dialogue();

        ArrayList<String> list_name = new ArrayList<>();
        ArrayList<String> list_phone = new ArrayList<>();

        Cursor cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            list_name.add(name);
            list_phone.add(phoneNumber);
        }

        //   final MyRegister_Pojoclass myPojoclass=new MyRegister_Pojoclass(sname, slastname, semail, slandline, smobile, list_name.toString(), list_phone.toString());
        serverapi.register_user(sname, slastname, semail, slandline, smobile, "27", list_name.toString(), list_phone.toString(),
                new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {

                        //  myCustomProgress_dialogue.dismiss_dialogue();
                        dismiss_dialogue();

                        // TypedInput body = response.getBody();
                        String s = new String(((TypedByteArray) response.getBody()).getBytes());

                        Log.d("ok", s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int success = jsonObject.getInt("success");
                            //       String msg=jsonObject.getString("message");
                            if (success == 1) {
                                String userid = jsonObject.getString("user_id");
                                String mobile = jsonObject.getString("mobile");
                                boolean loginintent = false;

                                Intent intent = new Intent(getActivity().getApplicationContext(), Otp_verify.class);
                                intent.putExtra("loginintent", loginintent);
                                intent.putExtra("uid", userid);
                                intent.putExtra("umobile", mobile);
                                getActivity().startActivity(intent);
                                getActivity().finish();
                            }
                            if (success == 2) {
                                String message = jsonObject.getString("message");
                                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                            }
                            if (success == 0) {
                                String message = jsonObject.getString("message");
                                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Something thig went wrong", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        // myCustomProgress_dialogue.dismiss_dialogue();
                        dismiss_dialogue();
                        Toast.makeText(context, "Unable to register right now", Toast.LENGTH_LONG).show();
                        Log.d("error reg", error.toString());
                 /*       Toast.makeText(context,"\n"+error.getBody().toString()+"\n"+error.toString(),Toast.LENGTH_LONG).show();
                        Log.d("myerror","\n"+error.getBody().toString()+"\n"+error.toString());*/
                    }
                });
    }


    void load_city() {
        serverapi.citylist(new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                String s = new String(((TypedByteArray) response.getBody()).getBytes());
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray cityarray = jsonObject.getJSONArray("output");

                    for (int i = 0; i < cityarray.length(); i++) {
                        JSONObject c = cityarray.getJSONObject(i);
                        String cityid = c.getString("id");
                        String cityname = c.getString("name");

                        cityidList.add(cityid);
                        citynameList.add(cityname);
                    }
                    //   myCustomProgress_dialogue.dismiss_dialogue();
                    dismiss_dialogue();

                } catch (Exception e) {
                    //  myCustomProgress_dialogue.dismiss_dialogue();
                    dismiss_dialogue();
                    e.printStackTrace();
                    // Toast.makeText(context,""+e,Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void failure(RetrofitError error) {
                //  myCustomProgress_dialogue.dismiss_dialogue();
                dismiss_dialogue();
                // Toast.makeText(context,""+error,Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == READ_PHONE_CONTACT) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0) {

                //       boolean aaeptcon=grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    register_retro();
                    Toast.makeText(getActivity().getApplicationContext(), "Thanks for Allow permission \n We Assure you that we dont Use or distribute the Data of your to Any Third Party",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    Toast.makeText(getActivity().getApplicationContext(), "permission required. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();


                }
                // permission was granted, yay! Do the
                // contacts-related task you need to do.

            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Toast.makeText(getActivity().getApplicationContext(), "permission both required. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();


            }


        }
    }

    public void show_dialogue() {

        dialog = new CustomProgressDialog(getActivity());
    }

    public void dismiss_dialogue() {
        dialog.hide();
    }

}