import java.io.*;
import java.net.*;

public class Server{
	
	private Socket socket = null;
	private ServerSocket server = null;
	private DataInputStream in = null;
	
	public Server(int port) {
		
		try {
			server = new ServerSocket(port);
			
			System.out.println("Server started");
			
			System.out.println("Waiting for a client ...");
		
			socket = server.accept();
			System.out.println("Client accepted");
			
			in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			
			String line = "";
			
			
			
			// close the connection when typing Over into the client's Console
			while (!line.equals("Over")) {
				
				try {
					
					line = in.readUTF();
					System.out.println(line);
				}
				catch(IOException i) {
					
					System.out.println(i);
				}
			}
			System.out.println("Closing connection");
			
			socket.close();
			in.close();
		}
		catch(IOException i) {
			System.out.println(i);
		}
	}
	

	public static void main(String[] args) {
		// start the server
		Server server = new Server(5000);
		
	}
	
}