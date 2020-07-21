package com.example.test1.ui.home;

import android.content.Context;
import android.widget.ExpandableListView;

import java.util.ArrayList;

public class myGroup {

    public ArrayList<String> child;
    public HomepageInfo  parent;

    myGroup(HomepageInfo profile) {
        child = new ArrayList<String>();
        parent = profile;
    }
}
