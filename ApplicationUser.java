package com.xiaozhuo.pintugame.utils;


import android.app.Application;
import android.content.Context;

import com.umeng.socialize.PlatformConfig;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/3/14.
 */
public class ApplicationUser extends Application {
    public static Context mContext;
    @Override
    public void onCreate() {
        mContext = this;
        super.onCreate();
        //初始化友盟分享
        initUMentShare();
    }

    private void initUMentShare() {
        //我的
        PlatformConfig.setWeixin("wxd953fdd353a30fc8", "1027d9e8b16cbfb2bad592b981245c22");
        PlatformConfig.setQQZone("1105767857", "w08oC6XUy5HfZWdt");
        PlatformConfig.setSinaWeibo("3874167889", "08408c0716bd2d76bd7496a2a7971b17");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
