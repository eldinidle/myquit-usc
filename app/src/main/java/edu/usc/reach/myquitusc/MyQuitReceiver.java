package edu.usc.reach.myquitusc;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.io.IOException;
import java.util.Calendar;
import org.apache.commons.net.ftp.FTPClient;

public class MyQuitReceiver extends BroadcastReceiver {
    static final int KEY_NUM_REPROMPTS = 3;

    public MyQuitReceiver() {
    }

    public static boolean sftpUpload(String userName, String filePath) {
        FTPClient uscHost = new FTPClient();

        try {
            uscHost.connect("ftp://mysmoke.usc.edu", 22);
            uscHost.login("MyQuitUSCMobilePhone", "thisisthepassword");
            MyQuitCSVHelper.logEMAEvents("Login success", MyQuitCSVHelper.getFulltime());
            try {
                uscHost.changeWorkingDirectory("/" + userName);
                MyQuitCSVHelper.logEMAEvents("Directory change success", MyQuitCSVHelper.getFulltime());
            }
            catch (Exception de) {
                uscHost.makeDirectory("/" + userName);
                uscHost.changeWorkingDirectory("/" + userName);
                MyQuitCSVHelper.logEMAEvents("Made directory success", MyQuitCSVHelper.getFulltime());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar realTime = Calendar.getInstance();
        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(context)
                .setContentTitle("MyQuit USC")
                .setContentText("Deciding...")
                .setSmallIcon(R.drawable.ic_launcher)
                .setOngoing(false)
                .setAutoCancel(true);
        Notification myQuitSFTPNotify = notifBuilder.build();
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, myQuitSFTPNotify);

        if (MyQuitCSVHelper.pullLastEvent()[0].equalsIgnoreCase("intentPresented")) {
            Intent launchService = new Intent(context, MyQuitService.class);
            launchService.putExtra("Action","EMA");
            launchService.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startService(launchService);
        }
        else if (MyQuitCSVHelper.pullLastEvent()[0].substring(0,11).equalsIgnoreCase("emaReprompt") & (MyQuitCSVHelper.convertRepromptChar() > KEY_NUM_REPROMPTS)) {
            MyQuitCSVHelper.logEMAEvents("emaMissedSurvey", MyQuitCSVHelper.getFulltime());
        }
        else if ((MyQuitCSVHelper.pullLastEvent()[0].equalsIgnoreCase("emaPrompted") | MyQuitCSVHelper.pullLastEvent()[0].substring(0,11).equalsIgnoreCase("emaReprompt"))  & MyQuitCSVHelper.isLastEventPastXMinutes(3)) {
            Intent launchService = new Intent(context, MyQuitService.class);
            launchService.putExtra("Action","EMA");
            launchService.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startService(launchService);
        }




        mNotificationManager.cancel(1);

//        throw new UnsupportedOperationException("Not yet implemented");
    }
}
