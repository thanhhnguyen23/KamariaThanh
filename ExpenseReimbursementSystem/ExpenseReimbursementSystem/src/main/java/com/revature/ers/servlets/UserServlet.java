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
import com.revature.ers.models.User;
import com.revature.ers.services.UserService;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(UserServlet.class);

	private UserService userService = new UserService();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		log.info("Request received by UserServlet.doPost()");
		/////////////////////////////////////////////////////
		// checking to see if user role Id can work from here
		log.info("getting userid: 9 \n--> Does it work?" + userService.getUserById(9));
		/////////////////////////////////////////////////////

		User newUser = null;
		log.info("setting newUser null");

		ObjectMapper mapper = new ObjectMapper();
		log.info("setting up mapper object");

		try {
			log.info(req);
			log.info(mapper);
			log.info(User.class);

			newUser = mapper.readValue(req.getInputStream(), User.class);

		} catch (MismatchedInputException mie) {
			log.error(mie.getMessage());

			log.info(newUser);

			resp.setStatus(400);
			return;

		} catch (Exception e) {
			log.error(e.getMessage());
			resp.setStatus(500);
		}

		newUser = userService.addUser(newUser);
		try {

			String userJson = mapper.writeValueAsString(newUser);
			PrintWriter out = resp.getWriter();
			out.write(userJson);
		} catch (Exception e) {
			log.error(e.getMessage());
			resp.setStatus(500);
		}

	}

}
