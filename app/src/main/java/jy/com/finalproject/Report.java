package jy.com.finalproject;


/**
 * Created by jy on 3/2/2016.
 */
public class Report {
    private static String finalLocation;
    public static void report(String returnedAddress){
        Contact t = MainActivity.contacts.contacts.get(MainActivity.contacts.contacts.size() - 1);
        System.out.println("Message: " + t.text);
        System.out.print("Number: " + t.number);
        finalLocation = returnedAddress;
        String number = t.number;
        String sms = t.text + " " + returnedAddress;
        System.out.println("7) Send a text with final location: "+returnedAddress);
        //Send this along with text.
        //Ccall the texting function here, and pass in the returnedAddress
        Texting.sendMsg(number,sms);
    }

    public static void report(){
        Contact t = MainActivity.contacts.contacts.get(MainActivity.contacts.contacts.size() - 1);
        String number = t.number;
        String sms = t.text;
        System.out.println("3) Send text without a location.");
        //Send text
        //Call the texting function here
        Texting.sendMsg(number,sms);
    }
}
