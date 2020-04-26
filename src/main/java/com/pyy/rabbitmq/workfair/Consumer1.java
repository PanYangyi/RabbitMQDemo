package com.pyy.rabbitmq.workfair;

import com.pyy.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 这种是基于轮询分发的，无论消费者12的能力如何，均是每人分发一个消息处理
 *
 * 运行结果是：1 2分别处理偶数、奇数消息
 *
 * @Author panyangyi
 * @create 2020/4/22 14:15
 */
public class Consumer1 {

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

                System.out.println("consumer 1 recv :" + msg);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("consumer 1 done");
                    channel.basicAck(envelope.getDeliveryTag(),false);    //设置成公平分发需要改的地方
                }
            }
        };

        boolean autoAck = false;   //设置成false，手动确认
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);
    }
}
