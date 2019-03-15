package com.revature.ers.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.revature.ers.models.Principal;
import com.revature.ers.models.User;
import com.revature.ers.services.UserService;
import com.revature.ers.util.JwtConfig;
import com.revature.ers.util.JwtGenerator;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(AuthServlet.class);
	
	private UserService userService = new UserService();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		ObjectMapper mapper = new ObjectMapper();
		String[] credentials = null;
		PrintWriter out = null;
		
		try {
			out = resp.getWriter();
			credentials = mapper.readValue(req.getInputStream(), String[].class);

		} catch (MismatchedInputException mie) {
			log.error(mie.getMessage());
			resp.setStatus(400);
			return;
		}
		
		if(credentials != null && credentials.length != 2) {
			log.warn("Invalid request, unexpected number of credential values");
			resp.setStatus(400);
			return;
		}
		
		User user = userService.getUserByCredentials(credentials[0], credentials[1]);
		
		if(user == null) {
			resp.setStatus(401);
			return;
		}
		// attempting to retrieve role and rolename
//		Principal principal = new Principal((user.getRoleId(), user.getUsername());
		Principal principal = new Principal(Integer.toString(user.getRoleId()), user.getUsername());
		out.write(mapper.writeValueAsString(principal));
		
		resp.setStatus(200);
		resp.addHeader(JwtConfig.HEADER, JwtConfig.PREFIX + JwtGenerator.createJwt(user));
	}
}