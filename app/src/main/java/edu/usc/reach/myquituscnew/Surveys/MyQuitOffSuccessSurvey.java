package edu.usc.reach.myquituscnew.Surveys;

import android.util.Log;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import edu.usc.reach.myquituscnew.MyQuitCSVHelper;

/**
 * Created by Eldin on 12/30/14.
 */
public class MyQuitOffSuccessSurvey {
    public static final int KEY_SURVEY_SUCCESS = 7;
    public static final int KEY_END_SURVEY = 2;
    public static final int KEY_SURVEY_LENGTH = 3;

    public final String[] didSmoke = {"Did you smoke a cigarette?", "Yes", "No"};
    public final String[] doInstead = {"What did you do", "TEXT_ENTRY"};
    public final String[] endMessage = {"Thank you for completing the survey"};


    public List<String[]> getQuestions = Arrays.asList(didSmoke,doInstead,endMessage);

    public static int validateNextPosition(int qID, int aID) {
        Log.d("MQU-EMA","Received Qid" + qID + " and " + aID );
        switch (qID) {
            case 0:
                switch (aID) {
                    case 1001:
                        try {
                            MyQuitCSVHelper.pushCigarette(MyQuitCSVHelper.getFullDate(),MyQuitCSVHelper.getFulltime());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return KEY_END_SURVEY;
                    case 1002:
                        return 1;
                    default:
                        return 0;
                }
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
        return 0;
    }



}
