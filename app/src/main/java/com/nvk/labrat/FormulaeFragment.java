package com.nvk.labrat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import lumenghz.com.pullrefresh.PullToRefreshView;
import lumenghz.com.pullrefresh.refresh_view.RocketRefreshView;


/**
 * Created by Nijo on 24/02/2018.
 */

public class FormulaeFragment extends ListFragment {
    public static final int REFRESH_DELAY = 2000;

    public static FormulaeFragment newInstance() {
        FormulaeFragment fragment = new FormulaeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_formulae, container, false);
    }

//    @Override
//    public void onListItemClick(ListView l, View v, int position, long id) {
//        String item = (String) getListAdapter().getItem(position);
//        Toast.makeText(getActivity(), item + " selected", Toast.LENGTH_LONG).show();
//    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ListView lv = (ListView) getView().findViewById(R.id.list);
        String[] values = new String[] { "C(start) x V(start) = C(final) x V(final)", "M(g) = C(mol/L) x V(L) x MW(g/mol)",
                "C(mol/L) = [M(g) / V(L)] x [1 / MW(g/mol)]","C(mg/ml) = M (mg) / V(ml)"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item,R.id.title_item ,values);
        lv.setAdapter(adapter);

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


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                if (position == 0) {
                    //String item = ((TextView) view).getText().toString();

                    //Toast.makeText(getContext(), item, Toast.LENGTH_LONG).show();
                    FragmentManager fm = getFragmentManager();

                    if (fm != null) {
                        // Perform the FragmentTransaction to load in the list tab content.
                        // Using FragmentTransaction#replace will destroy any Fragments
                        // currently inside R.id.fragment_content and add the new Fragment
                        // in its place.
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.frame_layout, new CVFragment());
                        ft.addToBackStack(null).commit();
                    }

                } else if (position == 1){
                    FragmentManager fm = getFragmentManager();

                    if (fm != null) {
                        // Perform the FragmentTransaction to load in the list tab content.
                        // Using FragmentTransaction#replace will destroy any Fragments
                        // currently inside R.id.fragment_content and add the new Fragment
                        // in its place.
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.frame_layout, new MolarityFragment());
                        ft.addToBackStack(null).commit();
                    }
                } else if (position == 2){
                    FragmentManager fm = getFragmentManager();

                    if (fm != null) {
                        // Perform the FragmentTransaction to load in the list tab content.
                        // Using FragmentTransaction#replace will destroy any Fragments
                        // currently inside R.id.fragment_content and add the new Fragment
                        // in its place.
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.frame_layout, new ConcentrationFragment());
                        ft.addToBackStack(null).commit();
                    }
                } else if (position == 3){
                    FragmentManager fm = getFragmentManager();

                    if (fm != null) {
                        // Perform the FragmentTransaction to load in the list tab content.
                        // Using FragmentTransaction#replace will destroy any Fragments
                        // currently inside R.id.fragment_content and add the new Fragment
                        // in its place.
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.frame_layout, new ReconstitutionFragment());
                        ft.addToBackStack(null).commit();
                    }
                }
            }
        });


    }

}
