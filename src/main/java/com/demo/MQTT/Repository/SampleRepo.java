package com.demo.MQTT.Repository;

import com.demo.MQTT.entity.ParameterSample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleRepo extends JpaRepository<ParameterSample, String> {

}
