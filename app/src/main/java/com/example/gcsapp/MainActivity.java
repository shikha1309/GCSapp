package com.example.gcsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Spinner Spinner_Connect_Type ;
    Spinner Spinner_Flight_Type;

    TextView display_drone_command;
    TextView display_drone_cmd_ack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner_Connect_Type  = findViewById(R.id.spinnerConnectType);
        Spinner_Flight_Type =findViewById(R.id.spinnerFlightType);

        // TextView
        display_drone_command = findViewById(R.id.Command);
        display_drone_cmd_ack = findViewById(R.id.Command_Ack);

      // setting the Mode Spinner

        Resources res = getResources();
        String [] mode = res.getStringArray(R.array.mode);
        final ArrayAdapter<String>  myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,mode);
        Spinner_Flight_Type.setAdapter(myAdapter);
        Spinner_Flight_Type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // SET_MODE_FUNCTION will call here from Drone_Command.java

                String text = Spinner_Connect_Type.getItemAtPosition(position).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



















    }
}