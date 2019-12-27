package com.develop.konkin.drawit.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.develop.konkin.drawit.R;

import java.util.List;

public class FuncAdapter extends RecyclerView.Adapter<FuncAdapter.FuncHolder> {

    private final List<String> functions;

    public FuncAdapter(List<String> functions) {
        this.functions = functions;
    }


    //TODO создать из кода
    @NonNull
    @Override
    public FuncHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_func, null, false);
        itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new FuncHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FuncHolder holder, int i) {
        holder.bind(functions.get(i));
    }

    @Override
    public int getItemCount() {
        return functions.size();
    }

    class FuncHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        private FuncHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_func__text);
        }

        void bind(final String function) {
            if (!TextUtils.isEmpty(function)) {
                textView.setText(function);
            }
        }
    }
}
