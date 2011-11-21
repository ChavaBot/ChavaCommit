package com.alta189.chavacommit;

import com.alta189.chavabot.events.Listener;
import com.alta189.chavabot.events.channelevents.MessageEvent;

public class CommitMessageListener implements Listener<MessageEvent> {

	@Override
	public void onEvent(MessageEvent event) {
		CommandParser.parse(event.getMessage(), event.getUser().getNick(), event.getChannel());
	}

}
