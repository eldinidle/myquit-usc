package edu.usc.reach.myquitusc;

import android.content.Context;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Eldin on 1/1/15.
 */
public class MyQuitPlanHelper {

    /**
     *
     * @param intentions as String[]
     * @return shuffled intentions as String[]
     */
    private static String[] shuffleIntentions(String[] intentions) {
        List<String> tester = Arrays.asList(intentions);
        Collections.shuffle(tester);
        String[] tester2 = new String[intentions.length];
        return tester.toArray(tester2);
    }

    public static String[] outWithFriends = new String[] {"Going out with friends",
            "If I am going out with friends...",""}; // , I will...",""};
    private static String[] helpOutWithFriends = new String[] {
            "I will leave my cigarettes at home or in my car.",
            "I will ask a non-smoker friend to stay inside with me.",
            "I will stay away from the place people usually smoke at.",
            "I will give my pack of cigarettes to a non-smoker friend."
};

    public static String[] partyBar = new String[] {"Party or bar",
            "If I am at a party or a bar...",""}; // , I will...",""};
    private static String[] helpPartyBar = new String[] {
            "I will leave my cigarettes at home or in my car.",
            "I will ask a non-smoker friend to stay inside with me.",
            "I will stay away from the place people usually smoke at.",
            "I will give my pack of cigarettes to a non-smoker friend."
    };


    public static String[] aroundSmokers = new String[] {"Around other smokers",
            "If I am around other people who smoke...",""}; // , I will...",""};
    private static String[] helpAroundSmokers = new String[] {
            "I will remind myself about why I want to quit smoking.",
            "I will be prepared to decline a cigarette offer.",
            "I will ask a non-smoker friend to stay inside with me.",
            "I will go to different location"
};

    public static String[] offerCigarette = new String[] {"Someone will offer cigarette",
            "If someone offers me a cigarette...",""}; // , I will...",""};
    private static String[] helpOfferCigarette = new String[] {
            "I will say I'm quitting and go to a different location",
            "I will hold the cigarette but not smoke it.",
            "I will say I am not feeling well or that my throat hurts.",
            "I will decline the offer and then remind myself why I want to quit"
    };

    public static String[] nonSmokingVenue = new String[] {"At a place where smoking isn't allowed",
            "If I am about to go to places where smoking is not allowed...",""}; // , I will...",""};
    private static String[] helpNonSmokingVenue = new String[] {
            "I will bring snacks to distract me ",
            "I will bring something to chew on, like gum or a toothpick",
            "I will look at my progress so far on MyQuit",
            "I will call or chat with my close friends"
    };

    public static String[] wakeUpActivity = new String[] {"Waking up",
            "If I get up in the morning...",""}; // , I will...",""};
    private static String[] helpWakeUpActivity = new String[] {
            "I will brush my teeth or stretch to get myself going",
            "I will take a shower as soon as I get up.",
            "I will stay busy by checking my email or my Facebook newsfeed.",
            "I will look over my progress on MyQuit so far"
    };

    public static String[] mealFinished = new String[] {"Eating a meal",
            "If I just finished a meal...",""}; // , I will...",""};
    private static String[] helpMealFinished = new String[] {
            "I will hide my cigarette pack inside my bag ",
            "I will eat dessert with a non-smoker friend",
            "I will look at my progress so far on MyQuit",
            "I will stay inside where smoking is not allowed."
    };

    public static String[] coffeeOrTea = new String[] {"Having coffee or tea",
            "If I am having coffee or tea...",""}; // , I will...",""};
    private static String[] helpCoffeeOrTea = new String[] {
            "I will hide my cigarette pack inside my bag ",
            "I will sit with a non-smoking friend",
            "I will look at my progress so far on MyQuit",
            "I will stay inside where smoking is not allowed."
    };

    public static String[] onABreak = new String[] {"On a break",
            "If I am having a break...",""}; // , I will...",""};
    private static String[] helpOnABreak = new String[] {
            "I will stay away from the place people usually smoke at.",
            "I will hang out with a non-smoker friend.",
            "I will go for a walk inside my building.",
            "I will look at my progress so far on MyQuit"
};

    public static String[] inACarActivity = new String[] {"Commuting or in a car",
            "If I commuting or in the car...",""}; // , I will...",""};
    private static String[] helpInACarActivity = new String[] {
            "I will chew on a straw or have a snack.",
            "I will sing along to the music that's playing.",
            "I will talk to a friend/family member on the phone",
            "I will put my cigarettes in the trunk."
};

