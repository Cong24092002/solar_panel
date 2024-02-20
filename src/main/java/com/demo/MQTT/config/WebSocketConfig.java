package com.demo.MQTT.config;

import com.demo.MQTT.Repository.SampleRepo;
import com.demo.MQTT.service.SolarPanelService;
import com.demo.MQTT.websocket.SolarWebSocket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final SampleRepo sampleRepo;

    public WebSocketConfig(SampleRepo sampleRepo) {
        this.sampleRepo = sampleRepo;
    }

    //http://localhost:8080/solar
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(getSolarWebSocket(),"/solar");
    }

    @Bean
    public SolarWebSocket getSolarWebSocket(){
        return new SolarWebSocket(getSolarPanelService());
    }

    @Bean
    public SolarPanelService getSolarPanelService(){
        return new SolarPanelService(sampleRepo);
    }



}
