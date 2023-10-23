package com.example.gcsapp;

import android.os.Handler;
import android.util.Log;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import io.dronefleet.mavlink.MavlinkConnection;
import io.dronefleet.mavlink.MavlinkMessage;
import io.dronefleet.mavlink.common.CommandLong;

public class MVConnection {

    static MavlinkConnection connection;
    MavlinkMessage message;

    Socket socket;
    Handler mHandler;


    public MVConnection (Handler mHandler){
          this.mHandler=mHandler;
    }

    public  void Create_Connection (Spinner connect_type ) {
        new Thread(() ->{
            int connect_num = connect_type.getSelectedItemPosition();
            if(connection == null){
                try {
                  if (connect_num  == 0) {

                      socket = new Socket("127.0.0.1", 14550);
                      connection = MavlinkConnection.create(socket.getInputStream(), socket.getOutputStream());
                      Toast.makeText(connect_type.getContext(), "your connection is done" ,Toast.LENGTH_SHORT).show();

                  }else {

                  }
                }

                catch (EOFException eof) {
                    Log.i("MVConnection", "Connection Failed (SITL crush)");

                } catch (UnknownHostException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            }
        }).start();
    }


    public void Send(CommandLong commandLong){

        new Thread(() -> {
            if (connection==null) {
                try {
                    connection.send1(255,0,commandLong);

                }catch (IOException e ){
                    e.printStackTrace();
                }
            }


        }).start();

    }




}
