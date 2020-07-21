package com.example.test1;

import android.app.Activity;
import android.os.Bundle;

import com.example.test1.ui.Data.HomeData;
import com.example.test1.ui.Data.HomeResponse;
import com.example.test1.ui.Data.UserResponse;
import com.example.test1.ui.RetrofitClient;
import com.example.test1.ui.ServiceApi;
import com.example.test1.ui.Data.JoinData;
import com.example.test1.ui.Data.JoinResponse;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends Activity {
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mNameView;
    private Button mJoinButton;
    private ProgressBar mProgressView;
    private ServiceApi service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.join_email);
        mPasswordView = (EditText) findViewById(R.id.join_password);
        mNameView = (EditText) findViewById(R.id.join_name);
        mJoinButton = (Button) findViewById(R.id.join_button);
        mProgressView = (ProgressBar) findViewById(R.id.join_progress);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        mJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptJoin();
            }
        });
    }

    private void attemptJoin() {
        mNameView.setError(null);
        mEmailView.setError(null);
        mPasswordView.setError(null);

        String name = mNameView.getText().toString();
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // 패스워드의 유효성 검사
        if (password.isEmpty()) {
            mPasswordView.setError("비밀번호를 입력해주세요.");
            focusView = mPasswordView;
            cancel = true;
        }
        else if (!isPasswordValid(password)) {
            mPasswordView.setError("6자 이상의 비밀번호를 입력해주세요.");
            focusView = mPasswordView;
            cancel = true;
        }

        // 이메일의 유효성 검사
        if (email.isEmpty()) {
            mEmailView.setError("이메일을 입력해주세요.");
            focusView = mEmailView;
            cancel = true;
        }
        else if (!isEmailValid(email)) {
            mEmailView.setError("@를 포함한 유효한 이메일을 입력해주세요.");
            focusView = mEmailView;
            cancel = true;
        }

        // 이름의 유효성 검사
        if (name.isEmpty()) {
            mNameView.setError("이름을 입력해주세요.");
            focusView = mNameView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        }
        else {
            startJoin(new JoinData(name,email,password));
            showProgress(true);
        }

        String str = name + email;
        Log.v("aaa", password );
        Log.v("bbb", name );
        Log.v("ccc", email );
    }

    private void startJoin(JoinData data) {
        service.joinCheck(data).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse result = response.body();
                if(result.getResult()==1){
                    service.userJoin(data).enqueue(new Callback<JoinResponse>() {
                        @Override
                        public void onResponse(Call<JoinResponse> call, Response<JoinResponse> response) {
                            JoinResponse result = response.body();
                            Toast.makeText(JoinActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                            showProgress(false);
                            if (result.getCode() == 200) {
                                service.hpAdd(new HomeData(data.getName(), data.getEmail(), " "," ")).enqueue(new Callback<HomeResponse>() {
                                    @Override
                                    public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                                        HomeResponse result = response.body();
                                        Toast.makeText(JoinActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                                        if (result.getCode() == 200) {
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<HomeResponse> call, Throwable t) {
                                        Toast.makeText(JoinActivity.this, "회원가입 에러 발생", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<JoinResponse> call, Throwable t) {
                            Toast.makeText(JoinActivity.this, "회원가입 에러 발생", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    Toast.makeText(JoinActivity.this, "이미 존재하는 닉네임 입니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(JoinActivity.this, "회원가입 에러 발생", Toast.LENGTH_SHORT).show();
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

}