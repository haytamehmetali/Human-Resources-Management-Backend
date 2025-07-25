package owl.humanresourcesmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import owl.humanresourcesmanagement.dto.request.MailSenderRequestDto;

@Service
public class MailSenderService {
	private final JavaMailSender javaMailSender;
	
	@Autowired
	public MailSenderService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	public void sendMail(MailSenderRequestDto dto) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		
		simpleMailMessage.setTo(dto.mail());
		simpleMailMessage.setSubject("Quick HR Activation Procedures");
		simpleMailMessage.setText("Activation Code: " + dto.activationCode());
		simpleMailMessage.setFrom("noreply@my-app.com");
		
		javaMailSender.send(simpleMailMessage);
	}
	
	public void sendPasswordMail(MailSenderRequestDto dto){
		SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
		
		simpleMailMessage.setTo(dto.mail());
		simpleMailMessage.setSubject("Quick HR Password Reset");
		simpleMailMessage.setText("Activation Code For Password Renewal: " + dto.activationCode());
		simpleMailMessage.setFrom("noreply@my-app.com");
		
		javaMailSender.send(simpleMailMessage);
	}
}
