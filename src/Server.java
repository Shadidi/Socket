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
	
	List<String> listOfClientNames = new ArrayList<String>();
	
	static Vector<ServerThread> clientList;
	private int counter = 0; //counts total clients to ever join. This can get high as long as clients disconnect before we reach the cap.
	
	public static String exitWord = ".exit"; //it's static so I can reference it in ServerThread
	
	public Server(int port){
		Vector<ServerThread> clientList = new Vector<ServerThread>();
		try {
			server = new ServerSocket(port); //Listen for connections
			
			System.out.println("Server started");
			
			
			while(clientList.size() <= 100) { //search for more clients as long as we aren't above max capacity
				//we can raise the number of max clients if we want
			
				socket = server.accept();	//Call this method and only accept the connection when
											//port # is valid. If not throws an exception. 
				
				System.out.println("<<NEW CLIENT ACCEPTED>>");
				
				
				// Store buffered incoming data
				in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
				out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
				
				String newName = in.readUTF();
				
				for (int x = 0; x < clientList.size(); x++){ 
					ServerThread client = clientList.get(x);
					if(client.name.equals(newName)) {
						newName += counter;
					}
				}
				
				ServerThread newClient = new ServerThread(socket, counter, in, out, newName);
				
				Thread thread = new Thread(newClient);
				if(clientList != null) {
					clientList.add(newClient);
					listOfClientNames.add(newClient.name);
					counter++; //This never goes down so clients cant all connect with the same name, have 1 disconnect, and another get on with the same name
					//which leads to the same make
					
					for (int x = 0; x < clientList.size(); x++){ 
						ServerThread client = clientList.get(x);
						if(client.getConnect() == false) {
							clientList.remove(client);
							listOfClientNames.remove(client.name);
						}
					}
				}
				
				System.out.println("List of connected users: " + listOfClientNames.toString());
				thread.start();
				
			}
			System.out.println("Server Full! No more clients can join at this time. Thank you.");
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