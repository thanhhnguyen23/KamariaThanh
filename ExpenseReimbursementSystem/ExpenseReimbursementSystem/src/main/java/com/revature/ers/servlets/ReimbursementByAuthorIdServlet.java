package com.revature.ers.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.revature.ers.models.Principal;
import com.revature.ers.models.Reimbursement;
import com.revature.ers.models.User;
import com.revature.ers.services.ReimbursementService;
import com.revature.ers.services.UserService;

@WebServlet("/reimbById")
public class ReimbursementByAuthorIdServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(ReimbursementByAuthorIdServlet.class);

	private ReimbursementService reimbService = new ReimbursementService();
	private UserService userService = new UserService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		log.info("request sent to getting all reimbursements by AuthorId");

		resp.setContentType("application/json");

		Principal principal = (Principal) req.getAttribute("principal");
//		String requestURI = req.getRequestURI();
		ObjectMapper mapper = new ObjectMapper();

		try {

			log.info("setting up mapper object for AuthorId");

			PrintWriter out = resp.getWriter();
			
			if(principal == null) {
				log.warn("no principal on request");
				resp.setStatus(401);
				return; 
			}
			List<Reimbursement> reimb = reimbService.getReimbByAuthorId(Integer.parseInt(principal.getId()));
			
			String reimbJson = mapper.writeValueAsString(reimb);
			
			resp.setStatus(200);
			out.write(reimbJson);
		} 
		catch(NumberFormatException nfe) {
			log.error(nfe.getMessage());
			resp.setStatus(400);
		}
		catch (MismatchedInputException mie) {
			log.error(mie.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}
