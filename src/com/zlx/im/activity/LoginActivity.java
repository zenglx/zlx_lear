package com.zlx.im.activity;

import com.example.videonetty.R;
import com.zlx.im.domain.QQMessage;
import com.zlx.im.domain.MessageType;
import com.zlx.im.netty.SimpleChatClientHandler;
import com.zlx.im.netty.SimpleChatClientHandler.OnMessageListener;
import com.zlx.im.util.TcpClient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity{
	private EditText account;
	private EditText password;
	private String accountStr;// 账号
	private String passwordStr;// 密码
	private Button login;
	private TcpClient client;
	private ImApp imApp;
	private Toast toast;
	public SimpleChatClientHandler simpleChatClientHandler = new SimpleChatClientHandler();
	private OnMessageListener listener = new OnMessageListener() {
		
		@Override
		public void onReveive(QQMessage msg) {
			if("login_success".equals(msg.type)){
				Message m = new Message();
				m.what = 0;
				Bundle bundle = new Bundle();      
                bundle.putString("content",msg.content);  //往Bundle中存放数据     
                m.setData(bundle);
				loginHandler.sendMessage(m);
			}
			
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		account = (EditText) findViewById(R.id.account);
		password = (EditText) findViewById(R.id.password);
		login = (Button) findViewById(R.id.login);
		client = new TcpClient();
		client.start();
		imApp = (ImApp)getApplicationContext();
		imApp.setMyConn(client);
		imApp.setMyAccount(101);
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				accountStr = account.getText().toString();
				passwordStr = password.getText().toString();
							QQMessage message = new QQMessage();
							message.type = MessageType.MSG_TYPE_LOGIN;
							message.content = accountStr + "#" + passwordStr;
							client.sendMsg(message);
							simpleChatClientHandler.addOnMessageListener(listener);
						//	
			}
			
		});
	}
	Handler loginHandler = new Handler(){
		 public void handleMessage(android.os.Message msg) {
			 if(msg.what==0){
				 toast = Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_LONG);
				 toast.show();
				 simpleChatClientHandler.removeOnMessageListener(listener);
				 Intent intent = new Intent(LoginActivity.this,FriendListActivity.class);
				 intent.putExtra("content", msg.getData().getString("content"));
				 startActivity(intent);
				 finish();
			 }
		 }
	};
	protected void onDestroy() {
		super.onDestroy();
	};
}