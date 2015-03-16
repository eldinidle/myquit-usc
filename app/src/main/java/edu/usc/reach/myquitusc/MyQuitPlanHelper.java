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
            "I will leave my cigarettes at home.",
            "I will bring a stress ball with me.",
            "I will ask my friends to give me a dollar for each hour that I didn't smoke.",
            "I will hang out with non-smoker friends when smokers smoke",
            "I will bring a water bottle with me",
            "I will bring my phone and play with it",
            "I will take a cigarette and throw it away"
};

    public static String[] partyBar = new String[] {"Party or bar",
            "If I am at a party or a bar, I will...",""};
    private static String[] helpPartyBar = new String[] {
            "I will interact more with people who are not smoking",
            "I will keep myself busy with dancing or talking to people",
            "I will stay inside where smoke is not allowed",
            "I will text a friend to distract myself  I will give my pack of cigarettes to a friend",
            "I will not bring my pack of cigarettes",
            "I will give my pack of cigarettes to a friend"
    };

    public static String[] aroundSmokers = new String[] {"Around other smokers",
            "If I am around other people who smoke, I will...",""};
    private static String[] helpAroundSmokers = new String[] {
            "I will ask them not to offer me a cigarette",
            "I will excuse myself and go indoors if they're smoking outdoors",
            "I will stay behind with non smoker friends",
            "I will chew gum and play phone games",
            "I will excuse myself to go to the bathroom"
};

    public static String[] offerCigarette = new String[] {"Someone will offer cigarette",
            "If someone offers me a cigarette, I will...",""};
    private static String[] helpOfferCigarette = new String[] {
            "I will say I'm quitting and go to a restroom",
            "I will say that my throat is hurting",
            "I will say I am not feeling well",
            "I will refuse and then remind myself why I want to quit",
            "I will say no and say I'm quitting",
            "I will politely say no and then engange in some friendly conversation",
    };

    public static String[] nonSmokingVenue = new String[] {"At a place where smoking isn't allowed",
            "If I am about to go to places where smoking is not allowed, I will...",""};
    private static String[] helpNonSmokingVenue = new String[] {
            "I will bring snacks to distract me",
            "I will bring a toothpick to chew on",
            "I will bring a pack of gum",
            "I will engage with other non-smokers"
    };

    public static String[] wakeUpActivity = new String[] {"Waking up",
            "If I get up in the morning, I will...",""};
    private static String[] helpWakeUpActivity = new String[] {
            "I will brush my teeth first thing in the morning.",
            "I will do some exercises to get myself going.",
            "I will do some stretching to get myself going",
            "I will nibble on some snacks",
            "I will stay busy, not allow any time to smoke",
            "I will drink a cup of cold water"
    };

    public static String[] mealFinished = new String[] {"Eating a meal",
            "If I just finished a meal, I will...",""};
    private static String[] helpMealFinished = new String[] {
            "I will drink coffee",
            "I will go talk with a non-smoker",
            "I will go for a walk",
            "I will eat fresh fruit",
            "I will not go outside",
            "I will floss and brush my teeth"
    };

    public static String[] coffeeOrTea = new String[] {"Having coffee or tea",
            "If I am having coffee or tea, I will...",""};
    private static String[] helpCoffeeOrTea = new String[] {
            "I will not drink coffee/tea with smokers",
            "I will drink the coffee/tea indoors"
    };

    public static String[] onABreak = new String[] {"On a break",
            "If I am having a break, I will...",""};
    private static String[] helpOnABreak = new String[] {
            "I will avoid being around smokers",
            "I will listen to a song/watch a video clip",
            "I will go for a walk",
            "I will read a book",
            "I will take a nap",
            "I will try new snacks I have never had before"
};

    public static String[] inACarActivity = new String[] {"Commuting or in a car",
            "If I commuting or in the car, I will...",""};
    private static String[] helpInACarActivity = new String[] {
            "I will play engaging music or podcasts to keep my focus",
            "I will talk to a friend/family member on the phone",
            "I will listen to my favorite music for not smoking",
            "I will throw my cigarette pack in the back seat"
};

    public static String[] bedTimeActivity = new String[] {"Going to bed",
            "If I am about to go to bed, I will...",""};
    private static String[] helpBedtimeActivity = new String[] {
            "I will listen to calming music to relax myself",
            "I will meditate",
            "I will read a book",
            "I will hide my cigarettes so it is harder for me to find them in the morning",
            "I will plan out my entire morning and not allow time to smoke"
};

    public static String[] feelingDepressed = new String[] {"I'm feeling depressed",
            "If I am feeling depressed, I will...",""};
    private static String[] helpFeelingDepressed = new String[] {
            "I will go for a walk or for a run to clear my mind",
            "I will call a friend or family member",
            "I will take a nap",
};

    public static String[] underStress = new String[] {"I'm feeling angry or frustrated",
            "If I am feeling angry or frustrated, I will...",""};
    private static String[] helpUnderStress = new String[] {
            "I will practice breathing deeply",
            "I will go for a walk alone",
            "I will go out for a fresh air",
            "I will play with a stress ball"
};

    public static String[] desireCig = new String[] {"I'm desiring a cigarette",
            "If I am desiring a cigarette, I will...",""};
    private static String[] helpDesireCig = new String[] {
            "I will put my cigarette pack out of reach",
            "I will text a close friend that I promise not to smoke a cigarette for the rest of the day.",
            "I will substitute the unhealthy behavior to a positive one such as eating fruits/vegetables",
            "I will chew on a straw",
            "I will call a friend or family member",
            "I will think of my significant other",
            "I will think of my future child"
    };

    public static String[] enjoyingSmoking = new String[] {"Someone is smoking and enjoying it",
            "If I see someone smoking and enjoying it, I will...",""};
    private static String[] helpEnjoyingSmoking = new String[] {
            "I will distract myself and avoid looking at them",
            "I will reflect on the negative health consequences for them,",
            "I will visualize the smoking affecting their/my lungs",
            "I will watch a video on harmful effects of smoking",
            "I will remind myself of the positive consequences of quitting smoking",
            "I will try and think of a more positive behavior",
            "I will think of my loved ones who care about me"
};

    public static String[] gainedWeight = new String[] {"I noticed I gained weight",
            "If I noticed that I have gained weight, I will...",""};
    private static String[] helpGainedWeight = new String[] {
            "I will go straight to the gym",
            "I will plan a workout/diet schedule for the day",
            "I will go to the gym more often",
            "I will not eat past 8pm",
            "I will start walking",
            "I will try and eat healthy"
};

    public static String[] iAmBored = new String[] {"I'm bored",
            "If I am bored, I will...",""};
    private static String[] helpIAmBored = new String[] {
            "I will go out to a park for a walk/jog",
            "I will find something constructive to do with a friend",
            "I will read a book",
            "I will learn a new hobby",
            "I will read and be more current with the news",
            "I will play game on my phone"
    };

    public static String[] iAmDrinking = new String[] {"I'm drinking",
            "If I am drinking, I will...",""};
    private static String[] helpIAmDrinking = new String[] {
            "I will not take my cigarettes with me",
            "I will chew on a straw",
            "I will use the restroom more frequently",
            "I will ask a friend to stay inside with me",
            "I will not bring my pack of cigarettes",
            "I will ask my friends to not offer me a cigarette",
            "I will not drink to the point where I am drunk",
            "I will only stay inside so I don't smoke outside",
            "I will not ask anyone for a cig"
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
