package com.zlx.im.netty;

import java.util.ArrayList;
import java.util.List;

import com.example.videonetty.R;
import com.zlx.im.domain.MessageType;
import com.zlx.im.domain.Msg;
import com.zlx.im.domain.QQMessage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MsgAdapter extends ArrayAdapter<QQMessage> {
	private int resourceId;
	private List<QQMessage> messageList= new ArrayList<QQMessage>();
	public MsgAdapter(Context context,int textViewResourceId, List<QQMessage> objects) {
		super(context,textViewResourceId,objects);
		resourceId = textViewResourceId;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		QQMessage msg = getItem(position);
		View view;
		ViewHolder viewHolder;
		if(convertView==null){
			  
			view = LayoutInflater.from(getContext()).inflate(resourceId,null);
			viewHolder = new ViewHolder();
			viewHolder.leftLayout = (LinearLayout) view.findViewById(R.id.left_layout);
			viewHolder.rightLayout = (LinearLayout) view.findViewById(R.id.right_layout);
			viewHolder.leftMsg = (TextView) view.findViewById(R.id.left_msg);
			viewHolder.rightMsg = (TextView) view.findViewById(R.id.right_msg);
			view.setTag(viewHolder);
		}else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
			}
		if(msg.type.equals(MessageType.MSG_TYPE_RECEIVE)){
			viewHolder.leftLayout.setVisibility(View.VISIBLE);
			viewHolder.rightLayout.setVisibility(View.GONE);
			viewHolder.leftMsg.setText(msg.content);
		} else if(msg.type.equals(MessageType.MSG_TYPE_SEND)) {
			// 如果是发出的消息，则显示右边的消息布局，将左边的消息布局隐藏
			viewHolder.rightLayout.setVisibility(View.VISIBLE);
			viewHolder.leftLayout.setVisibility(View.GONE);
			viewHolder.rightMsg.setText(msg.content);
		}
		return view;
	}
	class ViewHolder {
		LinearLayout leftLayout;
		LinearLayout rightLayout;
		TextView leftMsg;
		TextView rightMsg;
		}
}
