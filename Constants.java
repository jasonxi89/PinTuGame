package com.xiaozhuo.pintugame.utils;

import android.graphics.Bitmap;
import android.os.Environment;

import com.xiaozhuo.pintugame.R;

import java.io.File;

public class Constants {
    public static String SP_NAME = "sp_name";

    public final static String TAG = "TAG";
    public final static String TYPE = "TYPE";
    public final static String TAG_NAME = "TAG_NAME";
    public final static String TAG_RESULT = "TAG_RESULT";

    public final static String HAVE_KEY = "have_key";
    public final static String HAVE_KEY_YES = "have_key_yes";
    public final static String HAVE_KEY_NO = "have_key_no";
    public final static String GAME_PIE = "GAME_PIE";
    public final static String GAME_TIME = "GAME_TIME";

    public final static String GAME_USE_TIME = "GAME_USE_TIME";
    public final static String photoPath = Tools.getRootPath(ApplicationUser.mContext) + File.separator + "temp.jpg";


    public final static int SORT_SYSTEM = 1;
    public final static int SORT_PHOTO = 2;
    public static Bitmap bitmap;

    public final static int SORT_CITY = 0;
    public final static int SORT_PEOPLE = 1;
    public final static int SORT_SCENERY = 2;
    public final static int SORT_CAR = 3;

    //系统里面配置的城市类图片的id，其他类别同理
    public final static int[] citys=new int[]{
            R.mipmap.sort_city1,
            R.mipmap.a1
    };

    public final static int[] peoples=new int[]{
            R.mipmap.sort_people1,
            R.mipmap.a2
    };

    public final static int[] scenerys=new int[]{
            R.mipmap.sort_scenery1,
            R.mipmap.a3
    };

    public final static int[] cars=new int[]{
            R.mipmap.sort_car1,
            R.mipmap.a4
    };

    public final static int[] pieces=new int[]{2,4,5,6,7};
}
