package com.ppab1.dreamsaver.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ppab1.dreamsaver.R;
import com.ppab1.dreamsaver.activity.AddUpdateActivity;
import com.ppab1.dreamsaver.activity.LaporanActivity;
import com.ppab1.dreamsaver.activity.MainActivity;
import com.ppab1.dreamsaver.model.History;

import java.util.ArrayList;

import static com.ppab1.dreamsaver.activity.AddUpdateActivity.EXTRA_TARGET;
import static com.ppab1.dreamsaver.utils.AppUtils.getRemainingDays;
import static com.ppab1.dreamsaver.utils.AppUtils.getRupiahFormat;
import static com.ppab1.dreamsaver.utils.DateUtils.addDay;
import static com.ppab1.dreamsaver.utils.DateUtils.getCurrentDate;
import static com.ppab1.dreamsaver.utils.DateUtils.getFullDate;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private static final String TAG = TargetAdapter.class.getSimpleName();
    private final Activity activity;
    private final ArrayList<History> historyList = new ArrayList<>();

    public HistoryAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setData(ArrayList<History> historyList){
        this.historyList.clear();
        this.historyList.addAll(historyList);
        notifyDataSetChanged();
    }

    public ArrayList<History> getData(){
        return historyList;
    }


    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int resource = 0;
        String activityName = activity.getClass().getSimpleName();

        if (!activityName.equals(MainActivity.class.getSimpleName())) resource = R.layout.item_target;

        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new HistoryAdapter.HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.HistoryViewHolder holder, int position) {
        History history = historyList.get(position);
        holder.bind(history);


    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTimeReport, tvDateReport, tvNominalReport, tvDescReport;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTimeReport = itemView.findViewById(R.id.item_time);
            tvDateReport = itemView.findViewById(R.id.item_date);
            tvNominalReport = itemView.findViewById(R.id.tv_savings_report);
            tvDescReport = itemView.findViewById(R.id.desc_report);
        }

        public void bind(History history) {
            tvTimeReport.setText(history.getTime());
            tvDateReport.setText(getFullDate(history.getDate(), false));
            tvNominalReport.setText(getRupiahFormat(history.getNominal()));
            tvDescReport.setText(history.getDesc());

        }
    }
}
