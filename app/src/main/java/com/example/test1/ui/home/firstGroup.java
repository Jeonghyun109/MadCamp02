package com.example.test1.ui.home;

import android.content.Context;
import android.widget.ExpandableListView;

import java.util.ArrayList;

public class firstGroup {
    public Context context;

    public ExpandableListView child;
    public ArrayList<ProfileInfo> parent;

    firstGroup() {
        child = new ExpandableListView(context);
        parent = new ArrayList<ProfileInfo>();
    }
}
