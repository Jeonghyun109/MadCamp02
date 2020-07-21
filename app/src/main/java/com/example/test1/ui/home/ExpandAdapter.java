package com.example.test1.ui.home;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.test1.R;

import java.util.ArrayList;

public class ExpandAdapter extends BaseExpandableListAdapter {
    private Context context;
    private int groupLayout = 0;
    private int chlidLayout = 0;
    private ArrayList<myGroup> DataList;
    private LayoutInflater myinf = null;

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

        // parent의 view 정해주기
        TextView parentId = (TextView)convertView.findViewById(R.id.b_number);
        TextView parentName = (TextView)convertView.findViewById(R.id.b_name);
        TextView parentTime = (TextView)convertView.findViewById(R.id.t_stamp);
        ImageView parentImg = (ImageView) convertView.findViewById(R.id.b_photo);
        TextView parentContent = (TextView)convertView.findViewById(R.id.b_content);
        TextView parentReplies = (TextView)convertView.findViewById(R.id.replies);

        parentId.setText(String.valueOf(DataList.get(groupPosition).parent.getB_number()));
        parentName.setText(DataList.get(groupPosition).parent.getB_Name());
        parentTime.setText(DataList.get(groupPosition).parent.getTimestamp());
        Glide.with(context).load(DataList.get(groupPosition).parent.getImg_uri()).into(parentImg);
        parentContent.setText(DataList.get(groupPosition).parent.getB_content());
        parentReplies.setText(String.valueOf(DataList.get(groupPosition).parent.getB_replies()));


        EditText text = (EditText)convertView.findViewById(R.id.b_edit);
        Button button = (Button)convertView.findViewById(R.id.b_button);

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
                String total_text = text.getText().toString();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add comment to DB!!!!!!!!!
            }
        });


        return convertView;
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