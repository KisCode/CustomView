package com.kiscode.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.kiscode.ui.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RectFragment extends Fragment {

    public RectFragment() {
        // Required empty public constructor
    }


    public static RectFragment newInstance() {
        RectFragment fragment = new RectFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rect, container, false);
    }
}