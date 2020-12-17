package com.ppab1.dreamsaver.testing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ppab1.dreamsaver.R;
import com.ppab1.dreamsaver.activity.AddUpdateActivity;
import com.ppab1.dreamsaver.adapter.TargetAdapter;
import com.ppab1.dreamsaver.callback.LoadTargetCallback;
import com.ppab1.dreamsaver.database.DatabaseContract.TargetColumns;
import com.ppab1.dreamsaver.model.Target;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.ppab1.dreamsaver.database.MappingHelper.mapCursorToTargetList;

public class DatabaseActivity extends AppCompatActivity implements LoadTargetCallback {
    private static final String TAG = DatabaseActivity.class.getSimpleName();
    private TargetAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        RecyclerView recyclerView = findViewById(R.id.rv_target_database);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TargetAdapter(this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        FloatingActionButton fabAdd = findViewById(R.id.fab_add_database);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DatabaseActivity.this, AddUpdateActivity.class);
                startActivity(intent);
            }
        });

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        DataObserver dataObserver = new DataObserver(handler, this, this);
        getContentResolver().registerContentObserver(TargetColumns.CONTENT_URI, true, dataObserver);

        if (savedInstanceState == null) new LoadTargetAsync(this, this).execute();
    }

    @Override
    public void preExecute() {}

    @Override
    public void postExecute(ArrayList<Target> targetList) {
        for (Target target : targetList){
            Log.d(TAG, target.getName());
        }

        adapter.setData(targetList);
    }

    private static class LoadTargetAsync extends AsyncTask<Void, Void, ArrayList<Target>>{
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadTargetCallback> weakCallback;

        private LoadTargetAsync(Context context, LoadTargetCallback callback){
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Target> doInBackground(Void... voids) {
            ArrayList<Target> targetList = new ArrayList<>();
            Cursor cursor = weakContext.get().getContentResolver().query(TargetColumns.CONTENT_URI,
                    null, null, null, null);

            if (cursor != null){
                targetList.addAll(mapCursorToTargetList(cursor));
                cursor.close();
            }

            return targetList;
        }

        @Override
        protected void onPostExecute(ArrayList<Target> targetList) {
            super.onPostExecute(targetList);
            weakCallback.get().postExecute(targetList);
        }
    }

    public static class DataObserver extends ContentObserver{
        private final Context context;
        private final LoadTargetCallback callback;

        DataObserver(Handler handler, Context context, LoadTargetCallback callback) {
            super(handler);
            this.context = context;
            this.callback = callback;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadTargetAsync(context, callback).execute();
        }
    }
}