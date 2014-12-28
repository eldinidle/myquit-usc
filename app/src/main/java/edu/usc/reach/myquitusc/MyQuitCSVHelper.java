package edu.usc.reach.myquitusc;
import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.opencsv.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Eldin on 12/26/14.
 */
public class MyQuitCSVHelper {

    private static final String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyQuitUSC/";
    private static final String calPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyQuitUSC/Calendars";
    private static final String emaPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyQuitUSC/EMA";
    private static final String logPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyQuitUSC/Logs";

    final public static String[] defaultTimes =
            new String[] { "12:00 AM", "01:00 AM", "02:00 AM", "03:00 AM", "04:00 AM", "05:00 AM",
                    "06:00 AM", "07:00 AM", "08:00 AM", "09:00 AM", "10:00 AM", "11:00 AM",
                    "12:00 PM", "01:00 PM", "02:00 PM", "03:00 PM", "04:00 PM", "05:00 PM",
                    "06:00 PM", "07:00 PM", "08:00 PM", "09:00 PM", "10:00 PM", "11:00 PM"};

    public static String getFulltime() {
        Calendar emptyCal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String fullTime = sdf.format(emptyCal.getTime());
        return fullTime;
    }

    public static void logCigarette(String fullTime) {
        String[] pushEvent = new String [] {fullTime};
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(logPath + "Cigarettes.csv"));
            writer.writeNext(pushEvent);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logEvent(String logMessage, String fullTime) {
        String[] pushEvent = new String [] {logMessage, fullTime};
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(logPath + "SystemEvents.csv"));
            writer.writeNext(pushEvent);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean createStructure () {
        File newDirectory = new File(filePath);
        File newCalendar = new File(calPath);
        File newEMA = new File(emaPath);
        File newLogs = new File(logPath);

        boolean doubleCheckDir = true;
        boolean doubleCheckCal = true;
        boolean doubleCheckEMA = true;
        boolean doubleCheckLogs = true;

        if(!newDirectory.exists()){
            doubleCheckDir = newDirectory.mkdirs();
        }
        if(!newCalendar.exists()){
            doubleCheckCal = newCalendar.mkdirs();
        }
        if(!newEMA.exists()){
            doubleCheckEMA = newEMA.mkdirs();
        }
        if(!newLogs.exists()){
            doubleCheckLogs = newLogs.mkdirs();
        }
        if (!doubleCheckCal | !doubleCheckDir | !doubleCheckEMA | !doubleCheckLogs){
            return false;
        }
        else{
            return true;
        }
    }


    public static void pushDateTimes(String calledDate, String[] newTimes) throws IOException {
        String stepDate = calledDate.replaceAll("/", "_");
        String fileName = stepDate + ".csv";
        CSVWriter writer = new CSVWriter(new FileWriter(calPath + fileName));
        String[] pushTimes = newTimes;
        writer.writeNext(pushTimes);
        writer.close();
    }

    public static String[] pullDateTimes(String calledDate) throws IOException {
        String stepDate = calledDate.replaceAll("/", "_");
        String fileName = stepDate + ".csv";
        CSVReader reader = new CSVReader(new FileReader(calPath + fileName));
        String[] pullTimes = reader.readNext();
        reader.close();
        return pullTimes;
    }
// TODO:EMA section for push
    public static void pushEMATimes(String calledDate, String calledTime) throws IOException {
        String stepDate = calledDate.replaceAll("/", "_");
        String fileName = stepDate + ".csv";
        CSVWriter writer = new CSVWriter(new FileWriter(emaPath + fileName));
        String[] pushTimes = new String[] { calledDate, calledTime };
        writer.writeNext(pushTimes);
        writer.close();
    }

// TODO:EMA section for pull
    public static String[] pullEMATimes(String calledDate, String calledTime) throws IOException {
        String stepDate = calledDate.replaceAll("/", "_");
        String fileName = stepDate + ".csv";
        CSVReader reader = new CSVReader(new FileReader(emaPath + fileName));
        String[] pullTimes = reader.readNext();
        reader.close();
        return pullTimes;
    }




}
