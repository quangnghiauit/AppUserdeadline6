package com.app.nqn.appstudent.model;

import javax.xml.transform.sax.SAXResult;

public class User {

    private int mID;
    private String mName;
    private String mEmai;
    private String mPhoneNumber;
    private enum Gender {
        MALE,
        FEMALE
    }

    public User(int mID, String mName, String mEmai, String mPhoneNumber) {
        this.mID = mID;
        this.mName = mName;
        this.mEmai = mEmai;
        this.mPhoneNumber = mPhoneNumber;
    }


    public User(String mName, String mEmai, String mPhoneNumber) {
        this.mName = mName;
        this.mEmai = mEmai;
        this.mPhoneNumber = mPhoneNumber;
    }

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmEmai() {
        return mEmai;
    }

    public void setmEmai(String mEmai) {
        this.mEmai = mEmai;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }
}
