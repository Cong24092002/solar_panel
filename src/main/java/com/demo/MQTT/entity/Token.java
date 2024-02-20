package com.demo.MQTT.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Data
public class Token {
    private String token;
    public Token(String token){
        this.token = token;
    }
}
