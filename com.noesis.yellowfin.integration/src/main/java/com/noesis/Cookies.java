
package com.noesis;

import java.util.List;

public class Cookies{
   	private String key;
   	private List<Value> value;

 	public String getKey(){
		return this.key;
	}
	public void setKey(String key){
		this.key = key;
	}
 	public List<Value> getValue(){
		return this.value;
	}
	public void setValue(List<Value> value){
		this.value = value;
	}
}
