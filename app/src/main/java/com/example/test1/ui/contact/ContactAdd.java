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
import com.example.test1.ui.Data.UserResponse;
import com.example.test1.ui.RetrofitClient;
import com.example.test1.ui.ServiceApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactAdd extends Activity {
    private EditText mPhoneView;
    private EditText mNameView;
    private AutoCompleteTextView mEmailView;

    private ServiceApi service;
    private Context mContext;
    private Button add;

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_add);
        mContext = getApplicationContext();

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
        mNameView.setError(null);

        String name = mNameView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        int id = ((JHJApplication)this.getApplication()).getId();
        String myName = ((JHJApplication)this.getApplication()).getName();
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
        else if(name.equals(myName)){
            mNameView.setError("본인 외 이름을 입력해주세요.");
            focusView = mNameView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            addContact(new ContactData(id, name));
        }
    }


    private void addContact(ContactData data) {
        service.addCheck(data).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse result1 = response.body();
                //json으로 받은 거 이메일 변수에 저장해야 함

                if(result1.getResult()==1){
                    String nj="{\"Contact\":["+result1.getJson()+"]}";
                    jsonParsing(nj);
                    ServiceApi mservice = RetrofitClient.getClient().create(ServiceApi.class);
                    mservice.contactAdd(new ContactData(data.getId(), data.getName(), email)).enqueue(new Callback<ContactResponse>() {
                        @Override
                        public void onResponse(Call<ContactResponse> call, Response<ContactResponse> response) {
                            ContactResponse result2 = response.body();
                            Toast.makeText(ContactAdd.this, result2.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onFailure(Call<ContactResponse> call, Throwable t) {
                            Toast.makeText(ContactAdd.this, "연락처 추가 에러 발생", Toast.LENGTH_SHORT).show();
                        }
                    });
                    finish();
                }
                else{
                    Toast.makeText(ContactAdd.this, "존재하지 않는 사용자 입니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(ContactAdd.this, "연락처 추가 에러 발생", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isNameValid(String phone) {
        return phone.length() > 0;
    }

    private void jsonParsing(String json){
        try{
            Log.d("ttttt",json);
            JSONObject jobj=new JSONObject(json);
            JSONArray jarray=jobj.getJSONArray("Contact");
//            Log.d("ttttt", String.valueOf(jarray.length()));

            JSONObject pobj=jarray.getJSONObject(0);

            email=pobj.getString("UserEmail");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
