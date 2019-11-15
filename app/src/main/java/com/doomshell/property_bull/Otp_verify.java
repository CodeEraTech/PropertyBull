package com.doomshell.property_bull;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.doomshell.property_bull.helper.CustomProgressDialog;
import com.doomshell.property_bull.helper.Serverapi;
import com.doomshell.property_bull.model.SharedPrefManager;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class Otp_verify extends AppCompatActivity implements View.OnClickListener {
    private boolean timer_called = true;
    private int i_timer = 59;
    TextView number, tv_time_otp_verify;
    Button verify, resend;
    EditText otp;
    String snumber, sotp, suserid;
    String Baseurl;
    RestAdapter restAdapter;
    Serverapi serverapi;
    LinearLayout ll_resend_otp_verify;
    //   MyCustomProgress_dialogue myCustomProgress_dialogue;
    SharedPreferences mySharedPreferences;
    boolean loginintent;
    Dialog contactdialog;
    ProgressBar bar;
    CustomProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        contactdialog = new Dialog(Otp_verify.this);
        contactdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        contactdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00ff1919")));
        bar = new ProgressBar(Otp_verify.this, null, android.R.attr.progressBarStyleLarge);
//bar.setProgress()
        bar.getIndeterminateDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY);
        contactdialog.setContentView(bar);
        contactdialog.setCancelable(false);

        number = (TextView) findViewById(R.id.otp_mobilenumber);
        ll_resend_otp_verify = (LinearLayout) findViewById(R.id.ll_resend_otp_verify);
        tv_time_otp_verify = (TextView) findViewById(R.id.tv_time_otp_verify);
        otp = (EditText) findViewById(R.id.otp_edit);
        verify = (Button) findViewById(R.id.verify_otp_btn);
        resend = (Button) findViewById(R.id.resend_otp_btn);

        number.setText(snumber);
        verify.setOnClickListener(this);
        resend.setOnClickListener(this);

        Intent intent = getIntent();
        loginintent = getIntent().getBooleanExtra("loginintent", false);
        if (loginintent == true) {
            snumber = intent.getStringExtra("umobile");
            verify.setText("Login");


        } else {
            suserid = intent.getStringExtra("uid");
            snumber = intent.getStringExtra("umobile");

        }
        setUpOTP();
        number.setText(snumber);

        Baseurl = getResources().getString(R.string.myurl);
        restAdapter = new RestAdapter.Builder().setEndpoint(Baseurl).build();
        serverapi = restAdapter.create(Serverapi.class);


    }

    @Override
    public void onClick(View v) {
        if (v == verify) {
            sotp = otp.getText().toString();

            if (sotp == null || sotp.equals("")) {
                Toast.makeText(getApplicationContext(), "Please enter otp", Toast.LENGTH_SHORT).show();
            } else {

                //      myCustomProgress_dialogue=new MyCustomProgress_dialogue(Otp_verify.this, Color.BLUE);
                //    myCustomProgress_dialogue.show_dialogue();
                show_dialogue();
                //OtpVerify_model otpVerify_model=new OtpVerify_model(snumber,sotp);
                //  snumber="9024383238";
                serverapi.verifyotp(snumber, sotp, new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {

                        // TypedInput body = response.getBody();
                        String s = new String(((TypedByteArray) response.getBody()).getBytes());
                        //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int success = jsonObject.getInt("success");
                            Log.d("Logindata2", jsonObject + "");
                            if (success == 1) {
                                String uid = jsonObject.getString("id");
                                String name = jsonObject.getString("name");
                                String lname = jsonObject.getString("lname");
                                String username = jsonObject.getString("username");
                                String mobile = jsonObject.getString("mobile");
                                String landline = jsonObject.getString("landline");
                                String city_id = jsonObject.getString("city_id");

                                SharedPrefManager.getInstance(getApplicationContext()).saveuser_details("id", uid);
                                SharedPrefManager.getInstance(getApplicationContext()).saveuser_details("name", name);
                                SharedPrefManager.getInstance(getApplicationContext()).saveuser_details("lname", lname);
                                SharedPrefManager.getInstance(getApplicationContext()).saveuser_details("username", username);
                                SharedPrefManager.getInstance(getApplicationContext()).saveuser_details("mobile", mobile);
                                SharedPrefManager.getInstance(getApplicationContext()).saveuser_details("landline", landline);
                                SharedPrefManager.getInstance(getApplicationContext()).saveuser_details("city_id", city_id);

                                SharedPrefManager.getInstance(getApplicationContext()).save_Login_status("login", true);

                                //  myCustomProgress_dialogue.dismiss_dialogue();
                                dismiss_dialogue();
                                //        Toast.makeText(getApplicationContext(),"Welcome :"+SharedPrefManager.getInstance(getApplicationContext()).getuser_details("name"),Toast.LENGTH_SHORT).show();

                                //   Toast.makeText(Otp_verify.this, ""+SharedPrefManager.getInstance(getApplicationContext()).getuser_details("city_id"), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Otp_verify.this, Home_Activity.class);
                                startActivity(intent);
                                finish();

                            } else if (success == 0) {
                                //myCustomProgress_dialogue.dismiss_dialogue();
                                dismiss_dialogue();
                                Toast.makeText(getApplicationContext(), "Wrong OTP", Toast.LENGTH_SHORT).show();
                            } else {
                                // myCustomProgress_dialogue.dismiss_dialogue();
                                dismiss_dialogue();
                                Toast.makeText(getApplicationContext(), "Check Connection", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            //   myCustomProgress_dialogue.dismiss_dialogue();
                            dismiss_dialogue();
                            //  Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        // myCustomProgress_dialogue.dismiss_dialogue();
                        dismiss_dialogue();
                        //    Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                    }
                });

            }


        }

        if (v == resend) {
//         sotp=otp.getText().toString();
            checklogin();
            setUpOTP();
        }
    }

    void checklogin() {
        // myCustomProgress_dialogue=new MyCustomProgress_dialogue(Otp_verify.this, Color.BLUE);
        // myCustomProgress_dialogue.show_dialogue();
        show_dialogue();
        serverapi.loginuser(snumber, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {

                // myCustomProgress_dialogue.dismiss_dialogue();
                dismiss_dialogue();
                String s = new String(((TypedByteArray) response.getBody()).getBytes());
                //        Log.d("ok",s);
                //          Toast.makeText(Otp_verify.this,s,Toast.LENGTH_LONG).show();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int success = jsonObject.getInt("success");
                    //       String msg=jsonObject.getString("message");
                    if (success == 1) {
                        //  Toast.makeText(context, " Redirecting", Toast.LENGTH_LONG).show();
                        Toast.makeText(Otp_verify.this, "Otp sent", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    dismiss_dialogue();
                    e.printStackTrace();
                }

            }

            @Override
            public void failure(RetrofitError error) {
                //  myCustomProgress_dialogue.dismiss_dialogue();
                dismiss_dialogue();
                Toast.makeText(Otp_verify.this, "unable to login right now", Toast.LENGTH_SHORT).show();
                Log.d("retroerror", "" + error.getCause());
            }
        });
    }

    public void show_dialogue() {

        dialog = new CustomProgressDialog(Otp_verify.this);
    }

    public void dismiss_dialogue() {
        dialog.hide();
    }


    @Override
    public void onResume() {
       /* IncomingSms incomingSms=new IncomingSms();
        LocalBroadcastManager.getInstance(Otp_verify.this).registerReceiver(incomingSms, new IntentFilter("pdus"));*/
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
       /* IncomingSms incomingSms=new IncomingSms();
        LocalBroadcastManager.getInstance(Otp_verify.this).unregisterReceiver(incomingSms);*/
    }

    /*  public class IncomingSms extends BroadcastReceiver {

          public  String msg;
          // Get the object of SmsManager
          final SmsManager sms = SmsManager.getDefault();

          public void onReceive(Context context, Intent intent) {

              // Retrieves a map of extended data from the intent.
              final Bundle bundle = intent.getExtras();

              try {

                  if (bundle != null) {

                      final Object[] pdusObj = (Object[]) bundle.get("pdus");

                      for (int i = 0; i < pdusObj.length; i++) {

                          SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                          String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                          String senderNum = phoneNumber;
                          String message = currentMessage.getDisplayMessageBody();

                          if(senderNum.equalsIgnoreCase("AD-Prbull"))
                          {
                              Toast toast = Toast.makeText(context,
                                      "ok senderNum: "+ senderNum , Toast.LENGTH_SHORT);
                              toast.show();
                          }else{
                              Toast toast = Toast.makeText(context,
                                      "message from other numnber: "+ senderNum , Toast.LENGTH_SHORT);
                              toast.show();
                          }
                          //     Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);


                          // Show Alert

                          Toast toast = Toast.makeText(context,
                                  "senderNum: "+ senderNum + ", message: " + message, Toast.LENGTH_LONG);
                          toast.show();

                      } // end for loop
                  } // bundle is null

              } catch (Exception e) {
                  Log.e("SmsReceiver", "Exception smsReceiver" +e);

              }
          }
      }*/
    private void setUpOTP() {
        i_timer = 59;
        timer_called = true;
        timer(true);
    }


    public void timer(boolean status) {
        if (status) {
            tv_time_otp_verify.setVisibility(View.VISIBLE);
            ll_resend_otp_verify.setVisibility(View.GONE);
            if (timer_called) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (i_timer > 0) {
                            timer(true);
                            tv_time_otp_verify.setText("00:" + twoDigitsInt(i_timer));
                        } else {
                            timer(false);
                            ll_resend_otp_verify.setVisibility(View.VISIBLE);
                        }
                        i_timer--;
                    }
                }, 1000);
            }
        } else {
            timer_called = false;
            ll_resend_otp_verify.setVisibility(View.VISIBLE);
            tv_time_otp_verify.setVisibility(View.GONE);
        }
    }

    public static String twoDigitsInt(int i) {
        String s_i = "";
        if (i < 10) {
            s_i = "0" + i;
        } else {
            s_i = "" + i;
        }
        return s_i;
    }
}
