package com.ppab1.dreamsaver.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.ppab1.dreamsaver.R;
import com.ppab1.dreamsaver.adapter.TargetAdapter;
import com.ppab1.dreamsaver.database.DatabaseContract.TargetColumns;
import com.ppab1.dreamsaver.model.Target;
import com.ppab1.dreamsaver.testing.DatabaseActivity;
import com.ppab1.dreamsaver.testing.LoadTargetCallback;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.ppab1.dreamsaver.database.MappingHelper.mapCursorToTargetList;
import static com.ppab1.dreamsaver.testing.DummyTarget.getDummyTarget;
import static com.ppab1.dreamsaver.utils.AppUtils.showToast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LoadTargetCallback {
    private static final String TAG = DatabaseActivity.class.getSimpleName();

    private TargetAdapter adapter;
    private PopupMenu menu;
    private ImageButton btnMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new TargetAdapter(this);
        adapter.notifyDataSetChanged();
        //loadDummyTarget(adapter);

        ViewPager2 viewPager = findViewById(R.id.vp_target_main);
        viewPager.setAdapter(adapter);

        DotsIndicator dots = findViewById(R.id.dots_target_main);
        dots.setViewPager2(viewPager);

        // Disable overscroll animation
        View child = viewPager.getChildAt(0);
        if (child instanceof RecyclerView) child.setOverScrollMode(View.OVER_SCROLL_NEVER);

        ImageButton btnAdd = findViewById(R.id.ib_add_main);
        btnAdd.setOnClickListener(this);
        btnMenu = findViewById(R.id.ib_menu_main);
        btnMenu.setOnClickListener(this);

        initializeMenu();

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        DataObserver dataObserver = new DataObserver(handler, this, this);
        getContentResolver().registerContentObserver(TargetColumns.CONTENT_URI, true, dataObserver);

        new LoadTargetAsync(this, this).execute();
    }

    private void initializeMenu() {
        menu = new PopupMenu(this, btnMenu);
        MenuInflater inflater = menu.getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu.getMenu());
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_manage_target_main:
                        Intent intentTarget = new Intent(MainActivity.this, RencanaActivity.class);
                        startActivity(intentTarget);
                        break;

                    case R.id.menu_settings_main:
                        showToast(MainActivity.this, getString(R.string.title_settings));
                        break;

                    case R.id.menu_about_main:
                        Intent intentAbout = new Intent(MainActivity.this, AboutActivity.class);
                        startActivity(intentAbout);
                        break;
                }
                return false;
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ib_add_main:
                Intent intent = new Intent(MainActivity.this, AddUpdateActivity.class);
                startActivity(intent);
                break;

            case R.id.ib_menu_main:
                menu.show();
                break;
        }
    }

    private void loadDummyTarget(TargetAdapter adapter) {
        ArrayList<Target> dummyData = getDummyTarget();
        ArrayList<Target> dummyTarget = new ArrayList<>();
        for (Target target : dummyData){
            if (target.getTotalSavings() < target.getSavingsTarget()) dummyTarget.add(target);
        }

        /*ArrayList<History> dummyHistory = getDummyHistory();
        for (Target target : dummyTarget){
            for (History history : dummyHistory){
                if (target.getId() == history.getId_target()){
                    target.setTotalSavings(target.getTotalSavings()+history.getNominal());
                }
            }
        }*/

        adapter.setData(dummyTarget);
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
            new LoadTargetAsync(context, callback).execute();
        }
    }
}