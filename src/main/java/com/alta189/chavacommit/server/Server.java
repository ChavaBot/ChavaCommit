package com.alta189.chavacommit.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {
	ServerSocket serverSocket;
	List<ConnectionHandler> handlers = new ArrayList<ConnectionHandler>();
	
	public Server(int port){
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(!isInterrupted()){
			try {
				Socket socket = serverSocket.accept();
				if(socket!=null) {
					System.out.println(socket.getRemoteSocketAddress().toString() + " connected!");
					ConnectionHandler handler = new ConnectionHandler(socket, this);
					handlers.add(handler);
					handler.start();
					sleep(1000);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void removeHandler(ConnectionHandler connectionHandler) {
		handlers.remove(connectionHandler);
	}
}
