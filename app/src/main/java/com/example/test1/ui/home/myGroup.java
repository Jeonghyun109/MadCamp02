package com.example.test1.ui.home;

import java.util.ArrayList;

public class myGroup {

    public ArrayList<ChildInfo> child;
    public ParentInfo parent;

    myGroup(ParentInfo profile) {
        child = new ArrayList<ChildInfo>();
        parent = profile;
    }
}
