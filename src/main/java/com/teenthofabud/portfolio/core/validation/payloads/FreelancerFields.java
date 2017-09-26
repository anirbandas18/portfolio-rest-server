package com.teenthofabud.portfolio.core.validation.payloads;

import javax.validation.Payload;

public class FreelancerFields {
	
	public static class id implements Payload {};
	
	public static class emailId implements Payload {};
	
	public static class phoneNumber implements Payload {};
	
	public static class firstName implements Payload {};
	
	public static class lastName implements Payload {};
	
	public static class resumeFile implements Payload {};
	
	public static class avatarFile implements Payload {};
}
