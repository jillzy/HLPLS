package jy.com.finalproject;

import android.telephony.SmsManager;

/**
 * Created by fernie on 3/6/2016.
 */
public class Texting {

    protected static void sendMsg(String number, String message) {
        if(MainActivity.savedNumStatic != null && number != null && number.length() > 4) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, message, null, null);
        }
    }
}
