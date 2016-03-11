package jy.com.finalproject;

import android.app.Application;

import java.util.List;

/**
 * Created by InvaderZim on 3/6/2016.
 */
public class ContactList extends Application{
    public List<Contact> contacts;
    public ContactList() {
        contacts = null;
    }

    public void addContact(int type, String number, String name) {
        Contact n = new Contact(type, number, name);
        contacts.add(n);
    }

    public void addContact(Contact n) {
        contacts.add(n);
    }

    public void deleteContact(String number) {
        contacts.remove(contacts.indexOf(number));
    }

    public Contact findContact(String number) {
        return contacts.get(contacts.indexOf(number));
    }
}
