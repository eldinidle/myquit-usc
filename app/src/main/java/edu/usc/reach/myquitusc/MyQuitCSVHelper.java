package edu.usc.reach.myquitusc;
import android.os.Environment;

import com.opencsv.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Eldin Dzubur on 12/26/14.
 */
public class MyQuitCSVHelper{

    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    /*
    final String filePath;
        final String calPath;
        final String emaPath;
        final String logPath;

    public MyQuitCSVHelper(Context c) {
        String path = c.getExternalFilesDir(Environment.getExternalStorageDirectory().getAbsolutePath()).getAbsolutePath()
        filePath = path + "/MyQuitUSC/";
        calPath = path + "/MyQuitUSC/Calendars/";
        emaPath = path + "/MyQuitUSC/EMA/";
        logPath = path + "/MyQuitUSC/Logs/";
    }
    */

    private static final String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyQuitUSC/";
    public static final String calPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyQuitUSC/Calendars/";
    public static final String emaPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyQuitUSC/EMA/";
    public static final String logPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyQuitUSC/Logs/";


    public static final int ROGUE_EMA_KEY = 1;
    public static final int CALENDAR_EMA_KEY = 2;
    public static final int END_OF_DAY_EMA_KEY = 3;
    public static final int RANDOM_EMA_KEY = 4;
    public static final int SMOKE_EMA_KEY = 5;
    public static final int PQ_END_OF_DAY_EMA_KEY = 6;
    public static final int OFF_EMA_KEY = 7;



    final public static String[] defaultTimes =
            new String[] { "12:00 AM", "01:00 AM", "02:00 AM", "03:00 AM", "04:00 AM", "05:00 AM",
                    "06:00 AM", "07:00 AM", "08:00 AM", "09:00 AM", "10:00 AM", "11:00 AM",
                    "12:00 PM", "01:00 PM", "02:00 PM", "03:00 PM", "04:00 PM", "05:00 PM",
                    "06:00 PM", "07:00 PM", "08:00 PM", "09:00 PM", "10:00 PM", "11:00 PM"};



    public static int convertRepromptChar(int emaType) {
        String testID = MyQuitCSVHelper.pullLastEvent(emaType)[0];
        //int lengthFull = testID.length();
        //int lengthMin = lengthFull - 1;
        int bringBack = 0;
                if (testID.substring(0, 11).equalsIgnoreCase("emaReprompt")) {
                    bringBack = Integer.parseInt(testID.substring(11));
                    return bringBack;
                }

        else {
              return bringBack;
              }
    }

