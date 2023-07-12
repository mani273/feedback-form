package com.exterro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.exterro.service.EmailService;

import jakarta.annotation.PostConstruct;

	@SpringBootApplication
	public class SpringBootRelationshipApplication {

//    	private final EmailService emailService;

//	    @Autowired
//	    public SpringBootRelationshipApplication(EmailService emailService) {
//	        this.emailService = emailService;
//	    }

	    public static void main(String[] args) {
	        SpringApplication.run(SpringBootRelationshipApplication.class, args);
	    }

//	    @PostConstruct
//	    public void sendEmail() {
//	        String recipientEmail = "varatharajsubramani419@gmail.com";
//	        String subject = "Hello!";
//	        String body = "This is the email content.";
//
//	        emailService.sendEmail(recipientEmail, subject, body);
//	    }
	}

//	@Bean
//	CommandLineRunner commandLineRunner(UserRepository users, PasswordEncoder encoder) {
//		return args -> {
//			users.save(new User("admin",encoder.encode("admin123"),"ROLE_ADMIN"));
//		};
//	}
	
	
	

