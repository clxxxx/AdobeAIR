package com.siz.adobeair;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.lang.reflect.Method;

public class FileMgrUtils {
    public static final int ANDROID_SDK_R = 30;
    private static final String SETTINGS_ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION = "android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION";

    public static boolean verLessR(){
        return Build.VERSION.SDK_INT < ANDROID_SDK_R;
    }
    private static Method externalStorageManagerQueryMethod;
    public static boolean isExternalStorageManager(){
        if(verLessR()){
            return true;
        }
        try{
            boolean result = (boolean)getExternalStorageManagerQueryMethod().invoke(null);
            Log.d("Environment", "isExternalStorageManager result = "+result);
            return result;
        }catch (Exception e){
            return true;
        }
    }
    private static Method getExternalStorageManagerQueryMethod(){
        if(externalStorageManagerQueryMethod == null){
            try {
                externalStorageManagerQueryMethod = Environment.class.getMethod("isExternalStorageManager");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return externalStorageManagerQueryMethod;
    }

    public static Intent createFileManagerPermissionIntent(Activity activity){
        Intent intent = new Intent(SETTINGS_ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        return intent;
    }

    public static void startFileManagerPermissionIntent(Activity activity, int requestCode){
        Intent intent = new Intent(SETTINGS_ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        activity.startActivityForResult(intent, requestCode);
    }
}
