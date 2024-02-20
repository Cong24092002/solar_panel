package com.demo.MQTT.websocket;

import com.demo.MQTT.entity.ParameterSample;
import com.demo.MQTT.entity.SampleDTO;
import com.demo.MQTT.service.SolarPanelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Optional;

@Slf4j
public class SolarWebSocket extends TextWebSocketHandler {
    private final SolarPanelService solarPanelService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SolarWebSocket(SolarPanelService solarPanelService) {
        this.solarPanelService = solarPanelService;
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        log.info("message: " + message.getPayload());
        // sử dụng message = token --> nhận thực người dùng
        Optional<ParameterSample> parameterSample = solarPanelService.getData();
        SampleDTO sampleDTO = new SampleDTO();
        objectMapper.registerModule(new JavaTimeModule());
        while(true){
            sampleDTO.setTimeAt(parameterSample.get().getTimeAt());
            sampleDTO.setVL(parameterSample.get().getVL());
            sampleDTO.setIL(parameterSample.get().getIL());
            sampleDTO.setTa(parameterSample.get().getTa());
            sampleDTO.setHumidity(parameterSample.get().getHumidity());
            sampleDTO.setPower(parameterSample.get().getPower());
            sampleDTO.setG(parameterSample.get().getG());
            sampleDTO.setEffciency(parameterSample.get().getEffciency());
            String json = objectMapper.writeValueAsString(sampleDTO);

            session.sendMessage(new TextMessage(json));
            Thread.sleep(10000); // 30s gửi 1 lần
        }

    }

}
