package edu.usc.reach.myquituscnew;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.IOException;

public class MyQuitService extends Service {
    public MyQuitService() {
    }


    private void choosePromptingSequence(int emaType, int decisionSessionID, int surveyID) {
        if (MyQuitCSVHelper.pullLastEvent(emaType)[0].equalsIgnoreCase("emaPrompted")) {
            MyQuitCSVHelper.logEMAEvents(emaType, "emaReprompt1", MyQuitCSVHelper.getFulltime(),
                    MyQuitCSVHelper.pullLastEvent(emaType)[2],MyQuitCSVHelper.pullLastEvent(emaType)[3]);
        }
        else if (MyQuitCSVHelper.pullLastEvent(emaType)[0].substring(0,11).equalsIgnoreCase("emaReprompt")) {
            int suffix = MyQuitCSVHelper.convertRepromptChar(emaType);
            suffix++;
            String label = "emaReprompt" + suffix;
            MyQuitCSVHelper.logEMAEvents(emaType, label, MyQuitCSVHelper.getFulltime(),
                    MyQuitCSVHelper.pullLastEvent(emaType)[2],MyQuitCSVHelper.pullLastEvent(emaType)[3]);
        }
        else {
            MyQuitCSVHelper.logEMAEvents(emaType, "emaPrompted", MyQuitCSVHelper.getFulltime(),
                    MyQuitCSVHelper.pullLastEvent(emaType)[2],MyQuitCSVHelper.pullLastEvent(emaType)[3]);
        }
    }

    private void decisionEMA(int emaType, int decisionSessionID, int surveyID) {
        PendingIntent emaIntent;
        Intent launchEMA = new Intent(this, MyQuitEMA.class);
        launchEMA.putExtra("Survey",surveyID);
        Log.d("MQU-DECIDE","putted extra is" + surveyID);
        MyQuitEMAHelper.pushLastSessionID(MyQuitCSVHelper.getFullDate(),surveyID,decisionSessionID);
        //launchEMA.putExtra("Position",0);
        //launchEMA.putExtra("SessionID",decisionSessionID);
        //String decID = String.valueOf(decisionSessionID);
        //launchEMA.putExtra("StringSessionID",decID);
        Log.d("MY-QUIT-USC","pushed session ID is" + MyQuitEMAHelper.pullLastSessionID(MyQuitCSVHelper.getFullDate(),
                surveyID));
        try {
            MyQuitEMAHelper.pushSpecificAnswer(MyQuitCSVHelper.getFullDate(),
                    MyQuitCSVHelper.getTimeOnly(),
                    decisionSessionID, 9999, 0, true, MyQuitEMA.retrieveSurveyLength(surveyID),
                    surveyID);
                    //decisionSessionID, 9999, 0, true, MyQuitEMA.retrieveSurveyLength(MyQuitCheckSuccessSurvey.KEY_SURVEY_SUCCESS),
                    //MyQuitCheckSuccessSurvey.KEY_SURVEY_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("MY-QUIT-USC", "Something's wrong...");
        }
        launchEMA.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        launchEMA.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        launchEMA.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        emaIntent = PendingIntent.getActivity(this, 0, launchEMA, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri tone= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder emaNotification = new NotificationCompat.Builder(this)
                .setContentTitle("Click here!")
                .setContentText("New survey available")
                .setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true)
                .setSound(tone)
                .setContentIntent(emaIntent);
        ;
        Notification emaNotify = emaNotification.build();
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        choosePromptingSequence(emaType, decisionSessionID, surveyID);
        mNotificationManager.notify(22222, emaNotify);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String decisionAction;
        int surveyID = 0;
        int decisionSessionID = 0;
        try {
            decisionAction = intent.getStringExtra("Action");
            surveyID = intent.getIntExtra("Survey",1);
            decisionSessionID = intent.getIntExtra("SessionID",0);
        }
        catch(Exception e) {
            decisionAction = "Do Nothing";
        }
        int emaType = surveyID;
        String actionString = "Processing...";
        if (decisionAction.matches("SFTP")) {
            actionString = "Uploading data to the cloud...";
        }
        else if (decisionAction.matches("EMA")) {
            actionString = "Thank you for participating in the study.";
        }
        else if (decisionAction.matches("Calendar")) {
            actionString = "Thank you for participating in the study.";
        }
        else {
            actionString = "Remember to record your smoking!";
        }
        PendingIntent homeScreen;
        Intent launchHomeScreen = new Intent(this, MyQuitHomeScreen.class);
        launchHomeScreen.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        launchHomeScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        launchHomeScreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        homeScreen = PendingIntent.getActivity(this, 0, launchHomeScreen, 0);
        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("MyQuit USC")
                .setContentText("Compliance; 100%")
                .setContentText(actionString)
                .setContentIntent(homeScreen)
                .setSmallIcon(R.drawable.ic_launcher);
        Notification myQuitSFTPNotify = notifBuilder.build();
        startForeground(999, myQuitSFTPNotify);

       if (decisionAction.matches("EMA")) {
           decisionEMA(emaType, decisionSessionID, surveyID);
           MyQuitEMA.passThroughUpload(decisionSessionID,emaType);
       }

       else if (decisionAction.matches("Calendar")){
           PendingIntent calendarIntent;
           Intent launchCalendar = new Intent(this, MyQuitIntentPrompt.class);

           launchCalendar.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
           launchCalendar.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
           launchCalendar.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           calendarIntent = PendingIntent.getActivity(this, 0, launchCalendar, 0);
           Uri tone= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
           NotificationCompat.Builder calendarNotification = new NotificationCompat.Builder(this)
                   .setContentTitle("Click here!")
                   .setContentText("Let's try something else instead of smoking!")
                   .setSmallIcon(R.drawable.ic_launcher)
                   .setAutoCancel(true)
                   .setSound(tone)
                   .setContentIntent(calendarIntent);
           ;
           Notification calendarNotify = calendarNotification.build();
           NotificationManager mNotificationManager =
                   (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
           mNotificationManager.notify(3, calendarNotify);
       }
        return START_STICKY; //TODO: Maybe keep this
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
