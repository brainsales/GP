package com.brainsales.gameport.utils;

import android.app.Application;

/**
 * Created by user on 2017-07-11.
 */

//For common val

public class Gameport extends Application{

    public boolean checkUserData = false;

    public boolean getGlobalValue(){
        return checkUserData;
    }

    public void setGlobalValue(boolean mValue){
        this.checkUserData = mValue;
    }

}
