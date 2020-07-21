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

    private ExpandableListView expandableListView;

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

        TreeNode Root = TreeNode.root();

        TreeNode parent = new TreeNode("MyParentNode");
        TreeNode child0 = new TreeNode("ChildNode0");
        TreeNode child1 = new TreeNode("ChildNode1");
        parent.addChildren(child0, child1);
        root.addChild(parent);

        AndroidTreeView tView = new AndroidTreeView(getActivity(), root);
        containerView.addView(tView.getView());

        IconTreeItem nodeItem = new IconTreeItem();
        TreeNode child1 = new TreeNode(nodeItem).setViewHolder(new MyHolder(mContext));

        TreeNode.setClickListener(new TreeNodeClickListener() {

            @Override
            public void onClick(TreeNode node, Object value) {
                //Your Code Here//

            }
        });

        LinkedHashMap<String, String[]> thirdLevelq1 = new LinkedHashMap<>();
        LinkedHashMap<String, String[]> thirdLevelq2 = new LinkedHashMap<>();
        LinkedHashMap<String, String[]> thirdLevelq3 = new LinkedHashMap<>();
        /**
         * Second level array list
         */
        List<String[]> secondLevel = new ArrayList<>();
        /**
         * Inner level data
         */
        List<LinkedHashMap<String, String[]>> data = new ArrayList<>();

        root.setUpAdapter();

        return root;
    }
}