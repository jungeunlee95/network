package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class EchoServerReceiveThread extends Thread {
	private Socket socket;

	public EchoServerReceiveThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		// 3-2 host ipㅡport 가져오기
		InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
		String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
		int remotePort = inetRemoteSocketAddress.getPort();
		
		EchoServer.log("conneted by client [ " + remoteHostAddress + ": " + remotePort + " ]");

		try {
			// 4. IOStream 받아오기
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));

			PrintWriter pr = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);
			// true:flash 자동
			while (true) {
				// 5. 데이터 읽기
				String data = br.readLine();
				if (data == null) {
					EchoServer.log("closed by client");
					break;
				}

				EchoServer.log("received : " + data);

				// 6. 데이터 쓰기
				pr.println(data);
			}

		} catch (SocketException e) {
			System.out.println(Thread.currentThread().getId());
			System.out.println("[server] sudden closed by client");
		} catch (IOException e) { // 정상종료 안하고 확 꺼버린 ..!
			e.printStackTrace();
		} finally {
			try {
				if (socket != null && socket.isClosed()) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} // 데이터 통신용 exception
	}

}
