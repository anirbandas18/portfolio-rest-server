package com.teenthofabud.portfolio.model.fields;

import java.util.Comparator;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.teenthofabud.portfolio.core.validation.groups.RequestBodyValidation;
import com.teenthofabud.portfolio.core.validation.groups.RequestParamValidation;


public class Location implements Comparable<Location> {

	@NotEmpty(message = "city can't be blank", groups = RequestBodyValidation.class)
	@NotBlank(message = "city can't be blank", groups = RequestBodyValidation.class)
	@Pattern(regexp = "^[a-zA-Z\\s]+$", message = "city should only contain alphabets and space", groups = { RequestParamValidation.class, RequestBodyValidation.class})
	private String city;
	@NotEmpty(message = "state can't be blank", groups = RequestBodyValidation.class)
	@NotBlank(message = "state can't be blank", groups = RequestBodyValidation.class)
	@Pattern(regexp = "^[a-zA-Z\\s]+$", message = "state should only contain alphabets and space", groups = { RequestParamValidation.class, RequestBodyValidation.class})
	private String state;
	@NotEmpty(message = "country can't be blank", groups = RequestBodyValidation.class)
	@NotBlank(message = "country can't be blank", groups = RequestBodyValidation.class)
	@Pattern(regexp = "^[a-zA-Z\\s]+$", message = "country should only contain alphabets and space", groups = { RequestParamValidation.class, RequestBodyValidation.class})
	private String country;
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public int compareTo(Location o) {
		// TODO Auto-generated method stub
		return Comparator.comparing(Location::getCity)
				.thenComparing(Location::getState)
				.thenComparing(Location::getState)
				.compare(this, o);
	}

}
