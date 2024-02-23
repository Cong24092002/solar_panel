package com.demo.MQTT.mytoys;

public class SleepSystem {
    public static void run(int milliseconds){
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
