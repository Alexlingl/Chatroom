package server;

import java.util.HashMap;
import java.util.Map;
 
//定义一个处理用户登录信息的类
public class DaoTools {
	//内存用户信息数据库
	private static Map<String,UserInfo>userDB=new HashMap();
	//静态块：模拟生成内存中的用户数据，用户名为1~10
	//当程序启动时这段代码会自动执行向userDB放入数据
	static {
		for(int i=1;i<=10;i++) {
			UserInfo user=new UserInfo();
			user.setName("user"+i);
			user.setPassword("psw"+i);
			userDB.put(user.getName(), user);
		}
	}
	
	public static boolean checkLogin(UserInfo user) {
		//在只验证用户名是否存在
		if(userDB.containsKey(user.getName())) {
			return true;
		}
		System.out.println(user.getName()+"用户验证失败！");
		return false;
	}
}