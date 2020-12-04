package com.ppab1.dreamsaver.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ppab1.dreamsaver.R;
import com.ppab1.dreamsaver.activity.MainActivity;
import com.ppab1.dreamsaver.model.Target;
import com.ppab1.dreamsaver.testing.AddUpdateActivity;
import com.ppab1.dreamsaver.testing.DatabaseActivity;

import java.util.ArrayList;

import static com.ppab1.dreamsaver.testing.AddUpdateActivity.EXTRA_TARGET;
import static com.ppab1.dreamsaver.utils.AppUtils.getRemainingDays;
import static com.ppab1.dreamsaver.utils.AppUtils.getRupiahFormat;
import static com.ppab1.dreamsaver.utils.AppUtils.showToast;
import static com.ppab1.dreamsaver.utils.DateUtils.addDay;
import static com.ppab1.dreamsaver.utils.DateUtils.getCurrentDate;
import static com.ppab1.dreamsaver.utils.DateUtils.getFullDate;

public class TargetAdapter extends RecyclerView.Adapter<TargetAdapter.TargetViewHolder> {
    private static final String TAG = TargetAdapter.class.getSimpleName();
    private final Activity activity;
    private final ArrayList<Target> targetList = new ArrayList<>();

    public TargetAdapter(Activity activity){
        this.activity = activity;
    }

    public void setData(ArrayList<Target> targetList){
        this.targetList.clear();
        this.targetList.addAll(targetList);
        notifyDataSetChanged();

        for (Target target : this.targetList){
            Log.d(TAG, target.getName());
        }
    }

    public ArrayList<Target> getData(){
        return targetList;
    }

    @NonNull
    @Override
    public TargetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int resource = 0;
        String activityName = activity.getClass().getSimpleName();

        if (activityName.equals(MainActivity.class.getSimpleName())) resource = R.layout.item_target_main;
        else if (activityName.equals(DatabaseActivity.class.getSimpleName())) resource = R.layout.item_target;

        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new TargetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TargetViewHolder holder, int position) {
        Target target = targetList.get(position);
        holder.bind(target);

        String activityName = activity.getClass().getSimpleName();
        if (activityName.equals(MainActivity.class.getSimpleName())){
            holder.itemView.findViewById(R.id.ib_manage_savings_target).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showToast(activity, "Tambah/ambil jumlah tabungan");
                }
            });

            final CardView cvNotification = holder.itemView.findViewById(R.id.cv_notification_target);
            final TextView tvReminder = holder.itemView.findViewById(R.id.tv_reminder_target);
            final TextView tvNotification = holder.itemView.findViewById(R.id.tv_notification_target);
            final TextView tvSavingsToday = holder.itemView.findViewById(R.id.tv_savings_today_target);

            int savingsToday = 5000; // Dummy
            if (savingsToday >= target.getDailyTarget()){
                cvNotification.setCardBackgroundColor(activity.getResources().getColor(R.color.green));
                if (savingsToday == target.getDailyTarget()) tvNotification.setText(R.string.notification_3_target);
                else tvNotification.setText(R.string.notification_4_target);
            } else {
                cvNotification.setCardBackgroundColor(activity.getResources().getColor(R.color.red));
                if (savingsToday == 0) tvNotification.setText(R.string.notification_1_target);
                else tvNotification.setText(R.string.notification_2_target);
            }

            tvReminder.setText("12:00");
            tvSavingsToday.setText(getRupiahFormat(savingsToday));
        }
        else if (activityName.equals(DatabaseActivity.class.getSimpleName())){
            holder.itemView.findViewById(R.id.ib_menu_target).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, AddUpdateActivity.class);
                    intent.putExtra(EXTRA_TARGET, target);
                    activity.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return targetList.size();
    }

    public static class TargetViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName, tvTotalSavings, tvSavingsTarget, tvDailyTarget, tvDateTarget;
        private final TextView tvRemainingDays, tvRemainingDate;

        public TargetViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name_target);
            tvTotalSavings = itemView.findViewById(R.id.tv_total_savings_target);
            tvSavingsTarget = itemView.findViewById(R.id.tv_savings_target_target);
            tvDailyTarget = itemView.findViewById(R.id.tv_daily_target_target);
            tvDateTarget = itemView.findViewById(R.id.tv_date_target_target);

            tvRemainingDays = itemView.findViewById(R.id.tv_remaining_days_target);
            tvRemainingDate = itemView.findViewById(R.id.tv_remaining_date_target);
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
        }
    }
}
