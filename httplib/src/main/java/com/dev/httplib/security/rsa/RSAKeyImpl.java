package com.dev.httplib.security.rsa;

import java.security.Key;


/**
 * RSA KEY 具体实现类
 */
public class RSAKeyImpl implements RSAKey {

	private Key key;

	public RSAKeyImpl(Key key) {
		this.key = key;
	}

	public Key getKey() {
		return key;
	}

}
