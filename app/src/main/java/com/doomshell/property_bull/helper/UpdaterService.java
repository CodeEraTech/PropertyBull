package com.doomshell.property_bull.helper;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

/**
 * Created by Doom_Anuj on 11/16/2017.
 */

public class UpdaterService extends Service {
    private boolean isRunning;
    private Context context;
    private Thread backgroundThread;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        this.context = this;
        this.isRunning = false;
        this.backgroundThread = new Thread(myTask);
    }

    private Runnable myTask = new Runnable() {
        public void run() {

            CheckNotication checkNotication=new CheckNotication();
            checkNotication.execute();
            stopSelf();
        }
    };

    @Override
    public void onDestroy() {
        this.isRunning = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!this.isRunning) {
            this.isRunning = true;
            this.backgroundThread.start();
        }
        return START_STICKY;
    }

    public void showNotification()
    {

//        AppUpdaterUtils appUpdaterUtils = new AppUpdaterUtils(context);
//        appUpdaterUtils.setUpdateFrom(UpdateFrom.GOOGLE_PLAY)
//                //.setUpdateFrom(UpdateFrom.GITHUB)
//                //.setGitHubUserAndRepo("javiersantos", "AppUpdater")
//                //...
//                .withListener(new AppUpdaterUtils.UpdateListener() {
//                    @Override
//                    public void onSuccess(Update update, Boolean isUpdateAvailable) {
//                        try {
//
//
//                            MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());
//                            //intent = new Intent(getApplicationContext(), MainActivity.class);
//                            Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
//                            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
//                            // To count with Play market backstack, After pressing back button,
//                            // to taken back to our application, we need to add following flags to intent.
//                            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
//                                    Intent.FLAG_ACTIVITY_NEW_TASK |
//                                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//
//                            if(isUpdateAvailable)
//                            {
//                                mNotificationManager.showSmallNotification("PropertyBull Update", "A new version of PropertyBull is available. Please update app to version "+update.getLatestVersion()+" and get latest feature of app", goToMarket);
//                                //  mNotificationManager.showSmallNotification("Tossclick Update", "A new version of Tossclick is available. Please update app to version "+update.getLatestVersion()+" and get latest feature of app", intent);
//                                // showUpdateDialog(update.getLatestVersion(),update.getUrlToDownload().toString(),update.getReleaseNotes());
//                            }
//                            else
//                            {
//
//                            }
//                        }
//                        catch (Exception e)
//                        {
//
//                        }
//                    }
//
//                    @Override
//                    public void onFailed(AppUpdaterError error) {
//                        Log.d("AppUpdater Error", "Something went wrong");
//                    }
//                });
//        appUpdaterUtils.start();
    }
    class CheckNotication extends AsyncTask<Integer,Integer,Integer>
    {

        @Override
        protected Integer doInBackground(Integer... integers) {
            return null;
        }
        @Override
        public void onPostExecute(Integer result)
        {
            super.onPostExecute(result);
            showNotification();
        }
    }

}
