package com.doomshell.property_bull;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.doomshell.property_bull.helper.CustomProgressDialog;
import com.doomshell.property_bull.helper.Serverapi;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class HomeLoan_Fag extends Fragment {
    Context context;
    View convertview;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    String Baseurl;
    RestAdapter restAdapter = null;
    Serverapi serverapi;
    // Dialog contactdialog;
    ProgressBar bar;
    AppCompatActivity appCompatActivity;
    ActionBar actionBar;
    String emailId, emailPattern, loanId, loanName, userType;
    RadioGroup radioGroup;
    Spinner personLoanType;
    Button btnSubmit;
    ArrayList<String> getLoanTypeId = new ArrayList<String>();
    ArrayList<String> getLoanTypeName = new ArrayList<String>();
    TextView loanPersonName, loanPersonEmail, loanPersonMob, loanPersonAddress,
            personDob, personTotalIncome, personIncome, termsandcondloan;
    CheckBox checkboxterm;
    CustomProgressDialog dialog;
    RadioButton radioButton;
    final Calendar myCalendar = Calendar.getInstance();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity().getApplicationContext();
        convertview = inflater.inflate(R.layout.homeloan, container, false);
        appCompatActivity = (AppCompatActivity) getActivity();

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        TextView screentitle = (TextView) toolbar.findViewById(R.id.screentitle);
        ImageView imageView = (ImageView) toolbar.findViewById(R.id.screenimageview);
        BottomNavigationView bottomnavView = getActivity().findViewById(R.id.bottom_nav_view);
        bottomnavView.setVisibility(View.GONE);
        screentitle.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
        screentitle.setText("Home Loan");


        Baseurl = getActivity().getResources().getString(R.string.myurl);
        com.squareup.okhttp.OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(5, TimeUnit.MINUTES.MINUTES);
        okHttpClient.setReadTimeout(5, TimeUnit.MINUTES.MINUTES);
        restAdapter = new RestAdapter.Builder().setEndpoint(Baseurl).setClient(new OkClient(okHttpClient)).build();
        serverapi = restAdapter.create(Serverapi.class);

        setId();
        setClickListeners();
        get_LoanType();
        personLoanType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loanId = getLoanTypeId.get(i);
                loanName = getLoanTypeName.get(i);

                Log.d("selectedOperatorId", loanId + "(" + loanName + ")" + loanId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                loanId = getLoanTypeId.get(personLoanType.getSelectedItemPosition());
            }
        });
        return convertview;
    }

    public void setId() {
        radioGroup = convertview.findViewById(R.id.radioGroup);
        loanPersonName = convertview.findViewById(R.id.loanPersonName);
        loanPersonMob = convertview.findViewById(R.id.loanPersonMob);
        loanPersonEmail = convertview.findViewById(R.id.loanPersonEmail);
        loanPersonAddress = convertview.findViewById(R.id.loanPersonAddress);
        personLoanType = convertview.findViewById(R.id.personLoanType);
        personDob = convertview.findViewById(R.id.personDob);
        personIncome = convertview.findViewById(R.id.personIncome);
        personTotalIncome = convertview.findViewById(R.id.personTotalIncome);
        termsandcondloan = convertview.findViewById(R.id.termsandcondloan);
        btnSubmit = convertview.findViewById(R.id.btnSubmit);
        checkboxterm = convertview.findViewById(R.id.checkboxterm);


        termsandcondloan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });

        emailId = loanPersonEmail.getText().toString().trim();
        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        loanPersonEmail.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (emailId.matches(emailPattern) && s.length() > 0) {

                } else {
                    Toast.makeText(getActivity(), "Invalid email address", Toast.LENGTH_SHORT).show();
                    //or
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });
    }

    public void setClickListeners() {

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        personDob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // get selected radio button from radioGroup
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = convertview.findViewById(selectedId);

        if (radioButton.getText().equals("Salaried")) {
            userType = "1";
        } else if (radioButton.getText().equals("Self Employed")) {
            userType = "2";
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loanPersonName.getText().toString().equals("")) {
                    loanPersonName.setError("Please Enter  Name");

                } else if (loanPersonMob.getText().toString().equals("")) {
                    loanPersonMob.setError("Please Enter Valid Mobile No");
                } else if (loanPersonEmail.getText().toString().equals("")) {
                    loanPersonEmail.setError("Please Enter Valid Email");
                } else if (loanPersonAddress.getText().toString().equals("")) {
                    loanPersonAddress.setError("Please Enter Valid Address");
                } else if (personDob.getText().toString().equals("")) {
                    personDob.setError("Please Select D.O.B");
                } else if (personIncome.getText().toString().equals("")) {
                    personIncome.setError("Please Enter Your Income");
                } else if (personTotalIncome.getText().toString().equals("")) {
                    personTotalIncome.setError("Please Enter Total Family Income");
                } else if (checkboxterm.isChecked() == false) {
                    checkboxterm.setError("Please Accept Terms and Condition");
                } else {
                    String LoanPersonName = loanPersonName.getText().toString();
                    String LoanPersonMob = loanPersonMob.getText().toString();
                    String LoanPersonEmail = loanPersonEmail.getText().toString();
                    String PersonDob = personDob.getText().toString();
                    String LoanPersonAddress = loanPersonAddress.getText().toString();
                    String PersonIncome = personIncome.getText().toString();
                    String PersonTotalIncome = personTotalIncome.getText().toString();

                    loan_request(loanId, userType, LoanPersonName, LoanPersonMob, LoanPersonEmail, PersonDob, LoanPersonAddress
                            , PersonIncome, PersonTotalIncome);
                }

            }
        });

    }

    void get_LoanType() {
        show_dialogue();
        getLoanTypeId.add("");
        getLoanTypeName.add("Select Loan Type");
        serverapi.loan_type(new Callback<Response>() {
            @Override
            public void success(retrofit.client.Response response, Response response2) {
                String s = new String(((TypedByteArray) response.getBody()).getBytes());
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int success = jsonObject.getInt("success");
                    if (success == 1) {
                        JSONArray profiledata = jsonObject.getJSONArray("loanTypes");
                        Log.d("ProfileData", "" + profiledata);
                        for (int i = 0; i < profiledata.length(); i++) {
                            JSONObject c = profiledata.getJSONObject(i);
                            String LoanId = c.getString("id");
                            String LoanTypeName = c.getString("name");
                            getLoanTypeId.add(LoanId);
                            getLoanTypeName.add(LoanTypeName);

                            //Set Adaptor
                            ArrayAdapter<String> banknameadaptor = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, getLoanTypeName) {
                                public View getView(int position, View convertView, ViewGroup group) {
                                    View view = super.getView(position, convertView, group);
                                    ((TextView) view).setGravity(Gravity.CENTER_HORIZONTAL);
                                    if (position == 0) {
                                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary));
                                    } else {
                                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary));
                                    }

                                    return view;

                                }
                            };
                            personLoanType.setAdapter(banknameadaptor);
                        }


                        dismiss_dialogue();

                    }
                } catch (JSONException e) {
                    dismiss_dialogue();
                    Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                dismiss_dialogue();

            }
        });
    }

    void loan_request(String loanType, String userType, String name, String mob,
                      String email, String dob, String location, String monthlySalary, String
                              grossSalary) {
        show_dialogue();
        serverapi.loan_request(loanType, userType, name, mob, email, dob, location, grossSalary, monthlySalary, new Callback<retrofit.client.Response>() {
            @Override
            public void success(retrofit.client.Response response, Response response2) {
                String s = new String(((TypedByteArray) response.getBody()).getBytes());
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int success = jsonObject.getInt("success");
                    if (success == 1) {
                        dismiss_dialogue();

                        MainHome_Frag mainHome_frag = new MainHome_Frag();
                        FragmentTransaction devicetrans = getActivity().getSupportFragmentManager().beginTransaction();
                        devicetrans.replace(R.id.frame_container, mainHome_frag);
                        devicetrans.addToBackStack(mainHome_frag.getClass().toString());
                        devicetrans.commit();

                        Toast.makeText(context, "Your Request Has Been Registered Successfully.", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    dismiss_dialogue();
                    Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                dismiss_dialogue();

            }
        });
    }

    public void show_dialogue() {

        dialog = new CustomProgressDialog(getActivity());
    }


    public void dismiss_dialogue() {
        dialog.hide();
    }

    private void updateLabel() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        personDob.setText(sdf.format(myCalendar.getTime()));
    }
}
