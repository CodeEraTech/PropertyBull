package com.doomshell.property_bull;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.helpers.Constants;
import com.darsh.multipleimageselect.models.Image;
import com.doomshell.property_bull.adapter.MultipleImageAdaptor;
import com.doomshell.property_bull.adapter.MySpinnerAdapter;
import com.doomshell.property_bull.helper.CustomProgressDialog;
import com.doomshell.property_bull.helper.ImageListBean;
import com.doomshell.property_bull.helper.MyCustomProgress_dialogue;
import com.doomshell.property_bull.helper.Serverapi;
import com.doomshell.property_bull.model.SharedPrefManager;
import com.squareup.okhttp.OkHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

public class AddProperty_discription extends Fragment implements View.OnClickListener {
    Context context;
    View convertview;
    EditText p4_price, p4_unit, p4_discription;
    TextView p4_totalprice;
    Button p4_post_btn;
    Button p4_post_image;
    int price = 0;
    int unit = 0;
    int total = 0;
    int READ_ex_Storage = 112;
    String imagepath;
    ImageView postproert_image;
    boolean isfile;
    AlertDialog optionalDialogue;
    private static final int CAMERA_REQUEST = 1888;
    String sprice, sunit, stotal, sdicrciption;
    String num_rooms, bathroom, totalFloor, propertonFloor, Flooring, propertyface, ageofproperty, option1, option2, proid, location, title, landmark, pincode, address;
    CustomProgressDialog dialog;
    boolean isprice, isunit, istotal, isdes;
    Spinner unit_spiner;
    RestAdapter restAdapter = null;
    Serverapi serverapi;
    MyCustomProgress_dialogue myCustomProgress_dialogue = null;
    String Baseurl;
    DisplayMetrics displayMetrics;
    ArrayList<String> path;
    double screenHeight;
    double screenWidth;
    LinearLayout depositlyt;
    EditText depositedit;
    String deposittxt = "1";
    ArrayList<String> unit_list;
    String unitt = "";
    boolean isunitt;

    RecyclerView reyclerimagelist;
    int PIC_CODE = 0;
    TextView noimagetext;


