package com.example.almoufasseralsaghir.entity;

public class User {
	
	private static final String DEFAULT_UID = "999999999";
	
	private String uid = DEFAULT_UID; 
	private String udid;
	private String name; 
	private String email;
	private String twitter; 
	private String facebook;
	private String follower1;
	private String type1 = "0";
	private String follower2;
	private String type2 = "1";
	private String follower3;
	private String type3 = "2";
	private boolean loggedIn = false;
	private String defaultReciter = "1";
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUdid() {
		return udid;
	}
	public void setUdid(String udid) {
		this.udid = udid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTwitter() {
		return twitter;
	}
	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}
	public String getFacebook() {
		return facebook;
	}
	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}
	public String getFollower1() {
		return follower1;
	}
	public void setFollower1(String follower1) {
		this.follower1 = follower1;
	}
	public String getType1() {
		return type1;
	}
	public void setType1(String type1) {
		this.type1 = type1;
	}
	public String getFollower2() {
		return follower2;
	}
	public void setFollower2(String follower2) {
		this.follower2 = follower2;
	}
	public String getType2() {
		return type2;
	}
	public void setType2(String type2) {
		this.type2 = type2;
	}
	public String getFollower3() {
		return follower3;
	}
	public void setFollower3(String follower3) {
		this.follower3 = follower3;
	}
	public String getType3() {
		return type3;
	}
	public void setType3(String type3) {
		this.type3 = type3;
	}
	public boolean isLoggedIn() {
		return loggedIn;
	}
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	public String getDefaultReciter() {
		return defaultReciter;
	}
	public void setDefaultReciter(String defaultReciter) {
		this.defaultReciter = defaultReciter;
	}
	
	
}
