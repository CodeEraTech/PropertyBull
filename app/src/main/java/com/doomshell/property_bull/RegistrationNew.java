package com.doomshell.property_bull;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationNew extends Fragment {
    private RelativeLayout rlLoginScreen;
    private ProgressBar loginProgress;
    private ScrollView loginForm;
    private LinearLayout login;
    private TextView projectName;
    private EditText edtName;
    private EditText edtEmail;
    private Spinner spSelctType;
    private EditText edtPhone;
    private EditText edtPassword;
    private EditText edtConfirmPassword;
    private LinearLayout linear;
    private TextView terms;
    private CheckBox regAcceptCheckbox;
    private TextView tncText;
    private ProgressBar pbSignup;
    private Button btnSignUp;

    private String[] type = {"Select", "Owner", "Agent", "Builder"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        initView(view);
        ArrayAdapter<String> arrayAdapter_shifttime = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, type);
        spSelctType.setAdapter(arrayAdapter_shifttime);
        spSelctType.setSelection(0);
        tncText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();
                String usernameemail = edtEmail.getText().toString();
                String mobile = edtPhone.getText().toString();
                String pass = edtPassword.getText().toString();
                String confirmpass = edtConfirmPassword.getText().toString();
                String type = spSelctType.getSelectedItem().toString();


                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getActivity(), "Please Enter Name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(usernameemail)) {
                    Toast.makeText(getActivity(), "Please Enter Email Id", Toast.LENGTH_SHORT).show();
                } else if (!emailValid(usernameemail)) {
                    Toast.makeText(getActivity(), "Please Enter Valid Email", Toast.LENGTH_SHORT).show();
                } else if (type.equals("Select")) {
                    Toast.makeText(getActivity(), "Please Select Type", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(mobile)) {
                    Toast.makeText(getActivity(), "Please Enter Mobile No", Toast.LENGTH_SHORT).show();
                } else if (mobile.length() < 10) {
                    Toast.makeText(getActivity(), "Please Enter Valid Mobile No", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(getActivity(), "Please Enter Password", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(confirmpass)) {
                    Toast.makeText(getActivity(), "Please Enter Confirm Password", Toast.LENGTH_SHORT).show();
                } else if (!TextUtils.equals(pass, confirmpass)) {
                    Toast.makeText(getActivity(), "Password Not Matched", Toast.LENGTH_SHORT).show();
                } else if (!regAcceptCheckbox.isChecked()) {
                    Toast.makeText(getActivity(), "Accept Terms And Condition", Toast.LENGTH_SHORT).show();
                } else {
                    if (type.equals("Owner")) {
                        type = "1";
                    } else if (type.equals("Agent")) {
                        type = "2";

                    } else if (type.equals("Builder")) {
                        type = "3";

                    }
                    Registration(name, usernameemail, type, mobile, pass);
                }

            }
        });
        return view;


    }

    public boolean emailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void initView(View view) {
        rlLoginScreen = (RelativeLayout) view.findViewById(R.id.rl_login_screen);
        loginProgress = (ProgressBar) view.findViewById(R.id.login_progress);
        loginForm = (ScrollView) view.findViewById(R.id.login_form);
        login = (LinearLayout) view.findViewById(R.id.login);
        projectName = (TextView) view.findViewById(R.id.project_name);
        edtName = (EditText) view.findViewById(R.id.edtName);
        edtEmail = (EditText) view.findViewById(R.id.edtEmail);
        spSelctType = (Spinner) view.findViewById(R.id.spSelctType);
        edtPhone = (EditText) view.findViewById(R.id.edtPhone);
        edtPassword = (EditText) view.findViewById(R.id.edtPassword);
        edtConfirmPassword = (EditText) view.findViewById(R.id.edtConfirmPassword);
        linear = (LinearLayout) view.findViewById(R.id.linear);
        terms = (TextView) view.findViewById(R.id.terms);
        regAcceptCheckbox = (CheckBox) view.findViewById(R.id.reg_accept_checkbox);
        tncText = (TextView) view.findViewById(R.id.tnc_text);
        pbSignup = (ProgressBar) view.findViewById(R.id.pb_signup);
        btnSignUp = (Button) view.findViewById(R.id.btnSignUp);
    }

    public void Registration(String name, String username, String type, String phone, String password) {
        btnSignUp.setVisibility(View.GONE);
        pbSignup.setVisibility(View.VISIBLE);
        AndroidNetworking.post("https://www.propertybull.com/mobile/user_registration")
                .addBodyParameter("name", name)
                .addBodyParameter("username", username)
                .addBodyParameter("mobile", phone)
                .addBodyParameter("role_id", type)
                .addBodyParameter("password", password)
                .setPriority(Priority.MEDIUM)
                .setTag("reg")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.has("success") && response.getString("success").equals("1")) {
                                String user_id = response.getString("user_id");
                                String mobile = response.getString("mobile");
                                boolean loginintent = false;
                                Intent intent = new Intent(getActivity().getApplicationContext(), Otp_verify.class);
                                intent.putExtra("loginintent", loginintent);
                                intent.putExtra("uid", user_id);
                                intent.putExtra("umobile", mobile);
                                getActivity().startActivity(intent);
                                getActivity().finish();

                            } else {
                                Toast.makeText(getActivity(), response.getString("message"), Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        btnSignUp.setVisibility(View.VISIBLE);
                        pbSignup.setVisibility(View.GONE);

                    }

                    @Override
                    public void onError(ANError anError) {
                        if (TextUtils.equals("connectionError", anError.getErrorDetail())) {
                            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), anError.getErrorBody(), Toast.LENGTH_SHORT).show();
                        }

                        btnSignUp.setVisibility(View.VISIBLE);
                        pbSignup.setVisibility(View.GONE);
                    }
                });

    }
}

