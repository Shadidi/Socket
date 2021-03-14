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
		
		String username = "[" + address + "]";
		try {
			
			System.out.println("Enter your name: ");	//if they enter nothing they get represented by their IP

			input = new DataInputStream(System.in); // whatever the client is typing
			if (input.readLine() == "") {
				username = "[" + input.readLine() + "]";
			}
			
			socket = new Socket (address, port); //ask name first, THEN connect so the first line said isn't the username
			
			System.out.println("Connected");
			
			out = new DataOutputStream(socket.getOutputStream()); // sends out client's input
		
		}
			
		catch(UnknownHostException u) {
			System.out.println(u);
		}
		catch(IOException i) {
			System.out.println(i);
		}
		
		String line = "";
		
		
		while (!line.equals(Server.exitWord)) {
			
			try {
				
				line = input.readLine();
				out.writeUTF(username); //This format is because because Server checks the last UTF message sent and flips out if it sees a name
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
		
		Client client = new Client ("127.0.0.1", 5000); // Definitely change ip address but add more stuff maybe.
	}	// First try on local host for them to talk to each other then extend to over
		// the real network.

}
