package com.hufeng.droidtools;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by feng on 2014-03-30.
 */
public class EnvironmentUtil {

    private static final String TAG = EnvironmentUtil.class.getSimpleName();


    public static String test() {
        //System directories
        StringBuilder builder = new StringBuilder();
        File file;
        file = Environment.getDataDirectory();           ///data
        builder.append(print(file, "getDataDirectory"));
        file = Environment.getDownloadCacheDirectory();  ///cache
        builder.append(print(file, "getDownloadCacheDirectory"));
        file = Environment.getRootDirectory();           ///system
        builder.append(print(file, "getRootDirectory"));

        //External storage directories
        file = Environment.getExternalStorageDirectory();	///storage/sdcard0
        builder.append(print(file, "getExternalStorageDirectory"));
        file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS);	///storage/sdcard0/Alarms
        builder.append(print(file, "getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS)"));
        file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);	///storage/sdcard0/DCIM
        builder.append(print(file, "getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)"));
        file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);	///storage/sdcard0/Download
        builder.append(print(file, "getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)"));
        file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);	 ///storage/sdcard0/Movies
        builder.append(print(file, "getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)"));
        file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);	 ///storage/sdcard0/Music
        builder.append(print(file, "getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)"));
        file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS);	///storage/sdcard0/Notifications
        builder.append(print(file, "getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS)"));
        file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);	///storage/sdcard0/Pictures
        builder.append(print(file, "getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)"));
        file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS);	///storage/sdcard0/Podcasts
        builder.append(print(file, "getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS)"));
        file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES);
        builder.append(print(file, "getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES)"));
        return builder.toString();
    }

    public static String test2(Context context) {
        StringBuilder builder = new StringBuilder();
        File file;
        //Application directories
        file = context.getCacheDir();	///data/data/package/cache
        builder.append(print(file,"getCacheDir"));
        file = context.getFilesDir();	///data/data/package/files
        builder.append(print(file,"getFilesDir"));
        file = context.getFilesDir().getParentFile();	///data/data/package
        builder.append(print(file,"getFilesDir().getParentFile()"));


        //Application External storage directories
        file = context.getExternalCacheDir();	///storage/sdcard0/Android/data/package/cache
        builder.append(print(file,"getExternalCacheDir"));
        file = context.getExternalFilesDir(null);	///storage/sdcard0/Android/data/package/files
        builder.append(print(file,"getExternalFilesDir(null)"));
        file = context.getExternalFilesDir(Environment.DIRECTORY_ALARMS);	///storage/sdcard0/Android/data/package/files/Alarms
        builder.append(print(file,"getExternalFilesDir(Environment.DIRECTORY_ALARMS)"));
        file = context.getExternalFilesDir(Environment.DIRECTORY_DCIM);	///storage/sdcard0/Android/data/package/files/DCIM
        builder.append(print(file,"getExternalFilesDir(Environment.DIRECTORY_DCIM)"));
        file = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);	///storage/sdcard0/Android/data/package/files/Download
        builder.append(print(file,"getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)"));
        file = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES);	///storage/sdcard0/Android/data/package/files/Movies
        builder.append(print(file,"getExternalFilesDir(Environment.DIRECTORY_MOVIES)"));
        file = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC);	///storage/sdcard0/Android/data/package/files/Music
        builder.append(print(file,"getExternalFilesDir(Environment.DIRECTORY_MUSIC)"));
        file = context.getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS);	///storage/sdcard0/Android/data/package/files/Notifications
        builder.append(print(file,"getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS)"));
        file = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);	///storage/sdcard0/Android/data/package/files/Pictures
        builder.append(print(file,"getExternalFilesDir(Environment.DIRECTORY_PICTURES)"));
        file = context.getExternalFilesDir(Environment.DIRECTORY_PODCASTS);	///storage/sdcard0/Android/data/package/files/Podcasts
        builder.append(print(file,"getExternalFilesDir(Environment.DIRECTORY_PODCASTS)"));
        file = context.getExternalFilesDir(Environment.DIRECTORY_RINGTONES);	///storage/sdcard0/Android/data/package/files/Ringtones
        builder.append(print(file,"getExternalFilesDir(Environment.DIRECTORY_RINGTONES)"));
        return builder.toString();
    }

    private static String print(File file, String tag_info){
        String str = tag_info+": "+((file == null)?"null":file.getAbsolutePath());
        Log.i(TAG, str);
        return str+"\n";
    }

}
