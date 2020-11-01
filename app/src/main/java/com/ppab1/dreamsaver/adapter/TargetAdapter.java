package com.ppab1.dreamsaver.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ppab1.dreamsaver.R;
import com.ppab1.dreamsaver.model.Target;

import java.util.ArrayList;

import static com.ppab1.dreamsaver.utils.AppUtils.getRemainingDays;
import static com.ppab1.dreamsaver.utils.AppUtils.getRupiahFormat;
import static com.ppab1.dreamsaver.utils.DateUtils.addDay;
import static com.ppab1.dreamsaver.utils.DateUtils.getCurrentDate;
import static com.ppab1.dreamsaver.utils.DateUtils.getFullDate;

public class TargetAdapter extends RecyclerView.Adapter<TargetAdapter.TargetViewHolder> {
    private final Activity activity;
    private final ArrayList<Target> listTarget = new ArrayList<>();

    public TargetAdapter(Activity activity){
        this.activity = activity;
    }

    public void setData(ArrayList<Target> listTarget){
        this.listTarget.clear();
        this.listTarget.addAll(listTarget);
        notifyDataSetChanged();
    }

    public ArrayList<Target> getData(){
        return listTarget;
    }

    @NonNull
    @Override
    public TargetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_target_main, parent, false);
        return new TargetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TargetViewHolder holder, int position) {
        Target target = listTarget.get(position);
        holder.bind(target);
    }

    @Override
    public int getItemCount() {
        return listTarget.size();
    }

    public static class TargetViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName, tvTotalSavings, tvSavingsTarget, tvDailyTarget, tvDateTarget;
        private final TextView tvRemainingDays, tvRemainingDate, tvSavingsToday;

        public TargetViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name_target);
            tvTotalSavings = itemView.findViewById(R.id.tv_total_savings_target);
            tvSavingsTarget = itemView.findViewById(R.id.tv_savings_target_target);
            tvDailyTarget = itemView.findViewById(R.id.tv_daily_target_target);
            tvDateTarget = itemView.findViewById(R.id.tv_date_target_target);

            tvRemainingDays = itemView.findViewById(R.id.tv_remaining_days_target);
            tvRemainingDate = itemView.findViewById(R.id.tv_remaining_date_target);
            tvSavingsToday = itemView.findViewById(R.id.tv_savings_today_target);
        }

        public void bind(Target target) {
            tvName.setText(target.getName());
            tvTotalSavings.setText(getRupiahFormat(target.getTotalSavings()));
            tvSavingsTarget.setText(getRupiahFormat(target.getSavingsTarget()));
            tvDailyTarget.setText(getRupiahFormat(target.getDailyTarget()));
            tvDateTarget.setText(getFullDate(target.getDateTarget(), false));

            int remainingDays = getRemainingDays(target.getSavingsTarget(), target.getTotalSavings(), target.getDailyTarget());
            tvRemainingDays.setText(String.valueOf(remainingDays));
            tvRemainingDate.setText(getFullDate(addDay(getCurrentDate(), remainingDays), false));
            tvSavingsToday.setText(getRupiahFormat(0));
        }
    }
}
