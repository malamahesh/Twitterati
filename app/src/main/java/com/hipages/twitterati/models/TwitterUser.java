package com.hipages.twitterati.models;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;


public class TwitterUser {

	@SerializedName("screen_name")
	private String screenName;
	
	@SerializedName("name")
	private String name;
	
	@SerializedName("profile_image_url")
	private String profileImageUrl;

	@SerializedName("profile_image")
	private Bitmap profileImage;

	public Bitmap getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(Bitmap profileImage) {
		this.profileImage = profileImage;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
