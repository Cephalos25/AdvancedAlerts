package com.example.advancedalarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditDescriptionActivity extends AppCompatActivity {
    private TextView cancelView;
    private TextView finishView;
    private EditText editDescription;

    private Alarm editedAlarm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editdescription);
        Intent receivedIntent = getIntent();
        editedAlarm = receivedIntent.getParcelableExtra("data");

        wireWidgets();
        if (editedAlarm != null){
            editDescription.setText(editedAlarm.getAlert().getDescription());
        }
        setListeners();
    }

    private void wireWidgets() {
        cancelView = findViewById(R.id.textView_editdescription_cancel);
        finishView = findViewById(R.id.textView_editdescription_finish);
        editDescription = findViewById(R.id.editText_editdescription_description);
    }

    private void setListeners() {
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBackIntent = new Intent(EditDescriptionActivity.this, EditAlarmActivity.class);
                goBackIntent.putExtra("alarm", editedAlarm);
                startActivity(goBackIntent);
            }
        });
        finishView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changeDescriptionIntent = new Intent(EditDescriptionActivity.this, EditAlarmActivity.class);
                editedAlarm.getAlert().setDescription(editDescription.getText().toString());
                changeDescriptionIntent.putExtra("data", editedAlarm);
                startActivity(changeDescriptionIntent);
            }
        });
    }
}
