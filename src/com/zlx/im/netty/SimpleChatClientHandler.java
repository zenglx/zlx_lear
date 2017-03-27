package com.zlx.im.netty;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.zlx.im.domain.QQBuddyList;
import com.zlx.im.domain.QQMessage;
import com.zlx.im.netty.SimpleChatClientHandler.WaitThread;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

public class SimpleChatClientHandler  extends  ChannelInboundHandlerAdapter{
	public static List<String> msgs = new ArrayList<String>();
	public static QQMessage message = new QQMessage();
	//public static QQBuddyList buddyList = new QQBuddyList();
	Queue<QQMessage> queue = new LinkedList<QQMessage>();
	private WaitThread waitThread;
	private boolean isWaiting = false;
	
	 

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		JSONObject jsonObject = JSON.parseObject((String) msg);
    	if("login_success".equals(jsonObject.get("type"))){
    			System.out.println("登录成功");
    			message.type = "login_success";
    	}else if("buddyList".equals(jsonObject.get("type"))){
    		message.type = "buddyList";
    	}else if("send".equals(jsonObject.get("type"))){
    		message.type = "send";
    	}
    	message.content = jsonObject.getString("content");
    	isWaiting = true;
    	waitThread = new WaitThread();
		waitThread.start();
	}
	
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("数据读取完毕");
	}


	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
	}

	
	public class WaitThread extends Thread {

		@Override
		public void run() {
			super.run();
			while (isWaiting) {
				// 这里接收到消息，根据消息中保存的type字段来处理登录，获取联系人列表，登出等操作，将这一部分操作抽取出来一个接口，类似于按钮的点击事件那样，接收到消息就做操作
				/*
				
				 * 接收到消息之后，依次调用每个监听器的onReceive方法
				 */
				for (OnMessageListener listener : listeners) {
					listener.onReveive(message);
				}
				isWaiting = false;
			}

		}
	}
	

	public static interface OnMessageListener {
		public void onReveive(QQMessage msg);
	}
	
	private static List<OnMessageListener> listeners = new ArrayList<OnMessageListener>();

	public void addOnMessageListener(OnMessageListener listener) {
		listeners.add(listener);
	}

	public void removeOnMessageListener(OnMessageListener listener) {
		listeners.remove(listener);
	}
}
