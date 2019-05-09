package nettyserverv1;

import java.util.ArrayList;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * Created by linguolong on 2019/05/08.
 * Chatroom server built using netty framework
 */

public class SubreqServer {
	
	public static ArrayList<UserInfo> userlist = new ArrayList<UserInfo>();
	static{
		for(int i=0;i<10;i++){
			UserInfo user=new UserInfo();
			user.setUserID("123"+i);
			user.setUserName("user"+i);
			user.setPassword("pwd"+i);
			userlist.add(user);
		}
	}
	
    public void bind(int port) throws Exception{
        //���÷����NIO �߳���
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap server = new ServerBootstrap();

        try {
            server.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            /**
                             * ������: ��������������������: #1 �����������л�������ֽڳ���,����������1M;
                             *                           #2 �������: weakCachingConcurrentResolver�����̰߳�ȫ��WeakReferenceMa������������л���,
                             *                                      ֧�ֶ��̲߳�������,��������ڴ治��ʱ,���ͷŻ����е��ڴ�,��ֹ�ڴ�й©.
                             */
                            ch.pipeline().addLast(new ObjectDecoder(1024*1024,
                                    ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())) );

                            ch.pipeline().addLast(new ObjectEncoder());

                            ch.pipeline().addLast(new SubreqServerHandler());
                        }
                    });

            //�󶨶˿�, ͬ���ȴ��ɹ�
            ChannelFuture future = server.bind(port).sync();

            //�ȴ�����˼����˿ڹر�
            future.channel().closeFuture().sync();
        } finally {
            //���Źر� �߳���
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    /**
     * main ����
     * @param args
     */
    public static void main(String[] args) {
        SubreqServer server = new SubreqServer();
        try {
            server.bind(18888);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}