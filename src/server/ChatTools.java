package server;

import java.io.IOException;
import java.util.ArrayList;
 
/*
 * ����һ�������࣬�൱��һ���н飬�����̣߳�ת����Ϣ
 * ���ֻ�ṩ�������ã�����Ҫʵ����������˶��Ǿ�̬����
 */
public class ChatTools {
	//�����̴߳���Ķ���
	private static ArrayList<ServerThread> serverThreadList=new ArrayList<ServerThread>();
	//����Ҫʵ�����࣬��˹�����Ϊ˽��
	private ChatTools() {}
	
	//��һ���ͻ���Ӧ���̴߳��������뵽������
	public static void addClient(ServerThread serverThread) throws IOException {
		serverThreadList.add(serverThread);//������̴߳��������뵽������
		castMsg(serverThread.getOwerUser(),"�������ˣ�Ŀǰ������"+serverThreadList.size());
	}
	
	public static void removeClient(ServerThread serverThread) throws IOException{
		castMsg(serverThread.getOwerUser(),"�������ˣ�Ŀǰ������"+serverThreadList.size());
		serverThreadList.remove(serverThread);
	}
	
	//������Ϣ�������û�
	public static void castMsg(UserInfo sender,String msg) throws IOException {
		msg=sender.getName()+"˵��"+msg;//����˵�Ķ���
		for(int i=0;i<serverThreadList.size();i++) {
			ServerThread serverThread = serverThreadList.get(i);
			serverThread.sendMsg2Me(msg);//����Ϣ��ÿһ���ͻ���
		}
	}
}