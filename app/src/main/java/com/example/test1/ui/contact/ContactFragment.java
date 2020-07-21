package com.example.test1.ui.contact;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.test1.JHJApplication;
import com.example.test1.R;
import com.example.test1.ui.Data.ContactData;
import com.example.test1.ui.Data.ContactResponse;
import com.example.test1.ui.RetrofitClient;
import com.example.test1.ui.ServiceApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
                mAdapter = new ContactAdapter(context,mArrayList,ContactFragment.this);
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