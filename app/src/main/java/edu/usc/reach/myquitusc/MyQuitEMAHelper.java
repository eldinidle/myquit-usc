package edu.usc.reach.myquitusc;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import edu.usc.reach.myquitusc.Surveys.MyQuitRandomSurvey;
import edu.usc.reach.myquitusc.Surveys.MyQuitSmokeSurvey;

/**
 * Created by Eldin on 12/30/14.
 */
public class MyQuitEMAHelper {
    static final int KEY_NUM_REPROMPTS = 3;

    private static int KEY_EOD_PROMPT_HOUR() {
        try {
            int checkReturnHour = Integer.parseInt(MyQuitCSVHelper.pullLoginStatus("EOD Prompt"));
            if(checkReturnHour < 20 | checkReturnHour > 23){
                return 22;
            }
            else{return checkReturnHour;}
        }
        catch(NumberFormatException nfe){
            return 22;
        }
    }

    //static final int KEY_EOD_PROMPT_HOUR = 22;

    private static final SimpleDateFormat newsdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");


    public static String[] returnLastEMASurvey(String calledDate, int sessionID, int surveyID,
                                               String situation, String intention) {
        Log.d("MQU-PHP","added session PHP " + calledDate + sessionID + surveyID);
        String stepDate = calledDate.replaceAll("/", "_");
        String fileName = stepDate + "_" + surveyID + "_" + sessionID + ".csv";
        try {
            CSVReader reader = new CSVReader(new FileReader(MyQuitCSVHelper.emaPath + fileName));
            Log.d("MY-QUIT-USC","added session PHP reader");
            List<String[]> pullAll = reader.readAll();
            Log.d("MY-QUIT-USC","added session PHP read");
            reader.close();
            Log.d("MY-QUIT-USC","added session PHP in EMA");
            String[] pulledStringArray =  pullAll.get(pullAll.size()-1);
            String[] newExpand = new String[pulledStringArray.length+4];
            int count = 0;
            for(String inArray: pulledStringArray){
                newExpand[count] = inArray;
                count++;
            }
            newExpand[newExpand.length-4] = MyQuitCSVHelper.pullLoginStatus("UserName");
            newExpand[newExpand.length-3] = String.valueOf(surveyID);
            newExpand[newExpand.length-2] = situation;
            newExpand[newExpand.length-1] = intention;
            Log.d("MY-QUIT-USC","added session PHP in EMA, done with extraction");
            return newExpand;
        }
        catch(IOException abc) {
            Log.d("MY-QUIT-USC","added session PHP IO EXCEPTION ERROR");
            return null;
        }
    }

    public static String[] returnCalendarEMARow() {
        List<String[]> events = MyQuitCalendarHelper.returnCalendarEMA();
        Date now = Calendar.getInstance().getTime();
        String[] returnRow = new String[3];
        try {
        for(String[] row: events) {
            try {
                Date promptTime = newsdf.parse(row[0]);
                Calendar rolledPrompt = Calendar.getInstance();
                rolledPrompt.setTime(promptTime);
                rolledPrompt.add(Calendar.MINUTE,15);
                Date rolledPromptTime = rolledPrompt.getTime();
                if(now.after(promptTime) && now.before(rolledPromptTime)){
                    returnRow = row;
                }
            } catch (ParseException e) {
                Log.d("MQU-EMA","Can't parse NEWSDF Date");
                e.printStackTrace();
            }
        }
        }
        catch (NullPointerException neo) {
            neo.printStackTrace();
            Log.d("MQU-EMA","Null returns");
            return returnRow;
        }
        return returnRow;
    }

    private static Boolean getRogueActivation(){
        String fileName = "DelayedRogueEMA.csv";
        try {
            CSVReader reader = new CSVReader(new FileReader(MyQuitCSVHelper.emaPath + fileName));
            List<String[]> allTimes = reader.readAll();
            reader.close();
            String activation = "false";
            for (String[] time : allTimes) {
                if(time.length>1){
                    activation = time[3];
                }
            }
            return Boolean.parseBoolean(activation);
        }
        catch(IOException io){
            io.printStackTrace();
            return false;
        }

    }