    /*RequestInterceptor requestInterceptor = new RequestInterceptor() {
        @Override
        public void intercept(RequestFacade request) {
            request.addHeader("Content-Type", "multipart/form-data");
            //  request.addHeader("Content-Type","application/json");
            //   request.addHeader("Content-Type","text/html");
        }
    };*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity().getApplicationContext();
        convertview = inflater.inflate(R.layout.fragment_add_descrition, container, false);

        Baseurl = getActivity().getResources().getString(R.string.myurl);
        //    Baseurl="http://192.168.5.53/propertybull/";
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(5, TimeUnit.MINUTES.MINUTES);
        okHttpClient.setReadTimeout(5, TimeUnit.MINUTES.MINUTES);

        displayMetrics = context.getResources().getDisplayMetrics();
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        int lwi = (int) (screenWidth * 0.40);
        // restAdapter = new RestAdapter.Builder().setEndpoint(Baseurl).setRequestInterceptor(requestInterceptor).setClient(new OkClient(okHttpClient)).setLogLevel(RestAdapter.LogLevel.FULL).build();

        restAdapter = new RestAdapter.Builder().setEndpoint(Baseurl).setClient(new OkClient(okHttpClient)).build();

        serverapi = restAdapter.create(Serverapi.class);
        myCustomProgress_dialogue = new MyCustomProgress_dialogue(getActivity(), Color.BLUE);

        savedInstanceState = getArguments();

        num_rooms = savedInstanceState.getString("num_rooms");
        bathroom = savedInstanceState.getString("bathroom");
        totalFloor = savedInstanceState.getString("totalFloor");
        propertonFloor = savedInstanceState.getString("propertonFloor");
        Flooring = savedInstanceState.getString("Flooring");
        propertyface = savedInstanceState.getString("propertyface");
        ageofproperty = savedInstanceState.getString("ageofproperty");
        option1 = savedInstanceState.getString("option1");
        option2 = savedInstanceState.getString("option2");
        proid = savedInstanceState.getString("proid");
        location = savedInstanceState.getString("location");
        title = savedInstanceState.getString("title");
        landmark = savedInstanceState.getString("landmark");
        pincode = savedInstanceState.getString("pincode");
        address = savedInstanceState.getString("address");
        unit_spiner = (Spinner) convertview.findViewById(R.id.unit_spiner);
        unit_list = new ArrayList<>();
        unit_list.add("Area Unit");
        unit_list.add("Sq Ft");
        unit_list.add("Sq Mtr");
        unit_list.add("Sq Yard");
        unit_list.add("Bigha");
        unit_list.add("Hectare");

        unit_spiner.setAdapter(new MySpinnerAdapter(context, unit_list));

        unit_spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                unitt = unit_list.get(i).toString();

                if (unitt.equalsIgnoreCase("Hectare")) {
                    unitt = "hec";
                } else {
                    unitt = unit_list.get(i).toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        p4_price = (EditText) convertview.findViewById(R.id.p4_price);
        p4_unit = (EditText) convertview.findViewById(R.id.p4_unit);
        p4_totalprice = (TextView) convertview.findViewById(R.id.p4_totalprice);
        p4_discription = (EditText) convertview.findViewById(R.id.p4_discription);
        p4_post_btn = (Button) convertview.findViewById(R.id.p4_post_btn);
        p4_post_image = (Button) convertview.findViewById(R.id.p4_post_image);
        // postproert_image = (Ima) convertview.findViewById(R.id.postproert_image);


        depositedit = (EditText) convertview.findViewById(R.id.p4_depositedt);
        depositlyt = (LinearLayout) convertview.findViewById(R.id.depositlyt);
        if (option1.equalsIgnoreCase("RENT")) {
            depositlyt.setVisibility(View.VISIBLE);
        }
        //  p4_post_btn.getLayoutParams().width= lwi;
        //  p4_post_btn.requestLayout();
        p4_post_btn.setOnClickListener(this);
        p4_post_image.setOnClickListener(this);

        p4_unit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (p4_unit.hasFocus()) {

                }
            }
        });

        p4_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String newsprice = p4_price.getText().toString();
                String newsunit = p4_unit.getText().toString();

                if (newsprice == null || newsprice.equals("")) {
                    price = 0;
                } else {
                    try {
                        price = Integer.parseInt(newsprice);
                    } catch (Exception e) {
                        Log.d("exp", "" + e);
                    }

                }

                if (newsunit == null || newsunit.equals("")) {
                    unit = 0;
                } else {
                    try {
                        unit = Integer.parseInt(newsunit);
                    } catch (Exception e) {
                        Log.d("exp", "" + e);
                    }

                }
                total = price * unit;
                p4_totalprice.setText("Total :" + "\u20B9 " + total);
            }
        });

        p4_unit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String newsprice = p4_price.getText().toString();
                String newsunit = p4_unit.getText().toString();

                if (newsprice == null || newsprice.equals("")) {
                    price = 0;
                } else {
                    try {
                        price = Integer.parseInt(newsprice);
                    } catch (Exception e) {
                        Log.d("exp", "" + e);
                    }

                }

                if (newsunit == null || newsunit.equals("")) {
                    unit = 0;
                } else {
                    try {
                        unit = Integer.parseInt(newsunit);
                    } catch (Exception e) {
                        Log.d("exp", "" + e);
                    }

                }
                total = price * unit;
                p4_totalprice.setText("Total :" + "\u20B9 " + total);
            }
        });

        reyclerimagelist = (RecyclerView) convertview.findViewById(R.id.reyclerimagelist);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 4);
        reyclerimagelist.setLayoutManager(manager);

        noimagetext = (TextView) convertview.findViewById(R.id.noimagetext);

        path = new ArrayList<>();


        return convertview;
    }

    @Override
    public void onClick(View v) {
        if (v == p4_post_btn) {
            sprice = p4_price.getText().toString();
            sunit = p4_unit.getText().toString();
            sdicrciption = p4_discription.getText().toString();


            if (option1.equals("SELL")) {
                option1 = "Sell";
                deposittxt = "1";
            }
            if (option1.equals("RENT")) {
                option1 = "Rent";
                deposittxt = depositedit.getText().toString();

            }
            if (sprice == null || sprice.equals("")) {
                p4_price.setError("Enter price per unit");
                isprice = false;
            } else {
                isprice = true;
            }
            if (unitt.equalsIgnoreCase("Area Unit")) {
                isunitt = false;
                Toast.makeText(context, "Please select unit of area", Toast.LENGTH_SHORT).show();
            } else {

                isunitt = true;
            }
            if (sunit == null || sunit.equals("")) {
                p4_unit.setError("Enter total units of property");
                isunit = false;
            } else {
                isunit = true;
            }

            if (noimagetext.isShown()) {
                isfile = false;
                Toast.makeText(context, "Please select picture to upload", Toast.LENGTH_SHORT).show();
            }

            if (sdicrciption == null || sdicrciption.equals("")) {
                p4_discription.setError("Please enter some discription about property");
                isdes = false;
            }/*else {
                if(sdicrciption.trim().length()<50){
                    p4_discription.setError("Tell at least in 50 characters ");
                    isdes=false;
                }*/ else {
                isdes = true;
            }

