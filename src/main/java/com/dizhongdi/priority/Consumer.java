package com.dizhongdi.priority;

import com.dizhongdi.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:Consumer
 * Package:com.dizhongdi.priority
 * Description:
 *      带优先级的消息消费者
 * @Date: 2022/7/25 0:02
 * @Author:dizhongdi
 */
public class Consumer {
    private static final String QUEUE_NAME="hello";
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        //设置队列的最大优先级 最大可以设置到 255 官网推荐 1-10 如果设置太高比较吃内存和 CPU
        Map<String, Object> params = new HashMap();
        params.put("x-max-priority", 10);
        channel.queueDeclare(QUEUE_NAME, true, false, false, params);
        System.out.println("消费者启动等待消费......");
        DeliverCallback deliverCallback=(consumerTag, delivery)->{
            String receivedMessage = new String(delivery.getBody());
            System.out.println("接收到消息:"+receivedMessage);
        };
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,(consumerTag)->{
            System.out.println("消费者无法消费消息时调用，如队列被删除");
        });
    }
}
