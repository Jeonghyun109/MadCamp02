package com.example.test1.ui;

import com.example.test1.ui.Data.JoinData;
import com.example.test1.ui.Data.JoinResponse;
import com.example.test1.ui.Data.LoginData;
import com.example.test1.ui.Data.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceApi {
    @POST("/user/login")
    Call<LoginResponse> userLogin(@Body LoginData data);

    @POST("/user/join")
    Call<JoinResponse> userJoin(@Body JoinData data);

}