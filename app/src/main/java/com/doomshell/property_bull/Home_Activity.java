package com.doomshell.property_bull;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.view.ContextThemeWrapper;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.doomshell.property_bull.helper.AlarmReceiver;
import com.doomshell.property_bull.helper.CallBack;
import com.doomshell.property_bull.helper.CustomTypefaceSpan;
import com.doomshell.property_bull.helper.MyCustomProgress_dialogue;
import com.doomshell.property_bull.model.Pname_setter;
import com.doomshell.property_bull.model.SharedPrefManager;
import com.github.javiersantos.appupdater.AppUpdaterUtils;
import com.github.javiersantos.appupdater.enums.AppUpdaterError;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.objects.Update;

import static com.doomshell.property_bull.MainHome_Frag.homes;
import static com.doomshell.property_bull.MainHome_Frag.isRecentCall;
import static com.doomshell.property_bull.MainHome_Frag.isRecentCallSearch;
import static com.doomshell.property_bull.MainHome_Frag.isRecentCallSearch1;
import static com.doomshell.property_bull.MainHome_Frag.isRecentCalldetais;

public class Home_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, CallBack {
    BottomNavigationView bottomnavView;
    TextView nav_userid;
    ImageView menuimagelogo;
    BroadcastReceiver broadcastReceiver = null;
    Button topbutton;
    android.support.v7.app.AlertDialog contact_alert;
    ImageView imageView_share;
    SharedPreferencesDatabase sharedPreferencesDatabase;
    private static final int PERIOD = 2000;
    private boolean back_status = false;
    private long lastPressedTime;
    private CoordinatorLayout coordinator_layout;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    nav_home();
                    back_status = true;
                    return true;
                case R.id.navigation_dashboard:
                    back_status = false;
                    nav_search();
                    return true;
                case R.id.navigation_notifications:
                    nav_profile();
                    back_status = false;
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = Home_Activity.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getApplicationContext().getResources().getColor(R.color.colorPrimary));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        topbutton = (Button) toolbar.findViewById(R.id.savesearch);
        imageView_share = (ImageView) toolbar.findViewById(R.id.share_recent);
        coordinator_layout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        imageView_share.setVisibility(View.GONE);
        bottomnavView = Home_Activity.this.findViewById(R.id.bottom_nav_view);
        bottomnavView.setVisibility(View.VISIBLE);
        bottomnavView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //======================Internet conectivity=============================//
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (isNetworkAvailable()) {
                    //     Toast.makeText(context, "network available", Toast.LENGTH_SHORT).show();
                    if (isOnline()) {
              /*  if(Home_Checker.Is_comming_After_offline) {
                    Home_Checker.Is_comming_After_offline=false;
                    finish();
                    startActivity(getIntent());
                }*/


                        //     Toast.makeText(context, "online", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent1 = new Intent(Home_Activity.this, NoInternet.class);
                        startActivity(intent1);
                        //    Toast.makeText(context, "offline", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Intent intent1 = new Intent(Home_Activity.this, NoInternet.class);
                    startActivity(intent1);
                    //    Toast.makeText(context, "not available", Toast.LENGTH_SHORT).show();
                }

            }
        };

        //=========================== app update dialog start==================================//

        AppUpdaterUtils appUpdaterUtils = new AppUpdaterUtils(this)
                .setUpdateFrom(UpdateFrom.GOOGLE_PLAY)
                //.setUpdateFrom(UpdateFrom.GITHUB)
                //.setGitHubUserAndRepo("javiersantos", "AppUpdater")
                //...
                .withListener(new AppUpdaterUtils.UpdateListener() {
                    @Override
                    public void onSuccess(Update update, Boolean isUpdateAvailable) {
                        try {
                            Log.d("Latest Version", update.getLatestVersion());
                            Log.d("Release notes", update.getReleaseNotes());
                            Log.d("URL", update.getUrlToDownload().toString());
                            Log.d("Is update available?", Boolean.toString(isUpdateAvailable));
                            if (isUpdateAvailable) {
                                showUpdateDialog(update.getLatestVersion(), update.getUrlToDownload().toString(), update.getReleaseNotes());
                            }
                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onFailed(AppUpdaterError error) {
                        Log.d("AppUpdater Error", "Something went wrong");
                    }
                });
        appUpdaterUtils.start();

        //=========================== app update dialog end==================================//

        //===========================app update notification start=================================//

        Intent alarm = new Intent(this, AlarmReceiver.class);
        boolean alarmRunning = (PendingIntent.getBroadcast(this, 0, alarm, PendingIntent.FLAG_NO_CREATE) != null);
        if (alarmRunning == false) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarm, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 21600000, pendingIntent);
        }
        //==============================app update notification end===========================================//
        //================================ firebase push notification ===========================================//
        String token = SharedPrefManager.getInstance(getApplicationContext()).getDeviceToken();
       /* if(token!=null)
        {
            Updatetoken updatetoken=new Updatetoken();
            updatetoken.execute(101);
//Toast.makeText(getApplicationContext(),token,Toast.LENGTH_SHORT).show();
        }else {
            //          Toast.makeText(getApplicationContext(),"null token"+token,Toast.LENGTH_SHORT).show();
            FirebaseCrash.report(new Exception("firsebase token is null to update in MyFirebaseMessagingService, user id : "+uid));
            //             Toast.makeText(getApplicationContext(),"Refresed tokken : "+" null "+token,Toast.LENGTH_LONG).show();
        }*/

        //================================ firebase push notification end===========================================//
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        Menu m = navigationView.getMenu();

        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);
            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }


        /*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        nav_userid = (TextView) header.findViewById(R.id.nav_header_idtext);
        menuimagelogo = (ImageView) header.findViewById(R.id.menuimagelogo);

        /******************************Header image screen size with screen*********************/

        DisplayMetrics displayMetrics;
        double screenHeight;
        double screenWidth;
        int lwi, lhi;
        int layout_height;


        displayMetrics = getResources().getDisplayMetrics();
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
        sharedPreferencesDatabase = new SharedPreferencesDatabase(Home_Activity.this);
        sharedPreferencesDatabase.createDatabase();

      /*  lwi = (int) (screenWidth * 0.50);
        lhi = (int) (screenHeight * 0.20);*/
        layout_height = (int) (screenHeight * 0.10);
        menuimagelogo.getLayoutParams().height = layout_height;
        menuimagelogo.requestLayout();
        String input = SharedPrefManager.getInstance(Home_Activity.this).getuser_details("name");
        String output = input.substring(0, 1).toUpperCase() + input.substring(1);
        nav_userid.setText("Welcome " + output);

        MainHome_Frag mainHome_frag = new MainHome_Frag();
        FragmentTransaction devicetrans = getSupportFragmentManager().beginTransaction();
        devicetrans.replace(R.id.frame_container, mainHome_frag, "home");
        //devicetrans.addToBackStack(mainHome_frag.getClass().toString());
        devicetrans.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //  getSupportFragmentManager().popBackStack(getClass().toString(),0);
            try {
                super.onBackPressed();
                topbutton.setVisibility(View.GONE);
            } catch (Exception e) {

            }
        }

    }



  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        topbutton.setVisibility(View.GONE);
        if (id == R.id.nav_Home) {
            back_status = true;
            MainHome_Frag mainHome_frag = new MainHome_Frag();
            FragmentTransaction devicetrans = getSupportFragmentManager().beginTransaction();
            devicetrans.replace(R.id.frame_container, mainHome_frag, "home");
            devicetrans.addToBackStack(mainHome_frag.getClass().toString());
            devicetrans.commit();
            // Handle the camera action
        } else if (id == R.id.nav_Property) {
            Myproperty myproperty = new Myproperty();
            FragmentTransaction devicetrans = getSupportFragmentManager().beginTransaction();
            devicetrans.replace(R.id.frame_container, myproperty);
            devicetrans.addToBackStack(myproperty.getClass().toString());
            devicetrans.commit();
            back_status = false;
        } else if (id == R.id.nav_myrequirement) {
            Show_Requirement myproperty = new Show_Requirement();
            FragmentTransaction devicetrans = getSupportFragmentManager().beginTransaction();
            devicetrans.replace(R.id.frame_container, myproperty);
            devicetrans.addToBackStack(myproperty.getClass().toString());
            devicetrans.commit();
            back_status = false;
        } else if (id == R.id.nav_Search) {

            Search_Property_Frag search_property_frag = new Search_Property_Frag();
            FragmentTransaction devicetrans = getSupportFragmentManager().beginTransaction();
            devicetrans.replace(R.id.frame_container, search_property_frag);
            devicetrans.addToBackStack(search_property_frag.getClass().toString());
            devicetrans.commit();
            back_status = false;

        } else if (id == R.id.nav_saved) {

            Saved_Search search_property_frag = new Saved_Search();
            FragmentTransaction devicetrans = getSupportFragmentManager().beginTransaction();
            devicetrans.replace(R.id.frame_container, search_property_frag);
            devicetrans.addToBackStack(search_property_frag.getClass().toString());
            devicetrans.commit();
            back_status = false;

        } else if (id == R.id.nav_Add_Property) {
            AddProperty_screen1 addproperty = new AddProperty_screen1();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, addproperty);
            fragmentTransaction.addToBackStack(addproperty.getClass().toString());
            fragmentTransaction.commit();
            back_status = false;
        } else if (id == R.id.nav_Add_Requirment) {
            back_status = false;
            Add_Requirement add_requirement = new Add_Requirement();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, add_requirement);
            fragmentTransaction.addToBackStack(add_requirement.getClass().toString());
            fragmentTransaction.commit();

        } else if (id == R.id.nav_Recent_Projects) {
           /* Recent_property recent_property=new Recent_property();
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container,recent_property);
            fragmentTransaction.addToBackStack(recent_property.getClass().toString());
            fragmentTransaction.commit();*/
            Recent_Projectlist recent_property = new Recent_Projectlist();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, recent_property);
            fragmentTransaction.addToBackStack(recent_property.getClass().toString());
            fragmentTransaction.commit();
            back_status = false;


        }
