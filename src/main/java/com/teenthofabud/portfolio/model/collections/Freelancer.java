package com.teenthofabud.portfolio.model.collections;

import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.teenthofabud.portfolio.model.fields.Detail;
import com.teenthofabud.portfolio.model.fields.Location;
import com.teenthofabud.portfolio.model.fields.Profile;
import com.teenthofabud.portfolio.model.fields.SocialMedia;

@Document
public class Freelancer implements Comparable<Freelancer>{

	@Id
	private String id;
	private String resumePath;
	private String avatarPath;
	private Detail detail;
	private Profile profile;
	private Location location;
	private List<SocialMedia> socialLinks;
	private List<String> languagesKnown;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public List<SocialMedia> getSocialLinks() {
		return socialLinks;
	}
	public void setSocialLinks(List<SocialMedia> socialLinks) {
		this.socialLinks = socialLinks;
	}
	public List<String> getLanguagesKnown() {
		return languagesKnown;
	}
	public void setLanguagesKnown(List<String> languagesKnown) {
		this.languagesKnown = languagesKnown;
	}
	public String getResumePath() {
		return resumePath;
	}
	public void setResumePath(String resumePath) {
		this.resumePath = resumePath;
	}
	public String getAvatarPath() {
		return avatarPath;
	}
	public void setAvatarPath(String avatarPath) {
		this.avatarPath = avatarPath;
	}
	@Override
	public int compareTo(Freelancer o) {
		// TODO Auto-generated method stub
		Integer i = Comparator.comparing(Freelancer::getId)
				.thenComparing(Freelancer::getDetail)
				.thenComparing(Freelancer::getLocation)
				.thenComparing(Freelancer::getProfile)
				.thenComparing(Freelancer::getResumePath)
				.thenComparing(Freelancer::getAvatarPath)
				.compare(this, o);
		Integer j = this.getLanguagesKnown().equals(o.getLanguagesKnown()) ? 0 : 1;
		Integer k = CollectionUtils.isEqualCollection(socialLinks, o.getSocialLinks()) ? 0 : 1;
		Integer t = i.compareTo(j);
		return t.compareTo(k);
	}
	
	
}
