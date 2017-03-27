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
                bundle.putString("content",msg.content);  //��Bundle�д������     
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
				// ��õ�ǰ�����Ŀ����Ϣ.�����˺ź��ǳ�
				ContactInfo info = contactInfoList.get(position);
				// ���ܸ��Լ�����
				//if (info.account != imApp.getMyAccount()) {
					Intent intent = new Intent(FriendListActivity.this,
							MainActivity.class);
					// ���˺ź͸���ǩ��������һ��activity
					intent.putExtra("account", info.account+"");
					intent.putExtra("nick", info.nick);
					// TODO �������
					startActivity(intent);
					
				/*} else {
					Toast.makeText(getBaseContext(), "���ܸ��Լ�����", 0).show();
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
