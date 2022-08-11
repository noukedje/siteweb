package com.gestion.commerce.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.commerce.Model.Utilisateur;
import com.gestion.commerce.Service.MailService;

@RestController
public class MailController {

	@Autowired
	private MailService notificationService;

	@Autowired
	private Utilisateur user;

	@RequestMapping("/services/sendmail")
	public String send() {
		//user.setEmailAddress("receiver's email address");  
		try {
			//notificationService.sendEmail(user);
		} catch (MailException mailException) {
			System.out.println(mailException);
		}
		return "Your mail has been send to the user.";
	}
	@RequestMapping("send-mail-attachment")
	public String sendWithAttachment() throws MessagingException {
		//user.setEmailAddress("receiver's email address"); 
		try {
	//	notificationService.sendEmailWithAttachment(user,"attachment");
		} catch (MailException mailException) {
			System.out.println(mailException);
		}
		return "Your mail has been send to the user.";
	}
}