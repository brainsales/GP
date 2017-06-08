package com.brainsales.gameport.Fragment;

import android.content.Context;
import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brainsales.gameport.R;
import com.brainsales.gameport.utils.ProduceSetting;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

/**
 * Created by Ryu on 2017-05-23.
 */

public class Produce extends Fragment {

    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<ProduceSetting, ProduceViewHolder> mFirebaseAdapter;
    private RecyclerView mProduceList = null;
    private View rootView;
    private ProgressDialog mProgress;

    private LinearLayoutManager manager;

    public Produce() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_produce, container, false);
        mProgress = new ProgressDialog(getActivity());

        mProduceList = (RecyclerView) rootView.findViewById(R.id.produce_recycler_view);
        manager = new LinearLayoutManager(getActivity());
        manager.setStackFromEnd(true);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Games");

        manager.setReverseLayout(true);

        mProduceList.setLayoutManager(manager);

        if (mProduceList!=null){
            mProduceList.setHasFixedSize(true);
        }

        mFirebaseAdapter = new FirebaseRecyclerAdapter<ProduceSetting, ProduceViewHolder>(
                ProduceSetting.class, R.layout.card_item_produce, ProduceViewHolder.class, mDatabase
        ) {
            @Override
            protected void populateViewHolder(ProduceViewHolder viewHolder, ProduceSetting model, int position) {
                viewHolder.setName(model.getGameName());
                viewHolder.setType(model.getGameType());
                viewHolder.setImage(getActivity().getApplicationContext(), model.getImage());
            }
        };

        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition =
                        manager.findLastCompletelyVisibleItemPosition();
                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    mProduceList.scrollToPosition(positionStart);
                }
            }
        });

        mProduceList.setLayoutManager(manager);
        mFirebaseAdapter.notifyDataSetChanged();
        mProduceList.setAdapter(mFirebaseAdapter);
        return rootView;
    }


    public static class ProduceViewHolder extends RecyclerView.ViewHolder {

        View mView;
        public ProduceViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setName(String name) {
            TextView post_name = (TextView) mView.findViewById(R.id.post_name);
            post_name.setText(name);
        }
        public void setType(String type) {
            TextView post_type = (TextView) mView.findViewById(R.id.post_type);
            post_type.setText(type);
        }
        public void setImage(Context ctx, String image) {
            ImageView post_image = (ImageView) mView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).into(post_image);
        }
    }


}
