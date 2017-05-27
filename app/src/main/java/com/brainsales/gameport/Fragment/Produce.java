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
import com.brainsales.gameport.adapter.ProduceAdapter;

/**
 * Created by Ryu on 2017-05-23.
 */

public class Produce extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_produce, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.produce_recycler_view);
        recyclerView.setHasFixedSize(true);

        ProduceAdapter adapter = new ProduceAdapter();
        recyclerView.setAdapter(adapter);

        /*LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);*/

        return rootView;
    }
}
