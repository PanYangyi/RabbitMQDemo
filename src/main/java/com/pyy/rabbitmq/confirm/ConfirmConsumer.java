package com.pyy.rabbitmq.confirm;

import com.pyy.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author panyangyi
 * @create 2020/4/28 1:20
 */
public class ConfirmConsumer {

    private static final String QUEUE_NAME = "test_queue_confirm";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //定义消费者消费消息
        channel.basicConsume(QUEUE_NAME,true,new DefaultConsumer(channel){
            //获取得到消息实体
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                String msg = new String(body, "utf-8");

                System.out.println("get msg : "+msg);
            }
        });
    }
}
