package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TCPServer {

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			// 1. 서버 소켓 생성
			serverSocket = new ServerSocket();
			
			// 2. 바인딩(binding) : Socket에 SocketAddress(IPAddress + Port)를 바인딩 한다.
			InetAddress inetAddress = InetAddress.getLocalHost();
			
//			String localhost = inetAddress.getHostAddress();
//			serverSocket.bind(new InetSocketAddress(localhost, 5000));
//			serverSocket.bind(new InetSocketAddress(inetAddress, 5000));
			serverSocket.bind(new InetSocketAddress("0.0.0.0", 5000)); 
					//모든 ip허용- 클라이언트가 찌를 때 하나 선택해서 찌름, 연결되면 실제 ip로 변경
			
			// 3. accept : client의 연결요청을 기다린다.
			Socket socket = serverSocket.accept();  // blocking : 안되면 밑에 실행 X
			
			// 3-2 host ipㅡport 가져오기										// down casting
			InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
			// inetRemoteSocketAddress.getAddress() 				  : /ip
			// inetRemoteSocketAddress.getAddress().getHostAddress()  : ip
			String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
			
			int remotePort = inetRemoteSocketAddress.getPort();
			System.out.println("[server] conneted by client [" + remoteHostAddress + ": " + remotePort+"]");
//			System.out.println("connected by client");
			
			try {
				// 4. IOStream 받아오기
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();
				
				while(true) {
					// 5. 데이터 읽기
					byte[] buffer = new byte[256];
					int readByteCount = is.read(buffer); //blocking
					
					if(readByteCount == -1) {
						// 정상 종료 - client쪽에서 우아하게 종료 ! close() 메소드를 호출해서 나 끈다~
						System.out.println("[server] closed by client");
						break;
					}
													// 0번째부터 readByCount까지
					String data = new String(buffer, 0, readByteCount, "utf-8");
					System.out.println("[server] received : " + data);
					
					// 6. 데이터 쓰기 
					os.write(data.getBytes("utf-8"));
					
				}
			}catch(SocketException e) { 
				System.out.println("[server] sudden closed by client");
			}catch(IOException e) { // 정상종료 안하고 확 꺼버린 ..!
				e.printStackTrace();
			}finally {
				try {
					if(socket != null && socket.isClosed()) {
						socket.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
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
		}

	}

}
