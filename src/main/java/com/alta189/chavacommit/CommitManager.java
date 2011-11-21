package com.alta189.chavacommit;

import java.util.ArrayList;
import java.util.List;

public class CommitManager {
	private static List<String> commits = new ArrayList<String>();
	
	public static void addCommit(String commitID) {
		CommitManager.commits.add(commitID);
	}
	
	public static boolean isUnique(String commitID) {
		return !CommitManager.commits.contains(commitID);
	}
}
