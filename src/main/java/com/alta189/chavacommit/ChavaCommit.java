package com.alta189.chavacommit;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import com.alta189.chavabot.events.Order;
import com.alta189.chavabot.events.channelevents.MessageEvent;
import com.alta189.chavabot.plugins.java.JavaPlugin;
import com.alta189.chavabot.util.SettingsHandler;
import com.alta189.chavacommit.ShortUrlService.Service;
import com.alta189.chavacommit.server.Server;

public class ChavaCommit extends JavaPlugin {
	private static SettingsHandler commits;
	private static SettingsHandler authors;
	private SettingsHandler settings;
	private String logPrefix = "[ChavaCommit] ";
	private Server server;
	
	@Override
	public void onDisable() {
		if (server != null) server.interrupt();
		server = null;
		ChavaCommit.commits = null;
		ChavaCommit.authors = null;
		settings = null;
		
		getLogger().log(Level.INFO, logPrefix + "Disabled!");
	}

	@Override
	public void onEnable() {
		getDataFolder().mkdir();
		try {
			ChavaCommit.commits = new SettingsHandler(ChavaCommit.class.getResource("commit").openStream(), new File(getDataFolder(), "commits.properties"));
			settings = new SettingsHandler(ChavaCommit.class.getResource("settings").openStream(), new File(getDataFolder(), "settings.properties"));
			ChavaCommit.authors = new SettingsHandler(ChavaCommit.class.getResource("authors").openStream(), new File(getDataFolder(), "authors.properties"));
			ChavaCommit.commits.setCached(true);
			ChavaCommit.commits.load();
			ChavaCommit.authors.setCached(true);
			ChavaCommit.authors.load();
			settings.load();
		} catch (IOException e) {
			e.printStackTrace();
			getPluginLoader().disablePlugin(this);
			return;
		}
		if (!settings.checkProperty("listen-port")) {
			getLogger().log(Level.INFO, logPrefix + "Listen Port is not defined in the settings file, defaulting to 5555");
		}
		if (!settings.checkProperty("service")) {
			getLogger().log(Level.SEVERE, logPrefix + "Service is not defined in the settings file!");
			getLogger().log(Level.SEVERE, logPrefix + "Disabling");
			getPluginLoader().disablePlugin(this);
			return;
		}
		if (!settings.checkProperty("user")) {
			getLogger().log(Level.INFO, logPrefix + "User is not defined in the settings file!");
			getLogger().log(Level.SEVERE, logPrefix + "Disabling");
			getPluginLoader().disablePlugin(this);
			return;
		}
		if (!settings.checkProperty("api-key")) {
			getLogger().log(Level.INFO, logPrefix + "API Key is not defined in the settings file!");
			getLogger().log(Level.SEVERE, logPrefix + "Disabling");
			getPluginLoader().disablePlugin(this);
			return;
		}
		
		Service service = null;
		
		try {
			service = Service.valueOf(settings.getPropertyString("service", null).toUpperCase());
			if (service == null) {
				throw new Exception();		
			}
		} catch (Exception e) {
			getLogger().log(Level.SEVERE, logPrefix + "Service defined in settings file is invalid!");
			getLogger().log(Level.SEVERE, logPrefix + "Disabling");
			getPluginLoader().disablePlugin(this);
			return;	
		}
		
		String user = settings.getPropertyString("user", null);
		String apiKey = settings.getPropertyString("api-key", null);
		
		ShortUrlService.setService(service);
		ShortUrlService.setUser(user);
		ShortUrlService.setApiKey(apiKey);
		
		server = new Server(settings.getPropertyInteger("listen-port", 5555));
		server.start();
		
		getLogger().log(Level.INFO, logPrefix + "Enabled!");
		
		MessageEvent.register(new CommitMessageListener(), Order.Default, this);
	}

	public static SettingsHandler getCommitSettings() {
		return commits;
	}

	public static SettingsHandler getAuthors() {
		return authors;
	}

}
