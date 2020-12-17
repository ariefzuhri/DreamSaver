package com.ppab1.dreamsaver.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ppab1.dreamsaver.model.Target;
import com.ppab1.dreamsaver.adapter.TargetAdapter;

import com.ppab1.dreamsaver.R;

import static com.ppab1.dreamsaver.utils.AppUtils.getRemainingDays;
import static com.ppab1.dreamsaver.utils.AppUtils.getRupiahFormat;
import static com.ppab1.dreamsaver.utils.DateUtils.addDay;
import static com.ppab1.dreamsaver.utils.DateUtils.getCurrentDate;
import static com.ppab1.dreamsaver.utils.DateUtils.getFullDate;

public class LaporanActivity extends AppCompatActivity {
    private TextView tvDailyReport, tvTargetReport, tvDateReport, tvResultReport, tvRemainingDaysReport,
    tvRemainingDateReport;

    public static final String EXTRA_TARGET = "extra_target";
    private TargetAdapter adapter;
    private Target target;
    private boolean isUpdate;
    private String dateTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan);

        tvDailyReport = findViewById(R.id.tv_daily_report_target);
        tvTargetReport = findViewById(R.id.tv_target_report_target);
        tvDateReport = findViewById(R.id.tv_date_report_target);
        tvResultReport = findViewById(R.id.tv_result_report_target);
        tvRemainingDaysReport = findViewById(R.id.tv_remaining_days_report);
        tvRemainingDateReport = findViewById(R.id.tv_remaining_date_report);

        TextView tvTitle = findViewById(R.id.tv_title_title);

        ImageButton buttonTitle = findViewById(R.id.ib_back_title);
        buttonTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        isUpdate = intent.hasExtra(EXTRA_TARGET);
        if (isUpdate){
            target = intent.getParcelableExtra(EXTRA_TARGET);
            tvTitle.setText(target.getName());
            tvDailyReport.setText(getRupiahFormat(target.getDailyTarget()));
            tvDateReport.setText(getFullDate(target.getDateTarget(), false));
            tvTargetReport.setText(getRupiahFormat(target.getSavingsTarget()));
            tvResultReport.setText(getRupiahFormat(target.getTotalSavings()));

            int remainingDays = getRemainingDays(target.getSavingsTarget(), target.getTotalSavings(), target.getDailyTarget());
            tvRemainingDaysReport.setText(String.valueOf(remainingDays));
            tvRemainingDateReport.setText(getFullDate(addDay(getCurrentDate(), remainingDays), false));

        }


    }
}