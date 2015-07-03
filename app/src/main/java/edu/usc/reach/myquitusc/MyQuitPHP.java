package edu.usc.reach.myquitusc;

import android.os.AsyncTask;
import android.util.Log;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.usc.reach.myquitusc.Surveys.MyQuitCalendarSuccessSurvey;
import edu.usc.reach.myquitusc.Surveys.MyQuitCheckSuccessSurvey;
import edu.usc.reach.myquitusc.Surveys.MyQuitEndOfDaySurvey;
import edu.usc.reach.myquitusc.Surveys.MyQuitOffSuccessSurvey;
import edu.usc.reach.myquitusc.Surveys.MyQuitRandomSurvey;
import edu.usc.reach.myquitusc.Surveys.MyQuitRandomSurveyBackup;
import edu.usc.reach.myquitusc.Surveys.MyQuitSmokeSurvey;

/**
 * Created by Eldin on 1/14/15.
 */
public class MyQuitPHP {

    private final static int phpAllowInteger = 1265;

    private final static String rogueSuccessTable = "RoguePushSuccessTable.csv";
    private final static String trackerSuccessTable = "TrackerPushSuccessTable.csv";
    private final static String emaSuccessTable = "EMAPushSuccessTable.csv";
    private final static String calendarSuccessTable = "CalendarPushSuccessTable.csv";
    private final static String gpsSuccessTable = "GPSPushSuccessTable.csv";

    private final static String unPlannedSuccessTable = "UnplannedPushSuccessTable.csv";

    private static final String urlPostRogueEvent = "http://myquitadmin.usc.edu/data.php";
    private static final String urlTrackerEvent = "http://myquitadmin.usc.edu/tracker.php";
    private static final String urlPostEMAEvent = "http://myquitadmin.usc.edu/myquitema.php";
    private static final String urlPostCalendarEvent = "http://myquitadmin.usc.edu/calendar.php";
    private static final String urlPostGPSEvent = "http://myquitadmin.usc.edu/gps.php";

    private static final String urlPostUnplannedEvent = "http://myquit.usc.edu/postunplanned.php";


   // static JSONParser jsonParser = new JSONParser();


    static class PostRogueEvent extends AsyncTask<String,String,String> {



