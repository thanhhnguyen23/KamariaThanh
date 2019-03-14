package com.revature.ers.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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

@WebServlet("/reimbursements")
public class ReimbServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(ReimbServlet.class);

	private ReimbursementService reimbService = new ReimbursementService();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		log.info("request received by ReimbServlet.doPost()");
		
		Reimbursement newReimb = null;
		
		log.info("setting new reimbursement to null");
		
		ObjectMapper mapper = new ObjectMapper();
		log.info("setting up mapper object");

		try {
			log.info("mapper: " + mapper);
			log.info(Reimbursement.class);
			log.info(newReimb); 
			
			log.info(ReimbServlet.class);
			
			newReimb = mapper.readValue(req.getInputStream(), Reimbursement.class);
			log.info("newReimb: " + newReimb);

		}
		catch(MismatchedInputException mie) {
			log.error(mie.getMessage());
			log.info(newReimb);
			
			resp.setStatus(400);
			return;
		}
		catch(Exception e) {
			log.error(e.getMessage());
			resp.setStatus(500);
		}
		
		newReimb = reimbService.addReimbursement(newReimb);

		try {
			String reimbJson = mapper.writeValueAsString(newReimb);
			PrintWriter out = resp.getWriter();
			out.write(reimbJson);
		}
		catch(Exception e) {
			log.error(e.getMessage());
			resp.setStatus(500);
		}
		
	}
	// getting all reimbursements
	@Override 
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{

////////////////////////////////////////////////////////////////////////////////////
// testing getting all reimbursements by authorId using "getting all reimbursements
// hardcoding int
////////////////////////////////////////////////////////////////////////////////////
// 		currently working, but servlet must accept arguments of type int representing authorid
//		log.info("request sent to getting all reimbursements by AuthorId");
//
//
//		List<Reimbursement> reimbursementByAuthorId = reimbService.getReimbByAuthorId(48);
//		log.info("getting all reimbursements by AuthorId" + reimbursementByAuthorId);
//
//		if(reimbursementByAuthorId.isEmpty()){
//			log.info("There are no reimbursements by this authorId");
//			resp.setStatus(400);
//			return;
//		}
//		resp.setStatus(200);
//		
//		PrintWriter pw = resp.getWriter();
//		ObjectMapper mapper = new ObjectMapper();
//		String response = mapper.writeValueAsString(reimbursementByAuthorId);
//				
//		
//		resp.setContentType("application/json");
//		pw.write(response);
////////////////////////////////////////////////////////////////////////////////
		
		
///////////////////////////////////////////////////////////////////////////////////////
		log.info("Request sent to get all reimbursements");
		List<Reimbursement> allReimbursements = reimbService.getAllReimbursements();
		log.info("getting all reimbursements: " + allReimbursements);
		
		if(allReimbursements.isEmpty()) {
			log.info("Reimbursement List is empty");
			
			resp.setStatus(400);
			return;
		}
		resp.setStatus(200);
		
		// send json object back to client that requested info
		PrintWriter pw = resp.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		String response = mapper.writeValueAsString(allReimbursements);
		
		resp.setContentType("application/json");
		pw.write(response);
///////////////////////////////////////////////////////////////////////////////////////
	
		//////////////////////////////////////////
		// need to get reimbursements by authorId
		// services/daos are already in place
		//////////////////////////////////////////
	}
}
