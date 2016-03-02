package jy.com.finalproject;

import jy.com.finalproject.Interface.GetAddressService;
import jy.com.finalproject.Models.Example;
import jy.com.finalproject.Models.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by jy on 3/2/2016.
 */
public class GetAddress {
    private static String returnedAddress;
    public static void getAddress(final String latlng, Retrofit retrofit) {
        System.out.println("3) called getAddress()");

        GetAddressService getMessageService = retrofit.create(GetAddressService.class);
        Call<Example> queryResponseCall =
                getMessageService.path(latlng);

        System.out.println("4) getAddress() knows latlng is " + latlng);

        //Call retrofit asynchronously for GETTING messages
        queryResponseCall.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Response<Example> response) {
                Example responseObject = response.body();
                System.out.println("5) Got a json response");
                System.out.println(responseObject.status);
                if (responseObject.status.equals("OK")) {
                    //If an address is found at the coordinates
                    returnedAddress = responseObject.results.get(0).formattedAddress
                            +" at (LAT,LNG) " + latlng;
                    System.out.println("6) Found and retrieved an address. Use address and coordinates.");
                    Report.report(returnedAddress);
                } else {
                    //If there is no address found at the coordinates
                    returnedAddress = latlng;
                    System.out.println("6) Could not find an address. Just use coordinates instead.");
                    Report.report(returnedAddress);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                //TODO: somehow show error status message
                System.out.println("Request Failed");
            }
        });
    }
}