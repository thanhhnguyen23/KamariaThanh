package com.revature.ers.util;

import javax.servlet.http.HttpServletRequest;

import com.revature.ers.models.Principal;

public class RequestViewHelper {

	private RequestViewHelper() {
		super();
	}
	
	public static String process(HttpServletRequest request) {
		
		switch(request.getRequestURI()) {
		
		case "/ExpenseReimbursementSystem/login.view":
			return "partials/login.html"; //TODO -- need to create partials folder with html files (login, register, and dashboard)
		
		case "/ExpenseReimbursementSystem/register.view":
			return "partials/register.html";
		
		case "/ExpenseReimbursementSystem/dashboard.view":
			Principal principal = (Principal) request.getAttribute("principal");
			if(principal.getRole() == "ADMIN") {
				return "partials/admin-dash.html"; // admin/employee will have their dashboard
			}
			return "partials/dashboard.html";
			
		// reimbursements
		//TODO -- KD -- Reimbursements View 

		case "/ExpenseReimbursementSystem/new-reimb.view":
			principal = (Principal) request.getAttribute("principal");
			// principal.getRole() may not be functioning while retrieving new reimbursement screen

//			if(principal.getRole() == "USER" || principal.getRole() == "ADMIN") {
			return "partials/new-reimb.html"; 
//			}
//			return "partials/dashboard.html";
		
		default: 
			return null;
		
		}
	}

}
