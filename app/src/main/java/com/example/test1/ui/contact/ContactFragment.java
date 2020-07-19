package com.example.test1.ui.contact;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.provider.ContactsContract;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.test1.JHJApplication;
import com.example.test1.LoginActivity;
import com.example.test1.MainActivity;
import com.example.test1.R;
import com.example.test1.ui.Data.ContactData;
import com.example.test1.ui.Data.ContactResponse;
import com.example.test1.ui.Data.LoginData;
import com.example.test1.ui.Data.LoginResponse;
import com.example.test1.ui.LoginCallback;
import com.example.test1.ui.RetrofitClient;
import com.example.test1.ui.ServiceApi;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactFragment extends Fragment {

    private Button add;
    private ServiceApi service;

    private ArrayList<ContactInfo> mArrayList = new ArrayList<ContactInfo>();
    private ContactAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ContentResolver contentResolver;
    private Context context;
    private RecyclerView recyclerView;
    private EditText editSearch;
    private ArrayList<ContactInfo> list;
    private ArrayList<ContactInfo> arraylist;
    private ContactAdapter adapter;
    private LinearLayoutManager layoutManager;
    int id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        mArrayList = new ArrayList<>();


        contentResolver = getActivity().getContentResolver();

        id = ((JHJApplication)this.getActivity().getApplication()).getId();
        showContact(new ContactData(id));
//        Log.v("존나",String.valueOf(mArrayList.size()));
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_contact, container, false);

        mLayoutManager = new LinearLayoutManager(getActivity());
        /* Init mRecyclerView */
        mRecyclerView = root.findViewById(R.id.recyclerview_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        final SwipeRefreshLayout pullToRefresh = root.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable(){
                    @Override
                    public void run(){
                        showContact(new ContactData(id));
                    }
                }
                ).start();
                //updateData();
                pullToRefresh.setRefreshing(false);
            }
        });

        add = (Button) root.findViewById(R.id.go_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), ContactAdd.class);
                startActivity(intent);
            }
        });

        showContact(new ContactData(id));

        return root;
    }

    private void showContact(ContactData data) {
//        mArrayList.clear();

        service = RetrofitClient.getClient().create(ServiceApi.class);
        service.contact(data).enqueue(new Callback<ContactResponse>() {
            @Override
            public void onResponse(Call<ContactResponse> call, Response<ContactResponse> response) {
                ContactResponse result = response.body();
                String nj="{\"Contact\":"+result.getJson()+"}";
                mAdapter = new ContactAdapter(context,mArrayList);
                mRecyclerView.setAdapter(mAdapter);
                jsonParsing(nj);
            }

            @Override
            public void onFailure(Call<ContactResponse> call, Throwable t) {
                Log.d("ㅕㅕㅕㅕㅕㅕㅕㅕ", "이거 아니야");
            }
        });
    }

    private void jsonParsing(String json){
        mArrayList.clear();
        try{
            JSONObject jobj=new JSONObject(json);
            JSONArray jarray=jobj.getJSONArray("Contact");

            Log.d("제이슨",String.valueOf(jarray.length()));

            for(int i=0; i<jarray.length(); i++){
                JSONObject pobj=jarray.getJSONObject(i);
                Log.d("qqqqqqqqqq", pobj.toString());
                ContactInfo c=new ContactInfo(pobj.getString("CName"),pobj.getString("CNumber"),pobj.getString("CEmail"));

                mArrayList.add(c);
                Log.d("qqqqqqqqqq", pobj.getString("CName"));
                Log.d("qqqqqqqqqq", String.valueOf(mArrayList.size()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mAdapter.notifyDataSetChanged();
    }
}