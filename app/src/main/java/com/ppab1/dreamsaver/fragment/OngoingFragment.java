package com.ppab1.dreamsaver.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ppab1.dreamsaver.R;
import com.ppab1.dreamsaver.activity.AddUpdateActivity;
import com.ppab1.dreamsaver.adapter.TargetAdapter;
import com.ppab1.dreamsaver.callback.TargetMoveCallback;
import com.ppab1.dreamsaver.database.DatabaseContract;
import com.ppab1.dreamsaver.model.Target;
import com.ppab1.dreamsaver.testing.DatabaseActivity;
import com.ppab1.dreamsaver.callback.LoadTargetCallback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.ppab1.dreamsaver.database.MappingHelper.mapCursorToTargetList;

public class OngoingFragment extends Fragment implements LoadTargetCallback {
    private static final String TAG = DatabaseActivity.class.getSimpleName();
    private TargetAdapter adapter;

    public OngoingFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ongoing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_rencana);
        recyclerView.setHasFixedSize(true);
        adapter = new TargetAdapter(getActivity());
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        View addButton = view.findViewById(R.id.addButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ItemTouchHelper.Callback callback = new TargetMoveCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddUpdateActivity.class);
                startActivity(intent);
            }
        });
        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        OngoingFragment.DataObserver dataObserver = new OngoingFragment.DataObserver(handler, getActivity(), this);
        getActivity().getContentResolver().registerContentObserver(DatabaseContract.TargetColumns.CONTENT_URI, true, dataObserver);

        if (savedInstanceState == null) new OngoingFragment.LoadTargetAsync(getActivity(), this).execute();
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

    private static class LoadTargetAsync extends AsyncTask<Void, Void, ArrayList<Target>> {
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
            Cursor cursor = weakContext.get().getContentResolver().query(DatabaseContract.TargetColumns.CONTENT_URI,
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

    public static class DataObserver extends ContentObserver {
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
            new OngoingFragment.LoadTargetAsync(context, callback).execute();
        }
    }
}