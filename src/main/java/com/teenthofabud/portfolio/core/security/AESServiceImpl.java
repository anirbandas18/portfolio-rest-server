package com.teenthofabud.portfolio.core.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;


@Component
public class AESServiceImpl implements CryptoService {
	
	private static final Logger LOG = LoggerFactory.getLogger(AESServiceImpl.class);
	
	private Cipher cipher;
	
	private String encryptionAlgorithm;
	
	@PostConstruct
	private void init() throws NoSuchAlgorithmException, NoSuchPaddingException {
		this.encryptionAlgorithm = "AES";
		this.cipher = Cipher.getInstance(encryptionAlgorithm);
	}

	@Override
	public String encrypt(String key, String data) {
		// TODO Auto-generated method stub
		String encodedEncryption = "";
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), encryptionAlgorithm);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] rawEncryption = cipher.doFinal(data.getBytes());
			encodedEncryption = Base64Utils.encodeToString(rawEncryption);
			return encodedEncryption;
		} catch (UnsupportedEncodingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			LOG.error("Encryption error: {}", e);
		}
		return encodedEncryption;
	}

	@Override
	public String decrypt(String key, String data) {
		// TODO Auto-generated method stub
		String decoded = "";
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), encryptionAlgorithm);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] rawEncryption = Base64Utils.decodeFromString(data);
			byte[] rawDecryption = cipher.doFinal(rawEncryption);
			decoded = new String(rawDecryption);
			return decoded;
		} catch (UnsupportedEncodingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			LOG.error("Decryption error: {}", e);
		}
		return decoded;
	}

}
