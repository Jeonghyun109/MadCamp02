package com.example.test1.ui.contact;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.test1.JHJApplication;
import com.example.test1.MainActivity;
import com.example.test1.R;
import com.example.test1.ui.Data.CommData;
import com.example.test1.ui.Data.ContactData;
import com.example.test1.ui.Data.ContactResponse;
import com.example.test1.ui.Data.HomeData;
import com.example.test1.ui.Data.HomeResponse;
import com.example.test1.ui.Data.VisitorData;
import com.example.test1.ui.RetrofitClient;
import com.example.test1.ui.ServiceApi;
import com.example.test1.ui.home.ChildInfo;
import com.example.test1.ui.home.ExpandAdapter;
import com.example.test1.ui.home.ParentInfo;
import com.example.test1.ui.home.ProfileInfo;
import com.example.test1.ui.home.myGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.zip.DataFormatException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisitHomepage extends Activity {
    private Context context;
    private ExpandableListView listView;
    private ServiceApi service;
    private ProfileInfo profileInfo;
    private ParentInfo parentInfo;
    private ChildInfo childInfo;
    private String content;
    private ArrayList<myGroup> DataList;
    private ArrayList<ParentInfo> ParentList;
    private ArrayList<ChildInfo> ChildList;

    private ImageView home_img;
    private TextView home_name;
    private TextView home_email;
    private TextView home_message;
    private ExpandAdapter adapter;

    ParentInfo p_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.visit_hompage);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        Calendar cal = Calendar.getInstance();
        //////////////////////////////
        showProfile(new HomeData(name));
        home_img = (ImageView)findViewById(R.id.visit_img);
        home_name = (TextView)findViewById(R.id.visit_name);
        home_email = (TextView)findViewById(R.id.visit_email);
        home_message = (TextView)findViewById(R.id.visit_message);

        ImageView img = (ImageView)findViewById(R.id.w_img);
        EditText text = (EditText)findViewById(R.id.w_message);
        Button button = (Button)findViewById(R.id.w_button);

        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // input창에 문자를 입력할 때마다 호출됨
                // search 메소드 호출
                content = text.getText().toString();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = formatter.format(cal.getTime());
                ////////////////////////////////
                if(content != null) addVisit(new VisitorData(name,null, content, MainActivity.name, MainActivity.id, time));
                else Toast.makeText(context, "방명록을 입력해주세요.", Toast.LENGTH_SHORT).show();
            }
        });


        DataList = new ArrayList<myGroup>();
        listView = (ExpandableListView) findViewById(R.id.visit_guestbook);
        ParentList=new ArrayList<>();
        ChildList=new ArrayList<>();

        adapter = new ExpandAdapter(getApplicationContext(),R.layout.guestbook_item,R.layout.reply_again,DataList);

        //////////////////////////////////////
        showBelow(new VisitorData(name));

    }

    private void showProfile(HomeData data) {
        service = RetrofitClient.getClient().create(ServiceApi.class);
        service.hpHost(data).enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                Log.d("ㅕㅕㅕㅕㅕㅕㅕㅕ", "이afs야");
                HomeResponse result = response.body();
                String nj="{\"Profile\":"+result.getJson()+"}";
                jsonParsing(nj);
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                Log.d("ㅕㅕㅕㅕㅕㅕㅕㅕ", "이거 아니야");
            }
        });
    }

    private void addVisit(VisitorData data) {
        service = RetrofitClient.getClient().create(ServiceApi.class);
        service.visitAdd(data).enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                HomeResponse result = response.body();
                Toast.makeText(VisitHomepage.this.getBaseContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                Log.d("ㅕㅕㅕㅕㅕㅕㅕㅕ", "이거 아니야");
            }
        });
    }

    private void showBelow(VisitorData data){
        service = RetrofitClient.getClient().create(ServiceApi.class);
        service.hpPost(data).enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                HomeResponse result = response.body();
                String nj="{\"Visitor\":"+result.getJson()+"}";
                jsonParsingArr(nj, "Visitor");

                for(int i=0; i<ParentList.size(); i++){
                    myGroup temp = new myGroup(ParentList.get(i));
                    Log.v("2냐",String.valueOf(ParentList.get(i).getB_number()));

                    p_info = ParentList.get(i);

                    service.hpComm(new CommData(p_info.getB_number())).enqueue(new Callback<HomeResponse>() {
                        @Override
                        public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                            Log.d("ㅕㅕㅕㅕ","이거 맞아");
                            HomeResponse result = response.body();

                            String nj="{\"Comment\":"+result.getJson()+"}";
                            Log.d("ㅕㅕㅕㅕ",nj);
                            jsonParsingArr(nj, "Comment");
                            for(int j=0; j<ChildList.size(); j++){
                                temp.child.add(ChildList.get(j));
                                p_info.setB_replies(p_info.getB_replies()+1);
                            }

                            DataList.add(temp);
                            Log.d("이거뭔데",String.valueOf(adapter));
                            listView.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(Call<HomeResponse> call, Throwable t) {
                            Log.d("ㅕㅕㅕㅕㅕㅕㅕㅕ", "이거 아니야");
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                Log.d("ㅕㅕㅕㅕㅕㅕㅕㅕ", "이거 아니야");
            }
        });
    }

    private void jsonParsing(String json){
        try{
            JSONObject jobj=new JSONObject(json);
            JSONArray jarray=jobj.getJSONArray("Profile");

            JSONObject pobj=jarray.getJSONObject(0);
            profileInfo=new ProfileInfo(pobj.getString("HPhoto"),pobj.getString("HName"),pobj.getString("HEmail"),pobj.getString("HComm"));

            //        Glide.with(context).load(profileInfo.getP_img()).into(home_img);
            home_name.setText(profileInfo.getP_name());
            home_email.setText(profileInfo.getP_email());
            home_message.setText(profileInfo.getP_message());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void jsonParsingArr(String json, String field){
        if(field.equals("Visitor")) ParentList.clear();
        ChildList.clear();

        try{
            JSONObject jobj=new JSONObject(json);
            JSONArray jarray=jobj.getJSONArray(field);

            for(int i=0; i<jarray.length(); i++){
                JSONObject pobj=jarray.getJSONObject(i);

                if(field.equals("Visitor")){
                    parentInfo=new ParentInfo(pobj.getInt("PostID"), pobj.getString("VisitName"), pobj.getString("Time"),pobj.getString("Photo"),pobj.getString("Content"),0);
                    ParentList.add(parentInfo);
                }
                else if(field.equals("Comment")){
                    childInfo=new ChildInfo(pobj.getString("CommName"), pobj.getString("Time"), pobj.getString("CommCont"));
                    ChildList.add(childInfo);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
