import java.io.*;
import java.net.*;
import java.util.*;

public class Client{

	private Socket socket = null;
	private DataInputStream input = null;
	private DataOutputStream out = null;
	private String username = "";
	private Scanner scan; //This takes our client's input
	private boolean isConnected = false; //To close socket when disconnecting.

	
	
	public Client(String address, int port) {
		
		try {
			isConnected = !isConnected; // now client is connected so true.
			System.out.println("Enter your name: ");	//if they enter nothing they get represented by their IP
			username = address;
			
			scan = new Scanner(System.in); //the scanner to take our input

			String name = scan.nextLine();
			if (!name.equals("")) {
				username = name;
			}
			
			socket = new Socket (address, port); //ask name first, THEN connect so the first line said isn't the username
			
			System.out.println("Connected");
			
			input = new DataInputStream(socket.getInputStream()); 
			out = new DataOutputStream(socket.getOutputStream()); // sends out client's input
			out.writeUTF(username);//This is "sent" to nobody, but server needs a message to know what the client's name is before it accepts more
		
		}
			
		catch(UnknownHostException u) {
			System.out.println(u);
		}
		catch(IOException i) {
			System.out.println(i);
		}
		
	
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
            	
            	while (!line.equals(".exit")) {
        			try {
	        				line = scan.nextLine();
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
		send.start();
		if (!isConnected){ //If client didn't disconnect then read line.
			read.start();
		}
	}
	public String getClientName() {
		return username;
	}
		
	
	public static void main(String args[]) {
        // start the client
		
		Client client = new Client ("127.0.0.1", 5000); //The address can be replaced
	}

}