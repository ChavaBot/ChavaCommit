package com.alta189.chavacommit;

import java.util.StringTokenizer;

import com.alta189.chavabot.ChavaManager;
import com.alta189.chavaperms.ChavaPerms;

public class CommandParser {
	
	public static void parse(String message, String sender, String channel) {
		if (!message.startsWith("."))
			return;
		StringTokenizer tokens = new StringTokenizer(message);
		String parentCmd = tokens.nextToken().substring(1);
		if (!parentCmd.equalsIgnoreCase("commit") || !parentCmd.equalsIgnoreCase("c")) {
			if (ChavaPerms.getPermsManager().hasPerms(sender, "commit")) {
				String formattedChan = channel.replaceAll("#", "@");
				if (tokens.hasMoreTokens()) {
					String cmd = tokens.nextToken();
					if (cmd.equalsIgnoreCase("info")) {
						if (ChavaCommit.getCommitSettings().checkProperty(formattedChan)) {
							String projects = ChavaCommit.getCommitSettings().getPropertyString(formattedChan, "").replaceAll(",", ", ");
							ChavaManager.getInstance().getChavaBot().sendMessage(channel, sender + ": The projects whose commits will be reported here are " + projects);
						} else {
							ChavaManager.getInstance().getChavaBot().sendMessage(channel, sender + ": No project's commits will be reported here.");
						}
					} else if (cmd.equalsIgnoreCase("add")) {
						if (tokens.hasMoreTokens()) {
							StringBuilder pl = new StringBuilder();
							pl.append(tokens.nextToken());
							while (tokens.hasMoreTokens()) {
								pl.append(",").append(tokens.nextToken());
							}
							
							if (ChavaCommit.getCommitSettings().checkProperty(formattedChan)) { 
								pl.insert(0, ChavaCommit.getCommitSettings().getPropertyString(formattedChan, "ChavaCommit") + ",");
								ChavaCommit.getCommitSettings().changeProperty(formattedChan, pl.toString());
							} else {
								ChavaCommit.getCommitSettings().put(formattedChan, pl.toString());
							}
						}
					} else if (cmd.equalsIgnoreCase("rem")) {
						while (tokens.hasMoreTokens()) {
							remove(formattedChan, tokens.nextToken());
						}
					} else if (cmd.equalsIgnoreCase("reload")) {
						ChavaCommit.getCommitSettings().load();
					}
				}
			} else {
				ChavaManager.getInstance().getChavaBot().sendNotice(sender, "You do not have permission to use this command!");
			}
		}
	}
	
	private static void remove(String chan, String project) {
		if (ChavaCommit.getCommitSettings().checkProperty(chan)) {
			String old = ChavaCommit.getCommitSettings().getPropertyString(chan, "crap");
			if (old.equalsIgnoreCase(project) || old.equalsIgnoreCase(project + ",")) {
				ChavaCommit.getCommitSettings().delete(chan);
			} else {
				old = old.replaceAll(project + ",", "");
				ChavaCommit.getCommitSettings().changeProperty(chan, old);
			}
		}
	}
	
}
