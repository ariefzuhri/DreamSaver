package com.ppab1.dreamsaver.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ppab1.dreamsaver.R;
import com.ppab1.dreamsaver.activity.AboutActivity;
import com.ppab1.dreamsaver.activity.LaporanActivity;
import com.ppab1.dreamsaver.activity.MainActivity;
import com.ppab1.dreamsaver.activity.TargetActivity;
import com.ppab1.dreamsaver.callback.TargetMoveCallback;
import com.ppab1.dreamsaver.database.DatabaseContract.TargetColumns;
import com.ppab1.dreamsaver.dialog.DialogSaveTake;
import com.ppab1.dreamsaver.model.Target;
import com.ppab1.dreamsaver.activity.AddUpdateActivity;

import java.util.ArrayList;
import java.util.Collections;

import static com.ppab1.dreamsaver.activity.AddUpdateActivity.EXTRA_TARGET;
import static com.ppab1.dreamsaver.utils.AppUtils.getRemainingDays;
import static com.ppab1.dreamsaver.utils.AppUtils.getRupiahFormat;
import static com.ppab1.dreamsaver.utils.AppUtils.showToast;
import static com.ppab1.dreamsaver.utils.DateUtils.addDay;
import static com.ppab1.dreamsaver.utils.DateUtils.getCurrentDate;
import static com.ppab1.dreamsaver.utils.DateUtils.getFullDate;

public class TargetAdapter extends RecyclerView.Adapter<TargetAdapter.TargetViewHolder> implements TargetMoveCallback.ItemTouchHelperContract {
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
        else  resource = R.layout.item_target;

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
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(EXTRA_TARGET, target);

                    DialogSaveTake dialogSaveTake = new DialogSaveTake();
                    dialogSaveTake.setArguments(bundle);
                    dialogSaveTake.show(((AppCompatActivity) activity).getSupportFragmentManager(), dialogSaveTake.getTag());
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
        else {
            ImageButton ibMenu = holder.itemView.findViewById(R.id.ib_menu_target);
            if (target.getTotalSavings() == target.getSavingsTarget()) ibMenu.setVisibility(View.INVISIBLE); // Kalau sudah selesai, sembunyikan
            ibMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu menu = new PopupMenu(activity, view);
                    MenuInflater inflater = menu.getMenuInflater();
                    inflater.inflate(R.menu.menu_target, menu.getMenu());
                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @SuppressLint("NonConstantResourceId")
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()){
                                case R.id.menu_update_target:
                                    Intent intentUpdate = new Intent(activity, AddUpdateActivity.class);
                                    intentUpdate.putExtra(EXTRA_TARGET, target);
                                    activity.startActivity(intentUpdate);
                                    break;

                                case R.id.menu_save_take_target:
                                    Bundle bundle = new Bundle();
                                    bundle.putParcelable(EXTRA_TARGET, target);

                                    DialogSaveTake dialogSaveTake = new DialogSaveTake();
                                    dialogSaveTake.setArguments(bundle);
                                    dialogSaveTake.show(((AppCompatActivity) activity).getSupportFragmentManager(), dialogSaveTake.getTag());
                                    break;

                                case R.id.menu_report_target:
                                    Intent intentReport = new Intent(activity, LaporanActivity.class);
                                    intentReport.putExtra(EXTRA_TARGET, target);
                                    activity.startActivity(intentReport);
                                    break;

                                case R.id.menu_delete_target:
                                    new AlertDialog.Builder(activity)
                                            .setTitle("Hapus rencana")
                                            .setMessage("Apakah Anda yakin ingin menghapus rencana/target ini?")
                                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    Uri uri = Uri.parse(TargetColumns.CONTENT_URI + "/" + target.getId());
                                                    activity.getContentResolver().delete(uri, null, null);
                                                    showToast(activity, "Rencana berhasil dihapus");
                                                }
                                            })
                                            .setNegativeButton("Tidak", null)
                                            .setNeutralButton("Batal", null)
                                            .create().show();
                                    break;
                            }
                            return false;
                        }
                    });
                    menu.show();
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

    @Override
    // Metode ini terpanggil ketika item dipindah (setelah drag dan drop)
    public void onRowMoved(int fromPosition, int toPosition) {
        if (fromPosition < toPosition)
            for (int i = fromPosition; i < toPosition; i++) Collections.swap(targetList, i, i+1); // Jika posisinya jadi maju, swap terus-menerus sampai posisi yang sesuai
        else for (int i = fromPosition; i > toPosition; i--) Collections.swap(targetList, i, i-1); // mundur
        notifyItemMoved(fromPosition, toPosition); // Refresh adapter karena ada item yang berubah posisi
    }

    @Override
    // Metode ini terpanggil ketika mulai dan sedang drag
    public void onRowSelected(TargetViewHolder viewHolder) {
        viewHolder.itemView.setBackgroundColor(activity.getResources().getColor(R.color.gray));
    }

    @Override
    // Metode ini terpanggil ketika selesai men-drag
    public void onRowClear(TargetViewHolder viewHolder) {
        viewHolder.itemView.setBackgroundColor(activity.getResources().getColor(R.color.white));

        for (int i = 0; i < getItemCount(); i++){
            Target target = targetList.get(i);

            ContentValues contentValues = new ContentValues();
            contentValues.put(TargetColumns.NAME, target.getName());
            contentValues.put(TargetColumns.DAILY_TARGET, target.getDailyTarget());
            contentValues.put(TargetColumns.SAVINGS_TARGET, target.getSavingsTarget());
            contentValues.put(TargetColumns.DATE_TARGET, target.getDateTarget());
            contentValues.put(TargetColumns.TOTAL_SAVINGS, target.getTotalSavings());
            contentValues.put(TargetColumns.POSITION, i);

            Uri uri = Uri.parse(TargetColumns.CONTENT_URI + "/" + target.getId());
            activity.getContentResolver().update(uri, contentValues, null, null);
        }
    }
}
