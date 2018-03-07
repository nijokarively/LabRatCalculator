package com.nvk.labrat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rengwuxian.materialedittext.MaterialEditText;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import de.codecrafters.tableview.toolkit.TableDataRowBackgroundProviders;
import lumenghz.com.pullrefresh.PullToRefreshView;
import lumenghz.com.pullrefresh.refresh_view.RocketRefreshView;

import static com.nvk.labrat.FormulaeFragment.REFRESH_DELAY;

/**
 * Created by Nijo on 06/03/2018.
 */


public class PCRResultsFragment extends Fragment {
    public static PCRResultsFragment newInstance() {
        PCRResultsFragment fragment = new PCRResultsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pcr_results, container, false);
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



        Bundle args = getArguments();
        String[][] DATA_TO_SHOW = (String[][]) args.getSerializable("PCRData");
        String[] TABLE_HEADERS = { "", "Per Reaction", "Master Mix" };
        TableView<String[]> tableView = (TableView<String[]>) getView().findViewById(R.id.tableView);
        tableView.setHeaderElevation(10);
        tableView.setSaveEnabled(true);
        tableView.setHeaderBackgroundColor(getResources().getColor(R.color.colorWhite));
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(getContext(), TABLE_HEADERS));
        tableView.setDataAdapter(new SimpleTableDataAdapter(getContext(), DATA_TO_SHOW));
        int colorEvenRows = getResources().getColor(R.color.colorWhite);
        int colorOddRows = getResources().getColor(R.color.colorGray);
        tableView.setDataRowBackgroundProvider(TableDataRowBackgroundProviders.alternatingRowColors(colorEvenRows, colorOddRows));


    }
}