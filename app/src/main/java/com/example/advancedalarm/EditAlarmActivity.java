package com.example.advancedalarm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.time.LocalDateTime;

public class EditAlarmActivity extends AppCompatActivity {

    private NumberPicker monthPicker;
    private NumberPicker dayPicker;
    private NumberPicker yearPicker;
    private NumberPicker hourPicker;
    private NumberPicker minutePicker;
    private NumberPicker dayPartPicker;

    private TextView cancelView;
    private TextView deleteView;
    private TextView finishView;
    private EditText nameInput;
    private TextView dateIndicator;
    private TextView timeIndicator;
    private TextView importanceIndicator;
    private TextView importanceInteractableView;
    private TextView shortDescriptionView;
    private EditText shortDescriptionInput;

    private Button editDescriptionButton;

    private boolean is24HourFormat;
    private boolean receivedEditIntent;
    Alarm editedAlarm;

    private boolean importance = false;
    String alertDescription;

    SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editalarm);
        Intent receivedIntent = getIntent();
        receivedEditIntent = receivedIntent.getBooleanExtra("editalarm", false);
        editedAlarm = receivedIntent.getParcelableExtra("alarm");

        is24HourFormat = DateFormat.is24HourFormat(getApplicationContext());
        mPref = getSharedPreferences(getString(R.string.sharedpreferences_key), MODE_PRIVATE);

        wireWidgets();
        populateViews();
        if (editedAlarm != null) {
            populateViewsFromAlarm(editedAlarm);
        }
        setListeners();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferencesOperator.putList(mPref, "alarm list", Alarm.alarmList);
        AlarmMaker.eraseAlarms(this, Alarm.alarmList);
        AlarmMaker.placeAlarms(this, Alarm.alarmList);
    }

    private void wireWidgets() {
        monthPicker = findViewById(R.id.numberPicker_editalarm_month);
        dayPicker = findViewById(R.id.numberPicker_editalarm_day);
        yearPicker = findViewById(R.id.numberPicker_editalarm_year);
        hourPicker = findViewById(R.id.numberPicker_editalarm_hour);
        minutePicker = findViewById(R.id.numberPicker_editalarm_minute);
        dayPartPicker = findViewById(R.id.numberPicker_editalarm_daypart);
        cancelView = findViewById(R.id.textView_editalarm_cancel);
        deleteView = findViewById(R.id.textView_editalarm_delete);
        finishView = findViewById(R.id.textView_editalarm_finish);
        nameInput = findViewById(R.id.editText_editalarm_name);
        dateIndicator = findViewById(R.id.textView_editalarm_dateindicator);
        timeIndicator = findViewById(R.id.textView_editalarm_timeindicator);
        importanceIndicator = findViewById(R.id.textView_editalarm_importanceindicator);
        importanceInteractableView = findViewById(R.id.textView_editalarm_importance);
        shortDescriptionView = findViewById(R.id.textView_editalarm_shortdescription);
        shortDescriptionInput = findViewById(R.id.editText_editalarm_shortdescription);
        editDescriptionButton = findViewById(R.id.button_editalarm_editdescription);
    }

    private void populateViews() {
        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        dayPicker.setMinValue(1);
        dayPicker.setMaxValue(31);
        yearPicker.setMinValue(2018);
        yearPicker.setMaxValue(LocalDateTime.now().getYear()+10);
        if (is24HourFormat){
            hourPicker.setMinValue(0);
            hourPicker.setMaxValue(23);
            dayPartPicker.setMinValue(0);
            dayPartPicker.setMaxValue(0);
            dayPartPicker.setVisibility(View.INVISIBLE);
        } else {
            hourPicker.setMinValue(1);
            hourPicker.setMaxValue(12);
            dayPartPicker.setMinValue(0);
            dayPartPicker.setMaxValue(1);
            dayPartPicker.setDisplayedValues(new String[]{getString(R.string.AM), getString(R.string.PM)});
            dayPartPicker.setVisibility(View.VISIBLE);
        }
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);
        if (!receivedEditIntent){
            deleteView.setVisibility(View.INVISIBLE);
        } else {
            deleteView.setVisibility(View.VISIBLE);
        }
    }

    private void populateViewsFromAlarm(Alarm alarm) {
        monthPicker.setValue(alarm.getEventDate().getMonthValue());
        dayPicker.setValue(alarm.getEventDate().getDayOfMonth());
        yearPicker.setValue(alarm.getEventDate().getYear());
        minutePicker.setValue(alarm.getEventDate().getMinute());
        nameInput.setText(alarm.getName());
        if (is24HourFormat){
            hourPicker.setValue(alarm.getEventDate().getHour());
        } else {
            int hour;
            hour = alarm.getEventDate().getHour()%12;
            if (hour == 0){
                hour = 12;
            }
            hourPicker.setValue(hour);
            if (alarm.getEventDate().getHour()>12){
                dayPartPicker.setValue(1);
            } else {
                dayPartPicker.setValue(0);
            }
        }
        if (alarm.getAlert().isImportant()){
            importanceInteractableView.setText(getString(R.string.editalarm_highimportance));
            importance = true;
        } else {
            importanceInteractableView.setText(getString(R.string.editalarm_normalimportance));
            importance = false;
        }
    }

    private void setListeners() {
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cancelEditingIntent = new Intent(EditAlarmActivity.this, AlarmViewActivity.class);
                startActivity(cancelEditingIntent);
            }
        });
        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deleteAlarmIntent = new Intent(EditAlarmActivity.this, AlarmViewActivity.class);
                if (receivedEditIntent){
                    Alarm.alarmList.remove(editedAlarm);
                }
                startActivity(deleteAlarmIntent);
            }
        });
        finishView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent finishEditingIntent = new Intent(EditAlarmActivity.this, AlarmViewActivity.class);
                Alert alarmAlert = new Alert(shortDescriptionInput.getText().toString(), alertDescription, importance);
                if (receivedEditIntent){
                    editedAlarm.setAlert(alarmAlert);
                    LocalDateTime newEventDate;
                    if(is24HourFormat) {
                        newEventDate = LocalDateTime.of(yearPicker.getValue(), monthPicker.getValue(),
                                dayPicker.getValue(), hourPicker.getValue(), minutePicker.getValue());
                    } else {
                        newEventDate = LocalDateTime.of(yearPicker.getValue(), monthPicker.getValue(),
                                dayPicker.getValue(), hourPicker.getValue()%12+12*dayPartPicker.getValue(),
                                minutePicker.getValue());
                    }
                    editedAlarm.setEventDate(newEventDate);
                    Alarm.alarmList.set(Alarm.alarmList.indexOf(editedAlarm), editedAlarm);
                } else {
                    Alarm newAlarm = new Alarm();
                    newAlarm.setAlert(alarmAlert);
                    newAlarm.setName(nameInput.getText().toString());
                    LocalDateTime eventDate;
                    if(is24HourFormat) {
                        eventDate = LocalDateTime.of(yearPicker.getValue(), monthPicker.getValue(),
                                dayPicker.getValue(), hourPicker.getValue(), minutePicker.getValue());
                    } else {
                        eventDate = LocalDateTime.of(yearPicker.getValue(), monthPicker.getValue(),
                                dayPicker.getValue(), hourPicker.getValue()%12+12*dayPartPicker.getValue(),
                                minutePicker.getValue());
                    }
                    newAlarm.setEventDate(eventDate);
                    Alarm.alarmList.add(newAlarm);
                }
                startActivity(finishEditingIntent);
            }
        });
        importanceInteractableView.setOnClickListener(view -> {
            importance = !importance;
            if (importance){
                importanceInteractableView.setText(getString(R.string.editalarm_highimportance));
            } else {
                importanceInteractableView.setText(getString(R.string.editalarm_normalimportance));
            }
        });
        editDescriptionButton.setOnClickListener(view -> {
            Intent editDescriptionIntent = new Intent(EditAlarmActivity.this, EditDescriptionActivity.class);
            Alert alarmAlert = new Alert(shortDescriptionInput.getText().toString(), alertDescription, importance);
            if (receivedEditIntent){
                editedAlarm.setAlert(alarmAlert);
                LocalDateTime newEventDate;
                if(is24HourFormat) {
                    newEventDate = LocalDateTime.of(yearPicker.getValue(), monthPicker.getValue(),
                            dayPicker.getValue(), hourPicker.getValue(), minutePicker.getValue());
                } else {
                    newEventDate = LocalDateTime.of(yearPicker.getValue(), monthPicker.getValue(),
                            dayPicker.getValue(), hourPicker.getValue()%12+12*dayPartPicker.getValue(),
                            minutePicker.getValue());
                }
                editedAlarm.setEventDate(newEventDate);
                editDescriptionIntent.putExtra("data", editedAlarm);
            } else {
                Alarm newAlarm = new Alarm();
                newAlarm.setAlert(alarmAlert);
                newAlarm.setName(nameInput.getText().toString());
                LocalDateTime eventDate;
                if(is24HourFormat) {
                    eventDate = LocalDateTime.of(yearPicker.getValue(), monthPicker.getValue(),
                            dayPicker.getValue(), hourPicker.getValue(), minutePicker.getValue());
                } else {
                    eventDate = LocalDateTime.of(yearPicker.getValue(), monthPicker.getValue(),
                            dayPicker.getValue(), hourPicker.getValue()%12+12*dayPartPicker.getValue(),
                            minutePicker.getValue());
                }
                newAlarm.setEventDate(eventDate);
                editDescriptionIntent.putExtra("data", newAlarm);
            }
            startActivity(editDescriptionIntent);
        });
    }
}
