package com.example.resturentpractice.service;

import com.example.resturentpractice.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {

    @POST("/user/add")
    public Call<User> add(@Body User user);

    @GET("/user/list")
    public Call<List<User>> getAll();

    @GET("/user/one/{id}")
    public Call<User> getById(@Path("id") long id);

    @GET("/user/delete/{id}")
    public Call<User> deleteById(@Path("id") long id);

    @POST("/user/login")
    public Call<User> login(@Body User user);

}
