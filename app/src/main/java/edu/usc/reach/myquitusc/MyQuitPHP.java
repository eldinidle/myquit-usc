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

import edu.usc.reach.myquitusc.Surveys.MyQuitCheckSuccessSurvey;

/**
 * Created by Eldin on 1/14/15.
 */
public class MyQuitPHP {

    private final static int phpAllowInteger = 1265;

    private final static String rogueSuccessTable = "RoguePushSuccessTable.csv";
    private final static String trackerSuccessTable = "TrackerPushSuccessTable.csv";
    private final static String emaSuccessTable = "EMAPushSuccessTable.csv";
    private final static String unPlannedSuccessTable = "UnplannedPushSuccessTable.csv";

    private static final String urlPostRogueEvent = "http://myquitadmin.usc.edu/data.php";
    private static final String urlTrackerEvent = "http://myquitadmin.usc.edu/tracker.php";
    private static final String urlPostEMAEvent = "http://myquitadmin.usc.edu/myquitema.php";
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
            if(surveyType== MyQuitCheckSuccessSurvey.KEY_SURVEY_SUCCESS){
                emaParams.add(new BasicNameValuePair("didFollow",params[0][0]));
                emaParams.add(new BasicNameValuePair("howHelpful",params[0][1]));
                emaParams.add(new BasicNameValuePair("didSmokeY",params[0][2]));
                emaParams.add(new BasicNameValuePair("didSmokeN",params[0][3]));
                emaParams.add(new BasicNameValuePair("doInstead",params[0][4]));
                emaParams.add(new BasicNameValuePair("situation",params[0][fullLength-2]));
                emaParams.add(new BasicNameValuePair("intention",params[0][fullLength-1]));
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
                Log.d("MQU-PHP","Failure FNF");
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
        if(surveyType==MyQuitCheckSuccessSurvey.KEY_SURVEY_SUCCESS){
            emaParams.add(new BasicNameValuePair("didFollow",params[0]));
            emaParams.add(new BasicNameValuePair("howHelpful",params[1]));
            emaParams.add(new BasicNameValuePair("didSmokeY",params[2]));
            emaParams.add(new BasicNameValuePair("didSmokeN",params[3]));
            emaParams.add(new BasicNameValuePair("doInstead",params[4]));
            emaParams.add(new BasicNameValuePair("situation",params[fullLength-2]));
            emaParams.add(new BasicNameValuePair("intention",params[fullLength-1]));
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
                Log.d("MQU-PHP", "PHP loop event confirmed");
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
            new SyncEMAEvent().execute();
            new SyncTrackerEvent().execute();
            Log.d("MQU-PHP", "Finished looping");
        }
    }


}
