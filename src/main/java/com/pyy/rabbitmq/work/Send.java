package com.pyy.rabbitmq.work;

import com.pyy.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 工作模式
 * @Author panyangyi
 * @create 2020/4/22 14:10
 */
public class Send {

    private static final String QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        String msg = null;

        /**
         * 生产者循环发送50条消息
         */
        for (int i = 0; i < 50; i++) {

            msg = "hello "+i;

            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
        }

        channel.close();
        connection.close();
    }
}
