//package com.demo.MQTT.mqttServer;
//
//import org.eclipse.paho.client.mqttv3.IMqttClient;
//import org.eclipse.paho.client.mqttv3.MqttMessage;
//
//import java.util.Random;
//import java.util.concurrent.Callable;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.TimeUnit;
//
//public class EngineSolarSensor implements Callable<Void> {
//        private IMqttClient client;
//        private final String TOPIC1 = "#";
//        private final String TOPIC2 = "$SYS/#";
//
//        // ... private members omitted
//
//        public EngineSolarSensor(IMqttClient client) {
//            this.client = client;
//        }
//
//        @Override
//        public Void call() throws Exception {
//            if ( !client.isConnected()) {
//                return null;
//            }
//
//            client.publish(TOPIC1,msg);
//            client.publish(TOPIC2,msg);
//            return null;
//        }
//
//
//}
