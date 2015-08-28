package edu.usc.reach.myquitusc.Surveys;

import android.content.Context;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

import edu.usc.reach.myquitusc.MyQuitPlanHelper;

/**
 * Created by Eldin on 12/30/14.
 */
public class MyQuitRandomSurvey {
    public static final int KEY_SURVEY_SUCCESS = 4;
    public static final int KEY_END_SURVEY = 32;
    public static final int KEY_SURVEY_LENGTH = 33;

    public static final int KEY_NA = 3;
    public static final int KEY_PA = 4;
    public static final int KEY_PSS = 5;
    public static final int KEY_CC = 6;
    public static final int KEY_ANHEDONIA = 7;

    public static final String[] whereAt = {"Where were you when the phone alerted you?","Home","Bathroom",
    "Dormitory","Coffee shop/cafe","Class","Bar/restaurant","Work","Outside","Car","Other location"};
    public static final String[] whereAtText = {"Where were you?","TEXT_ENTRY"};

    public static final String[] whatDoing = {"What were you doing when the phone alerted you?","Socializing","Studying/reading/working",
    "TV/hobby/phone","Exercise/walking","Eating","Sleeping","Commuting","Other"};
    public static final String[] whatDoingText = {"What was this other thing?","TEXT_ENTRY"};

    public static final String[] whoWith = {"Who have you been with in the last 15 minutes?","No one, I was alone",
            "Korean friends","Non-Korean friends", "Family","Other persons","MULTIPLE_CHOICE"};

    public static final String[] hadThese = {"Have you had any of these in the past hour?","Alcohol","Water",
    "Coffee","Tea","Soda","Meal/snack","Other","Nothing","MULTIPLE_CHOICE"};
    public static final String[] othersSmoke = {"Were others smoking around you when the phone went off?",
    "Yes", "No"};
    public static final String[] allowSmoke = {"Is smoking allowed where you are at when the phone went off?",
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
    //public static final String[] ccMindVape = {"I had trouble getting e-cigs/vaping off my mind in the past 15 minutes.",
    //        "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};
    public static final String[] ccDesireCig = {"I was bothered by the desire to smoke in the past 15 minutes.",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};
    //public static final String[] ccDesireVape = {"I was bothered by the desire to vape in the past 15 minutes.",
    //        "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};
    public static final String[] ccUrgeCig = {"I had frequent urges to smoke in the past 15 minutes.",
            "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};
    //public static final String[] ccUrgeVape = {"I had frequent urges to vape in the past 15 minutes.",
    //        "1 = Not at all", "2","3","4","5","6","7","8","9","10 = Extremely"};

//TODO: Implement 60% dump

    public static final String[] anhedoniaPeople = {"How much pleasure/enjoyment would you feel right now in response to spending time with people you are close to, such as family and friends?",
            "1 = No pleasure", "2","3","4","5","6","7","8","9","10 = Extreme pleasure"};
    public static final String[] anhedoniaHobby = {"How much pleasure/enjoyment would you feel right now in response to personal hobbies, such as reading, painting, following sports, or collecting things?",
            "1 = No pleasure", "2","3","4","5","6","7","8","9","10 = Extreme pleasure"};
    public static final String[] anhedoniaSocial = {"How much pleasure/enjoyment would you feel right now in response to socializing with other people in-person or over the phone and Internet?",
            "1 = No pleasure", "2","3","4","5","6","7","8","9","10 = Extreme pleasure"};

    public static final String[] endMessage = {"Thank you for completing the survey"};


    public  List<String[]> getQuestions = Arrays.asList(whereAt,whereAtText,whatDoing,whatDoingText,whoWith, // 5
                hadThese,othersSmoke,allowSmoke,persistStressor,occurStressor,naScared,naUpset, // 7 12
                naDistressed,naTense,naSad,naIrritable,naHopeless,paHappy,paCheerful,paEnthusiastic, //8 20
                paProud,paInterested,pssSchool,pssWork,pssFamily,pssMoney,ccMindCig, // 7 27
                ccDesireCig,ccUrgeCig,anhedoniaPeople,anhedoniaHobby, // 4 31
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
                case 9:
                    if(naTrue){return 10;}
                    else if(paTrue){return 17;}
                    else if(pssTrue){return 22;}
                    else if(ccTrue){return 26;}
                    else if(anhedoniaTrue){return 29;}
                    else{return KEY_END_SURVEY;}
                case 16:
                    if(paTrue){return 17;}
                    else if(pssTrue){return 22;}
                    else if(ccTrue){return 26;}
                    else if(anhedoniaTrue){return 29;}
                    else{return KEY_END_SURVEY;}
                case 21:
                    if(pssTrue){return 22;}
                    else if(ccTrue){return 26;}
                    else if(anhedoniaTrue){return 29;}
                    else{return KEY_END_SURVEY;}
                case 25:
                    if(ccTrue){return 26;}
                    else if(anhedoniaTrue){return 29;}
                    else{return KEY_END_SURVEY;}
                case 28:
                    if(anhedoniaTrue){return 29;}
                    else{return KEY_END_SURVEY;}
                default: return qID + 1;
        //    }
        //    return 0;
        }
    }

    public static int validateNextPosition(int qID, String aID, boolean naTrue, boolean paTrue,
                                           boolean pssTrue, boolean ccTrue, boolean anhedoniaTrue) {
        Log.d("MQU-EMA","Received Qid" + qID + " and " + aID );
        return qID +1;
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
            case 17:
                if(naTrue){return 16;}
                else{return 9;}
            case 22:
                if(paTrue){return 21;}
                else if(naTrue){return 16;}
                else{return 9;}
            case 26:
                if(pssTrue){return 25;}
                else if(paTrue){return 21;}
                else if(naTrue){return 16;}
                else{return 9;}
            case 29:
                if(ccTrue){return 28;}
                else if(pssTrue){return 25;}
                else if(paTrue){return 21;}
                else if(naTrue){return 16;}
                else{return 9;}
            default: return (qID - 1);
        }

    }




}
