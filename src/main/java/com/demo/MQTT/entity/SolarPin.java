package com.demo.MQTT.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "solar_pin")
public class SolarPin {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String model;
    @Column(name = "max_pow")
    private Float maxPow;

    private Float Vmpp;
    private Float Impp;
    private Float Voc;
    private Float Isc;
    private Float efficiency;
    @Column(name = "sys_id")
    private String sysId;
}
