package com.xiaozhuo.pintugame.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Tools {
    //把view转化为图片
    public static Bitmap getBitmapFromView(View view) {
        view.destroyDrawingCache();
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache(true);
        return bitmap;
    }


    //检查应用是否含有某个权限
    public static boolean isGetPermission(Context context, String permission) {
        PackageManager pm = context.getPackageManager();
        boolean isGet = (PackageManager.PERMISSION_GRANTED == pm.checkPermission(permission, context.getPackageName()));
        return isGet;
    }

    public static void startActivity(Activity nowActivity, Class<?> desClass) {
        Intent intent = new Intent(nowActivity, desClass);
        nowActivity.startActivity(intent);
    }

    //根据距离来判断单位和保留的小数
    public static String getFormatLength(double lenMeter, boolean isKmType) {

        if (lenMeter > 1000) {
            float dis = (float) lenMeter / 1000;
            DecimalFormat fnum = new DecimalFormat("##0.0");
            String dstr = fnum.format(dis);
            if (isKmType) {
                return "" + dstr + "KM";
            } else {
                return "" + dstr + "公里";
            }
        }

        int dis = (int) lenMeter / 10 * 10;
        if (isKmType) {
            return "" + dis + "M";
        } else {
            return "" + dis + "米";
        }
    }


    /**
     * 保留几位小数
     */
    public static float keepFloatCount(float value, int count) {
        BigDecimal b = new BigDecimal(value);
        float f1 = (float) b.setScale(count, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
        return f1;
    }

//    /**
//     * 让小数点后至少5位
//     */
//    public static double keepDoubleCount(double value) {
//        DecimalFormat df2  = new DecimalFormat("###.00000");
//        return Double.parseDouble(df2.format(value));
//    }

    /**
     * 让小数点后至少5位
     */
    public static double keepDoubleCount(double value, int count) {
//        BigDecimal b = new BigDecimal(value);
//        double f1 = b.setScale(count, BigDecimal.ROUND_HALF_UP)
//                .doubleValue();
//        return f1;
//
//        BigDecimal b = new BigDecimal(value);
//        double df = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        DecimalFormat df = new DecimalFormat("######0.00000");
        String a = df.format(value);
        Log.e("lin", "fff-->" + a);
        Log.e("lin", "fffb-->" + Double.parseDouble(a));
        return Double.parseDouble(a);

    }


    /**
     * 保证一个整数至少有几位
     */
    public static String keepIntCount(int value, int count) {
        String retrunValue = String.format("%0" + count + "d", value);
        return retrunValue;
    }

    /**
     * @param activity 当前context
     * @param cls      目标activity
     */
    public static void startUpAct(Activity activity, Class<?> cls, boolean isFinish,
                                  Bundle bundle) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setClass(activity, cls);
        activity.startActivity(intent);
        if (isFinish) {
            activity.finish();
        }
    }


    /**
     * 将ip的整数形式转换成ip形式
     *
     * @param ipInt
     * @return
     */
    public static String int2ip(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }

    /**
     * 将php端返回的时间戳转换成Date类型
     *
     * @param time php端时间戳
     * @return java本地的Date对象
     * @author zhaozhijun
     */
    public static Date convertTimeStampFromPHP(long time) {
        Date date = new Date();
        if (new String(time + "").length() == 10) {
            //php端的时间戳没有毫秒，手动加3个0进去
            date.setTime(Long.decode(time + "000"));
        }
        return date;
    }


    /**
     * 保留最后几位
     */
    public static String keepLastString(String value, int count) {
        if (value == null && value.equals("")) {
            return value;
        }
        if (value.length() <= count) {
            return value;
        }
        String keepStr = value.substring(value.length() - count, value.length());
        return keepStr;
    }

    /**
     * 将php返回的以秒作单位的int型时间戳，转成ms单位的对应日期格式的字符串
     *
     * @param phpTime      服务器返回时间戳(秒)
     * @param formatString eg:MM月dd日
     * @return string
     * @author chenyouren
     */
    public static String phpTimeFormatToString(int phpTime, String formatString) {
        long time = 1000;
        time *= phpTime;
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        return format.format(new Date(time));
    }


    /**
     * 判断string是否为null，或者“”
     */
    public static boolean isNullString(String value) {
        if (value != null && !value.equals("")) {
            return false;
        } else {
            return true;
        }
    }


    public static void toastShow(Context context, String showText) {
        Toast.makeText(context, showText, Toast.LENGTH_SHORT).show();
    }

    //	md5加密
    public static String md5(String encode) {
        return normalEncode(encode, "MD5");
    }

    // 加密
    public static String normalEncode(String encode, String type) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance(type).digest(encode.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    public static void savePreferencesValue(Context context, String name, String value) {
        SharedPreferences preferences = context.getSharedPreferences(Constants.SP_NAME, Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(name, value);
        editor.commit();
    }

    public static String getPreferencesValue(Context context, String name, String defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(Constants.SP_NAME, Context.MODE_WORLD_READABLE);
        return preferences.getString(name, defaultValue);
    }

    public static void saveIntPreferencesValue(Context context, String name, int value) {
        SharedPreferences preferences = context.getSharedPreferences(Constants.SP_NAME, Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(name, value);
        editor.commit();
    }

    public static int getIntPreferencesValue(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(Constants.SP_NAME, Context.MODE_WORLD_READABLE);
        return preferences.getInt(name, 0);
    }

    public static void savePreferencesCount(Context context, int count) {
        SharedPreferences preferences = context.getSharedPreferences(Constants.SP_NAME, Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("count", count);
        editor.commit();
    }

    public static int getPreferencesCount(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Constants.SP_NAME, Context.MODE_WORLD_READABLE);
        return preferences.getInt("count", 0);
    }

    public static String getCountNum(int num) {
        Format f1 = new DecimalFormat("0000");
        return f1.format(num);
    }


    public static String getPhotoFileName(String dirPath, String custId) {
        String fileName = custId + "_" + getCountNum(0);
        List<String> names = new ArrayList<>();
        File file = new File(dirPath);
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                fileName = custId + "_" + getCountNum(0);
            } else {
                for (File tempFile : childFile) {
                    if (tempFile.isFile()) {
                        String tempFileName = getFileNameNoEx(tempFile.getName());
                        if (tempFileName.startsWith(custId + "_")) {
                            String[] tempFileIndexs = tempFileName.split("_");
                            if (tempFileIndexs != null && tempFileIndexs.length == 2) {
                                names.add(tempFileIndexs[1]);
                            }
                        }
                    }

//                        if (tempFileName.length() >=5 && tempFileName.contains("_")){
//                            String[] tempFileIndexs = tempFileName.split("_");
//                                if (tempFileIndexs != null && tempFileIndexs.length == 3){
//                                    te
//                                }
//                            }
//                        }
                }
            }

            if (names.size() > 0) {
                Collections.sort(names);
                int num = Integer.parseInt(names.get(names.size() - 1)) + 1;
                String tempName = getCountNum(num);
                fileName = custId + "_" + tempName;
            }
        }
        return fileName;
    }

    /*
 * Java文件操作 获取文件扩展名
 *
 */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /*
     * Java文件操作 获取不带扩展名的文件名
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * 获得hh：mm：ss格式的数据
     *
     * @return
     */
    public static String getFormatTime(long millisecond) {
        long days = millisecond / (1000 * 60 * 60 * 24);
        long hours = (millisecond - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (millisecond - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
        long second = (millisecond - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / 1000;
        String time = "";
        if (days > 0)
            time = time + Tools.keepIntCount((int) days, 2) + " days";
        if (days > 0 || hours > 0)
            time = time + Tools.keepIntCount((int) hours, 2) + " hours";
        if (days > 0 || hours > 0 || minutes > 0)
            time = time + Tools.keepIntCount((int) minutes, 2) + " mins";
        if (days > 0 || hours > 0 || minutes > 0 || second > 0)
            time = time + Tools.keepIntCount((int) second, 2) + " seconds";
        else time = "0 seconds";
        return time;
    }

    /**
     * 把view转化为图片
     * @param view
     * @return
     */
    public static String viewSaveToImage(View view) {
        String path = null;
        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        view.setDrawingCacheBackgroundColor(Color.WHITE);

        // 把一个View转换成图片
        Bitmap cachebmp = loadBitmapFromView(view);

        // 添加水印
        Bitmap bitmap = Bitmap.createBitmap(createWatermarkBitmap(cachebmp,
                "@ Zhang Phil"));

        FileOutputStream fos;
        try {
            String tempPath = null;
            // 判断手机设备是否有SD卡
//            boolean isHasSDCard = Environment.getExternalStorageState().equals(
//                    android.os.Environment.MEDIA_MOUNTED);
//            if (isHasSDCard) {
//                // SD卡根目录
                String rootPath = Tools.getRootPath(ApplicationUser.mContext);
                File sdRoot = new File(rootPath);
                File file = new File(sdRoot, "test.PNG");
                fos = new FileOutputStream(file);
                tempPath = file.getPath();
//            } else
//                throw new Exception("创建文件失败!");

            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);

            fos.flush();
            fos.close();
            path = tempPath;
        } catch (Exception e) {
            e.printStackTrace();
        }

        view.destroyDrawingCache();
        return path;
    }

    private static Bitmap loadBitmapFromView(View v) {
        int w = v.getWidth();
        int h = v.getHeight();

        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */

        v.layout(0, 0, w, h);
        v.draw(c);

        return bmp;
    }

    // 为图片target添加水印
    public static Bitmap createWatermarkBitmap(Bitmap target, String str) {
        int w = target.getWidth();
        int h = target.getHeight();

        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);

        Paint p = new Paint();

        // 水印的颜色
        p.setColor(Color.RED);

        // 水印的字体大小
        p.setTextSize(16);

        p.setAntiAlias(true);// 去锯齿

        canvas.drawBitmap(target, 0, 0, p);

        // 在中间位置开始添加水印
        canvas.drawText(str, w / 2, h / 2, p);

        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();

        return bmp;
    }

    //把view转化为图片
    public static Bitmap compressBitmap(Bitmap bitmap) {
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
//        return bitmap;

        return ThumbnailUtils.extractThumbnail(bitmap, 512, 512);


//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
//        int options = 100;
//        while (baos.toByteArray().length / 1024 > 300) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
//            baos.reset();// 重置baos即清空baos
//            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
//            options -= 10;// 每次都减少10
//        }
//        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
//        bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
//        return bitmap;

    }

    public static void saveBitmap(Bitmap bitmap,String targetPath){
        try {
            File file=new File(targetPath);
            FileOutputStream out= null;
                out = new FileOutputStream(file);
            if(bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)){
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 获取扩展SD卡存储目录
     *
     * 如果有外接的SD卡，并且已挂载，则返回这个外置SD卡目录
     * 否则：返回内置SD卡目录
     *
     * @return
     */
    public static String getExternalSdCardPath() {

        if (SDCardUtils.isMounted()) {
            File sdCardFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            return sdCardFile.getAbsolutePath();
        }

        String path = null;

        File sdCardFile = null;

        ArrayList<String> devMountList = getDevMountList();

        for (String devMount : devMountList) {
            File file = new File(devMount);

            if (file.isDirectory() && file.canWrite()) {
                path = file.getAbsolutePath();

                String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
                File testWritable = new File(path, "test_" + timeStamp);

                if (testWritable.mkdirs()) {
                    testWritable.delete();
                } else {
                    path = null;
                }
            }
        }

        if (path != null) {
            sdCardFile = new File(path);
            return sdCardFile.getAbsolutePath();
        }

        return null;
    }

    /**
     * 遍历 "system/etc/vold.fstab” 文件，获取全部的Android的挂载点信息
     *
     * @return
     */
    private static ArrayList<String> getDevMountList() {
        String[] toSearch = FileUtils.readFile("/etc/vold.fstab").split(" ");
        ArrayList<String> out = new ArrayList<String>();
        for (int i = 0; i < toSearch.length; i++) {
            if (toSearch[i].contains("dev_mount")) {
                if (new File(toSearch[i + 2]).exists()) {
                    out.add(toSearch[i + 2]);
                }
            }
        }
        return out;
    }
    public static String getRootPath(Context context){
        String phonePicsPath ="";
        if(getExternalSdCardPath() != null){
            phonePicsPath = getExternalSdCardPath();
        }else{
            phonePicsPath = context.getFilesDir().getAbsolutePath();
        }
//        phonePicsPath = context.getFilesDir().getAbsolutePath();
        return phonePicsPath;
    }


}
