package com.sobot.demo;

import android.app.Application;

import com.sobot.chat.ZCSobotApi;
import com.sobot.chat.api.apiUtils.SobotBaseUrl;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ZCSobotApi.setShowDebug(true);
        SobotBaseUrl.setApi_Host("https://www.soboten.com");
        ZCSobotApi.initSobotSDK(this,"f7ad0c8bd0914edfb92ce7f6e0b24cac","");
    }
}
