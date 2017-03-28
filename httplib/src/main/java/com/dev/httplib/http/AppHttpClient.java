package com.dev.httplib.http;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

public class AppHttpClient {

	public static AppHttpClient instance = new AppHttpClient();

	public static AppHttpClient getInstance() {
		return instance;
	}

	HttpClient client;
	boolean success;
	int statusCode = -1;
	HttpResponse httpResponse;

	private AppHttpClient() {
		success = false;
	}

	public HttpClient createClient() {
		HttpParams params = new BasicHttpParams();
		params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
				HttpVersion.HTTP_1_1);
		params.setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET,
				HTTP.DEFAULT_CONTENT_CHARSET);
		params.setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, true);
		params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30 * 1000);
		params.setParameter(CoreConnectionPNames.SO_TIMEOUT, 30 * 1000);

		SchemeRegistry schReg = new SchemeRegistry();
		schReg.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schReg.register(new Scheme("https",
				SSLSocketFactory.getSocketFactory(), 443));
		ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
				params, schReg);
		HttpClient hc = new DefaultHttpClient(conMgr, params);
		return hc;
	}
}
