package com.exterro.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;

@Entity
public class UserAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id") 
    private UserEntity user;

    @Transient
    private String email; 

    private String question;
    private String ans;
    private String batch;

    public UserAnswer() {
    	
    }
   
    
    public UserAnswer(UserEntity userEntity, List<String> list, List<String> list2, String string) {
    }

    
    
    
    public UserAnswer(UserEntity user, String question, String ans, String batch) {
        this.user = user;
        this.email = user.getEmail();
        this.question = question;
        this.ans = ans;
        this.batch = batch;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    @Override
    public String toString() {
        return "UserAnswer [id=" + id + ", user=" + user + ", email=" + email + ", question=" + question + ", ans=" + ans
                + ", batch=" + batch + "]";
    }
}
