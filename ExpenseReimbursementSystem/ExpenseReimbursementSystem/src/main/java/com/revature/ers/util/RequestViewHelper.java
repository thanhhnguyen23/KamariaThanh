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
			if(principal == null) return null;
			return "partials/dashboard.html";
		
		default: 
			return null;
		
		}
	}

}
