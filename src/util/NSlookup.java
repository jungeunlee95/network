package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class NSlookup {

	public static void main(String[] args) {
		
		while(true) {
			try {
				String hostname = "";
				System.out.print("> ");
				InputStreamReader isr = new InputStreamReader(System.in);
				BufferedReader br = new BufferedReader(isr);
				
				try {
					hostname = br.readLine();
					if(hostname.equals("exit")) {
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
				e.printStackTrace();
			}

		}

	}

}
