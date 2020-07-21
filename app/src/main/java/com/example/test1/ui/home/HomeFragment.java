package com.example.test1.ui.home;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.test1.JHJApplication;
import com.example.test1.R;
import com.example.test1.ui.ServiceApi;
import com.example.test1.ui.atv.view.AndroidTreeView;
import com.example.test1.ui.atv.model.TreeNode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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

        ArrayList<myGroup> DataList = new ArrayList<myGroup>();
        listView = (ExpandableListView) root.findViewById(R.id.guestbook);

//        myGroup temp = new myGroup(new HomepageInfo(?,?,?,?,?));
//        temp.child.add("댓글");

//        ExpandAdapter adapter = new ExpandAdapter(getApplicationContext(),R.layout.)

        return root;
    }
}