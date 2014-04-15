package com.hufeng.droidtools;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hufeng.droidtool.R;

/**
 * Created by feng on 2014-03-12.
 */
public class ScreenFragment extends BaseFragment {

    public static final String FRAGMENT_TAG = "ScreenFragment";

    private TextView mScreenLayoutSize;
    private TextView mLogicalDisplayDensity;
    private TextView mDisplayDensityDPI;
    private TextView mScreenAspectRatio;
    private TextView mCurrentScreenOrientation;
    private TextView mScreenWidthInPixels;
    private TextView mScreenHeightInPixels;
    private TextView mScreenWidthInDP;
    private TextView mScreenHeightInDP;
    private TextView mSmallestScreenWidthInDP;

    public static ScreenFragment newFragmentInstance() {
        ScreenFragment fragment = new ScreenFragment();
        return fragment;
    }

    public ScreenFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_screen, container, false);

        mScreenLayoutSize = (TextView)rootView.findViewById(R.id.screen_layout_size);
        mLogicalDisplayDensity = (TextView)rootView.findViewById(R.id.logical_display_density);
        mDisplayDensityDPI = (TextView)rootView.findViewById(R.id.display_density_dpi);
        mScreenAspectRatio = (TextView)rootView.findViewById(R.id.screen_aspect_ratio);
        mCurrentScreenOrientation = (TextView)rootView.findViewById(R.id.current_screen_orientation);
        mScreenWidthInPixels = (TextView)rootView.findViewById(R.id.screen_width_in_pixels);
        mScreenHeightInPixels = (TextView)rootView.findViewById(R.id.screen_height_in_pixels);
        mScreenWidthInDP = (TextView)rootView.findViewById(R.id.screen_width_in_dp);
        mScreenHeightInDP = (TextView)rootView.findViewById(R.id.screen_height_in_dp);
        mSmallestScreenWidthInDP = (TextView)rootView.findViewById(R.id.smallest_screen_width);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity act = getActivity();
        int width_in_pixel = ScreenUtils.getScreenPixelWidth(act);
        int height_in_pixel = ScreenUtils.getScreenPixelHeight(act);
        mScreenWidthInPixels.setText(getString(R.string.screen_width_in_pixels)+" "+width_in_pixel);
        mScreenHeightInPixels.setText(getString(R.string.screen_height_in_pixels)+" "+height_in_pixel);

        float density_ratio = ScreenUtils.getScreenLogicalDensityRatio(act);
        int density_dpi = ScreenUtils.getScreenLogicalDensityDpi(act);
        mScreenWidthInDP.setText(getString(R.string.screen_width_in_dp) +" "+ (int)((float)width_in_pixel/density_ratio));
        mScreenHeightInDP.setText(getString(R.string.screen_height_in_dp) +" "+ (int)((float)height_in_pixel/density_ratio));
        String density_dpi_str = "unknown";
        switch (density_dpi) {
            case 120:
                density_dpi_str = "ldpi";
                break;
            case 160:
                density_dpi_str = "mdpi";
                break;
            case 240:
                density_dpi_str = "hdpi";
                break;
            case 213:
                density_dpi_str = "tvdpi";
                break;
            case 320:
                density_dpi_str = "xhdpi";
                break;
            case 480:
                density_dpi_str = "xxhdpi";
                break;
            case 640:
                density_dpi_str = "xxxhdpi";
                break;
        }

        mDisplayDensityDPI.setText(getString(R.string.display_density_dpi) +" "+ density_dpi+"("+density_dpi_str+")");
        mLogicalDisplayDensity.setText(getString(R.string.logical_display_density)+" "+density_ratio);

        int smallest_screen_width = ScreenUtils.getSmallestScreenWidthInfo(act);
        mSmallestScreenWidthInDP.setText(getString(R.string.smallest_screen_width)+" "+smallest_screen_width);

        int aspect_ratio = ScreenUtils.getScreenAspectRatioInfo(act);
        String aspect_ratio_str = "unknown";
        switch (aspect_ratio) {
            case Configuration.SCREENLAYOUT_LONG_NO:
                aspect_ratio_str = "notlong";
                break;
            case Configuration.SCREENLAYOUT_LONG_YES:
                aspect_ratio_str = "long";
                break;
            case Configuration.SCREENLAYOUT_LONG_UNDEFINED:
                aspect_ratio_str = "undefined";
                break;
        }
        mScreenAspectRatio.setText(getString(R.string.screen_aspect_ratio) +" "+ aspect_ratio_str);

        int layout_size = ScreenUtils.getScreenLayoutSizeInfo(act);
        String layout_size_str = "unknown";
        switch (layout_size) {
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                layout_size_str = "small";
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                layout_size_str = "normal";
                break;
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                layout_size_str = "large";
                break;
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                layout_size_str = "xlarge";
                break;
            case Configuration.SCREENLAYOUT_SIZE_UNDEFINED:
                layout_size_str = "undefined";
                break;
        }
        mScreenLayoutSize.setText(getString(R.string.screen_layout_size)+" "+layout_size_str);

        int orientation = ScreenUtils.getScreenOrientationInfo(act);
        String orientation_str = "unknown";
        switch (orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                orientation_str = "portrait";
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                orientation_str = "landscape";
                break;
            case Configuration.ORIENTATION_UNDEFINED:
                orientation_str = "undefined";
                break;
            case Configuration.ORIENTATION_SQUARE:
                orientation_str = "square";
                break;
        }
        mCurrentScreenOrientation.setText(getString(R.string.current_screen_orientation)+" "+orientation_str);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onFragmentAttached("Screen");
    }
}
