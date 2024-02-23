package com.demo.MQTT.websocket;

import com.demo.MQTT.entity.ParameterSample;
import com.demo.MQTT.entity.SampleDTO;
import com.demo.MQTT.mytoys.CacheData;
import com.demo.MQTT.service.SolarPanelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.LocalDateTime;
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
        SampleDTO sampleDTO = new SampleDTO();
        objectMapper.registerModule(new JavaTimeModule());
        int checkDuplicate = 1;
        while(true){
              if(!CacheData.getQ1().isEmpty() && !CacheData.getQ2().isEmpty() && !CacheData.getQ3().isEmpty()
                      && !CacheData.getQ4().isEmpty() && !CacheData.getQ5().isEmpty()
                        && checkDuplicate == 1){
                  sampleDTO.setTimeAt(LocalDateTime.now());
                  sampleDTO.setVL(Float.parseFloat(CacheData.getQ1().peek().getPayload())); // cast data
                  sampleDTO.setIL(Float.parseFloat(CacheData.getQ2().peek().getPayload()));
                  sampleDTO.setTa(Float.parseFloat(CacheData.getQ5().peek().getPayload()));
                  sampleDTO.setHumidity(Math.round(Float.parseFloat(CacheData.getQ3().peek().getPayload())));
                  sampleDTO.setPower(Float.parseFloat(CacheData.getQ4().peek().getPayload()));
                  sampleDTO.setG(0F);
                  sampleDTO.setEffciency(0F);
                  String json = objectMapper.writeValueAsString(sampleDTO);
                  session.sendMessage(new TextMessage(json));
                  CacheData.setStatusPoll(true); // kích hoạt trạng thái poll object ra khỏi queue
                  checkDuplicate = 0;
              }
              if(!CacheData.getStatusPoll()){
                  checkDuplicate = 1;
              }
            //Thread.sleep(10000); // 30s gửi 1 lần
        }
    }

}
