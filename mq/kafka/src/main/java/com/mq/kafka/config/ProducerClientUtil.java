package com.mq.kafka.config;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProducerClientUtil {


    private KafkaProducer kafkaProducer;
    private static String BROKER_LISTS = "localhost:9092,localhost:9093";

    public static ProducerClientUtil  create(){

        ProducerClientUtil producerClientUtil = new ProducerClientUtil();
        KafkaProducer producer = new KafkaProducer(initConfig());
        producerClientUtil.kafkaProducer = producer;
        return producerClientUtil;
    }

    public void   sendData(String TOPIC,String key,String data){
        kafkaProducer.send(new ProducerRecord<String, String>(TOPIC, key, data), new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                System.out.println("recordMetadata = " + recordMetadata);
            }
        });
    }

    private static  Properties initConfig(){
        Properties props = new Properties();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKER_LISTS);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return props;
    }

}