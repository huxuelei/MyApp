package com.dev.httplib.http;

import com.dev.httplib.http.annotation.SecurityType;

public class KeyValue {

	public String key;

	public String value;

	public SecurityType securityType = SecurityType.None;

	public KeyValue() {

	}

	public KeyValue(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public KeyValue(String key, String value, SecurityType securityType) {
		super();
		this.key = key;
		this.value = value;
		this.securityType = securityType;
	}

	public String toString() {
		return "KeyValue [key=" + key + ", value=" + value + ", securityType="
				+ securityType + "]";
	}
}