package com.example.resturentpractice.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {
    private static final String BASE_URL ="http://192.168.0.188:8080";

    private static Retrofit.Builder builder
            = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();
    private static OkHttpClient.Builder httpClient
                = new OkHttpClient.Builder();

    public static <S> S createService(Class<S> serviceClass){
        return retrofit.create(serviceClass);
    }

}