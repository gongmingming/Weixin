package com.twx.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.twx.po.AccessToken;
import com.twx.po.Image;
import com.twx.po.ImageMessage;
import com.twx.po.Music;
import com.twx.po.MusicMessage;
import com.twx.po.News;
import com.twx.po.NewsMessage;
import com.twx.po.TextMessage;
import com.twx.test.WeixinTest;

public class MessageUtil {
	public static final String MSGTYPE_TEXT = "text";
	public static final String MSGTYPE_NEWS = "news";
	public static final String MSGTYPE_IMAGE = "image";
	public static final String MSGTYPE_VOICE = "voice";
	public static final String MSGTYPE_MUSIC = "music";
	public static final String MSGTYPE_LOCATION = "location";
	public static final String MSGTYPE_LINK = "link";
	public static final String MSGTYPE_EVENT = "event";
	public static final String EVENT_SUBSCRIBE = "subscribe";
	public static final String EVENT_SCAN = "SCAN";
	public static final String EVENT_LOCATION = "location_select";
	public static final String EVENT_CLICK = "CLICK";
	public static final String EVENT_VIEW = "VIEW";
	public static final String EVENT_SCANCODE_PUSH = "scancode_push";
	
	/**
	 * xml转map
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> xmlToMap(HttpServletRequest request ) throws IOException, DocumentException{
		SAXReader reader = new SAXReader();
		Map<String, String> map = new HashMap<String,String>();
		
		InputStream input = request.getInputStream();
		Document doc = reader.read(input);
		
		List<Element> list = new ArrayList<>();
		
		Element root = doc.getRootElement();
		
		list = root.elements();
		
		for(Element e: list){
			map.put(e.getName(), e.getText());
		}
		input.close();
		return map;
	}
	
	/**
	 * 组装文本消息
	 * @param toUser
	 * @param fromUser
	 * @param content
	 * @return
	 */
	public static String initText(String toUser,String fromUser,String content){
		TextMessage tm = new TextMessage();
		tm.setFromUserName(toUser);
		tm.setToUserName(fromUser);
		tm.setMsgType(MSGTYPE_TEXT);
		tm.setCreateTime(new Date().getTime());
		tm.setContent(content);
		return MessageUtil.textMessageToXml(tm);
	}
	
	/**
	 * TextMessage转xml
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static String textMessageToXml(TextMessage text){
		XStream stream = new XStream();
		stream.alias("xml", text.getClass());//将序列化中的类全量名称，用别名xml替换。
		return stream.toXML(text);
	}
	
	//被关注时弹出的消息字符串
	public static String mainMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎关注我的个人微信公众号，在这里你可以给我留言或者给我提出建议\n")
		.append("1、获取我的个人信息\n")
		.append("2、获取我的shadowsocks账号密码");
		return sb.toString();
	}
	
	//用户输入数字1、2时弹出的消息字符串
	public static String keyWord(String word){		
		StringBuffer sb = new StringBuffer();
		if("1".equals(word)){
			sb.append("QQ: 1120497187\n")
			.append("微博: 该隐的星球\n")
			.append("email: 1120497187@qq.com");
		}else if("2".equals(word)){
			sb.append("服务器:108.61.183.107\n")
			.append("密码：nexusqaz\n")
			.append("端口号：4000\n")
			.append("加密方式：aes-256-cfb");
		}else {
			sb.append("输入的指令无效");
		}
		return sb.toString();
	}
	
	/**
	 * 组装图文消息
	 * @param toUser
	 * @param fromUser
	 * @return
	 */
	public static String initNewsMessage(String toUser,String fromUser){
		NewsMessage newsMessage = new NewsMessage();//图文消息		
		List<News> newsList = new ArrayList<>();//图文消息内容
	
		News news = new News();
		news.setTitle("牛客网");
		news.setDescription("牛客网是一个专注于程序员的学习和成长的专业平台，集笔面试系统、课程教育、社群交流、招聘内推于一体。");
		news.setPicUrl("http://mytest.tunnel.qydev.com/Weixin/image/niuke.png");
		news.setUrl("http://www.nowcoder.com/");	
		newsList.add(news);		
		News news1 = new News();
		news1.setTitle("慕课网");
		news1.setDescription("慕课网(IMOOC)是国内最大的IT技能学习平台，提供了丰富的移动端开发、php开发、web前端、android开发以及html5等视频教程资源公开课。");
		news1.setPicUrl("http://mytest.tunnel.qydev.com/Weixin/image/imooc.jpg");
		news1.setUrl("http://www.imooc.com/");	
		newsList.add(news1);
		
		newsMessage.setToUserName(fromUser);
		newsMessage.setFromUserName(toUser);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MSGTYPE_NEWS);
		newsMessage.setArticleCount(newsList.size());
		newsMessage.setArticles(newsList);
		
