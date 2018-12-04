package com.example.advancedalarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private ListView alarmList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar actionBar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(actionBar);
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
                Intent makeAlarmIntent = new Intent(MainActivity.this, EditAlarmActivity.class);
        }
        return true;
    }

    private void wireWidgets() {
        alarmList = findViewById(R.id.listView_main_alarms);
    }

    private void populateViews() {

    }

    private void setListeners() {
        alarmList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editAlarmIntent = new Intent(MainActivity.this, EditAlarmActivity.class);
                editAlarmIntent.putExtra("alarmId", id);
            }
        });
    }
}
