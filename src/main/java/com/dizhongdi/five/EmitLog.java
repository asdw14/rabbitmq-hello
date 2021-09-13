package com.dizhongdi.five;

import com.dizhongdi.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

/**
 * ClassName:EmitLog
 * Package:com.dizhongdi.five
 * Description:
 *      路由模式
 * @Date: 2021/9/14 1:52
 * @Author:dizhongdi
 */
public class EmitLog {
    //交换机名称
    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
        /**
         * 声明一个 exchange
         * 1.exchange 的名称
         * 2.exchange 的类型
         */
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message = scanner.nextLine();
            channel.basicPublish(EXCHANGE_NAME,"asdw14",null,message.getBytes("UTF-8"));
            System.out.println("生产者发出消息"+ message);
        }

    }
}
