package edu.usc.reach.myquituscnew.Surveys;

import android.util.Log;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Eldin on 12/30/14.
 */
public class MyQuitSmokeSurveyBackup {
    public static final int KEY_SURVEY_SUCCESS = 5;
    public static final int KEY_END_SURVEY = 34;
    public static final int KEY_SURVEY_LENGTH = 35;

    public static final String[] whereAt = {"Where were you when you decided to smoke this cigarette?","Home","Bathroom",
    "Dormitory","Class","Bar/restaurant","Work","Outside","Car","Other location"};
    public static final String[] whereAtText = {"Where were you?","TEXT_ENTRY"};

    public static final String[] whatDoing = {"What were you doing when you decided to smoke this cigarette?","Socializing","Studying/reading/working",
    "TV/hobby/phone","Exercise/walking","Eating","Sleeping","Commuting","Other"};
    public static final String[] whatDoingText = {"What was this other thing?","TEXT_ENTRY"};

    public static final String[] reasonSmoke = {"I am smoking because...","Reduce craving","Soon going where I cannot smoke",
        "Cope with negative emotion","Enhance positive emotion","Habit/automatic","Opportunity to socialize",
        "Break from work/studying","Boredom/to kill time","Other reason"};
    public static final String[] reasonSmokeText = {"Why are you smoking this cigarette?","TEXT_ENTRY"};

    public static final String[] whoWith = {"Who have you been with in the last 15 minutes?","No one, I was alone",
            "Friends","Family","Other persons"};

    public static final String[] hadThese = {"Have you had any of these in the past hour?","Alcohol","Water",
    "Coffee","Tea","Soda","Meal/snack","Other","Nothing"};
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
            whoWith, hadThese,othersSmoke,allowSmoke,persistStressor,occurStressor,naScared,naUpset, // 8
                naDistressed,naTense,naSad,naIrritable,naHopeless,paHappy,paCheerful,paEnthusiastic, //8
                paProud,paInterested,pssSchool,pssWork,pssFamily,pssMoney,ccMindCig, // 7
                ccDesireCig,ccUrgeCig,anhedoniaPeople,anhedoniaHobby, // 4
                anhedoniaSocial,endMessage); // 2

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

    public static int validateNextPosition(int qID, String aID) {
        Log.d("MQU-EMA","Received Qid" + qID + " and " + aID );
        return qID + 1;
    }


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




}
