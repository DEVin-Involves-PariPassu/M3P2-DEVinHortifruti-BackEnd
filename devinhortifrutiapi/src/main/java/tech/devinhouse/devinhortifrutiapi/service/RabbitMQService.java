package tech.devinhouse.devinhortifrutiapi.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.devinhouse.devinhortifrutiapi.constants.RabbitMQConstants;
import tech.devinhouse.devinhortifrutiapi.dto.EmailDto;
import tech.devinhouse.devinhortifrutiapi.dto.SmsDto;

@Service
public class RabbitMQService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void enviarEmail(EmailDto mensagem) {
        this.amqpTemplate.convertAndSend(RabbitMQConstants.EXCHANGE, RabbitMQConstants.FILA_EMAIL, mensagem);
    }

    public void enviarSms(SmsDto mensagem) {
        this.amqpTemplate.convertAndSend(RabbitMQConstants.EXCHANGE, RabbitMQConstants.FILA_SMS, mensagem);
    }
}
