package nettyserverv1;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;


public class SubreqServerHandler extends SimpleChannelInboundHandler<String>{
	//新建一个channelGroup，用于存放连接的channel
	public static ChannelGroup online_channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx){
        Channel leave_channel = ctx.channel();
        for (Channel channel : online_channels) {
            if (channel != leave_channel){
                channel.writeAndFlush("[用户 " + leave_channel.remoteAddress() + "]下线了!\n");
            }
        }
        System.out.println(ctx.channel().id()+"下线了");
		//把刚下线的channel移除出在线用户队列
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
                    channel.writeAndFlush("[用户 " + coming_channel.remoteAddress() + "]: " + msg );
                } else {
                    channel.writeAndFlush("[我]: " + msg);
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
        	System.out.println("账号"+req.getUserID()+"登录成功");
            ctx.writeAndFlush("您已登录成功~\n");
            //将当前的通道加入在线队列中
            online_channels.add(ctx.channel());
        }
        else{
        	System.out.println("账号"+req.getUserID()+"登录失败");
            ctx.writeAndFlush("登录失败!");
            //关闭连接
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
        //释放资源
        ctx.close();
    }
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		
	}
}