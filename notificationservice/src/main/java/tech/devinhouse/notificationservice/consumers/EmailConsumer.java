package tech.devinhouse.notificationservice.consumers;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import tech.devinhouse.notificationservice.constants.RabbitMQConstants;
import tech.devinhouse.notificationservice.dto.EmailDto;

@Component
public class EmailConsumer {

    @Autowired
    private JavaMailSender emailSender;

    @RabbitListener(queues = RabbitMQConstants.FILA_EMAIL)
    private void consumidor(EmailDto email) {
        System.out.println("Enviando Email...");
        enviarEmail(email.getDestinatario(), email.getTitulo(), email.getMensagem());
//        System.out.println(email.getDestinatario());
//        System.out.println(email.getTitulo());
//        System.out.println(email.getMensagem());
        System.out.println("--------------------");

        //throw new AmqpRejectAndDontRequeueException("Não jogue na fila novamente");
    }

    private void enviarEmail(String para, String titulo, String texto) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setFrom("noreply@devinhortifruti.com");
        mensagem.setTo(para);
        mensagem.setSubject(titulo);
        mensagem.setText(texto);
        emailSender.send(mensagem);
    }
}
