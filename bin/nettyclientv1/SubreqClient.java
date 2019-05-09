package nettyclientv1;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * Created by linguolong on 2019/05/08.
 * Chatroom client built using netty framework
 */

public class SubreqClient {

    public void connect(int port, String host) throws Exception{
        //配置客户端NIO 线程组
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap client = new Bootstrap();

        try {
            client.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                        	//添加解码器
                            ch.pipeline().addLast(new ObjectDecoder(1024,
                                    ClassResolvers.cacheDisabled(this.getClass().getClassLoader())) );
                            //添加编码器
                            ch.pipeline().addLast(new ObjectEncoder());
                            ch.pipeline().addLast(new SubreqClientHandler());
                        }
                    });

            //异步获取当前已连接的channel
            Channel now_channel = client.connect(host,port).sync().channel();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            
            //异步等待客户端连接端口关闭
//            now_channel.closeFuture().sync();
            
            //是客户端一直处于输入状态，直到读取到"bye"
            String message = " ";
            while (true) {
            	//读到bye时退出
            	if(message.equals("bye")) break;
            	message = reader.readLine();
                now_channel.writeAndFlush(message+"\n");
            }
            
            //读到了"bye"字符串，主动断开连接
            now_channel.close();
        } finally {
            //优雅关闭 线程组
            group.shutdownGracefully();
        }
    }


    public static void main(String[] args) {
        SubreqClient client = new SubreqClient();
        try {
            client.connect(18888, "127.0.0.1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}