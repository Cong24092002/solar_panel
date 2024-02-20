package com.demo.MQTT.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@lombok.Data
public class Response {

    private String header;
    private Object data;
    @JsonProperty("statuscode")
    private String statusCode;
    private String message;

    public Response(String h, Object t, String http, String m) {
        this.header = h;
        this.data = t;
        this.statusCode = http;
        this.message = m;
    }
}