package com.qixi.mq.delay.producer;

import com.qixi.mq.delay.common.constant.RabbitConstant;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 延时消息生产者
 *
 * @author ZhengNC
 * @date 2020/9/21 14:15
 */
@Service
public class TTLProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送一条延时消息
     *
     * @param message
     */
    public void sendTTLMessage(String message){
        rabbitTemplate.convertAndSend(
                RabbitConstant.Exchanges.ttlExchange,
                RabbitConstant.RouterKey.ttlRouteKey,
                message);
    }

    /**
     * 发送一条延时消息（延时插件的实现方式）
     *
     * @param message
     * @param time
     */
    public void sendDelayedMessage(String message, Long time){
        Long finalTime = time == null ? 10000 : time;
        rabbitTemplate.convertAndSend(
                RabbitConstant.Exchanges.delayedExchange,
                RabbitConstant.RouterKey.delayedRouteKey,
                message,
                msg -> {
                    //设置此消息延时十秒
                    msg.getMessageProperties()
                            .setHeader("x-delay", finalTime);
                    return msg;
                });
    }
}
