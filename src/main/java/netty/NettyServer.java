package netty;

import java.net.InetSocketAddress;



import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import netty.handler.ServerHandler;

public class NettyServer {

	
    public void bind(int port)throws Exception{
    	EventLoopGroup connectGroup = new NioEventLoopGroup();//连接处理的轮循组
    	EventLoopGroup workerGroup =new NioEventLoopGroup();//读写处理的轮循组
    	try {
    		System.out.println("开始启动netty server");
			ServerBootstrap b = new ServerBootstrap();//启动类
			b.group(connectGroup,workerGroup)
			    .channel(NioServerSocketChannel.class)
			    .localAddress(new InetSocketAddress(port))
			    .childHandler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new ServerHandler());
						
					}
				});
			System.out.println("启动netty server完毕");
			ChannelFuture sync = b.bind(port).sync();
			if (sync.isSuccess() == true) {
				System.out.println("NettyServer启动成功");
            } else {
				System.out.println("NettyServer启动失败"+sync.cause());
            }
			sync.channel().closeFuture().sync();
			System.out.println("关闭netty server");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//释放资源
			connectGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
			System.out.println("finally里释放netty server");
		}
    }
    
    
/*    ServerBootstrap serverBootstrap = new ServerBootstrap();    //启动NIO服务的辅助启动类
    serverBootstrap.group(parentGroup, childGroup).channel(NioServerSocketChannel.class)  //启动服务时, 通过反射创建一个NioServerSocketChannel对象

            //服务器初始化时执行, 属于AbstracBootstrap的方法
            .handler(new LoggingHandler(LogLevel.INFO))    //handler在初始化时就会执行，可以设置打印日志级别
            .option(ChannelOption.SO_BACKLOG, 1024)      //设置tcp缓冲区, 可连接队列大小
            .option(ChannelOption.SO_REUSEADDR, true)    //允许重复使用本地地址和端口

            //客户端连接成功之后执行, 属于ServerBootstrap的方法,继承自AbstractBootstrap
            .childOption(ChannelOption.SO_KEEPALIVE, true)    //两小时没有数据通信时, 启用心跳保活机制探测客户端是否连接有效
            .childOption(ChannelOption.SO_REUSEADDR, true)
            .childHandler(serverChannelInit);    //childHandler在客户端成功连接后才执行，实例化ChannelInitializer

    ChannelFuture cf = serverBootstrap.bind(port).sync();    //绑定端口, 添加异步阻塞等待服务器启动完成

    if (cf.isSuccess() == true) {
        logger.info("NettyServer启动成功");
    } else {
        logger.error("NettyServer启动失败", cf.cause());
    }

    cf.channel().closeFuture().sync();    //等待服务器套接字关闭
*/ 
}
