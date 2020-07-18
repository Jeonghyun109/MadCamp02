package com.example.test1.ui;

import com.example.test1.ui.Data.ContactData;
import com.example.test1.ui.Data.ContactResponse;
import com.example.test1.ui.Data.ImgResponse;
import com.example.test1.ui.Data.JoinData;
import com.example.test1.ui.Data.JoinResponse;
import com.example.test1.ui.Data.LoginData;
import com.example.test1.ui.Data.LoginResponse;
import com.example.test1.ui.Data.UserResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

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

    @Multipart
    @POST("/user/upload_img")
    Call<ImgResponse> UploadImg(@Header("accessToken") String header, @Part MultipartBody.Part imageFile);
}
//    public void uploadImage(File file) {
//        // create multipart
//        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
//
//        // upload
//        getViewInteractor().showProfileUploadingProgress();
//
//        Observable<Response<String>> observable = api.uploadPhoto("", body);
//
//        // on Response
//        subscribeForNetwork(observable, new ApiObserver<Response<String>>() {
//            @Override
//            public void onError(Throwable e) {
//                getViewInteractor().hideProfileUploadingProgress();
//            }
//
//            @Override
//            public void onResponse(Response<String> response) {
//
//                if (response.code() != 200) {
//                    Timber.d("error " + response.code());
//                    return;
//                }
//                getViewInteractor().hideProfileUploadingProgress();
//                getViewInteractor().onProfileImageUploadSuccess(response.body());
//
//            }
//        });
//    }