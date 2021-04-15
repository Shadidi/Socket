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
					out.writeUTF(line);
					System.out.println(line);
				}
				
					
			}
			catch(IOException i) {
					
				System.out.println(i);
			}
       	} 
        
		try {
			System.out.println("\t left the server\n");
			in.close();
			socket.close();
			connect = false;
		} catch (IOException e) {
			
			System.out.println(e);
		}
		
    }

}
