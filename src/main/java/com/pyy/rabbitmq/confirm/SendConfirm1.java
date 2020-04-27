package com.pyy.rabbitmq.confirm;

import com.pyy.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *
 *  confirm机制
 *
 *  注意：如果队列之前已经被设置成AMPQ协议模式，再对同一个队列设置成confirm模式会出异常
 *
 *  普通模式    串行
 *
 * @Author panyangyi
 * @create 2020/4/28 1:13
 */
public class SendConfirm1 {

    private static final String QUEUE_NAME = "test_queue_confirm";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        String msg = "hello confirm";

        channel.confirmSelect();    //生产者调用confirmSelect() 将channel设置为confirm模式

        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

        //确认
        if (!channel.waitForConfirms()){

            System.out.println("message send failed");

        } else {
            System.out.println("message send success");
        }

        channel.close();

        connection.close();
    }
}
