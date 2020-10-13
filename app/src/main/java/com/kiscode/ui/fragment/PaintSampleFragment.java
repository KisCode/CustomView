package com.kiscode.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/****
 * Description: Paint特性演示fragment
 * Author:  keno
 * CreateDate: 2020/10/13 19:33
 */
public class PaintSampleFragment extends Fragment {
    private static final String KEY_VIEW_CLASS_NAME = "VIEW_CLASS_NAME";
    private String mViewClassName;

    public static PaintSampleFragment newInstance(String viewClassName) {
        Bundle args = new Bundle();
        args.putString(KEY_VIEW_CLASS_NAME, viewClassName);
        PaintSampleFragment fragment = new PaintSampleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        String viewClassName = getArguments().getString(KEY_VIEW_CLASS_NAME);
        View view = null;
        try {
            Constructor constructor = Class.forName(viewClassName).getConstructor(Context.class);
            view = (View) constructor.newInstance(getContext());
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | java.lang.InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return view;
    }
}
