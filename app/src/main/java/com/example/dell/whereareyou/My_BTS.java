package com.example.dell.whereareyou;

/**
 * Created by DELL on 2016/10/4.
 */

import java.util.Vector;

public class My_BTS {
    public String mName;
    public String mAddr;
    public Vector<Short> mRSSIVector;

    public My_BTS() {
        mName = new String();
        mAddr = new String();
        mRSSIVector = new Vector<Short>();
    }

    public My_BTS(String name, String addr) {
        mName = name;
        mAddr = addr;
        mRSSIVector = new Vector<Short>();
    }
}