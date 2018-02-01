package com.lnsel.ambujaneotiasales.helpers.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.lnsel.ambujaneotiasales.presenters.ServicePresenter;
import com.lnsel.ambujaneotiasales.utils.UrlUtil;
import com.lnsel.ambujaneotiasales.utils.SharedManagerUtil;

/**
 * Created by apps2 on 6/1/2017.
 */
public class LocationService extends Service {
    SharedManagerUtil session;

    private ServicePresenter service;

    boolean isGPSEnable;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        //Toast.makeText(this, " MyService Created ", Toast.LENGTH_LONG).show();

        // Session Manager
        session = new SharedManagerUtil(this);

        service = new ServicePresenter(this);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 20 seconds
                //Toast.makeText(getApplicationContext(), "Service is running", Toast.LENGTH_SHORT).show();
                Log.d("Service: ","running");

                locationUpdate();

                handler.postDelayed(this, 60000);
            }
        }, 20000);  //the time is in miliseconds
    }

    @Override
    public void onStart(Intent intent, int startId) {
        //Toast.makeText(this, " MyService Started", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        //Toast.makeText(this, "MyService Stopped", Toast.LENGTH_LONG).show();
    }

    public void locationUpdate(){

        if(isNetworkAvailable()){

            if(session.isLoggedIn()){

                GPSService mGPSService = new GPSService(this);
                mGPSService.getLocation();

                if (mGPSService.isLocationAvailable == false) {
                    Toast.makeText(this, "Location not available, Open GPS", Toast.LENGTH_SHORT).show();
                } else {

                    SimpleDateFormat stf = new SimpleDateFormat("HH:mm");
                    String current_time = stf.format(new Date());

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String current_date = sdf.format(new Date());

                    // Getting location co-ordinates
                    double latitude = mGPSService.getLatitude();
                    double longitude = mGPSService.getLongitude();

                    String currentLat = Double.toString(latitude);
                    String currentLong = Double.toString(longitude);
                    //Toast.makeText(this, "Latitude:" + latitude + " | Longitude: " + longitude +" | UserId: "+session.getUserID(), Toast.LENGTH_LONG).show();

                    String isTripStart, trpId;

                    if(session.isTripStart()){
                        isTripStart = "true";
                        trpId = session.getTripId();
                    }else{
                        isTripStart="false";
                        trpId = "0";
                    }

                    if(currentLat.equals("0.0")){
                        Toast.makeText(this, "GPS not Responding, Check your GPS", Toast.LENGTH_LONG).show();
                    }else{
                        service.getLocationUpdateService(UrlUtil.LOCATION_UPDATE_URL+"?userId="+session.getUserID()+"&userLoginRecordId="+session.getUserLoginRecordId()+"&crlDate="+current_date+"&crlTime="+current_time+"&crlLat="+currentLat+"&crlLong="+currentLong+"&isTripStart="+isTripStart+"&trpId="+trpId);
                    }


                }

            }

        }

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
