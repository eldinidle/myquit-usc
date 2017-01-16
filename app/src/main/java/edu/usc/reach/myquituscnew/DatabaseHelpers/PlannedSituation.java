package edu.usc.reach.myquituscnew.DatabaseHelpers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eldin on 3/17/15.
 */
public class PlannedSituation {

    int _count;
    String _datetime;
    String _situation;
    boolean _activated;

    public PlannedSituation (){};

    public PlannedSituation(int count, String datetime, String situation, boolean activated){
        this._activated = activated;
        this._situation = situation;
        this._datetime = datetime;
        this._count = count;
    }

    public PlannedSituation(String datetime, String situation, boolean activated){
        this._activated = activated;
        this._situation = situation;
        this._datetime = datetime;
    }


    public void setDateTime(String dateTime){
        this._datetime = dateTime;
    }
    public void setSituation(String situation){
        this._situation = situation;
    }
    public void setActivated(boolean activated){
        this._activated = activated;
    }
    public void setCount(int count){
        this._count = count;
    }

    public String getDateTime(){
        return this._datetime;
    }
    public String getSituation(){
        return this._situation;
    }
    public boolean getActivated(){
        return this._activated;
    }
    public int getCount(){
        return this._count;
    }

    public static List<String[]> toArray(List<PlannedSituation> inputListPS) {
        List<String[]> finalList= new ArrayList<String[]>();
        String[] tempArray = new String[4];
        for(PlannedSituation pSit: inputListPS) {
            tempArray[0] = String.valueOf(pSit.getCount());
            tempArray[1] = pSit.getDateTime();
            tempArray[2] = pSit.getSituation();
            tempArray[3] = String.valueOf(pSit.getActivated());
            finalList.add(tempArray);
        }
        return finalList;
    }

}
