package tech.devinhouse.devinhortifrutiapi;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tech.devinhouse.devinhortifrutiapi.constants.RabbitMQConstants;
import tech.devinhouse.devinhortifrutiapi.dto.EmailDto;
import tech.devinhouse.devinhortifrutiapi.dto.SmsDto;

@SpringBootApplication
//public class DevinhortifrutiapiApplication implements CommandLineRunner {
public class DevinhortifrutiapiApplication {

	@Autowired
	private AmqpTemplate amqpTemplate;

	public static void main(String[] args) {
		SpringApplication.run(DevinhortifrutiapiApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		System.out.println("Sending SMS...");
//		SmsDto sms = new SmsDto();
//		sms.setDestinatario("+5548991224064");
//		sms.setMensagem("Teste");
//		amqpTemplate.convertAndSend(RabbitMQConstants.EXCHANGE, RabbitMQConstants.FILA_SMS, sms);
//		System.out.println("Sending email...");
//		EmailDto email = new EmailDto();
//		email.setDestinatario("lbc92@hotmail.com");
//		email.setTitulo("Teste DEVinHortifruti");
//		email.setMensagem("Hello RabbitMQ!");
//		amqpTemplate.convertAndSend(RabbitMQConstants.EXCHANGE, RabbitMQConstants.FILA_EMAIL, email);
//	}
}
