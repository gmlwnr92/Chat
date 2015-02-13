package com.chatprogram.dclient;

import java.net.*;
import java.io.*;

class DClient extends Thread {
	int port = 3333;
	// String host = "61.83.228.194";
	String host = "192.168.123.159";
	Socket s;
	InputStream is;
	OutputStream os;
	DataInputStream dis;
	DataOutputStream dos;

	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	DClient() {
		try {
			s = new Socket(host, port);
			is = s.getInputStream();
			os = s.getOutputStream();
			dis = new DataInputStream(is);
			dos = new DataOutputStream(os);
			pln("서버와 접속 성공");
			while (true)
				write();
		} catch (UnknownHostException ne) {
			pln("해당 서버를 찾을 수 없슴");
		} catch (IOException e) {
			pln(port + "번 포트는 이미 다른 서비스로 쓰이고 있음");
		}
	}

	void write() { // 키보드 => Socket
		start();
		while (true) {
			try {
				String line = br.readLine();
				dos.writeUTF(line);
				dos.flush();
				Thread.sleep(100);
			} catch (IOException ie) {
			} catch (Exception e) {
			}
		}
	}

	public void run() {
		while (true) {
			try {
				String msgFromClient = dis.readUTF();
				pln("Server >> " + msgFromClient);
				try {
					Thread.sleep(100);
				} catch (Exception e) {
				}
			} catch (IOException ie) {
				pln("서버와 연결이 끊어졌습니다!! , 2초후에 프로그램을 종료하겠습니다.");
				try {
					Thread.sleep(2000);
					System.exit(-1);
				} catch (Exception e) {
				}
			}
		}
	}

	void pln(String str) {
		System.out.println(str);
	}

	public static void main(String[] args) {
		new DClient();
	}
}
