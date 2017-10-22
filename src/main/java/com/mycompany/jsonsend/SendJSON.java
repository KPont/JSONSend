/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jsonsend;

/**
 *
 * @author Kasper
 */
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.AMQP.BasicProperties.Builder;
import com.rabbitmq.client.AMQP.Queue.DeclareOk;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import org.json.*;

public class SendJSON {

    private static final String EXCHANGE_NAME = "cphbusiness.bankJSON";

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("datdb.cphbusiness.dk");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();


        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        
        

        String message = "{\'ssn\':11605787,\'creditScore\':598,\'loanAmount\':10.0,\'loanDuration\':360}";
        JSONObject json = new JSONObject("{\"ssn\": 1160570787,\"creditScore\": 598,\"loanAmount\": 10.0,\"loanDuration\": 360}");
        
        Builder builder = new BasicProperties().builder();
        
        builder.replyTo("kkc-receiver");
        
        BasicProperties prop = builder.build();
        
        BasicProperties props = new BasicProperties
            .Builder()
            .replyTo("john")
            .build();
        
        

        channel.basicPublish(EXCHANGE_NAME, "", prop, json.toString().getBytes());
        System.out.println(" [x] Sent '" + json.toString() + "'");

        channel.close();
        connection.close();
    }
}
