package com.app.kafka.demokafka.controller;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

@RestController
@RequestMapping("topics")
public class TopicController {
    @Autowired
    KafkaAdmin kafkaAdmin;

    Logger log = Logger.getLogger(TopicController.class.getName());

    @PostMapping
    public String createTopic(@RequestBody Map<String, String> topicDetails) {
        log.info("in topic controller");
        log.info("creating topic with name " + topicDetails);
        try {
            createNewTopic(topicDetails);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "done";
    }

    @GetMapping
    public Set<String> listAllTopic(){
        AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties());
        ListTopicsResult listTopicsResult = adminClient.listTopics();
        Set<String> topics = null;
        try {
            topics = listTopicsResult.names().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        log.info("all topics are "+topics);

        return topics;
    }

    private void createNewTopic(Map<String, String> topicDetails) throws ExecutionException, InterruptedException {
        Map<String, String> topicConfig = new HashMap<>();
        topicConfig.put(TopicConfig.RETENTION_MS_CONFIG, String.valueOf(24 * 60 * 60 * 1000)); // 24 hours retention

        NewTopic newTopic = new NewTopic(topicDetails.get("topic"), 1, (short) 1).configs(topicConfig);

        try (AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {
            //Blocking call to make sure topic is created
            adminClient.createTopics(Collections.singletonList(newTopic)).all().get();
        }
    }

}
