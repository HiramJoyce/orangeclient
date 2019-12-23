package org.orange.manager.service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class NodeMaster {

	private static Map<String, Node> nodeMap = new ConcurrentHashMap<>();
	private static boolean enable = true;

	public static void main(String[] args) throws IOException {
		ServerSocket server = null;
		try {
			server = new ServerSocket(10201);
			System.out.println("Server start! " + server);

			while (enable) {
				Socket socket = server.accept(); // 阻塞等待, 直到一个客户端 socket过来
				System.out.println("There comes a socket!");
				int port = socket.getPort();
				InetAddress localAddress = socket.getLocalAddress();

				System.out.println(port);
				System.out.println(localAddress);
				
				Node node = new Node();
				node.setHost(localAddress.getHostAddress());
				node.setPort(port);
				node.setSocket(socket);
				node.send("conn start.");
				nodeMap.put(localAddress.getHostAddress() + "/" + port, node);
				System.out.println(nodeMap);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			server.close();
		}

		System.out.println("Server end!");
	}

}
