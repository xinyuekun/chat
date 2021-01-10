package chat;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {

	Socket s = null;
	
	public ChatClient() throws UnknownHostException, IOException {
		s = new Socket("127.0.0.1", 8888);
		(new Thread(new ReceiveClient())).start();
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
		//error 先等输入再连接到server
		//下两行不能弄反
		//不行debug
		ChatClient cc = new ChatClient();
		String str = bis.readLine();
		while(str != null && str.length() != 0) {
			cc.send(str);
			str = bis.readLine();
		}
		cc.disConnection();
	}
	
	class ReceiveClient implements Runnable {

		@Override
		public void run() {
			if(s == null) return;
			// TODO Auto-generated method stub
			try {
				DataInputStream dis = new DataInputStream(s.getInputStream());
				String str = dis.readUTF();
				while(str != null && str.length() != 0) {
					System.out.println(str);
					str = dis.readUTF();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
