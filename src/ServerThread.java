import java.io.*;
import java.util.*;
import java.net.*;

public class ServerThread implements Runnable{
	private Socket socket = null;
	private DataInputStream in = null;
	private DataOutputStream out = null;
	private int clientNumber = 0;
	public String name = "";
	
	public ServerThread(Socket s, int num, DataInputStream i, DataOutputStream o) {
		socket = s;
		clientNumber = num;
		in = i;
		out = o;	
	}
	
    public void run() {
    	try {
			name = in.readUTF();
		} catch (IOException e) {
			System.out.println(e);
		}
        String line = "";
        while (!line.equals(Server.exitWord)) {
        		//System.out.println("Open"); //This is just a debugging message to check if the server is running
			try {
					// Store 'in' value
				line = in.readUTF();
				if (!line.contains(Server.exitWord)) {
					System.out.println(line);
				}
				
//				out.writeUTF(line);
				
				
				/*for (ServerThread client : Server.clientList){ 
					client.out.writeUTF(line);
				}*/
					
					
			}
			catch(IOException i) {
					
				System.out.println(i);
			}
       	} 
        
		try {
			System.out.println("has left the server");
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			
			System.out.println(e);
		}
		
    }

}
