package com.chitfund.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

public class JwtTokenFilter extends GenericFilterBean {

	private JwtTokenProvider jwtTokenProvider;

	public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
		new ObjectMapper();
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
			throws IOException, ServletException {
		String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);

		try {
			if (token != null && jwtTokenProvider.validateToken(token)) {
				Authentication auth = jwtTokenProvider.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
			filterChain.doFilter(req, res); // Proceed with the filter chain
		} catch (ExpiredJwtException ex) {
			handleException((HttpServletResponse) res, "Token is expired", HttpServletResponse.SC_UNAUTHORIZED);
		} catch (JwtException ex) {
			handleException((HttpServletResponse) res, "Token is invalid, please check username/password",
					HttpServletResponse.SC_UNAUTHORIZED);
		} catch (Exception e) {
			handleException((HttpServletResponse) res, e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	private void handleException(HttpServletResponse response, String message, int statusCode) throws IOException {
		if (!response.isCommitted()) {
			String errorResponse = String.format("{\"statusCode\": %d, \"message\": \"%s\"}", statusCode, message);
			response.setStatus(statusCode);

			if (response.getWriter() != null) {
				response.getWriter().write(errorResponse);
			}

			response.getWriter().flush(); // Flush to ensure the response is sent
		}
	}

}
