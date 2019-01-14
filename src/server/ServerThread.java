package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
 
/*
 * ÿ���пͻ����ͷ���������ʱ����Ҫ����һ�����ܶ������������ݵĴ���
 * �ӷ������ĽǶȿ����������ǿͻ���
 */
public class ServerThread extends Thread{
	private Socket client;//�߳��еĴ������
	private OutputStream ous;//���������
	private UserInfo user;//�û���Ϣ����
	
	public ServerThread(Socket client) {
		this.client=client;
	}
	
	public UserInfo getOwerUser() {
		return this.user;
	}
	
	public void run() {
		try {
			processSocket();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 
	//����ʾ���д�ӡ��Ϣ������"�û���"��"����"�ȵ�
	public void sendMsg2Me(String msg) throws IOException {
		msg+="\r\n";
		ous.write(msg.getBytes());
		ous.flush();
	}
	
	
	private void processSocket() throws IOException {
		// TODO Auto-generated method stub
		InputStream ins=client.getInputStream();
		ous=client.getOutputStream();
		BufferedReader brd=new BufferedReader(new InputStreamReader(ins));
		
		sendMsg2Me("��ӭ�������죬����������û�����");
		String userName=brd.readLine();
		System.out.println(userName);
		sendMsg2Me("���������룺");
		String pwd=brd.readLine();
		System.out.println(pwd);
		user=new UserInfo();
		user.setName(userName);
		user.setPassword(pwd);
		//�������ݿ⣬��֤�û��Ƿ����
		boolean loginState=DaoTools.checkLogin(user);
		if(!loginState) {
			//�������������˺���ر�
			this.closeMe();
			return;
		}
		ChatTools.addClient(this);//��֤�ɹ���������û��������������
		String input=brd.readLine();
		while(!input.equals("bye")) {
			System.out.println("��������������:"+input);
			ChatTools.castMsg(this.user, input);
			input=brd.readLine();
		}
		ChatTools.castMsg(this.user, "bye");
		this.closeMe();
	}
	
	//�رյ�ǰ�ͻ���������������ӡ�
	public void closeMe() throws IOException {
		client.close();
	}
	
	
}