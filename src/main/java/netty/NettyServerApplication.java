package netty;

public class NettyServerApplication {
   public static void main(String[] args) {
	     try {
	    	 NettyServer nettyServer = new NettyServer();
			nettyServer.bind(6666);
		} catch (Exception e) {
			e.printStackTrace();
		}
	     
}
}
