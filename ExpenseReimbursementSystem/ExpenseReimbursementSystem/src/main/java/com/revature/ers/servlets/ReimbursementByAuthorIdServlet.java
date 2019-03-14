package com.revature.ers.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.revature.ers.services.ReimbursementService;

@WebServlet("/reimbById")
public class ReimbursementByAuthorIdServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(ReimbursementByAuthorIdServlet.class);
	
	private ReimbursementService reimbService = new ReimbursementService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		log.info("request received by ReimbursementByAutorIdServlet.doGet()");
		//TODO -- reimbByAuthorId
		// getting reimbursements by authorId

//		List<User> users = userService.getAllUsers();
//		String usersJSON = mapper.writeValueAsString(users);
//		resp.setStatus(200);
//		out.write(usersJSON);
		
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        List<Reimbursement> reimbursement = reimService.getByAuthor(Integer.parseInt(principal.getId()));
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


		
		
		
		//TODO -- getting all reimbursements by authorId 
		// making reimbursement by author id
//		List<Reimbursement> reimbByAuthorId = reimbService.getReimbursementById(reimbId)

	}
	

}
