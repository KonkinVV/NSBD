package com.develop.konkin.drawit.controllers.fragment.graph;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.develop.konkin.drawit.R;
import com.develop.konkin.drawit.adapter.FuncAdapter;
import com.develop.konkin.drawit.controllers.activity.MainActivity;
import com.develop.konkin.drawit.model.opn.OPNParser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FuncListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FuncListFragment extends Fragment {
    private RecyclerView.Adapter adapter;
    private List<String> functions;
    private Context context;
    private EditText funcEditText;

    public static FuncListFragment newInstance(final ArrayList<String> functions) {
        final Bundle args = new Bundle();
        final FuncListFragment fragment = new FuncListFragment();

        args.putStringArrayList(MainActivity.FUNCTIONS_KEY, functions);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        Bundle args = getArguments();
        assert args != null && context != null;

        functions = args.getStringArrayList(MainActivity.FUNCTIONS_KEY);
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_func_list, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.fragment_func_list__recycler);
        funcEditText = view.findViewById(R.id.fragment_func_list__func_text);

        assert functions != null;
        adapter = new FuncAdapter(functions);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        return view;
    }

    public void add() {
        if (!TextUtils.isEmpty(funcEditText.getText())) {
            String function = funcEditText.getText().toString();
            try {
                OPNParser.readFunc(function);
                functions.add(function);
                funcEditText.setText("");
                adapter.notifyDataSetChanged();

            } catch (IllegalArgumentException e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(FuncListFragment.class.getSimpleName(), "DESTROY_VIEW");
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (funcEditText != null && inputManager != null) {
            inputManager.hideSoftInputFromWindow(funcEditText.getWindowToken(), 0);
        }
    }
}
