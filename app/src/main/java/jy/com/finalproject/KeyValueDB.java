package jy.com.finalproject;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by InvaderZim on 3/13/2016.
 */
public class KeyValueDB {
    private SharedPreferences sharedPreferences;
    private static String PREF_NAME = "prefs";

    public KeyValueDB(){

    }

    private static SharedPreferences getPrefs(Context context){
        return context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
    }

    public static String getNumber(Context context){
        return getPrefs(context).getString("number", "No Number");
    }

    public static void setNumber(Context context, String input){
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("number",input);
        editor.commit();
    }

    public static String getText(Context context){
        return getPrefs(context).getString("text", "No Text");
    }

    public static void setText(Context context, String input){
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("text",input);
        editor.commit();
    }
}
