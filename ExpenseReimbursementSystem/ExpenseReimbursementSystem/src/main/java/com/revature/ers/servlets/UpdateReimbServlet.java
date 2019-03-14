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
import com.revature.ers.models.Reimbursement;
import com.revature.ers.services.ReimbursementService;

@WebServlet("/updateReimbStatus")
public class UpdateReimbServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(UpdateReimbServlet.class);
	
	private ReimbursementService reimbService = new ReimbursementService();

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		log.info("request received by UpdateReimbServlet.doPost()");
		
		Reimbursement updatedReimbursement = null;
		
		log.info("setting up updatedReimbursement to null");
		
		ObjectMapper mapper = new ObjectMapper();
		log.info("setting mapper object");
		
		
		try {
			log.info("mapper: " + mapper);
			log.info(UpdateReimbServlet.class);
			log.info(updatedReimbursement); // should null at this point
			
			
			updatedReimbursement = mapper.readValue(req.getInputStream(), Reimbursement.class);
			log.info("updatedReimbursement: " + updatedReimbursement);
		}
		catch(MismatchedInputException mie) {
			log.error(mie.getMessage());
			log.info(updatedReimbursement);
			
			resp.setStatus(400);
			return;
		}
		catch(Exception e) {
			log.error(e.getMessage());
			resp.setStatus(500);
		}
		updatedReimbursement = reimbService.updateReimbStatus(updatedReimbursement);
		try {
			String reimbStatusJson = mapper.writeValueAsString(updatedReimbursement);
			PrintWriter out = resp.getWriter();
			out.write(reimbStatusJson);
		}
		catch(Exception e) {
			log.error(e.getMessage());
			resp.setStatus(500);
		}
	}
}

