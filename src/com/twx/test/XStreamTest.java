package com.twx.test;
import java.util.Date;

import com.thoughtworks.xstream.XStream;
import com.twx.po.TextMessage;

public class XStreamTest {
	public static void main(String[] args) {
		//转换成xml格式文件
		XStream xStream = new XStream();
		TextMessage tm = new TextMessage();
		tm.setContent("您好！");
		tm.setCreateTime(new Date().getTime());
		/*Date.getTime()所返回的是一个long型的毫秒数, 
		若要获取特定格式的时间需要格式化 ,如下所示
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
