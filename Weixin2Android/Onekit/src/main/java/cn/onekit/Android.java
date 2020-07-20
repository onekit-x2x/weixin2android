package cn.onekit;

import android.annotation.SuppressLint;
import android.app.ActivityThread;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.util.Size;
import android.util.TypedValue;

public class Android {

    @SuppressLint("StaticFieldLeak")
    public static Context context;
    public static Application application() {
        try {
            return ActivityThread.currentApplication();
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static  ComponentName launchActivity(Context context) {
        PackageManager pm = context.getPackageManager();
        String packageName = context.getPackageName();
        return pm.getLaunchIntentForPackage(packageName).getComponent();
    }
    public static String launchActivityName(Context context) {
        return launchActivity(context).getClassName();
    }
    /*
    public static ComponentName currentActivty(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.RunningTaskInfo info = manager.getRunningTasks(1).get(0);
       return info.topActivity;
    }
    public static String currentActiivtyName(Context context) {
        return Android.currentActivty(context).getClassName();
    }*/
    public static Size SIZE(){
        DisplayMetrics metric = getDisplayMetrics();
        Size size = new Size(metric.widthPixels,metric.heightPixels);
        return size;//new Size(size.getWidth()/2,size.getHeight()/2);
    }
    static DisplayMetrics displayMetrics;
    final static DisplayMetrics getDisplayMetrics() {
        if (displayMetrics == null) {
            displayMetrics = context.getResources().getDisplayMetrics();
        }
        return displayMetrics;
    }

    public static int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getDisplayMetrics());
    }

    public static float px2dp(int px) {
        return px / (getDisplayMetrics().density);
    }
/*
    public static int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,getDisplayMetrics());
    }

    public static float px2sp(int px) {
        return px /  getDisplayMetrics().scaledDensity;
    }*/
}