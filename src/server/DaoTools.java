package server;

import java.util.HashMap;
import java.util.Map;
 
//����һ�������û���¼��Ϣ����
public class DaoTools {
	//�ڴ��û���Ϣ���ݿ�
	private static Map<String,UserInfo>userDB=new HashMap();
	//��̬�飺ģ�������ڴ��е��û����ݣ��û���Ϊ1~10
	//����������ʱ��δ�����Զ�ִ����userDB��������
	static {
		for(int i=1;i<=10;i++) {
			UserInfo user=new UserInfo();
			user.setName("user"+i);
			user.setPassword("psw"+i);
			userDB.put(user.getName(), user);
		}
	}
	
	public static boolean checkLogin(UserInfo user) {
		//��ֻ��֤�û����Ƿ����
		if(userDB.containsKey(user.getName())) {
			return true;
		}
		System.out.println(user.getName()+"�û���֤ʧ�ܣ�");
		return false;
	}
}