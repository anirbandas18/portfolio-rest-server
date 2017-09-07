package com.teenthofabud.portfolio.model.collections;

import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.base.Objects;
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
	public void setResumePath(@Value("#root.resumePath ?: 'hello'") String resumePath) {
		this.resumePath = resumePath;
	}
	public String getAvatarPath() {
		return avatarPath;
	}
	public void setAvatarPath(@Value("#root.avatarPath ?: 'avatar'") String avatarPath) {
		this.avatarPath = avatarPath;
	}
	@Override
	public int compareTo(Freelancer o) {
		// TODO Auto-generated method stub
		/*Boolean avatarComparison = Boolean.TRUE;
		Boolean resumeComparison = Boolean.TRUE;	
		try {
			Path avatar1 = Paths.get(this.getAvatarPath());
			Path avatar2 = Paths.get(o.getAvatarPath());
			avatarComparison = FileUtils.contentEquals(avatar1.toFile(), avatar2.toFile());
		} catch (IOException e) {
			avatarComparison = Boolean.FALSE;
		}
		try {
			Path resume1 = Paths.get(this.getResumePath());
			Path resume2 = Paths.get(o.getResumePath());
			resumeComparison = FileUtils.contentEquals(resume1.toFile(), resume2.toFile());	
		} catch (IOException e) {
			resumeComparison = Boolean.FALSE;
		}
		Integer a = avatarComparison ? 0 : 1;
		Integer b = resumeComparison ? 0 : 1;
		Integer c = a.compareTo(b);*/
		Integer i = Comparator.comparing(Freelancer::getId)
				.thenComparing(Freelancer::getDetail)
				.thenComparing(Freelancer::getLocation)
				.thenComparing(Freelancer::getProfile)
				.compare(this, o);
		Integer j = this.getLanguagesKnown().equals(o.getLanguagesKnown()) ? 0 : 1;
		Integer k = CollectionUtils.isEqualCollection(socialLinks, o.getSocialLinks()) ? 0 : 1;
		Integer t = i.compareTo(j);
		Integer x = t.compareTo(k);
		return x;
	}
	public Freelancer() {
		super();
		// TODO Auto-generated constructor stub
	}
	/*@PersistenceConstructor
	public Freelancer(@Value("#root.resumePath ?: 'hello'") String resumePath, @Value("#root.avatarPath ?: 'avatar'") String avatarPath, Detail detail, Profile profile,
			Location location, List<SocialMedia> socialLinks, List<String> languagesKnown) {
		super();
		this.resumePath = resumePath;
		this.avatarPath = avatarPath;
		this.detail = detail;
		this.profile = profile;
		this.location = location;
		this.socialLinks = socialLinks;
		this.languagesKnown = languagesKnown;
	}*/
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public int hashCode() {
		return Objects.hashCode(detail);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Freelancer other = (Freelancer) obj;
		return Objects.equal(detail, other.detail);
	}
	
	
}
