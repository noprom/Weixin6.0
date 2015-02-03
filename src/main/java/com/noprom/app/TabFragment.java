package com.noprom.app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class TabFragment extends Fragment {

    private String mTitle = "Defaulat";

    public static final String TITLE = "title";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null) {
            mTitle = getArguments().getString(TITLE);
        }
        TextView textView = new TextView(getActivity());
        textView.setTextSize(20);
        textView.setBackgroundColor(Color.parseColor("#ffffffff"));
        textView.setText(mTitle);

        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
