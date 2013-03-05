
package com.noesis;

import java.util.List;

public class Credentials{
   	private List<ApplicationsKeyChain> applicationsKeyChain;
   	private String created;
   	private boolean energyStarUser;
   	private String firstName;
   	private String greetingName;
   	private String lastAccess;
   	private String lastName;
   	private String modified;
   	private String notAfter;
   	private String notBefore;
   	private String oldPassword;
   	private String password;
   	private String passwordUpdate;
   	private String registrationDate;
   	private String username;
   	private String __type;

 	public List<ApplicationsKeyChain> getApplicationsKeyChain(){
		return this.applicationsKeyChain;
	}
	public void setApplicationsKeyChain(List<ApplicationsKeyChain> applicationsKeyChain){
		this.applicationsKeyChain = applicationsKeyChain;
	}
 	public String getCreated(){
		return this.created;
	}
	public void setCreated(String created){
		this.created = created;
	}
 	public boolean getEnergyStarUser(){
		return this.energyStarUser;
	}
	public void setEnergyStarUser(boolean energyStarUser){
		this.energyStarUser = energyStarUser;
	}
 	public String getFirstName(){
		return this.firstName;
	}
	public void setFirstName(String firstName){
		this.firstName = firstName;
	}
 	public String getGreetingName(){
		return this.greetingName;
	}
	public void setGreetingName(String greetingName){
		this.greetingName = greetingName;
	}
 	public String getLastAccess(){
		return this.lastAccess;
	}
	public void setLastAccess(String lastAccess){
		this.lastAccess = lastAccess;
	}
 	public String getLastName(){
		return this.lastName;
	}
	public void setLastName(String lastName){
		this.lastName = lastName;
	}
 	public String getModified(){
		return this.modified;
	}
	public void setModified(String modified){
		this.modified = modified;
	}
 	public String getNotAfter(){
		return this.notAfter;
	}
	public void setNotAfter(String notAfter){
		this.notAfter = notAfter;
	}
 	public String getNotBefore(){
		return this.notBefore;
	}
	public void setNotBefore(String notBefore){
		this.notBefore = notBefore;
	}
 	public String getOldPassword(){
		return this.oldPassword;
	}
	public void setOldPassword(String oldPassword){
		this.oldPassword = oldPassword;
	}
 	public String getPassword(){
		return this.password;
	}
	public void setPassword(String password){
		this.password = password;
	}
 	public String getPasswordUpdate(){
		return this.passwordUpdate;
	}
	public void setPasswordUpdate(String passwordUpdate){
		this.passwordUpdate = passwordUpdate;
	}
 	public String getRegistrationDate(){
		return this.registrationDate;
	}
	public void setRegistrationDate(String registrationDate){
		this.registrationDate = registrationDate;
	}
 	public String getUsername(){
		return this.username;
	}
	public void setUsername(String username){
		this.username = username;
	}
 	public String get__type(){
		return this.__type;
	}
	public void set__type(String __type){
		this.__type = __type;
	}
}
