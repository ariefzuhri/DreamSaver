package com.ppab1.dreamsaver.tab;

import android.os.Bundle;
import com.ppab1.dreamsaver.R;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class SecondTabActivity extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    private ArrayList<ItemData> itemValues;
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;

    public SecondTabActivity() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_second_tab, container, false);

        recyclerView = view.findViewById(R.id.recycler_rencana_2);

        itemValues = new ArrayList<>();

        ItemData item = new ItemData();

        item.dailyTarget = "Daily ";
        item.totalTarget = "Total";
        item.savingTarget = "Saving";
        item.dateTarget = "Date";

        itemValues.add(item);

        itemAdapter = new ItemAdapter(getActivity(),itemValues);

        GridLayoutManager layoutManager1 = new GridLayoutManager(getActivity(),1);

        recyclerView.setLayoutManager(layoutManager1);
        recyclerView.setAdapter(itemAdapter);

        return view;
    }
}