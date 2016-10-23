package com.twx.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.SSLContext;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONObject;
import android.R.integer;

import com.twx.util.MyX509TrustManager;

public class TokenTest {
	    //测试/开发者ID的APPID（应用ID）和APPSECRET（应用密钥）
		private static final String APPID = "wx0b8a7d3071bdcae9";
		private static final String APPSECRET = "1031b2f16e31a4e45bcf8e1fb55e0fb5";
		//接口调用access_token                             
		private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
		public static void main(String[] args) throws Exception {
		//测试获取凭证（HTTPS GET请求）
        String tokenUrl = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);	
	   //建立连接
        URL url = new URL(tokenUrl);
	    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
	    //使用自定义的信任管理器
	    TrustManager[] tm = {new MyX509TrustManager()};
	    SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
	    sslContext.init(null, tm, new java.security.SecureRandom());
	    SSLSocketFactory ssf = sslContext.getSocketFactory();
	    conn.setSSLSocketFactory(ssf);
	    conn.setDoInput(true);
	    //设置请求方式
	    conn.setRequestMethod("GET");
	    //取得输入流
	    InputStream inputStream = conn.getInputStream();
	    InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
	    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	    //读取响应内容
	    StringBuffer buffer = new StringBuffer();
	    String str = null;
	    while ((str=bufferedReader.readLine())!=null) {
			buffer.append(str);
		}
	    bufferedReader.close();
	    inputStreamReader.close();
	    inputStream.close();
	    conn.disconnect();
	    //从得到的JSON字符串中获取
	    JSONObject jsonObject = JSONObject.fromObject(buffer.toString());
	    String accesstoken = jsonObject.getString("access_token");
	    int expiresIn = jsonObject.getInt("expires_in");
	    //输出返回结果
	    System.out.println("accesstoken:"+accesstoken+"\n"+"expiresIn:"+expiresIn);	    
	}

}
