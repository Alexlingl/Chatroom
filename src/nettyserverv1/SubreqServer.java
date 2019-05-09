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
        //配置服务端NIO 线程组
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
                             * 解码器: 构造器传入了两个参数: #1 单个对象序列化后最大字节长度,这是设置是1M;
                             *                           #2 类解析器: weakCachingConcurrentResolver创建线程安全的WeakReferenceMa对类加载器进行缓存,
                             *                                      支持多线程并发访问,当虚拟机内存不足时,会释放缓存中的内存,防止内存泄漏.
                             */
                            ch.pipeline().addLast(new ObjectDecoder(1024*1024,
                                    ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())) );

                            ch.pipeline().addLast(new ObjectEncoder());

                            ch.pipeline().addLast(new SubreqServerHandler());
                        }
                    });

            //绑定端口, 同步等待成功
            ChannelFuture future = server.bind(port).sync();

            //等待服务端监听端口关闭
            future.channel().closeFuture().sync();
        } finally {
            //优雅关闭 线程组
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    /**
     * main 函数
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