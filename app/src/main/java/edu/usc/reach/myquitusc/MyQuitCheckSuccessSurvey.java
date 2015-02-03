package edu.usc.reach.myquitusc;

import android.util.Log;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Eldin on 12/30/14.
 */
public class MyQuitCheckSuccessSurvey {
    public static final int KEY_SURVEY_SUCCESS = 1;
    public static final int KEY_END_SURVEY = 5;
    public static final int KEY_SURVEY_LENGTH = 6;


    public String[] didFollow = {"When " + MyQuitCSVHelper.pullLastEvent(MyQuitCSVHelper.ROGUE_EMA_KEY)[2] +
            ", you said you would " + MyQuitCSVHelper.pullLastEvent(MyQuitCSVHelper.ROGUE_EMA_KEY)[3] + "." +
            System.getProperty("line.separator") +
            "Did you follow through with your plans?", "Yes", "No"};
    public final String[] howHelpful = {"How helpful was the intervention?", "Extremely helpful",
            "Quite helpful", "Somewhat helpful", "A little helpful", "Not at all helpful"};
    public final String[] didSmokeY = {"Did you smoke a cigarette?", "Yes", "No"};
    public final String[] didSmokeN = {"Did you smoke a cigarette?", "Yes", "No"};
    public final String[] doInstead = {"What did you do instead", "TEXT_ENTRY"};
    public final String[] endMessage = {"Thank you for completing the survey"};


    public List<String[]> getQuestions = Arrays.asList(didFollow,howHelpful,didSmokeY, didSmokeN, doInstead,endMessage);

    public static int validateNextPosition(int qID, int aID) {
        Log.d("MQU-EMA","Received Qid" + qID + " and " + aID );
        switch (qID) {
            case 0:
                switch (aID) {
                    case 1001: return 2;
                    case 1002: return 3;
                    default: return 0;
                }
            case 1:
                MyQuitCSVHelper.pushCigAvoided();
                return KEY_END_SURVEY;
            case 2:
                switch (aID) {
                    case 1001: return KEY_END_SURVEY;
                    case 1002: return 1;
                    default: return 0;
                }
            case 3:
                switch (aID) {
                    case 1001: return KEY_END_SURVEY;
                    case 1002: return 4;
                    default: return 0;
                }
            case 4:
                return KEY_END_SURVEY;
            default: return 0;
        }
    }

    public static int validateNextPosition(int qID, String aID) {
        Log.d("MQU-EMA","Received Qid" + qID + " and " + aID );
        switch(aID) {
            default:
                MyQuitCSVHelper.pushCigAvoided();
                return KEY_END_SURVEY;
        }
    }


    public static int validatePreviousPosition(int qID) {
        switch (qID) {
            case 0: return 0;
            case 1: return 2;
            case 2: return 0;
            case 3: return 0;
            case 4: return 3;
            default: return 0;
        }
    }



}
