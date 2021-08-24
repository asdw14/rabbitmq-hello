package com.dizhongdi.four;

import com.dizhongdi.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.MessageProperties;

/**
 * ClassName:ConfirmMessage
 * Package:com.dizhongdi.four
 * Description:
 *
 * @Date: 2021/8/24 21:11
 * @Author:dizhongdi
 */
public class ConfirmMessage {
    public static final String QUEUE_NAME = "Confirm";
    public static final int MESSAGE_COUNT = 1000;

    public static void main(String[] args) throws Exception {
//        publishMessageIndividually();
//        publishMessageBatch();
        publishMessageAsync();
    }

    public static void publishMessageIndividually() throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        //开启发布确认
        channel.confirmSelect();

        long begin = System.currentTimeMillis();
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = i + "";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            //服务端返回 false 或超时时间内未返回，生产者可以消息重发
            boolean flag = channel.waitForConfirms();
            if(flag){
                System.out.println("消息发送成功");
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("发布" + MESSAGE_COUNT + "个单独确认消息,耗时" + (end - begin) +
                "ms");
    }

//    批量确认发布
    public static void publishMessageBatch() throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        //开启发布确认
        channel.confirmSelect();

        long begin = System.currentTimeMillis();
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = i + "";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            //服务端返回 false 或超时时间内未返回，生产者可以消息重发
            if (i%100==0){
                if(channel.waitForConfirms()){
                    System.out.println("消息发送成功");
                }
            }

        }
        long end = System.currentTimeMillis();
        System.out.println("发布" + MESSAGE_COUNT + "个批量确认消息,耗时" + (end - begin) +
                "ms");
    }

//    异步确认发布
    public static void publishMessageAsync() throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        //开启发布确认
        channel.confirmSelect();
        ConfirmCallback ackCallback = (deliveryTag, multiple)->{
            System.out.println("确认的消息: "+deliveryTag);
        };
        ConfirmCallback nackCallback =(deliveryTag, multiple)->{
            System.out.println("未确认的消息: "+deliveryTag);
        };
        channel.addConfirmListener(ackCallback,nackCallback);
        long begin = System.currentTimeMillis();
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = i + "";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());

        }
        long end = System.currentTimeMillis();
        System.out.println("发布" + MESSAGE_COUNT + "异步确认消息,耗时" + (end - begin) +
                "ms");
    }
}
