package com.nvk.labrat;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * Created by Nijo on 27/02/2018.
 */

public class CVFragment extends Fragment {
    public static CVFragment newInstance() {
        CVFragment fragment = new CVFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cv, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final MaterialEditText cv2 = (MaterialEditText) getView().findViewById(R.id.concentration2);
        final MaterialEditText v2 = (MaterialEditText) getView().findViewById(R.id.volume2);

        cv2.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if (cv2.getText().toString().matches("")){
                    v2.setEnabled(true);
                    v2.setHelperText("");
                    v2.setHelperTextAlwaysShown(false);
                } else {
                    v2.setText("");
                    v2.setEnabled(false);
                    v2.setHelperText("Disabled");
                    v2.setHelperTextAlwaysShown(true);
                    v2.setHelperTextColor(Color.parseColor("#e91e63"));
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {


            }
        });

        v2.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if (v2.getText().toString().matches("")){
                    cv2.setEnabled(true);
                    cv2.setHelperText("");
                    cv2.setHelperTextAlwaysShown(false);

                } else {
                    cv2.setText("");
                    cv2.setEnabled(false);
                    cv2.setHelperText("Disabled");
                    cv2.setHelperTextAlwaysShown(true);
                    cv2.setHelperTextColor(Color.parseColor("#e91e63"));
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {


            }
        });


    }
}