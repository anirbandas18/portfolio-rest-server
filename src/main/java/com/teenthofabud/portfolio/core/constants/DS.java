package com.teenthofabud.portfolio.core.constants;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.Principal;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.ServerHttpAsyncRequestControl;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;

public class DS implements ServerHttpRequest {

	public DS() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public HttpMethod getMethod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI getURI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpHeaders getHeaders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getBody() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Principal getPrincipal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InetSocketAddress getLocalAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InetSocketAddress getRemoteAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServerHttpAsyncRequestControl getAsyncRequestControl(ServerHttpResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

}
