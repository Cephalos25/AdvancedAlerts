package com.example.advancedalarm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.List;

public class AlarmAdapter extends ArrayAdapter<Alarm> {
    private List<Alarm> data;
    private Context mContext;
    private DateFormat dateFormat = DateFormat.getDateInstance();
    private DateFormat timeFormat = DateFormat.getTimeInstance();

    public AlarmAdapter(@NonNull Context context, List<Alarm> objects) {
        super(context, 0, objects);
        this.data = objects;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        }

        Alarm actedAlarm = data.get(position);

        TextView name = listItem.findViewById(R.id.textView_listitem_main_name);
        name.setText(actedAlarm.getName());

        String date = dateFormat.format(actedAlarm.getEventDate());
        return listItem;
    }
}
