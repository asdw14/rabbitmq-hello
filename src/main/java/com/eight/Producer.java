package com.eight;

import com.dizhongdi.utils.RabbitMqUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

/**
 * ClassName:Producer
 * Package:com.eight
 * Description:
 *      死信交换机之生产者
 * @Date: 2021/9/14 3:26
 * @Author:dizhongdi
 */
public class Producer {
    //普通交换机
    public static final String NORMAL_EXCHANGE = "normal_exchange";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.TOPIC);
        //设置消息的 TTL 时间
        AMQP.BasicProperties basicProperties =
                new AMQP.BasicProperties().
                        builder().
                        expiration("100000").
                        build();
        for (int i = 0; i < 10; i++) {
            String message = "info" + i;
            channel.basicPublish(NORMAL_EXCHANGE,"zhangsan",basicProperties,message.getBytes("UTF-8"));
        }
    }
}
