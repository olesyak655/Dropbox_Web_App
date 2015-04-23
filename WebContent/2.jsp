<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.dropbox.core.*" %>
<%@ page import="org.home.dropbox.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
</head>
<body>

	<h3>Authorization is success!</h3>
	<h4>You can upload files to Dropbox and download its from Dropbox</h4> 
	
	<%
		StoreToken stt = new StoreToken();
		String storedToken = stt.readStoredTokenFromFile(request);
	
		if (storedToken == null ) {
			
			ConnectDropbox connectDropbox = new ConnectDropbox();
 			DbxRequestConfig requestConfig = connectDropbox.createRequestConfig(request, response);
 			DbxWebAuth auth = connectDropbox.createAuth(request, response); 
 			
 			DbxAuthFinish authFinish;
 			try {
 			    authFinish = auth.finish(request.getParameterMap());
 			}
 			catch (DbxWebAuth.BadRequestException ex) {
 			    log("On /JEE_2015_dropbox_2: Bad request: " + ex.getMessage());
 			   // response.sendError(400);
 			    return;
 			}
 			catch (DbxWebAuth.BadStateException ex) {
 			    // Send them back to the start of the auth flow.
 			    response.sendRedirect("http://localhost:8080/JEE_2015_dropbox_2");
 			    return;
 			}
 			catch (DbxWebAuth.CsrfException ex) {
 			    log("On /JEE_2015_dropbox_2: CSRF mismatch: " + ex.getMessage());
 			    return;
 			}
 			catch (DbxWebAuth.NotApprovedException ex) {
 			    // When Dropbox asked "Do you want to allow this app to access your
 			    // Dropbox account?", the user clicked "No".
 				response.sendRedirect("http://localhost:8080/JEE_2015_dropbox_2");
 			    return;
 			 }
 			 catch (DbxWebAuth.ProviderException ex) {
 			     log("On /JEE_2015_dropbox_2: Auth failed: " + ex.getMessage());
 			     response.sendError(503, "Error communicating with Dropbox.");
 			     return;
 			 }
 			 catch (DbxException ex) {
 			     log("On /JEE_2015_dropbox_2: Error getting token: " + ex.getMessage());
 			     response.sendError(503, "Error communicating with Dropbox.");
 			     return;
 			 } 
 			 String accessToken = authFinish.accessToken;
			 
 			 //StoreToken stt = new StoreToken();
 			 stt.setStoredToken(accessToken);
 			 stt.writeStoredTokenToFile(request);
		}	 
 		%>
	<div>
		<form name="Upload Form" method="post" action="UploadServlet" >
             <div>
             	<span>
             		File name: <input name="fileNameUpload" type="text" />
            	</span>
                <span style="margin-left: 3%;">
             		<input type="submit" value="upload" name="submit" /><br/><br/>
             	</span>
             	<div id="messageFileNameUpload" style="color: red; font-size: 10px">
             			Input full file name, please
             	</div> 
             </div>
             
         </form>
	</div>
	<div>
		<form name="Downnload Form" method="post" action="DownloadServlet" >
             <div>
             	<span>
             		File name: <input name="fileNameDownload" type="text" />
            	</span>
                <span style="margin-left: 3%;">
             		<input type="submit" value="download" name="submit" /><br/><br/>
             	</span>
             	<div id="messageFileNameDownload" style="color: red; font-size: 10px">
             			Input full file name, please
             	</div> 
             </div>
             
         </form>
	</div>
	
</body>
</html>