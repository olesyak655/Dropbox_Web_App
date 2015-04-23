package org.home.servlets;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.home.dropbox.ConnectDropbox;
import org.home.dropbox.StoreToken;

import com.dropbox.core.DbxWebAuth;

@WebServlet("/DropboxServlet")
public class DropboxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	FileReader storeTokenReader; 
	FileWriter storeTokenWriter;
	
	public DropboxServlet() {
        super();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	StoreToken stt = new StoreToken();
    	String storedToken = stt.readStoredTokenFromFile(request);

    	if (storedToken != null ) {
    		if (storedToken.equals("error")) {
    			response.sendError(400);
    		}
    		RequestDispatcher rd = request.getRequestDispatcher("/2.jsp"); 
            rd.forward(request, response);
    	}else {
    		
    		ConnectDropbox connectDropbox = new ConnectDropbox();
    		
    		DbxWebAuth auth = connectDropbox.createAuth(request, response);
    		
     		String authorizePageUrl = auth.start();
    		response.sendRedirect(authorizePageUrl);
    	}  
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
