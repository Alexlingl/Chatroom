package server;

import java.io.IOException;
import java.util.ArrayList;
 
/*
 * 定义一个管理类，相当于一个中介，处理线程，转发消息
 * 这个只提供方法调用，不需要实例化对象，因此都是静态方法
 */
public class ChatTools {
	//保存线程处理的对象
	private static ArrayList<ServerThread> stList=new ArrayList();
	//不需要实例化类，因此构造器为私有
	private ChatTools() {}
	
	//将一个客户对应的线程处理对象加入到队列中
	public static void addClient(ServerThread st) throws IOException {
		stList.add(st);//将这个线程处理对象加入到队列中
		castMsg(st.getOwerUser(),"我上线了！目前人数："+stList.size());
	}
	
	//发送消息给其他用户
	public static void castMsg(UserInfo sender,String msg) throws IOException {
		msg=sender.getName()+"说："+msg;//加上说的对象
		for(int i=0;i<stList.size();i++) {
			ServerThread st=stList.get(i);
			st.sendMsg2Me(msg);//发消息给每一个客户机
		}
	}
}