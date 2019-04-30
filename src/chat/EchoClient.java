package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
	
	private static final String SERVER_IP = "192.168.1.12";
	private static final int SERVER_PORT = 7000;
	private static String nickName = null;
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("닉네임을 입력하세요 : ");
		nickName = scanner.nextLine();
		
		Socket socket = null;
		
		try {
			// 1. Scanner 생성(표준입력 연결)
			
			// 2. socket 생성
			socket = new Socket();
			
			// 3. 서버 연결
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			log("[" + nickName + "]님 채팅 방에 입장했습니다.");
			
			// 4. IOStream 생성(받아오기)
			BufferedReader br = new BufferedReader(
					new InputStreamReader(socket.getInputStream(),"utf-8"));
			
			PrintWriter pr = new PrintWriter(
					new OutputStreamWriter(socket.getOutputStream(),"utf-8"), true);
			
			int cnt = 0;
			
			while(true) {
				
				if (nickName.isEmpty() == true ) {
					break;
				}
				if(cnt == 0) {
					pr.println(nickName);
				}
				cnt++;
				
				// 5. 키보드 입력받기
				System.out.print(">> ");
				String line = scanner.nextLine();
				if("quit".contentEquals(line)) {
					break;
				}
				
				// 6. 데이터 쓰기
				pr.println(nickName + " : " + line);
				
				// 7. 데이터 읽기
				String data = br.readLine();
				if(data == null) {
					log("closed by server");
					break;
				}
				
				// 8. 콘솔 출력
				System.out.println("<< " + data);
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(scanner != null) {
					scanner.close();
				}
				
				if(socket != null && socket.isClosed() == false) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void log(String log) {
		System.out.println("[client] " + log);
	}

}
