package com.example.h_flory.alat;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.h_flory.DetailActivity;
import com.example.h_flory.R;
import com.example.h_flory.model.DataModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlatFragment extends Fragment implements View.OnClickListener {

    FloatingActionButton btnTambahAlat;
    AlatAdapter adapter;
    private RecyclerView recyclerView;

    public AlatFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_alat, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        btnTambahAlat = view.findViewById(R.id.btn_actAlat);
        btnTambahAlat.setOnClickListener(this);

        recyclerView = view.findViewById(R.id.recycler_view);

        ArrayList<DataModel> data = new ArrayList<>();
        data.add(new DataModel("Alat 1","20:00",0));
        data.add(new DataModel("Alat 2","12:00",1));
        data.add(new DataModel("Alat 3","21:00",1));


        adapter = new AlatAdapter(data,getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }

}