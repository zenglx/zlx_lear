package com.zlx.im.activity;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.videonetty.R;
import com.zlx.im.domain.ContactInfo;
import com.zlx.im.domain.MessageType;
import com.zlx.im.domain.QQMessage;
import com.zlx.im.netty.ContactInfoAdapter;
import com.zlx.im.netty.SimpleChatClientHandler;
import com.zlx.im.netty.SimpleChatClientHandler.OnMessageListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class FriendListActivity extends Activity{
	private ImApp imApp;
	private ListView listView;
	private SimpleChatClientHandler simpleChatClientHandler = new SimpleChatClientHandler();
	private ContactInfoAdapter adapter;
	private List<ContactInfo> contactInfoList;
	private OnMessageListener listener = new OnMessageListener() {
		@Override
		public void onReveive(QQMessage msg) {
				Message m = new Message();
				m.what = 0;
				Bundle bundle = new Bundle();      
                bundle.putString("content",msg.content);  //往Bundle中存放数据     
                m.setData(bundle);
                buddyListHandler.sendMessage(m);
			
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_friendlist);
		listView = (ListView) findViewById(R.id.listview);
		Intent intent = getIntent();
		String content = intent.getStringExtra("content");
		QQMessage msg = new QQMessage();
		msg.content = content;
		msg.type = MessageType.MSG_TYPE_BUDDY_LIST;
		imApp = (ImApp)getApplicationContext();
		imApp.getMyConn().sendMsg(msg);;
		//Toast.makeText(getApplicationContext(),content, Toast.LENGTH_LONG).show();;
		simpleChatClientHandler.addOnMessageListener(listener);
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				simpleChatClientHandler.removeOnMessageListener(listener);
				// 获得当前点击条目的信息.包含账号和昵称
				ContactInfo info = contactInfoList.get(position);
				// 不能跟自己聊天
				//if (info.account != imApp.getMyAccount()) {
					Intent intent = new Intent(FriendListActivity.this,
							MainActivity.class);
					// 将账号和个性签名带到下一个activity
					intent.putExtra("account", info.account+"");
					intent.putExtra("nick", info.nick);
					// TODO 聊天界面
					startActivity(intent);
					
				/*} else {
					Toast.makeText(getBaseContext(), "不能跟自己聊天", 0).show();
				}*/

			}
		});
	}
	
	Handler buddyListHandler = new Handler(){
		 public void handleMessage(android.os.Message msg) {
			 contactInfoList = new ArrayList<ContactInfo>();
			 if(msg.what==0){
				 String messageStr =  msg.getData().getString("content");
				 JSONArray messageList = JSON.parseArray(messageStr);
				 JSONObject message = null;
				 for(int i=0;i<messageList.size();i++){
					 message = messageList.getJSONObject(i);
					 ContactInfo contactInfo = new ContactInfo();
					 contactInfo.avatar = (Integer) message.get("avatar");
					 contactInfo.nick = (String) message.get("nick");
					 contactInfo.account =(Integer) message.get("account");			
					 contactInfoList.add(contactInfo);
				 }
				 adapter = new ContactInfoAdapter(FriendListActivity.this,contactInfoList);
				 listView.setAdapter(adapter);
				 adapter.notifyDataSetChanged();
				// Toast.makeText(getApplicationContext(), messageStr, Toast.LENGTH_LONG).show();
				 //finish();
			 }
		 }
	};
}
