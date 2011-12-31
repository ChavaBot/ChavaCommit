package com.alta189.chavacommit.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.yaml.snakeyaml.Yaml;

import com.alta189.chavabot.ChavaManager;
import com.alta189.chavacommit.CommitManager;

public class Formatter {
	private List<Commit> commits = new ArrayList<Commit>();
	private Repository repo = null;

	@SuppressWarnings("unchecked")
	public void load(String payload) {
		Yaml yaml = new Yaml();
		Object obj = yaml.load(payload);
		if (obj instanceof String) {
			System.out.println(obj);
		} else {
			HashMap<String, Object> map = (HashMap<String, Object>) obj; // Load entire payload

			// Load the Repository \\
			HashMap<String, Object> repo = (HashMap<String, Object>) map.get("repository");

			this.repo = new Repository(repo);
			this.repo.setBranch(((String) map.get("ref")).replaceAll("refs/heads/", ""));

			// Load Commits \\
			ArrayList<Object> commits = (ArrayList<Object>) map.get("commits");
			for (Object commitObj : commits) {
				HashMap<String, Object> commitMap = (HashMap<String, Object>) commitObj;
				Commit commit = new Commit(commitMap);
				if (CommitManager.isUnique(commit.getId())) {
					CommitManager.addCommit(commit.getId());
					this.commits.add(commit);
				}
				
			}
		}
	}

	public void send(Channel channel) {
		if (repo != null && commits.size() > 0) {
			for (Commit commit : commits) {
				ChavaManager.getInstance().getChavaBot().sendMessage(channel, "\u000306" + Colors.BOLD + repo.getName() + Colors.NORMAL + ": " + "\u000303" + commit.getAuthor().getName() + " " + Colors.MAGENTA + repo.getBranch() + Colors.NORMAL + " - " + Colors.BLUE + commit.getShortURL());
				ChavaManager.getInstance().getChavaBot().sendMessage(channel, "    Files Added: " + Colors.RED + commit.getAdded().size() + Colors.NORMAL + "   Files Removed: " + Colors.RED + commit.getRemoved().size() + Colors.NORMAL + "   Files Modified: " + Colors.RED + commit.getModified().size());
				for (String msg : commit.getMessage().split("\n")) {
					ChavaManager.getInstance().getChavaBot().sendMessage(channel, Colors.BLUE + "    - '" + Colors.NORMAL + msg + Colors.BLUE + "'");
				}
			}
		}
	}
	
	public Repository getRepo() {
		return this.repo;
	}
	
}
