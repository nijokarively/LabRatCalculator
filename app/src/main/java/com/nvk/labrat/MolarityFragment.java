package com.nvk.labrat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Nijo on 27/02/2018.
 */

public class MolarityFragment extends Fragment {
    public static MolarityFragment newInstance() {
        MolarityFragment fragment = new MolarityFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_molarity, container, false);
    }
}
