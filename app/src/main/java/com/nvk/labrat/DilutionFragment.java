package com.nvk.labrat;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import lumenghz.com.pullrefresh.PullToRefreshView;
import lumenghz.com.pullrefresh.refresh_view.RocketRefreshView;

import static com.nvk.labrat.FormulaeFragment.REFRESH_DELAY;


/**
 * Created by Nijo on 24/02/2018.
 */

public class DilutionFragment extends Fragment {

    public static DilutionFragment newInstance() {
        DilutionFragment fragment = new DilutionFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dilution, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final PullToRefreshView mPullToRefreshView = (PullToRefreshView) getView().findViewById(R.id.pull_to_refresh);
        RocketRefreshView rocketRefreshView = new RocketRefreshView(mPullToRefreshView);
        mPullToRefreshView.setRefreshView(rocketRefreshView);

        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, REFRESH_DELAY);
            }
        });

    }
}
