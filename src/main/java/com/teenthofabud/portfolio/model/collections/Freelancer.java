package com.teenthofabud.portfolio.model.collections;

import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.base.Objects;
import com.teenthofabud.portfolio.dto.FileArchiveMetadata;
import com.teenthofabud.portfolio.model.fields.Detail;
import com.teenthofabud.portfolio.model.fields.Location;
import com.teenthofabud.portfolio.model.fields.Profile;
import com.teenthofabud.portfolio.model.fields.SocialMedia;

@Document
public class Freelancer implements Comparable<Freelancer>{

	@Id
	private String id;
	private FileArchiveMetadata resumeMetadata;
	private FileArchiveMetadata avatarMetadata;
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
	public FileArchiveMetadata getResumeMetadata() {
		return resumeMetadata;
	}
	public void setResumeMetadata(FileArchiveMetadata resumeMetadata) {
		this.resumeMetadata = resumeMetadata;
	}
	public FileArchiveMetadata getAvatarMetadata() {
		return avatarMetadata;
	}
	public void setAvatarMetadata(FileArchiveMetadata avatarMetadata) {
		this.avatarMetadata = avatarMetadata;
	}
	@Override
	public int compareTo(Freelancer o) {
		// TODO Auto-generated method stub
		/*Boolean avatarComparison = Boolean.TRUE;
		Boolean resumeComparison = Boolean.TRUE;	
		try {
			Metadata avatar1 = Metadatas.get(this.getAvatarMetadata());
			Metadata avatar2 = Metadatas.get(o.getAvatarMetadata());
			avatarComparison = FileUtils.contentEquals(avatar1.toFile(), avatar2.toFile());
		} catch (IOException e) {
			avatarComparison = Boolean.FALSE;
		}
		try {
			Metadata resume1 = Metadatas.get(this.getResumeMetadata());
			Metadata resume2 = Metadatas.get(o.getResumeMetadata());
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
	public Freelancer(@Value("#root.resumeMetadata ?: 'hello'") String resumeMetadata, @Value("#root.avatarMetadata ?: 'avatar'") String avatarMetadata, Detail detail, Profile profile,
			Location location, List<SocialMedia> socialLinks, List<String> languagesKnown) {
		super();
		this.resumeMetadata = resumeMetadata;
		this.avatarMetadata = avatarMetadata;
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
