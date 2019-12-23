package org.orange.manager.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NodeManager {

	public static String send(String host, String data) {
		try {
			// 向本机的10202端口发出客户请求
			Socket socket = new Socket(host, 10202);
			// 由系统标准输入设备构造BufferedReader对象
			// 由Socket对象得到输出流，并构造PrintWriter对象
			PrintWriter os = new PrintWriter(socket.getOutputStream());
			// 由Socket对象得到输入流，并构造相应的BufferedReader对象
			BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// 若从标准输入读入的字符串为 "bye"则停止循环
			// 将从系统标准输入读入的字符串输出到Server
			os.write(data);
			// 刷新输出流，使Server马上收到该字符串
			os.flush();
			// 在系统标准输出上打印读入的字符串
			System.out.println("Client:" + data);
			// 从Server读入一字符串，并打印到标准输出上
			String res = "";
			String line = null;
			while ((line = is.readLine()) != null) {
				res += line;
			}
			System.out.println("Server:" + res);
			os.close(); // 关闭Socket输出流
			is.close(); // 关闭Socket输入流
			socket.close(); // 关闭Socket
			return res;
		} catch (Exception e) {
			e.printStackTrace();// 出错，打印出错信息
		}
		return "";
	}

	public static void main(String[] args) {
		for (int i = 0; i < 1; i++) {
			NodeManager.send("192.168.0.102", "{\r\n" + "                \"home\": \"/root\",\r\n"
					+ "                \"cmds\": [\"ipconfig -all\"]\r\n" + "            }");
		}
	}

}
