package com.hufeng.droidtools;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.view.Display;

/**
 * Created by feng on 2014-03-12.
 */
public class ScreenUtils {

    @TargetApi(16)
    public static int getScreenPixelWidth(Activity act) {
        Point localPoint = new Point();
        Display localDisplay = act.getWindowManager().getDefaultDisplay();
        if (android.os.Build.VERSION.SDK_INT >= 16) {
            localDisplay.getRealSize(localPoint);
        } else {
            localDisplay.getSize(localPoint);
        }
        return localPoint.x;
    }

    @TargetApi(16)
    public static int getScreenPixelHeight(Activity act) {
        Point localPoint = new Point();
        Display localDisplay = act.getWindowManager().getDefaultDisplay();
        if (android.os.Build.VERSION.SDK_INT >= 16) {
            localDisplay.getRealSize(localPoint);
        } else {
            localDisplay.getSize(localPoint);
        }
        return localPoint.y;
    }

    public static int getScreenAspectRatioInfo(Context context) {
        return Configuration.SCREENLAYOUT_LONG_MASK & context.getResources().getConfiguration().screenLayout;
    }

    public static int getScreenLayoutSizeInfo(Context context) {
        return Configuration.SCREENLAYOUT_SIZE_MASK & context.getResources().getConfiguration().screenLayout;
    }

    public static float getScreenLogicalDensityRatio(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static int getScreenLogicalDensityDpi(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    public static int getScreenOrientationInfo(Context context) {
        return context.getResources().getConfiguration().orientation;
    }

    public static int getSmallestScreenWidthInfo(Context context) {
        return context.getResources().getConfiguration().smallestScreenWidthDp;
    }


}
