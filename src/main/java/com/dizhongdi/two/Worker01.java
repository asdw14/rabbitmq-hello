package com.dizhongdi.two;

import com.dizhongdi.utils.RabbitMqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * ClassName:Worker01
 * Package:com.dizhongdi.two
 * Description:
 *
 * @Date: 2021/8/22 22:13
 * @Author:dizhongdi
 */
public class Worker01 {
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        //推送的消息如何进行消费的接口回调
        DeliverCallback deliverCallback = (var1, delivery)->{
            String message= new String(delivery.getBody());
            System.out.println("接收到消息:"+message);
        };
        //取消消费的一个回调接口 如在消费的时候队列被删除掉了
        CancelCallback cancelCallback = (consumerTag)->{
            System.out.println(consumerTag+"消费者取消消费接口回调逻辑");
        };
        /**
         * 消费者消费消息
         * 1.消费哪个队列
         * 2.消费成功之后是否要自动应答 true 代表自动应答 false 手动应答
         * 3.消费者未成功消费的回调
         */
        System.out.println("C2 消费者启动等待消费......");
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
    }
}
