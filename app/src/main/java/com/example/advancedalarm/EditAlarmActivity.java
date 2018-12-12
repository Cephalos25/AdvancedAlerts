package com.example.advancedalarm;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.threeten.bp.LocalDateTime;

public class EditAlarmActivity extends AppCompatActivity {
    public static final int DESCRIPTION_REQUEST = 12321;

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
    private int receivedID;
    Alarm editedAlarm;

    private boolean importance = false;
    String alertDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editalarm);
        Intent receivedIntent = getIntent();
        receivedEditIntent = receivedIntent.getBooleanExtra("editalarm", false);
        receivedID = receivedIntent.getIntExtra("alarmId", -1);
        if (receivedID != -1){
            editedAlarm = Alarm.alarmList.get(receivedID); //editedAlarm only exists if the id is not -1.
            alertDescription = editedAlarm.getAlert().getDescription();
        } else {
            alertDescription = null;
        }
        is24HourFormat = DateFormat.is24HourFormat(getApplicationContext());

        wireWidgets();
        populateViews();
        if (receivedID != -1) {
            populateViewsFromAlarm(receivedID);
        }
        setListeners();
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

    private void populateViewsFromAlarm(int alarmId) {
        monthPicker.setValue(editedAlarm.getEventDate().getMonthValue());
        dayPicker.setValue(editedAlarm.getEventDate().getDayOfMonth());
        yearPicker.setValue(editedAlarm.getEventDate().getYear());
        minutePicker.setValue(editedAlarm.getEventDate().getMinute());
        nameInput.setText(editedAlarm.getName());
        if (is24HourFormat){
            hourPicker.setValue(editedAlarm.getEventDate().getHour());
        } else {
            int hour;
            hour = editedAlarm.getEventDate().getHour()%12;
            if (hour == 0){
                hour = 12;
            }
            hourPicker.setValue(hour);
            if (editedAlarm.getEventDate().getHour()>12){
                dayPartPicker.setValue(1);
            } else {
                dayPartPicker.setValue(0);
            }
        }
        if (editedAlarm.getAlert().isImportant()){
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
                    Alarm.alarmList.remove(receivedID);
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
                    LocalDateTime newEventDate = editedAlarm.getEventDate().withMonth(monthPicker.getValue())
                            .withDayOfMonth(dayPicker.getValue()).withYear(yearPicker.getValue())
                            .withHour(hourPicker.getValue()%12+12*dayPartPicker.getValue())
                            .withMinute(minutePicker.getValue());
                    editedAlarm.setEventDate(newEventDate);
                    Alarm.alarmList.set(receivedID, editedAlarm);
                } else {
                    Alarm newAlarm = new Alarm();
                    newAlarm.setAlert(alarmAlert);
                    newAlarm.setName(nameInput.getText().toString());
                    LocalDateTime eventDate = LocalDateTime.of(yearPicker.getValue(), monthPicker.getValue(),
                            dayPicker.getValue(), hourPicker.getValue()%12+12*dayPartPicker.getValue(),
                            minutePicker.getValue());
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
            if (receivedEditIntent){
                editDescriptionIntent.putExtra("description", editedAlarm.getAlert().getDescription());
                int hourValue;
                if (!is24HourFormat){
                    hourValue = hourPicker.getValue()%12 + dayPartPicker.getValue()*12;
                } else {
                    hourValue = hourPicker.getValue();
                }
                EditAlarmStorage storage = new EditAlarmStorage(monthPicker.getValue(), dayPicker.getValue(),
                        yearPicker.getValue(), hourValue, minutePicker.getValue(), importance,
                        shortDescriptionInput.getText().toString());
            }
            startActivityForResult(editDescriptionIntent, DESCRIPTION_REQUEST);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == DESCRIPTION_REQUEST){
            if (resultCode == RESULT_OK){
                String newDescription;
                if (data != null) {
                    newDescription = data.getStringExtra("description");
                    alertDescription = newDescription;
                }
            }
        }
    }
}
