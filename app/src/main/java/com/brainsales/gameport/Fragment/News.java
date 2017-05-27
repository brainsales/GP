package com.brainsales.gameport.Fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brainsales.gameport.R;
import com.brainsales.gameport.adapter.FragmentAdapter;

/**
 * Created by Ryu on 2017-05-23.
 */

public class News extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.news_recycler_view);
        recyclerView.setHasFixedSize(true);

        FragmentAdapter adapter = new FragmentAdapter();
        recyclerView.setAdapter(adapter);

        /*LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);*/

        return rootView;
    }
}
