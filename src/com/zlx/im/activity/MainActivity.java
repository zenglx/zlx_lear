package com.zlx.im.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.videonetty.R;
import com.zlx.im.domain.MessageType;
import com.zlx.im.domain.Msg;
import com.zlx.im.domain.QQMessage;
import com.zlx.im.netty.MsgAdapter;
import com.zlx.im.netty.SimpleChatClientHandler;
import com.zlx.im.netty.SimpleChatClientHandler.OnMessageListener;
import com.zlx.im.util.TcpClient;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity{
	private ListView msgListView;
	private EditText inputText;
	private Button send;
	private MsgAdapter adapter;
	private List<Msg> msgs = new ArrayList<Msg>();
	private List<String> msgsContent = new ArrayList<String>();
	private TextView title;
	private ImApp imApp;
	private TcpClient client;
	private String fromNick;
	public SimpleChatClientHandler simpleChatClientHandler = new SimpleChatClientHandler();
	private static List<QQMessage> messageList= new ArrayList<QQMessage>();
	/**
	 * 接收消息，使用监听器
	 */
	private OnMessageListener listener = new OnMessageListener() {
		@Override
		public void onReveive(QQMessage msg) {
			messageList.add(msg);
			Bundle bundle = new Bundle();      
            bundle.putString("content",msg.content);  //往Bundle中存放数据     
            bundle.putString("from",msg.type);
			Message m = new Message();
			m.what = 0;
			 m.setData(bundle);
			mTimeHandler.sendMessage(m);
			
		}

		
	};

		

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		Intent intent = getIntent();
		final String titleStr = intent.getStringExtra("account");
		fromNick = intent.getStringExtra("nick");
		title = (TextView) findViewById(R.id.account);
		title.setText(titleStr);
		imApp = (ImApp) getApplicationContext();
		client = imApp.getMyConn();
		initMsgs();
		simpleChatClientHandler.addOnMessageListener(listener);
		msgListView = (ListView) findViewById(R.id.msg_list_view);
		adapter = new MsgAdapter(MainActivity.this, R.layout.msg_item,messageList);
		send = (Button) findViewById(R.id.btnSend);
		inputText = (EditText) findViewById(R.id.input_text);
		msgListView.setAdapter(adapter);
		send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String content = inputText.getText().toString();
				if(!"".equals(content)){
					Msg msg = new Msg(content, Msg.TYPE_SENT);
					QQMessage message = new QQMessage();
					message.to = Integer.parseInt(titleStr);
					message.content = content;
					message.fromNick = fromNick;
					message.type = MessageType.MSG_TYPE_CHAT_P2P;
					client.sendMsg(message);
					inputText.setText(""); // 清空输入框中的内容
				}
			}
		});
	}
	Handler mTimeHandler = new Handler() {	 
        public void handleMessage(android.os.Message msg) { 	  
            if (msg.what == 0){
        		 adapter.notifyDataSetChanged();
    	         msgListView.setSelection(msgs.size());	
            }
        }
    };
	private void initMsgs() {
		QQMessage msg1 = new QQMessage();
		msg1.content = "hello zlx";
		msg1.type = "receive";
		messageList.add(msg1);
		}
	public  void updateUI1(String str) {
		String a = str;	
		System.out.println(a);
		Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
	}
}
