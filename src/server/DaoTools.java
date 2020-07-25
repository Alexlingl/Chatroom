package server;

import java.util.HashMap;
import java.util.Map;
 
//定义一个处理用户登录信息的类
public class DaoTools {
	//内存用户信息数据库
	private static Map<String,UserInfo>userDB=new HashMap<String,UserInfo>();
	//静态块：模拟生成内存中的用户数据，用户名为1~10
	//当程序启动时这段代码会自动执行向userDB放入数据
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
		System.out.println(user.getName()+"用户验证失败！");
		return false;
	}
}