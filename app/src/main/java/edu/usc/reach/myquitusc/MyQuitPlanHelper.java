package edu.usc.reach.myquitusc;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Eldin on 1/1/15.
 */
public class MyQuitPlanHelper {
    private static String[] lunchActivity = new String[] {"Lunch with Others",
            "When I'm having lunch with others...",""};
    private static String[] clubActivity = new String[] {"Clubbing",
            "When I go out clubbing...",""};
    private static String[] hangingOut = new String[] {"Hanging out",
            "When I hang out with friends...",""};


    public static final List<String[]> baseList = Arrays.asList(lunchActivity, clubActivity,hangingOut);


    //TODO: Add code to allow for custom events
    // public static void addCustomIntent(Context context, )

    public static String[] pullTasksList(Context context) {
        String fileName = "ACTIVITY_PAIRING" + ".csv";
        List<String[]> pullAll = pullBaseList(context);
            int length = pullAll.size();
            int count = 0;
            String[] newPush = new String[length];
            for(String[] allPulled: pullAll) {
                newPush[count] = allPulled[0];
                count++;
            }
            return newPush;
    }

    public static boolean pushIntentBaseList(Context context, int position, String specifyItem) {
        String fileName = "ACTIVITY_PAIRING" + ".csv";
        try {
            List<String[]> pullAll = pullBaseList(context);
            int length = pullAll.size();
            int count = 0;
            ArrayList<String[]> newPush = new ArrayList<>();
            for(String[] allPulled: pullAll) {
                Log.d("MQU","Position is" + position + "count is" + count +"size is"+length);
                Log.d("MQU","Pulled" + allPulled[0]);
                if(count==position){
                    Log.d("MQU","Changed" + allPulled[0]);
                    allPulled[2] = specifyItem;
                    newPush.add(allPulled);
                }
                else {
                    Log.d("MQU","Pushed" + allPulled[0]);
                    newPush.add(allPulled);
                }
                count++;
            }
            CSVWriter writer = new CSVWriter(new FileWriter(MyQuitCSVHelper.calPath + fileName));
            writer.writeAll(newPush);
            writer.close();
            return true;
        }
        catch(IOException eo) {
            Toast.makeText(context,"Please restart app and try again",Toast.LENGTH_LONG).show();
            return false;
        }
    }


    public static boolean pushBaseList(Context context, List<String[]> newBaseList) {
        String fileName = "ACTIVITY_PAIRING" + ".csv";
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(MyQuitCSVHelper.calPath + fileName));
            writer.writeAll(newBaseList);
            writer.close();
            return true;
        }
        catch(IOException eo) {
            Toast.makeText(context,"Please restart app and try again",Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public static List<String[]> pullBaseList(Context context) {
        String fileName = "ACTIVITY_PAIRING" + ".csv";
        try {
            CSVReader reader = new CSVReader(new FileReader(MyQuitCSVHelper.calPath + fileName));
            List<String[]> pulledList = reader.readAll();
            reader.close();
            return pulledList;
        }
        catch(IOException eo) {
            Toast.makeText(context,"Please restart app and try again",Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public static boolean listDone(Context context) {
        return (pullSizeBaseList(context) == countCompletion(context));
    }

    public static String getTextCompletion(Context context) {
        if(pullSizeBaseList(context) == countCompletion(context)) {
            return "Submit";
        }
        else {
            return (countCompletion(context) + "/" + pullSizeBaseList(context) + " Completed");
        }
    }

    public static int pullSizeBaseList(Context context) {
        List<String[]> testList = pullBaseList(context);
        return testList.size();
    }


    public static int countCompletion(Context context) {
        List<String[]> testList = pullBaseList(context);
        int count = 0;
        for(String[] testArray: testList){
            if(testArray[2].length()>5) {
                count++;
            }
        }
        return count;
    }


   public static String[] suggestList = new String[] {
           "Go out for a jog",
           "Chew some gum",
           "Do 10 push-ups",
           "Go indoors"};
}
