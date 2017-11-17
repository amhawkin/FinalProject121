package com.example.court.finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class snoozeSet extends AppCompatActivity {

    public Integer minutes;
    //String mints;
    Spinner num_Mins;

    Button submitButton;

    //final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snooze_set);

        num_Mins = (Spinner) findViewById(R.id.snooze_Time);

        submitButton = (Button) findViewById(R.id.done_Button);

        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

//                minutes = Integer.valueOf(String.valueOf(num_Mins.getSelectedItem()));
//                SharedPreferences.Editor editor = prefs.edit();
//                editor.putInt("mints", minutes);
//                editor.commit();

                Toast.makeText(snoozeSet.this, "Your snooze time has been saved!", Toast.LENGTH_SHORT).show();

                onBackPressed();
            }
        });

    }
}
