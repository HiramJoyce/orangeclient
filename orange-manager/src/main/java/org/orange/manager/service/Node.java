package org.orange.manager.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Node {
	private String host;
	private String group;
	private int port;
	private Socket socket;

	public String send(String data) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // 输入，from 客户端
			PrintWriter out = new PrintWriter(socket.getOutputStream()); // 输出，to 客户端
			System.out.println(in.readLine()); // 打印 客户 socket 发过来的字符，按行(\n,\r,或\r\n)
			out.write(data);
			out.flush(); // to 客户端，输出
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return host;
	}

	public String close() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return host;
	}

	public String onMessage() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // 输入，from 客户端
			PrintWriter out = new PrintWriter(socket.getOutputStream()); // 输出，to 客户端
			System.out.println(in.readLine()); // 打印 客户 socket 发过来的字符，按行(\n,\r,或\r\n)
			out.println("Server reponse! ^_^ ");
			out.flush(); // to 客户端，输出
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return host;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
}
