package edu.usc.reach.myquitusc;

import android.content.Context;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Eldin on 1/1/15.
 */
public class MyQuitPlanHelper {

    public static String[] outWithFriends = new String[] {"Going out with friends",
            "If I am going out with friends, I will...",""};
    private static String[] helpOutWithFriends = new String[] {
            "hang out with the non smokers when the smokers go somewhere to smoke",
            "leave my cigarettes at home",
            "bring a stress ball with me so I can play with it whenever craving",
            "bring a water bottle with me so I can drink water whenever craving",
            "bring my phone and play with it"
    };

    public static String[] partyBar = new String[] {"Party or bar",
            "If I am at a party or a bar, I will...",""};
    private static String[] helpPartyBar = new String[] {
            "interact more with people who are not smoking",
            "not bring my pack of cigarettes",
            "text a friend to distract myself",
            "give my pack of cigarettes to a friend",
            "keep myself busy with dancing or talking to my friends"
    };

    public static String[] aroundSmokers = new String[] {"Around other smokers",
            "If I am around other people who smoke, I will...",""};
    private static String[] helpAroundSmokers = new String[] {
            "excuse myself and go indoors if they're smoking outdoors",
            "stay behind with non smoker friends",
            "chew gum and play phone games",
            "ask them not to offer me a cigarette",
            "excuse myself to go to the bathroom"
    };

    public static String[] offerCigarette = new String[] {"Someone will offer cigarette",
            "If someone offers me a cigarette, I will...",""};
    private static String[] helpOfferCigarette = new String[] {
            "say no and say I'm quitting",
            "say I'm quitting and eat candy instead",
            "politely say no and then engange in some friendly conversation, or listen to my favourite song for a minute or two to keep myslef distracted and keep my spirits high.",
            "take a cigarette and throw it away",
            "say that my throat is hurting or I am not feeling well"
    };

    public static String[] nonSmokingVenue = new String[] {"At a place where smoking isn't allowed",
            "If I am about to go to places where smoking is not allowed, I will...",""};
    private static String[] helpNonSmokingVenue = new String[] {
            "bring a pack of gum",
            "engage with other non-smokers",
            "bring a toothpick to chew on",
            "bring snacks to distract me"
    };

    public static String[] wakeUpActivity = new String[] {"Waking up",
            "If I get up in the morning, I will...",""};
    private static String[] helpWakeUpActivity = new String[] {
            "do some exercises or stretching to get myself going",
            "brush my teeth first thing in the morning",
            "make sure to not allow for time to smoke, stay busy",
            "drink a cup of cold water"
    };

    public static String[] mealFinished = new String[] {"Eating a meal",
            "If I just finished a meal, I will...",""};
    private static String[] helpMealFinished = new String[] {
            "eat fresh fruit",
            "not go outside unless I need to",
            "go for a walk",
            "drink coffee",
            "floss and brush my teeth"
    };

    public static String[] coffeeOrTea = new String[] {"Having coffee or tea",
            "If I am having coffee or tea, I will...",""};
    private static String[] helpCoffeeOrTea = new String[] {
            "ask them not to offer me a cigarette",
            "not drink coffee or tea with smokers",
            "drink the coffee or tea indoors"
    };

    public static String[] onABreak = new String[] {"On a break",
            "If I am having a break, I will...",""};
    private static String[] helpOnABreak = new String[] {
            "take a nap",
            "go for a walk",
            "listen to a song or watch a video clip.",
            "go to a restaurant or get a snack from somewhere I have not been before",
            "read a book"
    };

    public static String[] inACarActivity = new String[] {"Commuting or in a car",
            "If I commuting or in the car, I will...",""};
    private static String[] helpInACarActivity = new String[] {
            "play engaging music or podcasts to keep my focus",
            "talk to a friend or family member on the phone",
            "throw my cigarette pack in the back seat"
    };

    public static String[] bedTimeActivity = new String[] {"Going to bed",
            "If I am about to go to bed, I will...",""};
    private static String[] helpBedtimeActivity = new String[] {
            "listen to calming music to relax myself",
            "read a book",
            "meditate",
            "hide my cigarettes so it is harder for me to find them in the morning",
            "plan out my entire morning and not allow time to smoke"
    };

    public static String[] feelingDepressed = new String[] {"I'm feeling depressed",
            "If I am feeling depressed, I will...",""};
    private static String[] helpFeelingDepressed = new String[] {
            "go for a walk or for a run to clear my mind",
            "call a friend or family member",
            "take a nap",
            "exercise or go for a run"
    };

    public static String[] underStress = new String[] {"I'm feeling angry or frustrated",
            "If I am feeling angry or frustrated, I will...",""};
    private static String[] helpUnderStress = new String[] {
            "go out for a fresh air",
            "practice breathing deeply",
            "play with a stress ball",
            "go for a walk alone"
    };

    public static String[] desireCig = new String[] {"I'm desiring a cigarette",
            "If I am desiring a cigarette, I will...",""};
    private static String[] helpDesireCig = new String[] {
            "substitute the unhealthy behavior to a positive one such as eating fruits or vegetables",
            "chew on a straw",
            "call a friend or family member",
            "put my cigarette pack out of reach",
            "text a close friend that I promise not to smoke a cigarette for the rest of the day",
    };

    public static String[] enjoyingSmoking = new String[] {"Someone is smoking and enjoying it",
            "If I see someone smoking and enjoying it, I will...",""};
    private static String[] helpEnjoyingSmoking = new String[] {
            "try and think of a more positive behavior",
            "reflect on the negative health consequences for them",
            "watch a scary video reflecting negative smoking health effects on Youtube",
            "remind myself of the positive consequences of quitting smoking",
            "think of my loved ones or people who care about me and remind myself of how happy they are knowing I am quitting smoking",
            "visualize the smoking affecting their or my lungs"
    };

    public static String[] gainedWeight = new String[] {"I noticed I gained weight",
            "If I noticed that I have gained weight, I will...",""};
    private static String[] helpGainedWeight = new String[] {
            "go to the gym more often",
            "not eat past 8pm",
            "start walking",
            "try and eat healthy",
            "plan a workout or diet schedule for the day"
    };

    public static String[] iAmBored = new String[] {"I'm bored",
            "If I am bored, I will...",""};
    private static String[] helpIAmBored = new String[] {
            "read and be more current with the news",
            "play game on my phone",
            "read a book",
            "go out to a park for a walk or jog",
            "learn a new hobby",
            "bring my phone and play with it"
    };

    public static String[] iAmDrinking = new String[] {"I'm drinking",
            "If I am drinking, I will...",""};
    private static String[] helpIAmDrinking = new String[] {
            "not bring my pack of cigarettes or ask for a cig",
            "ask my friends to not offer me a cigarette",
            "not drink to the point where I am drunk and incapable of stopping myself from smoking",
            "only stay inside so that I cannot smoke outside",
            "ask a friend to stay inside with me"
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


    public static boolean pushBaseList(Context context, List<String[]> newBaseList, boolean temporaryChange) {
        String fileName;
        if(temporaryChange){fileName = "ACTIVITY_PAIRING" + ".csv";}
        else {fileName = "ACTIVITY_PAIRING_PERMANENT" + ".csv";}
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
