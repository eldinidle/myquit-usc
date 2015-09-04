package edu.usc.reach.myquitusc.Surveys;

import android.content.Context;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

import edu.usc.reach.myquitusc.MyQuitPlanHelper;

/**
 * Created by Eldin on 12/30/14.
 */
public class MyQuitEndOfDaySurvey {
    public static final int KEY_SURVEY_SUCCESS = 3;
    public static final int KEY_END_SURVEY = 28;
    public static final int KEY_SURVEY_LENGTH = 29;

    public static final String[] howManyCigs = {"How many cigarettes did you smoke today?", "NUMERICAL_ENTRY"};

    public static final String[] pssSchool = {"Today, I have felt stressful because of SCHOOL:",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};
    public static final String[] pssWork = {"Today, I have felt stressful because of WORK:",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};
    public static final String[] pssFamily = {"Today, I have felt stressful because of FRIENDS/FAMILY relationships:",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};
    public static final String[] pssMoney = {"Today, I have felt stressful because of MONEY:",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};

    public static final String[] naScared = {"Today, I have felt SCARED:",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};
    public static final String[] naUpset = {"Today, I have felt UPSET:",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};
    public static final String[] naDistressed = {"Today, I have felt DISTRESSED:",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};
    public static final String[] naTense = {"Today, I have felt TENSE/ANXIOUS:",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};
    public static final String[] naSad = {"Today, I have felt SAD/DEPRESSED:",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};
    public static final String[] naIrritable = {"Today, I have felt IRRITABLE/EASILY ANGERED:",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};
    public static final String[] naHopeless = {"Today, I have felt HOPELESS/DISCOURAGED:",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};

    public static final String[] paHappy = {"Today, I have felt HAPPY:",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};
    public static final String[] paCheerful = {"Today, I have felt CHEERFUL:",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};
    public static final String[] paEnthusiastic = {"Today, I have felt ENTHUSIASTIC:",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};
    public static final String[] paProud = {"Today, I have felt PROUD:",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};
    public static final String[] paInterested = {"Today, I have felt INTERESTED:",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};

    public static final String[] troubleCigs = {"I had trouble getting cigarettes off my mind today",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};
    public static final String[] botherDesire = {"I was bothered by the desire to smoke today",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};
    public static final String[] frequentUrges = {"I had frequent urges to smoke today",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};

    public static final String[] confidentQuit = {"Today, I felt confident that I can quit smoking.",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};

    public static  String[] helpQuit(Context context) {
        String[] hold = MyQuitPlanHelper.pullIntentsList(context, false);
        String[] newArray = new String[hold.length+2];
        newArray[0] = "Which plan or intention did you find the most beneficial to help you stop smoking today?";
        int count = 1;
        for(String holdString: hold){
            newArray[count] = holdString;
            count++;
        }
        newArray[newArray.length-1] = "None of these";
        return newArray;
    }

    public static final String[] vapeCig = {"Did you vape or use e-cigs today?",
            "Yes","No"};
    public static final String[] vapeCigCount = {"How many times did you vape or use e-cigs today?",
            "NUMERICAL_ENTRY"};
    public static final String[] vapePuffCount = {"How many puffs did you take each time you vaped or used e-cigs?",
            "NUMERICAL_ENTRY"};

    public static final String[] anhedoniaPeople = {"How much pleasure/enjoyment would you feel right now in response to spending time with people you are close to, such as family and friends?",
            "1 = No pleasure", "2","3","4","5","6","7","8","9","10 = Extreme pleasure"};
    public static final String[] anhedoniaHobby = {"How much pleasure/enjoyment would you feel right now in response to personal hobbies, such as reading, painting, following sports, or collecting things?",
            "1 = No pleasure", "2","3","4","5","6","7","8","9","10 = Extreme pleasure"};
    public static final String[] anhedoniaSocial = {"How much pleasure/enjoyment would you feel right now in response to socializing with other people in-person or over the phone and Internet?",
            "1 = No pleasure", "2","3","4","5","6","7","8","9","10 = Extreme pleasure"};


    public static final String[] endMessage = {"Thank you for completing the survey"};


    public  List<String[]> getQuestions(Context context){
        List<String[]> returnList = Arrays.asList(howManyCigs, pssFamily,pssMoney,pssSchool,pssWork, // 4/5
              naDistressed, naHopeless, naIrritable, naSad, naScared, naTense, naUpset, //7 11/12
              paCheerful, paEnthusiastic, paHappy, paInterested, paProud, // 5 16/17
              troubleCigs, botherDesire, frequentUrges, confidentQuit, helpQuit(context), //5 21/22
              vapeCig, vapeCigCount, vapePuffCount, anhedoniaPeople, anhedoniaHobby, anhedoniaSocial, endMessage); // 4 28/29
        return returnList;
    }

    public static int validateNextPosition(int qID, int aID) {
        Log.d("MQU-EMA","Received Qid" + qID + " and " + aID );
        if(qID<22){
            return qID + 1;
        }
        else {
            switch(qID){
                case 22:
                    switch(aID){
                        case 1001: return 23;
                        case 1002: return 25;
                    } break;
                case 23: return 24;
                case 24: return 25;
                case 25: return 26;
                case 26: return 27;
                case 27: return KEY_END_SURVEY;
                default: return 0;
            }
        }
        return 0;
    }

    public static int validateNextPosition(int qID, String aID) {
        Log.d("MQU-EMA","Received Qid" + qID + " and " + aID );
        switch(aID) {
            default: return KEY_END_SURVEY;
        }
    }


    public static int validatePreviousPosition(int qID) {
        switch(qID){
            case 0: return 0;
            case 25: return 22;
            default: return qID-1;
        }
    }




}
