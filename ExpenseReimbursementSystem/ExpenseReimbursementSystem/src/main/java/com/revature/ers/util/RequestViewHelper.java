package com.revature.ers.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.revature.ers.models.Principal;
import com.revature.ers.models.User;
import com.revature.ers.services.UserService;

public class RequestViewHelper {

	private static UserService userService = new UserService();

	private RequestViewHelper() {
		super();
	}

	public static String process(HttpServletRequest request) {

		switch (request.getRequestURI()) {

		case "/ExpenseReimbursementSystem/login.view":
			return "partials/login.html";   

		case "/ExpenseReimbursementSystem/register.view":
			return "partials/register.html";

		case "/ExpenseReimbursementSystem/dashboard.view":
			return "partials/dashboard.html";

		case "/ExpenseReimbursementSystem/admin-dashboard.view":
			return "partials/admin-dash.html";


		case "/ExpenseReimbursementSystem/new-reimb.view":
//			principal = (Principal) request.getAttribute("principal");
			return "partials/new-reimb.html"; 

		case "/ExpenseReimbursementSystem/view-reimb.view":
//			principal = (Principal) request.getAttribute("principal");
			return "partials/view-reimb.html";

		default:
			return null;

		}
	}

}
