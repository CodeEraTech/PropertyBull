package com.doomshell.property_bull.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Anuj on 11/29/2016.
 */

public class ConnectionDetector {

    private Context context;

    public ConnectionDetector(Context context)
    {
        this.context=context;
    }
    public boolean isConnectingToInternet()
    {
        ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager!=null)
        {
            NetworkInfo[] info=connectivityManager.getAllNetworkInfo();
            if(info!=null)
            {
                for(int i=0;i<info.length;i++)
                    if(info[i].getState()==NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
            }
        }

        return false;
    }
}
