package com.neolynk.demoapp.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wassim on 2017/07/16
 */
public enum Transactions {
	
	DEBIT("Debit"), 
	CREDIT("Credit");
	
	/**
	 * the event type
	 */
	String type;

	/**
	 * @param type
	 */
	private Transactions(String type) {
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type 
	 * @param type the name to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	public static Transactions fromString(String type){
		if(DEBIT.getType().equals(type)) return DEBIT ;
		if(CREDIT.getType().equals(type)) return CREDIT ;
		return null ;
	}
	
	
	public static List<String> asStringList(){
		List<String> values = new ArrayList<String>();
		values.add(DEBIT.getType());
		values.add(CREDIT.getType());
		return values ;
	}

}
