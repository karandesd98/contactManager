package com.sk.contactManager.contactManager.Models;

public class User {

	Integer USER_ID;
	String USER_NAME;
    String EMAIL;
	String PASSWORD;
    String ROLE;
    Boolean enabled;
    String IMAGE;
    String ABOUT;

	public User() {

	}
    
    public User(Integer USER_ID1, String uSER_NAME, String eMAIL, String pASSWORD, String rOLE, Boolean enabled, String iMAGE,String aBOUT) {
    	USER_ID=USER_ID1;
    	USER_NAME = uSER_NAME;
		EMAIL = eMAIL;
		PASSWORD = pASSWORD;
		ROLE = rOLE;
		this.enabled = enabled;
		IMAGE = iMAGE;
		ABOUT = aBOUT;
	}
    
    
	public String getUSER_NAME() {
		return USER_NAME;
	}
	public void setUSER_NAME(String uSER_NAME) {
		USER_NAME = uSER_NAME;
	}
	public String getEMAIL() {
		return EMAIL;
	}
	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	public String getROLE() {
		return ROLE;
	}
	public void setROLE(String rOLE) {
		ROLE = rOLE;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public String getIMAGE() {
		return IMAGE;
	}
	public void setIMAGE(String iMAGE) {
		IMAGE = iMAGE;
	}
	public String getABOUT() {
		return ABOUT;
	}
	public void setABOUT(String aBOUT) {
		ABOUT = aBOUT;
	}

	public Integer getUSER_ID() {
		return USER_ID;
	}

	public void setUSER_ID(Integer uSER_ID) {
		USER_ID = uSER_ID;
	}


	
	
}
