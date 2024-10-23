package com.kata.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class TokenValidacion {

//	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String authHeader = httpRequest.getHeader("Authorization");
		System.out.println("VERIFICA CABECERA --> " + authHeader + " || " + (authHeader == null) + " || " + (!isValidToken(authHeader)));
		if (authHeader == null || !isValidToken(authHeader)) {
//			throw new ServletException("Token no válido o ausente");
		}
//		chain.doFilter(request, response);
	}
	
	private boolean isValidToken(String token) {
		return "token_authorization".equals(token);
	}

}
