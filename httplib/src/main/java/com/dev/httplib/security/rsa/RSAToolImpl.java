package com.dev.httplib.security.rsa;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.bouncycastle.openssl.PEMReader;
import org.bouncycastle.openssl.PEMWriter;

/**
 * RSAToolImpl ，生产RSA ,获得 RSA key
 */
public class RSAToolImpl implements RSATool {

	@Override
	public void generateKeyPair(File publicKeyFile, File privateKeyFile)
			throws FileNotFoundException, IOException,
			NoSuchAlgorithmException, InvalidKeySpecException,
			NoSuchProviderException {
		SecureRandom random = new SecureRandom();
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");

		generator.initialize(2048, random);

		KeyPair pair = generator.generateKeyPair();
		Key pubKey = pair.getPublic();

		PEMWriter pubWriter = new PEMWriter(new FileWriter(publicKeyFile));
		pubWriter.writeObject(pubKey);
		pubWriter.close();

		PEMWriter privWriter = new PEMWriter(new FileWriter(privateKeyFile));
		privWriter.writeObject(pair);
		privWriter.close();
	}

	@Override
	public RSAKey loadPublicKey(File file) throws FileNotFoundException,
			IOException, ClassNotFoundException, InvalidKeySpecException,
			NoSuchAlgorithmException {
		PEMReader reader = new PEMReader(new FileReader(file));
		Key pubKey = (Key) reader.readObject();
		reader.close();
		return new RSAKeyImpl(pubKey);
	}

	@Override
	public RSAKey loadPrivateKey(File file) throws FileNotFoundException,
			IOException, ClassNotFoundException, NoSuchAlgorithmException,
			InvalidKeySpecException {
		PEMReader reader = new PEMReader(new FileReader(file),
				new PasswordFinderImpl("D811D3"));
		KeyPair pair = (KeyPair) reader.readObject();
		Key privKey = (Key) pair.getPrivate();
		reader.close();
		return new RSAKeyImpl(privKey);
	}

	@Override
	public RSAKey loadPublicKey(String publicKeyStr)
			throws NoSuchAlgorithmException, InvalidKeySpecException,
			IOException, NullPointerException {

		// BASE64Decoder base64Decoder= new BASE64Decoder();
		// byte[] buffer= base64Decoder.decodeBuffer(publicKeyStr);
		// KeyFactory keyFactory= KeyFactory.getInstance("RSA");
		// X509EncodedKeySpec keySpec= new X509EncodedKeySpec(buffer);
		// RSAPublicKey publicKey = (RSAPublicKey)
		// keyFactory.generatePublic(keySpec);
		// return new RSAKeyImpl(publicKey);

		// BASE64Decoder base64Decoder = new BASE64Decoder();
		// byte[] buffer = base64Decoder.decodeBuffer(publicKeyStr);
		// KeyFactory keyFactory;
		// try {
		// // keyFactory = KeyFactory.getInstance("RSA");
		// keyFactory = KeyFactory.getInstance("RSA", "BC");
		// X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
		// PublicKey publicKey = keyFactory.generatePublic(keySpec);
		// return new RSAKeyImpl(publicKey);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// return null;
		// TODO XIAQIULEI
		PEMReader reader = new PEMReader(new StringReader(publicKeyStr));
		Key pubKey = (Key) reader.readObject();
		reader.close();
		return new RSAKeyImpl(pubKey);
	}

	public RSAKey loadPrivateKey(String privateKeyStr)
			throws NoSuchAlgorithmException, InvalidKeySpecException,
			IOException, NullPointerException {
		
		PEMReader reader = new PEMReader(new StringReader(privateKeyStr));
		KeyPair kp = (KeyPair) reader.readObject();
		reader.close();
		PrivateKey privateKey = kp.getPrivate();
		return new RSAKeyImpl(privateKey);

	}

	@Override
	public byte[] encryptWithKey(byte[] input, RSAKey key)
			throws IllegalBlockSizeException, BadPaddingException,
			InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, ((RSAKeyImpl) key).getKey(),
				new SecureRandom());
		return cipher.doFinal(input);
	}

	@Override
	public byte[] decryptWithKey(byte[] input, RSAKey key)
			throws IllegalBlockSizeException, BadPaddingException,
			InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, ((RSAKeyImpl) key).getKey());
		return cipher.doFinal(input);
	}

	@Override
	public byte[] signWithKey(byte[] input, RSAKey key)
			throws NoSuchAlgorithmException, InvalidKeyException,
			SignatureException {
		Signature signature = Signature.getInstance("SHA1withRSA");
		signature.initSign((PrivateKey) ((RSAKeyImpl) key).getKey(),
				new SecureRandom());
		signature.update(input);
		return signature.sign();
	}

	@Override
	public boolean verifyWithKey(byte[] input, byte[] sig, RSAKey key)
			throws SignatureException, InvalidKeyException,
			NoSuchAlgorithmException {
		Signature signature = Signature.getInstance("SHA1withRSA");
		signature.initVerify((PublicKey) ((RSAKeyImpl) key).getKey());
		signature.update(input);
		return signature.verify(sig);
	}

}
