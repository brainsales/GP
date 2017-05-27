package com.brainsales.gameport.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brainsales.gameport.R;

/**
 * Created by Ryu on 2017-05-27.
 */

public class ProduceAdapter extends RecyclerView.Adapter<ProduceAdapter.ProduceViewHolder> {

    private String[] mDataset;
    public static class ProduceViewHolder extends RecyclerView.ViewHolder {

        public ProduceViewHolder (View v) {
            super(v);


        }
    }

    @Override
    public ProduceAdapter.ProduceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.produce_item, parent, false);
        ProduceViewHolder produceViewHolder = new ProduceViewHolder(v);
        return produceViewHolder;

    }

    @Override
    public void onBindViewHolder(ProduceViewHolder holder, int position){

    }

    @Override
    public int getItemCount() { return mDataset.length; }


}
