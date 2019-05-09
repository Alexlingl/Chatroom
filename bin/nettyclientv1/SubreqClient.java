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
        //���ÿͻ���NIO �߳���
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap client = new Bootstrap();

        try {
            client.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                        	//��ӽ�����
                            ch.pipeline().addLast(new ObjectDecoder(1024,
                                    ClassResolvers.cacheDisabled(this.getClass().getClassLoader())) );
                            //��ӱ�����
                            ch.pipeline().addLast(new ObjectEncoder());
                            ch.pipeline().addLast(new SubreqClientHandler());
                        }
                    });

            //�첽��ȡ��ǰ�����ӵ�channel
            Channel now_channel = client.connect(host,port).sync().channel();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            
            //�첽�ȴ��ͻ������Ӷ˿ڹر�
//            now_channel.closeFuture().sync();
            
            //�ǿͻ���һֱ��������״̬��ֱ����ȡ��"bye"
            String message = " ";
            while (true) {
            	//����byeʱ�˳�
            	if(message.equals("bye")) break;
            	message = reader.readLine();
                now_channel.writeAndFlush(message+"\n");
            }
            
            //������"bye"�ַ����������Ͽ�����
            now_channel.close();
        } finally {
            //���Źر� �߳���
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