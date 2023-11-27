package com.demo.MQTT.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SampleDTO {
    private LocalDateTime timeAt;
    private Float VL;
    private Float IL;
    private Float Ta;
    private Integer humidity;
    private Float G;
    private Float power;
    private Float effciency;
}
