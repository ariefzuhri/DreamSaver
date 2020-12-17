package com.ppab1.dreamsaver.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ppab1.dreamsaver.R;
import com.ppab1.dreamsaver.model.History;

import java.util.ArrayList;

import static com.ppab1.dreamsaver.utils.AppUtils.getRupiahFormat;
import static com.ppab1.dreamsaver.utils.DateUtils.getFullDate;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private final ArrayList<History> historyList = new ArrayList<>();

    public HistoryAdapter() {}

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_report, parent, false);
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
        private final ImageView imgIcon;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTimeReport = itemView.findViewById(R.id.item_time);
            tvDateReport = itemView.findViewById(R.id.item_date);
            tvNominalReport = itemView.findViewById(R.id.tv_savings_report);
            tvDescReport = itemView.findViewById(R.id.desc_report);
            imgIcon = itemView.findViewById(R.id.img_icon_report);
        }

        public void bind(History history) {
            tvTimeReport.setText(history.getTime());
            tvDateReport.setText(getFullDate(history.getDate(), false));
            tvNominalReport.setText(getRupiahFormat(Math.abs(history.getNominal())));
            tvDescReport.setText(history.getDesc());

            if (history.getNominal() > 0){
                tvNominalReport.setTextColor(itemView.getResources().getColor(R.color.green));
                imgIcon.setImageResource(R.drawable.ic_baseline_archive_24_green);
            } else {
                tvNominalReport.setTextColor(itemView.getResources().getColor(R.color.red));
                imgIcon.setImageResource(R.drawable.ic_baseline_unarchive_24_red);
            }
        }
    }
}
