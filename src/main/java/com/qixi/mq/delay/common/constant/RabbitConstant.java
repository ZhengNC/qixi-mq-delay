package com.qixi.mq.delay.common.constant;

/**
 * RabbitMQ常量
 *
 * @author ZhengNC
 * @date 2020/9/21 11:40
 */
public interface RabbitConstant {
    /**
     * 交换机
     */
    interface Exchanges{

        /**
         * 死信交换机
         */
        String deadExchange = "spring.boot.dead.exchange";

        /**
         * 延时交换机
         */
        String ttlExchange = "spring.boot.ttl.exchange";

        /**
         * 延时交换机（通过延时插件实现 rabbitmq_delayed_message_exchange）
         */
        String delayedExchange = "spring.boot.delayed.exchange";
    }

    /**
     * 队列
     */
    interface Queues{

        /**
         * 死信队列
         */
        String deadQueue = "spring.boot.dead.queue";

        /**
         * 延时队列
         */
        String ttlQueue = "spring.boot.ttl.queue";

        /**
         * 延时队列（通过延时插件实现）
         */
        String delayedQueue = "spring.boot.delayed.queue";
    }

    /**
     * 路由key
     */
    interface RouterKey{

        /**
         * 死信路由key
         */
        String deadRouteKey = "dead.route.key";

        /**
         * 延时路由key
         */
        String ttlRouteKey = "ttl.route.key";

        /**
         * 延时路由key（通过延时插件实现）
         */
        String delayedRouteKey = "delayed.route.key";
    }
}
