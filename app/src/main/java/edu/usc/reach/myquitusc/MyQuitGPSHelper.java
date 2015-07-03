package edu.usc.reach.myquitusc;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Eldin on 7/1/15.
 */
public class MyQuitGPSHelper {


    Context mContext;

    static Criteria criteria = new Criteria();


    public MyQuitGPSHelper(Context mContext){
        this.mContext=mContext;
    }

    public static Location getLocation(Context context) {
        LocationManager mlocManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        LocationListener mlocListener = new MyQuitGPS();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String provider = mlocManager.getBestProvider(criteria,true);
        if(provider != null) {
            mlocManager.requestLocationUpdates(provider, 0, 0, mlocListener);
            return mlocManager.getLastKnownLocation(provider);
        }
        else {return null;}
    }

    public static String[] getLocationArray(Context context) {
        String fullTime = MyQuitCSVHelper.getFulltime();
        if(getLocation(context) != null) {
            String latitude = String.valueOf(getLocation(context).getLatitude());
            String longitude = String.valueOf(getLocation(context).getLongitude());
            String accuracy = String.valueOf(getLocation(context).getAccuracy());
            Log.d("MQU-GPS","time" + fullTime);
            Log.d("MQU-GPS","latitude" + latitude);
            Log.d("MQU-GPS","longitude" + longitude);
            Log.d("MQU-GPS","accuracy" + accuracy);
            return new String[] {fullTime, latitude, longitude, accuracy};
        }
        else {return new String[] {fullTime,"0","0","0"};}

    }



    public static void pushGPSData(Context context) {
        String[] pushGPS = getLocationArray(context);
        MyQuitPHP.postGPSEvent(MyQuitCSVHelper.pullLoginStatus("UserName"),pushGPS[0],pushGPS[1],pushGPS[2],pushGPS[3]);
        /*
        String calledDate = MyQuitCSVHelper.getFullDate();
        String stepDate = calledDate.replaceAll("/", "_");
        String fileName = "GPS" + stepDate +  ".csv";
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(MyQuitCSVHelper.logPath + fileName, true));
            String[] pushGPS = getLocationArray(context);
            writer.writeNext(pushGPS);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }




}
