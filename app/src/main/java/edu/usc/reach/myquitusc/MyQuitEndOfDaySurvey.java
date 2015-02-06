package edu.usc.reach.myquitusc;

import android.util.Log;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Eldin on 12/30/14.
 */
public class MyQuitEndOfDaySurvey {
    public static final int KEY_SURVEY_SUCCESS = 3;
    public static final int KEY_END_SURVEY = 17;
    public static final int KEY_SURVEY_LENGTH = 18;


    public static final String[] howManyCigs = {"How many cigarettes did you smoke today?", "NUMERICAL_ENTRY"};
    public static final String[] troubleCigsNone = {"Great job! Let's keep up the good work!" +
            System.getProperty("line.separator") +
            "Today, I had trouble getting cigarettes off my mind.",
            "0 = Not at all", "1", "2", "3", "4", "5", "6 = Extremely"};
    public static final String[] troubleCigsYes = {"Today, I had trouble getting cigarettes off my mind.",
            "0 = Not at all", "1", "2", "3", "4", "5", "6 = Extremely"};
    public static final String[] botherDesireNone = {"Today, I was bothered by my desire to smoke.",
            "0 = Not at all", "1", "2", "3", "4", "5", "6 = Extremely"};
    public static final String[] botherDesireYes = {"Today, I was bothered by my desire to smoke.",
            "0 = Not at all", "1", "2", "3", "4", "5", "6 = Extremely"};
    public static final String[] frequentUrgesNone = {"Today, I had frequent urges to smoke",
            "0 = Not at all", "1", "2", "3", "4", "5", "6 = Extremely"};
    public static final String[] frequentUrgesYes = {"Today, I had frequent urges to smoke",
            "0 = Not at all", "1", "2", "3", "4", "5", "6 = Extremely"};
    public static final String[] confidentQuitNone = {"Today, how confident do you feel that you can stay quit in the next 7 days?",
            "1 = Not confident", "2","3","4","5","6","7","8","9","10 = Very confident"};
    public static final String[] confidentQuitYes = {"Today, how confident do you feel that you can stay quit in the next 7 days?",
            "1 = Not confident", "2","3","4","5","6","7","8","9","10 = Very confident"};
    public static final String[] vapeCigNone = {"Did you vape/e-cig today?",
            "Yes","No"};
    public static final String[] vapeCigYes = {"Did you vape/e-cig today?",
            "Yes","No"};
    public static final String[] vapeCigCountNone = {"How many occasions did you vape? (i.e., picking " +
            "up your device to use and putting it down as one occasion)",
            "NUMERICAL_ENTRY"};
    public static final String[] vapeCigCountYes = {"How many occasions did you vape? (i.e., picking " +
            "up your device to use and putting it down as one occasion)",
            "NUMERICAL_ENTRY"};
    public static final String[] helpQuitNoneV = {"Which plans/intentions did you find the most beneficial to help you stay quit today?",
            "This one","That one","Neither"};
    public static final String[] helpQuitYesNV = {"Which plans/intentions did you find the most beneficial to help you stay quit today?",
            "This one","That one","Neither"};
    public static final String[] helpQuitYesV = {"Which plans/intentions did you find the most beneficial to help you stay quit today?",
            "This one","That one","Neither"};
    public static final String[] helpQuitNoneNV = {"Which plans/intentions did you find the most beneficial to help you stay quit today?",
            "This one","That one","Neither"};
    public static final String[] endMessage = {"Thank you for completing the survey"};


    public  List<String[]> getQuestions = Arrays.asList(howManyCigs, troubleCigsNone,troubleCigsYes,botherDesireNone,
            botherDesireYes, frequentUrgesNone, frequentUrgesYes, confidentQuitNone, confidentQuitYes,
            vapeCigNone, vapeCigYes, vapeCigCountNone, vapeCigCountYes,helpQuitNoneV, helpQuitYesV, helpQuitNoneNV,
            helpQuitYesNV, endMessage);

    public static int validateNextPosition(int qID, int aID) {
        Log.d("MQU-EMA","Received Qid" + qID + " and " + aID );
        switch (qID) {
            case 0:
                switch(aID) {
                    case 0: return 1;
                    default: return 2;
                }
            case 1: return 3;
            case 2: return 4;
            case 3: return 5;
            case 4: return 6;
            case 5: return 7;
            case 6: return 8;
            case 7: return 9;
            case 8: return 10;
            case 9:
                switch (aID) {
                    case 1001: return 11;
                    case 1002: return 15;
                    default: return 0;
                }
            case 10:
                switch (aID) {
                    case 1001: return 12;
                    case 1002: return 16;
                    default: return 0;
                }
            case 11: return 13;
            case 12: return 14;
            case 13: return KEY_END_SURVEY;
            case 14: return KEY_END_SURVEY;
            case 15: return KEY_END_SURVEY;
            case 16: return KEY_END_SURVEY;
            default: return 0;
        }
    }

    public static int validateNextPosition(int qID, String aID) {
        Log.d("MQU-EMA","Received Qid" + qID + " and " + aID );
        switch(aID) {
            default: return KEY_END_SURVEY;
        }
    }


    public static int validatePreviousPosition(int qID) {
        switch (qID) {
            case 3: return 1;
            case 4: return 2;
            case 5: return 3;
            case 6: return 4;
            case 7: return 5;
            case 8: return 6;
            case 9: return 7;
            case 10: return 8;
            case 11: return 9;
            case 12: return 10;
            case 13: return 11;
            case 14: return 12;
            case 15: return 9;
            case 16: return 10;
            default: return 0;
        }
    }




}
