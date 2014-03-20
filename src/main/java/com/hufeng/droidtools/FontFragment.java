package com.hufeng.droidtools;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.hufeng.droidtool.R;

/**
 * Created by feng on 2014-03-12.
 */
public class FontFragment extends Fragment{

    int font_style = Typeface.NORMAL;
    Typeface face_type = Typeface.DEFAULT;
    TextView mTextViewNormal, mTextViewExtra;
    int font_size = 12;
    int line_spacing = 0;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FontFragment newInstance() {
        FontFragment fragment = new FontFragment();
        return fragment;
    }

    public FontFragment() {
    }

    public void renderText () {
        mTextViewNormal.setTextSize(TypedValue.COMPLEX_UNIT_SP, font_size);
        mTextViewExtra.setTextSize(TypedValue.COMPLEX_UNIT_SP, font_size);
        mTextViewExtra.setLineSpacing(line_spacing-10, 1.0f);
        mTextViewNormal.setTypeface(face_type, font_style);
        mTextViewExtra.setTypeface(face_type, font_style);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_foot, container, false);
        mTextViewNormal = (TextView) rootView.findViewById(R.id.font_normal_example);
        mTextViewExtra = (TextView) rootView.findViewById(R.id.font_extra_example);
        final TextView line_spacing_label_view = (TextView) rootView.findViewById(R.id.line_spacing_label);
        Spinner type_face_view = (Spinner) rootView.findViewById(R.id.font_type_face);
        Spinner font_style_view = (Spinner) rootView.findViewById(R.id.font_style);
        Spinner font_size_view = (Spinner) rootView.findViewById(R.id.font_size);
        SeekBar line_spacing_view = (SeekBar) rootView.findViewById(R.id.line_spacing);
        line_spacing_view.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                line_spacing = progress;
                line_spacing_label_view.setText((progress-10)+"sp");
                renderText();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                line_spacing_label_view.setText((line_spacing-10)+"sp");
                renderText();
            }
        });
        font_size_view.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    font_size = 12;
                } else if (position == 1) {
                    font_size = 14;
                } else if (position == 2) {
                    font_size = 18;
                } else if (position == 3) {
                    font_size = 22;
                }
                renderText();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                font_size = 12;
                renderText();
            }
        });
        font_style_view.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    font_style = Typeface.NORMAL;
                } else if (position == 1) {
                    font_style = Typeface.BOLD;
                } else if (position == 2) {
                    font_style = Typeface.ITALIC;
                }
                renderText();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        type_face_view.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener (){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    face_type = Typeface.DEFAULT;
                } else if (position == 1) {
                    face_type = Typeface.SANS_SERIF;
                } else if (position == 2) {
                    face_type = Typeface.SERIF;
                } else if (position == 3) {
                    face_type = Typeface.MONOSPACE;
                }
                renderText();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                face_type = Typeface.DEFAULT;
            }
        });
        line_spacing_view.setProgress(10);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onFragmentAttached("Font");
    }

}
