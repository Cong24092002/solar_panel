package com.demo.MQTT.mytoys;

import com.demo.MQTT.entity.Message;


import java.util.LinkedList;
import java.util.Queue;

public class CacheData {
    private static Queue<Message> Q1 =  new LinkedList<>();
    private static Queue<Message> Q2 = new LinkedList<>();
    private static Queue<Message> Q3 = new LinkedList<>();
    private static Queue<Message> Q4 = new LinkedList<>();
    private static Queue<Message> Q5 = new LinkedList<>();
    private static Boolean statusPoll = false; // do có 2 process cùng trỏ đến 1 CacheData cần 1 biến status để quản lí việc
                                        // pull push object
                                        // 1 luồng đẩy xuống databaase 1 luồng hiển thị websocket
                                        // default false
    public static Queue<Message> getQ1(){
        return Q1;
    }
    public static Queue<Message> getQ2(){
        return Q2;
    }
    public static Queue<Message> getQ3(){
        return Q3;
    }
    public static Queue<Message> getQ4(){
        return Q4;
    }
    public static Queue<Message> getQ5(){
        return Q5;
    }
    public static Boolean getStatusPoll(){
        return statusPoll;
    }
    public static void setStatusPoll(boolean pollStatus){
        statusPoll = pollStatus;
    }

}
