package com.example.test1.ui;

import com.example.test1.ui.Data.CommData;
import com.example.test1.ui.Data.ContactData;
import com.example.test1.ui.Data.ContactResponse;
import com.example.test1.ui.Data.GalleryData;
import com.example.test1.ui.Data.GalleryResponse;
import com.example.test1.ui.Data.HomeData;
import com.example.test1.ui.Data.HomeResponse;
import com.example.test1.ui.Data.ImgResponse;
import com.example.test1.ui.Data.JoinData;
import com.example.test1.ui.Data.JoinResponse;
import com.example.test1.ui.Data.LoginData;
import com.example.test1.ui.Data.LoginResponse;
import com.example.test1.ui.Data.UserResponse;
import com.example.test1.ui.Data.VisitorData;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ServiceApi {
    @POST("/user/login")
    Call<LoginResponse> userLogin(@Body LoginData data);

    @POST("/user/join")
    Call<JoinResponse> userJoin(@Body JoinData data);

    @POST("/contact")
    Call<ContactResponse> contact(@Body ContactData data);

    @POST("/contact/add")
    Call<ContactResponse> contactAdd(@Body ContactData data);

    @POST("/contact/delete")
    Call<ContactResponse> contactDelete(@Body ContactData data);

    @POST("/user/get")
    Call<UserResponse> userUser(@Body LoginData data);

    @POST("/check/add")
    Call<UserResponse> addCheck(@Body ContactData data);

    @POST("/check/join")
    Call<UserResponse> joinCheck(@Body JoinData data);

    @POST("/user/logout")
    Call<LoginResponse> userLogout();

    @POST("/photo/add")
    Call<GalleryResponse> photoAdd(@Body GalleryData data);

    @POST("/photo/delete")
    Call<GalleryResponse> photoDelete(@Body GalleryData data);

    @POST("/photo")
    Call<GalleryResponse> photo(@Body GalleryData data);

    @POST("/hp/host")
    Call<HomeResponse> hpHost(@Body HomeData data);

    @POST("/hp/post")
    Call<HomeResponse> hpPost(@Body VisitorData data);

    @POST("/hp/comm")
    Call<HomeResponse> hpComm(@Body CommData data);

    @POST("/comm/add")
    Call<HomeResponse> commAdd(@Body CommData data);

    @POST("/comm/delete/single")
    Call<HomeResponse> commDSingle(@Body CommData data);

    @POST("/comm/delete/all")
    Call<HomeResponse> commDAll(@Body CommData data);

    @POST("/visit/add")
    Call<HomeResponse> visitAdd(@Body VisitorData data);

    @POST("/visit/delete")
    Call<HomeResponse> visitDelete(@Body VisitorData data);

    @POST("/modify/msg")
    Call<HomeResponse> modifyMsg(@Body HomeData data);

    //cyworld
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