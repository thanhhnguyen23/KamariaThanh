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

		ObjectMapper mapper = new ObjectMapper();
		log.info("setting up mapper object for AuthorId");

		Integer reimbId = null;

//		try {
//			log.info("getting request: " + req);
//			log.info("mapper: " + mapper);
//			
//			reimbId = mapper.readValue(req.getInputStream(), Integer.class); // reimbId as integer
//		
//
//		List<Reimbursement> reimbursementsByAuthorId = reimbService.getReimbByAuthorId(reimbId); // need to parse AuthorId // currently authorId: 48
//		log.info("getting all reimbursements by AuthorId" + reimbursementsByAuthorId);
//
//		if(reimbursementsByAuthorId.isEmpty()){
//			log.info("There are no reimbursements by this authorId");
//			resp.setStatus(400);
//			return;
//		}
//		
//		resp.setContentType("application/json");

		try {
			PrintWriter out = resp.getWriter();
			Principal principal = (Principal) req.getAttribute("principal");

			if (principal == null) {
				log.info("no principal found on request");
				resp.setStatus(401);
				return;
			}
			List<Reimbursement> reimbs = new ArrayList<>();

			log.info("inside our ReimbursementByAuthorServlet" + "current Id: " + principal.getId());
			reimbs = reimbService.getReimbByAuthorId(Integer.parseInt((principal.getId())));

			log.info("reimbursements: " + reimbs);
			if (!reimbs.isEmpty() && reimbs != null) {

				resp.setHeader("Content-Type", "application/json");
				resp.setHeader("userId", principal.getId());
				
				out.write(mapper.writeValueAsString(reimbs));
				resp.setStatus(200);
			}

		} catch (MismatchedInputException mie) {
			log.error(mie.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}
}
