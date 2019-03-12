package com.revature.ers.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.revature.ers.models.Principal;
import com.revature.ers.util.JwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

//////////////////////////////////////////////////////////////////////
// taking out filters and all related files to test login/registration
///////////////////////////////////////////////////////////////////////


//@WebFilter("/*")
public class JwtFilter extends HttpFilter {
	
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(JwtFilter.class);

	@Override
	public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
		log.info("Inside of JwtFilter.doFilter()");
		
		// 2. Get the HTTP header named "Authorization"
		// header is not receiving the "Authorization" as the header
		// however, the JwtConfig.HEADER & JwtConfig.PREFIX does its proper values
		
		String header = req.getHeader(JwtConfig.HEADER);
//		String header = JwtConfig.HEADER; // testing
		log.info("header: " + header);
		log.info("header: " + JwtConfig.HEADER);
		log.info("prefix: " + JwtConfig.PREFIX);
		
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
		log.info("header: " + header);
		String token = header.replaceAll(JwtConfig.PREFIX, "");
		log.info("token: " + token);
		
		try {
			
			// 5. Validate the token
			Claims claims = Jwts.parser()
					.setSigningKey(JwtConfig.signingKey)
					.parseClaimsJws(token)
					.getBody();
			
			// 6. Obtain the principal/subject stored in the JWT
//			Principal principal = new Principal();
			Principal principal = new Principal(
				Integer.parseInt(claims.getId()),
				claims.get("username", String.class),
				////////////////////////////////
				//TODO -- get role isn't working

				claims.get("role", String.class)
				////////////////////////////////
			);

//			principal.setId(Integer.parseInt(claims.getId()));
//			// role & username is still null
//			
//			principal.setRole(claims.get("role", String.class));
//			principal.setUsername(claims.get("username", String.class));
			
			log.info("principal.getId(): " + principal.getId());
			log.info("principal.getRole(): " + principal.getRole());
			log.info("principal.getUsername(): " + principal.getUsername());
			
			log.info("obtaining the principal/subject stored in JWT");
			
			// 7. Attach an attribute to the request indicating information about the principal
			req.setAttribute("principal", principal);
			/////////////////////////////////////////////////
			//TODO -- check back why role & username is null
			/////////////////////////////////////////////////
			log.info("setting attribute in prinicipal: " + req.getAttribute("principal"));
			
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		
		// 8. Send the request to the next filter in the chain (target servlet)
		chain.doFilter(req, resp);
	}

}
