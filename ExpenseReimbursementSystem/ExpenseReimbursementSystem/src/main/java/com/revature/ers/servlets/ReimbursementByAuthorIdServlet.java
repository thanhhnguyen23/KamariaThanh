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
import com.revature.ers.services.ReimbursementService;

@WebServlet("/reimbById")
public class ReimbursementByAuthorIdServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(ReimbursementByAuthorIdServlet.class);
	
	private ReimbursementService reimbService = new ReimbursementService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		
		resp.setContentType("application/json");
		Principal principal = (Principal) req.getAttribute("principal");

		String requestURI = req.getRequestURI();
		
		ObjectMapper map = new ObjectMapper();
		
		try {
			PrintWriter out = resp.getWriter();
			if (principal == null) {
				log.info("no principal found on request");
				resp.setStatus(401);
				return;
			}
			List<Reimbursement> reimbs = new ArrayList<>();
			
			log.info("inside our ReimbursementByAuthorServlet" + "current Id: " + principal.getId());
			reimbs = reimbService.getReimbByAuthorId(principal.getId());

			log.info("reimbursements: " + reimbs);
			if(!reimbs.isEmpty() && reimbs != null) {
				out.write(map.writeValueAsString(reimbs));
				resp.setStatus(200);
			}
		}
		catch(MismatchedInputException mie) {
			log.error(mie.getMessage());
		}
		catch(Exception e) {
			log.error(e.getMessage());
		}

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		log.info("request received by ReimbursementByAuthorIdServlet.doGet()");
//
//		log.info("request sent to getting all reimbursements by AuthorId");
//
//
//		// map javascript to java datastructure, specifically authorId
//		ObjectMapper mapper = new ObjectMapper();
//		log.info("setting up mapper object for AuthorId");
//
//		Integer reimbId = null;
//
//		try {
//			log.info("getting request: " + req);
//			log.info("mapper: " + mapper);
//			
//			reimbId = mapper.readValue(req.getInputStream(), Integer.class); // reimbId as integer
//		
//
//		List<Reimbursement> reimbursementByAuthorId = reimbService.getReimbByAuthorId(reimbId); // need to parse AuthorId // currently authorId: 48
//		log.info("getting all reimbursements by AuthorId" + reimbursementByAuthorId);
//
//		if(reimbursementByAuthorId.isEmpty()){
//			log.info("There are no reimbursements by this authorId");
//			resp.setStatus(400);
//			return;
//		}
//		resp.setStatus(200);
//		
//		}
//		catch(MismatchedInputException mie) {
//			log.error(mie.getMessage());
//			log.info(reimbId);
//			
//			resp.setStatus(400);
//			return;
//		}
//		catch(Exception e) {
//			log.error(e.getMessage());
//			resp.setStatus(500);
//		}
//		// return list of reimbursements by AuthorId
//		
//		reimbId = reimbService.getReimbByAuthorId();
//
		}

}
