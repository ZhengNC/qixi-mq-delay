package com.qixi.mq.delay.config;

import com.qixi.mq.delay.common.constant.RabbitConstant;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * RabbitMQ配置
 *
 * @author ZhengNC
 * @date 2020/9/14 10:40
 */
@Configuration
public class RabbitConfig {

    /**
     * 死信队列
     *
     * @return
     */
    @Bean("deadQueue")
    public Queue deadQueue(){
        return new Queue(RabbitConstant.Queues.deadQueue);
    }

    /**
     * 延时队列
     *
     * @return
     */
    @Bean("ttlQueue")
    public Queue ttlQueue1(){
        Map<String, Object> args = new HashMap<>(16);
        // 指定消息到期后发送到的死信交换机
        args.put("x-dead-letter-exchange", RabbitConstant.Exchanges.deadExchange);
        // 指定消息到期后发送到的路由键
        args.put("x-dead-letter-routing-key", RabbitConstant.RouterKey.deadRouteKey);
        // 声明队列的过期时间（TTL）（单位：毫秒）
        args.put("x-message-ttl", 10000);
        return QueueBuilder
                .durable(RabbitConstant.Queues.ttlQueue)
                .withArguments(args)
                .build();
    }

    /**
     * 延时队列（通过延时插件实现）
     *
     * @return
     */
    @Bean("delayedQueue")
    public Queue delayedQueue(){
        return new Queue(RabbitConstant.Queues.delayedQueue);
    }

    /**
     * 死信队列交换机
     *
     * @return
     */
    @Bean("deadExchange")
    public DirectExchange deadExchange(){
        return new DirectExchange(RabbitConstant.Exchanges.deadExchange);
    }

    /**
     * 延时队列交换机
     *
     * @return
     */
    @Bean("ttlExchange")
    public DirectExchange ttlExchange(){
        return new DirectExchange(RabbitConstant.Exchanges.ttlExchange);
    }

    /**
     * 延时交换机（通过延时插件实现）
     *
     * @return
     */
    @Bean("delayedExchange")
    public CustomExchange delayedExchange(){
        Map<String, Object> map = new HashMap<>();
        map.put("x-delayed-type", "direct");
        return new CustomExchange(RabbitConstant.Exchanges.delayedExchange,
                "x-delayed-message", true, false, map);
    }

    /**
     * 绑定延时队列和延时交换机
     *
     * @param ttlQueue
     * @param ttlExchange
     * @return
     */
    @Bean
    public Binding ttlQueue_ttlExchange(
            @Qualifier("ttlQueue") Queue ttlQueue,
            @Qualifier("ttlExchange") DirectExchange ttlExchange){
        return BindingBuilder.bind(ttlQueue)
                .to(ttlExchange)
                .with(RabbitConstant.RouterKey.ttlRouteKey);
    }

    /**
     * 绑定死信队列和死信交换机
     *
     * @param deadQueue
     * @param deadExchange
     * @return
     */
    @Bean
    public Binding deadQueue_deadExchange(
            @Qualifier("deadQueue") Queue deadQueue,
            @Qualifier("deadExchange") DirectExchange deadExchange){
        return BindingBuilder.bind(deadQueue)
                .to(deadExchange)
                .with(RabbitConstant.RouterKey.deadRouteKey);
    }

    @Bean
    public Binding delayedQueue_delayedExchange(
            @Qualifier("delayedQueue") Queue delayedQueue,
            @Qualifier("delayedExchange")CustomExchange delayedExchange){
        return BindingBuilder.bind(delayedQueue)
                .to(delayedExchange)
                .with(RabbitConstant.RouterKey.delayedRouteKey)
                .noargs();
    }
}