    public static String[] bedTimeActivity = new String[] {"Going to bed",
            "If I am about to go to bed...",""}; // , I will...",""};
    private static String[] helpBedtimeActivity = new String[] {
            "I will hide my cigarettes so it is harder for me to find them in the morning",
            "I will remind myself about why I want to quit smoking.",
            "I will look at my progress so far on MyQuit",
            "I will plan out my schedule for the next day on MyQuit"
};

    public static String[] feelingDepressed = new String[] {"I'm feeling depressed",
            "If I am feeling depressed...",""}; // , I will...",""};
    private static String[] helpFeelingDepressed = new String[] {
            "I will go for a walk or a run to clear my mind",
            "I will do my favorite hobby.",
            "I will call a friend or family member",
            "I will hang out with a non-smoker friend."
};

    public static String[] underStress = new String[] {"I'm feeling angry or frustrated",
            "If I am feeling angry or frustrated...",""}; // , I will...",""};
    private static String[] helpUnderStress = new String[] {
            "I will close my eyes and breathe deeply and slowly",
            "I will call a friend or family member",
            "I will go to the gym",
            "I will hang out with a non-smoker friend."
};

    public static String[] desireCig = new String[] {"I'm desiring a cigarette",
            "If I am desiring a cigarette...",""}; // , I will...",""};
    private static String[] helpDesireCig = new String[] {
            "I will put my cigarette pack out of reach ",
            "I will keep busy by doing my work.",
            "I will hang out with a non-smoker friend.",
            "I will stay inside where smoking is not allowed."
    };

    public static String[] enjoyingSmoking = new String[] {"Someone is smoking and enjoying it",
            "If I see someone smoking and enjoying it...",""}; // , I will...",""};
    private static String[] helpEnjoyingSmoking = new String[] {
            "I will go to different location",
            "I will think about what is happening to their lungs.",
            "I will stay inside where smoking is not allowed.",
            "I will remind myself of the benefits of quitting."
};

    public static String[] gainedWeight = new String[] {"I noticed I gained weight",
            "If I noticed that I have gained weight...",""}; // , I will...",""};
    private static String[] helpGainedWeight = new String[] {
            "I will go outside for a quick walk.",
            "I will work out with a non-smoker friend.",
            "I will eat something healthy.",
            "I will drink a glass of water or tea."
};

    public static String[] iAmBored = new String[] {"I'm bored",
            "If I am bored...",""}; // , I will...",""};
    private static String[] helpIAmBored = new String[] {
            "I will go outside for a quick walk.",
            "I will hang out with a non-smoker friend.",
            "I will look at my progress so far on MyQuit",
            "I will stay busy by checking my email or my Facebook newsfeed."
    };

    public static String[] iAmDrinking = new String[] {"I'm drinking",
            "If I am drinking...",""}; // , I will...",""};
    private static String[] helpIAmDrinking = new String[] {
            "I will leave my cigarettes at home or in my car.",
            "I will ask a non-smoker friend to stay inside with me.",
            "I will stay away from the place people usually smoke at.",
            "I will give my pack of cigarettes to a non-smoker friend."
    };



    public static final ArrayList<String[]> baseHelpList = new ArrayList(Arrays.asList(helpOutWithFriends,helpPartyBar,helpAroundSmokers,
            helpOfferCigarette,helpNonSmokingVenue,helpWakeUpActivity,helpMealFinished,helpCoffeeOrTea,helpOnABreak,
            helpInACarActivity,helpBedtimeActivity,helpUnderStress,helpFeelingDepressed,helpDesireCig,
            helpEnjoyingSmoking,helpGainedWeight,helpIAmBored,helpIAmDrinking));


    public static final List<String[]> baseList = Arrays.asList(outWithFriends, partyBar,aroundSmokers,
            offerCigarette, nonSmokingVenue, wakeUpActivity, mealFinished, coffeeOrTea, onABreak,
            inACarActivity, bedTimeActivity, underStress, feelingDepressed, desireCig,
            enjoyingSmoking, gainedWeight, iAmBored,iAmDrinking);

