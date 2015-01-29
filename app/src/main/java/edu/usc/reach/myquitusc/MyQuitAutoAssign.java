package edu.usc.reach.myquitusc;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Eldin on 1/24/15.
 */
public class MyQuitAutoAssign {

    final public static int minimumLabels = 5;

    private static String[] pullNewTimes() throws IOException {
        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        if(dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY){
            return MyQuitCSVHelper.pullDateTimes("DEFAULT_WEEKEND");
        }
        else {
            return MyQuitCSVHelper.pullDateTimes("DEFAULT_WEEKDAY");
        }
    }

    public static boolean minimumLabelConfirm(String calledDate) {
        try {
            String[] pulledTimes = MyQuitCSVHelper.pullDateTimes(calledDate);
            int count = 0;
            for (String array : pulledTimes) {
                if (array.length() > 8) {
                    count++;
                }
            }
            return (count > minimumLabels);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
    }

    public static void autoAssignCalendar() {
        try {
            if(!minimumLabelConfirm(MyQuitCSVHelper.getFullDate())){
                String [] newTimes = pullNewTimes();
                MyQuitCSVHelper.pushDateTimes(MyQuitCSVHelper.getFullDate(),newTimes);
            }
        } catch (IOException e) {
            e.printStackTrace();
            }
        }

    public static void populateCalendar() {
        
    }
}
