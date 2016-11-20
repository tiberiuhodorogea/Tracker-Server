package Framework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

import SharedClasses.Utils.DateUtil;

public class ClientManager implements Runnable{

	private static int SO_TIMEOUT_MILIS = 4000;
	private Socket client = null;
	private String message = null;
	BufferedReader reader = null;
	
	public ClientManager(Socket client){
		if(client == null)
			throw new UnsupportedOperationException("Client reference is null!");
		this.client = client;
		
		try {
			client.setSoTimeout(SO_TIMEOUT_MILIS);
			reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		try {
			message = reader.readLine();
		} catch (IOException e) {
			System.out.println("Failed reading client request");
			e.printStackTrace();
		}
		System.out.println("Client request read succesfully, invoking RequestHandler...");
		System.out.println("REQUEST: "+ message + "\nTimestamp: " + DateUtil.fromIntFormat(DateUtil.nowIntFormat()));
		
		try {
			client.setSoTimeout(0);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new RequestHandler(message, client).execute();
	}

}
