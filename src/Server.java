import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class Server {
	private static final int SERVER_PORT =5055;
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
			EventLoopGroup bossGroup = new NioEventLoopGroup(1);
			EventLoopGroup workerGroup = new NioEventLoopGroup();
		try
		{
			ServerBootstrap bootStrap = new ServerBootstrap();
			bootStrap.group(bossGroup,workerGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG,100)
			.handler(new LoggingHandler(LogLevel.DEBUG))
			.childHandler(new 	ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel channel) throws Exception {
					// TODO Auto-generated method stub
					
					ChannelPipeline pipeline = channel.pipeline();
					pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
					//pipeline.addLast("Echo Handler", new EchoPipe());
				}
			})
			.childOption(ChannelOption.SO_KEEPALIVE, true);
			
			ChannelFuture f = bootStrap.bind(SERVER_PORT).sync();
			 f.channel().closeFuture().sync();
		}
		finally
		{
			workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
		}

	}

}
