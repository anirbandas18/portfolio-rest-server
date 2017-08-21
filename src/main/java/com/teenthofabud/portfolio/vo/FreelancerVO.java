package com.teenthofabud.portfolio.vo;

import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.teenthofabud.portfolio.model.fields.Detail;
import com.teenthofabud.portfolio.model.fields.Location;
import com.teenthofabud.portfolio.model.fields.Profile;
import com.teenthofabud.portfolio.model.fields.SocialMedia;

@JsonRootName(value = "freelancer")
public class FreelancerVO {
	
	@Valid
	private Detail detail;
	@Valid
	private Profile profile;
	@Valid
	private Location location;
	@Valid
	private List<SocialMedia> socialLinks;
	private List<String> languagesKnown;
	
	public Detail getDetail() {
		return detail;
	}
	public void setDetail(Detail detail) {
		this.detail = detail;
	}
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	public List<SocialMedia> getSocialLinks() {
		return socialLinks;
	}
	public void setSocialLinks(List<SocialMedia> socialLinks) {
		this.socialLinks = socialLinks;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public List<String> getLanguagesKnown() {
		return languagesKnown;
	}
	public void setLanguagesKnown(List<String> languagesKnown) {
		this.languagesKnown = languagesKnown;
	}
	
}
