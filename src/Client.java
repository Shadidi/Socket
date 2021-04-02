
import java.io.*;
import java.net.*;

public class Client{

	private Socket socket = null;
	private DataInputStream input = null;
	private DataOutputStream out = null;
	private String username = "";
	private boolean sent = false; //checks if the message was sent yet, important for the while loop
	
	
	public Client(String address, int port) {
		
		try {
			
			System.out.println("Enter your name: ");	//if they enter nothing they get represented by their IP
			username = "[" + address + "]";
			input = new DataInputStream(System.in); // whatever the client is typing

			String name = input.readLine();
			if (!name.equals("")) {
				username = "[" + name + "]";
			}
			//InetAddress ip = InetAddress.getByName("localhost"); gets your IP address if you want to use that later
			socket = new Socket (address, port); //ask name first, THEN connect so the first line said isn't the username
			
			System.out.println("Connected");
			
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
		//send.start();  //The main issue: Only the 1st message sends. Based on debugging println the issue is in client. Server keeps listening it seems
		
		
		/*while (!line.equals(Server.exitWord)) {
			
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
		
	}*/ //original code, just in case
	
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
	        				line = input.readLine();
	        				out.writeUTF(username); //This format is because because Server checks the last UTF message sent and flips out if it sees a name
	        				out.writeUTF(line);
	        				sent = true;
        			}
        			catch(IOException i) {
        				System.out.println(i);
        			}
        			try { 
                        // read the message sent to this client 
                        line = input.readUTF(); 
                        System.out.println(line); 
                    } 
                    catch (IOException e) { 
  
                        e.printStackTrace(); 
                    }
        			
        			if(line.equals(Server.exitWord)){
        				break;
        			}
        		}
            	System.out.println("left");
        		try {
        			input.close();
        			out.close();
        			socket.close();
        			System.out.println("closed");
        		}
        		catch(IOException i){
        			System.out.println(i);
        		}
            } 
        }); 
		line = "";
    	
    
			try {
				while (!line.equals(Server.exitWord)) {
					System.out.println(1); 
	    				line = input.readLine();
	    				out.writeUTF(username); //This format is because because Server checks the last UTF message sent and flips out if it sees a name
	    				out.writeUTF(line);
	    				System.out.println(2); 
	    				
	    				read.start();
	    				System.out.println(3);
					}
			}
			catch(IOException i) {
				System.out.println(i);
			}
			try { 
                // read the message sent to this client 
                line = input.readUTF(); 
                System.out.println(line); 
            } 
            catch (IOException e) { 

                e.printStackTrace(); 
            }
    	
		System.out.println("thread done");
		//read.start();
	}
		
	public static void main(String args[]) {
        // start the client
		// Client client2 = new Client 
		
		Client client = new Client ("127.0.0.1", 5000); // Definitely change ip address but add more stuff maybe.
	}	// First try on local host for them to talk to each other then extend to over
		// the real network.

}