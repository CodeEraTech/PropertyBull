package com.doomshell.property_bull;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANConstants;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.doomshell.property_bull.helper.CustomProgressDialog;
import com.doomshell.property_bull.helper.Serverapi;
import com.doomshell.property_bull.model.SharedPrefManager;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

import static android.app.Activity.RESULT_OK;


public class Profile extends Fragment {
    Context context;
    View convertview;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    String Baseurl;
    RestAdapter restAdapter = null;
    Serverapi serverapi;

    private static final int CAMERA_CODE = 101, GALLERY_CODE = 201, CROPING_CODE = 301;
    private File outPutFile = null;
    // Dialog contactdialog;
    ProgressBar bar, pb_save_edit_profile;
    AppCompatActivity appCompatActivity;
    ActionBar actionBar;
    EditText profilename, email, mob, profileaddress;
    ImageView profileimage;
    CustomProgressDialog dialog;
    String rid, rname, rmob, remail, raddress, rimage;
    String id;
    String noimageurl = "http://www.cybecys.com/wp-content/uploads/2017/07/no-profile.png";
    private Button btn_save_edit_profile;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity().getApplicationContext();
        convertview = inflater.inflate(R.layout.activity_profile, container, false);
        appCompatActivity = (AppCompatActivity) getActivity();

        //Toolbar
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        TextView screentitle = (TextView) toolbar.findViewById(R.id.screentitle);
        ImageView imageView = (ImageView) toolbar.findViewById(R.id.screenimageview);
        BottomNavigationView bottomnavView = getActivity().findViewById(R.id.bottom_nav_view);
        bottomnavView.setVisibility(View.VISIBLE);
        screentitle.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
        screentitle.setText("Profile");

        id = SharedPrefManager.getInstance(getActivity()).getuser_details("id");

        Baseurl = getActivity().getResources().getString(R.string.myurl);
        com.squareup.okhttp.OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(5, TimeUnit.MINUTES.MINUTES);
        okHttpClient.setReadTimeout(5, TimeUnit.MINUTES.MINUTES);
        //  myCustomProgress_dialogue=new MyCustomProgress_dialogue(getActivity(),R.color.colorPrimary);

        restAdapter = new RestAdapter.Builder().setEndpoint(Baseurl).setClient(new OkClient(okHttpClient)).build();
        serverapi = restAdapter.create(Serverapi.class);

        setId();
        get_profile();
        profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity().start(getContext(), Profile.this);
            }
        });
        btn_save_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = profilename.getText().toString();
                String mobile = mob.getText().toString();
                String address = profileaddress.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(context, "Please Enter Name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(mobile)) {
                    Toast.makeText(context, "Please Enter Mobile No", Toast.LENGTH_SHORT).show();
                } else if (mobile.length() < 10) {
                    Toast.makeText(context, "Please Enter Valid Mobile No", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(address)) {
                    Toast.makeText(context, "Please Enter address", Toast.LENGTH_SHORT).show();
                } else {
                    update_profile(rid, name, "", mobile);
                }
            }
        });
        return convertview;

    }

    private void setId() {
        profilename = convertview.findViewById(R.id.profilename);
        email = convertview.findViewById(R.id.profileemail);
        mob = convertview.findViewById(R.id.profilemob);
        profileaddress = convertview.findViewById(R.id.profileaddress);
        profileimage = convertview.findViewById(R.id.profileimage);
        btn_save_edit_profile = convertview.findViewById(R.id.btn_save_edit_profile);
        pb_save_edit_profile = convertview.findViewById(R.id.pb_save_edit_profile);

    }

    void get_profile() {

        show_dialogue();
        serverapi.get_profile(id, new Callback<retrofit.client.Response>() {
            @Override
            public void success(retrofit.client.Response response, Response response2) {
                String s = new String(((TypedByteArray) response.getBody()).getBytes());
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int success = jsonObject.getInt("success");
                    if (success == 1) {
                        JSONArray profiledata = jsonObject.getJSONArray("data");
                        Log.d("ProfileData", "" + profiledata);
                        for (int i = 0; i < profiledata.length(); i++) {
                            JSONObject c = profiledata.getJSONObject(i);
                            rid = c.getString("id").trim();
                            rname = c.getString("name").trim();
                            rmob = c.getString("mob").trim();
                            remail = c.getString("email").trim();
                            raddress = c.getString("address").trim();
                            rimage = c.getString("image").trim();

                            //Set Values
                            if (rimage.equalsIgnoreCase("") || rimage.equalsIgnoreCase(null)
                                    || rimage.equalsIgnoreCase("https:\\/\\/www.property_bull.com\\/user_image\\/")
                            ) {
                                Glide.with(getActivity()).load(noimageurl).into(profileimage);
                            } else {
                                Glide.with(getActivity()).load(rimage).into(profileimage);

                            }
                            if (rname.equalsIgnoreCase("") || rname.equalsIgnoreCase(null)) {
                                profilename.setText("-");
                            } else {
                                String pname = rname.substring(0, 1).toUpperCase() + rname.substring(1);
                                profilename.setText(pname);
                            }
                            if (rmob.equalsIgnoreCase("") || rmob.equalsIgnoreCase(null)) {
                                mob.setText("-");

                            } else {
                                mob.setText(rmob);
                            }
                            if (remail.equalsIgnoreCase("") || remail.equalsIgnoreCase(null)) {
                                email.setText("-");

                            } else {
                                email.setText(remail);

                            }
                            if (raddress.equalsIgnoreCase("") || raddress.equalsIgnoreCase(null)) {
                                profileaddress.setText("-");

                            } else {
                                profileaddress.setText("-");

                            }


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

    public void show_dialogue() {

        dialog = new CustomProgressDialog(getActivity());
    }

    public void dismiss_dialogue() {
        dialog.hide();
    }


    public void update_profile(String id, String name, String username, String mobileno) {
        btnVisiblity(false, pb_save_edit_profile, btn_save_edit_profile);
        AndroidNetworking.upload("https://www.propertybull.com/mobile/edit_profile")
                .addMultipartParameter("id", id)
                .addMultipartParameter("name", name)
                .addMultipartParameter("lname", "NA")
                .addMultipartParameter("username", username)
                .addMultipartParameter("landline", "NA")
                .addMultipartParameter("mobileno", mobileno)
                .addMultipartFile("image", outPutFile)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if (response.has("success") && response.getString("success").equals("1")) {
                                Toast.makeText(context, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                                get_profile();
                                Intent intent = new Intent(getActivity(), Home_Activity.class);
                                startActivity(intent);
                                getActivity().finish();

                            }

                        } catch (JSONException e) {

                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                        btnVisiblity(true, pb_save_edit_profile, btn_save_edit_profile);
                    }

                    @Override
                    public void onError(ANError error) {
                        if (TextUtils.equals(error.getErrorDetail(), ANConstants.CONNECTION_ERROR)) {

                            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();

                        } else {

                            Toast.makeText(getActivity(), error.getErrorDetail(), Toast.LENGTH_LONG).show();
                        }


                        btnVisiblity(true, pb_save_edit_profile, btn_save_edit_profile);

                    }

                });
    }

    public void btnVisiblity(boolean status, ProgressBar pb_bar, Button btn) {
        if (status) {
            btn.setVisibility(View.VISIBLE);
            pb_bar.setVisibility(View.GONE);
        } else {
            btn.setVisibility(View.INVISIBLE);
            pb_bar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                outPutFile = new File(resultUri.getPath());
                /*try {
                    //outPutFile = new Compressor(getActivity()).compressToFile(outPutFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                Picasso.get().load(outPutFile).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(profileimage);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
