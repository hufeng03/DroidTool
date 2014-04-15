package com.hufeng.droidtools;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.hufeng.droidtool.R;

/**
 * Created by feng on 14-3-31.
 */
public class DrawableFragment extends BaseFragment{

    public static final String FRAGMENT_TAG = "DrawableFragment";

    private PorterDuff.Mode mFilterMode;
    private int mFilterColor;

    private ImageView mImageView;

    private int[] FILTER_COLORS = {
            -1,
            Color.RED,
            Color.GREEN,
            Color.BLUE,
            Color.YELLOW,
            Color.BLACK,
            Color.WHITE
    };

    private PorterDuff.Mode[] FILTER_MODE = {
            null,
            PorterDuff.Mode.ADD,
            PorterDuff.Mode.CLEAR,
            PorterDuff.Mode.DARKEN,
            PorterDuff.Mode.DST,
            PorterDuff.Mode.DST_ATOP,
            PorterDuff.Mode.DST_IN,
            PorterDuff.Mode.DST_OUT,
            PorterDuff.Mode.DST_OVER,
            PorterDuff.Mode.LIGHTEN,
            PorterDuff.Mode.MULTIPLY,
            PorterDuff.Mode.OVERLAY,
            PorterDuff.Mode.SCREEN,
            PorterDuff.Mode.SRC,
            PorterDuff.Mode.SRC_ATOP,
            PorterDuff.Mode.SRC_IN,
            PorterDuff.Mode.SRC_OUT,
            PorterDuff.Mode.SRC_OVER,
            PorterDuff.Mode.XOR
    };

    public static DrawableFragment newFragmentInstance() {
        DrawableFragment fragment = new DrawableFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_drawable, container, false);

        Spinner color_filter_color = (Spinner)rootView.findViewById(R.id.color_filter_color);
        Spinner color_filter_mode = (Spinner)rootView.findViewById(R.id.color_filter_mode);

        mImageView = (ImageView)rootView.findViewById(R.id.image_view);

        color_filter_color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mFilterColor = FILTER_COLORS[position];
                refreshColorFilter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mFilterColor = -1;
                refreshColorFilter();
            }
        });

        color_filter_mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mFilterMode = FILTER_MODE[position];
                refreshColorFilter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mFilterMode = null;
                refreshColorFilter();
            }
        });

        return rootView;
    }

    private void refreshColorFilter() {
        if (mImageView == null) {
            return;
        }
        Drawable drawable = getResources().getDrawable(R.drawable.ic_folder_inbox).mutate();
        if (mFilterColor == -1 || mFilterMode == null) {
            drawable.clearColorFilter();
        } else {
            drawable.setColorFilter(mFilterColor, mFilterMode);
        }
        mImageView.setImageDrawable(drawable);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onFragmentAttached("Drawable");
    }

}
