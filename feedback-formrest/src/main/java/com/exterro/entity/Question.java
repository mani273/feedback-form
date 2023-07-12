package com.exterro.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
//import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Question {

	  @Id
	  @GeneratedValue
	    private int id;
	    private String text1;
	    private String batch;
	    
	    public Question() {
			super();
		}

		public Question(int id, String text1, String batch) {
			super();
			this.id = id;
			this.text1 = text1;
			this.batch = batch;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getText1() {
			return text1;
		}

		public void setText1(String text1) {
			this.text1 = text1;
		}

		public String getBatch() {
			return batch;
		}

		public void setBatch(String batch) {
			this.batch = batch;
		}

		@Override
		public String toString() {
			return "Question [id=" + id + ", text1=" + text1 + ", batch=" + batch + "]";
		}

	    
	    
}