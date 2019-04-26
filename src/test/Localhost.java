package test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Localhost {

	public static void main(String[] args) {
		
		try {
			// 1. ip 주소 가져오기
			InetAddress inetAddress = InetAddress.getLocalHost();
			
			// 2. pc 이름, ip 주소
			String hostname = inetAddress.getHostName();
			String hostAddress = inetAddress.getHostAddress();
			System.out.println(hostname + " : " + hostAddress);

			byte[] addresses = inetAddress.getAddress();
			int cnt = 0;
			for(byte address : addresses) {
				cnt ++;
				System.out.print(address & 0x000000ff);
				if (cnt != addresses.length) {
					System.out.print(".");
				}
			}
			
//			System.out.println("----------------------");
//			InetAddress[] inetAddresses = InetAddress.getAllByName(hostname);
//			for(InetAddress addr : inetAddresses) {
//				System.out.println(addr.getHostAddress());
//			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

	}

}
