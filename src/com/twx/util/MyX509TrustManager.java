package com.twx.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

import com.sun.net.ssl.TrustManager;

//���ι�����
public class MyX509TrustManager implements X509TrustManager, TrustManager{

	@Override
	public void checkClientTrusted(X509Certificate[] arg0, String arg1)
			throws CertificateException {  //���ͻ���֤��		
	}

	@Override
	public void checkServerTrusted(X509Certificate[] arg0, String arg1)
			throws CertificateException {  //����������֤��		
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() { //���������ε�X509֤������
		return null;
	}

}
