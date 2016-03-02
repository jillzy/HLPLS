package jy.com.finalproject;

/**
 * Created by jy on 3/2/2016.
 */
public class Report {
    private static String finalLocation;
    public static void report(String returnedAddress){
        finalLocation = returnedAddress;
        System.out.println("Send text with final location: "+returnedAddress);
        //Send this along with text
    }

    public static void report(){
        System.out.println("Send text without a location.");
        //Send text
    }
}
