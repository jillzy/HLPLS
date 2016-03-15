package jy.com.finalproject;

import android.app.Application;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by InvaderZim on 3/6/2016.
 */
public class ContactList extends Application{
    public static List<Contact> contacts;
    public ContactList() {
        contacts = new ArrayList<Contact>();
    }

    public void addContact(int type, String number, String name) {
        Contact n = new Contact(type, number, name);
        contacts.add(n);
    }

    public void addContact(Contact n) {
        contacts.add(n);
    }

    public void addContact(int type, String number, String name, String text) {
        Contact n = new Contact(type, number, name, text);
        contacts.add(n);
    }

    public void deleteContact(String number) {
        for(int i = 0; i < contacts.size(); i++) {
            if(contacts.get(i).number == number) {
                contacts.remove(i);
            }
        }
    }

    public Contact findContact(String number) {
        for(int i = 0; i < contacts.size(); i++) {
            if(contacts.get(i).number == number) {
                return contacts.get(i);
            }
        }
        Contact empty = new Contact(-1, null, null);
        return empty;
    }
/*
    public static StringBuilder displayContacts() {
        StringBuilder builder = new StringBuilder();
        for (Contact contact : contacts) {
            if (contact.name == null ) {
                builder.append(contact.number+ "\n");
            } else {
                builder.append(contact.name + "\n");
            }
        }
        return builder;
    }*/
}
