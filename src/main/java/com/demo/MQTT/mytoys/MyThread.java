package com.demo.MQTT.mytoys;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.concurrent.atomic.AtomicBoolean;

public class MyThread extends Thread {
    private String topic;
    private IMqttClient subscriber;
    private int timeDelay;
    private boolean shouldStop;
    public MyThread(IMqttClient subscriber, String topic, int timeDelay){
        this.topic = topic;
        this.subscriber = subscriber;
        this.timeDelay = timeDelay;
    }
    public void run() {
        AtomicBoolean callbackFinished = new AtomicBoolean(false);
        try {
            subscriber.subscribe(topic, (topicReceived, message) -> {
                System.out.println(topicReceived + "-----" + new String(message.getPayload()));
                callbackFinished.set(true);
                subscriber.unsubscribe(topic);
                // Thực hiện các công việc khác ở đây nếu cần
            });
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }
    public void stopThread() {
        shouldStop = true;
    }
}
