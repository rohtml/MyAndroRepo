package com.vpapps.interfaces;

public interface OrderCancelListener {
    void onStart();
    void onEnd(String success, String message);
}