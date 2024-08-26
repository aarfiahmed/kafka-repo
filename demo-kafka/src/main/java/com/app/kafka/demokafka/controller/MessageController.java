package com.app.kafka.demokafka.controller;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

@RestController
@RequestMapping("messages")
public class MessageController {
    @Autowired
    private KafkaTemplate<Integer, Object> kafkaTemplate;
    Logger log = Logger.getLogger(MessageController.class.getName());


    @PostMapping
    public String publishMessage(@RequestBody Map<String, String> messageDetails) {
        log.info("message details " + messageDetails);


        CompletableFuture<SendResult<Integer, Object>> send = kafkaTemplate.send(messageDetails.get("topic"), messageDetails.get("message"));
        try {
            log.info("result "+send.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "success";
    }



}
