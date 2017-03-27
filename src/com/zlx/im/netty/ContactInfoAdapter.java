package com.zlx.im.netty;

import java.util.List;

import com.example.videonetty.R;
import com.zlx.im.domain.ContactInfo;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactInfoAdapter extends ArrayAdapter<ContactInfo> {

	/**
	 * 传入上下文与集合
	 * 
	 * @param context
	 * @param objects
	 */
	public ContactInfoAdapter(Context context, List<ContactInfo> objects) {
		super(context, 0, objects);
	}

	class ViewHolder {
		ImageView icon;
		TextView title;
		TextView desc;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ContactInfo info = getItem(position);// 从集合中取得数据
		// listView的优化
		ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(getContext(),
					R.layout.view_item_contact, null);
			holder = new ViewHolder();
			holder.icon = (ImageView) convertView.findViewById(R.id.icon);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.desc = (TextView) convertView.findViewById(R.id.desc);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// 赋值
		if (info.avatar == 0) {
			// 默认头像
			holder.icon.setImageResource(R.drawable.ic_launcher);
		} else {
			holder.icon.setImageResource(info.avatar);
		}
		// 如果是自己登录的账号，则显示自己，否则显示账号
		// 获得保存在application中的自己登录的账号

		holder.title.setText(info.account + "");

		holder.desc.setText(info.nick);
		return convertView;
	}
}
