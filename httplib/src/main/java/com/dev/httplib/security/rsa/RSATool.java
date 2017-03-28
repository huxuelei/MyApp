package com.dev.httplib.security.rsa;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public interface RSATool {

	/**
	 * 产生 RSA Key pair
	 * 
	 * @ReqParam publicKeyFile public key file 文件形式
	 * @ReqParam privateKeyFile private key file 形式
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchProviderException
	 */
	public void generateKeyPair(File publicKeyFile, File privateKeyFile)
			throws FileNotFoundException, IOException,
			NoSuchAlgorithmException, InvalidKeySpecException,
			NoSuchProviderException;

	/**
	 * 加载 RSA PublicKey
	 * 
	 * @ReqParam file 文件的形式
	 * @return RSAKey
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
	public RSAKey loadPublicKey(File file) throws FileNotFoundException,
			IOException, ClassNotFoundException, InvalidKeySpecException,
			NoSuchAlgorithmException;

	/**
	 * 加载 loadPrivateKey
	 * 
	 * @ReqParam file
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public RSAKey loadPrivateKey(File file) throws FileNotFoundException,
			IOException, ClassNotFoundException, NoSuchAlgorithmException,
			InvalidKeySpecException;

	/**
	 * 加载 RSA PublicKey 字符串的形式
	 * 
	 * @ReqParam publicKeyStr 字符串的形式
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws IOException
	 * @throws NullPointerException
	 */
	public RSAKey loadPublicKey(String publicKeyStr)
			throws NoSuchAlgorithmException, InvalidKeySpecException,
			IOException, NullPointerException;

	/**
	 * 加载 loadPrivateKey
	 * 
	 * @ReqParam privateKeyStr 字符串
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws IOException
	 * @throws NullPointerException
	 */
	public RSAKey loadPrivateKey(String privateKeyStr)
			throws NoSuchAlgorithmException, InvalidKeySpecException,
			IOException, NullPointerException;


	/**
	 * RSA 加密
	 * 
	 * @ReqParam input 输入参数，以字节形式
	 * @ReqParam key RSAKey
	 * @return byte[]
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 */
	public byte[] encryptWithKey(byte[] input, RSAKey key)
			throws IllegalBlockSizeException, BadPaddingException,
			InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException;

	/**
	 * RSA 解密
	 * 
	 * @ReqParam input 输入参数，以字节形式
	 * @ReqParam key RSAKey
	 * @return byte[]
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 */
	public byte[] decryptWithKey(byte[] input, RSAKey key)
			throws IllegalBlockSizeException, BadPaddingException,
			InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException;

	/**
	 * RSA 签名
	 * 
	 * @ReqParam input 输入参数，以字节形式
	 * @ReqParam key RSAKey
	 * @return byte[]
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 */
	public byte[] signWithKey(byte[] input, RSAKey key)
			throws NoSuchAlgorithmException, InvalidKeyException,
			SignatureException;

	/**
	 * 
	 * @ReqParam input
	 * @ReqParam signature
	 * @ReqParam key
	 * @return
	 * @throws SignatureException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 */
	public boolean verifyWithKey(byte[] input, byte[] signature, RSAKey key)
			throws SignatureException, InvalidKeyException,
			NoSuchAlgorithmException;

}
