package server;
//����һ���û���Ϣ����
public class UserInfo {
	private String name;//�û���
	private String password;//����
	private String loignTime;//��¼ʱ��
	private String address;//�ͻ����˿���
	
	public void setName(String name) {
		// TODO Auto-generated method stub
		this.name=name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setPassword(String password) {
		this.password=password;
	}
	
	public String getPassword(){
		return password;
	}
}
