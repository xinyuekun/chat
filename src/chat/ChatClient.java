package chat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {

	Socket s = null;
	
	public ChatClient() throws UnknownHostException, IOException {
		s = new Socket("127.0.0.1", 8888);
				
	}
	
	void send(String str) throws IOException {
		DataOutputStream dos = null;
		dos = new DataOutputStream(s.getOutputStream());
		dos.writeUTF(str);
//			dos.close();
		
	}
	
	void disConnection() throws IOException {
		s.close();
		
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader bis = new BufferedReader(new InputStreamReader(System.in)); 
		String str = bis.readLine();
		ChatClient cc = new ChatClient();
		while(str != null && str.length() != 0) {
			cc.send(str);
			str = bis.readLine();
		}
		cc.disConnection();
	}
}
