package com.pyy.rabbitmq.workfair;

import com.pyy.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author panyangyi
 * @create 2020/4/22 14:15
 */
public class Consumer2 {

    private static final String QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtils.getConnection();

        final Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        channel.basicQos(1);    //设置公平分发需要改动的地方

        DefaultConsumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                String msg = new String(body, "utf-8");

                System.out.println("consumer 2 recv :" + msg);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("consumer 2 done");

                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };

        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);
    }
}
