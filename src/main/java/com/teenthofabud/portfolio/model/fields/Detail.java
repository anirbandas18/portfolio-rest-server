package com.teenthofabud.portfolio.model.fields;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Comparator;

public class Detail implements Comparable<Detail> {

	private String firstName;
	private String lastName;
	private String emailId;
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
