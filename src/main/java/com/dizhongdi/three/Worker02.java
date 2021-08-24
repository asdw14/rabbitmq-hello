package com.dizhongdi.three;

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
public class Worker02 {
    public static final String QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws Exception {
        System.out.println("C1 等待接收消息处理时间较短");
        Channel channel = RabbitMqUtils.getChannel();
        channel.basicQos(2);
        //推送的消息如何进行消费的接口回调
        DeliverCallback deliverCallback = (var1, delivery)->{
            String message= new String(delivery.getBody());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("接收到消息:"+message);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
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
        System.out.println("C1 消费者启动等待消费......");
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME,autoAck,deliverCallback,cancelCallback);
    }
}
