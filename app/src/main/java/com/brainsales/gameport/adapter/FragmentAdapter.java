package com.brainsales.gameport.adapter;

import android.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brainsales.gameport.R;

/**
 * Created by Ryu on 2017-05-24.
 */

public class FragmentAdapter extends RecyclerView.Adapter<FragmentAdapter.FragmentViewHolder> {

    private String[] mDataset;

    public static class FragmentViewHolder extends RecyclerView.ViewHolder {

        public CardView mCardView;
        public FragmentViewHolder (View v) {
            super(v);

            mCardView = (CardView) v.findViewById(R.id.card_view);
        }
    }


    @Override
    public FragmentAdapter.FragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        FragmentViewHolder fragmentViewHolder = new FragmentViewHolder(v);
        return fragmentViewHolder;

    }

    @Override
    public void onBindViewHolder(FragmentViewHolder holder, int position){

    }

    @Override
    public int getItemCount() { return mDataset.length; }


}
