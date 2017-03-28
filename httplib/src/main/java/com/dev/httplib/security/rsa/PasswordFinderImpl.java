package com.dev.httplib.security.rsa;

import org.bouncycastle.openssl.PasswordFinder;

/**
 * To change this template use File | Settings | File Templates.
 */
public class PasswordFinderImpl implements PasswordFinder {
    private String passWord;

    public PasswordFinderImpl(String password) {
        this.passWord = password;
    }

    @Override
    public char[] getPassword() {
        char[] pass = passWord.toCharArray();
        return pass;
    }
}
