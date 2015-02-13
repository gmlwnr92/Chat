package com.chatprogram.dserver;

import java.net.*;
import java.io.*;

class DServer extends Thread {
	int port = 3333;
	ServerSocket ss;
	Socket s;
	InputStream is;
	OutputStream os;
	DataInputStream dis;
	DataOutputStream dos;

	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	DServer() {
		try {
			pln(port + "번 포트에서 서버가 대기중...");
			ss = new ServerSocket(port);
			// while(true){
			s = ss.accept();
			is = s.getInputStream();
			os = s.getOutputStream();
			dis = new DataInputStream(is);
			dos = new DataOutputStream(os);
			pln("연결 성공 : " + s.getInetAddress().getHostAddress());
			while (true)
				read();
			// }
		} catch (IOException e) {
		}
	}

	void read() { // Socket => 모니터
		start();
		while (true) {
			try {
				String msgFromClient = dis.readUTF();
				pln("Client >> " + msgFromClient);
				try {
					Thread.sleep(100);
				} catch (Exception e) {
				}
			} catch (IOException ie) {
				pln("클라이언트가 퇴장!! , 2초후에 프로그램을 종료하겠습니다.");
				try {
					Thread.sleep(2000);
					System.exit(-1);
				} catch (Exception e) {
				}
			}
		}
	}

	public void run() {
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

	void pln(String str) {
		System.out.println(str);
	}

	public static void main(String[] args) {
		new DServer();
	}
}
