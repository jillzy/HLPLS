package jy.com.finalproject.Interface;

import jy.com.finalproject.Models.Example;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jy on 3/2/2016.
 */
public interface GetAddressService {

    @GET("maps/api/geocode/json")
    Call<Example> path(@Query("latlng") String latlng);

}

