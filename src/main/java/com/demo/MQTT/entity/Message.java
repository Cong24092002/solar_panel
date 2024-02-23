package com.demo.MQTT.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Message {
    private String header;
    private String payload;
    private LocalDateTime time;
    public Message(String header, String payload,LocalDateTime time){
        this.header = header;
        this.payload = payload;
        this.time = time;
    }
    public String showInfo(){
        return header + "-----" + payload + "----" + time;
    }
}
