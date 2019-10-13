package com.project.ridobiko.API;

import com.project.ridobiko.RESPONSES.BikeResponse;
import com.project.ridobiko.RESPONSES.BookResponse;
import com.project.ridobiko.RESPONSES.CityResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @GET("cityNames.php")
    Call<CityResponse> getCities();

    @FormUrlEncoded
    @POST("bikeDetails.php")
    Call<BikeResponse> bikeDetails(
            @Field("cityName") String cityName,
            @Field("bookStart") String bookStart,
            @Field("bookEnd") String bookEnd
    );

    @FormUrlEncoded
    @POST("bookBike.php")
    Call<BookResponse> bookBike(
            @Field("bikeStart") String bikeStart,
            @Field("bikeEnd") String bikeEnd,
            @Field("bikeID") String bikeID
    );
}
