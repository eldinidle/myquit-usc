package edu.usc.reach.myquitusc;

import android.util.Log;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Eldin on 3/30/15.
 */
public class MyQuitExperienceSampling {
    private static final String scheduleFileName = MyQuitCSVHelper.logPath + "ESMSchedule.csv";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    private static final SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");

    public static void scheduleAllRandomEMA(String loginDate) throws ParseException, IOException {
            Date endDate = dateOnly.parse(loginDate);
            Calendar rollingCal = Calendar.getInstance();
            Date nowDate = rollingCal.getTime();
            while (dateBefore(nowDate,endDate)) {
                scheduleRandomEMA(nowDate);
                rollingCal.add(Calendar.DAY_OF_YEAR, 1);
                nowDate = rollingCal.getTime();
            }

    }


    public static boolean validPromptTime(Date nowTime) {
        Calendar dynamicCal = Calendar.getInstance();
        Date baseDate;
        Date hourDate;
        try {
            String[] pulledTime = pullTimes(nowTime);
            for(String timePull: pulledTime){
                baseDate = sdf.parse(timePull);
                dynamicCal.setTime(baseDate);
                dynamicCal.add(Calendar.HOUR,1);
                hourDate = dynamicCal.getTime();
                if(nowTime.after(baseDate) && nowTime.before(hourDate)){
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean dateBefore(Date nowDate, Date thenDate){
        Calendar thisCal = Calendar.getInstance();
        Calendar compareCal = Calendar.getInstance();
        thisCal.setTime(nowDate);
        compareCal.setTime(thenDate);
        int nowMonthGet = thisCal.get(Calendar.MONTH);
        int thenMonthGet = compareCal.get(Calendar.MONTH);
        int nowDayGet = thisCal.get(Calendar.DAY_OF_MONTH);
        int thenDayGet = compareCal.get(Calendar.DAY_OF_MONTH);
        int nowYearGet = thisCal.get(Calendar.YEAR);
        int thenYearGet = compareCal.get(Calendar.YEAR);
        if(nowYearGet>thenYearGet){
            return false;
        }
        else{
            if(nowMonthGet>thenMonthGet){
                return false;
            }
            else{
                if(nowDayGet>=thenDayGet){
                    return false;
                }
                else{
                    return true;
                }
            }
        }
    }

    private static boolean isThisToday(String pulledSchedule) {
        Calendar now = Calendar.getInstance();
        Date pull = null;
        try {
            pull = sdf.parse(pulledSchedule);
            Calendar then = Calendar.getInstance();
            then.setTime(pull);
            int nowMonthGet = now.get(Calendar.MONTH);
            int thenMonthGet = then.get(Calendar.MONTH);
            int nowDayGet = now.get(Calendar.DAY_OF_MONTH);
            int thenDayGet = then.get(Calendar.DAY_OF_MONTH);
            int nowYearGet = now.get(Calendar.YEAR);
            int thenYearGet = then.get(Calendar.YEAR);
            return(nowDayGet==thenDayGet && nowMonthGet==thenMonthGet && nowYearGet==thenYearGet);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String[] pullTimes(Date nowTime) throws IOException, ParseException {
        CSVReader reader = new CSVReader(new FileReader(scheduleFileName));
        List<String[]> allRead = reader.readAll();
        reader.close();
        for(String[] randomSchedule: allRead){
            if(isThisToday(randomSchedule[0])){
                return randomSchedule;
            }
        }
        return null;
    }




    private static int randomMinute() {
        Random random = new Random();
        return random.nextInt(61);
    }

    private static int randomHour(int timeOfDay) {
        Random random = new Random();
        switch(timeOfDay) {
            case 1:
                return random.nextInt(4)+8;
            case 2:
                return random.nextInt(5)+12;
            case 3:
                return random.nextInt(5)+17;
            default: return random.nextInt(24);
        }
    }

    private static String[] generateRandomSchedule(int month, int day, int year) {
        Calendar dynamicCal = Calendar.getInstance();
        Date dynamicDate;
        String[] returnString = new String[3];
        for(int i = 0;i<3;i++) {
            dynamicCal.set(year,month,day,randomHour(i+1),randomMinute(),0);
            dynamicDate = dynamicCal.getTime();
            returnString[i] = sdf.format(dynamicDate);
        }
        return returnString;
    }

    private static void scheduleRandomEMA(Date nowDate) throws IOException {
       Calendar convertCal = Calendar.getInstance();
       convertCal.setTime(nowDate);
        int month = convertCal.get(Calendar.MONTH);
        int year = convertCal.get(Calendar.YEAR);
        int day = convertCal.get(Calendar.DAY_OF_MONTH);
       String[] pushEvent = generateRandomSchedule(month,day,year);
       CSVWriter writer = new CSVWriter(new FileWriter(scheduleFileName, true));
       writer.writeNext(pushEvent);
       writer.close();
    }

}
