import java.io.*;
import java.util.*;
import java.net.*;

public class ServerThread implements Runnable{
	private Socket socket = null;
	private DataInputStream in = null;
	private DataOutputStream out = null;
	private int clientNumber = 0;
	public String name = "";
	public boolean connect = true;
	
	public ServerThread(Socket s, int num, DataInputStream i, DataOutputStream o, String n) {
		socket = s;
		clientNumber = num;
		in = i;
		out = o;	
		name = n;
		try {
			out.writeUTF("<<" + name + " HAS JOINED THE CHAT>>"); 
		}
		catch(IOException e) {
			System.out.println(name + " was unable to join. Please try again.\n");
		}
	}
	
	public boolean getConnect() {
		return connect;
	}
	
    public void run() {
    	
        String line = "";
        while (!line.equals(Server.exitWord)) {
			try {
				line = in.readUTF();
				if (!line.contains(Server.exitWord)) {
					out.writeUTF("[" + name + "]");
					System.out.println("[" + name + "]");
					
					out.writeUTF(line);
					System.out.println(line + "\n");
				}
			}
			catch(IOException i) {
				System.out.println(i);
			}
       	} 
        
		try {
			System.out.println("\t" + name + " left the server\n");
			in.close();
			socket.close();
			connect = false;
		} catch (IOException e) {
			
			System.out.println(e);
		}
		
    }

}