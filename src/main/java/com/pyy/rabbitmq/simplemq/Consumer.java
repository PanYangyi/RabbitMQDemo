package com.pyy.rabbitmq.simplemq;

import com.pyy.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息的消费者
 * @Author panyangyi
 * @create 2020/4/21 23:50
 */
public class Consumer {

    private static final String QUEUE_NAME = "test_queue_1";


    /**
     * 老API
     * @param args
     * @throws IOException
     * @throws TimeoutException
     * @throws InterruptedException
     */
    public static void odlApi(String[] args) throws IOException, TimeoutException, InterruptedException {

        //建立连接
        Connection connection = ConnectionUtils.getConnection();

        //创建通道
        Channel channel = connection.createChannel();
        //申明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //定义队列的消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        //监听队列
        channel.basicConsume(QUEUE_NAME,true,queueingConsumer);


        while (true){

            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();

            String msg = new String(delivery.getBody());

            System.out.println("[consumer msg :]"+msg);
        }

    }

    /**
     * 新API
     * @param args
     */
    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //定义消费者
        DefaultConsumer consumer = new DefaultConsumer(channel){

            //获取到到达的消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                String msg = new String(body, "UTF-8");
                System.out.println("new API recv :"+msg);
            }
        };

        //监听队列
        channel.basicConsume(QUEUE_NAME,true,consumer);

    }


}
