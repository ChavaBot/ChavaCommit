package com.alta189.chavacommit.objects;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import com.alta189.chavacommit.ShortUrlService;

public class Commit {
	private String id;
	private String shortID;
	private String message;
	private String url;
	private String shortURL = null;
	private Timestamp timestamp;
	private ArrayList<String> added = new ArrayList<String>();
	private ArrayList<String> removed = new ArrayList<String>();
	private ArrayList<String> modified = new ArrayList<String>();
	private Author author;

	@SuppressWarnings("unchecked")
	public Commit(HashMap<String, Object> commit) {
		HashMap<String, Object> authorMap = (HashMap<String, Object>) commit.get("author");
		Author author = new Author(authorMap);
		this.setAuthor(author);

		this.setMessage((String) commit.get("message"));

		this.setId((String) commit.get("id"));

		this.setUrl((String) commit.get("url"));
		
		int i = 7;
		String cID = "";
		while (getShortURL() == null || getShortURL().equals("") || getShortURL().equals("null")) {
			if (cID.length() >= i) {
				// TODO: Change to logging Core.getSpouty().sendMessage("##Spouty", "alta189: There was an error with getting the short URL!");
				this.setShortURL("error");
				break;
			}
			cID = this.getId().substring(0, i);
			this.setShortURL(ShortUrlService.shorten(url, cID));
			i ++;
		}

		if (commit.containsKey("added")) {
			ArrayList<Object> addedObj = (ArrayList<Object>) commit.get("added");
			for (Object modify : addedObj) {
				added.add((String) modify);
			}
		}

		if (commit.containsKey("removed")) {
			ArrayList<Object> removedObj = (ArrayList<Object>) commit.get("removed");
			for (Object modify : removedObj) {
				removed.add((String) modify);
			}
		}

		if (commit.containsKey("modified")) {
			ArrayList<Object> modifiedObj = (ArrayList<Object>) commit.get("modified");
			for (Object modify : modifiedObj) {
				modified.add((String) modify);
			}
		}

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public ArrayList<String> getAdded() {
		return added;
	}

	public void setAdded(ArrayList<String> added) {
		this.added = added;
	}

	public ArrayList<String> getRemoved() {
		return removed;
	}

	public void setRemoved(ArrayList<String> removed) {
		this.removed = removed;
	}

	public ArrayList<String> getModified() {
		return modified;
	}

	public void setModified(ArrayList<String> modified) {
		this.modified = modified;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public String getShortID() {
		return shortID;
	}

	public void setShortID(String shortID) {
		this.shortID = shortID;
	}

	public String getShortURL() {
		return shortURL;
	}

	public void setShortURL(String shortURL) {
		this.shortURL = shortURL;
	}

}
