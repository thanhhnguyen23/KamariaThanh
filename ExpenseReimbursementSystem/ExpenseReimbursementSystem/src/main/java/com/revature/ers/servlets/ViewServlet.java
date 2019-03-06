package com.revature.ers.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.revature.ers.util.RequestViewHelper;

public class ViewServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(ViewServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		log.debug("Request sent to front controller, ViewServlet.doGet()");
		
		String nextView = RequestViewHelper.process(req);
		log.debug("Navigating to: " + nextView);
		
		if(nextView != null) {
			req.getRequestDispatcher(nextView).forward(req, resp);
		} else {
			resp.setStatus(401);
		}
	}
}