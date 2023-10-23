package com.example.gcsapp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;


public class MainActivity extends AppCompatActivity {

    Spinner Spinner_Connect_Type ;
    Spinner Spinner_Flight_Type;

    // Text View
    TextView display_drone_command;
    TextView display_drone_cmd_ack;
    TextView display_isArmed;
    TextView display_Conn_Status;
    TextView  display_flight_mode;
    // handler
    static  MyHandler mHandler = null;



//   public Button mConnectBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner_Connect_Type  = findViewById(R.id.spinnerConnectType);
        Spinner_Flight_Type =findViewById(R.id.spinnerFlightType);

        // TextView
        display_drone_command = findViewById(R.id.Command);
        display_drone_cmd_ack = findViewById(R.id.Command_Ack);
        display_isArmed = findViewById(R.id.textview_isArmed);
        display_Conn_Status= findViewById(R.id.TextViewConnection);
        display_flight_mode= findViewById(R.id.textView_FlightMode);



        // setting the Mode Spinner

        Resources res = getResources();
        String [] mode = res.getStringArray(R.array.mode);
        final ArrayAdapter<String>  myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,mode);
        Spinner_Flight_Type.setAdapter(myAdapter);
        Spinner_Flight_Type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // SET_MODE_FUNCTION will call here from Drone_Command.java

                Drone_Command.SET_MODE_FUNCTION(position);

                // Show toast of the selected mode
                String text = Spinner_Flight_Type.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this ,text,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }


    // All button that controlling  DRONE
    public void connect(View view) {

        MVConnection.Create_Connection(Spinner_Connect_Type);

    }

    public void arm(View view) {

        Drone_Command.ARM_DISARM_FUNCTION(1);
    }

    public void disarm(View view) {

        Drone_Command.ARM_DISARM_FUNCTION(0);
    }

    public void takeOff(View view) {
        float alt = Float .parseFloat("10.5");
        Drone_Command.TAKEOFF_FUNCTION(alt);
    }

// for handling textview of the UI

 public  static class MyHandler extends Handler{
private final WeakReference<MainActivity> mActivity;


     public MyHandler(MainActivity activity) {

         mActivity = new WeakReference<>(activity);
     }

     @Override
     public void handleMessage( Message msg) {

         // All these text are handled with textview of the activity main.Xml file

      switch (msg.what) {
          case 100:
              String connection_Status = (String) msg.obj;
              mActivity.get().display_Conn_Status.setText(connection_Status);
              break;


          case 200:
              String mode_Status = (String) msg.obj;
              mActivity.get().display_flight_mode.setText(mode_Status);

              case 201:
              String isArmed = (String) msg.obj;
              mActivity.get().display_isArmed.setText(isArmed);
              break;




//          case 300:
//              String web_cmd = (String) msg.obj;
//              mActivity.get().display_drone_command.setText(web_cmd);
//
//          case 301:
//              String cmd_ack =(String) msg.obj;
//              mActivity.get().display_drone_cmd_ack.setText(cmd_ack);

      }
     }
 }

}