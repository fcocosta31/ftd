package br.ftd.model;

import java.io.IOException;
import java.util.Properties;

import java.io.InputStream;

public class FTDProperties {
	
	private String dbname;
	private String dbuser;
	private String dbpass;
	private String apikey;
	private String urlapi;
	
	public static FTDProperties ftdProperties;
	
	public static FTDProperties getInstance() {
		ftdProperties = new FTDProperties();
		return ftdProperties;
	}
	
	public FTDProperties() {
		
		Properties prop = new Properties();
		String propFileName = "config.properties";
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		try {
		
			prop.load(inputStream);
			this.dbname = prop.getProperty("dbname");
			this.dbuser = prop.getProperty("dblocaluser");
			this.dbpass = prop.getProperty("dblocalpass");
			this.apikey = prop.getProperty("apikey");
			this.urlapi = prop.getProperty("urlapi");

		}catch(IOException e) {
			System.out.println("File "+propFileName+" not found in classpath!");
		}
		
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public String getDbuser() {
		return dbuser;
	}

	public void setDbuser(String dbuser) {
		this.dbuser = dbuser;
	}

	public String getDbpass() {
		return dbpass;
	}

	public void setDbpass(String dbpass) {
		this.dbpass = dbpass;
	}

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	public String getUrlapi() {
		return urlapi;
	}

	public void setUrlapi(String urlapi) {
		this.urlapi = urlapi;
	}
	
	

}