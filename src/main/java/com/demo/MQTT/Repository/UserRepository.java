package com.demo.MQTT.Repository;

import com.demo.MQTT.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
