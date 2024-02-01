package com.demo.MQTT.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "parameter_sample")
public class ParameterSample {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "time_at")
    private LocalDateTime timeAt;

    private Float VL;
    private Float IL;
    private Float Ta;
    private Integer humidity;
    private Float G;
    private Float power;
    private Float effciency;
    @Column(name = "sys_id")
    private String sysId;

    public ParameterSample(SampleDTO sampleDTO){
        this.timeAt = sampleDTO.getTimeAt();
        this.VL = sampleDTO.getVL() * 1000; // V
        this.IL = sampleDTO.getIL(); // mA
        this.Ta = sampleDTO.getTa();
        this.humidity = sampleDTO.getHumidity();
        this.power = sampleDTO.getIL() * sampleDTO.getVL(); // mW
    }

}
