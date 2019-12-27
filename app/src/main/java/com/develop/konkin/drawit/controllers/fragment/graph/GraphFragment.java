package com.develop.konkin.drawit.controllers.fragment.graph;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.develop.konkin.drawit.R;
import com.develop.konkin.drawit.controllers.activity.MainActivity;
import com.develop.konkin.drawit.model.opn.OPNParser;
import com.develop.konkin.drawit.model.tree.Node;
import com.develop.konkin.drawit.view.GraphView;

import java.util.ArrayList;
import java.util.List;

public class GraphFragment extends Fragment {

    private List<String> functions;

    public static GraphFragment newInstance(final ArrayList<String> functions) {
        final Bundle args = new Bundle();
        final GraphFragment fragment = new GraphFragment();
        args.putStringArrayList(MainActivity.FUNCTIONS_KEY, functions);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        assert args != null;

        functions = args.getStringArrayList(MainActivity.FUNCTIONS_KEY);
    }

    private GraphView graphView;

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_graph, container, false);

        graphView = view.findViewById(R.id.fragment_graph__graph_view);
        graphView.post(() -> {
            graphView.update();
            final List<Node> list = new ArrayList<>(functions.size());
            for (final String function : functions) {
                try {
                    list.add(OPNParser.createTree(function));
                } catch (IllegalArgumentException e) {
                    Toast.makeText(getContext(), function + ": " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            graphView.addAll(list);
        });
        return view;
    }
}
