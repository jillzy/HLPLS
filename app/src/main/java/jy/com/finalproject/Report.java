package jy.com.finalproject;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by jy on 3/2/2016.
 */
public class Report{
    private static String finalLocation;
    private Context context;

    public Report(Context context){
        this.context = context;
    }

    public static void report(String returnedAddress){
        if(MainActivity.savedNumStatic != null) {
            finalLocation = returnedAddress;
            String number = MainActivity.savedNumStatic;
            String sms = MainActivity.savedTextStatic + " " + returnedAddress;
            System.out.println("7) Send a text with final location: " + returnedAddress);
            //Send this along with text.
            //Ccall the texting function here, and pass in the returnedAddress
            Texting.sendMsg(number, sms);
        }
    }

    public static void report() {
        if(MainActivity.savedNumStatic != null) {
            String number = MainActivity.savedNumStatic;
            String sms = MainActivity.savedTextStatic;
            System.out.println("3) Send text without a location.");
            //Send text
            //Call the texting function here
            Texting.sendMsg(number, sms);
        }
    }
}
