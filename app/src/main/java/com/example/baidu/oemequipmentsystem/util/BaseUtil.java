package com.example.baidu.oemequipmentsystem.util;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.baidu.oemequipmentsystem.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * 基础工具类
 * Created by zhangbinbin03 on 16/6/8.
 */
public class BaseUtil {

    /**
     * 获取屏幕的宽度
     */
    public static int getWindowWidth(Context context) {
        WindowManager wm = (WindowManager) (context
                .getSystemService(Context.WINDOW_SERVICE));
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕的高度
     */
    public static int getWindowHeigh(Context context) {
        // 获取屏幕分辨率
        WindowManager wm = (WindowManager) (context
                .getSystemService(Context.WINDOW_SERVICE));
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * dip转px
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px转dip
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 隐藏软键盘
     */
    public static void dismissKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 弹出软键盘
     */
    public static void showKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * IMEI
     */
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String IMEI = telephonyManager.getDeviceId();
        return IMEI;
    }

    /**
     * 手机型号
     */
    public static String getModel() {
        String model = android.os.Build.MODEL;
        return model;
    }

    /**
     * 手机系统版本
     */
    public static String getOSVersion() {
        String os = android.os.Build.VERSION.RELEASE;
        return os;
    }

    /**
     * 分辨率
     */
    public static String getResulotion(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();
        int height = windowManager.getDefaultDisplay().getHeight();
        return width + "*" + height;
    }

    /**
     * CPU
     */
    public static String getCPU() {
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        String cpu = "";
        String[] arrayOfString;
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            for (int i = 2; i < arrayOfString.length; i++) {
                cpu = cpu + arrayOfString[i] + " ";
            }
            localBufferedReader.close();
        } catch (IOException e) {
        }
        return cpu;
    }

    /**
     * 运行内存
     */
    public static long getMemory() {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        long memory=0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() ;
            localBufferedReader.close();
            memory = initial_memory / (1024 );
            if (memory % 1024 > 512)
                memory = memory / 1024 + 1;
            else
                memory = memory / 1024;
        } catch (IOException e) {
        }
        return memory;
    }

    /**
     * 机身内存
     */
    public static long getTotalInternalStorageSize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();

        long storage = totalBlocks * blockSize / (1024 * 1024);
        if (storage % 1024 > 512)
            storage = storage / 1024 + 1;
        else
            storage = storage / 1024;
        return storage;
    }

    /**
     * SD存储空间
     */
    public static long getTotalExternalStorageSize() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize;
        }
        return 0;
    }

    /**
     * 检测当的网络状态
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * loading_dialog
     */
    public static Dialog createLoadingDialog(Context context,String content) {

        int density=(int)context.getResources().getDisplayMetrics().density;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.dialog_loading, null);
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.dialog_view);
        ImageView imageView = (ImageView) view.findViewById(R.id.img_dialog_loading);
        TextView textView=(TextView) view.findViewById(R.id.txt_dialog_loading);
        textView.setText(content);

        ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(imageView,"rotation",0f,360f);
        objectAnimator.setDuration(1000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.start();


        Dialog loadingDialog = new Dialog(context, R.style.oem_dialog_style);
        loadingDialog.setCancelable(false);
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(260*density,190*density));
        return loadingDialog;

    }

}
