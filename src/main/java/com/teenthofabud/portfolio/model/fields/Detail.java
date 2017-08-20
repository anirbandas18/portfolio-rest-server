package com.teenthofabud.portfolio.model.fields;

import java.util.Comparator;
import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

public class Detail implements Comparable<Detail> {

	@NotEmpty(message = "first name can't be blank")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "first name should only contain alphabets")
	private String firstName;
	@NotEmpty(message = "last name can't be blank")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "last name should only contain alphabets")
	private String lastName;
	@NotEmpty(message = "email id can't be blank")
	@Email
	private String emailId;
	@NotEmpty(message = "phone number can't be blank")
	@Pattern(regexp = "^[0-9]{10}$", message = "phone number should only 10 contain digits")
	private String phoneNumber;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private Date dateOfBirth;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Override
	public int compareTo(Detail o) {
		// TODO Auto-generated method stub
		return Comparator.comparing(Detail::getFirstName)
				.thenComparing(Detail::getLastName)
				.thenComparing(Detail::getEmailId)
				.thenComparing(Detail::getPhoneNumber)
				.thenComparing(Detail::getDateOfBirth)
				.compare(this, o);
	}

}
