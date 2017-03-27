package com.zlx.im.netty;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

public class SimpleChatClientInitializer  extends ChannelInitializer<SocketChannel>{
	public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        
        //pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast(new IdleStateHandler(0, 0, 10,TimeUnit.SECONDS));
        pipeline.addLast(new StringEncoder(Charset.forName("GBK")));
        pipeline.addLast(new StringDecoder(Charset.forName("UTF-8")));
        pipeline.addLast(new SimpleChatClientHandler());
        
    }
}
