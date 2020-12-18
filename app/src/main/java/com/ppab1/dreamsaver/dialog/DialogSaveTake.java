package com.ppab1.dreamsaver.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ppab1.dreamsaver.R;
import com.ppab1.dreamsaver.database.DatabaseContract.TargetColumns;
import com.ppab1.dreamsaver.database.DatabaseContract.HistoryColumns;
import com.ppab1.dreamsaver.model.Target;

import static com.ppab1.dreamsaver.utils.AppUtils.getRupiahFormat;
import static com.ppab1.dreamsaver.utils.AppUtils.showToast;
import static com.ppab1.dreamsaver.utils.DateUtils.getCurrentDate;
import static com.ppab1.dreamsaver.utils.DateUtils.getCurrentTime;
import static com.ppab1.dreamsaver.utils.EditTextUtils.getFixText;
import static com.ppab1.dreamsaver.utils.EditTextUtils.isNull;
import static com.ppab1.dreamsaver.utils.EditTextUtils.isZero;

public class DialogSaveTake extends DialogFragment implements View.OnClickListener {
    public static final String EXTRA_TARGET = "extra_target";

    private AlertDialog dialog;
    private EditText edtNominal, edtDesc;
    private Target target;
    private RadioGroup rgSaveTake;

    public DialogSaveTake(){}

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_save_take, null);

        TextView tvTitle = view.findViewById(R.id.tv_title_title);
        tvTitle.setText("Simpan/Ambil Uang");

        ImageButton ibBack = view.findViewById(R.id.ib_back_title);
        ibBack.setOnClickListener(this);

        Button btnSave = view.findViewById(R.id.btn_save_st);
        Button btnCancel = view.findViewById(R.id.btn_cancel_st);
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        edtNominal = view.findViewById(R.id.edt_nominal_st);
        edtDesc = view.findViewById(R.id.edt_desc_st);

        rgSaveTake = view.findViewById(R.id.rg_save_take_st);
        rgSaveTake.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if (id == R.id.rb_save_st){
                    edtDesc.setText("");
                    edtDesc.setVisibility(View.INVISIBLE);
                } else edtDesc.setVisibility(View.VISIBLE);
            }
        });

        // Receive bundle from activity
        Bundle bundle = getArguments();
        target = bundle.getParcelable(EXTRA_TARGET);

        // Create alert dialog instance
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        dialog = builder.create();
        return dialog;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_save_st) {
            boolean isSave = rgSaveTake.getCheckedRadioButtonId() == R.id.rb_save_st;

            String nominal;
            if (isSave) nominal = getFixText(edtNominal);
            else nominal = "-" + getFixText(edtNominal);
            String desc = getFixText(edtDesc);

            // Validasi
            if (isNull(nominal)){
                edtNominal.setError("Anda belum mengisi nominal");
                return;
            } else {
                if (isZero(getFixText(edtNominal))){
                    edtNominal.setError("Angka harus lebih dari nol");
                    return;
                } else if (!isSave && Math.abs(Long.parseLong(nominal)) > target.getTotalSavings()){
                    edtNominal.setError("Uang pada tabungan Anda " + getRupiahFormat(target.getTotalSavings()) + " tidak mencukupi");
                    return;
                }
            }

            if (!isSave && isNull(desc)){
                edtDesc.setError("Anda belum mengisi alasan mengambil");
                return;
            }

            // Simpan
            ContentValues contentValues = new ContentValues();
            contentValues.put(HistoryColumns.ID_TARGET, target.getId());
            contentValues.put(HistoryColumns.NOMINAL, nominal);
            contentValues.put(HistoryColumns.DESC, desc);
            contentValues.put(HistoryColumns.DATE, getCurrentDate());
            contentValues.put(HistoryColumns.TIME, getCurrentTime());

            getActivity().getContentResolver().insert(HistoryColumns.CONTENT_URI, contentValues);

            // Perbarui total savings
            ContentValues contentValuesTarget = new ContentValues();
            contentValuesTarget.put(TargetColumns.NAME, target.getName());
            contentValuesTarget.put(TargetColumns.DAILY_TARGET, target.getDailyTarget());
            contentValuesTarget.put(TargetColumns.SAVINGS_TARGET, target.getSavingsTarget());
            contentValuesTarget.put(TargetColumns.DATE_TARGET, target.getDateTarget());
            contentValuesTarget.put(TargetColumns.POSITION, target.getPosition());
            contentValuesTarget.put(TargetColumns.TOTAL_SAVINGS, target.getTotalSavings() + Long.parseLong(nominal)); // Ini

            Uri uri = Uri.parse(TargetColumns.CONTENT_URI + "/" + target.getId());
            getActivity().getContentResolver().update(uri, contentValuesTarget, null, null);

            // Tampilkan pesan
            String message;
            boolean isFinished = target.getTotalSavings() - target.getSavingsTarget()+Long.parseLong(nominal) >= 0;
            if (!isFinished) {
                if (isSave) message = "Kamu berhasil menabung " + getRupiahFormat(Long.parseLong(nominal)) + " untuk tabungan " + target.getName();
                else  message = "Kamu berhasil mengambil uang " + getRupiahFormat(Math.abs(Long.parseLong(nominal))) + " dari tabungan " + target.getName();
            } else {
                message = "Selamat! Tabungan untuk " + target.getName() + " telah selesai.";
            }

            showToast(getActivity(), message);
        }

        dialog.dismiss();
    }
}
