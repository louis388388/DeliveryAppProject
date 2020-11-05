package com.example.deliverylist.data.Interface;

import com.example.deliverylist.data.Entity.Entity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    //取得多筆資料
    @GET("/v2/deliveries")
    Call<List<Entity>> getPosts(
            @Query("offset") int offset,
            @Query("limit") int limit
    );

}
