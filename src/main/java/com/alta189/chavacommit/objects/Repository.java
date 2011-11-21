package com.alta189.chavacommit.objects;

import java.util.HashMap;

public class Repository {
	private String name;
	private String url;
	private String description;
	private Integer watchers;
	private Integer forks;
	private Author owner;
	private Boolean isPrivate;
	private String branch;
	
	public Repository() {
		
	}
	
	public Repository(HashMap<String,Object> repo) {
		this.setName((String)repo.get("name"));
		this.setDescription((String)repo.get("description"));
		this.setOwner(owner);
		this.setForks((Integer)repo.get("forks"));
		this.setWatchers((Integer)repo.get("watchers"));
		this.setUrl((String)repo.get("url"));
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Integer getWatchers() {
		return watchers;
	}
	
	public void setWatchers(Integer watchers) {
		this.watchers = watchers;
	}
	
	public Integer getForks() {
		return forks;
	}
	
	public void setForks(Integer forks) {
		this.forks = forks;
	}
	
	public Author getOwner() {
		return owner;
	}
	
	public void setOwner(Author owner) {
		this.owner = owner;
	}
	
	public Boolean getIsPrivate() {
		return isPrivate;
	}
	
	public void setIsPrivate(Boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	
	public String getBranch() {
		return branch;
	}
	
	public void setBranch(String branch) {
		this.branch = branch;
	}
	
	
}