    public static String[] taperPullBaseList(Context context, int positionEndOfListOne, int positionBeginListTwo, boolean temporaryChange) {
        List<String[]> pullAll = pullBaseList(context, temporaryChange);
        int length = pullAll.size();

        if((length)!=positionBeginListTwo) {
            List<String[]> pullAllOne = pullAll.subList(0,positionEndOfListOne);
            List<String[]> pullAllTwo = pullAll.subList((positionBeginListTwo),(length));
            for(String[] lineBy: pullAllTwo) {
                pullAllOne.add(lineBy);
            }
            int lengthnew = pullAllOne.size();
            int count = 0;
            String[] newPush = new String[lengthnew];
            for(String[] allPulled: pullAllOne) {
                newPush[count] = allPulled[0];
                count++;
            }
            return newPush;
        }
        else {
            List<String[]> pullAllNew = pullAll.subList(0,positionEndOfListOne);
            int lengthnew = pullAllNew.size();
            int count = 0;
            String[] newPush = new String[lengthnew];
            for(String[] allPulled: pullAllNew) {
                newPush[count] = allPulled[0];
                count++;
            }
            return newPush;
        }
    }

    public static boolean pushLabelBaseList(Context context, int position, String specifyItem, boolean temporaryChange) {
        String fileName;
        if(temporaryChange){fileName = "ACTIVITY_PAIRING" + ".csv";}
        else {fileName = "ACTIVITY_PAIRING_PERMANENT" + ".csv";}
        try {
            List<String[]> pullAll = pullBaseList(context, temporaryChange);
            int length = pullAll.size();
            int count = 0;
            ArrayList<String[]> newPush = new ArrayList<>();
            for(String[] allPulled: pullAll) {
                Log.d("MQU","Position is" + position + "count is" + count +"size is"+length);
                Log.d("MQU","Pulled" + allPulled[0]);
                if(count==position){
                    Log.d("MQU","Changed" + allPulled[0]);
                    allPulled[0] = specifyItem;
                    allPulled[1] = "If I am " + specifyItem;
                    newPush.add(allPulled);
                }
                else {
                    Log.d("MQU","Pushed" + allPulled[0]);
                    newPush.add(allPulled);
                }
                count++;
            }
            CSVWriter writer = new CSVWriter(new FileWriter(MyQuitCSVHelper.calPath + fileName));
            writer.writeAll(newPush);
            writer.close();
            return true;
        }
        catch(IOException eo) {
            Toast.makeText(context,"Please restart app and try again",Toast.LENGTH_LONG).show();
            return false;
        }
    }


    public static void extendBaseListOne(Context context, boolean temporaryChange) {
        List<String[]> pullAll = pullBaseList(context, temporaryChange);
        pullAll.add(new String[] {"","",""});
        pushBaseList(context,pullAll, temporaryChange);
    }

    public static void shrinkBaseList(Context context, int positionToDelete,boolean temporaryChange) {
        List<String[]> pullAll = pullBaseList(context,temporaryChange);
        pullAll.remove(positionToDelete);
        pushBaseList(context,pullAll,temporaryChange);
    }


    public static String[] pullIntentsList(Context context,boolean temporaryChange) {
        List<String[]> pullAll = pullBaseList(context, temporaryChange);
        int length = pullAll.size();
        int count = 0;
        String[] newPush = new String[length];
        for(String[] allPulled: pullAll) {
            newPush[count] = allPulled[2];
            count++;
        }
        return newPush;
    }


    public static String[] pullSituationList(Context context, boolean temporaryChange) {
        List<String[]> pullAll = pullBaseList(context, temporaryChange);
        int length = pullAll.size();
        int count = 0;
        String[] newPush = new String[length];
        for(String[] allPulled: pullAll) {
            newPush[count] = allPulled[1];
            count++;
        }
        return newPush;
    }

    public static boolean checkTaskListDuplicate(String situation, Context context) {
        String originalSituation = situation.replace(" ", "");
        for(String cycleSituation: pullTasksList(context,false)){
            if(cycleSituation.replace(" ","").equalsIgnoreCase(originalSituation)){
                return true;
            }
        }
        return false;
    }

    public static String[] pullTasksList(Context context, boolean temporaryChange) {
        List<String[]> pullAll = pullBaseList(context, temporaryChange);
        int length = pullAll.size();
        int count = 0;
        String[] newPush = new String[length];
        for(String[] allPulled: pullAll) {
            newPush[count] = allPulled[0];
            count++;
        }
        return newPush;
    }

