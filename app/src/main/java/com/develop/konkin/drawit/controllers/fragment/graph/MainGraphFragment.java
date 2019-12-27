package com.develop.konkin.drawit.controllers.fragment.graph;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.develop.konkin.drawit.R;
import com.develop.konkin.drawit.model.helper.AnimHelper;

import java.util.ArrayList;

import static com.develop.konkin.drawit.model.helper.FragmentHelper.changeFragment;

public class MainGraphFragment extends Fragment {

    private boolean onGraph;
    private FuncListFragment funcListFragment;

    public static MainGraphFragment newInstance() {
        return new MainGraphFragment();
    }

    private ArrayList<String> functions;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        functions = new ArrayList<>();
        onGraph = false;
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main_graph, container, false);

        final FloatingActionButton fabChange = view.findViewById(R.id.fragment_main_graph__fab_change_screen);
        final FloatingActionButton fabAdd = view.findViewById(R.id.fragment_main_graph__button_add);

        fabAdd.setOnClickListener(v -> {
            if (funcListFragment != null) {
                funcListFragment.add();
            }
        });

        fabChange.setOnClickListener(v -> {
            if (onGraph) {
                funcListFragment = FuncListFragment.newInstance(functions);
                AnimHelper.showView(fabAdd);
                changeFragment(funcListFragment, getChildFragmentManager(), R.id.fragment_main_graph__container);
            } else {
                funcListFragment = null;
                AnimHelper.hideView(fabAdd);
                changeFragment(GraphFragment.newInstance(functions), getChildFragmentManager(), R.id.fragment_main_graph__container);
            }
            onGraph = !onGraph;
        });

        funcListFragment = FuncListFragment.newInstance(functions);
        changeFragment(funcListFragment, getChildFragmentManager(), R.id.fragment_main_graph__container);

        return view;
    }
}
