package com.nvk.labrat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import com.rengwuxian.materialedittext.MaterialEditText;

import lumenghz.com.pullrefresh.PullToRefreshView;
import lumenghz.com.pullrefresh.refresh_view.RocketRefreshView;

import static com.nvk.labrat.FormulaeFragment.REFRESH_DELAY;

/**
 * Created by Nijo on 27/02/2018.
 */

public class PCRFragment extends Fragment {
    public static PCRFragment newInstance() {
        PCRFragment fragment = new PCRFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pcr, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final NestedScrollView sv = (NestedScrollView) getView().findViewById(R.id.scrollview);
        final FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.fab);
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


        sv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    fab.hide();
                } else {
                    fab.show();
                }
            }
        });


    }

}