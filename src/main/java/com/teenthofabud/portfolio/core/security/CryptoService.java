package com.teenthofabud.portfolio.core.security;

import org.springframework.stereotype.Service;


@Service
public interface CryptoService {

	public String encrypt(String key, String data);
	
	public String decrypt(String key, String data);
	
}
