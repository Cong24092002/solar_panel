package com.demo.MQTT.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class SampleDTO {
    private LocalDateTime timeAt;
    private Float VL;
    private Float IL;
    private Float Ta;
    private Integer humidity;
    private Float G;
    private Float power;
    private Float effciency;

    public SampleDTO(ParameterSample parameterSample){
        this.timeAt = parameterSample.getTimeAt();
        this.VL = parameterSample.getVL();
        this.IL = parameterSample.getIL();
        this.Ta = parameterSample.getTa();
        this.humidity = parameterSample.getHumidity();
        this.G = parameterSample.getG();
        this.power = parameterSample.getPower();
    }
    public SampleDTO(Optional<ParameterSample> parameterSample){
        this.timeAt = null;
        this.VL = null;
        this.IL = null;
        this.Ta = null;
        this.humidity = null;
        this.G = null;
        this.power = null;
    }

}
