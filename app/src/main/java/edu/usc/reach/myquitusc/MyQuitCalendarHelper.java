package edu.usc.reach.myquitusc;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Eldin on 1/10/15.
 */
public class MyQuitCalendarHelper {

    private static final  String[] stubTimes = new String[] {"", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" };
    //TODO: Fill out these classes

    public static boolean isWithinXNextHour(int minsBefore) {
        Calendar nowCal = Calendar.getInstance();
        Date nowTime = nowCal.getTime();
        SimpleDateFormat year = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat hour = new SimpleDateFormat("HH");
        String revertTimeYear = year.format(nowTime);
        String reverTimeHour = hour.format(nowTime);
        Log.d("MQU-CH","revertYear and Hour is " + revertTimeYear + " and " + reverTimeHour);
        String tenMinuteCheckTime = revertTimeYear + " " + reverTimeHour + ":" + (60-minsBefore) + ":" + "00";
        Log.d("MQU-CH","Ten minute check time is " + tenMinuteCheckTime);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        try {
            Date nextHourTime = sdf.parse(tenMinuteCheckTime);
            Log.d("MQU-CH","nextHourTime is " + nextHourTime);
            if(nowTime.after(nextHourTime)) {
                Log.d("MQU-CH","return true afternexthourTime");
                return true;
            }
            else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static String returnIntentFromSituation(Context context)  {
        String hourSituation = unassignHoursArray()[assignArrayPosition()];
        String parsedHourSituation;
        try {
             parsedHourSituation = hourSituation.substring(3);
        }
        catch(StringIndexOutOfBoundsException soeo) {
            return "No Match";
        }
        Log.d("MQU-CH","cast Situation position string is " + parsedHourSituation);
        String[] checkSituationArrayList = MyQuitPlanHelper.pullTasksList(context);
        int index = -1;
        int count = 0;
        for(String checkHour: checkSituationArrayList){
            if(checkHour.equalsIgnoreCase(parsedHourSituation)) {
                index = count;
            }
            count++;
        }
        if(index == -1){
            return "No Match";
        }
        else {
            return MyQuitPlanHelper.pullIntentsList(context)[index];
        }
    }

    public static String[] unassignHoursArray() {
        Calendar nowCal = Calendar.getInstance();
        Date nowTime = nowCal.getTime();
        SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/yyyy");
        try {
            String[] processHours = MyQuitCSVHelper.pullDateTimes(sdfDate.format(nowTime));
            String[] runThrough = new String[24];
            int count = 0;
            for(String holdOne: processHours){
                runThrough[count] = holdOne.substring(8);
                count ++;
            }
            return runThrough;

        } catch (IOException e) {
            e.printStackTrace();
            return stubTimes;
        }
    }



    public static int assignArrayPosition() {
        Calendar nowCal = Calendar.getInstance();
        Date nowTime = nowCal.getTime();
        SimpleDateFormat hour = new SimpleDateFormat("HH");
        String hourPull = hour.format(nowTime);
        int hourCast = Integer.valueOf(hourPull);
        if(hourCast == 23) {
            Log.d("MQU-CH","cast hour position on NULL");
            return 0;
        }
        else {
            Log.d("MQU-CH","cast hour position on " + hourCast);
            return (hourCast + 1);
        }

    }

    public static void decideCalendar (Context context) {
            if (isWithinXNextHour(59) & !returnIntentFromSituation(context).equalsIgnoreCase("No Match")) {
                pushActionCalendar(context);
            }

    }


    public static void pushActionCalendar(Context context) {
        Intent launchService = new Intent(context, MyQuitService.class);
        launchService.putExtra("Action","Calendar");
        launchService.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startService(launchService);
    }

}
