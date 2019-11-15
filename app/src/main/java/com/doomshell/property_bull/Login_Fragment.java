package com.doomshell.property_bull;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.doomshell.property_bull.helper.CustomProgressDialog;
import com.doomshell.property_bull.helper.Serverapi;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class Login_Fragment extends Fragment implements View.OnClickListener{
    EditText mob;
    Button loginbtn;
    View convertview;
    Context context;
    String smobilenumber,sotp,Baseurl;
    Boolean isphone,isotp;
    RestAdapter restAdapter=null;
    Serverapi serverapi;
    CustomProgressDialog dialog;
 //  MyCustomProgress_dialogue myCustomProgress_dialogue=null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        convertview =inflater.inflate(R.layout.fragment_login_, container, false);
        context=getActivity().getApplicationContext();

        Baseurl=getActivity().getResources().getString(R.string.myurl);
        OkHttpClient okHttpClient=new OkHttpClient();
        okHttpClient.setReadTimeout(5, TimeUnit.MINUTES.MINUTES);
        okHttpClient.setReadTimeout(5,TimeUnit.MINUTES.MINUTES);

        restAdapter=new RestAdapter.Builder().setEndpoint(Baseurl).setClient(new OkClient(okHttpClient)).build();
        serverapi=restAdapter.create(Serverapi.class);

        mob=(EditText)convertview.findViewById(R.id.login_mobile);
        loginbtn=(Button)convertview.findViewById(R.id.Login_btn);
        loginbtn.setOnClickListener(this);

        mob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!mob.getText().toString().startsWith("7") &&
                        !mob.getText().toString().startsWith("8") &&
                        !mob.getText().toString().startsWith("9")) {
                    mob.setError("Enter a valid mobile number");
                    isphone = false;
                } else if (mob.getText().toString().trim().length() < 10) {
                    mob.setError("Enter a valid mobile number");
                    isphone = false;

                } else if (mob.getText().toString().trim().length() > 10) {
                    mob.setError("Enter a valid mobile number");
                    isphone = false;
                } else {

                    isphone = true;

                    smobilenumber=mob.getText().toString();
                    final AlertDialog.Builder alertbBuilder = new AlertDialog.Builder(getActivity());
                    alertbBuilder.setTitle("Confirm " + smobilenumber);

                    alertbBuilder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alertbBuilder.setPositiveButton("Send Otp", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            checklogin();
                        }
                    });

                    alertbBuilder.show();

                }
            }
        });



        return convertview;
    }

    @Override
    public void onClick(View v) {

        if(v==loginbtn)
        {
            smobilenumber=mob.getText().toString();
            if (smobilenumber==null || smobilenumber.equals(""))
            {
                isphone=false;
            }

            if (isphone )
            {

                final AlertDialog.Builder alertbBuilder = new AlertDialog.Builder(getActivity());
                alertbBuilder.setTitle("Confirm " + smobilenumber);

                alertbBuilder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertbBuilder.setPositiveButton("Send Otp", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        checklogin();

                    }
                });

                alertbBuilder.show();

            }
        }
    }

    void checklogin()
    {
      show_dialogue();
        serverapi.loginuser(smobilenumber, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
               // myCustomProgress_dialogue.dismiss_dialogue();
                String s=new String(((TypedByteArray) response.getBody()).getBytes());
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    int success=jsonObject.getInt("success");
                    if(success==1)
                    {
                        dismiss_dialogue();

                        boolean loginintent = true;
                        Intent intent=new Intent(getActivity().getApplicationContext(),Otp_verify.class);
                        intent.putExtra("loginintent",loginintent);
                        intent.putExtra("umobile",smobilenumber);
                        getActivity().startActivity(intent);
                        getActivity().finish();
                    }else{
                        dismiss_dialogue();

                        Toast.makeText(context,"User not registered",Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                   // myCustomProgress_dialogue.dismiss_dialogue();
                    dismiss_dialogue();
                    e.printStackTrace();
                }

            }

            @Override
            public void failure(RetrofitError error) {
             //   myCustomProgress_dialogue.dismiss_dialogue();
                dismiss_dialogue();
                Toast.makeText(context,"unable to login right now",Toast.LENGTH_SHORT).show();
                Log.d("retroerror",""+error.getCause());
            }
        });
    }
    public void show_dialogue()
    {

        dialog=new CustomProgressDialog(getActivity());
    }

    public void dismiss_dialogue()
    {
        dialog.hide();
    }
}
