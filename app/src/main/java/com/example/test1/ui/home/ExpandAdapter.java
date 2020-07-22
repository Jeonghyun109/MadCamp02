package com.example.test1.ui.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
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
import com.example.test1.ui.contact.VisitHomepage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpandAdapter extends BaseExpandableListAdapter {
    private Context context;
    private int groupLayout = 0;
    private int chlidLayout = 0;
    private ArrayList<myGroup> DataList;
    private LayoutInflater myinf = null;
    private ServiceApi service;
    private String content;

    public ExpandAdapter(Context context,int groupLay,int chlidLay,ArrayList<myGroup> DataList){
        this.DataList = DataList;
        this.groupLayout = groupLay;
        this.chlidLayout = chlidLay;
        this.context = context;
        this.myinf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = myinf.inflate(this.groupLayout, parent, false);
        }

        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        Calendar cal = Calendar.getInstance();

        // parent의 view 정해주기
        TextView parentId = (TextView)convertView.findViewById(R.id.b_number);//
        TextView parentName = (TextView)convertView.findViewById(R.id.b_name);
        TextView parentTime = (TextView)convertView.findViewById(R.id.t_stamp);
        ImageView parentImg = (ImageView) convertView.findViewById(R.id.b_photo);
        TextView parentContent = (TextView)convertView.findViewById(R.id.b_content);
        TextView parentReplies = (TextView)convertView.findViewById(R.id.replies);

        Log.d("kkkkkkk", String.valueOf(DataList.get(groupPosition).parent.getB_number()));
//        parentId.setText(String.valueOf(DataList.get(groupPosition).parent.getB_number()));
        parentId.setText("No."+String.valueOf(DataList.get(groupPosition).parent.getB_cnt()));
        parentName.setText(DataList.get(groupPosition).parent.getB_Name());
        parentTime.setText(DataList.get(groupPosition).parent.getTimestamp());
        Glide.with(context).load(DataList.get(groupPosition).parent.getImg_uri()).into(parentImg);
        parentContent.setText(DataList.get(groupPosition).parent.getB_content());
        parentReplies.setText(String.valueOf(DataList.get(groupPosition).parent.getB_replies()));

        parentContent.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder chg=new AlertDialog.Builder(parent.getContext(), R.style.AlertDialog);
                chg.setTitle("골라라 냥!");
                chg.setMessage("무엇을 하겠냥?");

                chg.setNegativeButton("댓글 입력!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder add = new AlertDialog.Builder(parent.getContext());
                        Log.d("aaaaaa", String.valueOf(add));
                        add.setTitle("댓글!");
                        add.setMessage("달아봐라 냥!");

                        final EditText et = new EditText(parent.getContext());
                        add.setView(et);

                        add.setPositiveButton("입력 꾹꾹!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String value = et.getText().toString();
                                String time = formatter.format(cal.getTime());
                                addComm(new CommData(DataList.get(groupPosition).parent.getB_number(), MainActivity.name, value, time));
                            }
                        });
                        add.show();
                    }
                });

                chg.setPositiveButton("삭제!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Log.d("cccccccccc", MainActivity.name+"  "+DataList.get(groupPosition).parent.getB_Name());
                        service = RetrofitClient.getClient().create(ServiceApi.class);
                        service.commDAll(new CommData(DataList.get(groupPosition).parent.getB_number())).enqueue(new Callback<HomeResponse>() {
                            @Override
                            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                                HomeResponse result = response.body();
                                service.visitDelete(new VisitorData(DataList.get(groupPosition).parent.getB_Name(), DataList.get(groupPosition).parent.getTimestamp())).enqueue(new Callback<HomeResponse>() {
                                    @Override
                                    public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                                        HomeResponse result = response.body();

                                    }

                                    @Override
                                    public void onFailure(Call<HomeResponse> call, Throwable t) {
                                        Log.d("ㅕㅕㅕㅕㅕㅕㅕㅕ", "이거 아니야");
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<HomeResponse> call, Throwable t) {
                                Log.d("ㅕㅕㅕㅕㅕㅕㅕㅕ", "이거 아니야");
                            }
                        });
                    }
                });
                chg.create().show();

                return false;
            }
        });

        return convertView;
    }

    private void addComm(CommData data) {
        service = RetrofitClient.getClient().create(ServiceApi.class);
        service.commAdd(data).enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                HomeResponse result = response.body();
                Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                Log.d("ㅕㅕㅕㅕㅕㅕㅕㅕ", "이거 아니야");
            }
        });
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = myinf.inflate(this.chlidLayout, parent, false);
        }

        // child의 view 정해주기
        TextView childName = (TextView)convertView.findViewById(R.id.reply_name);
        TextView childTime = (TextView)convertView.findViewById(R.id.reply_time);
        TextView childMessage = (TextView)convertView.findViewById(R.id.reply_message);

        childName.setText(DataList.get(groupPosition).child.get(childPosition).getR_name());
        childTime.setText(DataList.get(groupPosition).child.get(childPosition).getR_timestamp());
        childMessage.setText(DataList.get(groupPosition).child.get(childPosition).getR_content());

        childTime.setOnLongClickListener(new AdapterView.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.d("aaaaaa", "aaaaa");
                service = RetrofitClient.getClient().create(ServiceApi.class);
                service.commDSingle(new CommData(DataList.get(groupPosition).child.get(childPosition).getR_name(), DataList.get(groupPosition).child.get(childPosition).getR_timestamp())).enqueue(new Callback<HomeResponse>() {
                    @Override
                    public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                        Toast.makeText(context, "삭제했다 냥!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<HomeResponse> call, Throwable t) {
                        Log.d("ㅕㅕㅕㅕㅕㅕㅕㅕ", "이거 아니야");
                    }
                });
//                AlertDialog.Builder chg=new AlertDialog.Builder(context, R.style.AlertDialog);
//                chg.setTitle("골라라 냥!");
//                chg.setMessage("무엇을 하겠냥?");
//
//                chg.setNegativeButton("댓글 입력!", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        AlertDialog.Builder add = new AlertDialog.Builder(context, R.style.AlertDialog);
//                        add.setTitle("삭제!");
//                        add.setMessage("할거냥?");
//
//                        add.setNegativeButton("맞다냥!", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                service = RetrofitClient.getClient().create(ServiceApi.class);
//                                service.commDSingle(new CommData(DataList.get(groupPosition).child.get(childPosition).getR_name(), DataList.get(groupPosition).child.get(childPosition).getR_timestamp())).enqueue(new Callback<HomeResponse>() {
//                                    @Override
//                                    public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
//                                        Toast.makeText(context, "삭제했다 냥!", Toast.LENGTH_SHORT).show();
//                                    }
//
//                                    @Override
//                                    public void onFailure(Call<HomeResponse> call, Throwable t) {
//                                        Log.d("ㅕㅕㅕㅕㅕㅕㅕㅕ", "이거 아니야");
//                                    }
//                                });
//                            }
//                        });
//                        add.setPositiveButton("아니다냥!", new DialogInterface.OnClickListener(){
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                            }
//                        });
//                        add.create().show();
//                    }
//                });
                return false;
            }
        });

        return convertView;
    }
    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return DataList.get(groupPosition).child.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return DataList.get(groupPosition).child.size();
    }

    @Override
    public myGroup getGroup(int groupPosition) {
        return DataList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return DataList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
}