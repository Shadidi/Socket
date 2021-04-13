import java.io.*;
import java.net.*;
import java.util.*;

public class Client{

	private Socket socket = null;
	private DataInputStream input = null;
	private DataOutputStream out = null;
	private String username = "";
	private Scanner scan;

	
	
	public Client(String address, int port) {
		
		try {
			
			System.out.println("Enter your name: ");	//if they enter nothing they get represented by their IP
			username = "[" + address + "]";
			
			scan = new Scanner(System.in); //the scanner to take our input

			String name = scan.nextLine();
			if (!name.equals("")) {
				username = "[" + name + "]";
			}
			//InetAddress ip = InetAddress.getByName("localhost"); gets your IP address if you want to use that later
			socket = new Socket (address, port); //ask name first, THEN connect so the first line said isn't the username
			
			System.out.println("Connected");
			
			input = new DataInputStream(socket.getInputStream()); 
			out = new DataOutputStream(socket.getOutputStream()); // sends out client's input
			out.writeUTF("<<" + username + " HAS JOINED THE CHAT>>"); //This is sent to nobody, but server needs a message before it accepts more clients
		
		}
			
		catch(UnknownHostException u) {
			System.out.println(u);
		}
		catch(IOException i) {
			System.out.println(i);
		}
		
		String line = "";
		
	
		Thread read = new Thread(new Runnable()  { 
            @Override
            public void run() { 
            	String line = "";
                while (true) { 
                    try { 
                        // read the message sent to this client 
                        line = input.readUTF(); 
                        System.out.println(line); 
                    } 
                    catch (IOException e) { 
  
                        e.printStackTrace(); 
                    } 
                } 
            } 
		});
		
		Thread send = new Thread(new Runnable()  
        { 
            @Override
            public void run() { 
            	String line = "";
            	
            	while (!line.equals(Server.exitWord)) {
        			try {
	        				line = scan.nextLine();
	        				out.writeUTF(username); //This format is because because Server checks the last UTF message sent and flips out if it sees a name
	        				out.writeUTF(line);
        			}
        			catch(IOException i) {
        				System.out.println(i);
        			}
        		}
            	System.out.println("left");
        		try {
        			input.close();
        			out.close();
        			scan.close();
        			socket.close();
        			System.out.println("closed");
        		}
        		catch(IOException i){
        			System.out.println(i);
        		}
            } 
        }); 
		line = "";
		send.start();
    	read.start();
    	
	}
		
	public static void main(String args[]) {
        // start the client
		// Client client2 = new Client 
		
		Client client = new Client ("127.0.0.1", 5000); // Definitely change ip address but add more stuff maybe.
	}	// First try on local host for them to talk to each other then extend to over
		// the real network.

}