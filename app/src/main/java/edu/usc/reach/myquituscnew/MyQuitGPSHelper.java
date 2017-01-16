package edu.usc.reach.myquituscnew;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import edu.usc.reach.myquituscnew.Surveys.MyQuitRandomSurvey;

/**
 * Created by Eldin on 7/1/15.
 */
public class MyQuitGPSHelper {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

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
        if(pushGPSDataCheck(context)) {
            String[] pushGPS = getLocationArray(context);
            MyQuitPHP.postGPSEvent(MyQuitCSVHelper.pullLoginStatus("UserName"), pushGPS[0], pushGPS[1], pushGPS[2], pushGPS[3]);
        }
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

    /*
  **  Pre QD every 2 minutes for 10 minutes up to risky schedule every 2 minutes for 10 minutes after risky schedule
every 2 minutes for 10 minutes BEFORE and AFTER Random every 2 minutes for 10 minutes BEFORE and AFTER Smoking
 ** Post QD every 2 minutes for 10 minutes up to risky schedule every 2 minutes for 10 minutes after risky schedule
 ** every 2 minutes for 10 minutes when I Just Smoked is pressed every 2 minutes for 10 minutes when I want to smoke is pressed
     */

    private static boolean pushGPSDataCheck(Context context) {
        if(MyQuitCalendarHelper.isWithinXNextHour(10) && !MyQuitCalendarHelper.returnIntentFromSituation(context, true).
                equalsIgnoreCase("No Match") && !MyQuitCalendarHelper.isWithinXAfterHour(20)){
            return true;
        }
        else if (!MyQuitCalendarHelper.isWithinXNextHour(10) && !MyQuitCalendarHelper.returnIntentFromSituation(context, false).
                equalsIgnoreCase("No Match") && MyQuitCalendarHelper.isWithinXAfterHour(10)){
            return true;
        }
        else if (compareCigCraveTimes(true)){
            return true;
        }
        else if (compareCigCraveTimes(false)){
            return true;
        }
        else if (MyQuitExperienceSampling.isXMinutesBeforeRandom(10)){
            return true;
        }
        else if (isWithinTenAfterPresentedEMA(MyQuitRandomSurvey.KEY_SURVEY_SUCCESS)){
            return true;
        }
        else {
            return false;
        }

    }

    private static boolean compareCigCraveTimes(Boolean cigCrave){ // true = cig, false = crave
        try {
                Date cigTime;
            if(cigCrave) {
                 cigTime = MyQuitCSVHelper.pullCigaretteTime(MyQuitCSVHelper.getFullDate());
            }
            else {
                 cigTime = MyQuitCSVHelper.pullCraveTime();
            }
            Calendar cigCal = Calendar.getInstance();
            cigCal.setTime(cigTime);
            cigCal.add(Calendar.MINUTE,10);
            cigTime = cigCal.getTime();

            Calendar nowCal = Calendar.getInstance();
            Date nowTime = nowCal.getTime();

            return cigTime.after(nowTime);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }


    private static boolean isWithinTenAfterPresentedEMA(int emaType) {
        String lastEvent = MyQuitCSVHelper.pullLastEvent("intentPresented", emaType);
        Calendar nowCal = Calendar.getInstance();
        Date nowTime = nowCal.getTime();
        try {
            nowCal.setTime(sdf.parse(lastEvent));
            nowCal.add(Calendar.MINUTE,10);
            return nowTime.before(nowCal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        catch (NullPointerException nee) {
            nee.printStackTrace();
            return false;
        }
    }





}