    private static Boolean getSmokeActivation(){
        String fileName = "DelayedSmokeEMA.csv";
        try {
            CSVReader reader = new CSVReader(new FileReader(MyQuitCSVHelper.emaPath + fileName));
            List<String[]> allTimes = reader.readAll();
            reader.close();
            String activation = "false";
            for (String[] time : allTimes) {
                if(time.length>1){
                    activation = time[1];
                }
            }
            return Boolean.parseBoolean(activation);
        }
        catch(IOException io){
            io.printStackTrace();
            return false;
        }

    }

    private static String getLastRogueSituation(){
        String fileName = "DelayedRogueEMA.csv";
        try {
            CSVReader reader = new CSVReader(new FileReader(MyQuitCSVHelper.emaPath + fileName));
            List<String[]> allTimes = reader.readAll();
            reader.close();
            String situation = "";
            for (String[] time : allTimes) {
                if(time.length>1){
                    situation = time[1];
                }
            }
            return situation;
        }
        catch(IOException io){
            io.printStackTrace();
            return "";
        }

    }
    private static String getLastRogueIntention(){
        String fileName = "DelayedRogueEMA.csv";
        try {
            CSVReader reader = new CSVReader(new FileReader(MyQuitCSVHelper.emaPath + fileName));
            List<String[]> allTimes = reader.readAll();
            reader.close();
            String intention = "";
            for (String[] time : allTimes) {
                if(time.length>1) {
                    intention = time[2];
                }
            }
            return intention;

        }
        catch(IOException io){
            io.printStackTrace();
            return "";
        }
    }

