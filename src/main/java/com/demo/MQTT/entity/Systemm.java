package com.demo.MQTT.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "system")
public class Systemm {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "sys_name")
    private String sysName;

    private String owner; // id user
    @Column(name = "sys_id")
    private String sysId;
}
