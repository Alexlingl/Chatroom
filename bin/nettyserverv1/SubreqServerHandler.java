package nettyserverv1;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;


public class SubreqServerHandler extends SimpleChannelInboundHandler<String>{
	//�½�һ��channelGroup�����ڴ�����ӵ�channel
	public static ChannelGroup online_channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx){
        Channel leave_channel = ctx.channel();
        for (Channel channel : online_channels) {
            if (channel != leave_channel){
                channel.writeAndFlush("[�û� " + leave_channel.remoteAddress() + "]������!\n");
            }
        }
        System.out.println(ctx.channel().id()+"������");
		//�Ѹ����ߵ�channel�Ƴ��������û�����
		online_channels.remove(leave_channel);
	}
	
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    	if(!check_login(ctx)){
    		check_identity(ctx,msg);
    	}else{
            Channel coming_channel = ctx.channel();
            for (Channel channel : online_channels) {
                if (channel != coming_channel){
                    channel.writeAndFlush("[�û� " + coming_channel.remoteAddress() + "]: " + msg );
                } else {
                    channel.writeAndFlush("[��]: " + msg);
                }
            }
    	}
    	
    }

    public void check_identity(ChannelHandlerContext ctx, Object msg){
    	UserInfo req = (UserInfo) msg;
        System.out.println("service receive client login req :{"+ req.toString() +"}");
        boolean login_flag = false;
        for(int i=0;i<SubreqServer.userlist.size();i++){
        	 if( SubreqServer.userlist.get(i).getUserID().equalsIgnoreCase(req.getUserID())&&(SubreqServer.userlist.get(i).getPassword().equals(req.getPassword()))){
                 login_flag=true;
             }
        }
        if(login_flag){
        	System.out.println("�˺�"+req.getUserID()+"��¼�ɹ�");
            ctx.writeAndFlush("���ѵ�¼�ɹ�~\n");
            //����ǰ��ͨ���������߶�����
            online_channels.add(ctx.channel());
        }
        else{
        	System.out.println("�˺�"+req.getUserID()+"��¼ʧ��");
            ctx.writeAndFlush("��¼ʧ��!");
            //�ر�����
        	ctx.close();
        	online_channels.remove(ctx.channel());
        }	
    }
    
    public boolean check_login(ChannelHandlerContext ctx){
    	boolean online_flag = false;
    	for(int i=0;i<online_channels.size();i++){
    		if(online_channels.contains(ctx.channel())){
    			online_flag = true;
    		}
    	}
		return online_flag;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //�ͷ���Դ
        ctx.close();
    }
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		
	}
}