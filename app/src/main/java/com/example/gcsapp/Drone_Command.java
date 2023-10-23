package com.example.gcsapp;

import io.dronefleet.mavlink.common.CommandLong;
import io.dronefleet.mavlink.common.MavCmd;

public class Drone_Command {
    public  static void ARM_DISARM_FUNCTION(int is_armed) {
        new Thread(()->{
            CommandLong cmd = new CommandLong.Builder().command(MavCmd.MAV_CMD_COMPONENT_ARM_DISARM) .param1(is_armed).build();
             MVConnection.Send(cmd);

        }).start();

    }

    public static void SET_MODE_FUNCTION(int mode_name){
        new Thread(()->{
            CommandLong cmd = new CommandLong.Builder().command(MavCmd.MAV_CMD_DO_SET_MODE).param1(1).param2(mode_name).build();
            MVConnection.Send(cmd);

        }).start();
    }


    public static void TAKEOFF_FUNCTION(float alt) {
        new Thread(() -> {
            CommandLong cmd = new CommandLong.Builder().command(MavCmd.MAV_CMD_NAV_TAKEOFF).param7(alt).build();
            MVConnection.Send(cmd);
        }).start();
    }


}
