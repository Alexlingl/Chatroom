package server;

import java.util.HashMap;
import java.util.Map;
 
//����һ�������û���¼��Ϣ����
public class DaoTools {
	//�ڴ��û���Ϣ���ݿ�
	private static Map<String,UserInfo>userDB=new HashMap<String,UserInfo>();
	//��̬�飺ģ�������ڴ��е��û����ݣ��û���Ϊ1~10
	//����������ʱ��δ�����Զ�ִ����userDB��������
	static {
		for(int i=1;i<=10;i++) {
			UserInfo userInfo=new UserInfo();
			userInfo.setName("user"+i);
			userInfo.setPassword("psw"+i);
			userDB.put(userInfo.getName(), userInfo);
		}
	}
	
	public static boolean checkLogin(UserInfo user) {
		if(userDB.containsKey(user.getName())) {
//			System.out.println("userName=" + user.getName() + "  userPassword=" + user.getPassword());
			UserInfo rightUserInfo = userDB.get(user.getName());
//			System.out.println("rightUserInfoName=" + rightUserInfo.getName() + " rightUserPassword=" + rightUserInfo.getPassword());
			String rightUserPassword = rightUserInfo.getPassword();
			if(rightUserPassword.equals(user.getPassword())){
				return true;
			}
		}
		System.out.println(user.getName()+"�û���֤ʧ�ܣ�");
		return false;
	}
}