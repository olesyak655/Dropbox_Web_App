package org.home.dropbox;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxSessionStore;
import com.dropbox.core.DbxStandardSessionStore;
import com.dropbox.core.DbxWebAuth;

public class ConnectDropbox {
	
	public DbxWebAuth auth;
	public DbxRequestConfig requestConfig;
	public String accessToken;
	
	public DbxRequestConfig createRequestConfig(HttpServletRequest request, HttpServletResponse response) {
		        
		String userLocale = Locale.getDefault().toString();
		requestConfig = new DbxRequestConfig("text-edit/0.1", userLocale);
		return requestConfig;
	}
	
	public DbxWebAuth createAuth(HttpServletRequest request, HttpServletResponse response) {
		final String APP_KEY = "1qxnqttlbbq2s4x";
        final String APP_SECRET = "eb4unjua39058nb";
        
		requestConfig = createRequestConfig(request, response);
		DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);
		
		HttpSession session = request.getSession(true);
		String sessionKey = "dropbox-auth-csrf-token";
		DbxSessionStore csrfTokenStore = new DbxStandardSessionStore(session, sessionKey);

		String redirectUri = "http://localhost:8080/JEE_2015_dropbox_2/2.jsp";
		DbxWebAuth auth = new DbxWebAuth(requestConfig, appInfo, redirectUri, csrfTokenStore);
		return auth;
	}
}
