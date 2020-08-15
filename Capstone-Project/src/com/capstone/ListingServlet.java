package com.capstone;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListingServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String address = req.getParameter("address");
		String city = req.getParameter("city");
		String province = req.getParameter("province");
		String country = req.getParameter("country");
		String postalCode = req.getParameter("postalCode");
		String numberOfBedroomsStr = req.getParameter("numberOfBedrooms");
		String numberOfBathroomsStr = req.getParameter("numberOfBathrooms");
		String homeType = req.getParameter("homeType");
		String price = req.getParameter("price");
		
		int numberOfBedrooms = 0;
		try {
			numberOfBedrooms = Integer.valueOf(numberOfBedroomsStr);
		} catch (Exception e) {
		}
		
		
		int numberOfBathrooms = 0;
		try {
			numberOfBathrooms = Integer.valueOf(numberOfBathroomsStr);
		} catch (Exception e) {
		}
		
		Database database = new Database();
		database.addListingToDatabase(new Listing(address, city, province, country, postalCode, numberOfBedrooms, numberOfBathrooms, homeType, price));
		
		req.setAttribute("listings", database.retrieveListings());
		req.getRequestDispatcher("home.jsp").forward(req, resp);		
	}
	
	@Override
	protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {	
		Database database = new Database();		
		req.setAttribute("listings", database.retrieveListings());
		req.getRequestDispatcher("home.jsp").forward(req, resp);	
	}

}
