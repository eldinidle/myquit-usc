package edu.usc.reach.myquitusc;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Created by Eldin on 6/25/15.
 */
public class MyQuitGPS implements LocationListener {

    /*
    Context mContext;
    public MyQuitGPS(Context mContext){
        this.mContext = mContext;
    }

    public void testthis(){
        LocationManager mlocManager = (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);
        LocationListener mlocListener = new MyQuitGPS(mContext);


        mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
    }
    */

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
