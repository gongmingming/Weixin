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
			System.out.println("票据token:"+token.getToken());
			System.out.println("有效时间expiresIn:"+token.getExpiresIn());
			
			/*String filePath = "E:/weixin/imooc.jpg";
			String media_id = WeixinUtil.upload(filePath, token.getToken(), "image");
			//输出imooc.jpg上传之后获得的media_id号
			System.out.println("media_id:"+media_id);
			
			String menu = JSONObject.fromObject(WeixinUtil.initMenu()).toString();
			//创建menu菜单，也是修改菜单
			int result1 = WeixinUtil.createMenu(token.getToken(), menu);
			if(result1==0){
				System.out.println("创建成功");
			}else{
			    System.out.println("创建失败"+result1);
			}
			//查询menu菜单
			JSONObject jsonObject=WeixinUtil.queryMenu(token.getToken());
			System.out.println(jsonObject);
			//删除menu菜单
			int result2 = WeixinUtil.deleteMenu(token.getToken());
			if(result2==0){
				System.out.println("菜单删除成功");
			}else{
			    System.out.println("菜单删除失败"+result2);
			}*/
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
