package com.example.test1.ui.contact;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test1.JHJApplication;
import com.example.test1.LoginActivity;
import com.example.test1.MainActivity;
import com.example.test1.R;
import com.example.test1.ui.Data.ContactData;
import com.example.test1.ui.Data.ContactResponse;
import com.example.test1.ui.RetrofitClient;
import com.example.test1.ui.ServiceApi;
import com.example.test1.ui.gallery.GalleryViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactAdd extends Activity {
    private EditText mPhoneView;
    private EditText mNameView;
    private AutoCompleteTextView mEmailView;

    private ServiceApi service;
    private Context mContext;

    private GalleryViewModel contactViewModel;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_add);
        mContext = getApplicationContext();

        mEmailView = (AutoCompleteTextView) findViewById(R.id.addc_email);
        mPhoneView = (EditText) findViewById(R.id.addc_phone);
        mNameView = (EditText) findViewById(R.id.addc_name);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        add = (Button) findViewById(R.id.addc_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptAddC(view);
            }
        });
    }
    private void attemptAddC(View v) {
        mEmailView.setError(null);
        mPhoneView.setError(null);
        mNameView.setError(null);

        String email = mEmailView.getText().toString();
        String name = mNameView.getText().toString();
        String phone = mPhoneView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // 이름의 유효성 검사
        if (name.isEmpty()) {
            mNameView.setError("이름을 입력해주세요.");
            focusView = mNameView;
            cancel = true;
        } else if (!isNameValid(name)) {
            mNameView.setError("이름을 1자 이상 입력해주세요.");
            focusView = mNameView;
            cancel = true;
        }

        // 번호의 유효성 검사
        if (phone.isEmpty()) {
            mPhoneView.setError("전화번호를 입력해주세요.");
            focusView = mPhoneView;
            cancel = true;
        } else if (!isPhoneValid(phone)) {
            mPhoneView.setError("10자 이상의 전화번호를 입력해주세요.");
            focusView = mPhoneView;
            cancel = true;
        }

        // 이메일의 유효성 검사
        if (email.isEmpty()) {
            mEmailView.setError("이메일을 입력해주세요.");
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email) && email.length()!=0) {
            mEmailView.setError("@를 포함한 유효한 이메일을 입력해주세요.");
            focusView = mEmailView;
            cancel = true;
        }

        Log.e("aaa", name+phone+email);

        if (cancel) {
            focusView.requestFocus();
        } else {
            int id = ((JHJApplication)this.getApplication()).getId();
            addContact(new ContactData(id, name, phone, email));
        }
    }

    private void addContact(ContactData data) {
        service.contactAdd(data).enqueue(new Callback<ContactResponse>() {
            @Override
            public void onResponse(Call<ContactResponse> call, Response<ContactResponse> response) {
                ContactResponse result = response.body();
                Toast.makeText(ContactAdd.this, result.getMessage(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<ContactResponse> call, Throwable t) {
                Toast.makeText(ContactAdd.this, "연락처 추가 에러 발생", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPhoneValid(String phone) {
        return phone.length() >= 10;
    }

    private boolean isNameValid(String phone) {
        return phone.length() > 0;
    }
}
