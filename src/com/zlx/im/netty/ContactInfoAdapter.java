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
	 * �����������뼯��
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
		ContactInfo info = getItem(position);// �Ӽ�����ȡ������
		// listView���Ż�
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

		// ��ֵ
		if (info.avatar == 0) {
			// Ĭ��ͷ��
			holder.icon.setImageResource(R.drawable.ic_launcher);
		} else {
			holder.icon.setImageResource(info.avatar);
		}
		// ������Լ���¼���˺ţ�����ʾ�Լ���������ʾ�˺�
		// ��ñ�����application�е��Լ���¼���˺�

		holder.title.setText(info.account + "");

		holder.desc.setText(info.nick);
		return convertView;
	}
}
