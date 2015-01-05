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

    private static String[] outWithFriends = new String[] {"Going out with friends",
            "If I am going out with friends...",""};
    private static String[] partyBar = new String[] {"Party or bar",
            "If I am at a party or a bar...",""};
    private static String[] aroundSmokers = new String[] {"Around other smokers",
            "If I am around other people who smoke...",""};
    private static String[] offerCigarette = new String[] {"Someone will offer cigarette",
            "If someone offers me a cigarette...",""};
    private static String[] nonSmokingVenue = new String[] {"At a non-smoking venue",
            "If I am about to go to places where smoking is not allowed...",""};
    private static String[] wakeUpActivity = new String[] {"Waking up",
            "If I get up in the morning...",""};
    private static String[] mealFinished = new String[] {"Eating a meal",
            "If I just finished a meal...",""};
    private static String[] coffeeOrTea = new String[] {"Having coffee or tea",
            "If I am having coffee or tea...",""};
    private static String[] onABreak = new String[] {"On a break",
            "If I am having a break...",""};
    private static String[] inACarActivity = new String[] {"In a car",
            "If I am in the car...",""};
    private static String[] bedTimeActivity = new String[] {"Going to bed",
            "If I am about to go to bed...",""};
    private static String[] underStress = new String[] {"I'm feeling depressed",
            "If I am feeling depressed...",""};
    private static String[] feelingDepressed = new String[] {"I'm feeling angry/frustrated",
            "If I am feeling angry or frustrated...",""};
    private static String[] desireCig = new String[] {"I'm desiring a cigarette",
            "If I am desiring a cigarette...",""};
    private static String[] enjoyingSmoking = new String[] {"Someone is smoking and enjoying it",
            "If I see someone smoking and enjoying it...",""};
    private static String[] gainedWeight = new String[] {"I noticed I gained weight",
            "If I noticed that I have gained weight...",""};
    private static String[] iAmBored = new String[] {"I'm bored",
            "If I am bored...",""};





    public static final List<String[]> baseList = Arrays.asList(outWithFriends, partyBar,aroundSmokers,
            offerCigarette, nonSmokingVenue, wakeUpActivity, mealFinished, coffeeOrTea, onABreak,
            inACarActivity, bedTimeActivity, underStress, feelingDepressed, desireCig,
            enjoyingSmoking, gainedWeight, iAmBored);

    public static String[] taperPullBaseList(Context context, int positionEndOfListOne, int positionBeginListTwo) {
        List<String[]> pullAll = pullBaseList(context);
        int length = pullAll.size();

        if((length)!=positionBeginListTwo) {
            List<String[]> pullAllOne = pullAll.subList(0,positionEndOfListOne);
            List<String[]> pullAllTwo = pullAll.subList((positionBeginListTwo),(length));
            for(String[] lineBy: pullAllTwo) {
                pullAllOne.add(lineBy);
            }
            int lengthnew = pullAllOne.size();
            int count = 0;
            String[] newPush = new String[lengthnew];
            for(String[] allPulled: pullAllOne) {
                newPush[count] = allPulled[0];
                count++;
            }
            return newPush;
        }
        else {
            List<String[]> pullAllNew = pullAll.subList(0,positionEndOfListOne);
            int lengthnew = pullAllNew.size();
            int count = 0;
            String[] newPush = new String[lengthnew];
            for(String[] allPulled: pullAllNew) {
                newPush[count] = allPulled[0];
                count++;
            }
            return newPush;
        }
    }

    public static boolean pushLabelBaseList(Context context, int position, String specifyItem) {
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
                    allPulled[0] = specifyItem;
                    allPulled[1] = "If I am " + specifyItem;
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


    public static void extendBaseListOne(Context context) {
        List<String[]> pullAll = pullBaseList(context);
        pullAll.add(new String[] {"","",""});
        pushBaseList(context,pullAll);
    }

    public static void shrinkBaseList(Context context, int positionToDelete) {
        List<String[]> pullAll = pullBaseList(context);
        pullAll.remove(positionToDelete);
        pushBaseList(context,pullAll);
    }


    public static String[] pullIntentsList(Context context) {
        String fileName = "ACTIVITY_PAIRING" + ".csv";
        List<String[]> pullAll = pullBaseList(context);
        int length = pullAll.size();
        int count = 0;
        String[] newPush = new String[length];
        for(String[] allPulled: pullAll) {
            newPush[count] = allPulled[2];
            count++;
        }
        return newPush;
    }


    public static String[] pullSituationList(Context context) {
        String fileName = "ACTIVITY_PAIRING" + ".csv";
        List<String[]> pullAll = pullBaseList(context);
        int length = pullAll.size();
        int count = 0;
        String[] newPush = new String[length];
        for(String[] allPulled: pullAll) {
            newPush[count] = allPulled[1];
            count++;
        }
        return newPush;
    }

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
            return (countCompletion(context) + "/" + pullSizeBaseList(context) + " Done");
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