    public static Boolean withinLastRogueSchedule(int addMinutes){
        String fileName = "DelayedRogueEMA.csv";
        try {
            CSVReader reader = new CSVReader(new FileReader(MyQuitCSVHelper.emaPath + fileName));
            List<String[]> allTimes = reader.readAll();
            reader.close();
            String timeSave = "";
            for(String[] time: allTimes){
                timeSave = time[0];
            }
            Calendar nowCal = Calendar.getInstance();
            Date nowTime = nowCal.getTime();
            try {
                Date compareTime = newsdf.parse(timeSave);
                Calendar roll = Calendar.getInstance();
                roll.setTime(compareTime);
                int add = addMinutes;
                roll.add(Calendar.MINUTE,add);
                compareTime = roll.getTime();
                if(nowTime.before(compareTime)){
                    return true;
                }
                else{
                    return false;
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static Boolean withinLastSmokeSchedule(int minutesAdd){
        String fileName = "DelayedSmokeEMA.csv";
        try {
            CSVReader reader = new CSVReader(new FileReader(MyQuitCSVHelper.emaPath + fileName));
            List<String[]> allTimes = reader.readAll();
            reader.close();
            String timeSave = "";
            for(String[] time: allTimes){
                timeSave = time[0];
            }
            Calendar nowCal = Calendar.getInstance();
            Date nowTime = nowCal.getTime();
            try {
                Date compareTime = newsdf.parse(timeSave);
                Calendar roll = Calendar.getInstance();
                roll.setTime(compareTime);
                int add = minutesAdd;
                roll.add(Calendar.MINUTE,add);
                compareTime = roll.getTime();
                if(nowTime.before(compareTime)){
                    return true;
                }
                else{
                    return false;
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }


    public static void pushRogueEvent(String situation, String intent) throws IOException {
        String fileName = "DelayedRogueEMA.csv";
        CSVWriter writer = new CSVWriter(new FileWriter(MyQuitCSVHelper.emaPath + fileName,true));
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE,15);
        Date then = now.getTime();
        boolean activate = Math.random()<0.6;
        MyQuitPHP.postTrackerEvent(MyQuitCSVHelper.pullLoginStatus("UserName"),"Craving push EMA",String.valueOf(activate),MyQuitCSVHelper.getFulltime());
        String[] pushArray = new String[] {newsdf.format(then),situation,intent,String.valueOf(activate)};
        writer.writeNext(pushArray);
        writer.close();
    }

    public static void pushSmokingEvent(Context context) throws IOException {
        String fileName = "DelayedSmokeEMA.csv";
        CSVWriter writer = new CSVWriter(new FileWriter(MyQuitCSVHelper.emaPath + fileName,true));
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE,0);
        //now.add(Calendar.MINUTE,15); // this is for 15 minute delay
        Date then = now.getTime();
        boolean activate = true;
        //boolean activate = Math.random()<0.6; // this is for percentage
        MyQuitPHP.postTrackerEvent(MyQuitCSVHelper.pullLoginStatus("UserName"),"Smoking push EMA",String.valueOf(activate),MyQuitCSVHelper.getFulltime());
        String[] pushArray = new String[] {newsdf.format(then),String.valueOf(activate)};
        writer.writeNext(pushArray);
        writer.close();
        MyQuitEMAHelper.setUpSmokeEMA();
        MyQuitEMAHelper.decideEMA(context, MyQuitCSVHelper.SMOKE_EMA_KEY);
    }

    public static void setUpSmokeEMA() {
        Calendar nowCal = Calendar.getInstance();
        Date nowDate = nowCal.getTime();
        if(MyQuitEMAHelper.withinLastSmokeSchedule(15) && !MyQuitEMAHelper.withinLastSmokeSchedule(0) &&
                MyQuitCSVHelper.isLastEventPastXMinutesTrue(MyQuitCSVHelper.SMOKE_EMA_KEY,15) &&
                getSmokeActivation()==true){
            MyQuitPHP.postTrackerEvent(MyQuitCSVHelper.pullLoginStatus("UserName"),"EMA Prompt Ready","Smoke",MyQuitCSVHelper.getFulltime());
            MyQuitCSVHelper.logEMAEvents(MyQuitCSVHelper.SMOKE_EMA_KEY,
                    "intentPresented", MyQuitCSVHelper.getFulltime(),"","");
        }
    }

    public static void setUpRogueEMA() {
        Calendar nowCal = Calendar.getInstance();
        Date nowDate = nowCal.getTime();
        if(MyQuitEMAHelper.withinLastRogueSchedule(15) && !MyQuitEMAHelper.withinLastRogueSchedule(0) &&
                MyQuitCSVHelper.isLastEventPastXMinutesTrue(MyQuitCSVHelper.ROGUE_EMA_KEY,15) &&
                getRogueActivation()==true){
            MyQuitPHP.postTrackerEvent(MyQuitCSVHelper.pullLoginStatus("UserName"),"EMA Prompt Ready","Rogue",MyQuitCSVHelper.getFulltime());
            MyQuitCSVHelper.logEMAEvents(MyQuitCSVHelper.ROGUE_EMA_KEY,
                    "intentPresented", MyQuitCSVHelper.getFulltime(),getLastRogueSituation(),getLastRogueIntention());
        }
    }


    public static void setUpEODEMA(boolean isMainStudy) {
        if(isMainStudy) {
            Calendar nowTime = Calendar.getInstance();
            if (nowTime.get(Calendar.HOUR_OF_DAY) >= KEY_EOD_PROMPT_HOUR() &&
                    MyQuitCSVHelper.isLastEventPastXMinutesTrue(MyQuitCSVHelper.END_OF_DAY_EMA_KEY, (60 * (24 - KEY_EOD_PROMPT_HOUR())))) {
                MyQuitPHP.postTrackerEvent(MyQuitCSVHelper.pullLoginStatus("UserName"), "EMA Prompt Ready", "EOD", MyQuitCSVHelper.getFulltime());
                MyQuitCSVHelper.logEMAEvents(MyQuitCSVHelper.END_OF_DAY_EMA_KEY,
                        "intentPresented", MyQuitCSVHelper.getFulltime(), "", "");
            }
        }
        else {
            Calendar nowTime = Calendar.getInstance();
            if (nowTime.get(Calendar.HOUR_OF_DAY) >= KEY_EOD_PROMPT_HOUR() &&
                    MyQuitCSVHelper.isLastEventPastXMinutesTrue(MyQuitCSVHelper.PQ_END_OF_DAY_EMA_KEY, (60 * (24 - KEY_EOD_PROMPT_HOUR())))) {
                MyQuitPHP.postTrackerEvent(MyQuitCSVHelper.pullLoginStatus("UserName"), "EMA Prompt Ready", "PreQuit EOD", MyQuitCSVHelper.getFulltime());
                MyQuitCSVHelper.logEMAEvents(MyQuitCSVHelper.PQ_END_OF_DAY_EMA_KEY,
                        "intentPresented", MyQuitCSVHelper.getFulltime(), "", "");
            }
        }
    }

    public static void setUpRandomEMA() {
        Calendar nowCal = Calendar.getInstance();
        Date nowDate = nowCal.getTime();
        if(MyQuitExperienceSampling.validPromptTime(nowDate) &&
                MyQuitCSVHelper.isLastEventPastXMinutesTrue(MyQuitCSVHelper.RANDOM_EMA_KEY,60) &&
                MyQuitCSVHelper.isLastEventPastXMinutesTrue(MyQuitCSVHelper.SMOKE_EMA_KEY,15)){
            MyQuitPHP.postTrackerEvent(MyQuitCSVHelper.pullLoginStatus("UserName"),"EMA Prompt Ready","Random",MyQuitCSVHelper.getFulltime());
            MyQuitCSVHelper.logEMAEvents(MyQuitCSVHelper.RANDOM_EMA_KEY,
                    "intentPresented", MyQuitCSVHelper.getFulltime(),"","");
        }
    }

    public static void setUpCalendarEMA() {  //TODO: inject algorithm 24x3 based on 50/50 shot of II presentation
        // TODO: determine whether algorithm injection is necessary for this declaration
        // TODO: determine if this class should be abstracted or if this declaration should be duplicated

        String[] returnRow = returnCalendarEMARow();
        if(returnRow[0]!=null) {
            try {
                Log.d("MQU-EMA", "ROW 0 is" + returnRow[0]);
                Log.d("MQU-EMA", "ROW 1 is" + returnRow[1]);
                Log.d("MQU-EMA", "ROW 2 is" + returnRow[2]);
                try {
                    String last = MyQuitCSVHelper.pullLastEvent(MyQuitCSVHelper.CALENDAR_EMA_KEY)[0];
                    Log.d("MQU-EMA", "LAST is" + last);
                    if (!last.equalsIgnoreCase("intentPresented") &&
                            MyQuitCSVHelper.isLastEventPastXMinutesTrue(MyQuitCSVHelper.CALENDAR_EMA_KEY, 15)) {
                        Log.d("MQU-EMA", "WARNING IN IP LOOP" + last);
                        MyQuitPHP.postTrackerEvent(MyQuitCSVHelper.pullLoginStatus("UserName"),"EMA Prompt Ready","Calendar",MyQuitCSVHelper.getFulltime());
                        MyQuitCSVHelper.logEMAEvents(MyQuitCSVHelper.CALENDAR_EMA_KEY,
                                "intentPresented", MyQuitCSVHelper.getFulltime(),
                                returnRow[1], returnRow[2]);
                    }
                } catch (NullPointerException neo) {
                    Log.d("MQU-EMA", "New file today, responding");
                    MyQuitPHP.postTrackerEvent(MyQuitCSVHelper.pullLoginStatus("UserName"),"EMA Prompt Ready","Calendar",MyQuitCSVHelper.getFulltime());
                    MyQuitCSVHelper.logEMAEvents(MyQuitCSVHelper.CALENDAR_EMA_KEY,
                            "intentPresented", MyQuitCSVHelper.getFulltime(),
                            returnRow[1], returnRow[2]);
                    neo.printStackTrace();
                }

            } catch (NullPointerException neo) {
                Log.d("MQU-EMA", "OUTSIDE OF WINDOW");
                neo.printStackTrace();
            }
        }
    }

    public static void pushActionEMA(Context context, int emaType) {
        Intent launchService = new Intent(context, MyQuitService.class);
        launchService.putExtra("Action","EMA");
        launchService.putExtra("Survey",emaType);
        Log.d("MQU-DECIDE","put extra is" + emaType);
        try {
            launchService.putExtra("SessionID",createNewSessionID(MyQuitCSVHelper.getFullDate()));
            Log.d("MY-QUIT-USC", "added session ID is" + launchService.getIntExtra("SessionID", 0));
            launchService.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Log.d("MY-QUIT-USC","added session PHP ERROR1");
            //MyQuitEMA.passThroughUpload(launchService.getIntExtra("SessionID", 0),emaType);
            Log.d("MY-QUIT-USC","added session PHP OK");
            context.startService(launchService);
        } catch (IOException e) {
            //TODO: Adjust dummy survey to use intent presented + sessionID
            createDummySurvey(MyQuitCSVHelper.getFullDate());
            try {
                launchService.putExtra("SessionID",createNewSessionID(MyQuitCSVHelper.getFullDate()));
                Log.d("MY-QUIT-USC","new added session ID is" + launchService.getIntExtra("SessionID",0));
                launchService.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Log.d("MY-QUIT-USC","added session PHP ERROR2");
                //MyQuitEMA.passThroughUpload(launchService.getIntExtra("SessionID", 0),emaType);
                Log.d("MY-QUIT-USC","added session PHP OK");
                context.startService(launchService);
            } catch (IOException e1) {
                Log.d("MY-QUIT-USC","added session ID ERROR");
                e1.printStackTrace();
            }
            e.printStackTrace();
        }

    }


    public static int lagDelay(int emaType) {
        switch(emaType){
            case 3: return 15;
            default: return 3;
        }
    }

    public static void decideEMA (Context context, int emaType) {
        Log.d("MQU-DECIDE","Decision on" + emaType);
    try {
        if (MyQuitCSVHelper.pullLastEvent(emaType)[0].equalsIgnoreCase("intentPresented")) {
            pushActionEMA(context, emaType);
            Log.d("MQU-DECIDE","Decision is prompt");
        } else if (MyQuitCSVHelper.pullLastEvent(emaType)[0].substring(0, 11).equalsIgnoreCase("emaReprompt") & (MyQuitCSVHelper.convertRepromptChar(emaType) > KEY_NUM_REPROMPTS)) {
            MyQuitCSVHelper.logEMAEvents(emaType, "emaMissedSurvey", MyQuitCSVHelper.getFulltime());
            Log.d("MQU-DECIDE","Decision is missed");
        } else if ((MyQuitCSVHelper.pullLastEvent(emaType)[0].equalsIgnoreCase("emaPrompted") | MyQuitCSVHelper.pullLastEvent(emaType)[0].substring(0, 11).equalsIgnoreCase("emaReprompt")) & MyQuitCSVHelper.isLastEventPastXMinutes(emaType,lagDelay(emaType))) {
            pushActionEMA(context, emaType);
            Log.d("MQU-DECIDE","Decision is reprompt");
        } else if (MyQuitCSVHelper.pullLastEvent(emaType)[0].equalsIgnoreCase("emaMissedSurvey")){
            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.cancel(22222);
            Log.d("MQU-DECIDE","Decision is cancel");
        }
          else if ((MyQuitCSVHelper.pullLastEvent(emaType)[0].equalsIgnoreCase("emaFinished"))) {
            Log.d("MQU-DECIDE","Decision is finished");
        }
    }
    catch (NullPointerException neo){

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
        String fileName = stepDate +"_" + surveyID +  "_Sessions.csv";
        boolean naTrue = false;
        boolean paTrue = false;
        boolean pssTrue = false;
        boolean ccTrue = false;
        boolean anhedoniaTrue = false;
        if(surveyID== MyQuitRandomSurvey.KEY_SURVEY_SUCCESS){
             naTrue = Math.random()<0.6;
             paTrue = Math.random()<0.6;
             pssTrue = Math.random()<0.6;
             ccTrue = Math.random()<0.6;
             anhedoniaTrue = Math.random()<0.6;
        }
        else if(surveyID== MyQuitSmokeSurvey.KEY_SURVEY_SUCCESS){
            naTrue = Math.random()<0.6;
            paTrue = Math.random()<0.6;
            pssTrue = Math.random()<0.6;
            ccTrue = Math.random()<0.6;
            anhedoniaTrue = Math.random()<0.6;
        }
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(MyQuitCSVHelper.emaPath + fileName, true));
            Log.d("MQU","Logging ID for " + sessionID);
            if(surveyID== MyQuitRandomSurvey.KEY_SURVEY_SUCCESS){
                writer.writeNext(new String[] {calledDate,String.valueOf(surveyID),String.valueOf(sessionID),
                        String.valueOf(naTrue),String.valueOf(paTrue),String.valueOf(pssTrue),
                        String.valueOf(ccTrue),String.valueOf(anhedoniaTrue)});
                MyQuitPHP.postTrackerEvent(MyQuitCSVHelper.pullLoginStatus("UserName"),"Session:" + sessionID,(naTrue+","+paTrue+","+pssTrue+","+ccTrue+","+anhedoniaTrue),MyQuitCSVHelper.getFulltime());
            }
            else if(surveyID== MyQuitSmokeSurvey.KEY_SURVEY_SUCCESS){
                writer.writeNext(new String[] {calledDate,String.valueOf(surveyID),String.valueOf(sessionID),
                        String.valueOf(naTrue),String.valueOf(paTrue),String.valueOf(pssTrue),
                        String.valueOf(ccTrue),String.valueOf(anhedoniaTrue)});
                MyQuitPHP.postTrackerEvent(MyQuitCSVHelper.pullLoginStatus("UserName"),"Session:" + sessionID,(naTrue+","+paTrue+","+pssTrue+","+ccTrue+","+anhedoniaTrue),MyQuitCSVHelper.getFulltime());
            }
            else {
                writer.writeNext(new String[] {calledDate,String.valueOf(surveyID),String.valueOf(sessionID)});
                MyQuitPHP.postTrackerEvent(MyQuitCSVHelper.pullLoginStatus("UserName"),"Session:" + sessionID,(naTrue+","+paTrue+","+pssTrue+","+ccTrue+","+anhedoniaTrue),MyQuitCSVHelper.getFulltime());
            }
            writer.close();
        } catch (IOException e) {
            Log.d("MQU","No directory structure");
        }

    }

    public static boolean pullLastSessionMarker(String calledDate, int surveyID, int sessionMarker) {
        String stepDate = calledDate.replaceAll("/", "_");
        String fileName = stepDate + "_" + surveyID + "_Sessions.csv";
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

            try {
                Log.d("MQU","Logging success final" + lastLine[2]);
                return Boolean.valueOf(lastLine[sessionMarker]);
            }
            catch (NullPointerException neo) {
                return false;
            }
        } catch (IOException e) {
            Log.d("MQU","No directory structure");
            return false;
        }
    }

    public static int pullLastSessionID(String calledDate, int surveyID) {
        String stepDate = calledDate.replaceAll("/", "_");
        String fileName = stepDate + "_" + surveyID + "_Sessions.csv";
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

              try {
                  Log.d("MQU","Logging success final" + lastLine[2]);
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

    public static void pushSpecificAnswer(String calledDate, String calledTime, int sessionID,
                                          int aID, int qPosition, boolean newSurvey,
                                          int surveyLength, int surveyID) throws IOException {
        String stepDate = calledDate.replaceAll("/", "_");
        String fileName = stepDate + "_" + surveyID + "_" + sessionID + ".csv";
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

    public static void pushSpecificAnswer(String calledDate, String calledTime, int sessionID,
                                          String aID, int qPosition, boolean newSurvey,
                                          int surveyLength, int surveyID) throws IOException {
        String stepDate = calledDate.replaceAll("/", "_");
        String fileName = stepDate + "_" + surveyID + "_" + sessionID + ".csv";
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
            reader.close();
            pushSurvey[qPosition] = aID;
            CSVWriter writer = new CSVWriter(new FileWriter(MyQuitCSVHelper.emaPath + fileName));
            writer.writeNext(pushSurvey);
            writer.close();
        }
    }


    public static int pullSpecificAnswer(String calledDate, int sessionID, int qPosition, int surveyID) {
        String stepDate = calledDate.replaceAll("/", "_");
        String fileName = stepDate + surveyID + "_" +  sessionID + ".csv";
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

    public static String pullSpecificAnswerString(String calledDate, int sessionID, int qPosition, int surveyID) {
        String stepDate = calledDate.replaceAll("/", "_");
        String fileName = stepDate + "_" + surveyID + "_" + sessionID + ".csv";
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
