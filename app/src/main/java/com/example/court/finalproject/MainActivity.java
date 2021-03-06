package com.example.court.finalproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    AlarmManager alarm_manager;
    TimePicker alarm_timepicker;
    TextView update_text;
    Context context;
    PendingIntent pending_intent;
    int snooze_mints;
    int choose_sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;

        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarm_timepicker = (TimePicker) findViewById(R.id.timePicker);
        update_text = (TextView) findViewById(R.id.update_text);
        final Calendar calendar = Calendar.getInstance();
        final Intent alarm_receiver = new Intent(this.context, Alarm_Receiver.class);

        // Spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.music_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Set onClickListener to onItemSelected method
        spinner.setOnItemSelectedListener(this);

        // Click 'Set Alarm' button
        Button alarm_on = (Button) this.findViewById(R.id.alarm_on);
        alarm_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set calendar
                calendar.set(Calendar.HOUR_OF_DAY, alarm_timepicker.getHour());
                calendar.set(Calendar.MINUTE, alarm_timepicker.getMinute());

                // Displaying time of alarm
                // int values of hour and minute
                int hour = alarm_timepicker.getHour();
                int minute = alarm_timepicker.getMinute();

                // Convert the int values to Strings
                String hour_string = String.valueOf(hour);
                String minute_string = String.valueOf(minute);

                // Convert 24-hour time to 12-hour time
                if (hour == 0) {
                    hour_string = String.valueOf(12);
                    minute_string = String.valueOf(minute) + " AM";
                } else if (hour > 12) {
                    hour_string = String.valueOf(hour - 12);
                    minute_string = String.valueOf(minute) + " PM";
                } else {
                    minute_string = String.valueOf(minute) + " AM";
                }

                if (minute < 10) {
                    minute_string = "0" + String.valueOf(minute);
                }

                set_alarm_text("Alarm set to: " + hour_string + ":" + minute_string);

                // Tells clock that you pressed "alarm on" button
                alarm_receiver.putExtra("extra", "alarm on");

                // Tells clock which sound you selected from spinner
                alarm_receiver.putExtra("sound_choice", choose_sound);
                Log.e("The sound id is ", String.valueOf(choose_sound));

                // Ringtone will play at set time
                pending_intent = PendingIntent.getBroadcast(MainActivity.this, 0, alarm_receiver,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending_intent);
            }
        });

        // Click 'Alarm Off' button
        Button alarm_off = (Button) this.findViewById(R.id.alarm_off);
        alarm_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display cancellation
                set_alarm_text("Alarm off!");

                // Tells clock that you pressed "alarm off" button
                alarm_receiver.putExtra("extra", "alarm off");

                // Tells clock which sound you selected from spinner
                // Prevents crash from Null Pointer Exception
                alarm_receiver.putExtra("sound_choice", choose_sound);

                // Cancel alarm, stop ringtone
                try {
                    alarm_manager.cancel(pending_intent);
                    sendBroadcast(alarm_receiver);

                } catch (NullPointerException e) {
                    Log.e("Alarm has not been set ", "and user pressed alarm off");
                }

                pending_intent = PendingIntent.getBroadcast(MainActivity.this, 0, alarm_receiver,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending_intent);
            }
        });

        //Button 'Snooze' button
        Button snooze_Button = (Button) this.findViewById(R.id.snooze_Button);
        snooze_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display cancellation
                set_alarm_text("Alarm has been snoozed!");

                // Tells clock that you pressed "alarm off" button
                alarm_receiver.putExtra("extra", "alarm off");

                // Tells clock which sound you selected from spinner
                // Prevents crash from Null Pointer Exception
                alarm_receiver.putExtra("sound_choice", choose_sound);

                // Cancel alarm, stop ringtone
                try {
                    alarm_manager.cancel(pending_intent);
                    sendBroadcast(alarm_receiver);


                } catch (NullPointerException e) {
                    Log.e("Alarm has not been set ", "and user pressed alarm off");
                }

//                Intent intent = new Intent(this, this.getClass());
//                pending_intent = PendingIntent.getService(MainActivity.this, 0, intent,
//                        PendingIntent.FLAG_UPDATE_CURRENT);
//                //alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending_intent);
//                long currentTimeMillis = System.currentTimeMillis();
//                long nextUpdateTimeMillis = currentTimeMillis +
            }
        });
    }


    private void set_alarm_text(String output) {
        update_text.setText(output);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Outputting sound user selects from spinner
        Toast.makeText(parent.getContext(), "the spinner item is " + id, Toast.LENGTH_SHORT).show();
        choose_sound = (int) id;

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void snoozeSettings(View view) {
        Intent startNewActivity = new Intent(this, snoozeSet.class);
        startActivity(startNewActivity);
    }
}


