package com.neolynk.demoapp.validation;

/**
 * Created by wassim on 2017/07/16
 */
public class ApplicationException extends RuntimeException {
	private static final long serialVersionUID = 8205877130108144548L;

	private String code;

	private String description;

	public ApplicationException(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public ApplicationException(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String toString() {
		return this.code + " " + this.description;
	}
}
