package com.twx.test;
import java.util.Date;

import com.thoughtworks.xstream.XStream;
import com.twx.po.TextMessage;

public class XStreamTest {
	public static void main(String[] args) {
		//ת����xml��ʽ�ļ�
		XStream xStream = new XStream();
		TextMessage tm = new TextMessage();
		tm.setContent("���ã�");
		tm.setCreateTime(new Date().getTime());
		/*Date.getTime()�����ص���һ��long�͵ĺ�����, 
		��Ҫ��ȡ�ض���ʽ��ʱ����Ҫ��ʽ�� ,������ʾ
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		sdf.format(new Date());*/
		tm.setMsgType("text");
		tm.setFromUserName("gong");
		tm.setMsgId("fsfs24sfd");
		
		xStream.alias("xml", tm.getClass());
		String result = xStream.toXML(tm);
		System.out.println(result);
	}
}
