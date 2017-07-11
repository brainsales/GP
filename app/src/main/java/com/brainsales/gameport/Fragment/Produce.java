package com.brainsales.gameport.Fragment;

import android.content.Context;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brainsales.gameport.R;
import com.brainsales.gameport.ReviewActivity;
import com.brainsales.gameport.utils.ProduceSetting;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Games");
        manager.setStackFromEnd(true);
        manager.setReverseLayout(true);

        if (mProduceList!=null){
            mProduceList.setHasFixedSize(true);
        }

        mFirebaseAdapter = new FirebaseRecyclerAdapter<ProduceSetting, ProduceViewHolder>(
                ProduceSetting.class, R.layout.produce_item, ProduceViewHolder.class, mDatabase
        ) {
            @Override
            protected void populateViewHolder(ProduceViewHolder viewHolder, ProduceSetting model, int position) {
                viewHolder.setName(model.getGameName());
                viewHolder.setType(model.getGameType());
                viewHolder.setImage(getActivity().getApplicationContext(), model.getImage());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getActivity(), ReviewActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }
                });
            }
        };

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
            TextView post_name = (TextView) mView.findViewById(R.id.name_text);
            post_name.setText(name);
        }

        public void setType(String type) {
            TextView post_type = (TextView) mView.findViewById(R.id.info_text);
            post_type.setText(type);
        }

        public void setImage(Context ctx, String image) {
            ImageView post_image = (ImageView) mView.findViewById(R.id.game_image);
           // Picasso.with(ctx).load(image).into(post_image);

            Glide.with(ctx)
                    .load(image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .override(340, 216)
                    .centerCrop()
                    .into(post_image);

        }
    }
}
