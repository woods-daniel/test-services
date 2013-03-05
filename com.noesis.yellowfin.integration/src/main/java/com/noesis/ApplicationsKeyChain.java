
package com.noesis;

public class ApplicationsKeyChain{
   	private String __type;
   	private String key;
   	private Value value;

 	public String get__type(){
		return this.__type;
	}
	public void set__type(String __type){
		this.__type = __type;
	}
 	public String getKey(){
		return this.key;
	}
	public void setKey(String key){
		this.key = key;
	}
 	public Value getValue(){
		return this.value;
	}
	public void setValue(Value value){
		this.value = value;
	}
}
