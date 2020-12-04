package com.ppab1.dreamsaver.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ppab1.dreamsaver.R;
import com.ppab1.dreamsaver.database.DatabaseContract.TargetColumns;
import com.ppab1.dreamsaver.model.Target;

import static com.ppab1.dreamsaver.utils.EditTextUtils.getFixText;

public class AddUpdateActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_TARGET = "extra_target";

    private EditText edtName, edtDailyTarget, edtSavingsTarget, edtDateTarget;

    private Target target;
    private boolean isUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update);

        edtName = findViewById(R.id.edt_name_au);
        edtDailyTarget = findViewById(R.id.edt_daily_target_au);
        edtSavingsTarget = findViewById(R.id.edt_savings_target_au);
        edtDateTarget = findViewById(R.id.edt_date_target_au);

        Button btnSave = findViewById(R.id.btn_save_au);
        Button btnDelete = findViewById(R.id.btn_delete_au);
        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        Intent intent = getIntent();
        isUpdate = intent.hasExtra(EXTRA_TARGET);
        if (isUpdate){
            target = intent.getParcelableExtra(EXTRA_TARGET);
            edtName.setText(target.getName());
            edtDailyTarget.setText(String.valueOf(target.getDailyTarget()));
            edtSavingsTarget.setText(String.valueOf(target.getSavingsTarget()));
            edtDateTarget.setText(target.getDateTarget());
        } else {
            target = new Target();
            btnDelete.setVisibility(View.INVISIBLE);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_save_au:
                String name = getFixText(edtName);
                String dailyTarget = getFixText(edtDailyTarget);
                String savingsTarget = getFixText(edtSavingsTarget);
                String dateTarget = getFixText(edtDateTarget);

                target.setName(name);
                target.setDailyTarget(Long.parseLong(dailyTarget));
                target.setSavingsTarget(Long.parseLong(savingsTarget));
                target.setDateTarget(dateTarget);

                ContentValues contentValues = new ContentValues();
                contentValues.put(TargetColumns.NAME, name);
                contentValues.put(TargetColumns.DAILY_TARGET, dailyTarget);
                contentValues.put(TargetColumns.SAVINGS_TARGET, savingsTarget);
                contentValues.put(TargetColumns.DATE_TARGET, dateTarget);
                contentValues.put(TargetColumns.TOTAL_SAVINGS, 0);

                if (isUpdate){
                    Uri uri = Uri.parse(TargetColumns.CONTENT_URI + "/" + target.getId());
                    getContentResolver().update(uri, contentValues, null, null);
                } else {
                    getContentResolver().insert(TargetColumns.CONTENT_URI, contentValues);
                }

                onBackPressed();
                break;

            case R.id.btn_delete_au:
                Uri uri = Uri.parse(TargetColumns.CONTENT_URI + "/" + target.getId());
                getContentResolver().delete(uri, null, null);
                onBackPressed();
                break;
        }
    }
}