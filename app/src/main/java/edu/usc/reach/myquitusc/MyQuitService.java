package edu.usc.reach.myquitusc;

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

public class MyQuitService extends Service {
    public MyQuitService() {
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String decisionAction = intent.getStringExtra("Action");
        String actionString = "Processing...";
        if (decisionAction.matches("SFTP")) {
            actionString = "Uploading data to the cloud...";
        }
        if (decisionAction.matches("EMA")) {
            actionString = "Thank you for participating in the study.";
        }
        else {
            actionString = "";
        }
        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("MyQuit USC")
                .setContentText("Compliance; 100%")
                .setContentText(actionString)
                .setSmallIcon(R.drawable.ic_launcher);
        Notification myQuitSFTPNotify = notifBuilder.build();
        startForeground(999, myQuitSFTPNotify);

       if (decisionAction.matches("EMA")) {
            PendingIntent emaIntent;
            Intent launchEMA = new Intent(this, MyQuitEMA.class);
            launchEMA.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            launchEMA.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            launchEMA.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            emaIntent = PendingIntent.getActivity(this, 0, launchEMA, 0);
            Uri tone= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
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
           if (MyQuitCSVHelper.pullLastEvent()[0].equalsIgnoreCase("emaPrompted")) {
               MyQuitCSVHelper.logEMAEvents("emaReprompt1", MyQuitCSVHelper.getFulltime());
           }
           else if (MyQuitCSVHelper.pullLastEvent()[0].substring(0,11).equalsIgnoreCase("emaReprompt")) {
               int suffix = MyQuitCSVHelper.convertRepromptChar();
               suffix++;
               String label = "emaReprompt" + suffix;
               MyQuitCSVHelper.logEMAEvents(label, MyQuitCSVHelper.getFulltime());
           }
           else {
               MyQuitCSVHelper.logEMAEvents("emaPrompted", MyQuitCSVHelper.getFulltime());
           }
           mNotificationManager.notify(2, emaNotify);

       }
        return START_STICKY; //TODO: Maybe keep this
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