            if (isprice && isunit && isdes && isfile && isunitt) {

                load_postProperty();
            }


        }


        if (v == p4_post_image) {
            int myAPI = Build.VERSION.SDK_INT;
            if (myAPI >= 23) {
                int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);

                if (result == PackageManager.PERMISSION_GRANTED) {
                    show_optional_dialogue();

                } else {

                    Toast.makeText(context, "Permission required for Uploading Image", Toast.LENGTH_SHORT).show();
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                READ_ex_Storage);

                        //     Toast.makeText(activity, "Microphone permission needed for recording. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
                    } else {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                                READ_ex_Storage);

                    }


                }
            } else {
                show_optional_dialogue();

            }

        }


    }


    @Override
    public void onActivityResult(int requescode, int resultcode, Intent data) {
        super.onActivityResult(requescode, resultcode, data);

     /*   if (requescode == 1 && resultcode == Activity.RESULT_OK && data != null && data.getData() != null) {


            try {
                Uri filepath = data.getData();

                imagepath = getFileNameByUri(getActivity().getApplicationContext(), filepath);
                postproert_image.setImageURI(filepath);
             //   Toast.makeText(context, "" + imagepath, Toast.LENGTH_SHORT).show();


            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            isfile = true;
        }*/
        if (requescode == Constants.REQUEST_CODE && resultcode == Activity.RESULT_OK && data != null) {
            noimagetext.setVisibility(View.GONE);
            reyclerimagelist.setVisibility(View.VISIBLE);
            // Uri images=data.getData();
            isfile = true;
            //  ImageListBean.getInstance().setImages(data.<Image>getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES));
            ArrayList<Image> images = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);

            // Uri imagesurl=data.getData();
            // postproert_image.setImageURI(Uri.parse(images.get(0).path));


            for (int i = 0; i < images.size(); i++) {
                path.add(images.get(i).path);
            }

            isfile = true;

            //Toast.makeText(getActivity(),images.get(0).path,Toast.LENGTH_LONG).show();
        }

        if (requescode == CAMERA_REQUEST && resultcode == Activity.RESULT_OK) {
            //     Bitmap photo = (Bitmap) data.getExtras().get("data");
            /* imageView.setImageBitmap(photo);*/


            try {

                noimagetext.setVisibility(View.GONE);
                reyclerimagelist.setVisibility(View.VISIBLE);
                // get new image here like this
                if (PIC_CODE < 10) {
                    // add new requset of picture like this
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);

                    Bitmap filepath = (Bitmap) data.getExtras().get("data");
                    // postproert_image.setImageBitmap(filepath);


                    Uri uri = getImageUri(getActivity(), filepath);

                    String imagepath = getRealPathFromURI(uri);

                    path.add(imagepath);

                    PIC_CODE++;
                }


                // Uri filepath = data.getData();


                // Toast.makeText(context, "" + filepath, Toast.LENGTH_SHORT).show();


            } catch (Exception e) {
                e.printStackTrace();
                //Toast.makeText(context, "" + e, Toast.LENGTH_SHORT).show();
            }
            isfile = true;
        }
        if (path.size() > 0) {

            ImageListBean.getInstance().setImages(path);
            MultipleImageAdaptor adaptor = new MultipleImageAdaptor((AppCompatActivity) getActivity(), getActivity(), noimagetext, reyclerimagelist);
            reyclerimagelist.setAdapter(adaptor);
            reyclerimagelist.setHasFixedSize(true);
            adaptor.notifyDataSetChanged();
        } else {

        }
    }


    @SuppressLint("NewApi")
    public static String getFileNameByUri(Context context, Uri uri) throws URISyntaxException {
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{
                        split[1]
                };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver()
                        .query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    public void show_optional_dialogue() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.myDialog));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.image_option_dialogue, null);
        dialogBuilder.setView(dialogView);

        LinearLayout camera, other;
        ImageButton cancel;
        camera = (LinearLayout) dialogView.findViewById(R.id.post_camera_layout);
        other = (LinearLayout) dialogView.findViewById(R.id.post_other_layout);
        cancel = (ImageButton) dialogView.findViewById(R.id.remove);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionalDialogue.dismiss();
            }
        });
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
              startActivityForResult(galleryIntent.createChooser(galleryIntent, "Select Profile image "), 1);*/
                Intent galleryintent = new Intent(getActivity(), AlbumSelectActivity.class);
                galleryintent.putExtra(Constants.INTENT_EXTRA_LIMIT, 10);
                startActivityForResult(galleryintent, Constants.REQUEST_CODE);
                optionalDialogue.dismiss();
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                /*Home_Activity activity = (Home_Activity) getActivity();
                if (cameraIntent.resolveActivity(activity.getPackageManager()) != null) {
                    // Create the File where the photo should go.
                    // If you don't do this, you may get a crash in some devices.
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        Toast toast = Toast.makeText(activity, "There was a problem saving the photo...", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri fileUri = Uri.fromFile(photoFile);
                        activity.setCapturedImageUR(fileUri);
                        activity.setCurrentPhotoPath(fileUri.getPath());
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                activity.getCapturedImageURI());
                        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                    }
                }*/


                //  Toast.makeText(context, "ok camera", Toast.LENGTH_SHORT).show();
                optionalDialogue.dismiss();
            }
        });

        optionalDialogue = dialogBuilder.create();

        optionalDialogue.show();
    }

    private void load_postProperty() {
        //myCustomProgress_dialogue.show_dialogue();
        dialog = new CustomProgressDialog(getActivity());
        String uid = SharedPrefManager.getInstance(context).getuser_details("id");
        String cityId = SharedPrefManager.getInstance(context).getuser_details("city_id");
        //  ArrayList<TypedFile> file=new ArrayList<>();

        MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
        for (int j = 0; j < ImageListBean.getInstance().getImages().size(); j++) {

            //  file.add(new TypedFile("multipart/form-data", new File(path.get(j))));
            multipartTypedOutput.addPart("photo[]", new TypedFile("multipart/form-data", new File(ImageListBean.getInstance().getImages().get(j))));
        }

        multipartTypedOutput.addPart("option", new TypedString(option1));
        if (deposittxt.equalsIgnoreCase("")) {
            deposittxt = "1";
        }
        multipartTypedOutput.addPart("deposittxt", new TypedString(deposittxt));
        multipartTypedOutput.addPart("sdicrciption", new TypedString(sdicrciption));
        multipartTypedOutput.addPart("unit", new TypedString(unitt));
        multipartTypedOutput.addPart("cus_id", new TypedString(uid));
        multipartTypedOutput.addPart("p_typeid", new TypedString(proid));
        multipartTypedOutput.addPart("name", new TypedString(title));
        multipartTypedOutput.addPart("city_id", new TypedString(cityId));
        multipartTypedOutput.addPart("location_id", new TypedString(location));
        multipartTypedOutput.addPart("address", new TypedString(address));
        multipartTypedOutput.addPart("address2", new TypedString(landmark));
//        multipartTypedOutput.addPart("pincode", new TypedString(pincode));
        multipartTypedOutput.addPart("pincode", new TypedString("0"));
        multipartTypedOutput.addPart("floor", new TypedString(totalFloor));
        multipartTypedOutput.addPart("p_floor", new TypedString(propertonFloor));
        multipartTypedOutput.addPart("flooring", new TypedString(Flooring));
        multipartTypedOutput.addPart("faceid", new TypedString(propertyface));
        multipartTypedOutput.addPart("area", new TypedString("" + price));
        multipartTypedOutput.addPart("per_unit", new TypedString("" + unit));
        multipartTypedOutput.addPart("tot_price", new TypedString("" + total));
        multipartTypedOutput.addPart("room", new TypedString(num_rooms));
        multipartTypedOutput.addPart("bathroom", new TypedString(bathroom));

        serverapi.post_property(multipartTypedOutput, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                // myCustomProgress_dialogue.dismiss_dialogue();
                try {
                    dialog.hide();
                    ;
                    String s = new String(((TypedByteArray) response.getBody()).getBytes());

                    Toast.makeText(context, "Property Successfully Added ", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(context, Home_Activity.class);
                    startActivity(intent);
                    getActivity().finish();
                } catch (Exception e) {
                    Toast.makeText(context, "Unable to post property right now", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                dialog.hide();
                ;

                Log.e("posting error", error.toString());

                Toast.makeText(context, "Something went wrong on server", Toast.LENGTH_SHORT).show();
            }
        });

        // TypedFile typedFile = new TypedFile("multipart/form-data", new File(imagepath);

        //    String description = "hello, this is description speaking";
    /*    RequestBody  Roption1=RequestBody.create(MediaType.parse("text/plain"), option1);
        RequestBody  Rid=RequestBody.create(MediaType.parse("text/plain"),uid );
        RequestBody  Rproid=RequestBody.create(MediaType.parse("text/plain"), proid);
        RequestBody  Rtitle=RequestBody.create(MediaType.parse("text/plain"), title);
        RequestBody  Rcity_id=RequestBody.create(MediaType.parse("text/plain"),cityId );
        RequestBody  Rlocation=RequestBody.create(MediaType.parse("text/plain"), location);
        RequestBody  Raddress=RequestBody.create(MediaType.parse("text/plain"), address);
        RequestBody  Rlandmark=RequestBody.create(MediaType.parse("text/plain"), landmark);
        RequestBody  Rpincode=RequestBody.create(MediaType.parse("text/plain"), pincode);
        RequestBody  RtotalFloor=RequestBody.create(MediaType.parse("text/plain"), totalFloor);
        RequestBody  RpropertonFloor=RequestBody.create(MediaType.parse("text/plain"), propertonFloor);
        RequestBody  RFlooring=RequestBody.create(MediaType.parse("text/plain"), Flooring);
        RequestBody  Rpropertyface=RequestBody.create(MediaType.parse("text/plain"), propertyface);
        RequestBody  Rprice=RequestBody.create(MediaType.parse("text/plain"), ""+price);
        RequestBody  Runit=RequestBody.create(MediaType.parse("text/plain"), ""+unit);
        RequestBody  Rtotal=RequestBody.create(MediaType.parse("text/plain"), ""+total);
        RequestBody  Rnum_rooms=RequestBody.create(MediaType.parse("text/plain"), num_rooms);
        RequestBody  Rbathroom=RequestBody.create(MediaType.parse("text/plain"), bathroom);
*/
        // RequestBody requestFile =RequestBody.create(MediaType.parse("multipart/form-data"),new File(imagepath));
        // RequestBody  typedFile=RequestBody.create(MediaType.parse("image/*"), new File(imagepath));

/*
        Roption1,
                Rid,
                Rproid,
                Rtitle,
                Rcity_id,
                Rlocation,
                Raddress,
                Rlandmark,
                Rpincode,
                RtotalFloor,
                RpropertonFloor,
                RFlooring,
                Rpropertyface,
                Rprice,
                Runit,
                Rtotal,
                Rnum_rooms,
                Rbathroom,*/

       /*
         option1,
         SharedPrefManager.getInstance(context).getuser_details("id"),
                proid,
                title,
                SharedPrefManager.getInstance(context).getuser_details("city_id"),
                location,
                address,
                landmark,
                pincode,
                totalFloor,
                propertonFloor,
                Flooring,
                propertyface,
                ""+ price,
                ""+ unit,
                ""+ total,
                num_rooms,
                bathroom,
*/
         /*       serverapi.post_property(
                        option1,
                        SharedPrefManager.getInstance(context).getuser_details("id"),
                        proid,
                        title,
                        SharedPrefManager.getInstance(context).getuser_details("city_id"),
                        location,
                        address,
                        landmark,
                        pincode,
                        totalFloor,
                        propertonFloor,
                        Flooring,
                        propertyface,
                        ""+ price,
                        ""+ unit,
                        ""+ total,
                        num_rooms,
                        bathroom,
                        multipartTypedOutput,
                new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        myCustomProgress_dialogue.dismiss_dialogue();

                        String s=new String(((TypedByteArray) response.getBody()).getBytes());

                        Toast.makeText(context,"Property Successfully added ",Toast.LENGTH_SHORT).show();

                        Intent intent=new Intent(context,Home_Activity.class);
                        startActivity(intent);
                        getActivity().finish();

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        myCustomProgress_dialogue.dismiss_dialogue();
                        Log.d("retro error",""+error);
                        Toast.makeText(context,""+error,Toast.LENGTH_SHORT).show();
                    }
                });*/

    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null,
                null, null);

        if (cursor == null) { // Source is Dropbox or other similar local file
            // path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor
                    .getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "property", null);
        return Uri.parse(path);
    }

  /*  public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }*/

}
