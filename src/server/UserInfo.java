package server;
//定义一个用户信息的类
public class UserInfo {
	private String name;//用户名
	private String password;//密码
	private String loignTime;//登录时间
	private String address;//客户机端口名
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		// TODO Auto-generated method stub
		this.name=name;
	}
	
	public void setPassword(String psw) {
		this.password=password;
	}
}
