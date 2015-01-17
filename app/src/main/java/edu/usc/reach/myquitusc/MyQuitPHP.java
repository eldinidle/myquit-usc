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

/**
 * Created by Eldin on 1/14/15.
 */
public class MyQuitPHP {

    protected final static int phpAllowInteger = 1265;



    private static final String urlPostRogueEvent = "http://myquitadmin.usc.edu/data.php";
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

    static class SyncRogueEvent extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... params) {
            String fileName = "RoguePushSuccessTable.csv";
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

    public static void postRogueEvent(String userName, String rogueSituation, String calledDateTime)  {
        String fixedSituation = rogueSituation.replace("'","");
        new PostRogueEvent().execute(userName, fixedSituation, calledDateTime);
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

    public static void postRogueStatus(String userName, String rogueSituation, String calledDateTime) {
        String fileName = "RoguePushSuccessTable.csv";
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

    public static void loopThroughRogueEvents() {
        String fileName = "RoguePushSuccessTable.csv";
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
            Log.d("MQU-PHP", "Finished looping");
        }
    }

    public static void postUnplannedEvent(String userName, String unplannedSituation, String presentedIntent, String calledDate) {

    }

}
