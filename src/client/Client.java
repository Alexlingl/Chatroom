package client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread{
    InputStream input=null;
    OutputStream output=null;
    BufferedReader bufferinput=null;
    Socket socket=null;

    public Client(){
        //��ʼ��ʱ���ӷ�����
        try {
            socket=new Socket("127.0.0.1",9003);
            input=socket.getInputStream();
            output=socket.getOutputStream();
            //��֤�û���Ϣ
            login();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Client cl=new Client();
    }

    public void login(){
        try {
            int Login=0;
            bufferinput=new BufferedReader(new InputStreamReader(input));
            String line;
            while(Login<2){
                //��ȡ��Ϣ
                if((line=bufferinput.readLine())!=null){
                    System.out.println(line);
                }

                //������Ϣ
                BufferedReader brName = new BufferedReader(new InputStreamReader(System.in));
                String strName;
                //����̨�ڶ�������ʱ�������Զ���ӻ��з���
                strName = brName.readLine()+"\r\n";
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
        start();//����һ���̶߳�ȡ��ǰ�û�������
        bufferinput=new BufferedReader(new InputStreamReader(input));
        String line;
        //��ȡ��Ϣ
        try {
            while(true){
                if((line=bufferinput.readLine())!=null){
                    System.out.println(line);
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
        while(true){                //������Ϣ
            BufferedReader brName = new BufferedReader(new InputStreamReader(System.in));
            String strName;
            //����̨�ڶ�������ʱ�������Զ���ӻ��з���
            try {
                strName = brName.readLine()+"\r\n";
                output.write(strName.getBytes());
                output.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
