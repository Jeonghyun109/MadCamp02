package com.example.test1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;

import com.example.test1.ui.Data.UserResponse;
import com.example.test1.ui.LoginCallback;
import com.example.test1.ui.RetrofitClient;
import com.example.test1.ui.ServiceApi;
import com.example.test1.ui.Data.LoginData;
import com.example.test1.ui.Data.LoginResponse;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.se.omapi.Session;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.security.MessageDigest;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private Button mEmailLoginButton;
    private Button mJoinButton;
    private ProgressBar mProgressView;
    private ServiceApi service;
    private Context mContext;
    private Button btn_custom_login;
    private Button btn_custom_logout;
    private LoginCallback mLoginCallback;
    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = getApplicationContext();
        mCallbackManager = CallbackManager.Factory.create();
        btn_custom_login = (Button) findViewById(R.id.btn_custom_login);
        btn_custom_logout = (Button) findViewById(R.id.btn_custom_logout);

        btn_custom_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager loginManager = LoginManager.getInstance();
                loginManager.logInWithReadPermissions(LoginActivity.this,
                        Arrays.asList("public_profile", "email"));
                loginManager.registerCallback(mCallbackManager, mLoginCallback);
            }
        });

        btn_custom_logout = (Button) findViewById(R.id.btn_custom_logout);
        btn_custom_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager ologinManager = LoginManager.getInstance();
                disconnectFromFacebook();
//                ologinManager.logOut();

                btn_custom_logout.setVisibility(View.INVISIBLE);
            }
        });

        mEmailView = (AutoCompleteTextView) findViewById(R.id.login_email);
        mPasswordView = (EditText) findViewById(R.id.login_password);
        mEmailLoginButton = (Button) findViewById(R.id.login_button);
        mJoinButton = (Button) findViewById(R.id.join_button);
        mProgressView = (ProgressBar) findViewById(R.id.login_progress);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        mEmailLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin(view);
            }
        });
        mJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("hey","I'm here");
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
            }
        });


    }

    private void attemptLogin(View v) {
        mEmailView.setError(null);
        mPasswordView.setError(null);

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // 패스워드의 유효성 검사
        if (password.isEmpty()) {
            mEmailView.setError("비밀번호를 입력해주세요.");
            focusView = mEmailView;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            mPasswordView.setError("6자 이상의 비밀번호를 입력해주세요.");
            focusView = mPasswordView;
            cancel = true;
        }

        // 이메일의 유효성 검사
        if (email.isEmpty()) {
            mEmailView.setError("이메일을 입력해주세요.");
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError("@를 포함한 유효한 이메일을 입력해주세요.");
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            startLogin(new LoginData(email, password));
            showProgress(true);
        }
    }

    private void startLogin(LoginData data) {
        service.userLogin(data).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse result = response.body();
                Toast.makeText(LoginActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                showProgress(false);

                ServiceApi mservice = RetrofitClient.getClient().create(ServiceApi.class);
                mservice.userUser(data).enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        UserResponse result2 = response.body();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("UserID", result2.getID());
                        intent.putExtra("Useremail", result2.getEmail());
                        intent.putExtra("Username", result2.getName());
                        startActivity(intent);

                    }
                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Log.e("이거 또 틀렸대",t.getMessage());
                    }
                });
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("로그인 에러 발생", t.getMessage());
                showProgress(false);
            }
        });
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

    private void showProgress(boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        Log.v("이거야",String.valueOf(requestCode));
        Log.v("결과야",String.valueOf(resultCode));
        Log.v("데이터야",String.valueOf(data));

        if (resultCode == -1) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            UserResponse result2 = new LoginCallback().requestMe();
//            intent.putExtra("Useremail", result2.getEmail());
//            intent.putExtra("Username", result2.getName());
            startActivity(intent);
        }
        if (resultCode == 0) {
            btn_custom_logout.setVisibility(View.VISIBLE);
            disconnectFromFacebook();
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    public void disconnectFromFacebook() {

        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                ///
                FacebookSdk.sdkInitialize(getApplicationContext());
                LoginManager.getInstance().setLoginBehavior(LoginBehavior.WEB_ONLY);
                ///

            }
        }).executeAsync();
    }
}