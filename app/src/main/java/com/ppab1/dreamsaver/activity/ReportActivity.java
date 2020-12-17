package com.ppab1.dreamsaver.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ppab1.dreamsaver.adapter.HistoryAdapter;
import com.ppab1.dreamsaver.callback.LoadHistoryCallback;
import com.ppab1.dreamsaver.database.DatabaseContract.HistoryColumns;
import com.ppab1.dreamsaver.model.History;
import com.ppab1.dreamsaver.model.Target;

import com.ppab1.dreamsaver.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.ppab1.dreamsaver.database.MappingHelper.mapCursorToHistoryList;
import static com.ppab1.dreamsaver.utils.AppUtils.getRemainingDays;
import static com.ppab1.dreamsaver.utils.AppUtils.getRupiahFormat;
import static com.ppab1.dreamsaver.utils.DateUtils.addDay;
import static com.ppab1.dreamsaver.utils.DateUtils.getCurrentDate;
import static com.ppab1.dreamsaver.utils.DateUtils.getFullDate;

public class ReportActivity extends AppCompatActivity implements LoadHistoryCallback {
    public static final String EXTRA_TARGET = "extra_target";
    private HistoryAdapter adapter;
    private static Target target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        TextView tvDailyReport = findViewById(R.id.tv_daily_report_target);
        TextView tvTargetReport = findViewById(R.id.tv_target_report_target);
        TextView tvDateReport = findViewById(R.id.tv_date_report_target);
        TextView tvResultReport = findViewById(R.id.tv_result_report_target);
        TextView tvRemainingDaysReport = findViewById(R.id.tv_remaining_days_report);
        TextView tvRemainingDateReport = findViewById(R.id.tv_remaining_date_report);
        TextView tvPercentageReport = findViewById(R.id.tv_percentage);
        ProgressBar progressBar = findViewById(R.id.pb_percentage_report);

        TextView tvTitle = findViewById(R.id.tv_title_title);

        ImageButton buttonTitle = findViewById(R.id.ib_back_title);
        buttonTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.rv_report);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HistoryAdapter();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_TARGET)){
            target = intent.getParcelableExtra(EXTRA_TARGET);
            tvTitle.setText(target.getName());
            tvDailyReport.setText(getRupiahFormat(target.getDailyTarget()));
            tvDateReport.setText(getFullDate(target.getDateTarget(), false));
            tvTargetReport.setText(getRupiahFormat(target.getSavingsTarget()));
            tvResultReport.setText(getRupiahFormat(target.getTotalSavings()));

            int remainingDays = getRemainingDays(target.getSavingsTarget(), target.getTotalSavings(), target.getDailyTarget());
            tvRemainingDaysReport.setText(String.valueOf(remainingDays));
            tvRemainingDateReport.setText(getFullDate(addDay(getCurrentDate(), remainingDays), false));

            progressBar.setProgress((int) (target.getTotalSavings()/target.getSavingsTarget()));
            tvPercentageReport.setText(progressBar.getProgress() + "%");
        }

        new LoadHistoryAsync(this, this).execute();
    }

    @Override
    public void preExecute() {}

    @Override
    public void postExecute(ArrayList<History> historyList) {
        adapter.setData(historyList);
    }

    private static class LoadHistoryAsync extends AsyncTask<Void, Void, ArrayList<History>> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadHistoryCallback> weakCallback;

        private LoadHistoryAsync(Context context, LoadHistoryCallback callback){
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<History> doInBackground(Void... voids) {
            ArrayList<History> historyList = new ArrayList<>();
            Uri uri = Uri.parse(HistoryColumns.CONTENT_URI + "/targetId/" + target.getId());
            Cursor cursor = weakContext.get().getContentResolver().query(HistoryColumns.CONTENT_URI,
                    null, null, null, null);

            if (cursor != null){
                historyList.addAll(mapCursorToHistoryList(cursor));
                cursor.close();
            }

            return historyList;
        }

        @Override
        protected void onPostExecute(ArrayList<History> historyList) {
            super.onPostExecute(historyList);
            weakCallback.get().postExecute(historyList);
        }
    }
}