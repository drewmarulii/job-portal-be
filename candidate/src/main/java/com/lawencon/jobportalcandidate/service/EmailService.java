package com.lawencon.jobportalcandidate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendEmail(String to, String subject, String message) {
		Thread thread = new Thread(){
            public void run(){
                SimpleMailMessage msg = new SimpleMailMessage();
				msg.setTo(to);
				msg.setSubject(subject);
				msg.setText(message);
				javaMailSender.send(msg);
            }
        };
        
        thread.start();

	}
}
