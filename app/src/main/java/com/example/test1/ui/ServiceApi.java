package com.example.test1.ui;

import com.example.test1.ui.Data.ContactData;
import com.example.test1.ui.Data.ContactResponse;
import com.example.test1.ui.Data.JoinData;
import com.example.test1.ui.Data.JoinResponse;
import com.example.test1.ui.Data.LoginData;
import com.example.test1.ui.Data.LoginResponse;
import com.example.test1.ui.Data.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceApi {
    @POST("/user/login")
    Call<LoginResponse> userLogin(@Body LoginData data);

    @POST("/user/join")
    Call<JoinResponse> userJoin(@Body JoinData data);

    @POST("/contact/add")
    Call<ContactResponse> contact(@Body ContactData data);

    @POST("/user/get")
    Call<UserResponse> userUser(@Body LoginData data);

    @POST("/user/logout")
    Call<LoginResponse> userLogout();
}