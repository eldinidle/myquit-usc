package edu.usc.reach.myquituscnew.Surveys;

import android.util.Log;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import edu.usc.reach.myquituscnew.MyQuitCSVHelper;

/**
 * Created by Eldin on 12/30/14.
 */
public class MyQuitCalendarSuccessSurvey {
    public static final int KEY_SURVEY_SUCCESS = 2;
    public static final int KEY_END_SURVEY = 6;
    public static final int KEY_SURVEY_LENGTH = 7;


    public String[] areContext = {"In the last hour, were you in the following situation:" +
            System.getProperty("line.separator") +
            MyQuitCSVHelper.pullLastEvent(MyQuitCSVHelper.CALENDAR_EMA_KEY)[2], "Yes", "No"};
    public  String[] didFollow = {"Did you do the following: " +
            MyQuitCSVHelper.pullLastEvent(MyQuitCSVHelper.CALENDAR_EMA_KEY)[3].replace(" me "," you ") + "?", "Yes", "No"};
    public static final String[] howHelpful = {"How helpful was the plan or intention?", "Extremely helpful",
            "Quite helpful", "Somewhat helpful", "A little helpful", "Not at all helpful"};
    public static final String[] didSmokeY = {"Did you smoke a cigarette?", "Yes", "No"};
    public static final String[] didSmokeN = {"Did you smoke a cigarette?", "Yes", "No"};
    public static final String[] doInstead = {"What did you do instead", "TEXT_ENTRY"};
    public static final String[] endMessage = {"Thank you for completing the survey"};


    public  List<String[]> getQuestions = Arrays.asList(areContext, didFollow,howHelpful,didSmokeY,
            didSmokeN, doInstead,endMessage);


    public static int validateNextPosition(int qID, int aID) {
        Log.d("MQU-EMA","Received Qid" + qID + " and " + aID );
        switch (qID) {
            case 0:
                return 1;
            case 1:
                switch (aID) {
                    case 1001: return 3;
                    case 1002: return 4;
                    default: return 0;
                }
            case 2:
                MyQuitCSVHelper.pushCigAvoided();
                MyQuitCSVHelper.pushEMACompleted();
                return KEY_END_SURVEY;
            case 3:
                switch (aID) {
                    case 1001:
                        try {
                            MyQuitCSVHelper.pushCigarette(MyQuitCSVHelper.getFullDate(),MyQuitCSVHelper.getFulltime());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        MyQuitCSVHelper.pushEMACompleted();
                        return KEY_END_SURVEY;
                    case 1002: return 2;
                    default: return 0;
                }
            case 4:
                switch (aID) {
                    case 1001:
                        try {
                            MyQuitCSVHelper.pushCigarette(MyQuitCSVHelper.getFullDate(),MyQuitCSVHelper.getFulltime());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        MyQuitCSVHelper.pushEMACompleted();
                        return KEY_END_SURVEY;
                    case 1002: return 5;
                    default: return 0;
                }
            case 5:
                try {
                    MyQuitCSVHelper.pushCigarette(MyQuitCSVHelper.getFullDate(),MyQuitCSVHelper.getFulltime());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MyQuitCSVHelper.pushEMACompleted();
                return KEY_END_SURVEY;
            default: return 0;
        }
    }

    public static int validateNextPosition(int qID, String aID) {
        Log.d("MQU-EMA","Received Qid" + qID + " and " + aID );
        switch(aID) {
            default:
                MyQuitCSVHelper.pushCigAvoided();
                MyQuitCSVHelper.pushEMACompleted();
                return KEY_END_SURVEY;
        }
    }


    public static int validatePreviousPosition(int qID) {
        switch (qID) {
            case 0: return 0;
            case 1: return 0;
            case 2: return 3;
            case 3: return 1;
            case 4: return 1;
            case 5: return 4;
            default: return 0;
        }
    }




}
