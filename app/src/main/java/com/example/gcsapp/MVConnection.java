package com.example.gcsapp;

import android.os.Handler;
import android.util.Log;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;


import io.dronefleet.mavlink.MavlinkConnection;
import io.dronefleet.mavlink.MavlinkMessage;
import io.dronefleet.mavlink.common.CommandLong;

public class MVConnection {

    static MavlinkConnection connection;
   static MavlinkMessage message;

    static Socket socket;
   static Handler mHandler;
     static Drone_Message drone_message = new Drone_Message();


    public MVConnection ( Handler mHandler){


          MVConnection.mHandler =mHandler;
      }


    public static void Create_Connection(Spinner connect_type) {
        new Thread(() -> {
            int connect_num = connect_type.getSelectedItemPosition();
            if(connection == null){
                try {
                  if (connect_num  == 0) {
                      socket = new Socket("127.0.0.1", 14550);
//                      socket = new Socket("192.168.1.27", 14550);
//                    socket = new Socket("192.168.1.3", 14550);
                      connection = MavlinkConnection.create(socket.getInputStream(), socket.getOutputStream());
                       Log.i("status" ,"connection done");

                  }else {
                     Log.i("information" , "connection is not done");
                  }
//
//                    while ((message = connection.next())!= null ) {
//
//                         // If receive data, push them to Drone_Message.java
//
//                              drone_message.Message_classify(message);
//                               mHandler .obtainMessage(100,"connection is done").sendToTarget();
//
//
////                        String status ="Your connection is done";
////                        drone_message.Message_classify(message);
////                        mHandler .obtainMessage();
//
//                     }


                }
                // Receiving Message
                catch (EOFException eof) {
                    Log.i("MVConnection", "Connection Failed (SITL crush)");

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            }
        }).start();
    }


    public static void Send(CommandLong commandLong){

        new Thread(() -> {
            if (connection!=null) {
                try {
                    connection.send1(255,0,commandLong);

                }catch (IOException e ){
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
