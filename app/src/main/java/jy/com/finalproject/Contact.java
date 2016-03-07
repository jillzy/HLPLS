package jy.com.finalproject;

import java.util.Random;

/**
 * Created by InvaderZim on 3/6/2016.
 */
public class Contact {
    public int type;
    public String number;
    public String name;
    public String text;
    public Contact(int typeP, String num, String nam) {
        type = typeP;
        number = num;
        name = nam;
        text = "";
    }
}
