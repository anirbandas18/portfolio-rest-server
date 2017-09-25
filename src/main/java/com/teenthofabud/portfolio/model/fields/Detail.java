package com.teenthofabud.portfolio.model.fields;

import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teenthofabud.portfolio.core.tags.groups.RequestBodyValidation;
import com.teenthofabud.portfolio.core.tags.groups.RequestParamValidation;
import com.teenthofabud.portfolio.core.tags.payloads.FreelancerDTO;

@JsonInclude(value = Include.NON_EMPTY)
public class Detail implements Comparable<Detail> {

	@NotEmpty(message = "first name can't be empty", groups = RequestBodyValidation.class)
	@NotBlank(message = "first name can't be blank", groups = RequestBodyValidation.class)
	@Pattern(regexp = "^[a-zA-Z]+$", message = "first name should only contain alphabets", groups = { RequestParamValidation.class, RequestBodyValidation.class}, payload = FreelancerDTO.firstName.class)
	private String firstName;
	@NotEmpty(message = "last name can't be empty", groups = RequestBodyValidation.class)
	@NotBlank(message = "last name can't be blank", groups = RequestBodyValidation.class)
	@Pattern(regexp = "^[a-zA-Z]+$", message = "last name should only contain alphabets", groups = { RequestParamValidation.class, RequestBodyValidation.class}, payload = FreelancerDTO.lastName.class)
	private String lastName;
	@NotEmpty(message = "email id can't be empty", groups = RequestBodyValidation.class)
	@NotBlank(message = "email id can't be blank", groups = RequestBodyValidation.class)
	// @Email(regexp = "^[\\\\w-\\\\+]+(\\\\.[\\\\w]+)*@[\\\\w-]+(\\\\.[\\\\w]+)*(\\\\.[a-z]{2,})$")
	// validate by sending email
	private String emailId;
	@NotEmpty(message = "phone number can't be empty", groups = RequestBodyValidation.class)
	@NotBlank(message = "phone number can't be blank", groups = RequestBodyValidation.class)
	@Pattern(regexp = "((\\+*)((0[ -]+)*|(91 )*)(\\d{12}+|\\d{10}+))|\\d{5}([- ]*)\\d{6}", message = "phone number should only 10 contain digits", groups = { RequestParamValidation.class, RequestBodyValidation.class}, payload = FreelancerDTO.phoneNumber.class)
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
		return Comparator.comparing(Detail::getEmailId)
				.thenComparing(Detail::getPhoneNumber)
				.compare(this, o);
	}

	@Override
	public int hashCode() {
		return Objects.hash(emailId, phoneNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Detail d = (Detail) obj;
		return Objects.equals(emailId, d.emailId) && Objects.equals(phoneNumber, d.phoneNumber);
	}

	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String json = mapper.writeValueAsString(this);
			return json;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	
}
