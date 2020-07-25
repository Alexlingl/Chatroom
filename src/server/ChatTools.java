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
	public static void addClient(ServerThread st) throws IOException {
		serverThreadList.add(st);//������̴߳��������뵽������
		castMsg(st.getOwerUser(),"�������ˣ�Ŀǰ������"+serverThreadList.size());
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