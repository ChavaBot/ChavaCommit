package com.alta189.chavacommit.objects;

import java.util.HashMap;

import com.alta189.chavacommit.ChavaCommit;

public class Author {
	private String name;
	private String email;

	public Author() {

	}

	public Author(HashMap<String, Object> author) {
		if (author.containsKey("name")) this.setName((String) author.get("name"));
		if (author.containsKey("email")) this.setEmail((String) author.get("email"));
	}

	public String getName() {		
		return name;
	}

	public void setName(String name) {
		if (ChavaCommit.getAuthors().checkProperty(name))
			name = ChavaCommit.getAuthors().getPropertyString(name, name);
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
