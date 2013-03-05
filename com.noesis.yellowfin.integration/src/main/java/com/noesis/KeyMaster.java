
package com.noesis;

public class KeyMaster{
   	private String cipheredTicketGrantingTicket;
   	private CookieContainer cookieContainer;
   	private Credentials credentials;
   	private int errorCode;
   	private String errorKey;
   	private String errorMessage;
   	private String requestKey;
   	private String services;
   	private String ticket;

 	public String getCipheredTicketGrantingTicket(){
		return this.cipheredTicketGrantingTicket;
	}
	public void setCipheredTicketGrantingTicket(String cipheredTicketGrantingTicket){
		this.cipheredTicketGrantingTicket = cipheredTicketGrantingTicket;
	}
 	public CookieContainer getCookieContainer(){
		return this.cookieContainer;
	}
	public void setCookieContainer(CookieContainer cookieContainer){
		this.cookieContainer = cookieContainer;
	}
 	public Credentials getCredentials(){
		return this.credentials;
	}
	public void setCredentials(Credentials credentials){
		this.credentials = credentials;
	}
 	public int getErrorCode(){
		return this.errorCode;
	}
	public void setErrorCode(int errorCode){
		this.errorCode = errorCode;
	}
 	public String getErrorKey(){
		return this.errorKey;
	}
	public void setErrorKey(String errorKey){
		this.errorKey = errorKey;
	}
 	public String getErrorMessage(){
		return this.errorMessage;
	}
	public void setErrorMessage(String errorMessage){
		this.errorMessage = errorMessage;
	}
 	public String getRequestKey(){
		return this.requestKey;
	}
	public void setRequestKey(String requestKey){
		this.requestKey = requestKey;
	}
 	public String getServices(){
		return this.services;
	}
	public void setServices(String services){
		this.services = services;
	}
 	public String getTicket(){
		return this.ticket;
	}
	public void setTicket(String ticket){
		this.ticket = ticket;
	}
}
