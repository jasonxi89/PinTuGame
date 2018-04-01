package com.xiaozhuo.pintugame.utils;

import android.os.Environment;

public class SDCardUtils {
    public static boolean isMounted() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            // sd card 可用
            return true;
        } else {
// 当前不可用
            return false;
        }
    }
}
