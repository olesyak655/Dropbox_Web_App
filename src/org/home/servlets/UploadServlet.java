package org.home.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.home.dropbox.ConnectDropbox;
import org.home.dropbox.StoreToken;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWriteMode;

@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	StoreToken storeToken = new StoreToken();
    	String accessToken = storeToken.readStoredTokenFromFile(request);
		if (accessToken.equals("error")) {
			response.sendError(400);
		}
    	
    	ConnectDropbox connectDropbox = new ConnectDropbox();
    	DbxRequestConfig requestConfig = connectDropbox.createRequestConfig(request, response);
    	
    	DbxClient client = new DbxClient(requestConfig, accessToken);
    	
    	String fileName=request.getParameter("fileNameUpload");
    	int i = fileName.lastIndexOf("/");
    	String fileNameUpload = fileName.substring(i);
    	File inputFile = new File(fileName);
        FileInputStream inputStream = new FileInputStream(inputFile);
        try {
            DbxEntry.File uploadedFile = client.uploadFile("/"+fileNameUpload,
                DbxWriteMode.add(), inputFile.length(), inputStream);
            System.out.println("Uploaded: " + uploadedFile.toString());
        }catch(Exception e) {
        	
        }
        finally {
            inputStream.close();
        }
        
        response.setContentType("text/html;charset=UTF-8");
                       
        try (PrintWriter out = response.getWriter()) {
                        
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>" + "Upload rezult" + "</title>");            
            out.println("</head>");
            out.println("<body bgcolor=\"#EEE8AA\">");
                      
            out.println("<br>");
            out.println("<br>");
 
            out.println("<h3>" + "Success!  File  " +fileName+" is upload to Dropbox" + "</h3>");
            out.println("<br>");
            out.println("<br>");
            out.println("<a href=\"2.jsp\">Back</a>");
            out.println("</body>");
            out.println("</html>");
            
        }
    
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
