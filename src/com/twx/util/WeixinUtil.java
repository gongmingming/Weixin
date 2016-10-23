package com.twx.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONObject;

import org.apache.http.client.ClientProtocolException;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.twx.menu.Button;
import com.twx.menu.ClickButton;
import com.twx.menu.Menu;
import com.twx.menu.ViewButton;
import com.twx.po.AccessToken;

public class WeixinUtil {
	//测试/开发者ID的APPID（应用ID）和APPSECRET（应用密钥）
	private static final String APPID = "wx0b8a7d3071bdcae9";
	private static final String APPSECRET = "1031b2f16e31a4e45bcf8e1fb55e0fb5";
	//接口调用access_token                             
	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	//接口调用上传media
	private static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	//接口调用创建menu
	private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	//接口调用查询menu
	private static final String QUERY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	//接口调用查询menu
	private static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
				
	/**
	 * 对接口的get请求
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws NoSuchProviderException 
	 * @throws Exception 
	 */
	public static JSONObject doGetStr(String url) throws ClientProtocolException, IOException, Exception{
		 
		 URL tokenUrl = new URL(url);
		 HttpsURLConnection conn = (HttpsURLConnection) tokenUrl.openConnection();
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
		return jsonObject;
	}
	
	/**
	 * 对接口的post请求
	 * @param url
	 * @param outStr
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 * @throws Exception 
	 */
	public static JSONObject doPostStr(String url,String outStr) throws ParseException, IOException, NoSuchAlgorithmException, Exception{
		
		 URL tokenUrl = new URL(url);
		 HttpsURLConnection conn = (HttpsURLConnection) tokenUrl.openConnection();
		    //使用自定义的信任管理器
		    TrustManager[] tm = {new MyX509TrustManager()};
		    SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
		    sslContext.init(null, tm, new java.security.SecureRandom());
		    SSLSocketFactory ssf = sslContext.getSocketFactory();
		    conn.setSSLSocketFactory(ssf);
		    conn.setDoInput(true);
		    conn.setDoOutput(true);
		    conn.setUseCaches(false); 
		    //设置请求方式
		    conn.setRequestMethod("POST");

		    OutputStream out = new DataOutputStream(conn.getOutputStream());
			//输出表头
		    byte[] head = outStr.getBytes("utf-8");
			out.write(head);
			
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
		return jsonObject;

		/*DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(url);
		JSONObject jsonObject = null;
		try {
			httpost.setEntity(new StringEntity(outStr,"UTF-8"));
			HttpResponse response = httpClient.execute(httpost);
			String result = EntityUtils.toString(response.getEntity(),"UTF-8");
			jsonObject = JSONObject.fromObject(result);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return jsonObject;*/
	}
	
	/**
	 * 获取access_token
	 * @return
	 * @throws Exception 
	 */
	public static AccessToken getAccessToken() throws Exception{
		AccessToken token = new AccessToken();
		//将发送access_token的url中的APPID和APPSECRET换成自己的，注意是replace（）方法。
		String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);		
		JSONObject jsonObject = doGetStr(url);
		if(jsonObject!=null){
			token.setToken(jsonObject.getString("access_token"));
			token.setExpiresIn(jsonObject.getInt("expires_in"));
		}
		return token;
	}
	
	/**
	 * 上传媒体文件，返回mediaId
	 * @param filePath
	 * @param accessToken
	 * @param type
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws KeyManagementException
	 */
	public static String upload(String filePath, String accessToken,String type) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("文件不存在");
		}

		String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE",type);
		
		URL urlObj = new URL(url);
		//连接
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

		con.setRequestMethod("POST"); 
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false); 

		//设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");

		//设置边界
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

		StringBuilder sb = new StringBuilder();
		sb.append("--");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");

		byte[] head = sb.toString().getBytes("utf-8");

		//获得输出流（注意这里的输出是指从这里输出到微信接口服务器,不要搞反了）
		OutputStream out = new DataOutputStream(con.getOutputStream());
		//输出表头
		out.write(head);

		//文件正文部分
		//把文件以流文件的方式推入到url中
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();

		//结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");//定义最后数据分割线
		out.write(foot);

		out.flush();
		out.close();//输出完毕，可以读取返回响应了

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		String result = null;
		try {
			//定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		JSONObject jsonObj = JSONObject.fromObject(result);
		System.out.println(jsonObj);
		String typeName = "media_id";
		if(!"image".equals(type)){
			typeName = type + "_media_id";
		}
		String mediaId = jsonObj.getString(typeName);
		return mediaId;
	}
	
	/**
	 * 组装菜单
	 */
	public static Menu initMenu(){
		Menu menu = new Menu();
		ClickButton button11 = new ClickButton();
		button11.setName("click菜单");
		button11.setType("click");
		button11.setKey("11");
		
		ViewButton button21 = new ViewButton();
		button21.setName("view菜单");
		button21.setType("view");
		button21.setUrl("http://www.imooc.com");
		
		ClickButton button31 = new ClickButton();
		button31.setName("扫码事件");
		button31.setType("scancode_push");
		button31.setKey("31");
		
		ClickButton button32 = new ClickButton();
		button32.setName("地理位置");
		button32.setType("location_select");
		button32.setKey("32");
		
		Button button3 = new Button();
		button3.setName("菜单三");//注意这里没有设置Type
		button3.setSub_button(new Button[]{button31,button32});
		
		menu.setButton(new Button[]{button11,button21,button3});
		return menu;
	}
	//创建下面的自定义menu菜单
	public static int createMenu(String token,String menu) throws Exception{
		int result = 0;
		String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doPostStr(url, menu);
		if(jsonObject != null){
			result = jsonObject.getInt("errcode");
		}
		return result;
	}
	//查询menu菜单
	public static JSONObject queryMenu(String token) throws IOException, Exception{
		String url = QUERY_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doGetStr(url);
		return jsonObject;
	}
	//删除menu菜单
	public static int deleteMenu(String token) throws IOException, Exception{
		int result = 0;
		String url = DELETE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doGetStr(url);
		if (jsonObject!=null) {
			result = jsonObject.getInt("errcode");
		}
		return result;
	}
}
