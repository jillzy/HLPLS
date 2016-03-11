package jy.com.finalproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.List;
import java.util.Locale;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    //whether or not the user has chosen to send their location
    boolean includeLocation = false;
    //whether or not a location has been retrieved
    boolean loc = false;
    float lat;
    float lng;
    public String latlng;
    public Retrofit retrofit;
    public static ContactList contacts = new ContactList();
    //flag that determines what happens when button is pushed
    private boolean editMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Define a LocationListener and request location updates
        makeLocationListener();
        //Prepare retrofit to reverse geocode
        retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Define the edit mode button
        ToggleButton editModeButton = (ToggleButton) findViewById(R.id.editModeButton);
        editModeButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            //Callback function, keeps track of what the current mode is
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //Edit mode is turned on
                    editMode = true;
                    System.out.println("edit mode on");
                } else {
                    //Edit mode is turned off
                    editMode = false;
                    System.out.println("edit mode off");
                }
            }
        });

    }


    //Button Listener
    public void buttonOneClick(View vv) {
        System.out.println("1) Button is pressed");
        if (editMode) {
            //The button goes to config page
            buttonEditOn();
        } else {
            //The button should send text
            buttonEditOff();
            String number = "5623251728";
            String sms = "Test Message";
            sendMsg(number,sms);
        }

    }


    protected void sendMsg(String number, String message){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number, null, message, null, null);

    }

    public void buttonEditOn() {
        //intent
        System.out.println("Pressed button with edit mode on");
        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
        startActivity(intent);
    }

    public void buttonEditOff() {
        //Check whether the user wants to include location
        checkLocationOption();

        //set phone to vibrate when button is pressed
        AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

        if (includeLocation == true){
            //Create the argument for our query
            latlng = lat+","+lng;
            //Begin the address lookup
            GetAddress.getAddress(latlng, retrofit);
        } else {
            //If the user doesn't want to include location
            //just send the preset message without it
            Report.report();

        }
    }

    //Helper method to check if user wants to include location in their text
    public void checkLocationOption() {
        CheckBox locationOption = (CheckBox) findViewById(R.id.locationOption);
        if (locationOption.isChecked()) {
            includeLocation = true;
            System.out.println("2) User wants to include location");
        } else {
            includeLocation = false;
            System.out.println("2) User does not want to include location");
        }
    }


    //*******************************************************//
    //                                                       //
    //             GETTING A LOCATION                        //
    //                                                       //
    //*******************************************************//
    protected void makeLocationListener() {
        //Acquire reference to system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }


        //Explicitly check for permissions
        if (locationManager != null &&
                (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //Use a cached location in the meantime before first location is received
                Location currentBestLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                //Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                //Register the listener with the Location Manager to receive location updates. Start listening
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            } else {
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {

                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_FINE_LOCATION);

                    // MY_PERMISSIONS_REQUEST_FINE_LOCATION is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }

            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        //Use a cached location in the meantime before first location is received
                        Location currentBestLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        //Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        //Register the listener with the Location Manager to receive location updates. Start listening
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                    } else {

                        // permission denied, boo! Disable the
                        // functionality that depends on this permission.
                    }
                    return;
                }

                // other 'case' lines to check for other
                // permissions this app might request
            }
        }
    }

    //Define a listener that responds to location updates
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            lat = (float)location.getLatitude();
            lng = (float)location.getLongitude();
            //Check the accuracy of the location
            if (location.getAccuracy() <= 50) {
                loc = true;
            }
            //Remove location update;
            //removeLocationUpdate();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onProviderDisabled(String provider) {}
    };

    private void removeLocationUpdate() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {

                locationManager.removeUpdates(locationListener);
            }
        }
    }


    protected void onStart() {
        //mGoogleApiClient.connect();
        super.onStart();

    }

    protected void onStop() {
        //mGoogleApiClient.disconnect();
        super.onStop();
    }
}