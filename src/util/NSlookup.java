package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class NSlookup {

	public static void main(String[] args) {
		
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String hostname = "";

		while(true) {
			try {
				System.out.print("> ");				
				try {
					hostname = br.readLine();
					
//					String s = new String("exit"); // 객체가 몇개 생길까?
					
//					if(hostname.equals("exit")) { // NullPointException날수있음
					if("exit".equals(hostname)) { // 이게 더 안전함 
						break;
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				InetAddress[] inetAddresses = InetAddress.getAllByName(hostname);
				for(InetAddress addr : inetAddresses) {
					System.out.println(hostname + " : " + addr.getHostAddress());
				}
				
			} catch (UnknownHostException e) {
//				e.printStackTrace();
				System.out.println("해당하는 IP Adrress가 없습니다.");
			}

		}
	}

}
