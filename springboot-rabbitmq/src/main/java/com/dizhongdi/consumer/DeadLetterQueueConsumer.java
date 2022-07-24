package com.dizhongdi.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * ClassName:DeadLetterQueueConsumer
 * Package:com.dizhongdi.consumer
 * Description:
 *
 * @Date: 2022/7/24 21:16
 * @Author:dizhongdi
 */
@Component
@Slf4j
public class DeadLetterQueueConsumer {

    public static final String DELAYED_QUEUE_NAME = "delayed.queue";

    @RabbitListener(queues = "QD")
    public void receiveD(Message message, Channel channel) throws IOException {
        log.info("当前时间：{},收到死信队列信息{}", new Date().toString(), message.getBody().toString());
    }

    //插件实现的自定义时间延迟队列
    @RabbitListener(queues = DELAYED_QUEUE_NAME)
    public void receiveDelayedQueue(Message message){
        String msg = new String(message.getBody());
        log.info("当前时间：{},收到延时队列的消息：{}", new Date().toString(), msg);
    }

}
