package br.com.fiap.spring.configuration;

import br.com.fiap.spring.messaging.RabbitMQReceiver;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.*;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedHashMap;


@Configuration
public class RabbitMQConfig {
    private final String TOPIC_EXCHANGE_NAME = "fiap-exchange3";
    private final String QUEUE_NAME = "fiap-queue3";

    private String username;
    private String password;
    private String vhost;

    @PostConstruct
    private void loadMQInfo() {
        try {
            FileInputStream mqSecret = null;
            for (String path : new String[]{"src/main/resources/config/rabbitmq_secret.json", "rabbitmq_secret.json"})
                if (new File(path).isFile()) {
                    mqSecret = new FileInputStream(path);
                    break;
                }
            var object = (LinkedHashMap<String, String>) new JSONParser(mqSecret).parse();
            username = object.get("username");
            password = object.get("password");
            vhost = object.get("vhost");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Bean
    Queue getQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    TopicExchange getExchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("fiap-email");
    }

    @Bean
    CachingConnectionFactory getConnection() {
        CachingConnectionFactory factory = new CachingConnectionFactory("jackal-01.rmq.cloudamqp.com");
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setVirtualHost(vhost);
        factory.setRequestedHeartBeat(30);
        factory.setConnectionTimeout(30000);

        return factory;
    }

    @Bean
    SimpleMessageListenerContainer getContainer(CachingConnectionFactory connection, MessageListenerAdapter listener) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();

        container.setConnectionFactory(connection);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(listener);

        return container;
    }

    @Bean
    MessageListenerAdapter getListener(RabbitMQReceiver receiver) {
        return new MessageListenerAdapter(receiver, "onMessage");
    }
}
