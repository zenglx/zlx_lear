package com.zlx.im.domain;

import com.zlx.im.util.MyTime;

/**
 * ����Э�鶨�弴ʱͨѶ��ʵ���࣬�����������ݽ���
 * 
 * @author ZHY
 * 
 */
public class QQMessage{
	public String type = MessageType.MSG_TYPE_CHAT_P2P;// ���͵����� chat login
	public int  from = 0;// ������ account
	public String fromNick = "";// �ǳ�
	public int fromAvatar = 1;// ͷ��
	public int to = 0; // ������ account
	public String content = ""; // ��Ϣ������ Լ��?
	public String sendTime = MyTime.geTime(); // ����ʱ��
}
