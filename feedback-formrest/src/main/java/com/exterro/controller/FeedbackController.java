package com.exterro.controller;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exterro.entity.FormData;
import com.exterro.entity.Question;
import com.exterro.entity.UserAnswer;
import com.exterro.entity.UserEntity;
import com.exterro.repository.FormDataRepository;
import com.exterro.repository.QuestionRepository;
import com.exterro.repository.UserAnswerRepository;
import com.exterro.repository.UserEntityRepository;
import com.exterro.repository.UserRepository;
import com.exterro.request.UserRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class FeedbackController {
	
	private JavaMailSender javaMailSender = null;
	
	@Autowired
    private FormDataRepository formDataRepository;

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private UserAnswerRepository userAnswerRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserEntityRepository userEntityRepository;
	
	
	
	@Autowired
    public void DataApiController(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

	@GetMapping("/data/api/questions/{batch}")
	@ResponseBody
	public ResponseEntity<List<Question>> getQuestionsApi(@PathVariable String batch) {
	
		if(batch.equals("allData")) {
			return ResponseEntity.ok(questionRepository.findAll());
		}
		return ResponseEntity.ok(questionRepository.findByBatch(batch));
	}
	
	
	@PostMapping("/question")

	public ResponseEntity<String> submitQuestion(@RequestBody String qst)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper obj = new ObjectMapper();
		Question detail = obj.readValue(qst, Question.class);
		Question feedback = new Question(1, detail.getText1(), detail.getBatch());
		questionRepository.save(feedback);
		return ResponseEntity.ok("feedbackConfirmation.html");
	}
	
	

	private static final SecretKey SECRET_KEY = generateSecretKey();
	private static SecretKey generateSecretKey() {
	    try {
	        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
	        keyGenerator.init(256);
	        SecretKey s = keyGenerator.generateKey();
	        System.out.println("Haiiiiiiii......"+s);
	        return s;
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
	    return null;
	}	

	@PostMapping("/store")
	public ResponseEntity<String> storeFormData(@RequestParam String data, HttpServletResponse response) throws JsonProcessingException {
	    ObjectMapper obj = new ObjectMapper();
	    FormData detail = obj.readValue(data, FormData.class);
	    FormData feedback = new FormData();
	    System.out.println("Data#$%^&^%$#@^^&%$" + data);

	    feedback.setName(detail.getName());
	    feedback.setEmail(detail.getEmail());
	    feedback.setPhone(detail.getPhone());

	    FormData feed = formDataRepository.save(feedback);
	    long a = feedback.getId();
	    String encryptedValue = encrypt(Long.toString(a));

	    Cookie idCookie = new Cookie("id", encryptedValue);
	    idCookie.setMaxAge(3600);
	    response.addCookie(idCookie);
	    return ResponseEntity.ok("SecondPage.html");
	}

	private String encrypt(String value) {
	    try {
	        Cipher cipher = Cipher.getInstance("AES");
	        cipher.init(Cipher.ENCRYPT_MODE, SECRET_KEY);
	        byte[] encryptedBytes = cipher.doFinal(value.getBytes());
	        return Base64.getEncoder().encodeToString(encryptedBytes);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	@PostMapping("/decrypt")
	public ResponseEntity<String> decryptValue(@RequestParam("encryptedValue") String encryptedValue) {
	    try {
	        Cipher cipher = Cipher.getInstance("AES");
	        cipher.init(Cipher.DECRYPT_MODE, SECRET_KEY);
	        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedValue));
	        return ResponseEntity.ok(new String(decryptedBytes));
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	
	
	
	
//	@PostMapping("/data/api/submit")
//	@ResponseBody
//	public ResponseEntity<String> submitDetails(@RequestParam String uu)
//	       throws JsonMappingException, JsonProcessingException {
//	    ObjectMapper obj = new ObjectMapper();
//	    UserRequest detail = obj.readValue(uu, UserRequest.class);
//	    List<UserAnswer> userAnswers = new ArrayList<>();
//	    List<String> questions = detail.getQuestion();
//	    List<String> answers = detail.getAnswer();
//
//	    if (questions.size() != answers.size()) {
//	        return ResponseEntity.badRequest().body("Number of questions and answers do not match.");
//	    }
//	    for (int i = 0; i < questions.size(); i++) {
//	        String question = questions.get(i);
//	        String answer = answers.get(i);
//	        System.out.println(answers.get(i));
//	        if (question != null && !question.isEmpty() && answer != null && !answer.isEmpty()) {
//	        	UserAnswer feedback = new UserAnswer(
//	        		    new UserEntity(detail.getName(), detail.getPhoneNumber(), detail.getEmail()),
//	        		    question, answer, detail.getBatch());
//
//	            userAnswers.add(feedback);
//	        }
//	        else {
//	        	System.out.println("Hello");
//	        }
//	    }
//	    if (!userAnswers.isEmpty()) {
//	        userAnswerRepository.saveAll(userAnswers);
//	    }
//
//	    return ResponseEntity.ok("ResultEmailTrigger.html");
//
//	}

	

    
	//java.html
	@PostMapping("/data/api/submit")
	@ResponseBody
	public ResponseEntity<String> submitDetails(@RequestParam String uu)
	       throws JsonMappingException, JsonProcessingException {
	    ObjectMapper obj = new ObjectMapper();
	    UserRequest detail = obj.readValue(uu, UserRequest.class);
	    List<UserAnswer> userAnswers = new ArrayList<>();
	    List<String> questions = detail.getQuestion();
	    List<String> answers = detail.getAnswer();

	    if (questions.size() != answers.size()) {
	        return ResponseEntity.badRequest().body("Number of questions and answers do not match.");
	    }
	    
	    for (int i = 0; i < questions.size(); i++) {
	        String question = questions.get(i);
	        String answer = answers.get(i);
	      
	        
	        if (question != null && !question.isEmpty() && answer != null && !answer.isEmpty()) {
	        	  System.out.println("Hello WOrld"+detail);
	        	UserAnswer feedback = new UserAnswer(
	        		    new UserEntity(detail.getName(), detail.getPhoneNumber(), detail.getEmail()),
	        		    question, answer, detail.getBatch());

	            userAnswers.add(feedback);
	        }
	        else {
	        	System.out.println("Hello");
	        }
	    }
	    if (!userAnswers.isEmpty()) {
	        userAnswerRepository.saveAll(userAnswers);
	    }

	    return ResponseEntity.ok("feedbackConfirmation.html");
	}

	
	
	
	
	
	
	
	//seconpage.html controller
	
	@PostMapping("/data/api/submit1")
	@ResponseBody
	public ResponseEntity<String> submitDetails1(@RequestParam String uu)
	       throws JsonMappingException, JsonProcessingException {
	    ObjectMapper obj = new ObjectMapper();
	    UserRequest detail = obj.readValue(uu, UserRequest.class);

	   
	    
	    List<UserAnswer> userAnswers = new ArrayList<>();
	    List<String> questions = detail.getQuestion();
	    List<String> answers = detail.getAnswer();

	    if (questions.size() != answers.size()) {
	        return ResponseEntity.badRequest().body("Number of questions and answers do not match.");
	    }
	    for (int i = 0; i < questions.size(); i++) {
	        String question = questions.get(i);
	        String answer = answers.get(i);
	        System.out.println(detail);
	        
	        if (question != null && !question.isEmpty() && answer != null && !answer.isEmpty()) {
	        	UserAnswer feedback = new UserAnswer(
	        		    new UserEntity(detail.getName(), detail.getPhoneNumber(), detail.getEmail()),
	        		    question, answer, detail.getBatch());
	        	System.out.println("feedback @#$%^&*(&^%$%^&*(*&^%$"+feedback);
	            userAnswers.add(feedback);
	        }
	        else {
	        	System.out.println("Hello");
	        }
	    }
	    if (!userAnswers.isEmpty()) {
	        userAnswerRepository.saveAll(userAnswers);
	    }

	    return ResponseEntity.ok("ResultEmailTrigger.html");

	}

	
	
	

	
	@GetMapping("/data/api/results/{batch}")
	@ResponseBody
	public ResponseEntity<List<UserAnswer>> getJavaDataApi(@PathVariable String batch) {
		if (batch.equals("allData")) {
			return ResponseEntity.ok(userAnswerRepository.findAll());
		}
		return ResponseEntity.ok(userAnswerRepository.getFeedbackBatchwise(batch));
	}
	
	
	
	
//	@RequestMapping("/")	
//	public class FrontPageController {
//
//	    @GetMapping
//	    public String showFrontPage() {
//	        return "frontpage";
//	    }
//
//	    @PostMapping("/store")
//	    public String storeFormData(String name, String email, String phone, HttpServletResponse response, RedirectAttributes redirectAttributes) {
//	        // Store the form data in cookies
//	        response.addCookie(new Cookie("name", name));
//	        response.addCookie(new Cookie("email", email));
//	        response.addCookie(new Cookie("phone", phone));
//
//	        // Redirect to the second page
//	        redirectAttributes.addFlashAttribute("successMessage", "Form data stored successfully!");
//	        return "redirect:/secondpage";
//	    }
//	}
	
	
	//Mail Sending Controller(Workable)
	
	
//	@PostMapping("/sendEmail")
//    @ResponseBody
//    public ResponseEntity<String> sendEmail(@RequestParam String recipientEmail,
//                                            @RequestParam String subject,
//                                            @RequestParam String body) {
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setTo(recipientEmail);
//            message.setSubject(subject);
//            message.setText(body);
//            javaMailSender.send(message);
//            return ResponseEntity.ok("Email sent successfully!");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email.");
//        }
//    }
	
	
	
	

	
	//EMAIL TRIGGER SECOND CONTROLLER(WORKABLE OPTIONAL)
	
	
	
	@PostMapping("/sendEmail")
	@ResponseBody
	public ResponseEntity<String> sendEmail(@RequestParam String recipientEmail, @RequestParam String subject, @RequestParam String body) {
	    try {
	        MimeMessage message = javaMailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        helper.setTo(recipientEmail);
	        helper.setSubject(subject);

	        String tableHtml = "<table>" +
	                "<thead>" +
	                "<tr>" +
//	                "<th>Email</th>" +
//	                "<th>Question</th>" +
//	                "<th>Answer</th>" +
	                "</tr>" +
	                "</thead>" +
	                "<tbody>" +
	                body +
	                "</tbody>" +
	                "</table>";

	        helper.setText(tableHtml, true); 

	        javaMailSender.send(message);

	        return ResponseEntity.ok("Email sent successfully.");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending email: " + e.getMessage());
	    }
	}

	
	
	@GetMapping("/data/api/user/{id}")
    public ResponseEntity<FormData> getUserById(@PathVariable Long id) {

        Optional<FormData> userOptional = formDataRepository.findById(id);
        System.out.println(userEntityRepository.findById(id));
        if (userOptional.isPresent()) {
            FormData user = userOptional.get();
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
	}
	
	

}
