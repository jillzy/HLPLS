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
    public static void getAddress(String latlng, Retrofit retrofit) {
        System.out.println("4) called getAddress()");
        GetAddressService getMessageService = retrofit.create(GetAddressService.class);


        Call<Example> queryResponseCall =
                getMessageService.path(latlng);

        System.out.println("5) getAddres() knows latlng is "+ latlng);

        //Call retrofit asynchronously for GETTING messages
        queryResponseCall.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Response<Example> response) {
                Example responseObject = response.body();
                System.out.println("6) Got a json response");
                System.out.println(responseObject.status);
                if (responseObject.status.equals("OK")) {
                    System.out.println("7) Address is: " + responseObject.results.get(0).formattedAddress);
                }//Example responseObject = response.body();
                /*
                for (i =  responseObject.resultList.size()-1; i >= 0 ; i--) {
                    System.out.println("Got a message with id: " + responseObject.resultList.get(i).userId);
                    le.ndisplay = responseObject.resultList.get(i).nickname;
                    le.mdisplay = responseObject.resultList.get(i).message;
                    aList.add(le);
                }*/
            }

            @Override
            public void onFailure(Throwable t) {
                //TODO: check how Hw2 threw
                System.out.println("Request Failed");
            }
        });
    }
}
