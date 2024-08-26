package com.app.springstreamkafka.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
@Slf4j

public class MessagePublishController {

    @Autowired
    MessageChannel output;

    @EventListener()
    @PostMapping
    public com.app.springstreamkafka.dto.User publishMessage(@RequestBody  com.app.springstreamkafka.dto.User user){

        output.send(MessageBuilder.withPayload(user).build());


        return user;
    }

}
