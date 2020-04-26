package com.pyy.rabbitmq.routing;

import com.pyy.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author panyangyi
 * @create 2020/4/22 15:42
 */
public class Consumer1 {


    private static final String EXCHANGE_NAME = "test_exchange_direct";

    private static final String QUEUE_NAME = "test_queue_1";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtils.getConnection();

        final Channel channel = connection.createChannel();

        //声明队列

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        channel.basicQos(1);    //设置公平分发需要改动的地方

        //绑定队列到交换机

        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"key1"); //绑定队列和交换机，并且设置匹配的路由键

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
                    channel.basicAck(envelope.getDeliveryTag(), false);    //设置成公平分发需要改的地方
                }

            }
        };

        boolean autoAck = false;   //设置成false，手动确认
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);

    }
}
