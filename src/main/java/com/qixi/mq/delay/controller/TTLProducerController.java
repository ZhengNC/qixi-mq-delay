package com.qixi.mq.delay.controller;

import com.qixi.mq.delay.common.dto.ResponseEntity;
import com.qixi.mq.delay.producer.TTLProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author ZhengNC
 * @date 2020/9/21 14:27
 */
@RestController
@RequestMapping("ttl")
public class TTLProducerController {

    @Autowired
    private TTLProducer producer;

    /**
     * 发送延时消息
     *
     * @return
     */
    @GetMapping("send")
    public ResponseEntity autoSend(){
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        StringBuilder message = new StringBuilder("这是一条延时消息，消息的发送时间为：");
        message.append(timeFormatter.format(LocalTime.now()));
        producer.sendTTLMessage(message.toString());
        return ResponseEntity.success();
    }

    /**
     * 发送延时消息（延时插件实现方式）
     *
     * @return
     */
    @GetMapping("sendDelayedMsg")
    public ResponseEntity<String> sendDelayedMsg(){
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        StringBuilder message = new StringBuilder("这是一条延时消息，消息的发送时间为：");
        message.append(timeFormatter.format(LocalTime.now()));
        producer.sendDelayedMessage(message.toString());
        return ResponseEntity.success();
    }
}
