package org.home.dropbox;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

public class StoreToken {

	String storedToken;
	
	public StoreToken() {
       
    }
	public void setStoredToken(String accessToken) {
		this.storedToken = accessToken;
	}
    public String readStoredTokenFromFile(HttpServletRequest request) {
		
         try {
            
            FileReader storeTokenReader = new FileReader(getPathPropertiesFile(request));
            BufferedReader br = new BufferedReader(storeTokenReader);
        	storedToken = br.readLine();
          //  storeTokenWriter = new FileWriter(projectPathFull + "\\WEB-INF\\classes\\"+fileName);            
            
            storeTokenReader.close();
            return storedToken;
            
        } catch (IOException e) {
            System.err.println("Error: Properties file is not found");
            return "error";
        }
	
	}
    
    public void writeStoredTokenToFile(HttpServletRequest request) {
		
        try {
           
           FileWriter storeTokenWriter = new FileWriter(getPathPropertiesFile(request));
           storeTokenWriter.write(storedToken);

           storeTokenWriter.close();
                      
       } catch (IOException e) {
           System.err.println("Error: Properties file is not found");
       }
	
	}
    
    private String getPathPropertiesFile(HttpServletRequest request) throws IOException {
    	
    	FileInputStream fis;
		Properties property = new Properties();
    	
		String projectPathFull = request.getRealPath("/");

		fis = new FileInputStream(projectPathFull + "\\WEB-INF\\classes\\"
				+ "storeTokenFileName.properties");

		property.load(fis);

		String fileName = property.getProperty("fileName");

		String pathFile = "";
		String[] sArray = projectPathFull.split("\\\\");
		for (int i = 0; i < sArray.length; i++) {
			pathFile = pathFile + sArray[i] + "/";
		}
		pathFile = pathFile + "WEB-INF/classes/" + fileName;

		fis.close();
		
		return pathFile;
    	}
	}
