package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
 
public class ChatServer {
	//���������
	public static void main(String[] args) throws IOException {
		//ʵ����һ����������Ķ���
		ChatServer cs=new ChatServer();
		//���÷�����Ϊָ���˿ڴ���������
		cs.setUpServer(9003);
	}
 
	private void setUpServer(int port) throws IOException {
		// TODO Auto-generated method stub
		ServerSocket server=new ServerSocket(port);
		//��ӡ�����ڴ����ķ������˿ں�
		System.out.println("�����������ɹ�!�˿ںţ�"+port);
		while(true) {
			//�ȴ����ӽ���
			Socket socket=server.accept();
			System.out.println("������һ���ͻ������ӣ�"+socket.getRemoteSocketAddress().toString());
			//����һ���߳�ȥ�����������
			ServerThread st=new ServerThread(socket);
			st.start();
		}
	}
}