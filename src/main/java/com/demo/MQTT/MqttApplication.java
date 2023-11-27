package com.demo.MQTT;

import com.demo.MQTT.Repository.SampleRepo;
import com.demo.MQTT.entity.ParameterSample;
import com.demo.MQTT.service.RunServer;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class MqttApplication {
	private static RunServer server;

	public MqttApplication(RunServer server) {
		this.server = server;
	}
//	private static SampleRepo sampleRepo;

	public static void main(String[] args) throws MqttException {
		SpringApplication.run(MqttApplication.class, args);
		server.run();

//		String TOPIC1 = "#";
//		String TOPIC2 = "$SYS/#";
//		String clientId = "mqtt-explorer-e7ca42a5";
//
//		char[] password = {'F', 'A', '6', '4', '3', 'D', '5', '4', 'A', '1', '8', '6', '4', '8', 'F', '2'};
//		try {
//			IMqttClient subscriber = new MqttClient("tcp://27.71.232.86:1111", clientId, new MemoryPersistence());
//			MqttConnectOptions options = new MqttConnectOptions();
//			options.setAutomaticReconnect(true);
//			options.setCleanSession(false);
//			options.setConnectionTimeout(61000);
//			options.setKeepAliveInterval(100);
//			options.setUserName("solarpanels");
//			options.setPassword(password);
//			subscriber.connect(options);
//			System.out.println("Connected!");
//			AtomicInteger messageCount = new AtomicInteger(0);
//			int maxMessages = 5;
//
//			subscriber.subscribe(TOPIC1, (topicReceived, message) -> {
//				System.out.println("hello");
//				String payload = new String(message.getPayload());
//				System.out.println(topicReceived + " : " + payload);
//				System.out.println("--------------------------------");
//
//			});
//
//		} catch (MqttException e) {
//			e.printStackTrace();
//		}
	}
}
