package edu.usc.reach.myquitusc.Surveys;

import android.util.Log;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Eldin on 12/30/14.
 */
public class MyQuitSmokeSurvey {
    public static final int KEY_SURVEY_SUCCESS = 5;
    public static final int KEY_END_SURVEY = 34;
    public static final int KEY_SURVEY_LENGTH = 35;


    public static final int KEY_NA = 3;
    public static final int KEY_PA = 4;
    public static final int KEY_PSS = 5;
    public static final int KEY_CC = 6;
    public static final int KEY_ANHEDONIA = 7;

    public static final String[] whereAt = {"Where were you when you decided to smoke this cigarette?","Home","Bathroom",
    "Dormitory","Coffee shop/cafe","Class","Bar/restaurant","Work","Outside","Car","Other location"};
    public static final String[] whereAtText = {"Where were you?","TEXT_ENTRY"};

    public static final String[] whatDoing = {"What were you doing when you decided to smoke this cigarette?","Socializing","Studying/reading/working",
    "TV","Phone","Exercise/walking","Eating","Sleeping","Commuting","Other"};
    public static final String[] whatDoingText = {"What was this other thing?","TEXT_ENTRY"};

    public static final String[] reasonSmoke = {"I am smoking because...","Reduce craving","Soon going where I cannot smoke",
        "Cope with negative emotion","Enhance positive emotion","Habit/automatic","Opportunity to socialize",
        "Break from work/studying","Boredom/to kill time","Other reason","MULTIPLE_CHOICE"};
    public static final String[] reasonSmokeText = {"Why are you smoking this cigarette?","TEXT_ENTRY"};

    public static final String[] whoWith = {"Who have you been with in the last 15 minutes?","No one, I was alone",
            "Korean friends","Non-Korean friends", "Family","Other persons","MULTIPLE_CHOICE"};

    public static final String[] hadThese = {"Have you had any of these in the past hour?","Alcohol","Water",
    "Coffee","Tea","Soda","Meal/snack","Other","Nothing","MULTIPLE_CHOICE"};
    public static final String[] othersSmoke = {"Were others smoking around you when you decided to smoke this cigarette?",
    "Yes","No"};
    public static final String[] allowSmoke = {"Is smoking allowed where you decided to smoke this cigarette?",
    "Yes", "No"};
    public static final String[] persistStressor = {"Has an ongoing stressor persisted up to the past 15 minutes?",
    "Yes", "No"};
    public static final String[] occurStressor = {"Did a stressful event occur in the past 15 minutes?", "Yes","No"};

    public static final String[] naScared = {"In the past 15 minutes, have you felt SCARED?",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};
    public static final String[] naUpset = {"In the past 15 minutes, have you felt UPSET?",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};
    public static final String[] naDistressed = {"In the past 15 minutes, have you felt DISTRESSED?",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};
    public static final String[] naTense = {"In the past 15 minutes, have you felt TENSE/ANXIOUS?",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};
    public static final String[] naSad = {"In the past 15 minutes, have you felt SAD/DEPRESSED?",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};
    public static final String[] naIrritable = {"In the past 15 minutes, have you felt IRRITABLE/EASILY ANGERED?",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};
    public static final String[] naHopeless = {"In the past 15 minutes, have you felt HOPELESS/DISCOURAGED?",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};

    public static final String[] paHappy = {"In the past 15 minutes, have you felt HAPPY?",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};
    public static final String[] paCheerful = {"In the past 15 minutes, have you felt CHEERFUL?",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};
    public static final String[] paEnthusiastic = {"In the past 15 minutes, have you felt ENTHUSIASTIC?",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};
    public static final String[] paProud = {"In the past 15 minutes, have you felt PROUD?",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};
    public static final String[] paInterested = {"In the past 15 minutes, have you felt INTERESTED?",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};

    public static final String[] pssSchool = {"In the past 15 minutes, have you felt stressful because of SCHOOL?",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};
    public static final String[] pssWork = {"In the past 15 minutes, have you felt stressful because of WORK?",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};
    public static final String[] pssFamily = {"In the past 15 minutes, have you felt stressful because of FRIENDS/FAMILY relationships?",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};
    public static final String[] pssMoney = {"In the past 15 minutes, have you felt stressful because of MONEY?",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};


    public static final String[] ccMindCig = {"I had trouble getting cigarettes off my mind in the past 15 minutes.",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};
    public static final String[] ccDesireCig = {"I was bothered by the desire to smoke in the past 15 minutes.",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};
    public static final String[] ccUrgeCig = {"I had frequent urges to smoke in the past 15 minutes.",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};


    public static final String[] anhedoniaPeople = {"How much pleasure/enjoyment would you feel right now in response to spending time with people you are close to, such as family and friends?",
            "1 = No pleasure", "2","3","4","5","6","7","8","9","10 = Extreme pleasure"};
    public static final String[] anhedoniaHobby = {"How much pleasure/enjoyment would you feel right now in response to personal hobbies, such as reading, painting, following sports, or collecting things?",
            "1 = No pleasure", "2","3","4","5","6","7","8","9","10 = Extreme pleasure"};
    public static final String[] anhedoniaSocial = {"How much pleasure/enjoyment would you feel right now in response to socializing with other people in-person or over the phone and Internet?",
            "1 = No pleasure", "2","3","4","5","6","7","8","9","10 = Extreme plesaure"};