        @Override
        protected String doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(urlPostRogueEvent);
            Log.d("MQU-PHP","Param 1 is" + params[0]);
            Log.d("MQU-PHP","Param 2 is" + params[1]);
            Log.d("MQU-PHP","Param 3 is" + params[2]);
            List<NameValuePair> rogueParams = new ArrayList<NameValuePair>();
            rogueParams.add(new BasicNameValuePair("username",params[0]));
            rogueParams.add(new BasicNameValuePair("situation",params[1]));
            rogueParams.add(new BasicNameValuePair("datetime",params[2]));
            rogueParams.add(new BasicNameValuePair("allowed",String.valueOf(phpAllowInteger)));

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(rogueParams));
                Log.d("MQU-PHP","URL is now encoded");

            } catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }

                try {
                    HttpResponse response = httpClient.execute(httpPost);
                    // write response to log
                    Log.d("MQU-PHP", "Http Post Response:" + response.toString());
                } catch (ClientProtocolException e) {
                    // Log exception
                    Log.d("MQU-PHP", "Http Post Response CPE error");
                    e.printStackTrace();
                    postRogueStatus(params[0], params[1], params[2]);
                } catch (IOException e) {
                    Log.d("MQU-PHP", "Http Post Response IO error");
                    // Log exception
                    e.printStackTrace();
                    postRogueStatus(params[0], params[1], params[2]);
                }

            return null;
        }
    }

    static class PostTrackerEvent extends AsyncTask<String,String,String> {



        @Override
        protected String doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(urlTrackerEvent);
            Log.d("MQU-PHP","Param 1 is" + params[0]);
            Log.d("MQU-PHP","Param 2 is" + params[1]);
            Log.d("MQU-PHP","Param 3 is" + params[2]);
            Log.d("MQU-PHP","Param 4 is" + params[3]);
            List<NameValuePair> rogueParams = new ArrayList<NameValuePair>();
            rogueParams.add(new BasicNameValuePair("username",params[0]));
            rogueParams.add(new BasicNameValuePair("event",params[1]));
            rogueParams.add(new BasicNameValuePair("meta",params[2]));
            rogueParams.add(new BasicNameValuePair("datetime",params[3]));
            rogueParams.add(new BasicNameValuePair("allowed",String.valueOf(phpAllowInteger)));

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(rogueParams));
                Log.d("MQU-PHP","URL is now encoded");

            } catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }

            try {
                HttpResponse response = httpClient.execute(httpPost);
                // write response to log
                Log.d("MQU-PHP", "Http Post Response:" + response.toString());
            } catch (ClientProtocolException e) {
                // Log exception
                Log.d("MQU-PHP", "Http Post Response CPE error");
                e.printStackTrace();
                postTrackerStatus(params[0], params[1], params[2], params[3]);
            } catch (IOException e) {
                Log.d("MQU-PHP", "Http Post Response IO error");
                // Log exception
                e.printStackTrace();
                postTrackerStatus(params[0], params[1], params[2], params[3]);
            }

            return null;
        }
    }

    static class PostGPSEvent extends AsyncTask<String,String,String> {



        @Override
        protected String doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(urlPostGPSEvent);
            Log.d("MQU-PHP","Param 1 is" + params[0]);
            Log.d("MQU-PHP","Param 2 is" + params[1]);
            Log.d("MQU-PHP","Param 3 is" + params[2]);
            Log.d("MQU-PHP","Param 4 is" + params[3]);
            List<NameValuePair> rogueParams = new ArrayList<NameValuePair>();
            rogueParams.add(new BasicNameValuePair("username",params[0]));
            rogueParams.add(new BasicNameValuePair("fulltime",params[1]));
            rogueParams.add(new BasicNameValuePair("latitude",params[2]));
            rogueParams.add(new BasicNameValuePair("longitude",params[3]));
            rogueParams.add(new BasicNameValuePair("accuracy",params[4]));
            rogueParams.add(new BasicNameValuePair("allowed",String.valueOf(phpAllowInteger)));

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(rogueParams));
                Log.d("MQU-PHP","URL is now encoded");

            } catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }

            try {
                HttpResponse response = httpClient.execute(httpPost);
                // write response to log
                Log.d("MQU-PHP", "Http Post Response:" + response.toString());
            } catch (ClientProtocolException e) {
                // Log exception
                Log.d("MQU-PHP", "Http Post Response CPE error");
                e.printStackTrace();
                postGPSStatus(params[0], params[1], params[2], params[3], params[4]);
            } catch (IOException e) {
                Log.d("MQU-PHP", "Http Post Response IO error");
                // Log exception
                e.printStackTrace();
                postGPSStatus(params[0], params[1], params[2], params[3], params[4]);
            }

            return null;
        }
    }

    static class PostCalendarEvent extends AsyncTask<String,String,String> {



        @Override
        protected String doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(urlPostCalendarEvent);
            Log.d("MQU-PHP","Param 1 is" + params[0]);
            Log.d("MQU-PHP","Param 2 is" + params[1]);
            Log.d("MQU-PHP","Param 3 is" + params[2]);
            Log.d("MQU-PHP","Param 4 is" + params[3]);
            List<NameValuePair> rogueParams = new ArrayList<NameValuePair>();
            rogueParams.add(new BasicNameValuePair("username",params[0]));
            rogueParams.add(new BasicNameValuePair("caldate",params[1]));
            rogueParams.add(new BasicNameValuePair("hour",params[2]));
            rogueParams.add(new BasicNameValuePair("situation",params[3]));
            rogueParams.add(new BasicNameValuePair("intention",params[4]));
            rogueParams.add(new BasicNameValuePair("timechanged",params[5]));
            rogueParams.add(new BasicNameValuePair("allowed",String.valueOf(phpAllowInteger)));

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(rogueParams));
                Log.d("MQU-PHP","URL is now encoded");

            } catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }

            try {
                HttpResponse response = httpClient.execute(httpPost);
                // write response to log
                Log.d("MQU-PHP", "Http Post Response:" + response.toString());
            } catch (ClientProtocolException e) {
                // Log exception
                Log.d("MQU-PHP", "Http Post Response CPE error");
                e.printStackTrace();
                postCalendarStatus(params[0], params[1], params[2], params[3], params[4], params[5]);
            } catch (IOException e) {
                Log.d("MQU-PHP", "Http Post Response IO error");
                // Log exception
                e.printStackTrace();
                postCalendarStatus(params[0], params[1], params[2], params[3], params[4], params[5]);
            }

            return null;
        }
    }

    /**
     * Parameter order is explicit.
     * The main parameter is a String[] with 5 appended items in addition to the EMA survey.
     * 1: Date
     * 2: Time
     * 3: SessionID
     * 4: Username
     * 5: Survey type key
     *
     */
    static class PostEMAEvent extends AsyncTask<String[],String,String> {

        @Override
        protected String doInBackground(String[]... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(urlPostEMAEvent);
            List<NameValuePair> emaParams = new ArrayList<NameValuePair>();
            int fullLength = params[0].length;
            String datetime = params[0][fullLength-7] + " " + params[0][fullLength-6];
            int surveyType = Integer.valueOf(params[0][fullLength-3]);

            switch(surveyType){
                case MyQuitCheckSuccessSurvey.KEY_SURVEY_SUCCESS:
                    emaParams.add(new BasicNameValuePair("didFollow",params[0][0]));
                    emaParams.add(new BasicNameValuePair("howHelpful",params[0][1]));
                    emaParams.add(new BasicNameValuePair("didSmokeY",params[0][2]));
                    emaParams.add(new BasicNameValuePair("didSmokeN",params[0][3]));
                    emaParams.add(new BasicNameValuePair("doInstead",params[0][4]));
                    emaParams.add(new BasicNameValuePair("situation",params[0][fullLength-2]));
                    emaParams.add(new BasicNameValuePair("intention",params[0][fullLength-1]));
                    break;
                case MyQuitCalendarSuccessSurvey.KEY_SURVEY_SUCCESS:
                    emaParams.add(new BasicNameValuePair("areContext",params[0][0]));
                    emaParams.add(new BasicNameValuePair("didFollow",params[0][1]));
                    emaParams.add(new BasicNameValuePair("howHelpful",params[0][2]));
                    emaParams.add(new BasicNameValuePair("didSmokeY",params[0][3]));
                    emaParams.add(new BasicNameValuePair("didSmokeN",params[0][4]));
                    emaParams.add(new BasicNameValuePair("doInstead",params[0][5]));
                    emaParams.add(new BasicNameValuePair("situation",params[0][fullLength-2]));
                    emaParams.add(new BasicNameValuePair("intention",params[0][fullLength-1]));
                    break;
                case MyQuitEndOfDaySurvey.KEY_SURVEY_SUCCESS:
                    emaParams.add(new BasicNameValuePair("howManyCigs", params[0][0]));
                    emaParams.add(new BasicNameValuePair("pssFamily", params[0][1]));
                    emaParams.add(new BasicNameValuePair("pssMoney", params[0][2]));
                    emaParams.add(new BasicNameValuePair("pssSchool", params[0][3]));
                    emaParams.add(new BasicNameValuePair("pssWork", params[0][4]));
                    emaParams.add(new BasicNameValuePair("naDistressed", params[0][5]));
                    emaParams.add(new BasicNameValuePair("naIrritable", params[0][6]));
                    emaParams.add(new BasicNameValuePair("naSad", params[0][7]));
                    emaParams.add(new BasicNameValuePair("naScared", params[0][8]));
                    emaParams.add(new BasicNameValuePair("naTense", params[0][9]));
                    emaParams.add(new BasicNameValuePair("naUpset", params[0][10]));
                    emaParams.add(new BasicNameValuePair("paCheerful", params[0][11]));
                    emaParams.add(new BasicNameValuePair("paEnthusiastic", params[0][12]));
                    emaParams.add(new BasicNameValuePair("paHappy", params[0][13]));
                    emaParams.add(new BasicNameValuePair("paInterested", params[0][14]));
                    emaParams.add(new BasicNameValuePair("paProud", params[0][15]));
                    emaParams.add(new BasicNameValuePair("troubleCigs", params[0][16]));
                    emaParams.add(new BasicNameValuePair("botherDesire", params[0][17]));
                    emaParams.add(new BasicNameValuePair("frequentUrges", params[0][18]));
                    emaParams.add(new BasicNameValuePair("confidentQuit", params[0][19]));
                    emaParams.add(new BasicNameValuePair("helpQuit", params[0][20]));
                    emaParams.add(new BasicNameValuePair("vapeCig", params[0][21]));
                    emaParams.add(new BasicNameValuePair("vapeCigCount", params[0][22]));
                    emaParams.add(new BasicNameValuePair("vapePuffCount", params[0][23]));
                    break;
                case MyQuitOffSuccessSurvey.KEY_SURVEY_SUCCESS:
                    break;
                case MyQuitRandomSurvey.KEY_SURVEY_SUCCESS:
                    emaParams.add(new BasicNameValuePair("whereAt", params[0][0]));
                    emaParams.add(new BasicNameValuePair("whereAtText", params[0][1]));
                    emaParams.add(new BasicNameValuePair("whatDoing", params[0][2]));
                    emaParams.add(new BasicNameValuePair("whatDoingText", params[0][3]));
                    emaParams.add(new BasicNameValuePair("whoWith", params[0][4]));
                    emaParams.add(new BasicNameValuePair("hadThese", params[0][5]));
                    emaParams.add(new BasicNameValuePair("othersSmoke", params[0][6]));
                    emaParams.add(new BasicNameValuePair("allowSmoke", params[0][7]));
                    emaParams.add(new BasicNameValuePair("persistStressor", params[0][8]));
                    emaParams.add(new BasicNameValuePair("occurStressor", params[0][9]));
                    emaParams.add(new BasicNameValuePair("naScared", params[0][10]));
                    emaParams.add(new BasicNameValuePair("naUpset", params[0][11]));
                    emaParams.add(new BasicNameValuePair("naDistressed", params[0][12]));
                    emaParams.add(new BasicNameValuePair("naTense", params[0][13]));
                    emaParams.add(new BasicNameValuePair("naSad", params[0][14]));
                    emaParams.add(new BasicNameValuePair("naIrritable", params[0][15]));
                    emaParams.add(new BasicNameValuePair("naHopeless", params[0][16]));
                    emaParams.add(new BasicNameValuePair("paHappy", params[0][17]));
                    emaParams.add(new BasicNameValuePair("paCheerful", params[0][18]));
                    emaParams.add(new BasicNameValuePair("paEnthusiastic", params[0][19]));
                    emaParams.add(new BasicNameValuePair("paProud", params[0][20]));
                    emaParams.add(new BasicNameValuePair("paInterested", params[0][21]));
                    emaParams.add(new BasicNameValuePair("pssSchool", params[0][22]));
                    emaParams.add(new BasicNameValuePair("pssWork", params[0][23]));
                    emaParams.add(new BasicNameValuePair("pssFamily", params[0][24]));
                    emaParams.add(new BasicNameValuePair("pssMoney", params[0][25]));
                    emaParams.add(new BasicNameValuePair("ccMindCig", params[0][26]));
                    emaParams.add(new BasicNameValuePair("ccMindVape", params[0][27]));
                    emaParams.add(new BasicNameValuePair("ccDesireCig", params[0][28]));
                    emaParams.add(new BasicNameValuePair("ccDesireVape", params[0][29]));
                    emaParams.add(new BasicNameValuePair("ccUrgeCig", params[0][30]));
                    emaParams.add(new BasicNameValuePair("ccUrgeVape", params[0][31]));
                    emaParams.add(new BasicNameValuePair("anhedoniaPeople", params[0][32]));
                    emaParams.add(new BasicNameValuePair("anhedoniaHobby", params[0][33]));
                    emaParams.add(new BasicNameValuePair("anhedoniaSocial", params[0][34]));
                    break;
                case MyQuitSmokeSurvey.KEY_SURVEY_SUCCESS:
                    emaParams.add(new BasicNameValuePair("whereAt", params[0][0]));
                    emaParams.add(new BasicNameValuePair("whereAtText", params[0][1]));
                    emaParams.add(new BasicNameValuePair("whatDoing", params[0][2]));
                    emaParams.add(new BasicNameValuePair("whatDoingText", params[0][3]));
                    emaParams.add(new BasicNameValuePair("reasonSmoke", params[0][4]));
                    emaParams.add(new BasicNameValuePair("reasonSmokeText", params[0][5]));
                    emaParams.add(new BasicNameValuePair("whoWith", params[0][6]));
                    emaParams.add(new BasicNameValuePair("hadThese", params[0][7]));
                    emaParams.add(new BasicNameValuePair("othersSmoke", params[0][8]));
                    emaParams.add(new BasicNameValuePair("allowSmoke", params[0][9]));
                    emaParams.add(new BasicNameValuePair("persistStressor", params[0][10]));
                    emaParams.add(new BasicNameValuePair("occurStressor", params[0][11]));
                    emaParams.add(new BasicNameValuePair("naScared", params[0][12]));
                    emaParams.add(new BasicNameValuePair("naUpset", params[0][13]));
                    emaParams.add(new BasicNameValuePair("naDistressed", params[0][14]));
                    emaParams.add(new BasicNameValuePair("naTense", params[0][15]));
                    emaParams.add(new BasicNameValuePair("naSad", params[0][16]));
                    emaParams.add(new BasicNameValuePair("naIrritable", params[0][17]));
                    emaParams.add(new BasicNameValuePair("naHopeless", params[0][18]));
                    emaParams.add(new BasicNameValuePair("paHappy", params[0][19]));
                    emaParams.add(new BasicNameValuePair("paCheerful", params[0][20]));
                    emaParams.add(new BasicNameValuePair("paEnthusiastic", params[0][21]));
                    emaParams.add(new BasicNameValuePair("paProud", params[0][22]));
                    emaParams.add(new BasicNameValuePair("paInterested", params[0][23]));
                    emaParams.add(new BasicNameValuePair("pssSchool", params[0][24]));
                    emaParams.add(new BasicNameValuePair("pssWork", params[0][25]));
                    emaParams.add(new BasicNameValuePair("pssFamily", params[0][26]));
                    emaParams.add(new BasicNameValuePair("pssMoney", params[0][27]));
                    emaParams.add(new BasicNameValuePair("ccMindCig", params[0][28]));
                    emaParams.add(new BasicNameValuePair("ccDesireCig", params[0][29]));
                    emaParams.add(new BasicNameValuePair("ccUrgeCig", params[0][30]));
                    emaParams.add(new BasicNameValuePair("anhedoniaPeople", params[0][31]));
                    emaParams.add(new BasicNameValuePair("anhedoniaHobby", params[0][32]));
                    emaParams.add(new BasicNameValuePair("anhedoniaSocial", params[0][33]));
                    break;
            }

            emaParams.add(new BasicNameValuePair("username",params[0][fullLength-4]));
            emaParams.add(new BasicNameValuePair("surveytype",params[0][fullLength-3]));
            emaParams.add(new BasicNameValuePair("sessionid",params[0][fullLength-5]));
            emaParams.add(new BasicNameValuePair("datetime",datetime));
            emaParams.add(new BasicNameValuePair("allowed",String.valueOf(phpAllowInteger)));
            Log.d("MQU-PHP","Caught" + params[0][fullLength-2] + params[0][fullLength-1] + params[0][fullLength-3]);
            Log.d("MQU-PHP","Caught" + params[0][2] + params[0][3] + params[0][4]);
            Log.d("MQU-PHP","Caught" + params[0][0] + params[0][1]);
            Log.d("MQU-PHP","Caught" + datetime + phpAllowInteger);


            try {
                httpPost.setEntity(new UrlEncodedFormEntity(emaParams));
                Log.d("MQU-PHP","URL is now encoded");

            } catch (UnsupportedEncodingException e)
            {
                Log.d("MQU-PHP","URL is not supported or encoded");
                e.printStackTrace();
            }

            try {
                HttpResponse response = httpClient.execute(httpPost);
                // write response to log
                Log.d("MQU-PHP", "Http Post Response:" + response.toString());
            } catch (ClientProtocolException e) {
                // Log exception
                Log.d("MQU-PHP", "Http Post Response CPE error");
                e.printStackTrace();
                postEMAStatus(emaParams);
            } catch (IOException e) {
                Log.d("MQU-PHP", "Http Post Response IO error");
                // Log exception
                e.printStackTrace();
                postEMAStatus(emaParams);
            }

            return null;
        }
    }

    static class SyncRogueEvent extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... params) {
            String fileName = rogueSuccessTable;
            try {
                CSVReader reader = new CSVReader(new FileReader(MyQuitCSVHelper.logPath + fileName));
                List<String[]> pullTimes = reader.readAll();
                reader.close();
                Log.d("MQU-PHP","Pulled" + pullTimes.size());
                List<String[]> newPush = new ArrayList<String[]>();
                for(String[] row: pullTimes){
                    if(!syncRogueEvent(row[0], row[1], row[2])){
                        Log.d("MQU-PHP","Adding" + row[0] + row[1] + row[2]);
                        newPush.add(row);
                        Log.d("MQU-PHP","Added" + row[0] + row[1] + row[2]);
                    }
                }
                CSVWriter writer = new CSVWriter(new FileWriter(MyQuitCSVHelper.logPath + fileName));
                writer.writeAll(newPush);
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    static class SyncTrackerEvent extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... params) {
            String fileName = trackerSuccessTable;
            try {
                CSVReader reader = new CSVReader(new FileReader(MyQuitCSVHelper.logPath + fileName));
                List<String[]> pullTimes = reader.readAll();
                reader.close();
                Log.d("MQU-PHP","Pulled" + pullTimes.size());
                List<String[]> newPush = new ArrayList<String[]>();
                for(String[] row: pullTimes){
                    if(!syncTrackerEvent(row[0], row[1], row[2], row[3])){
                        Log.d("MQU-PHP","Adding" + row[0] + row[1] + row[2]);
                        newPush.add(row);
                        Log.d("MQU-PHP","Added" + row[0] + row[1] + row[2]);
                    }
                }
                CSVWriter writer = new CSVWriter(new FileWriter(MyQuitCSVHelper.logPath + fileName));
                writer.writeAll(newPush);
                writer.close();
            } catch (FileNotFoundException e) {
                Log.d("MQU-PHP","Failure FNF EMA");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    static class SyncGPSEvent extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... params) {
            String fileName = gpsSuccessTable;
            try {
                CSVReader reader = new CSVReader(new FileReader(MyQuitCSVHelper.logPath + fileName));
                List<String[]> pullTimes = reader.readAll();
                reader.close();
                Log.d("MQU-PHP","Pulled" + pullTimes.size());
                List<String[]> newPush = new ArrayList<String[]>();
                for(String[] row: pullTimes){
                    if(!syncGPSEvent(row[0], row[1], row[2], row[3], row[4])){
                        Log.d("MQU-PHP","Adding" + row[0] + row[1] + row[2]);
                        newPush.add(row);
                        Log.d("MQU-PHP","Added" + row[0] + row[1] + row[2]);
                    }
                }
                CSVWriter writer = new CSVWriter(new FileWriter(MyQuitCSVHelper.logPath + fileName));
                writer.writeAll(newPush);
                writer.close();
            } catch (FileNotFoundException e) {
                Log.d("MQU-PHP","Failure FNF EMA");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    static class SyncCalendarEvent extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... params) {
            String fileName = calendarSuccessTable;
            try {
                CSVReader reader = new CSVReader(new FileReader(MyQuitCSVHelper.logPath + fileName));
                List<String[]> pullTimes = reader.readAll();
                reader.close();
                Log.d("MQU-PHP","Pulled" + pullTimes.size());
                List<String[]> newPush = new ArrayList<String[]>();
                for(String[] row: pullTimes){
                    if(!syncCalendarEvent(row[0], row[1], row[2], row[3], row[4], row[5])){
                        Log.d("MQU-PHP","Adding" + row[0] + row[1] + row[2]);
                        newPush.add(row);
                        Log.d("MQU-PHP","Added" + row[0] + row[1] + row[2]);
                    }
                }
                CSVWriter writer = new CSVWriter(new FileWriter(MyQuitCSVHelper.logPath + fileName));
                writer.writeAll(newPush);
                writer.close();
            } catch (FileNotFoundException e) {
                Log.d("MQU-PHP","Failure FNF EMA");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    static class SyncEMAEvent extends AsyncTask<String[],String,String> {

        @Override
        protected String doInBackground(String[]... params) {
            String fileName = emaSuccessTable;
            try {
                CSVReader reader = new CSVReader(new FileReader(MyQuitCSVHelper.logPath + fileName));
                List<String[]> pullTimes = reader.readAll();
                reader.close();
                Log.d("MQU-PHP","Pulled" + pullTimes.size());
                List<String[]> newPush = new ArrayList<String[]>();
                for(String[] row: pullTimes){
                    if(!syncEMAEvent(row)){
                        Log.d("MQU-PHP","Adding" + row[row.length-1] + row[row.length-2] + row[row.length-3]);
                        newPush.add(row);
                        Log.d("MQU-PHP","Added" + row[row.length-1] + row[row.length-2] + row[row.length-3]);
                    }
                }
                CSVWriter writer = new CSVWriter(new FileWriter(MyQuitCSVHelper.logPath + fileName));
                writer.writeAll(newPush);
                writer.close();
            } catch (FileNotFoundException e) {
                Log.d("MQU-PHP","Failure FNF EMA");
                e.printStackTrace();
            } catch (IOException e) {
                Log.d("MQU-PHP","Failure IOE");
                e.printStackTrace();
            }

            return null;
        }
    }

    public static void postRogueEvent(String userName, String rogueSituation, String calledDateTime)  {
        String fixedSituation = rogueSituation.replace("'","");
        new PostRogueEvent().execute(userName, fixedSituation, calledDateTime);
    }
    public static void postTrackerEvent(String userName, String trackerSituation, String trackerMetaData, String calledDateTime)  {
        String fixedSituation = trackerSituation.replace("'","");
        String fixedSituation2 = trackerMetaData.replace("'","");
        new PostTrackerEvent().execute(userName, fixedSituation, trackerMetaData, calledDateTime);
    }

    public static void postGPSEvent(String userName, String fulltime, String latitude, String longitude, String accuracy)  {
        String userName2 = userName.replace("'","");
        String fulltime2 = fulltime.replace("'","");
        String latitude2 = latitude.replace("'","");
        String longitude2 = longitude.replace("'","");
        String accuracy2 = accuracy.replace("'","");
        new PostGPSEvent().execute(userName2, fulltime2, latitude2, longitude2, accuracy2);
    }

    public static void postCalendarEvent(String userName, String calDate, String hour, String situation, String intention, String timeChanged)  {
        String fixedName = userName.replace("'","");
        String fixedCalDate2 = calDate.replace("'","");
        String fixedCalDate = fixedCalDate2.replace("DEFAULT_","");
        String fixedHour = hour.replace("'","");
        String fixedSituation = situation.replace("'","");
        String fixedIntention = intention.replace("'","");
        String fixedTimeChange = timeChanged.replace("'","");
        new PostCalendarEvent().execute(fixedName, fixedCalDate, fixedHour, fixedSituation, fixedIntention, fixedTimeChange);
    }

    public static void postEMAEvent(String[] surveyParams) {
       String[] fixedParams = new String[surveyParams.length];
       int count = 0;
       for(String iter: surveyParams){
           fixedParams[count] = iter.replace("'","");
           count++;
       }
       new PostEMAEvent().execute(fixedParams);
    }

    public static boolean syncEMAEvent(String[] params)  {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(urlPostEMAEvent);
        List<NameValuePair> emaParams = new ArrayList<NameValuePair>();
        int fullLength = params.length;
        String datetime = params[fullLength-7] + " " + params[fullLength-6];
        int surveyType = Integer.valueOf(params[fullLength-3]);
        switch(surveyType){
            case MyQuitCheckSuccessSurvey.KEY_SURVEY_SUCCESS:
                emaParams.add(new BasicNameValuePair("didFollow",params[0]));
                emaParams.add(new BasicNameValuePair("howHelpful",params[1]));
                emaParams.add(new BasicNameValuePair("didSmokeY",params[2]));
                emaParams.add(new BasicNameValuePair("didSmokeN",params[3]));
                emaParams.add(new BasicNameValuePair("doInstead",params[4]));
                emaParams.add(new BasicNameValuePair("situation",params[fullLength-2]));
                emaParams.add(new BasicNameValuePair("intention",params[fullLength-1]));
                break;
            case MyQuitCalendarSuccessSurvey.KEY_SURVEY_SUCCESS:
                emaParams.add(new BasicNameValuePair("areContext",params[0]));
                emaParams.add(new BasicNameValuePair("didFollow",params[1]));
                emaParams.add(new BasicNameValuePair("howHelpful",params[2]));
                emaParams.add(new BasicNameValuePair("didSmokeY",params[3]));
                emaParams.add(new BasicNameValuePair("didSmokeN",params[4]));
                emaParams.add(new BasicNameValuePair("doInstead",params[5]));
                emaParams.add(new BasicNameValuePair("situation",params[fullLength-2]));
                emaParams.add(new BasicNameValuePair("intention",params[fullLength-1]));
                break;
            case MyQuitEndOfDaySurvey.KEY_SURVEY_SUCCESS:
                emaParams.add(new BasicNameValuePair("howManyCigs", params[0]));
                emaParams.add(new BasicNameValuePair("pssFamily", params[1]));
                emaParams.add(new BasicNameValuePair("pssMoney", params[2]));
                emaParams.add(new BasicNameValuePair("pssSchool", params[3]));
                emaParams.add(new BasicNameValuePair("pssWork", params[4]));
                emaParams.add(new BasicNameValuePair("naDistressed", params[5]));
                emaParams.add(new BasicNameValuePair("naIrritable", params[6]));
                emaParams.add(new BasicNameValuePair("naSad", params[7]));
                emaParams.add(new BasicNameValuePair("naScared", params[8]));
                emaParams.add(new BasicNameValuePair("naTense", params[9]));
                emaParams.add(new BasicNameValuePair("naUpset", params[10]));
                emaParams.add(new BasicNameValuePair("paCheerful", params[11]));
                emaParams.add(new BasicNameValuePair("paEnthusiastic", params[12]));
                emaParams.add(new BasicNameValuePair("paHappy", params[13]));
                emaParams.add(new BasicNameValuePair("paInterested", params[14]));
                emaParams.add(new BasicNameValuePair("paProud", params[15]));
                emaParams.add(new BasicNameValuePair("troubleCigs", params[16]));
                emaParams.add(new BasicNameValuePair("botherDesire", params[17]));
                emaParams.add(new BasicNameValuePair("frequentUrges", params[18]));
                emaParams.add(new BasicNameValuePair("confidentQuit", params[19]));
                emaParams.add(new BasicNameValuePair("helpQuit", params[20]));
                emaParams.add(new BasicNameValuePair("vapeCig", params[21]));
                emaParams.add(new BasicNameValuePair("vapeCigCount", params[22]));
                emaParams.add(new BasicNameValuePair("vapePuffCount", params[23]));
                break;
            case MyQuitOffSuccessSurvey.KEY_SURVEY_SUCCESS:
                break;
            case MyQuitRandomSurvey.KEY_SURVEY_SUCCESS:
                emaParams.add(new BasicNameValuePair("whereAt", params[0]));
                emaParams.add(new BasicNameValuePair("whereAtText", params[1]));
                emaParams.add(new BasicNameValuePair("whatDoing", params[2]));
                emaParams.add(new BasicNameValuePair("whatDoingText", params[3]));
                emaParams.add(new BasicNameValuePair("whoWith", params[4]));
                emaParams.add(new BasicNameValuePair("hadThese", params[5]));
                emaParams.add(new BasicNameValuePair("othersSmoke", params[6]));
                emaParams.add(new BasicNameValuePair("allowSmoke", params[7]));
                emaParams.add(new BasicNameValuePair("persistStressor", params[8]));
                emaParams.add(new BasicNameValuePair("occurStressor", params[9]));
                emaParams.add(new BasicNameValuePair("naScared", params[10]));
                emaParams.add(new BasicNameValuePair("naUpset", params[11]));
                emaParams.add(new BasicNameValuePair("naDistressed", params[12]));
                emaParams.add(new BasicNameValuePair("naTense", params[13]));
                emaParams.add(new BasicNameValuePair("naSad", params[14]));
                emaParams.add(new BasicNameValuePair("naIrritable", params[15]));
                emaParams.add(new BasicNameValuePair("naHopeless", params[16]));
                emaParams.add(new BasicNameValuePair("paHappy", params[17]));
                emaParams.add(new BasicNameValuePair("paCheerful", params[18]));
                emaParams.add(new BasicNameValuePair("paEnthusiastic", params[19]));
                emaParams.add(new BasicNameValuePair("paProud", params[20]));
                emaParams.add(new BasicNameValuePair("paInterested", params[21]));
                emaParams.add(new BasicNameValuePair("pssSchool", params[22]));
                emaParams.add(new BasicNameValuePair("pssWork", params[23]));
                emaParams.add(new BasicNameValuePair("pssFamily", params[24]));
                emaParams.add(new BasicNameValuePair("pssMoney", params[25]));
                emaParams.add(new BasicNameValuePair("ccMindCig", params[26]));
                emaParams.add(new BasicNameValuePair("ccMindVape", params[27]));
                emaParams.add(new BasicNameValuePair("ccDesireCig", params[28]));
                emaParams.add(new BasicNameValuePair("ccDesireVape", params[29]));
                emaParams.add(new BasicNameValuePair("ccUrgeCig", params[30]));
                emaParams.add(new BasicNameValuePair("ccUrgeVape", params[31]));
                emaParams.add(new BasicNameValuePair("anhedoniaPeople", params[32]));
                emaParams.add(new BasicNameValuePair("anhedoniaHobby", params[33]));
                emaParams.add(new BasicNameValuePair("anhedoniaSocial", params[34]));
                break;
            case MyQuitSmokeSurvey.KEY_SURVEY_SUCCESS:
                emaParams.add(new BasicNameValuePair("whereAt", params[0]));
                emaParams.add(new BasicNameValuePair("whereAtText", params[1]));
                emaParams.add(new BasicNameValuePair("whatDoing", params[2]));
                emaParams.add(new BasicNameValuePair("whatDoingText", params[3]));
                emaParams.add(new BasicNameValuePair("reasonSmoke", params[4]));
                emaParams.add(new BasicNameValuePair("reasonSmokeText", params[5]));
                emaParams.add(new BasicNameValuePair("whoWith", params[6]));
                emaParams.add(new BasicNameValuePair("hadThese", params[7]));
                emaParams.add(new BasicNameValuePair("othersSmoke", params[8]));
                emaParams.add(new BasicNameValuePair("allowSmoke", params[9]));
                emaParams.add(new BasicNameValuePair("persistStressor", params[10]));
                emaParams.add(new BasicNameValuePair("occurStressor", params[11]));
                emaParams.add(new BasicNameValuePair("naScared", params[12]));
                emaParams.add(new BasicNameValuePair("naUpset", params[13]));
                emaParams.add(new BasicNameValuePair("naDistressed", params[14]));
                emaParams.add(new BasicNameValuePair("naTense", params[15]));
                emaParams.add(new BasicNameValuePair("naSad", params[16]));
                emaParams.add(new BasicNameValuePair("naIrritable", params[17]));
                emaParams.add(new BasicNameValuePair("naHopeless", params[18]));
                emaParams.add(new BasicNameValuePair("paHappy", params[19]));
                emaParams.add(new BasicNameValuePair("paCheerful", params[20]));
                emaParams.add(new BasicNameValuePair("paEnthusiastic", params[21]));
                emaParams.add(new BasicNameValuePair("paProud", params[22]));
                emaParams.add(new BasicNameValuePair("paInterested", params[23]));
                emaParams.add(new BasicNameValuePair("pssSchool", params[24]));
                emaParams.add(new BasicNameValuePair("pssWork", params[25]));
                emaParams.add(new BasicNameValuePair("pssFamily", params[26]));
                emaParams.add(new BasicNameValuePair("pssMoney", params[27]));
                emaParams.add(new BasicNameValuePair("ccMindCig", params[28]));
                emaParams.add(new BasicNameValuePair("ccDesireCig", params[29]));
                emaParams.add(new BasicNameValuePair("ccUrgeCig", params[30]));
                emaParams.add(new BasicNameValuePair("anhedoniaPeople", params[31]));
                emaParams.add(new BasicNameValuePair("anhedoniaHobby", params[32]));
                emaParams.add(new BasicNameValuePair("anhedoniaSocial", params[33]));
                break;
        }

        emaParams.add(new BasicNameValuePair("username",params[fullLength-4]));
        emaParams.add(new BasicNameValuePair("surveytype",params[fullLength-3]));
        emaParams.add(new BasicNameValuePair("sessionid",params[fullLength-5]));
        emaParams.add(new BasicNameValuePair("datetime",datetime));
        emaParams.add(new BasicNameValuePair("allowed",String.valueOf(phpAllowInteger)));
        Log.d("MQU-PHP","Caught" + params[fullLength-2] + params[fullLength-1] + params[fullLength-3]);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(emaParams));
            Log.d("MQU-PHP","URL is now encoded");
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            return false;
        }

        try {
            HttpResponse response = httpClient.execute(httpPost);
            // write response to log
            Log.d("MQU-PHP", "Http Post Response:" + response.toString());
            return true;
        } catch (ClientProtocolException e) {
            // Log exception
            Log.d("MQU-PHP", "Http Post Response CPE error");
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            Log.d("MQU-PHP", "Http Post Response IO error");
            // Log exception
            e.printStackTrace();
            return false;
        }
    }

    public static boolean syncRogueEvent(String userName, String rogueSituation, String calledDateTime)  {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(urlPostRogueEvent);
        List<NameValuePair> rogueParams = new ArrayList<NameValuePair>();
        rogueParams.add(new BasicNameValuePair("username",userName));
        rogueParams.add(new BasicNameValuePair("situation",rogueSituation));
        rogueParams.add(new BasicNameValuePair("datetime",calledDateTime));
        rogueParams.add(new BasicNameValuePair("allowed",String.valueOf(phpAllowInteger)));
        Log.d("MQU-PHP","Caught" + userName + rogueSituation + calledDateTime);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(rogueParams));
            Log.d("MQU-PHP","URL is now encoded");
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            return false;
        }

        try {
            HttpResponse response = httpClient.execute(httpPost);
            // write response to log
            Log.d("MQU-PHP", "Http Post Response:" + response.toString());
            return true;
        } catch (ClientProtocolException e) {
            // Log exception
            Log.d("MQU-PHP", "Http Post Response CPE error");
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            Log.d("MQU-PHP", "Http Post Response IO error");
            // Log exception
            e.printStackTrace();
            return false;
        }
    }

    public static boolean syncTrackerEvent(String userName, String rogueSituation, String metaData, String calledDateTime)  {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(urlTrackerEvent);
        List<NameValuePair> rogueParams = new ArrayList<NameValuePair>();
        rogueParams.add(new BasicNameValuePair("username",userName));
        rogueParams.add(new BasicNameValuePair("event",rogueSituation));
        rogueParams.add(new BasicNameValuePair("meta",metaData));
        rogueParams.add(new BasicNameValuePair("datetime",calledDateTime));
        rogueParams.add(new BasicNameValuePair("allowed",String.valueOf(phpAllowInteger)));
        Log.d("MQU-PHP","Caught" + userName + rogueSituation + calledDateTime);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(rogueParams));
            Log.d("MQU-PHP","URL is now encoded");
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            return false;
        }

        try {
            HttpResponse response = httpClient.execute(httpPost);
            // write response to log
            Log.d("MQU-PHP", "Http Post Response:" + response.toString());
            return true;
        } catch (ClientProtocolException e) {
            // Log exception
            Log.d("MQU-PHP", "Http Post Response CPE error");
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            Log.d("MQU-PHP", "Http Post Response IO error");
            // Log exception
            e.printStackTrace();
            return false;
        }
    }

    public static boolean syncGPSEvent(String userName, String fulltime, String latitude, String longitude, String accuracy)  {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(urlPostGPSEvent);
        List<NameValuePair> rogueParams = new ArrayList<NameValuePair>();
        rogueParams.add(new BasicNameValuePair("username",userName));
        rogueParams.add(new BasicNameValuePair("fulltime",fulltime));
        rogueParams.add(new BasicNameValuePair("latitude",latitude));
        rogueParams.add(new BasicNameValuePair("longitude",longitude));
        rogueParams.add(new BasicNameValuePair("accuracy",accuracy));
        rogueParams.add(new BasicNameValuePair("allowed",String.valueOf(phpAllowInteger)));
        Log.d("MQU-PHP","Caught" + userName + latitude + longitude);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(rogueParams));
            Log.d("MQU-PHP","URL is now encoded");
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            return false;
        }

        try {
            HttpResponse response = httpClient.execute(httpPost);
            // write response to log
            Log.d("MQU-PHP", "Http Post Response:" + response.toString());
            return true;
        } catch (ClientProtocolException e) {
            // Log exception
            Log.d("MQU-PHP", "Http Post Response CPE error");
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            Log.d("MQU-PHP", "Http Post Response IO error");
            // Log exception
            e.printStackTrace();
            return false;
        }
    }

    public static boolean syncCalendarEvent(String userName, String calDate, String hour, String situation, String intention, String timeChanged)  {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(urlPostCalendarEvent);
        List<NameValuePair> rogueParams = new ArrayList<NameValuePair>();
        rogueParams.add(new BasicNameValuePair("username",userName));
        rogueParams.add(new BasicNameValuePair("caldate",calDate));
        rogueParams.add(new BasicNameValuePair("hour",hour));
        rogueParams.add(new BasicNameValuePair("situation",situation));
        rogueParams.add(new BasicNameValuePair("intention",intention));
        rogueParams.add(new BasicNameValuePair("timechanged",timeChanged));
        rogueParams.add(new BasicNameValuePair("allowed",String.valueOf(phpAllowInteger)));
        Log.d("MQU-PHP","Caught" + userName + hour + timeChanged);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(rogueParams));
            Log.d("MQU-PHP","URL is now encoded");
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            return false;
        }

        try {
            HttpResponse response = httpClient.execute(httpPost);
            // write response to log
            Log.d("MQU-PHP", "Http Post Response:" + response.toString());
            return true;
        } catch (ClientProtocolException e) {
            // Log exception
            Log.d("MQU-PHP", "Http Post Response CPE error");
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            Log.d("MQU-PHP", "Http Post Response IO error");
            // Log exception
            e.printStackTrace();
            return false;
        }
    }

    public static void postRogueStatus(String userName, String rogueSituation, String calledDateTime) {
        String fileName = rogueSuccessTable;
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(MyQuitCSVHelper.logPath + fileName,true));
            String[] pushRow = new String[] {userName, rogueSituation, calledDateTime};
            writer.writeNext(pushRow);
            writer.close();
        } catch (IOException e) {
            Log.e("MyQuitUSC","RoguePushSuccess table write failure");
            e.printStackTrace();
        }

    }
    public static void postTrackerStatus(String userName, String trackerEvent, String metaData, String calledDateTime) {
        String fileName = trackerSuccessTable;
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(MyQuitCSVHelper.logPath + fileName,true));
            String[] pushRow = new String[] {userName, trackerEvent, metaData, calledDateTime};
            writer.writeNext(pushRow);
            writer.close();
        } catch (IOException e) {
            Log.e("MyQuitUSC","RoguePushSuccess table write failure");
            e.printStackTrace();
        }

    }

    public static void postGPSStatus(String userName, String fulltime, String latitude, String longitude, String accuracy) {
        String fileName = gpsSuccessTable;
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(MyQuitCSVHelper.logPath + fileName,true));
            String[] pushRow = new String[] {userName, fulltime, latitude, longitude, accuracy};
            writer.writeNext(pushRow);
            writer.close();
        } catch (IOException e) {
            Log.e("MyQuitUSC","RoguePushSuccess table write failure");
            e.printStackTrace();
        }

    }


    public static void postCalendarStatus(String userName, String calDate, String hour, String situation, String intention, String timeChanged) {
        String fileName = calendarSuccessTable;
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(MyQuitCSVHelper.logPath + fileName,true));
            String[] pushRow = new String[] {userName, calDate, hour, situation, intention, timeChanged};
            writer.writeNext(pushRow);
            writer.close();
        } catch (IOException e) {
            Log.e("MyQuitUSC","CalendarPushSuccess table write failure");
            e.printStackTrace();
        }

    }

    public static void postEMAStatus(List<NameValuePair>  emaParamList) {
        String fileName = emaSuccessTable;
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(MyQuitCSVHelper.logPath + fileName,true));
            String[] pushRow = new String[emaParamList.size()];
            int count = 0;
            for(NameValuePair iterate: emaParamList){
                pushRow[count] = iterate.getValue().toString();
            }
            writer.writeNext(pushRow);
            writer.close();
        } catch (IOException e) {
            Log.e("MyQuitUSC","EMAPushSuccess table write failure");
            e.printStackTrace();
        }

    }

    public static void loopThroughRogueEvents() {
        String fileName = rogueSuccessTable;
        try {
            CSVReader reader = new CSVReader(new FileReader(MyQuitCSVHelper.logPath + fileName));
            List<String[]> pullTimes = reader.readAll();
            reader.close();
            List<String[]> newPush = null;
            for(String[] row: pullTimes){
                if(!syncRogueEvent(row[0], row[1], row[2])){
                    newPush.add(row);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeLastFileUpload() {
        String fileName = "ServerFileAccess.csv";
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(MyQuitCSVHelper.logPath + fileName, true));
            String[] pushRow = new String[] {MyQuitCSVHelper.getFulltime()};
            writer.writeNext(pushRow);
            writer.close();
        } catch (IOException e) {
            Log.e("MyQuitUSC","ServerFileAccess write failure");
            e.printStackTrace();
        }
    }

    public static String[] pullLastEvent() {
        try {
            CSVReader reader = new CSVReader(new FileReader(MyQuitCSVHelper.logPath + "ServerFileAccess.csv"));
            String[] report;
            String[] report2 = null;
            while((report = reader.readNext()) != null) {
                report2 = report;
            }
            return report2;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isLastEventPastXMinutes(int minutes){
        try {
            String stringTime = pullLastEvent()[0];
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Calendar now = Calendar.getInstance();
            Date timeNow = now.getTime();
            try {
                Date timeThen = sdf.parse(stringTime);
                long compareTime = timeNow.getTime() - timeThen.getTime();
                Log.d("MQU-PHP", "PHP loop event confirmed"  + (compareTime > (minutes * 60 * 1000)));
                return (compareTime > (minutes * 60 * 1000));
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
        }
        catch(NullPointerException neo) {
            return true;
        }
    }

    public static void decidePHPPost(){
        if(isLastEventPastXMinutes(60)){
            Log.d("MQU-PHP", "Launching php event");
            writeLastFileUpload();
            Log.d("MQU-PHP", "Wrote last file upload");
            new SyncRogueEvent().execute();
            new SyncTrackerEvent().execute();
            new SyncEMAEvent().execute();
            new SyncCalendarEvent().execute();
            new SyncGPSEvent().execute();
            Log.d("MQU-PHP", "Finished looping");
        }
    }


}
