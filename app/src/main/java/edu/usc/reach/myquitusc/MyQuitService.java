package edu.usc.reach.myquitusc;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
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
        else {
            actionString = "";
        }
        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Thank you for participating in MyQuit USC")
                .setContentText(actionString)
                .setSmallIcon(R.drawable.ic_launcher)
                .setOngoing(true)
                .setAutoCancel(false);
        Notification myQuitSFTPNotify = notifBuilder.build();
        startForeground(1, myQuitSFTPNotify);
        return START_STICKY; //TODO: Maybe keep this
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
