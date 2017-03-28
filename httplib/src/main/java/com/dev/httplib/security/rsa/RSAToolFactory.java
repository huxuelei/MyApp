package com.dev.httplib.security.rsa;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

/**
 * RSATool 工具单例类
 */
public class RSAToolFactory {

	private static RSATool instance = null;
	
	public static RSATool getRSATool() {
		if(instance == null) {
			Security.addProvider(new BouncyCastleProvider());
			instance = new RSAToolImpl();
		}
		return instance;
	}

}
