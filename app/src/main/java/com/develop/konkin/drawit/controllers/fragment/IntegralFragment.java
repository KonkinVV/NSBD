package com.develop.konkin.drawit.controllers.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.develop.konkin.drawit.R;
import com.develop.konkin.drawit.model.helper.KeyBoardHelper;
import com.develop.konkin.drawit.model.opn.OPNParser;
import com.develop.konkin.drawit.model.tree.Node;
import com.develop.konkin.drawit.view.IntegralView;

import java.util.Stack;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IntegralFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IntegralFragment extends Fragment {
    public IntegralFragment() {
    }

    public static IntegralFragment newInstance() {
        return new IntegralFragment();
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_integral, container, false);
        final IntegralView integralView = view.findViewById(R.id.fragment_integral__integral_view);
        final EditText funcText = view.findViewById(R.id.fragment_integral__func_text);
        final EditText leftBoarderText = view.findViewById(R.id.fragment_integral__left_boarder);
        final EditText rightBoarderText = view.findViewById(R.id.fragment_integral__right_boarder);
        final TextView resultText = view.findViewById(R.id.fragment_integral__result);

        view.findViewById(R.id.fragment_integral__button).setOnClickListener(v -> {
            if (TextUtils.isEmpty(funcText.getText()) || TextUtils.isEmpty(leftBoarderText.getText()) || TextUtils.isEmpty(rightBoarderText.getText())) {
                resultText.setVisibility(View.GONE);
                return;
            }
            KeyBoardHelper.hideKeyBoard(getActivity());
            final String function = funcText.getText().toString();
            final String leftBoardStr = leftBoarderText.getText().toString();
            final String rightBoarderStr = rightBoarderText.getText().toString();

            final Node node = OPNParser.createTree(function);
            final float leftBoarder = Float.parseFloat(leftBoardStr);
            final float rightBoarder = Float.parseFloat(rightBoarderStr);

            integralView.clearFunctions();
            integralView.add(node);
            integralView.setHatchBoarders(leftBoarder, rightBoarder);
            final double answer = (double) Math.round(simpson(node, leftBoarder, rightBoarder) * 10_000) / 10_000 ;
            final String resultStr = Double.toString(answer);
            resultText.setVisibility(View.VISIBLE);
            resultText.setText(resultStr);
        });

        integralView.post(() -> {
            integralView.update();
            integralView.invalidate();
        });

        return view;
    }

    private double calculate(final Node node, final double x) {
        return node.calculate(x);
    }

    private double simpson(final Node node, final double a, final double b) {
        int n = 5;
        double h = (b - a) / (2 * n);
        double sum1 = 0;
        for (int i = 1; i < 2 * n; i += 2) {
            sum1 += calculate(node, a + i * h);
        }
        sum1 *= 4;
        double sum2 = 0;
        for (int i = 2; i < 2 * n; i += 2) {
            sum2 += calculate(node, a + i * h);
        }
        sum2 *= 2;
        return (h / 3) * (calculate(node, a) + calculate(node, b) + sum1 + sum2);
    }
}