//        else if (id == R.id.nav_service) {
//           /* Recent_property recent_property=new Recent_property();
//            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.frame_container,recent_property);
//            fragmentTransaction.addToBackStack(recent_property.getClass().toString());
//            fragmentTransaction.commit();*/
//            Service_provider recent_property=new Service_provider();
//            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.frame_container,recent_property);
//            fragmentTransaction.addToBackStack(recent_property.getClass().toString());
//            fragmentTransaction.commit();


//        }
        else if (id == R.id.nav_faq) {
            back_status = false;
            Faq_fragment faq_fragment = new Faq_fragment();
            FragmentTransaction devicetrans = getSupportFragmentManager().beginTransaction();
            devicetrans.replace(R.id.frame_container, faq_fragment);
            devicetrans.addToBackStack(faq_fragment.getClass().toString());
            devicetrans.commit();

        } else if (id == R.id.nav_ic_shortlist) {
            Favourites favourites = new Favourites();
            FragmentTransaction devicetrans = getSupportFragmentManager().beginTransaction();
            devicetrans.replace(R.id.frame_container, favourites);
            devicetrans.addToBackStack(favourites.getClass().toString());
            devicetrans.commit();
            back_status = false;

        } else if (id == R.id.nav_ic_rateus) {
            back_status = false;
            final AlertDialog.Builder alertbBuilder = new AlertDialog.Builder(Home_Activity.this);
            alertbBuilder.setMessage("Rate Us For Serving You Better");
            alertbBuilder.setNegativeButton("Later", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                }
            });
            alertbBuilder.setPositiveButton("Rate Now", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Uri uri = Uri.parse("market://details?id=" + Home_Activity.this.getPackageName());
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    // To count with Play market backstack, After pressing back button,
                    // to taken back to our application, we need to add following flags to intent.
                    goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                            Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    try {
                        startActivity(goToMarket);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://play.google.com/store/apps/details?id=" + Home_Activity.this.getPackageName())));
                    }
                }
            });
            alertbBuilder.show();


        } else if (id == R.id.nav_logout) {
            final AlertDialog.Builder alertbBuilder = new AlertDialog.Builder(Home_Activity.this);
            alertbBuilder.setMessage("Are You Sure For Logged Out ?");

            alertbBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            alertbBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    MyCustomProgress_dialogue myCustomProgress_dialogue = new MyCustomProgress_dialogue(Home_Activity.this, R.color.button_color);
                    myCustomProgress_dialogue.show_dialogue();
                    SharedPrefManager.getInstance(Home_Activity.this).save_Login_status("login", false);

                    SharedPrefManager.getInstance(getApplicationContext()).saveuser_details("id", "");
                    SharedPrefManager.getInstance(getApplicationContext()).saveuser_details("name", "");
                    SharedPrefManager.getInstance(getApplicationContext()).saveuser_details("lname", "");
                    SharedPrefManager.getInstance(getApplicationContext()).saveuser_details("username", "");
                    SharedPrefManager.getInstance(getApplicationContext()).saveuser_details("mobile", "");
                    SharedPrefManager.getInstance(getApplicationContext()).saveuser_details("landline", "");

                    myCustomProgress_dialogue.dismiss_dialogue();

                    Intent intent = new Intent(Home_Activity.this, Splash.class);
                    startActivity(intent);
                    finish();

                }
            });
            alertbBuilder.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
    }

    public void onToggle(View view) {
        ((RadioGroup) view.getParent()).check(view.getId());
        // app specific stuff ..
        ToggleButton tb = (ToggleButton) view;
        Pname_setter pname_setter = new Pname_setter();
        pname_setter.setPname(tb.getText().toString());
        //Toast.makeText(this, ""+pname_setter, Toast.LENGTH_SHORT).show();
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.create("sans-serif", Typeface.NORMAL);
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    private Boolean isOnline() {
        try {
            java.lang.Process process = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = process.waitFor();
            boolean reachable = (returnVal == 0);
            return reachable;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    public void onStart() {
        super.onStart();
        String name = String.valueOf(ConnectivityManager.CONNECTIVITY_ACTION);

        registerReceiver(broadcastReceiver, new IntentFilter(name));

    }

    private void showUpdateDialog(String latestVersion, String url, String releaseNotes) {
        final android.support.v7.app.AlertDialog.Builder dialogBuilder = new android.support.v7.app.AlertDialog.Builder(new ContextThemeWrapper(Home_Activity.this, R.style.myUpdateDialog));
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.updatedialog, null);
        dialogBuilder.setView(dialogView);
        TextView text = (TextView) dialogView.findViewById(R.id.text);
        text.setText("A new version of PropertyBull is available. Please update app to version " + latestVersion + " and get latest feature of app");
        dialogBuilder.setPositiveButton("Update Now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://details?id=" + Home_Activity.this.getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + Home_Activity.this.getPackageName())));
                }
            }
        });
        dialogBuilder.setNegativeButton("Not Now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                contact_alert.dismiss();
            }
        });
        ImageView cross = (ImageView) dialogView.findViewById(R.id.cross);
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contact_alert.dismiss();
            }
        });


        // dialogBuilder.setTitle("Custom dialog");
        //  dialogBuilder.setMessage("Enter text below");


        contact_alert = dialogBuilder.create();

        contact_alert.show();

    }
 /*   class Updatetoken extends AsyncTask<Integer, Integer, Integer> {
        JSONObject json;
        ProgressDialog pd;
        JSONParser jsonParser = new JSONParser();

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(MainScreen.this);
            pd.setMessage("Updating...");
            //  pd.setCancelable(false);
            pd.show();
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... arg0) {
            // TODO Auto-generated method stub
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();



            nameValuePairs.add(new BasicNameValuePair("id",uid));
            nameValuePairs.add(new BasicNameValuePair("token",token));
            json = jsonParser.makeHttpRequest("http://astrotrishla.com/wp-content/themes/twentysixteen-child/app/token.php", "POST", nameValuePairs);


            return null;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            try {
                int success = json.getInt("success");
                if (success==1)
                {
                    Toast.makeText(MainScreen.this,"Updated successfully",Toast.LENGTH_SHORT).show();
                    //         Log.d("ud",token);
                }
                else {
                    Toast.makeText(MainScreen.this,"Updaation failed",Toast.LENGTH_SHORT).show();
                    FirebaseCrash.report(new Exception("updation feiled, user id :"+userid));

                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MainScreen.this,"Something went wrong on server",Toast.LENGTH_SHORT).show();
            }

            // TODO Auto-generated method stub
            pd.dismiss();

        }
    }*/


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        imageView_share.setVisibility(View.GONE);
        bottomnavView.setVisibility(View.VISIBLE);
        if (isRecentCall == true) {

        } else {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                switch (event.getAction()) {
                    case KeyEvent.ACTION_DOWN:
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                        if (back_status) {
                            if (event.getDownTime() - lastPressedTime < PERIOD) {
                                ExitApp();
                            } else {
                                ExitApp();
                                lastPressedTime = event.getEventTime();
                            }
                        } else {
                            MainHome_Frag test = (MainHome_Frag) getSupportFragmentManager().findFragmentByTag("home");

                            FragmentManager fm = getSupportFragmentManager();
                            if (fm != null) {
                                fm.popBackStack();
                                if (test != null && test.isVisible()) {
                                    ExitApp();
                                } else {
                                    //Whatever
                                }

                            }
                        }
                        return true;
                }
            }
        }

        return false;

    }

    private void ExitApp() {
        new android.support.v7.app.AlertDialog.Builder(Home_Activity.this)
                .setTitle(getString(R.string.app_name))
                .setMessage("Are you sure to exit app")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //    Intent intent = new Intent(MainActivity.this,)
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();
    }


    @Override
    public void CallFragment(String str_name) {
        if (str_name.equals("Search\n Properties")) {

            Search_Property_Frag search_property_frag = new Search_Property_Frag();
            FragmentTransaction fragmentTransaction = Home_Activity.this.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, search_property_frag);
//            fragmentTransaction.addToBackStack(search_property_frag.getClass().toString());
            fragmentTransaction.addToBackStack(MainHome_Frag.class.getName());

            fragmentTransaction.commit();
            back_status = false;
        } else if (str_name.equals("Post Property \nFor Free")) {
            AddProperty_screen1 addProperty_screen1 = new AddProperty_screen1();
            FragmentTransaction fragmentTransaction = Home_Activity.this.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, addProperty_screen1);
            fragmentTransaction.addToBackStack(addProperty_screen1.getClass().toString());
            fragmentTransaction.commit();

            back_status = false;
        } else if (str_name.equals("Recent Projects")) {
            Recent_Projectlist recent_property = new Recent_Projectlist();
            FragmentTransaction fragmentTransaction = Home_Activity.this.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, recent_property);
            fragmentTransaction.addToBackStack(recent_property.getClass().toString());
            fragmentTransaction.commit();
            back_status = false;

        } else if (str_name.equals("My\n Requirements")) {
            Show_Requirement add_requirement = new Show_Requirement();
            FragmentTransaction fragmentTransaction = Home_Activity.this.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, add_requirement);
            fragmentTransaction.addToBackStack(add_requirement.getClass().toString());
            fragmentTransaction.commit();
            back_status = false;
        } else if (str_name.equals("My\n Properties")) {
            Myproperty viewProperties = new Myproperty();
            FragmentTransaction fragmentTransaction = Home_Activity.this.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, viewProperties);
            fragmentTransaction.addToBackStack(viewProperties.getClass().toString());
            fragmentTransaction.commit();
            back_status = false;
        } else if (str_name.equals("Register For \nHome Loan")) {
            HomeLoan_Fag homeLoanNew = new HomeLoan_Fag();
            FragmentTransaction fragmentTransaction = Home_Activity.this.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, homeLoanNew);
            fragmentTransaction.addToBackStack(homeLoanNew.getClass().toString());
            fragmentTransaction.commit();
            back_status = false;
        }
    }


    public void nav_home() {
        MainHome_Frag mainHome_frag = new MainHome_Frag();
        FragmentTransaction devicetrans = Home_Activity.this.getSupportFragmentManager().beginTransaction();
        devicetrans.replace(R.id.frame_container, mainHome_frag, "home");
        devicetrans.addToBackStack(mainHome_frag.getClass().toString());
        devicetrans.commit();
    }

    public void nav_search() {
        Search_Property_Frag search_property_frag = new Search_Property_Frag();
        FragmentTransaction devicetrans = Home_Activity.this.getSupportFragmentManager().beginTransaction();
        devicetrans.replace(R.id.frame_container, search_property_frag);
        devicetrans.addToBackStack(search_property_frag.getClass().toString());
        devicetrans.commit();

    }

    public void nav_profile() {
        Profile mainHome_frag = new Profile();
        FragmentTransaction devicetrans = Home_Activity.this.getSupportFragmentManager().beginTransaction();
        devicetrans.replace(R.id.frame_container, mainHome_frag);
        devicetrans.addToBackStack(mainHome_frag.getClass().toString());
        devicetrans.commit();
    }
}
