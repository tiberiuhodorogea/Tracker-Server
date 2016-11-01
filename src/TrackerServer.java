import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Framework.ClientManager;

public class TrackerServer {

	private static ServerSocket serverSocket;
	private static Socket clientEntrySocket;
	private static final int PORT = 7177;
	
	public static void main(String[] args){
		System.out.println("Binding to port " + PORT +" ...");
		try {
			serverSocket = new ServerSocket(PORT);

		} catch (IOException e) {
			System.out.println("Could not listen on port: "+PORT);
		}
		
		System.out.println("Server started. Listening to the port "+PORT);
		
		while( true ){
			
			try {
				System.out.println("Waiting for client...");
				clientEntrySocket = serverSocket.accept();
				System.out.println("Client connect, passing it to ClientManager...");
				new Thread(new ClientManager(clientEntrySocket)).start();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
