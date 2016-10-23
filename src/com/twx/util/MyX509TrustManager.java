package com.twx.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

import com.sun.net.ssl.TrustManager;

//信任管理器
public class MyX509TrustManager implements X509TrustManager, TrustManager{

	@Override
	public void checkClientTrusted(X509Certificate[] arg0, String arg1)
			throws CertificateException {  //检查客户端证书		
	}

	@Override
	public void checkServerTrusted(X509Certificate[] arg0, String arg1)
			throws CertificateException {  //检查服务器端证书		
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() { //返回受信任的X509证书数组
		return null;
	}

}
