package server;

import java.io.IOException;
import java.util.ArrayList;
 
/*
 * ����һ�������࣬�൱��һ���н飬�����̣߳�ת����Ϣ
 * ���ֻ�ṩ�������ã�����Ҫʵ����������˶��Ǿ�̬����
 */
public class ChatTools {
	//�����̴߳���Ķ���
	private static ArrayList<ServerThread> stList=new ArrayList();
	//����Ҫʵ�����࣬��˹�����Ϊ˽��
	private ChatTools() {}
	
	//��һ���ͻ���Ӧ���̴߳��������뵽������
	public static void addClient(ServerThread st) throws IOException {
		stList.add(st);//������̴߳��������뵽������
		castMsg(st.getOwerUser(),"�������ˣ�Ŀǰ������"+stList.size());
	}
	
	//������Ϣ�������û�
	public static void castMsg(UserInfo sender,String msg) throws IOException {
		msg=sender.getName()+"˵��"+msg;//����˵�Ķ���
		for(int i=0;i<stList.size();i++) {
			ServerThread st=stList.get(i);
			st.sendMsg2Me(msg);//����Ϣ��ÿһ���ͻ���
		}
	}
}