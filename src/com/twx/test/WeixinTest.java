package com.twx.test;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import net.sf.json.JSONObject;

import com.twx.po.AccessToken;
import com.twx.util.WeixinUtil;

public class WeixinTest {
	public static void main(String[] args) throws Exception {
		try {
			AccessToken token = WeixinUtil.getAccessToken();
			System.out.println("Ʊ��token:"+token.getToken());
			System.out.println("��Чʱ��expiresIn:"+token.getExpiresIn());
			
			/*String filePath = "E:/weixin/imooc.jpg";
			String media_id = WeixinUtil.upload(filePath, token.getToken(), "image");
			//���imooc.jpg�ϴ�֮���õ�media_id��
			System.out.println("media_id:"+media_id);
			
			String menu = JSONObject.fromObject(WeixinUtil.initMenu()).toString();
			//����menu�˵���Ҳ���޸Ĳ˵�
			int result1 = WeixinUtil.createMenu(token.getToken(), menu);
			if(result1==0){
				System.out.println("�����ɹ�");
			}else{
			    System.out.println("����ʧ��"+result1);
			}
			//��ѯmenu�˵�
			JSONObject jsonObject=WeixinUtil.queryMenu(token.getToken());
			System.out.println(jsonObject);
			//ɾ��menu�˵�
			int result2 = WeixinUtil.deleteMenu(token.getToken());
			if(result2==0){
				System.out.println("�˵�ɾ���ɹ�");
			}else{
			    System.out.println("�˵�ɾ��ʧ��"+result2);
			}*/
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
