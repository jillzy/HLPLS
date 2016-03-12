package jy.com.finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class SettingActivity extends AppCompatActivity {
    private String names = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //Manually add in number
        ((EditText)findViewById(R.id.phoneNumber)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Clicked edit text");

            }
        });

        Button submitNumberButton = (Button) findViewById(R.id.submitNumberButton);
        submitNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //retrieve string from edit text
                final EditText phoneNumber = (EditText) findViewById(R.id.phoneNumber);
                //save the string in a var
                String number = phoneNumber.getText().toString();
                phoneNumber.setText("");
                //saving stuff
                Contact n = new Contact(2, number, null);
                MainActivity.contacts.addContact(n);
            }
        });

        //*********************************************//
        //           Choose from address book          //
        //           Revised source code from:         //
        // http://stackoverflow.com/questions/4993063/ //
        //*********************************************//

        ((Button)findViewById(R.id.chooseContacts)).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(intent, 1);
                System.out.println("Choosing contact");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Uri uri = data.getData();

            if (uri != null) {
                Cursor c = null;
                try {
                    c = getContentResolver().query(uri, new String[]{
                                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                                    ContactsContract.CommonDataKinds.Phone.TYPE,
                                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME},
                            null, null, null);

                    if (c != null && c.moveToFirst()) {
                        String number = c.getString(0);
                        int type = c.getInt(1);
                        String name = c.getString(2);
                        showSelectedNumber(type, number, name);
                        saveSelectedNumber(type, number, name);

                    }
                } finally {
                    if (c != null) {
                        c.close();
                    }
                }
            }
        }
    }

    public void showSelectedNumber(int type, String number, String name) {
        //Type 2 indicates that it's a mobile number
        if (type == 2) {
            Toast.makeText(this, name +": " + number, Toast.LENGTH_LONG).show();
            TextView chosenContactList = (TextView) findViewById(R.id.chosenContactList);
            names += name+"\n";
            chosenContactList.setText(names);
        }

    }

    public void saveSelectedNumber(int type, String number, String name) {
        //Type 2 indicates that it's a mobile number
        if (type == 2) {
            MainActivity.contacts.addContact(type, number, name);
            System.out.println("Chose a contact");
            System.out.println("The contact chosen is: " +
                    MainActivity.contacts.findContact(number).name);
            System.out.println(MainActivity.contacts.findContact(number).name+"'s phone number is "+
                    MainActivity.contacts.findContact(number).number);
        }
    }
}
