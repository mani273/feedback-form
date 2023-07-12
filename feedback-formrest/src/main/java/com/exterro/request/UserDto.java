package com.exterro.request;

public class UserDto {
	  private String email;
	  private String phone;
	  private String name;
	  
	  public UserDto() {
		  
	  }

	  public UserDto(String email, String phone, String name) {
	    this.email = email;
	    this.phone = phone;
	    this.name = name;
	  }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "UserDto [email=" + email + ", phone=" + phone + ", name=" + name + "]";
	}

	

	}



