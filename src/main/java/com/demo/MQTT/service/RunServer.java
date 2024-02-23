package com.demo.MQTT.service;

import com.demo.MQTT.Repository.SampleRepo;
import com.demo.MQTT.entity.Message;
import com.demo.MQTT.entity.ParameterSample;
import com.demo.MQTT.entity.SampleDTO;
import com.demo.MQTT.mytoys.CacheData;
import com.demo.MQTT.mytoys.MyThread;
import com.demo.MQTT.mytoys.SleepSystem;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Component
public class RunServer {
    private final SampleRepo sampleRepo;
    private CacheData cacheData;

    public RunServer(SampleRepo sampleRepo) {
        this.sampleRepo = sampleRepo;
    }

    public void run() {
        String TOPIC1 = "ducthang906@gmail.com/loadvoltage";
        String TOPIC2 = "ducthang906@gmail.com/current";
        String TOPIC3 = "ducthang906@gmail.com/humidity";
        String TOPIC4 = "ducthang906@gmail.com/power";
        String TOPIC5 = "ducthang906@gmail.com/temperature";
        String clientId = "thang2.4";

        char[] password = {'2', 'b', 'c', 'f', 'j', 'k', 'q', 'u', 'v', 'y'};
        char[] passwordTest = {'d', 'u', 'c', 't', 'h', 'a', 'n', 'g', '0', '8', '0', '5', '2', '0', '0', '2'};
        try {
            IMqttClient subscriber = new MqttClient("tcp://maqiatto.com:1883", clientId, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(false);
            options.setConnectionTimeout(61000);
            options.setKeepAliveInterval(100);
            options.setUserName("ducthang906@gmail.com");
            options.setPassword(passwordTest);
            subscriber.connect(options);
            System.out.println("Connected!");
//            AtomicInteger messageCount = new AtomicInteger(0);
//            int maxMessages = 5;

            // Đăng ký để lắng nghe chủ đề
            // thay đổi quy trình xử lí sẽ làm kết quả bị lỗi không như mong đợi!!!
            SampleDTO sampleDTO = new SampleDTO(); // chỉ tạo 1 instance trong khi mình muốn luôn luôn làm mới các instance
            //chỉ có tối đa 5 luồng được tạo
            ExecutorService executor = Executors.newFixedThreadPool(5);
            Queue<Message> Q1 = CacheData.getQ1();
            Queue<Message> Q2 = CacheData.getQ2();
            Queue<Message> Q3 = CacheData.getQ3();
            Queue<Message> Q4 = CacheData.getQ4();
            Queue<Message> Q5 = CacheData.getQ5();
            
            subscriber.subscribe(TOPIC1, (topicReceived, message) -> {
                //System.out.println(topicReceived + "-----" + new String(message.getPayload()));
                Q1.offer(new Message(topicReceived, new String(message.getPayload()),LocalDateTime.now()));
                // Thực hiện các công việc khác ở đây nếu cần
            });
            subscriber.subscribe(TOPIC2, (topicReceived, message) -> {
                Q2.offer(new Message(topicReceived, new String(message.getPayload()),LocalDateTime.now()));
                // Thực hiện các công việc khác ở đây nếu cần
            });
            subscriber.subscribe(TOPIC3, (topicReceived, message) -> {
                Q3.offer(new Message(topicReceived, new String(message.getPayload()),LocalDateTime.now()));
                // Thực hiện các công việc khác ở đây nếu cần
            });
            subscriber.subscribe(TOPIC4, (topicReceived, message) -> {
                Q4.offer(new Message(topicReceived, new String(message.getPayload()),LocalDateTime.now()));
                // Thực hiện các công việc khác ở đây nếu cần
            });
            subscriber.subscribe(TOPIC5, (topicReceived, message) -> {
                Q5.offer(new Message(topicReceived, new String(message.getPayload()), LocalDateTime.now()));
                // Thực hiện các công việc khác ở đây nếu cần
            });
            int check = 1; // val check status change CacheData
            while(true){
                if(!Q1.isEmpty() && !Q2.isEmpty() && !Q3.isEmpty() && !Q4.isEmpty() && !Q5.isEmpty() && check == 1) {
                    System.out.println(Q1.peek().showInfo());
                    sampleDTO.setVL(Float.parseFloat(Q1.peek().getPayload()));
                    System.out.println(Q2.peek().showInfo());
                    sampleDTO.setIL(Float.parseFloat(Q2.peek().getPayload()));
                    System.out.println(Q3.peek().showInfo());
                    sampleDTO.setHumidity(Math.round(Float.parseFloat(Q3.peek().getPayload())));
                    System.out.println(Q4.peek().showInfo());
                    sampleDTO.setPower(Float.parseFloat(Q4.peek().getPayload()));
                    System.out.println(Q5.peek().showInfo());
                    sampleDTO.setTa(Float.parseFloat(Q5.peek().getPayload()));
                    //time avg on local app. not time respones from sensor
                    sampleDTO.setTimeAt(Q5.peek().getTime());
                    sampleDTO.setG(0F);
                    sampleDTO.setEffciency(0F);
                    check = 0;
                    ParameterSample sampleStore = new ParameterSample(sampleDTO);
                    sampleRepo.save(sampleStore);
                    System.out.println("=================================");
                }
                if(CacheData.getStatusPoll()){
                        Q1.poll(); Q2.poll(); Q3.poll(); Q4.poll(); Q5.poll();
                        CacheData.setStatusPoll(false);
                        check = 1;
                    }
                }



//            subscriber.subscribe(TOPIC1, (topicReceived, message) -> {
//                System.out.println(topicReceived + "-----" + new String(message.getPayload()));
//            });
//                sample.setTimeAt(LocalDateTime.now());
//                String payload = new String(message.getPayload());
//
//                int currentCount = messageCount.incrementAndGet();
//                if (currentCount >= maxMessages) {
//                    // Đã nhận đủ 5 tin nhắn, nghỉ 1 phút trước khi tiếp tục
//                    messageCount.set(0); // Reset biến đếm
//                    //sampleRepo.save(sample);
//                    try {
//                        Thread.sleep(60000); // Nghỉ 1 phút (60.000 miligiây)
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    //sampleRepo.save(sample);
//
//                    ParameterSample sample_save = new ParameterSample(sample); // tạo đối tượng mới để hứng lấy đối tượng sample
//                    // đáp ứng tạo đối tượng mới để save xuống database
//                    //sample_save = sample;
//                    //sampleRepo.save(sample_save);
//                    System.out.println(sample_save.getTimeAt());
//                    //sampleRepo.save(sample_save);
//                }

            //           });
//
//            subscriber.subscribe(TOPIC2, (topicReceived, message) -> {
//                System.out.println(topicReceived + "-----" + new String(message.getPayload()));
//            });
//            subscriber.subscribe(TOPIC3, (topicReceived, message) -> {
//                System.out.println(topicReceived + "-----" + new String(message.getPayload()));
//            });
//            subscriber.subscribe(TOPIC4, (topicReceived, message) -> {
//                System.out.println(topicReceived + "-----" + new String(message.getPayload()));
//            });
//            subscriber.subscribe(TOPIC5, (topicReceived, message) -> {
//                System.out.println(topicReceived + "-----" + new String(message.getPayload()));
//            });
//        } catch (MqttException e) {
//            e.printStackTrace();
            //       }
        } catch (MqttSecurityException e) {
            throw new RuntimeException(e);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }

    }
}

