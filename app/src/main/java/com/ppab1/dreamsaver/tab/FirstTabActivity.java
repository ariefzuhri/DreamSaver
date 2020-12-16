package com.ppab1.dreamsaver.tab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.ppab1.dreamsaver.R;
import com.ppab1.dreamsaver.adapter.ItemAdapter;

import java.util.ArrayList;

public class FirstTabActivity extends AppCompatActivity {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_tab);
    }
}