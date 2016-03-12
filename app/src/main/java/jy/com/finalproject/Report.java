package jy.com.finalproject;


/**
 * Created by jy on 3/2/2016.
 */
public class Report {
    private static String finalLocation;
    public static void report(String returnedAddress){
        finalLocation = returnedAddress;
        String number = "5623251728";
        String sms = "Test Message"+returnedAddress;
        System.out.println("7) Send a text with final location: "+returnedAddress);
        //Send this along with text.
        //Ccall the texting function here, and pass in the returnedAddress
        Texting.sendMsg(number,sms);
    }

    public static void report(){
        String number = "5623251728";
        String sms = "Test Message";
        System.out.println("3) Send text without a location.");
        //Send text
        //Call the texting function here
        Texting.sendMsg(number,sms);
    }
}
