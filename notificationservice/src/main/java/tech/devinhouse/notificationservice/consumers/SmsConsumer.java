package tech.devinhouse.notificationservice.consumers;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tech.devinhouse.notificationservice.constants.RabbitMQConstants;
import tech.devinhouse.notificationservice.dto.SmsDto;

@Component
public class SmsConsumer {

    @RabbitListener(queues = RabbitMQConstants.FILA_SMS)
    private void consumidor(SmsDto sms) {
        System.out.printf("%s - %s%n", sms.getDestinatario(), sms.getMensagem());
        System.out.println("--------------------");

        //throw new AmqpRejectAndDontRequeueException("Não jogue na fila novamente");
    }
}