package nettyclientv1;

import nettyserverv1.UserInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by linguolong on 2019/05/08.
 * Chatroom client built using netty framework
 */

public class SubreqClientHandler extends ChannelInboundHandlerAdapter{

    public SubreqClientHandler() {
    }

    /**
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	ctx.writeAndFlush(subReq("1231","user1","pwd1"));
        ctx.flush();
    }

	private UserInfo subReq(String id,String userName,String password){
        UserInfo req = new UserInfo();
        req.setUserID(id);
        req.setUserName(userName);
        req.setPassword(password);

        return req;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        LogUtil.log_debug("client -> read");

    	System.out.print(msg.toString());
//        LogUtil.log_debug("receive server response: { " + msg.toString() +"]");

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}