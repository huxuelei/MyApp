package com.dev.httplib.security.rsa;

import android.util.Log;

import com.dev.httplib.security.utils.Base64;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


/**
 * 测试类
 */
public class RSATest {

	public static void main(String[] args) throws FileNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException, IOException, ClassNotFoundException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchProviderException, SignatureException {

		RSATool rsaTool = RSAToolFactory.getRSATool();
       // rsaTool.generateKeyPair(new File("public.pem"), new File("private.pem"));
		byte[] input = "Hello World!".getBytes();

        RSAKey publicKey = rsaTool.loadPublicKey(new File("D:/public.pem"));

        byte[] encoded = rsaTool.encryptWithKey(input, publicKey);
        Log.i("=====","Encoded string: " + new String(encoded,"utf-8"));

        Log.i("=====","base64 data:" + Base64.encode(encoded));

        RSAKey privateKey = rsaTool.loadPrivateKey(new File("D:/private.pem"));
        byte[] decoded = rsaTool.decryptWithKey(encoded, privateKey);
        Log.i("=====","Decoded string: " + new String(decoded));
		
		/*
		int php_encrypted_length = (int) (new File("php_encrypted.bin")).length();
		byte[] php_encrypted = new byte[php_encrypted_length];
		FileInputStream fis = new FileInputStream("php_encrypted.bin");
		fis.read(php_encrypted);
		fis.close();
		
		byte[] php_decoded = tool.decryptWithKey(php_encrypted, privateKey);
		RsaLog.log("Decoded string: " + new String(php_decoded));	
		
		int php_signature_length = (int) (new File("php_signature.bin")).length();
		byte[] php_signature = new byte[php_signature_length];
		fis = new FileInputStream("php_signature.bin");
		fis.read(php_signature);
		fis.close();
		RsaLog.log(tool.verifyWithKey("Hello World!".getBytes(), php_signature, publicKey));
		*/


	}

    public static void WriteStringToFile(String filePath,String str) {
        try {
            File file = new File(filePath);
            PrintStream ps = new PrintStream(new FileOutputStream(file));
            ps.println(str);// 往文件里写入字符串
           // ps.append("http://www.docin.com/p-315288370.html");// 在已有的基础上添加字符串
            ps.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
