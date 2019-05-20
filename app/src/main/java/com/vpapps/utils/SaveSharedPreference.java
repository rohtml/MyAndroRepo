package com.vpapps.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Sagar on 12-09-2018.
 */
public class SaveSharedPreference {

    private static final String USER_PREFS = "LOCKATED_PREFS";
    private SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;

    private String LoggerId="LoggerId";
    private String LoggerName="LoggerName";
    private String LoggerType="LoggerType";
    private String SocityId="SocityId";
    private String SocityName="SocityName";
    private String Mobile="Mobile";
    private String Profile="Profile";
    private String Passcode="Passcode";

    private String Username = "Username";
    private String Email = "Email";
    private String Password = "Password";

    private String wingsResponse = "wingsResponse";

    private String VisitorCount="VisitorCount";
    private String TotalCount="TotalCount";
    private String StaffCount="StaffCount";
    private String RespCount="RespCount";
    private String LanguageId="LanguageId";
    private String Vids="Vids";
    private String Subids="Subids";
    private String PinResponse="PinResponse";

    private String Pid="Pid";
    private String Pname="Pname";
    private String Pqty="Pqty";
    private String Prate="Prate";



    private String TokenId="TokenId";


    private static final String IS_LOGGEDIN= "IS_LOGGEDIN";

    public SaveSharedPreference(Context context) {
        this.appSharedPrefs = context.getSharedPreferences(USER_PREFS, Activity.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
    }

    public String getPinResponse() {
        return appSharedPrefs.getString(PinResponse, "");
    }

    public void setPinResponse(String strPinResponse) {
        prefsEditor.putString(PinResponse, strPinResponse).commit();
    }



    public String getLoggerId() {
        return appSharedPrefs.getString(LoggerId, "");
    }

    public void setLoggerId(String strLoggerId) {
        prefsEditor.putString(LoggerId, strLoggerId).commit();
    }

    public String getLoggerName() {
        return appSharedPrefs.getString(LoggerName, "blank");
    }

    public void setLoggerName(String strLoggerName) {
        prefsEditor.putString(LoggerName, strLoggerName).commit();
    }

    public String getMobile() {
        return appSharedPrefs.getString(Mobile, "");
    }

    public void setMobile(String strMobile) {
        prefsEditor.putString(Mobile, strMobile).commit();
    }

    public String getProfile() {
        return appSharedPrefs.getString(Profile, "blank");
    }

    public void setProfile(String strProfile) {
        prefsEditor.putString(Profile, strProfile).commit();
    }

    public String getPasscode() {
        return appSharedPrefs.getString(Passcode, "blank");
    }

    public void setPasscode(String strPasscode) {
        prefsEditor.putString(Passcode, strPasscode).commit();
    }

    public String getSocityId() {
        return appSharedPrefs.getString(SocityId,"");
    }

    public void setSocityId(String strSocityId) {
        prefsEditor.putString(SocityId, strSocityId).commit();
    }
    public String getSocityName() {
        return appSharedPrefs.getString(SocityName, "blank");
    }

    public void setSocityName(String strSocityName) {
        prefsEditor.putString(SocityName, strSocityName).commit();
    }

    public String getUsername() {
        return appSharedPrefs.getString(Username, "blank");
    }

    public void setUsername(String strUsername) {
        prefsEditor.putString(Username, strUsername).commit();
    }

    public String getEmail() {
        return appSharedPrefs.getString(Email, "blank");
    }

    public void setEmail(String strEmail) {
        prefsEditor.putString(Email, strEmail).commit();
    }

    public String getLoggerType() {
        return appSharedPrefs.getString(LoggerType, "blank");
    }

    public void setLoggerType(String strType) {
        prefsEditor.putString(LoggerType, strType).commit();
    }



    public void setIsLoggedin(boolean isFirstTime) {
        prefsEditor.putBoolean(IS_LOGGEDIN, isFirstTime).commit();
    }

    public boolean isLoggedIn() {
        return appSharedPrefs.getBoolean(IS_LOGGEDIN, false);
    }

    public void setWingsResponse(String wingsRes) {
        prefsEditor.putString(wingsResponse, wingsRes).commit();
    }

    public String getWingsResponse() {
        return appSharedPrefs.getString(wingsResponse, "blank");
    }

    public String getVisitorCount() {
        return appSharedPrefs.getString(VisitorCount, " ");
    }

    public void setVisitorCount(String visitorCount) {
        prefsEditor.putString(VisitorCount, visitorCount).commit();
    }


    public String getTotalCount() {
        return appSharedPrefs.getString(TotalCount, " ");
    }

    public void setTotalCount(String totalCount) {
        prefsEditor.putString(TotalCount, totalCount).commit();
    }


    public String getStaffCount() {
        return appSharedPrefs.getString(StaffCount, " ");
    }

    public void setStaffCount(String staffCount) {
        prefsEditor.putString(StaffCount, staffCount).commit();
    }

    public String getRespCount() {
        return appSharedPrefs.getString(RespCount, " ");
    }

    public void setRespCount(String respCount) {
        prefsEditor.putString(RespCount, respCount).commit();
    }

    public String getTokenId() {
        return appSharedPrefs.getString(TokenId, "");
    }

    public void setTokenId(String tokenId) {
        prefsEditor.putString(TokenId, tokenId).commit();
    }


    public String getLanguageId() {
        return appSharedPrefs.getString(LanguageId, "");
    }

    public void setLanguageId(String languageId) {
        prefsEditor.putString(LanguageId, languageId).commit();
    }

    public String getVillageId() {
        return appSharedPrefs.getString(Vids, "");
    }

    public void setVillageId(String vids) {
        prefsEditor.putString(Vids, vids).commit();
    }

    public String getSubCatId() {
        return appSharedPrefs.getString(Subids, "");
    }

    public void setSubCatId(String strSubids) {
        prefsEditor.putString(Subids, strSubids).commit();
    }


    public String getPId() {
        return appSharedPrefs.getString(Pid, "");
    }

    public void setPId(String pid) {
        prefsEditor.putString(Pid, pid).commit();
    }

    public String getPname() {
        return appSharedPrefs.getString(Pname, "");
    }

    public void setPname(String pname) {
        prefsEditor.putString(Pname, pname).commit();
    }




    public String getPqty() {
        return appSharedPrefs.getString(Pqty, "");
    }

    public void setPqty(String pqty) {
        prefsEditor.putString(Pqty, pqty).commit();
    }


    public String getPrate() {
        return appSharedPrefs.getString(Prate, "");
    }

    public void setPrate(String prate) {
        prefsEditor.putString(Prate, prate).commit();
    }

}
