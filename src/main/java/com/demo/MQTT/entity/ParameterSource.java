package com.demo.MQTT.entity;

import com.demo.MQTT.Enum.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "parameter_source")
public class ParameterSource {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "time_at")
    private LocalDateTime timeAt;
    private Float Voc;
    private Float Isc;
    @Column(name= "status_")
    private Status status;
    @Column(name = "sys_id")
    private String sysId;
    @Column(name = "using_")
    private Boolean using;

}
