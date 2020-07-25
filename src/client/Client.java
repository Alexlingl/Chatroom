package client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread{
    private InputStream input;
    private OutputStream output;
    private BufferedReader bufferinput;
    private Socket socket;
    private boolean stop = false;

    public Client(){
        //初始化时连接服务器
        try {
            socket=new Socket("127.0.0.1",9003);
            input=socket.getInputStream();
            output=socket.getOutputStream();
            //验证用户信息
            login();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Client client=new Client();
    }

    public void login(){
        try {
            int Login=0;
            bufferinput=new BufferedReader(new InputStreamReader(input));
            String line;
            while(Login<2){
                //获取消息
                if((line=bufferinput.readLine())!=null){
                    System.out.println(line);
                }

                //发送消息
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                String strName;
                //控制台在读入数据时并不会自动添加换行符号
                strName = bufferedReader.readLine()+"\r\n";
                output.write(strName.getBytes());
                output.flush();
                Login++;
            }
            chat();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if(socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public void chat() {
        start();//开启一个线程读取当前用户的输入
        bufferinput=new BufferedReader(new InputStreamReader(input));
        String line;
        //获取消息
        try {
            while(true){
            	line=bufferinput.readLine();
                if(line!=null){
                    System.out.println(line);
                	//判断当前信息是否是服务端关闭连接的信息
                	if((line.equals("您输入的账号密码有误！"))||(line.equals("您已下线！"))){
//                		System.out.println("Enter");
                		this.stop = true;
                		socket.close();
                		//如果是，则停止程序
                		System.exit(0);
                		return;
                	}
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public void run() {
        while(true){   
        	//判断当前服务端是否已关闭客户端的Socket连接
//        	if(this.stop==true){
//        		//如果是，则停止程序
//        		break;
//        	}
        	//发送消息
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String strName;
            //控制台在读入数据时并不会自动添加换行符号
            try {
                strName = bufferedReader.readLine()+"\r\n";
                output.write(strName.getBytes());
                output.flush();
            } catch (IOException e) {
//                e.printStackTrace();
            }
        }
    }
}
