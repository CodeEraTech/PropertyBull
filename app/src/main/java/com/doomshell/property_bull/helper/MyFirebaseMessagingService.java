package com.doomshell.property_bull.helper;

import android.content.Intent;
import android.util.Log;

import com.doomshell.property_bull.Splash;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Anuj on 12/19/2016.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    Intent intent;
    String ck;
    String token;
    String stitle,smessage,simage,sclickaction;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

      //  Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

//        Toast.makeText(getApplicationContext(),remoteMessage.getNotification().getBody(),Toast.LENGTH_SHORT).show();

        MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());
        intent = new Intent(getApplicationContext(), Splash.class);

      //  remoteMessage.getNotification().getClickAction();

        if (remoteMessage.getData().size() > 0) {
//            HashMap<String,String> data = (HashMap<String, String>) remoteMessage.getData();

            Map<String, String> data = remoteMessage.getData();
            JSONObject jsonObject = new JSONObject(data);

            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
  /*          data.get("title").toString();
            data.get("message").toString();
            data.get("image").toString();
*/
            try {
                jsonObject.getString("data");
                JSONObject mydata = new JSONObject(jsonObject.getString("data"));
                stitle =mydata.getString("title");
                ck= ""+Splash.class.getPackage()+""+mydata.getString("click_action");
                smessage = mydata.getString("message");
                simage = mydata.getString("image");
                /*sclickaction=mydata.getString("click_action");
                remoteMessage.getNotification().getClickAction();*/
                Class cls = Class.forName(ck);
                intent = new Intent(getApplicationContext(), cls);
              //  intent=new Intent(getApplicationContext(),Splash.class);

                Log.d(TAG, "Activity name: " + sclickaction);

                if (simage.equals("null") || simage == null)
                    mNotificationManager.showSmallNotification(stitle, smessage, intent);
                else
                    mNotificationManager.showBigNotification(stitle, smessage, simage, intent);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {

            //if there is no image
            mNotificationManager.showSmallNotification(""+remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), intent);

        //    Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }






    }



}
