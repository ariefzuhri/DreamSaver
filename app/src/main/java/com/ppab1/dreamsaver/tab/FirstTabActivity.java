package com.ppab1.dreamsaver.tab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ppab1.dreamsaver.R;
import com.ppab1.dreamsaver.adapter.ItemAdapter;
import com.ppab1.dreamsaver.data.ItemData;

import java.util.ArrayList;

public class FirstTabActivity extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private ArrayList<ItemData> itemValues;
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private View addButton;

    public FirstTabActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_first_tab, container, false);

        recyclerView = view.findViewById(R.id.recycler_rencana);
        addButton = view.findViewById(R.id.addButton);

        itemValues = new ArrayList<>();

            ItemData item = new ItemData();

            item.dailyTarget = "Daily ";
            item.totalTarget = "Total";
            item.savingTarget = "Saving";
            item.dateTarget = "Date";

            itemValues.add(item);

        itemAdapter = new ItemAdapter(getActivity(),itemValues);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);

        GridLayoutManager layoutManager1 = new GridLayoutManager(getActivity(),2);

        recyclerView.setLayoutManager(layoutManager1);
        recyclerView.setAdapter(itemAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();
            }
        });
        return view;
    }
    private void addData(){
        ItemData item = new ItemData();
        item.dateTarget = "Date yang Baru";
        item.dailyTarget = "Target Data yang Baru";
        item.savingTarget = "Saving yang Baru";
        item.totalTarget = "Total yang baru";
        itemValues.add(item);
        itemAdapter.notifyDataSetChanged();
    }
}