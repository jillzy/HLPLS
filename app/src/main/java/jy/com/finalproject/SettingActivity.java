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
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

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

        //*********************************************//
        //           Choose from address book          //
        // http://stackoverflow.com/questions/4993063/ //
        //*********************************************//

        ((Button)findViewById(R.id.chooseContacts)).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // user BoD suggests using Intent.ACTION_PICK instead of .ACTION_GET_CONTENT to avoid the chooser
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                // BoD con't: CONTENT_TYPE instead of CONTENT_ITEM_TYPE
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                startActivityForResult(intent, 1);
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
        }
    }

    public void saveSelectedNumber(int type, String number, String name) {
        //Type 2 indicates that it's a mobile number
        if (type == 2) {
            //Change this to save the selected number into an object
            //Show the name of the person chosen
        }
    }
}
