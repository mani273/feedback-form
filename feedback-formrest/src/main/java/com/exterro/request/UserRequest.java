package com.exterro.request;

import java.util.List;

public class UserRequest {
    private String name;
    private String phoneNumber;
    private String email;
    private List<String> question;
    private List<String> answer;
    private String batch;
    public UserRequest() {
    	
    }
    
	public UserRequest(String name, String phoneNumber, String email, List<String> question, List<String> answer, String batch) {
		super();
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.question = question;
		this.answer = answer;
		this.batch = batch;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getQuestion() {
		return question;
	}

	public void setQuestion(List<String> question) {
		this.question = question;
	}

	public List<String> getAnswer() {
		return answer;
	}

	public void setAnswer(List<String> answer) {
		this.answer = answer;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	@Override
	public String toString() {
		return "UserRequest [name=" + name + ", phoneNumber=" + phoneNumber + ", email=" + email + ", question="
				+ question + ", answer=" + answer + ", batch=" + batch + "]";
	}
	
    
    
}
