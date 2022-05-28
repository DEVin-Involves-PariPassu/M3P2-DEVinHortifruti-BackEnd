package tech.devinhouse.devinhortifrutiapi.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.devinhouse.devinhortifrutiapi.constants.RabbitMQConstants;

@Configuration
public class RabbitMQConfiguration {

    @Bean
    AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    //é necessário este bean?
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost", 5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("123456");
        return connectionFactory;
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(RabbitMQConstants.EXCHANGE);
    }

    @Bean
    Queue filaEmail(DirectExchange directExchange) {
        return QueueBuilder.durable(RabbitMQConstants.FILA_EMAIL)
                .deadLetterExchange(directExchange.getName())
                .deadLetterRoutingKey("email-dead-letter")
                .build();
    }

    @Bean
    Queue filaEmailDeadLetter() {
        return QueueBuilder.durable(RabbitMQConstants.FILA_EMAIL + ".dead-letter").build();
    }

    @Bean
    Queue filaSms(DirectExchange directExchange) {
        return QueueBuilder.durable(RabbitMQConstants.FILA_SMS)
                .deadLetterExchange(directExchange.getName())
                .deadLetterRoutingKey("sms-dead-letter")
                .build();
    }

    @Bean
    Queue filaSmsDeadLetter() {
        return QueueBuilder.durable(RabbitMQConstants.FILA_SMS + ".dead-letter").build();
    }

    @Bean
    Binding bindingFilaEmail(Queue filaEmail, DirectExchange directExchange) {
        return BindingBuilder.bind(filaEmail).to(directExchange)
                .with(RabbitMQConstants.FILA_EMAIL);
    }

    @Bean
    Binding bindingFilaEmailDeadLetter(Queue filaEmailDeadLetter, DirectExchange directExchange) {
        return BindingBuilder.bind(filaEmailDeadLetter).to(directExchange)
                .with("email-dead-letter");
    }

    @Bean
    Binding bindingFilaSms(Queue filaSms, DirectExchange directExchange) {
        return BindingBuilder.bind(filaSms).to(directExchange)
                .with(RabbitMQConstants.FILA_SMS);
    }

    @Bean
    Binding bindingFilaSmsDeadLetter(Queue filaSmsDeadLetter, DirectExchange directExchange) {
        return BindingBuilder.bind(filaSmsDeadLetter).to(directExchange)
                .with("sms-dead-letter");
    }

}
