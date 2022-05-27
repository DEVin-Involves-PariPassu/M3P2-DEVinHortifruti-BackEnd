package tech.devinhouse.notificationservice.consumers;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tech.devinhouse.notificationservice.constants.RabbitMQConstants;
import tech.devinhouse.notificationservice.dto.EmailDto;

@Component
public class EmailConsumer {

    @RabbitListener(queues = RabbitMQConstants.FILA_EMAIL)
    private void consumidor(EmailDto email) {
        System.out.println(email.getDestinatario());
        System.out.println(email.getTitulo());
        System.out.println(email.getMensagem());
        System.out.println("--------------------");

        //throw new AmqpRejectAndDontRequeueException("Não jogue na fila novamente");
    }
}
