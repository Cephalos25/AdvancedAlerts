package com.example.advancedalarm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AlarmViewActivity extends AppCompatActivity {
    static final String TAG = "AlarmViewActivity";
    private ListView alarmListView;

    private Gson gson = new Gson();
    SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar actionBar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(actionBar);
        Context context = getApplicationContext();
        AndroidThreeTen.init(this);
        mPref = getSharedPreferences(getString(R.string.sharedpreferences_key), MODE_PRIVATE);

        wireWidgets();
        populateViews();
        setListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Type listType = new TypeToken<ArrayList<Alarm>>() {}.getType();
        SharedPreferencesOperator.getListToList(Alarm.alarmList, mPref, "alarm list", listType);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferencesOperator.putList(mPref, "alarm list", Alarm.alarmList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_newalert:
                Intent makeAlarmIntent = new Intent(AlarmViewActivity.this, EditAlarmActivity.class);
                makeAlarmIntent.putExtra("editalarm", false);
                startActivity(makeAlarmIntent);
        }
        return true;
    }

    private void wireWidgets() {
        alarmListView = findViewById(R.id.listView_main_alarms);
    }

    private void populateViews() {
        AlarmAdapter alarmListAdapter = new AlarmAdapter(this, Alarm.alarmList);
        alarmListView.setAdapter(alarmListAdapter);
    }

    private void setListeners() {
        alarmListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editAlarmIntent = new Intent(AlarmViewActivity.this, EditAlarmActivity.class);
                editAlarmIntent.putExtra("editalarm", true);
                editAlarmIntent.putExtra("alarmId", id);
                Log.d(TAG, ""+id);
                startActivity(editAlarmIntent);
            }
        });
    }
}
