package com.teenthofabud.portfolio.handler;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class ResponseHandler extends OncePerRequestFilter {

	@Value("${freelancer.avatar.uri.regex}")
	private String freelancerAvatarURIRegex;
	@Value("${freelancer.resume.uri.regex}")
	private String freelancerResumeURIRegex;
	@Autowired
	private PathMatcher pathMatcher;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String uri = request.getRequestURI();
		if(!pathMatcher.match(freelancerAvatarURIRegex, uri) && !pathMatcher.match(freelancerResumeURIRegex, uri)) {
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setDateHeader(HttpHeaders.TRANSFER_ENCODING, System.currentTimeMillis());
		}
		filterChain.doFilter(request, response);
	}

}