    public static final String[] endMessage = {"Thank you for completing the survey"};


    public  List<String[]> getQuestions = Arrays.asList(whereAt,whereAtText,whatDoing,whatDoingText, reasonSmoke, reasonSmokeText, //6
            whoWith, hadThese,othersSmoke,allowSmoke,persistStressor,occurStressor,naScared,naUpset, // 8 14
                naDistressed,naTense,naSad,naIrritable,naHopeless,paHappy,paCheerful,paEnthusiastic, //8 22
                paProud,paInterested,pssSchool,pssWork,pssFamily,pssMoney,ccMindCig, // 7 29
                ccDesireCig,ccUrgeCig,anhedoniaPeople,anhedoniaHobby, // 4 33
                anhedoniaSocial,endMessage); // 2

    public static int validateNextPosition(int qID, int aID, boolean naTrue, boolean paTrue,
                                           boolean pssTrue, boolean ccTrue, boolean anhedoniaTrue) {
        Log.d("MQU-EMA","Received Qid" + qID + " and " + aID );
        // if(qID>3){
        //     return qID + 1;
        // }
        // else {
        switch(qID){
            case 0:
                switch(aID) {
                    case 1009:
                        return 1;
                    default:
                        return 2;
                }
            case 1: return 2;
            case 2:
                switch(aID) {
                    case 1008:
                        return 3;
                    default:
                        return 4;
                }
            case 4:
                switch(aID) {
                    case 1009:
                        return 5;
                    default:
                        return 6;
                }
            case 11:
                if(naTrue){return 12;}
                else if(paTrue){return 19;}
                else if(pssTrue){return 24;}
                else if(ccTrue){return 28;}
                else if(anhedoniaTrue){return 31;}
                else{return KEY_END_SURVEY;}
            case 18:
                if(paTrue){return 19;}
                else if(pssTrue){return 24;}
                else if(ccTrue){return 28;}
                else if(anhedoniaTrue){return 31;}
                else{return KEY_END_SURVEY;}
            case 23:
                if(pssTrue){return 24;}
                else if(ccTrue){return 28;}
                else if(anhedoniaTrue){return 31;}
                else{return KEY_END_SURVEY;}
            case 27:
                if(ccTrue){return 28;}
                else if(anhedoniaTrue){return 31;}
                else{return KEY_END_SURVEY;}
            case 30:
                if(anhedoniaTrue){return 31;}
                else{return KEY_END_SURVEY;}
            default: return qID + 1;
            //    }
            //    return 0;
        }
    }
/*

    public static int validateNextPosition(int qID, int aID) {
        Log.d("MQU-EMA","Received Qid" + qID + " and " + aID );
        if(qID>5){
            return qID + 1;
        }
        else {
            switch(qID){
                case 0:
                    switch(aID) {
                        case 1009:
                            return 1;
                        default:
                            return 2;
                    }
                case 1: return 2;
                case 2:
                    switch(aID) {
                        case 1008:
                            return 3;
                        default:
                            return 4;
                    }
                case 3: return 4;
                case 4:
                    switch(aID) {
                        case 1009:
                            return 5;
                        default:
                            return 6;
                    }
                case 5: return 6;
            }
            return 0;
        }
    }
    */

    public static int validateNextPosition(int qID, String aID) {
        Log.d("MQU-EMA","Received Qid" + qID + " and " + aID );
        switch (qID){
            case 4: {
                char[] splitArray = aID.toCharArray();
                String splitDraw = String.valueOf(splitArray[(splitArray.length-2)]);
                Log.d("MQU-EMA","Received char" + splitDraw );
                if(Integer.parseInt(splitDraw)>0){
                    return qID + 1;
                }
                else {
                    return qID + 2;
                }
            }
            default: return qID +1;
        }
    }


    public static int validatePreviousPosition(int qID, boolean naTrue, boolean paTrue,
                                               boolean pssTrue, boolean ccTrue, boolean anhedoniaTrue) {
        if(qID==0){return 0;}
        //if(qID>4){
        //    return qID-1;
        //}
        switch(qID){
            case 2: return 0;
            case 3: return 2;
            case 4: return 2;
            case 5: return 4;
            case 6: return 4;
            case 19:
                if(naTrue){return 18;}
                else{return 11;}
            case 24:
                if(paTrue){return 23;}
                else if(naTrue){return 18;}
                else{return 11;}
            case 28:
                if(pssTrue){return 27;}
                else if(paTrue){return 23;}
                else if(naTrue){return 18;}
                else{return 11;}
            case 31:
                if(ccTrue){return 30;}
                else if(pssTrue){return 27;}
                else if(paTrue){return 23;}
                else if(naTrue){return 18;}
                else{return 11;}
            default: return (qID - 1);
        }

    }

/*
    public static int validatePreviousPosition(int qID) {
        if(qID>6){
            return qID-1;
        }
        switch(qID){
            case 2: return 0;
            case 3: return 2;
            case 4: return 2;
            case 5: return 4;
            case 6: return 4;
            default: return 0;
        }
    }
    */




}
