package com.eight;

import com.dizhongdi.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:Consumer01
 * Package:com.eight
 * Description:
 *  死信交换机
 *  消费者C2
 * @Date: 2021/9/14 3:06
 * @Author:dizhongdi
 */
public class Consumer02 {

    //死信队列
    public static final String DEAD_QUEUE= "dead_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        //死信交换机和队列
        channel.queueDeclare(DEAD_QUEUE,false,false,false,null);

        DeliverCallback deliverCallback = (consumerTag , message) ->{
            String s = new String(message.getBody(), "UTF-8");
            System.out.println("Consumer02接收消息: " +s);
        };
        channel.basicConsume(DEAD_QUEUE,true,deliverCallback, cancelCallback ->{});


    }
}
