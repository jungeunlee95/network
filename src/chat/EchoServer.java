package chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	
	private static final int PORT = 7000;
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			// 1. 서버 소켓 생성
			serverSocket = new ServerSocket();
			
			// 2. 바인딩(binding)
			serverSocket.bind(new InetSocketAddress("0.0.0.0", PORT)); 
			log("server starts...[port : " + PORT + "]");

			while(true) {
				// 3. accept 
				Socket socket = serverSocket.accept();  
				
				Thread thread = new EchoServerReceiveThread(socket);
				thread.start();
			}
				
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 							소켓이 닫히지 않았을 경우!
				if(serverSocket != null && serverSocket.isClosed()) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	// 소켓용 exception

	}

	public static void log(String log) {
		System.out.println("[server# "+ Thread.currentThread().getId() + "] " + log);
	}
	


}
