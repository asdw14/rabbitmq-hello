package com.dizhongdi.priority;

import com.dizhongdi.utils.RabbitMqUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:Producer
 * Package:com.dizhongdi.priority
 * Description:
 *      有优先级的生产者
 * @Date: 2022/7/24 23:58
 * @Author:dizhongdi
 */
public class Producer {
    private static final String QUEUE_NAME="hello";

    public static void main(String[] args) throws Exception {
        try (Channel channel = RabbitMqUtils.getChannel();) {
            //惰性队列
            Map<String, Object> arg = new HashMap<String, Object>();
            arg.put("x-queue-mode", "lazy");
            channel.queueDeclare("myqueue", false, false, false, arg);

            //给消息赋予一个 priority 属性
            AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().priority(5).build();
            for (int i = 0; i <= 10; i++) {
                String message = "info"+i;
                if (i==5){
                    channel.basicPublish("",QUEUE_NAME,properties,message.getBytes());
                }else {
                    channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
                }
                System.out.println("发送消息完成:" + message);
            }
        }

    }
}
