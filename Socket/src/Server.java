import java.io.*;
import java.net.*;
// client to client messaging app. The server would be the middle man between them.
// Server would store the input from ClientA and forwards it to ClientB and vise versa.
public class Server{
	
	private Socket socket = null;
	private ServerSocket server = null;
	private DataInputStream in = null;
	
	public Server(int port) {
		
		try {
			server = new ServerSocket(port); //Listen for connections
			
			System.out.println("Server started");
			
			System.out.println("Waiting for a client ...");
		
			socket = server.accept();	//Call this method and only accept the connection when
										//port # is valid. If not throws an exception. 
			System.out.println("Client accepted");
			
			// Store buffered incoming data
			in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			
			// Store input to print it out
			String line = "";
			
			
			
			// close the connection when typing Over into the client's Console
			while (!line.equals("Over")) {
				
				try {
					// Store 'in' value
					line = in.readUTF();
					
					// We will modify the println to send data to other client
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
		Server server = new Server(5000); //port number doesn't have to be 5000
		
	}
	
}
