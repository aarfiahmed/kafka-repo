package com.app.kafka.demokafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class MessageConsumer {
    Logger log = Logger.getLogger(MessageConsumer.class.getName());


    @KafkaListener(topics = {"firstTopic"})
    public void onMessage(ConsumerRecord<Integer, String> consumerRecord) {

        log.info("Consumer Record: "+ consumerRecord);
    }
}
