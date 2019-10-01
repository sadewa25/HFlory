package com.example.h_flory.home;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.h_flory.R;
import com.example.h_flory.model.DataModel;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    TextView name, email;
    private RecyclerView recyclerView;
    Button btn_logout;
    private static String URL_READ = "http://10.42.0.1/login_register/read_detail.php";
    private static String URL_EDIT = "http://10.42.0.1/login_register/edit_detail.php";

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_komoditi, container, false);
    }

    private HomeAdapter adapter;
    private ArrayList<DataModel> dataItems;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        recyclerView = view.findViewById(R.id.recycler_view);

        dataItems = new ArrayList<>();
        dataItems.add(new DataModel("Cabai","Cabai Indonesia",0,R.drawable.ic_cabai));
        dataItems.add(new DataModel("Rosella","Rosella Indonesia",0,R.drawable.ic_rosella));
        dataItems.add(new DataModel("Cengkeh","Cengkeh Dataran Tinggi",0,R.drawable.ic_cengkeh));

        adapter = new HomeAdapter(dataItems,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }


}