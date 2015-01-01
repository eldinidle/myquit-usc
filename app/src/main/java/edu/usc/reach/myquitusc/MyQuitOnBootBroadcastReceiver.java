package edu.usc.reach.myquitusc;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

public class MyQuitOnBootBroadcastReceiver extends BroadcastReceiver {
    public MyQuitOnBootBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("MY-QUIT-USC", "Boot broadcasted, starting MyQuitReceiver...");
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            if(MyQuitCSVHelper.pullLastEvent("loginSuccess") == null) {
                AlarmManager alarmMgr;
                PendingIntent alarmIntent;
                alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                Intent loopAlarm = new Intent(context, MyQuitReceiver.class);
                alarmIntent = PendingIntent.getBroadcast(context, 0, loopAlarm, 0);
                Calendar currentTime = Calendar.getInstance();
                alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, currentTime.getTimeInMillis(),
                        1000 * 60 * 2, alarmIntent);
            }
            Log.d("MY-QUIT-USC", "Success, receiver started.");
        }
        if (intent.getAction().equals("android.intent.action.QUICKBOOT_POWERON")) {
            if(MyQuitCSVHelper.pullLastEvent("loginSuccess") == null) {
                AlarmManager alarmMgr;
                PendingIntent alarmIntent;
                alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                Intent loopAlarm = new Intent(context, MyQuitReceiver.class);
                alarmIntent = PendingIntent.getBroadcast(context, 0, loopAlarm, 0);
                Calendar currentTime = Calendar.getInstance();
                alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, currentTime.getTimeInMillis(),
                        1000 * 60 * 2, alarmIntent);
            Log.d("MY-QUIT-USC", "Success, receiver started.");
            }
        }
  //      throw new UnsupportedOperationException("Not yet implemented");
    }
}
