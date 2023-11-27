package com.demo.MQTT.entity;


import jakarta.persistence.*;
import lombok.Cleanup;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    @Column(name = "google_id")
    private String googleId;
    private String address;
    private String email;
    @Column(name = "role_code")
    private String roleCode;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "edited_at")
    private LocalDateTime EditedAt;
}
