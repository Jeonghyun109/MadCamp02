package com.example.test1.ui.home;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.os.TestLooperManager;
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
import com.example.test1.R;
import com.example.test1.ui.ServiceApi;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    int id;
    private ServiceApi service;

    private ExpandableListView listView;

    private ContentResolver contentResolver;
    private Context context;

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

        ProfileInfo profileInfo = new ProfileInfo(?,?,?,?);
        ImageView home_img = (ImageView)root.findViewById(R.id.home_img);
        TextView home_name = (TextView)root.findViewById(R.id.home_name);
        TextView home_email = (TextView)root.findViewById(R.id.home_email);
        TextView home_message = (TextView)root.findViewById(R.id.home_message);

        Glide.with(context).load(profileInfo.getP_img()).into(home_img);
        home_name.setText(profileInfo.getP_name());
        home_email.setText(profileInfo.getP_email());
        home_message.setText(profileInfo.getP_message());

        ArrayList<myGroup> DataList = new ArrayList<myGroup>();
        listView = (ExpandableListView) root.findViewById(R.id.guestbook);

        myGroup temp = new myGroup(new ParentInfo(?,?,?,?,?));
        temp.child.add("댓글");

        ExpandAdapter adapter = new ExpandAdapter(context.getApplicationContext(),R.layout.guestbook_item,R.layout.reply_again,DataList);
        listView.setAdapter(adapter);

        return root;
    }
}