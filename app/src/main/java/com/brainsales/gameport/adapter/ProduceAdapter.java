/*
package com.brainsales.gameport.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brainsales.gameport.R;

import org.w3c.dom.Text;

*/
/**
 * Created by Ryu on 2017-05-27.
 *//*


public class ProduceAdapter extends RecyclerView.Adapter<ProduceAdapter.ProduceViewHolder> {

    private String[] mDataset;
    
    public static class ProduceViewHolder extends RecyclerView.ViewHolder {

        public CardView mCardView;
        public TextView mGameName;
        public TextView mGameType;
        public ProduceViewHolder (View v) {
            super(v);

            mCardView = (CardView) v.findViewById(R.id.item_produce);
            mGameName = (TextView) v.findViewById(R.id.post_name);
            mGameType = (TextView) v.findViewById(R.id.post_type);

        }
    }

    public ProduceAdapter(String[] dataSet){
        mDataset = dataSet;
    }

    @Override
    public ProduceAdapter.ProduceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_produce, parent, false);
        ProduceViewHolder produceViewHolder = new ProduceViewHolder(v);
        return produceViewHolder;

    }

    @Override
    public void onBindViewHolder(ProduceViewHolder holder, int position){
        holder.mGameName.setText(mDataset[position]);
        holder.mGameType.setText(mDataset[position]);
    }

    @Override
    public int getItemCount() { return mDataset.length; }


}
*/