    public static boolean pushIntentBaseList(Context context, int position, String specifyItem, boolean temporaryChange) {
        String fileName;
        if(temporaryChange){fileName = "ACTIVITY_PAIRING" + ".csv";}
        else {fileName = "ACTIVITY_PAIRING_PERMANENT" + ".csv";}
        try {
            List<String[]> pullAll = pullBaseList(context, temporaryChange);
            int length = pullAll.size();
            int count = 0;
            ArrayList<String[]> newPush = new ArrayList<>();
            for(String[] allPulled: pullAll) {
                Log.d("MQU","Position is" + position + "count is" + count +"size is"+length);
                Log.d("MQU","Pulled" + allPulled[0]);
                if(count==position){
                    Log.d("MQU","Changed" + allPulled[0]);
                    allPulled[2] = specifyItem;
                    newPush.add(allPulled);
                }
                else {
                    Log.d("MQU","Pushed" + allPulled[0]);
                    newPush.add(allPulled);
                }
                count++;
            }
            CSVWriter writer = new CSVWriter(new FileWriter(MyQuitCSVHelper.calPath + fileName));
            writer.writeAll(newPush);
            writer.close();
            return true;
        }
        catch(IOException eo) {
            Toast.makeText(context,"Please restart app and try again",Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private static void backupOldList(Context context){
        String fileName = "ACTIVITY_PAIRING_PERMANENT" + ".csv";
        List<String[]> holdAll;
        String newFile;
        try {
            CSVReader reader = new CSVReader(new FileReader(MyQuitCSVHelper.calPath + fileName));
            holdAll = reader.readAll();
            reader.close();
            String time = MyQuitCSVHelper.getFulltime();
            time = time.replaceAll(":","_");
            time = time.replaceAll("/","_");
            time = time.replaceAll(" ","_");
            newFile = "ACTIVITY_PAIRING_SET" + time + ".csv";
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(MyQuitCSVHelper.calPath + newFile));
            writer.writeAll(holdAll);
            writer.close();
        } catch (IOException e) {
            Toast.makeText(context,"Please restart app and try again",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    public static void cycleBaseListTracker(Context context){
        List<String[]> baseList = pullBaseList(context,false);
        for(String[] holdArray: baseList){
            String situation = holdArray[0];
            String intention = holdArray[2];
            MyQuitPHP.postTrackerEvent(MyQuitCSVHelper.pullLoginStatus("UserName"),"New Situation: " + situation,"New Intention: " +intention,MyQuitCSVHelper.getFulltime());
        }

    }

    public static void appendToBaseList(Context context, String situation, String intention,boolean temporaryChange){
        List<String[]> oldBaseList = pullBaseList(context,temporaryChange);
        String[] threeItems = new String[] {situation,"If I am " + situation + "...",intention}; // ", I will...",intention};
        oldBaseList.add(threeItems);
        pushBaseList(context,oldBaseList,temporaryChange);
        MyQuitPHP.postTrackerEvent(MyQuitCSVHelper.pullLoginStatus("UserName"),"New Situation: " + situation,"New Intention: " +intention,MyQuitCSVHelper.getFulltime());
    }

    public static boolean pushBaseList(Context context, List<String[]> newBaseList, boolean temporaryChange) {
        String fileName;
        if(temporaryChange){fileName = "ACTIVITY_PAIRING" + ".csv";}
        else {
            fileName = "ACTIVITY_PAIRING_PERMANENT" + ".csv";
            MyQuitPHP.postTrackerEvent(MyQuitCSVHelper.pullLoginStatus("UserName"),"MyQuit Plans Changed","NA",MyQuitCSVHelper.getFulltime());
            backupOldList(context);
        }
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(MyQuitCSVHelper.calPath + fileName));
            writer.writeAll(newBaseList);
            writer.close();
            return true;
        }
        catch(IOException eo) {
            Toast.makeText(context,"Please restart app and try again",Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public static List<String[]> pullBaseList(Context context, boolean temporaryChange) {
        String fileName;
        if(temporaryChange){fileName = "ACTIVITY_PAIRING" + ".csv";}
        else {fileName = "ACTIVITY_PAIRING_PERMANENT" + ".csv";}
        try {
            CSVReader reader = new CSVReader(new FileReader(MyQuitCSVHelper.calPath + fileName));
            List<String[]> pulledList = reader.readAll();
            reader.close();
            return pulledList;
        }
        catch(IOException eo) {
            Toast.makeText(context,"Please restart app and try again",Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public static boolean convertBaseList(Context context) {
        return pushBaseList(context,pullBaseList(context, true),false);
        //TODO: Add log of this, this is IMPORTANT
    }

    public static boolean listDone(Context context, boolean temporaryChange) {
        return (pullSizeBaseList(context, temporaryChange) == countCompletion(context, temporaryChange));
    }

    public static String getTextCompletion(Context context, boolean temporaryChange) {
        if(pullSizeBaseList(context, temporaryChange) == countCompletion(context, temporaryChange)) {
            return "Submit";
        }
        else {
            return (countCompletion(context, temporaryChange) + "/" + pullSizeBaseList(context, temporaryChange) + " Done");
        }
    }

    public static int pullSizeBaseList(Context context, boolean temporaryChange) {
        List<String[]> testList = pullBaseList(context, temporaryChange);
        return testList.size();
    }


    public static int countCompletion(Context context, boolean temporaryChange) {
        List<String[]> testList = pullBaseList(context, temporaryChange);
        int count = 0;
        for(String[] testArray: testList){
            if(testArray[2].length()>5) {
                count++;
            }
        }
        return count;
    }


   public static String[] suggestList(Context context, int position, boolean temporaryChange) {
       if (pullTasksList(context, temporaryChange)[position].equalsIgnoreCase(outWithFriends[0])) {
           return helpOutWithFriends;
       } else if (pullTasksList(context, temporaryChange)[position].equalsIgnoreCase(partyBar[0])) {
           return helpPartyBar;
       } else if (pullTasksList(context, temporaryChange)[position].equalsIgnoreCase(iAmDrinking[0])) {
           return helpIAmDrinking;
       } else if (pullTasksList(context, temporaryChange)[position].equalsIgnoreCase(aroundSmokers[0])) {
           return helpAroundSmokers;
       } else if (pullTasksList(context, temporaryChange)[position].equalsIgnoreCase(offerCigarette[0])) {
           return helpOfferCigarette;
       } else if (pullTasksList(context, temporaryChange)[position].equalsIgnoreCase(nonSmokingVenue[0])) {
           return helpNonSmokingVenue;
       } else if (pullTasksList(context, temporaryChange)[position].equalsIgnoreCase(wakeUpActivity[0])) {
           return helpWakeUpActivity;
       } else if (pullTasksList(context, temporaryChange)[position].equalsIgnoreCase(mealFinished[0])) {
           return helpMealFinished;
       } else if (pullTasksList(context, temporaryChange)[position].equalsIgnoreCase(coffeeOrTea[0])) {
           return helpCoffeeOrTea;
       } else if (pullTasksList(context, temporaryChange)[position].equalsIgnoreCase(onABreak[0])) {
           return helpOnABreak;
       } else if (pullTasksList(context, temporaryChange)[position].equalsIgnoreCase(inACarActivity[0])) {
           return helpInACarActivity;
       } else if (pullTasksList(context, temporaryChange)[position].equalsIgnoreCase(bedTimeActivity[0])) {
           return helpBedtimeActivity;
       } else if (pullTasksList(context, temporaryChange)[position].equalsIgnoreCase(feelingDepressed[0])) {
           return helpFeelingDepressed;
       } else if (pullTasksList(context, temporaryChange)[position].equalsIgnoreCase(desireCig[0])) {
           return helpDesireCig;
       } else if (pullTasksList(context, temporaryChange)[position].equalsIgnoreCase(enjoyingSmoking[0])) {
           return helpEnjoyingSmoking;
       } else if (pullTasksList(context, temporaryChange)[position].equalsIgnoreCase(gainedWeight[0])) {
           return helpGainedWeight;
       } else if (pullTasksList(context, temporaryChange)[position].equalsIgnoreCase(iAmBored[0])) {
           return helpIAmBored;
       } else if (pullTasksList(context, temporaryChange)[position].equalsIgnoreCase(underStress[0])) {
           return helpUnderStress;
       } else {
           return new String[] {
             "Chew some gum",
             "Do 10 push-ups",
             "Go indoors",
             "Go out for a jog"
           };
       }
   }


}
