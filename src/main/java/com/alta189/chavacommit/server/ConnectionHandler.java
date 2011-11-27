package com.alta189.chavacommit.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URLDecoder;

import com.alta189.chavabot.ChavaManager;
import com.alta189.chavacommit.ChavaCommit;
import com.alta189.chavacommit.objects.Formatter;

public class ConnectionHandler extends Thread {
	private Socket socket;
	private Server server;

	public ConnectionHandler(Socket socket, Server server) {
		this.socket = socket;
		this.server = server;
	}

	@Override
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			boolean post = false;
			boolean headerEnd = false;
			String payload = "";
			boolean stop = true;
			while ((stop)) {
				String cnt = reader.readLine();
				System.out.println(cnt);
				if (cnt == null) {
					System.out.println("line is null");
					break;
				}
				if (cnt.startsWith("POST /")) {
					post = true;
				}
				if (cnt.equals("")) {
					System.out.println("Header end");
					headerEnd = true;
				}
				if (post && headerEnd) {
					String args[] = cnt.split("=");
					if (args.length == 2) {
						if (args[0].equals("payload")) {
							payload = URLDecoder.decode(args[1], "UTF-8");
							stop = false;
							System.out.println("Got some payload!");
						}
					}
				}

			}
			if (payload.equals(""))
				return;

			Formatter formatter = new Formatter();

			formatter.load(payload);
			
			for (String channel : ChavaManager.getInstance().getChavaBot().getChannels()) {
				ChavaManager.getInstance().getChavaBot().sendMessage("#Spouty", "I am in:" + channel);
				if (ChavaCommit.getCommitSettings().checkProperty(channel.replaceAll("#", "@"))) {
					for (String project : ChavaCommit.getCommitSettings().getPropertyString(channel.replaceAll("#", "@"), "Bukkit").split(",")) {
						if (project.equalsIgnoreCase(formatter.getRepo().getName())) formatter.send(channel);
					}
				}
			}
			
			//formatter.send("#spoutdev");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		server.removeHandler(this);
	}
}
