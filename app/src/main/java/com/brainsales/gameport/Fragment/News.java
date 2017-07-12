package com.brainsales.gameport.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.brainsales.gameport.R;
import com.brainsales.gameport.utils.NewsSetting;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

/**
 * Created by Ryu on 2017-05-23.
 */

public class News extends Fragment {

    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<NewsSetting, NewsViewHolder> mFirebaseAdapter;
    private RecyclerView mAwardList = null;
    private View rootView;
    private ProgressDialog mProgress;

    private StaggeredGridLayoutManager manager;

    public News() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_news, container, false);

        mProgress = new ProgressDialog(getActivity());

        mAwardList = (RecyclerView) rootView.findViewById(R.id.news_recycler_view);
        manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mAwardList.setLayoutManager(manager);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Games");

        if (mAwardList != null) {
            mAwardList.setHasFixedSize(true);
        }

        mFirebaseAdapter = new FirebaseRecyclerAdapter<NewsSetting, NewsViewHolder>(
                NewsSetting.class, R.layout.news_item, NewsViewHolder.class, mDatabase
        ) {
            @Override
            protected void populateViewHolder(NewsViewHolder viewHolder, NewsSetting model, int position) {

                viewHolder.setImage(getActivity().getApplicationContext(), model.getImage());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(getActivity(), "To WebSite", Toast.LENGTH_LONG).show();

                    }
                });
            }
        };

        mAwardList.setLayoutManager(manager);
        mFirebaseAdapter.notifyDataSetChanged();
        mAwardList.setAdapter(mFirebaseAdapter);
        return rootView;
    }


    public static class NewsViewHolder extends RecyclerView.ViewHolder {

        View mView;
        public NewsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setImage(Context ctx, String image) {
            ImageView post_image = (ImageView) mView.findViewById(R.id.info_image);
            Picasso.with(ctx).load(image).into(post_image);
        }

    }
}
