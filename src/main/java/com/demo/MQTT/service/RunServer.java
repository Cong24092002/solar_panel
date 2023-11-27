package com.demo.MQTT.service;

import com.demo.MQTT.Repository.SampleRepo;
import com.demo.MQTT.entity.ParameterSample;
import com.demo.MQTT.entity.SampleDTO;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class RunServer {
    private final SampleRepo sampleRepo;

    public RunServer(SampleRepo sampleRepo) {
        this.sampleRepo = sampleRepo;
    }

    public void run(){
        String TOPIC1 = "#";
        String TOPIC2 = "$SYS/#";
        String clientId = "mqtt-explorer-e7ca42a5";

        char[] password = {'F','A','6','4','3','D','5','4','A','1','8','6','4','8','F','2'};
        try {
            IMqttClient subscriber = new MqttClient("tcp://27.71.232.86:1111", clientId, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(false);
            options.setConnectionTimeout(61000);
            options.setKeepAliveInterval(100);
            options.setUserName("solarpanels");
            options.setPassword(password);
            subscriber.connect(options);
            System.out.println("Connected!");
            AtomicInteger messageCount = new AtomicInteger(0);
            int maxMessages = 4;

            // Đăng ký để lắng nghe chủ đề
            // thay đổi quy trình xử lí sẽ làm kết quả bị lỗi không như mong đợi!!!
            SampleDTO sample = new SampleDTO(); // chỉ tạo 1 instance trong khi mình muốn luôn luôn làm mới các instance
            subscriber.subscribe(TOPIC1, (topicReceived, message) -> {
                sample.setTimeAt(LocalDateTime.now());
                String payload = new String(message.getPayload());
                if(topicReceived.substring(12).equals("loadvoltage")){
                    sample.setVL(Float.parseFloat(payload));
                }
                if(topicReceived.substring(12).equals("current")){
                    sample.setIL(Float.parseFloat(payload));
                }
                if(topicReceived.substring(12).equals("temperature")){
                    sample.setTa(Float.parseFloat(payload));
                }
                if(topicReceived.substring(12).equals("humdity")) {
                    sample.setHumidity(Math.round(Float.parseFloat(payload)));
                }
//				sample.setTimeAt(LocalDateTime.now());
//				sample.setPower(sample.getIL() * sample.getVL());
//                System.out.println(sample.getIL());

                int currentCount = messageCount.incrementAndGet();
                if (currentCount >= maxMessages) {
                    // Đã nhận đủ 5 tin nhắn, nghỉ 1 phút trước khi tiếp tục
                    messageCount.set(0); // Reset biến đếm
                    //sampleRepo.save(sample);
                    try {
                        Thread.sleep(60000); // Nghỉ 1 phút (60.000 miligiây)
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //sampleRepo.save(sample);

                    ParameterSample sample_save = new ParameterSample(sample); // tạo đối tượng mới để hứng lấy đối tượng sample
                    // đáp ứng tạo đối tượng mới để save xuống database
                    //sample_save = sample;
                    //sampleRepo.save(sample_save);
                    System.out.println(sample_save.getTimeAt());
                    sampleRepo.save(sample_save);
                }

            });

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    }

