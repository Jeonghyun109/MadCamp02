package com.example.test1.ui.home;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.test1.JHJApplication;
import com.example.test1.MainActivity;
import com.example.test1.R;
import com.example.test1.ui.Data.CommData;
import com.example.test1.ui.Data.HomeData;
import com.example.test1.ui.Data.HomeResponse;
import com.example.test1.ui.Data.VisitorData;
import com.example.test1.ui.RetrofitClient;
import com.example.test1.ui.ServiceApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    int id;
    private ServiceApi service;

    private ExpandableListView listView;

    private ContentResolver contentResolver;
    private Context context;

    private ProfileInfo profileInfo;
    private ParentInfo parentInfo;
    private ChildInfo childInfo;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        contentResolver = context.getContentResolver();

        id = ((JHJApplication)this.getActivity().getApplication()).getId();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        Display newDisplay = getActivity().getWindowManager().getDefaultDisplay();
        int width = newDisplay.getWidth();


        /////////////////////////////////
        showProfile(new HomeData(MainActivity.name));
        home_img = (ImageView)root.findViewById(R.id.home_img);
        home_name = (TextView)root.findViewById(R.id.home_name);
        home_email = (TextView)root.findViewById(R.id.home_email);
        home_message = (TextView)root.findViewById(R.id.home_message);



        DataList = new ArrayList<myGroup>();
        Log.v("정ㅎㄴ",String.valueOf(DataList));
        ParentList=new ArrayList<>();
        ChildList=new ArrayList<>();
        listView = (ExpandableListView) root.findViewById(R.id.guestbook);

        adapter = new ExpandAdapter(context.getApplicationContext(),R.layout.guestbook_item,R.layout.reply_again,DataList);

        listView.setIndicatorBounds(width-50, width);

        ///////////////////////////////////
        showBelow(new VisitorData(MainActivity.name));


        return root;
    }

    private void showProfile(HomeData data) {
        service = RetrofitClient.getClient().create(ServiceApi.class);
        Log.d("qqqqqqq", "ㅁㅁ");

        service.hpHost(data).enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                Log.d("qqqqqqq", "ㅁㅁㅁㅁ");
                HomeResponse result = response.body();
                Log.d("qqqqqqq", result.getJson());
                String nj="{\"Profile\":"+result.getJson()+"}";

                jsonParsing(nj);

                //        Glide.with(context).load(profileInfo.getP_img()).into(home_img);
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
            Log.d("wwwwwwww", pobj.getString("HName"));

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