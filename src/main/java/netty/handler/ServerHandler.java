package netty.handler;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class ServerHandler extends ChannelInboundHandlerAdapter{
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		super.channelRegistered(ctx);
		System.out.println("=====================>channelRegistered");
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		super.channelUnregistered(ctx);
		System.out.println("=====================>channelUnregistered");
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		System.out.println("=====================>channelActive");
		ctx.write("服务器已经收到连接。。。+abc + 123");
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);//
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		System.out.println("=====================>channelInactive");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)throws Exception {
		super.channelRead(ctx, msg);
		System.out.println("=====================>channelRead:" + msg);
		ByteBuf in = (ByteBuf) msg;
	    System.out.println("Server received: " + in.toString(CharsetUtil.UTF_8));        //2
	    ctx.write("中文加上abc+123");  
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		super.channelReadComplete(ctx);
		System.out.println("=====================>channelReadComplete:" + ctx);
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)//4  会出现一条信息分批读完成吗？这样如果处理完整的一条信息
        .addListener(ChannelFutureListener.CLOSE);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		//java.io.IOException: 远程主机强迫关闭了一个现有的连接。
		super.exceptionCaught(ctx, cause);
		System.out.println("=====================>exceptionCaught:" + cause.getMessage());
		Channel channel = ctx.channel();
		//出现异常时，要主动关闭TODO并做业务逻辑处理
		if(channel.isActive()){
			ctx.close();
		}
	}

}
