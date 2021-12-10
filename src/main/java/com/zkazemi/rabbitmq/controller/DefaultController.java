package com.zkazemi.rabbitmq.controller;

import com.zkazemi.rabbitmq.model.QueueObject;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RestController
@AllArgsConstructor
public class DefaultController
{
    private final AmqpTemplate defaultExchange;

    @GetMapping("default")
    public ResponseEntity<?> sendMessageWithDefaultExchange()
    {
        QueueObject object = new QueueObject("default", LocalDateTime.now());

        defaultExchange.convertAndSend(object);

        return ResponseEntity.ok(true);
    }
}
