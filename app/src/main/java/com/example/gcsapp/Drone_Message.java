package com.example.gcsapp;

import android.os.Handler;
import android.util.Log;

import java.text.DateFormat;
import java.util.Calendar;

import io.dronefleet.mavlink.MavlinkMessage;
import io.dronefleet.mavlink.minimal.Heartbeat;

public class Drone_Message {

    static String mav_type = null, mav_autopilot = null, system_status = null, flight_mode = null;
    static Character arm_disarm = null;

    private final Handler mHandler = MainActivity.mHandler;

    public  void Message_classify(MavlinkMessage message){
//       Calendar mCal =  Calendar.getInstance();
//       CharSequence timestamp = DateFormat.getDateInstance().format("yyyy-MM-dd kk:mm:ss");


       if (message.getPayload() instanceof Heartbeat) {
           MavlinkMessage <Heartbeat> heartbeatMessage= (MavlinkMessage<Heartbeat>) message;
           String payload = "" + heartbeatMessage.getPayload();
           String [] payload_Heartbeat = payload.split(",");
           String mav_type_ =payload_Heartbeat[1].replaceAll("[^A-Z_]", "");
           String base_mode = payload_Heartbeat[4].replaceAll("[^\\d-.,]", "");
           String mav_autopilot = payload_Heartbeat[3].replaceAll("[^A-Z_]", "");
           String system_status = payload_Heartbeat[8].replaceAll("[^A-Z_]", "");
           String custom_mode = payload_Heartbeat[6].replaceAll("[^\\d-.,]", "");
           String flight_mode = MODE_TYPE(custom_mode);

           // condition for arm and Disarm
           int i = 128;
           int intBaseMode= Integer.parseInt(base_mode);
           String arm_disarm;
           if((intBaseMode & i)!=0) {
               arm_disarm ="ARMED";
           }else{
               arm_disarm="DISARMED";
           }

            mHandler.obtainMessage(201, arm_disarm).sendToTarget();
             mHandler.obtainMessage(200,flight_mode).sendToTarget();
           Log.i("Drone_Message", "HEARTBEAT: " + mav_type_ + " " + mav_autopilot + " " + flight_mode + " " + system_status + " " + arm_disarm);

           
       }
       else{
           Log.i("message info","Exception" + message.toString());
       }
    }


    public String MODE_TYPE(String num){
        switch (num){
            case "0":
                return "STABILIZE";
            case "1":
                return "ACRO";
            case "2":
                return "ALT_HOLD";
            case "3":
                return "AUTO";
            case "4":
                return "GUIDED";
            case "5":
                return "LOITER";
            case "6":
                return "RTL";
            case "7":
                return "CIRCLE";
            case "8":
                return "POSITION";
            case "9":
                return "LAND";
        }
        return "ERROR";
    }


}
