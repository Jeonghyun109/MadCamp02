package com.example.test1.ui.contact;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.test1.R;
import com.example.test1.ui.home.ExpandAdapter;
import com.example.test1.ui.home.ParentInfo;
import com.example.test1.ui.home.ProfileInfo;
import com.example.test1.ui.home.myGroup;

import java.util.ArrayList;

public class VisitHomepage extends Activity {
    private Context context;
    private ExpandableListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.visit_hompage);
//
//        Intent intent = getIntent();
//        String name = intent.getStringExtra("name");
//
//
//        ProfileInfo profileInfo = new ProfileInfo(?,?,?,?);
//        ImageView home_img = (ImageView)findViewById(R.id.visit_img);
//        TextView home_name = (TextView)findViewById(R.id.visit_name);
//        TextView home_email = (TextView)findViewById(R.id.visit_email);
//        TextView home_message = (TextView)findViewById(R.id.visit_message);
//
//        Glide.with(context).load(profileInfo.getP_img()).into(home_img);
//        home_name.setText(profileInfo.getP_name());
//        home_email.setText(profileInfo.getP_email());
//        home_message.setText(profileInfo.getP_message());
//
//
//        ImageView img = (ImageView)findViewById(R.id.w_img);
//        EditText text = (EditText)findViewById(R.id.w_message);
//        Button button = (Button)findViewById(R.id.w_button);
//
//        text.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                // input창에 문자를 입력할 때마다 호출됨
//                // search 메소드 호출
//                String total_text = text.getText().toString();
//            }
//        });
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // add guestbook to DB!!!!!!!!!
//            }
//        });
//
//
//        ArrayList<myGroup> DataList = new ArrayList<myGroup>();
//        listView = (ExpandableListView) findViewById(R.id.visit_guestbook);
//
//        myGroup temp = new myGroup(new ParentInfo(?,?,?,?,?));
//        temp.child.add("댓글");
//
//        ExpandAdapter adapter = new ExpandAdapter(getApplicationContext(),R.layout.guestbook_item,R.layout.reply_again,DataList);
//        listView.setAdapter(adapter);

    }
}
