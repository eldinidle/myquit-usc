package edu.usc.reach.myquitusc;

import android.util.Log;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Eldin on 1/24/15.
 */
public class MyQuitAutoAssign {

    final public static int minimumLabels = 5;

    private static String[] pullNewTimes() throws IOException {
        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        if(dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY){
            return MyQuitCSVHelper.pullDateTimes("DEFAULT_WEEKEND");
        }
        else {
            return MyQuitCSVHelper.pullDateTimes("DEFAULT_WEEKDAY");
        }
    }

    public static int labelCount(String calledDate) {
        try {
            String[] pulledTimes = MyQuitCSVHelper.pullDateTimes(calledDate);
            int count = 0;
            for (String array : pulledTimes) {
                if (array.length() > 8) {
                    count++;
                }
            }
            return (count);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            return 0;
        }
    }

    public static boolean runEMAOffAlgorithm() {
        Log.d("MQU-ALGORITHM","Running decision tree for II prompt");
        String numberOfPrompts = MyQuitCSVHelper.pullLoginStatus("AlgorithmCount");
        int numPrompts = Integer.valueOf(numberOfPrompts);
        Log.d("MQU-ALGORITHM","Integer is " + numberOfPrompts);
        if(numPrompts>8){
            Log.d("MQU-ALGORITHM","Algorithm is within the 8 loop");
            return Math.random() < (1/(3-(0.125*(24-numPrompts))));
        }
        else {
            Log.d("MQU-ALGORITHM","Algorithm is returning true, not within 8 loop");
            return true;
        }

    }

    public static boolean minimumLabelConfirm(String calledDate) {
        try {
            String[] pulledTimes = MyQuitCSVHelper.pullDateTimes(calledDate);
            int count = 0;
            for (String array : pulledTimes) {
                if (array.length() > 8) {
                    count++;
                }
            }
            return (count >= minimumLabels);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
    }

    public static void autoAssignCalendar() {
        try {
            if(!minimumLabelConfirm(MyQuitCSVHelper.getFullDate())){
                String [] newTimes = pullNewTimes();
                MyQuitCSVHelper.pushDateTimes(MyQuitCSVHelper.getFullDate(),newTimes);
                MyQuitCSVHelper.logLoginEvents("AlgorithmCount",String.valueOf(labelCount(MyQuitCSVHelper.getFullDate())),MyQuitCSVHelper.getFulltime());
            }
            else {
                MyQuitCSVHelper.logLoginEvents("AlgorithmCount",String.valueOf(labelCount(MyQuitCSVHelper.getFullDate())),MyQuitCSVHelper.getFulltime());
            }
        } catch (IOException e) {
            e.printStackTrace();
            }
        }

    public static void populateCalendar() {
        
    }
}
