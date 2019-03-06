package com.revature.ers.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@WebFilter("/*")
public class JwtAuthFilter implements Filter {
	
	private static Logger log = Logger.getLogger(JwtAuthFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info("JwtAuthFilter initialized");
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		log.info("Inside of JwtAuthFilter.doFilter()");
		
		// 1. Cast the generic ServletRequest/Response objects to HttpServletRequest/Response objects
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		// 2. Get the HTTP header named "Authorization"
		String header = req.getHeader(JwtConfig.HEADER);
		
		// 3. Validate the header values and check the prefix
		if(header == null || !header.startsWith(JwtConfig.PREFIX)) {
			log.info("Request originates from an unauthenticated origin");
			
			// 3.1: Mark this request as unauthenticated (limits access to endpoints)
			req.setAttribute("isAuthenticated", false);
			
			// 3.2: If there is no header, or one that we provided, then go to the next step in the filter chain (target servlet)
			chain.doFilter(req, resp);
			return;
		}
		
		// 4. Get the token
		String token = header.replaceAll(JwtConfig.PREFIX, "");
		
		try {
			
			// 5. Validate the token
			Claims claims = Jwts.parser()
					.setSigningKey(JwtConfig.signingKey)
					.parseClaimsJws(token)
					.getBody();
			
			// 6. Obtain the principal/subject stored in the JWT
			Principal principal = new Principal();
			principal.setId(claims.getId());
			principal.setRole(claims.get("role", String.class));
			
			// 7. Attach an attribute to the request indicating information about the principal
			req.setAttribute("principal", principal);
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		// 8. Send the request to the next filter in the chain (target servlet)
		chain.doFilter(req, resp);
	}

	@Override
	public void destroy() {
		log.info("JwtAuthFilter destroyed");
	}


}