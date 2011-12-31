package com.alta189.chavacommit;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

@SuppressWarnings("rawtypes")
public class CommitMessageListener extends ListenerAdapter {

	public void onEvent(MessageEvent event) {
		CommandParser.parse(event.getMessage(), event.getUser().getNick(), event.getChannel().getName());
	}

}
