package chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class ChatServer {

	ServerSocket server = null;
	Collection cClients = null;
	
	public ChatServer(int port) throws IOException {
		
		server = new ServerSocket(port);
		cClients = new ArrayList<>();
		
	}
	
	public void startServer() throws IOException {
		while(true) {
			
			Socket s = server.accept();
			cClients.add(new Client(s));
		
		}
	}
	
	class Client implements Runnable {
		
		Socket s = null;
		
		public Client(Socket s) {
			this.s = s;
			(new Thread(this)).start();
		}

		public void send(String str) throws IOException {
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			dos.writeUTF(str);
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				DataInputStream dr = 
						new DataInputStream(s.getInputStream());
				String str = dr.readUTF();
				while((str != null && str.length() != 0)) {
					System.out.println(str);
					for(Iterator<Client> iter = cClients.iterator(); iter.hasNext();) {
						Client c = iter.next();
						if(c != this) {
							System.out.println("run");
							c.send(str);
						}
					}
					str = dr.readUTF();
				}
				dr.close();
				s.close();
				cClients.remove(this);
			} catch (IOException e) {
				if(s != null) {
					try {
						s.close();
						cClients.remove(this);
					} catch (IOException eio) {
						// TODO Auto-generated catch block
						eio.printStackTrace();
					}
					
				}
				
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	public static void main(String[] args) throws IOException {
		ChatServer server = new ChatServer(8888);
		server.startServer();
	}
}
