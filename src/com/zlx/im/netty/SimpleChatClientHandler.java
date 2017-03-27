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
    			System.out.println("��¼�ɹ�");
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
		System.out.println("���ݶ�ȡ���");
	}


	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
	}

	
	public class WaitThread extends Thread {

		@Override
		public void run() {
			super.run();
			while (isWaiting) {
				// ������յ���Ϣ��������Ϣ�б����type�ֶ��������¼����ȡ��ϵ���б��ǳ��Ȳ���������һ���ֲ�����ȡ����һ���ӿڣ������ڰ�ť�ĵ���¼����������յ���Ϣ��������
				/*
				
				 * ���յ���Ϣ֮�����ε���ÿ����������onReceive����
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
