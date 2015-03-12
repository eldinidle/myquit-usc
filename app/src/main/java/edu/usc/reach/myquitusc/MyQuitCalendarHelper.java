package edu.usc.reach.myquitusc;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

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

    public static boolean isWithinXAfterHour(int minsAfterLimit) {
        Calendar nowCal = Calendar.getInstance();
        Date nowTime = nowCal.getTime();
        SimpleDateFormat year = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat hour = new SimpleDateFormat("HH");
        String revertTimeYear = year.format(nowTime);
        String reverTimeHour = hour.format(nowTime);
        Log.d("MQU-CH","revertYear and Hour is " + revertTimeYear + " and " + reverTimeHour);
        String tenMinuteCheckTime = revertTimeYear + " " + reverTimeHour + ":" + (minsAfterLimit) + ":" + "00";
        Log.d("MQU-CH","Ten minute check time is " + tenMinuteCheckTime);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        try {
            Date nextHourTime = sdf.parse(tenMinuteCheckTime);
            Log.d("MQU-CH","nextHourTime is " + nextHourTime);
            if(nowTime.before(nextHourTime)) {
                Log.d("MQU-CH","return true beforenexthourTime");
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

    public static boolean isThisWakingUp(boolean preTenMinutes)  {
        String hourSituation = unassignHoursArray()[assignArrayPosition(preTenMinutes)];
        return hourSituation.equalsIgnoreCase("Waking up");
    }

    public static String returnIntentFromSituation(Context context, boolean preTenMinutes)  {
        String hourSituation = unassignHoursArray()[assignArrayPosition(preTenMinutes)];
        String parsedHourSituation;
        try {
             parsedHourSituation = hourSituation.substring(3);
        }
        catch(StringIndexOutOfBoundsException soeo) {
            return "No Match";
        }
        Log.d("MQU-CH","cast Situation position string is " + parsedHourSituation);
        String[] checkSituationArrayList = MyQuitPlanHelper.pullTasksList(context, false);
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
            return MyQuitPlanHelper.pullIntentsList(context,false)[index];
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

    private static int  castHour() {
        Calendar nowCal = Calendar.getInstance();
        Date nowTime = nowCal.getTime();
        SimpleDateFormat hour = new SimpleDateFormat("HH");
        String hourPull = hour.format(nowTime);
        return Integer.valueOf(hourPull);
    }

    public static int assignArrayPosition(boolean preTenMinutes) {
        int hourCast = castHour();
        if(preTenMinutes) {
            if (hourCast == 23) {
                Log.d("MQU-CH", "cast hour position on NULL");
                return 0;
            } else {
                Log.d("MQU-CH", "cast hour position on " + hourCast);
                return (hourCast + 1);
            }
        }
        else {
            if (hourCast == 0) {
                Log.d("MQU-CH", "cast hour position on NULL");
                return 0; //TODO: Make this go back a day
            } else {
                Log.d("MQU-CH", "cast hour position on " + hourCast);
                return (hourCast);
            }
        }

    }

    public static void decideCalendar (Context context) {
            if (isWithinXNextHour(10) && !returnIntentFromSituation(context,true).
                    equalsIgnoreCase("No Match") && !isThisWakingUp(true) && !isWithinXAfterHour(20)) {
                Log.d("MQU-CH", "Decide Loop > 50 minutes");
                if(didLastReadPassMinutes(30)) {
                    Log.d("MQU-CH", "50 minutes > YES");
                    boolean prompt = Math.random() > 0.5;
                    if (prompt) {
                        setSession(context, true, false);
                        pushActionCalendar(context);
                    }
                    else {
                        setSession(context, true, true);
                    }
                }
                else if(!lastSessionRead() & !didLastReadPassMinutes(30)){
                    Log.d("MQU-CH", "50 minutes > No No");
                    pushActionCalendar(context);
                }

            }
            else if (!isWithinXNextHour(10) && !returnIntentFromSituation(context,false).
                    equalsIgnoreCase("No Match") && isWithinXAfterHour(20)){
                Log.d("MQU-CH","Decide Loop < 20 minutes");
                if(didLastReadPassMinutes(30)){
                    Log.d("MQU-CH", "20 minutes > YES");
                    boolean prompt = Math.random() > 0.5;
                    if(prompt){
                        setSession(context,false,false);
                        pushActionCalendar(context);
                    }
                    else {
                        setSession(context,false,true);
                    }
                }
                else if(!lastSessionRead() & !didLastReadPassMinutes(30)){
                    Log.d("MQU-CH", "20 minutes > No No");
                    pushActionCalendar(context);
                }
            }
            else if (!isWithinXNextHour(10) && !isWithinXAfterHour(20)){
                NotificationManager mNotificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.cancel(3);
            }

    }

    public static int returnEMAPromptTime(int startPos) {
        String[] checkHours = unassignHoursArray();
        String[] situationList = new String[24];
        int count = 0;
        for(String oneSitu: checkHours){
            situationList[count] = oneSitu;
            count++;
        }
        List<Integer> integerArrayTable = new ArrayList<Integer>();
        int counter = 0;
        Log.d("MQU-CH","we're at situLoop with list size = " + situationList.length);
        situLoop:
        for(String oneSitu: situationList){
            try {
                oneSitu.substring(3);
                if(counter<(startPos)){
                    integerArrayTable.add(counter);
                    Log.d("MQU-CH","begin counter at " + counter);
                    counter++;
                }
                if(oneSitu.equalsIgnoreCase(situationList[startPos])
                        & counter>(startPos-1)) {
                    Log.d("MQU-CH","found current time at " + counter);
                    integerArrayTable.add(counter);
                    counter++;
                }
                else if(!oneSitu.equalsIgnoreCase(situationList[startPos])
                        & counter>startPos) {
                    break situLoop;
                }
            }
            catch(StringIndexOutOfBoundsException soeo) {
                if(!oneSitu.equalsIgnoreCase(situationList[startPos])
                        & counter>startPos) {
                    break situLoop;
                }
                else {
                    integerArrayTable.add(counter);
                    counter++;
                }
            }
        }
        logViewedHours(MyQuitCSVHelper.getFullDate(),integerArrayTable);
        return integerArrayTable.size();
    }

    public static void setUpEMAPrompt(int emaStartPosition, String situation, String intention) {
        int promptedHour = returnEMAPromptTime(emaStartPosition) - 1;
        //SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy");
        //SimpleDateFormat newsdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String calledFullTime = MyQuitCSVHelper.getFullDate() + " " + new DecimalFormat("00").format(promptedHour)
                + ":45:00";
        String[] pushEvent = new String [] {calledFullTime, situation, intention};
        try {
            String stepDate = MyQuitCSVHelper.getFullDate().replaceAll("/", "_");
            String fileName = "CalendarEMATimes" + stepDate + ".csv";
            CSVWriter writer = new CSVWriter(new FileWriter(MyQuitCSVHelper.emaPath +
                    fileName, true));
            writer.writeNext(pushEvent);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> returnCalendarEMA() {
        try {
            String stepDate = MyQuitCSVHelper.getFullDate().replaceAll("/", "_");
            String fileName = "CalendarEMATimes" + stepDate + ".csv";
            CSVReader reader = new CSVReader(new FileReader(MyQuitCSVHelper.emaPath +
                    fileName));
            List<String[]> returner = reader.readAll();
            reader.close();
            return returner;
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("MQU-CH","No EMA Calendar data");
            return null;
        }
    }

    /**
    *
    * Logs hours viewed by the application.
    *
    *
     */
    public static void logViewedHours(String calledDate, List<Integer> calledPositions) {
        Log.d("MQU-CH","pushed lock is" + calledPositions.size());
        String[] pushTimes = new String[calledPositions.size()];
        for(int i = 0; i < calledPositions.size(); i++) {
            pushTimes[i] = calledPositions.get(i).toString();
        }
        String stepDate = calledDate.replaceAll("/", "_");
        String fileName = stepDate + ".csv";
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(MyQuitCSVHelper.calPath + "CADT" + fileName));
            writer.writeNext(pushTimes);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static boolean didLastReadPassMinutes(int minutes) {
        Calendar nowCal = Calendar.getInstance();
        nowCal.add(Calendar.MINUTE,(0-minutes));
        Date testTime = nowCal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        try {
            Date thenTime = sdf.parse(lastSessionTime());
            Log.d("MQU-CH","Compare Time is" + sdf.format(testTime));
            Log.d("MQU-CH","Time is" + lastSessionTime());
            return testTime.after(thenTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void setSession(Context context, boolean preTenMinutes, boolean setSessionRead) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(MyQuitCSVHelper.calPath + "CalIntentSessions.csv", true));
            //TODO: Implement session system
            String intention = returnIntentFromSituation(context, preTenMinutes);
            String viewed = String.valueOf(setSessionRead);
            String[] pushNext = new String[] { intention, viewed, MyQuitCSVHelper.getFulltime()};
            writer.writeNext(pushNext);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static boolean lastSessionRead() {
        try {
            CSVReader reader = new CSVReader(new FileReader(MyQuitCSVHelper.calPath + "CalIntentSessions.csv"));
            List<String[]> fullRead = reader.readAll();
            reader.close();
            String[] lineRead = fullRead.get(fullRead.size()-1);
            return Boolean.valueOf(lineRead[1]);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String lastSessionTime() {
        try {
            CSVReader reader = new CSVReader(new FileReader(MyQuitCSVHelper.calPath + "CalIntentSessions.csv"));
            List<String[]> fullRead = reader.readAll();
            reader.close();
            String[] lineRead = fullRead.get(fullRead.size()-1);
            Log.d("MQU-CH", "Time read tag is" + lineRead[2]);
            return String.valueOf(lineRead[2]);
        } catch (IOException e) {
            e.printStackTrace();
            return "09/29/1988 01:01:01";
        }
    }

    public static int returnLockedHour(String calledDate) {
        String stepDate = calledDate.replaceAll("/", "_");
        String fileName = stepDate + ".csv";
        try {
            CSVReader reader = new CSVReader(new FileReader(MyQuitCSVHelper.calPath + "CADT" + fileName));
            String[] lineRead = reader.readNext();
            reader.close();
            return lineRead.length;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }


    public static void pushActionCalendar(Context context) {
        Intent launchService = new Intent(context, MyQuitService.class);
        launchService.putExtra("Action","Calendar");
        launchService.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startService(launchService);
    }

}
