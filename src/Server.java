import java.io.*;
import java.util.*;
import java.net.*;
// client to client messaging app. The server would be the middle man between them.
// Server would store the input from ClientA and forwards it to ClientB and vise versa.
public class Server{
	
	private Socket socket = null;
	private ServerSocket server = null;
	private DataInputStream in = null;
	private DataOutputStream out = null;
	
	static Vector<ServerThread> clientList;
	private int counter = 0; //counts our clients
	
	public static String exitWord = "Over"; //project specifically mentioned to close with .exit but if
	//we don't need that we can change it to be more intuitive
	//it's static so I can reference it whereever
	
	public Server(int port){
		Vector<ServerThread> clientList = new Vector<ServerThread>();
		try {
			server = new ServerSocket(port); //Listen for connections
			
			System.out.println("Server started");
			
			
			while(clientList.size() < 2) { //search for more clients as long as we aren't above max capacity
				//we can raise the number of max clients if we want
			
				System.out.println("Waiting for a client ...");
			
				socket = server.accept();	//Call this method and only accept the connection when
											//port # is valid. If not throws an exception. 
				
				System.out.println("Client accepted");
			
				
				// Store buffered incoming data
				in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
				out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
				out.writeUTF("");
				in.readUTF();
				
				ServerThread newClient = new ServerThread(socket, counter, in, out);
				
				
				Thread thread = new Thread(newClient);
				if(clientList != null) {
					clientList.add(newClient);
				}
			
				thread.start();
			}
				// Store input to print it out
				//String line = "";
			
			
			
			// close the connection when typing Over into the client's Console
			//This bit rehomed to ServerThread
			/*while (!line.equals(exitWord)) {
				
				try {
					// Store 'in' value
					line = in.readUTF();
					
					// We will modify the println to send data to other client
					System.out.println(line);
				}
				catch(IOException i) {
					
					System.out.println(i);
				}
				
			}*/
			
		}
		catch(IOException i) {
			System.out.println(i);
		}
	}
	
	

	public static void main(String[] args) throws IOException{
		// start the server
		Server server = new Server(5000); //port number doesn't have to be 5000
		
	}
	
}
