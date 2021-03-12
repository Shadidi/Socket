// client representing another device 
// trying to play the same audio to from its speaker
// to demonstrate a distributed system of more than one device 
// playing the same audio file. The access to more resource 
// would be the additional speaker/s.


import java.io.*;
import java.net.*;

public class Client{

	private Socket socket = null;
	private DataInputStream input = null;
	private DataOutputStream out = null;
	
	
	public Client(String address, int port) {
		try {
			
			socket = new Socket (address, port);
			System.out.println("Connected");
			
			input = new DataInputStream(System.in); // whatever the client is typing
			
			out = new DataOutputStream(socket.getOutputStream()); // sends out client's input
		
		}
			
		catch(UnknownHostException u) {
			System.out.println(u);
		}
		catch(IOException i) {
			System.out.println(i);
		}
		
		String line = "";
		
		
		while (!line.equals("Over")) {
			
			try {
				
				
				line = input.readLine();
				
				out.writeUTF(line);
			}
			catch(IOException i) {
				System.out.println(i);
			}
		}
		
		try {
			input.close();
			out.close();
			socket.close();
		}
		
		catch(IOException i){
		
			System.out.println(i);
		}
	}
	
	public static void main(String args[]) {
        // start the client
		// Client client2 = new Client 
		Client client = new Client ("127.0.0.1", 5000); // Definatly change ip address but add more stuff maybe.
	}	// First try on local host for them to talk to each other then extend to over
		// the real netweork.

}
