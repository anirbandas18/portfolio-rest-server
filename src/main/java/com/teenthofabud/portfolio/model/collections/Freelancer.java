package com.teenthofabud.portfolio.model.collections;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.teenthofabud.portfolio.model.fields.Location;
import com.teenthofabud.portfolio.model.fields.SocialMedia;

@Document
public class Freelancer {
	
	@Id
	private String id;
	private String firstName;
	private String lastName;
	private String photoURL;
	private Location currentLocation;
	private String emailId;
	private String phoneNumber;
	private List<String> languagesKnown;
	private List<SocialMedia> socialLinks;
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
	public String getPhotoURL() {
		return photoURL;
	}
	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}
	public Location getCurrentLocation() {
		return currentLocation;
	}
	public void setCurrentLocation(Location currentLocation) {
		this.currentLocation = currentLocation;
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
	public List<String> getLanguagesKnown() {
		return languagesKnown;
	}
	public void setLanguagesKnown(List<String> languagesKnown) {
		this.languagesKnown = languagesKnown;
	}
	public List<SocialMedia> getSocialLinks() {
		return socialLinks;
	}
	public void setSocialLinks(List<SocialMedia> socialLinks) {
		this.socialLinks = socialLinks;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}