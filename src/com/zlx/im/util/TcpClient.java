package com.zlx.im.util;

import com.alibaba.fastjson.JSON;
import com.zlx.im.domain.QQMessage;
import com.zlx.im.netty.SimpleChatClientInitializer;

import io.netty.bootstrap.Bootstrap;  
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;  
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;  
  

public class TcpClient extends Thread {
   
    public static String HOST = "192.168.1.139";
    public static int PORT = 9999;  
    public Channel channel;
    public void run() {  
    	super.run();
    	EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap  = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new SimpleChatClientInitializer());
            channel = bootstrap.connect(HOST, PORT).sync().channel();
            while(true){
                Thread.currentThread();
				Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }  
    public void sendMsg(QQMessage msg){
    	String strJson = JSON.toJSONString(msg);
    	channel.writeAndFlush(strJson);
    }
    
}  