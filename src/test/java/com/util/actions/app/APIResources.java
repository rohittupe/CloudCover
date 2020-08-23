package com.util.actions.app;

public enum APIResources {
	GetBadgesAPI("/2.2/badges"),
	GetBadgesByIdAPI("/2.2/badges/{ID}"),
	GetBadgesByTagAPI("/2.2/badges/tags"),
	GetBadgesByRecipientsAPI("/2.2/badges/recipients"),
	GetBadgesByRecipientsIdAPI("/2.2/badges/{ID}/recipients");
	
	private String resource;
	
	APIResources(String resource){
		this.resource=resource;
	}

	public String getResource(){
		return resource;
	}

}
