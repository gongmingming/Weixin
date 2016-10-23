package com.twx.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.twx.util.CheckUtil;
import com.twx.util.MessageUtil;

public class WeixinServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5858558674842597948L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//System.out.println("1111111111!!!");
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		
		PrintWriter out = resp.getWriter();
		if(CheckUtil.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);
		}
		out.close();
		out=null;
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//System.out.println("dopost");
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		try {
			Map<String, String> message = MessageUtil.xmlToMap(req);
			String fromUser = message.get("FromUserName");
			String toUser = message.get("ToUserName");
			String content = message.get("Content");
			String msgType = message.get("MsgType");
			String msgId = message.get("MsgId");
			
			
			String respone = null;
			if(MessageUtil.MSGTYPE_TEXT.equals(msgType)){
				if("1".equals(content) || "2".equals(content)){
					System.out.println("---1----2-----");
					respone = MessageUtil.initText(toUser, fromUser, MessageUtil.keyWord(content));
				}else if("3".equals(content)){
					System.out.println("----3-----");
					respone = MessageUtil.initNewsMessage(toUser, fromUser);
				}else if("4".equals(content)){
					System.out.println("----4-----");
					respone = MessageUtil.initImageMessage(toUser, fromUser);
				}else if("5".equals(content)){
					System.out.println("----5-----");
					respone = MessageUtil.initMusicMessage(toUser, fromUser);
				}else{
					System.out.println("-----��Ч----");
					//respone = "�����ָ����Ч";
					respone = MessageUtil.initText(toUser, fromUser, MessageUtil.keyWord(content));
				}				
			}else if(MessageUtil.MSGTYPE_NEWS.equals(msgType)){//ͼ����Ϣ
				respone = MessageUtil.initNewsMessage(toUser, fromUser);
			}
			else if(MessageUtil.MSGTYPE_EVENT.equals(msgType)){//������¼�
				String event = message.get("Event");//�¼�����				
				if(MessageUtil.EVENT_SUBSCRIBE.equals(event)){ //��ע
					respone = MessageUtil.initText(toUser, fromUser, MessageUtil.mainMenu());
				}else if(MessageUtil.EVENT_CLICK.equals(event)){//�û�����Զ���˵�,��ȡ��Ϣʱ���¼�����
					respone = MessageUtil.initText(toUser, fromUser, MessageUtil.mainMenu());
				}else if(MessageUtil.EVENT_VIEW.equals(event)){//�û�����Զ���˵�,����˵���ת����ʱ���¼�����
					String url = message.get("EventKey");//�¼�KEYֵ�����õ���תURL
					respone = MessageUtil.initText(toUser, fromUser, url);
				}else if(MessageUtil.EVENT_SCANCODE_PUSH.equals(event)){
					String key = message.get("EventKey");
					respone = MessageUtil.initText(toUser, fromUser, key);
				}
				/*}else if(MessageUtil.EVENT_LOCATION.equals(event)){//�û��ϱ�����λ��
					String label = message.get("SendLocationInfo");
					System.out.println("SendLocationInfo:"+label);
					respone = MessageUtil.initText(toUser, fromUser, label);
				}*/
			}else if(MessageUtil.MSGTYPE_LOCATION.equals(msgType)){//�û��ϱ�����λ��
				String label = message.get("Label");
				respone = MessageUtil.initText(toUser, fromUser, label);
			}else{
				respone = MessageUtil.initText(toUser, fromUser, MessageUtil.keyWord(content));
			}	
     		System.out.println(respone);
			out.println(respone);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			out.close();
			out=null;
		}
	}
}