    public static boolean isLastEventPastXMinutes(int emaType, int minutes){
        try {
        String stringTime = MyQuitCSVHelper.pullLastEvent(emaType)[1];
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Calendar now = Calendar.getInstance();
        Date timeNow = now.getTime();

            Date timeThen = sdf.parse(stringTime);
            long compareTime = timeNow.getTime() - timeThen.getTime();
            return (compareTime > (minutes * 60 * 1000));
        } catch (ArrayIndexOutOfBoundsException aioe) {
            aioe.printStackTrace();
            return false;
        }
        catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isLastEventPastXMinutesTrue(int emaType, int minutes){
        try {
            String stringTime = MyQuitCSVHelper.pullLastEvent(emaType)[1];
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Calendar now = Calendar.getInstance();
            Date timeNow = now.getTime();
            try {
                Date timeThen = sdf.parse(stringTime);
                long compareTime = timeNow.getTime() - timeThen.getTime();
                return (compareTime > (minutes * 60 * 1000));
            } catch (ParseException e) {
                e.printStackTrace();
                return true;
            }
        }
        catch(NullPointerException npe){
            return true;
        }

    }

    public static int compareEvents(String event1, String event2, int emaType) {
        String e1Time = pullLastEvent(event1,emaType);
        String e2Time = pullLastEvent(event2,emaType);
        if (e1Time == null & e2Time == null) {
            return 0;
        }
        else if (e1Time == null & e2Time != null) {
            return 2;
        }
        else if (e1Time != null & e2Time == null) {
            return 1;
        }
        else if (e1Time != null & e2Time != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Date e1Date;
            Date e2Date;
            try {
                e1Date = sdf.parse(e1Time);
                e2Date = sdf.parse(e2Time);
                if (e2Date.after(e1Date)) {
                    return 2;
                }
                else if (e2Date.before(e1Date)) {
                    return 1;
                }
                else {
                    return 3;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public static String getTimeOnly() {
        Calendar emptyCal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(emptyCal.getTime());
    }

    public static String getFullDate() {
        Calendar emptyCal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.format(emptyCal.getTime());
    }


    public static String getFulltime() {
        Calendar emptyCal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return sdf.format(emptyCal.getTime());
    }

    public static int pullEMACounts(String calledDate) throws IOException {
        String stepDate = calledDate.replaceAll("/", "_");
        String fileName = stepDate + "EMA_Count" + ".csv";
        CSVReader reader = new CSVReader(new FileReader(logPath + fileName));
        List<String[]> pullCigs = reader.readAll();
        reader.close();
        return pullCigs.size();
    }

    public static int pullCigAvoided(String calledDate) throws IOException {
        String stepDate = calledDate.replaceAll("/", "_");
        String fileName = stepDate + "R" + ".csv";
        CSVReader reader = new CSVReader(new FileReader(logPath + fileName));
        List<String[]> pullCigs = reader.readAll();
        reader.close();
        return pullCigs.size();
    }

    public static void pushEMACompleted() {
        String calledDate = MyQuitCSVHelper.getFullDate();
        String fullTime = MyQuitCSVHelper.getFulltime();
        String stepDate = calledDate.replaceAll("/", "_");
        String fileName = stepDate + "EMA_Count" + ".csv";
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(logPath + fileName, true));
            String[] pushTime = new String[] {fullTime};
            writer.writeNext(pushTime);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void pushCigAvoided() {
        String calledDate = MyQuitCSVHelper.getFullDate();
        String fullTime = MyQuitCSVHelper.getFulltime();
        String stepDate = calledDate.replaceAll("/", "_");
        String fileName = stepDate + "R" + ".csv";
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(logPath + fileName, true));
            String[] pushTime = new String[] {fullTime};
            writer.writeNext(pushTime);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Date pullCigaretteTime(String calledDate) throws IOException, ParseException {
        String stepDate = calledDate.replaceAll("/", "_");
        String fileName = stepDate + ".csv";
        CSVReader reader = new CSVReader(new FileReader(logPath + fileName));
        List<String[]> pullCigs = reader.readAll();
        reader.close();
        String[] cigDateTime = null;
        for(String[] holdRow: pullCigs){
            cigDateTime = holdRow;
        }
        String time = cigDateTime[0]; // + " " + cigDateTime[1];
        return sdf.parse(time);
    }

    public static int pullCigarette(String calledDate) throws IOException {
        String stepDate = calledDate.replaceAll("/", "_");
        String fileName = stepDate + ".csv";
        CSVReader reader = new CSVReader(new FileReader(logPath + fileName));
        List<String[]> pullCigs = reader.readAll();
        reader.close();
        return pullCigs.size();
    }

    public static void pushCigarette(String calledDate, String fullTime) throws IOException {
        String stepDate = calledDate.replaceAll("/", "_");
        String fileName = stepDate + ".csv";
        CSVWriter writer = new CSVWriter(new FileWriter(logPath + fileName, true));
        String[] pushTime = new String[] {fullTime};
        writer.writeNext(pushTime);
        writer.close();
    }

    public static String[] pullLastEvent(int emaType) {
        String fileName;
        switch(emaType){
            case CALENDAR_EMA_KEY: fileName = "CalEMAEvents.csv"; break;
            case END_OF_DAY_EMA_KEY: fileName = "EODEMAEvents.csv"; break;
            case ROGUE_EMA_KEY: fileName = "SystemEvents.csv"; break;
            case RANDOM_EMA_KEY: fileName = "RandomEMAEvents.csv"; break;
            case SMOKE_EMA_KEY: fileName = "SmokeEMAEvents.csv"; break;
            case OFF_EMA_KEY: fileName = "OffEMAEvents.csv"; break;
            case PQ_END_OF_DAY_EMA_KEY: fileName = "PreQuitEODEMAEvents.csv"; break;
            default: fileName = "SystemEvents.csv"; break;
        }
        try {
            CSVReader reader = new CSVReader(new FileReader(logPath + fileName));
            List<String[]> report = reader.readAll();
            reader.close();
            String[] report2 = report.get(report.size()-1);
            return report2;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String[] pullLastEventArray(String logMessage, int emaType) {
        String fileName;
        switch(emaType){
            case CALENDAR_EMA_KEY: fileName = "CalEMAEvents.csv"; break;
            case END_OF_DAY_EMA_KEY: fileName = "EODEMAEvents.csv"; break;
            case ROGUE_EMA_KEY: fileName = "SystemEvents.csv"; break;
            case RANDOM_EMA_KEY: fileName = "RandomEMAEvents.csv"; break;
            case SMOKE_EMA_KEY: fileName = "SmokeEMAEvents.csv"; break;
            case OFF_EMA_KEY: fileName = "OffEMAEvents.csv"; break;
            case PQ_END_OF_DAY_EMA_KEY: fileName = "PreQuitEODEMAEvents.csv"; break;
            default: fileName = "SystemEvents.csv"; break;
        }
        try {
            CSVReader reader = new CSVReader(new FileReader(logPath + fileName));
            String[] report;
            String[] returnReport = null;
            String pulledTime = null;
            String pulledMessage;
            while((report = reader.readNext()) != null) {
                pulledMessage = report[0];
                if (pulledMessage.equalsIgnoreCase(logMessage)) {
                    returnReport = report;
                }
            }
            return returnReport;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String pullLastEvent(String logMessage, int emaType) {
        String fileName;
        switch(emaType){
            case CALENDAR_EMA_KEY: fileName = "CalEMAEvents.csv"; break;
            case END_OF_DAY_EMA_KEY: fileName = "EODEMAEvents.csv"; break;
            case ROGUE_EMA_KEY: fileName = "SystemEvents.csv"; break;
            case RANDOM_EMA_KEY: fileName = "RandomEMAEvents.csv"; break;
            case SMOKE_EMA_KEY: fileName = "SmokeEMAEvents.csv"; break;
            case OFF_EMA_KEY: fileName = "OffEMAEvents.csv"; break;
            case PQ_END_OF_DAY_EMA_KEY: fileName = "PreQuitEODEMAEvents.csv"; break;
            default: fileName = "SystemEvents.csv"; break;
        }
        try {
            CSVReader reader = new CSVReader(new FileReader(logPath + fileName));
            String[] report;
            String pulledTime = null;
            String pulledMessage;
            while((report = reader.readNext()) != null) {
                pulledMessage = report[0];
                if (pulledMessage.equalsIgnoreCase(logMessage)) {
                    pulledTime = report[1];
                }
                else {
                    pulledTime = null;
                }
            }
            return pulledTime;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String pullLoginStatus(String logMessage) {
        try {
            CSVReader reader = new CSVReader(new FileReader(logPath + "LoginEvents.csv"));
            List<String[]> runThrough = reader.readAll();
            reader.close();
            String pulledLog = null;
            String pulledMessage;
            for(String[] pulledReport: runThrough) {
                pulledMessage = pulledReport[0];
                if (pulledMessage.equalsIgnoreCase(logMessage)) {
                    pulledLog = pulledReport[1];
                }
            }
            return pulledLog;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static Date pullCraveTime () throws IOException, ParseException {
        String[] pullEvent = null;

        CSVReader reader = new CSVReader(new FileReader(logPath + "CraveEvents.csv"));
        List<String[]> allRead = reader.readAll();
        for(String[] tempStringArray: allRead){
            pullEvent = tempStringArray;
        }

        return sdf.parse(pullEvent[1]);
    }

    public static void logCraveEvent(String activity, String fullTime) {
        String[] pushEvent = new String [] {activity, fullTime};
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(logPath + "CraveEvents.csv", true));
            writer.writeNext(pushEvent);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logRogueEvent(String fullTime) {
        String[] pushEvent = new String [] {"rogueEvent", fullTime};
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(logPath + "RogueEvents.csv", true));
            writer.writeNext(pushEvent);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logEMAEvents(int emaType, String logMessage, String fullTime) {
        String fileName;
        switch(emaType){
            case CALENDAR_EMA_KEY: fileName = "CalEMAEvents.csv"; break;
            case END_OF_DAY_EMA_KEY: fileName = "EODEMAEvents.csv"; break;
            case ROGUE_EMA_KEY: fileName = "SystemEvents.csv"; break;
            case RANDOM_EMA_KEY: fileName = "RandomEMAEvents.csv"; break;
            case SMOKE_EMA_KEY: fileName = "SmokeEMAEvents.csv"; break;
            case OFF_EMA_KEY: fileName = "OffEMAEvents.csv"; break;
            case PQ_END_OF_DAY_EMA_KEY: fileName = "PreQuitEODEMAEvents.csv"; break;
            default: fileName = "SystemEvents.csv"; break;
        }
        String[] pushEvent = new String [] {logMessage, fullTime};
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(logPath + fileName, true));
            writer.writeNext(pushEvent);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logEMAEvents(int emaType, String logMessage, String fullTime,
                                    String intent, String situation) {
        String fileName;
        switch(emaType){
            case CALENDAR_EMA_KEY: fileName = "CalEMAEvents.csv"; break;
            case END_OF_DAY_EMA_KEY: fileName = "EODEMAEvents.csv"; break;
            case ROGUE_EMA_KEY: fileName = "SystemEvents.csv"; break;
            case RANDOM_EMA_KEY: fileName = "RandomEMAEvents.csv"; break;
            case SMOKE_EMA_KEY: fileName = "SmokeEMAEvents.csv"; break;
            case OFF_EMA_KEY: fileName = "OffEMAEvents.csv"; break;
            case PQ_END_OF_DAY_EMA_KEY: fileName = "PreQuitEODEMAEvents.csv"; break;
            default: fileName = "SystemEvents.csv"; break;
        }
        String[] pushEvent = new String [] {logMessage, fullTime, intent, situation};
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(logPath + fileName, true));
            writer.writeNext(pushEvent);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logLoginEvents(String logMessage, String fullTime) {
        String[] pushEvent = new String [] {logMessage, fullTime};
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(logPath + "LoginEvents.csv", true));
            writer.writeNext(pushEvent);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logLoginEvents(String logMessage, String logMessageSupp, String fullTime) {
        String[] pushEvent = new String [] {logMessage, logMessageSupp, fullTime};
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(logPath + "LoginEvents.csv", true));
            writer.writeNext(pushEvent);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteDirectory(File path) {
        if(path.exists() ) {
            File[] files = path.listFiles();
            if (files == null) {
                return true;
            }
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                }
                else {
                    files[i].delete();
                }
            }
        }
        return(path.delete());
    }

    /*
    @implementation: DELETES ALL DATA and recreates structure
    @parameter:     boolean to confirm data deletion
                    integer password to confirm data deletion
    @return: none
    */
    public static void deleteAndRefresh(Boolean confirmYes,int passwordInClass) {
        /*
        The password is 495030.
         */
        if(confirmYes & (passwordInClass == 495030)){
            File newDirectory4 = new File(filePath);
            File newDirectory3 = new File(calPath);
            File newDirectory2 = new File(emaPath);
            File newDirectory1 = new File(logPath);
            deleteDirectory(newDirectory1);
            deleteDirectory(newDirectory2);
            deleteDirectory(newDirectory3);
            deleteDirectory(newDirectory4);
            createStructure();
        }
    }

    /*
    @implementation: creates a complete structure necessary for the application to function
    @parameter: none
    @return: true if structure is successfully created
    */
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


    /*
    @implementation: saves array of new times for a given date to a CSV into calendar path
    @parameter:     SimpleDateFormat String in MM/dd/YYYY
                    String array of new times and activities assigned to times
    @return: none
    */
    public static void pushDateTimes(String calledDate, String[] newTimes) throws IOException {
        String stepDate = calledDate.replaceAll("/", "_");
        String fileName = stepDate + ".csv";
        CSVWriter writer = new CSVWriter(new FileWriter(calPath + fileName));
        String[] pushTimes = newTimes;
        writer.writeNext(pushTimes);
        writer.close();
    }

    /*
    @parameter: SimpleDateFormat String in MM/dd/YYYY
    @return: String array of calendar entries of called date
    */
    public static String[] pullDateTimes(String calledDate) throws IOException {
        String stepDate = calledDate.replaceAll("/", "_");
        String fileName = stepDate + ".csv";
        CSVReader reader = new CSVReader(new FileReader(calPath + fileName));
        String[] pullTimes = reader.readNext();
        reader.close();
        return pullTimes;
    }

    /*
    @implementation:
     */

    public static String[] retrieveCigsToServer() {
        return null;
    }


}
