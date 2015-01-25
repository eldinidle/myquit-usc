package edu.usc.reach.myquitusc;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;

public class MyQuitReceiver extends BroadcastReceiver {
   // static final int KEY_NUM_REPROMPTS = 3;

    public MyQuitReceiver() {
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

        MyQuitEMAHelper.setUpCalendarEMA();
        MyQuitEMAHelper.decideEMA(context, MyQuitCSVHelper.ROGUE_EMA_KEY);
        MyQuitEMAHelper.decideEMA(context, MyQuitCSVHelper.CALENDAR_EMA_KEY);
        MyQuitEMAHelper.decideEMA(context, MyQuitCSVHelper.END_OF_DAY_EMA_KEY);
        MyQuitCalendarHelper.decideCalendar(context);
        MyQuitPHP.decidePHPPost();
        Log.d("MyQuitUSC", "Finished deciding");
        /*
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
        */


        mNotificationManager.cancel(1);

//        throw new UnsupportedOperationException("Not yet implemented");
    }
}
