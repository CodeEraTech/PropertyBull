package com.doomshell.property_bull.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

/**
 * Created by Vipin on 7/14/2017.
 */

public class ImcomingSmsReciever extends BroadcastReceiver {
    // object of SmsManager

    SmsManager sms=SmsManager.getDefault();

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle=intent.getExtras();
        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    String data[]=message.split(" ");
                    String x=data[data.length-1].trim();


                  //  Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);

                    Intent myIntent = new Intent("otp");
                    myIntent.putExtra("message", x);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(myIntent);
                }

            }
        }
        catch (Exception e)
        {
            //Toast.makeText(context,"error"+e,Toast.LENGTH_LONG).show();
        }

    }
}




        // Show Alert

