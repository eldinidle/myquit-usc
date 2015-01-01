package edu.usc.reach.myquitusc;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Eldin on 12/30/14.
 */
public class MyQuitEMAHelper {
    static final int KEY_NUM_REPROMPTS = 3;


    public static void pushActionEMA(Context context) {
        Intent launchService = new Intent(context, MyQuitService.class);
        launchService.putExtra("Action","EMA");
        try {
            launchService.putExtra("SessionID",createNewSessionID(MyQuitCSVHelper.getFullDate()));
            Log.d("MY-QUIT-USC", "added session ID is" + launchService.getIntExtra("SessionID", 0));
            launchService.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startService(launchService);
        } catch (IOException e) {
            createDummySurvey(MyQuitCSVHelper.getFullDate());
            try {
                launchService.putExtra("SessionID",createNewSessionID(MyQuitCSVHelper.getFullDate()));
                Log.d("MY-QUIT-USC","added session ID is" + launchService.getIntExtra("SessionID",0));
                launchService.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startService(launchService);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }

    }

    public static void decideEMA (Context context) {
        if (MyQuitCSVHelper.pullLastEvent()[0].equalsIgnoreCase("intentPresented")) {
            pushActionEMA(context);
        }
        else if (MyQuitCSVHelper.pullLastEvent()[0].substring(0,11).equalsIgnoreCase("emaReprompt") & (MyQuitCSVHelper.convertRepromptChar() > KEY_NUM_REPROMPTS)) {
            MyQuitCSVHelper.logEMAEvents("emaMissedSurvey", MyQuitCSVHelper.getFulltime());
        }
        else if ((MyQuitCSVHelper.pullLastEvent()[0].equalsIgnoreCase("emaPrompted") | MyQuitCSVHelper.pullLastEvent()[0].substring(0,11).equalsIgnoreCase("emaReprompt"))  & MyQuitCSVHelper.isLastEventPastXMinutes(3)) {
            pushActionEMA(context);
        }
        else if ((MyQuitCSVHelper.pullLastEvent()[0].equalsIgnoreCase("emaFinished"))) {
        }
    }


    public static void createDummySurvey(String calledDate) {
        String stepDate = calledDate.replaceAll("/", "_");
        String fileName = stepDate + ".csv";
        CSVWriter writer = null;
        try {
            writer = new CSVWriter(new FileWriter(MyQuitCSVHelper.emaPath + fileName, true));
            String[] writeThis = new String[] {"Skip this row","0"};
            writer.writeNext(writeThis);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void pushSurveyAnswers(String calledDate, String calledTime, int sessionID, int[] answers) throws IOException {
        String stepDate = calledDate.replaceAll("/", "_");
        String fileName = stepDate + ".csv";
        CSVWriter writer = new CSVWriter(new FileWriter(MyQuitCSVHelper.emaPath + fileName, true));
        String[] finalPush = new String[answers.length];
        int count = 0;
        for(int answer:answers){
            finalPush[count] = String.valueOf(answer);
            count++;
        }
        String[] copyAnswers = Arrays.copyOf(finalPush, answers.length + 3);
        copyAnswers[copyAnswers.length-1] = String.valueOf(sessionID);
        copyAnswers[copyAnswers.length-2] = calledTime;
        copyAnswers[copyAnswers.length-3] = calledDate;

        writer.writeNext(copyAnswers);
        writer.close();
    }

    public static void pushLastSessionID(String calledDate, int surveyID, int sessionID) {
        String stepDate = calledDate.replaceAll("/", "_");
        String fileName = stepDate + "_Sessions.csv";
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(MyQuitCSVHelper.emaPath + fileName));
            Log.d("MQU","Logging ID for " + sessionID);
            writer.writeNext(new String[] {calledDate,String.valueOf(surveyID),String.valueOf(sessionID)});
            writer.close();
        } catch (IOException e) {
            Log.d("MQU","No directory structure");
        }

    }

    public static int pullLastSessionID(String calledDate, int surveyID) {
        String stepDate = calledDate.replaceAll("/", "_");
        String fileName = stepDate + "_Sessions.csv";
        try {
            CSVReader reader = new CSVReader(new FileReader(MyQuitCSVHelper.emaPath + fileName));
            List<String[]> pullAll = reader.readAll();
            reader.close();
            String[] lastLine = null;
            for(String[] lineBy: pullAll) {
                if(lineBy[1].equalsIgnoreCase(String.valueOf(surveyID))) {
                    lastLine = lineBy;
                    Log.d("MQU","Logging success check" + lineBy[0] + lineBy[1] + lineBy[2]);
                }
                else {
                    Log.d("MQU","Logging failed check");
                }
            }
            Log.d("MQU","Logging success final" + lastLine[2]);
              try {
                  return Integer.valueOf(lastLine[2]);
              }
              catch (NullPointerException neo) {
                  return 0;
              }
        } catch (IOException e) {
            Log.d("MQU","No directory structure");
            return 0;
        }
    }

    public static void pushSpecificAnswer(String calledDate, String calledTime, int sessionID, int aID, int qPosition, boolean newSurvey, int surveyLength) throws IOException {
        String stepDate = calledDate.replaceAll("/", "_");
        String fileName = stepDate + sessionID + ".csv";
        String[] pushSurvey = new String[surveyLength+3];
        if(newSurvey) {
            CSVWriter writer = new CSVWriter(new FileWriter(MyQuitCSVHelper.emaPath + fileName));
            pushSurvey[qPosition] = String.valueOf(aID);
            pushSurvey[pushSurvey.length-1] = String.valueOf(sessionID);
            pushSurvey[pushSurvey.length-2] = calledTime;
            pushSurvey[pushSurvey.length-3] = calledDate;
            writer.writeNext(pushSurvey);
            writer.close();
        }
        else {
            CSVReader reader = new CSVReader(new FileReader(MyQuitCSVHelper.emaPath + fileName));
            pushSurvey = reader.readNext();
            pushSurvey[qPosition] = String.valueOf(aID);
            CSVWriter writer = new CSVWriter(new FileWriter(MyQuitCSVHelper.emaPath + fileName));
            writer.writeNext(pushSurvey);
            writer.close();
        }
    }

    public static void pushSpecificAnswer(String calledDate, String calledTime, int sessionID, String aID, int qPosition, boolean newSurvey, int surveyLength) throws IOException {
        String stepDate = calledDate.replaceAll("/", "_");
        String fileName = stepDate + sessionID + ".csv";
        String[] pushSurvey = new String[surveyLength+3];
        if(newSurvey) {
            CSVWriter writer = new CSVWriter(new FileWriter(MyQuitCSVHelper.emaPath + fileName));
            pushSurvey[qPosition] = aID;
            pushSurvey[pushSurvey.length-1] = String.valueOf(sessionID);
            pushSurvey[pushSurvey.length-2] = calledTime;
            pushSurvey[pushSurvey.length-3] = calledDate;
            writer.writeNext(pushSurvey);
            writer.close();
        }
        else {
            CSVReader reader = new CSVReader(new FileReader(MyQuitCSVHelper.emaPath + fileName));
            pushSurvey = reader.readNext();
            pushSurvey[qPosition] = aID;
            CSVWriter writer = new CSVWriter(new FileWriter(MyQuitCSVHelper.emaPath + fileName));
            writer.writeNext(pushSurvey);
            writer.close();
        }
    }


    public static int pullSpecificAnswer(String calledDate, int sessionID, int qPosition) {
        String stepDate = calledDate.replaceAll("/", "_");
        String fileName = stepDate + sessionID + ".csv";
        try {
            CSVReader reader = new CSVReader(new FileReader(MyQuitCSVHelper.emaPath + fileName));
            String[] pullAll = reader.readNext();
            reader.close();
            int answerID = Integer.valueOf(pullAll[qPosition]);
            return answerID;
        }
        catch(IOException abc) {
            return 0;
        }

    }

    public static String pullSpecificAnswerString(String calledDate, int sessionID, int qPosition) {
        String stepDate = calledDate.replaceAll("/", "_");
        String fileName = stepDate + sessionID + ".csv";
        try {
            CSVReader reader = new CSVReader(new FileReader(MyQuitCSVHelper.emaPath + fileName));
            String[] pullAll = reader.readNext();
            reader.close();
            return pullAll[qPosition];
        }
        catch(IOException abc) {
            return "";
        }

    }

    public static int createNewSessionID(String calledDate) throws IOException {
        String stepDate = calledDate.replaceAll("/", "_");
        String fileName = stepDate + ".csv";
        CSVReader reader = new CSVReader(new FileReader(MyQuitCSVHelper.emaPath + fileName));
        List<String[]> pullTimes = reader.readAll();
        reader.close();
        Random rand = new Random();
        int preID = rand.nextInt((99999 - 1) + 1) + 1;;
        Log.d("MQU-RANDOM","old is" + preID);
        if (pullTimes.size()>1) {
            boolean check = false;
            Random newrand;
            int newpreID;
            do {
                newrand = new Random();
                newpreID = newrand.nextInt((99999 - 1) + 1) + 1;
                for (String[] pullSession : pullTimes) {
                    check = (Integer.getInteger(pullSession[pullSession.length - 1]) != preID);
                }
            } while (check = false);
            Log.d("MQU-RANDOM","new is" + newpreID);
            return newpreID;
        }
        else {
            Log.d("MQU-RANDOM","old and new is" + preID);
            return preID;
        }
    }
}