		return newsMessageToXml(newsMessage);
	}
	 // 图文消息转换xml
	public static String newsMessageToXml(NewsMessage nm){
		XStream stream = new XStream();
		stream.alias("xml", nm.getClass());
		stream.alias("item", new News().getClass());
		return stream.toXML(nm);
	}
	
	/**
	 * 返回图片消息
	 * @param toUser
	 * @param fromUser
	 * @return
	 * @throws Exception 
	 */
	public static String initImageMessage(String toUserName,String fromUserName) throws Exception{
		String message = null;
		Image image = new Image();
		
		AccessToken token = WeixinUtil.getAccessToken();
		String filePath = "E:/weixin/imooc.jpg";
		String imageMediaId = WeixinUtil.upload(filePath, token.getToken(), "image");
		//image.setMediaId("H9Wrg9ebT_RhfbSIXFi9dYRjNTf6x21e0Sn2Q-DmmMPL2vno3QAQeCWQb3EzmvbR");
		image.setMediaId(imageMediaId);
		
		ImageMessage imageMessage = new ImageMessage();
		imageMessage.setFromUserName(toUserName);
		imageMessage.setToUserName(fromUserName);
		imageMessage.setMsgType(MSGTYPE_IMAGE);
		imageMessage.setCreateTime(new Date().getTime());
		imageMessage.setImage(image);
		
		message = imageMessageToXml(imageMessage);
		return message;
	}
	public static String imageMessageToXml(ImageMessage imageMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}
	
	/**
	 * 返回音乐
	 * @param toUser
	 * @param fromUser
	 * @return
	 * @throws Exception 
	 */
	public static String initMusicMessage(String toUserName,String fromUserName) throws Exception{
		String message = null;
		Music music = new Music();
		
		//AccessToken token = WeixinUtil.getAccessToken();
		//String filePath = "E:/weixin/imooc.jpg";
		//String musicMediaId = WeixinUtil.upload(filePath, token.getToken(), "thumb");
		//music.setThumbMediaId("C-4vT3pbBIzqwcWuasEzUV0HUH0vNc8EN65VOAOqYY2Y9g7EbyznIZdOVPzbdxj2");
		//music.setThumbMediaId(musicMediaId);
		
		music.setTitle("任然 - 疑心病");
		music.setDescription("流行歌曲");
		music.setMusicUrl("http://mytest.tunnel.qydev.com/Weixin/resource/yixin.mp3");
		music.setHQMusicUrl("http://mytest.tunnel.qydev.com/Weixin/resource/yixin.mp3");
		
		MusicMessage musicMessage = new MusicMessage();
		musicMessage.setFromUserName(toUserName);
		musicMessage.setToUserName(fromUserName);
		musicMessage.setMsgType(MSGTYPE_MUSIC);
		musicMessage.setCreateTime(new Date().getTime());
		musicMessage.setMusic(music);
		message = musicMessageToXml(musicMessage);
		return message;
	}
	public static String musicMessageToXml(MusicMessage musicMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}
}
