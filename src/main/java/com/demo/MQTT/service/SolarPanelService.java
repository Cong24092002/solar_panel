package com.demo.MQTT.service;

import com.demo.MQTT.Repository.SampleRepo;
import com.demo.MQTT.entity.ParameterSample;
import com.demo.MQTT.entity.SampleDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SolarPanelService {
    private final SampleRepo sampleRepo;

    public SolarPanelService(SampleRepo sampleRepo) {
        this.sampleRepo = sampleRepo;
    }
    // cần tối ưu hiệu năng query db ở đây
    public Optional<ParameterSample> getData(){
        // code something ...

        //test is here
        String testId = "e6cebb64-52c5-4fec-9bc9-8b4580ee7e00";
        Optional<ParameterSample> result = sampleRepo.findById(testId);
        return result;
    }
}
