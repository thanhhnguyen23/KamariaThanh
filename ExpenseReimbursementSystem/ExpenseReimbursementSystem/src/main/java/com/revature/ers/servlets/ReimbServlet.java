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
			log.info(req);
			log.info(mapper);
			log.info(Reimbursement.class);
			/////////////////////////////////////////////////////////////////////////////////////////////

			// checking out newReimb
			// 3/11/18
			// Can not deserialize value of type int from String "bwayne": not a valid Integer value
			log.info(newReimb); // currently null
			/////////////////////////////////////////////////////////////////////////////////////////////
			
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


}
