package edu.usc.reach.myquitusc.Surveys;

import android.content.Context;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

import edu.usc.reach.myquitusc.MyQuitPlanHelper;

/**
 * Created by Eldin on 12/30/14.
 */
public class MyQuitPreQuitEndOfDaySurvey {
    public static final int KEY_SURVEY_SUCCESS = 6;
    public static final int KEY_END_SURVEY = 6;
    public static final int KEY_SURVEY_LENGTH = 7;

    public static final String[] howManyCigs = {"How many cigarettes did you smoke today?", "NUMERICAL_ENTRY"};



    public static final String[] confidentQuit = {"Today, I felt confident that I can quit smoking.",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};

    public static final String[] readyQuit = {"Do you feel ready to quit?",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};


    public static final String[] vapeCig = {"Did you vape or use e-cigs today?",
            "Yes","No"};
    public static final String[] vapeCigCount = {"How many times did you vape or use e-cigs today?",
            "NUMERICAL_ENTRY"};
    public static final String[] vapePuffCount = {"How many puffs did you take each time you vaped or used e-cigs?",
            "NUMERICAL_ENTRY"};





    public static final String[] endMessage = {"Thank you for completing the survey"};


    public  List<String[]> getQuestions(Context context){
        List<String[]> returnList = Arrays.asList(howManyCigs, confidentQuit, readyQuit,
                vapeCig, vapeCigCount, vapePuffCount,endMessage);  // 3/4
        return returnList;
    }

    public static int validateNextPosition(int qID, int aID) {
        Log.d("MQU-EMA","Received Qid" + qID + " and " + aID );
        if(qID<3){
            return qID + 1;
        }
        else {
            switch(qID){
                case 3:
                    switch(aID){
                        case 1001: return 4;
                        case 1002: return KEY_END_SURVEY;
                    } break;
                case 4: return 5;
                case 5: return KEY_END_SURVEY;
                default: return 0;
            }
        }
        return 0;
    }

    public static int validateNextPosition(int qID, String aID) {
        Log.d("MQU-EMA","Received Qid" + qID + " and " + aID );
        switch(aID) {
            default: return qID + 1;
        }
    }


    public static int validatePreviousPosition(int qID) {
        switch(qID){
            case 0: return 0;
            default: return qID-1;
        }
    }




}
