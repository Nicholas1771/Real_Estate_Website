<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>
</head>
<body>

	<div id="listing">

	<h1>List your home</h1>

	<form>
		<label for="address">Address:</label>
		<input type="text" id="address" name="address"><br>
		
		<label for="city">City:</label>
		<input type="text" id="city" name="city"><br>
		
		<label for="province">Province:</label>
		<input type="text" id="province" name="province"><br>
		
		<label for="country">Country:</label>
		<input type="text" id="country" name="country"><br>
		
		<label for="postalCode">Postal Code:</label>
		<input type="text" id="postalCode" name="postalCode"><br>
		
		<label for="numberOfBedrooms">Number of Bedrooms:</label>
		<input type="number" id="numberOfBedrooms" name="numberOfBedrooms"><br>
		
		<label for="numberOfBathrooms">Number of Bathrooms:</label>
		<input type="number" id="numberOfBathrooms" name="numberOfBathrooms"><br>
		
		<label for="homeType">Home Type:</label>
		<input type="text" id="homeType" name="homeType"><br>
		
		<label for="price">Price:</label>
		<input type="text" id="price" name="price"><br>
		
		<input type="submit" value="Create Listing">
	</form>
	
	</div>

	<div id="currentListings">
	
		<h1>Current listings</h1>
	
		<table class="table">
                    <thead>
                      <tr>
                        <th scope="col">Address</th>
                        <th scope="col">City</th>
                        <th scope="col">Province</th>
                        <th scope="col">Country</th>
                        <th scope="col">Postal Code</th>
                        <th scope="col">Number of Bedrooms</th>
                        <th scope="col">Number of Bathrooms</th>
                        <th scope="col">Home Type</th>
                        <th scope="col">Price</th>
                      </tr>
                    </thead>
                    <tbody>
                   
                   <%@ page import = "java.util.ArrayList" %>
                   <%@ page import = "com.capstone.Listing" %>
                   
                    <%
                    ArrayList<com.capstone.Listing> listings = (ArrayList<com.capstone.Listing>) request.getAttribute("listings");         

                    	for (int i = 0; i < listings.size(); i++) {
                        	String address = listings.get(i).getAddress();
                            String city = listings.get(i).getCity();
                            String province = listings.get(i).getProvince();
                            String country = listings.get(i).getCountry();
                            String postalCode = listings.get(i).getPostalCode();
                            int numberOfBedrooms = listings.get(i).getNumberOfBedrooms();
                            int numberOfBathrooms = listings.get(i).getNumberOfBathrooms();
                            String homeType = listings.get(i).getHomeType();
                            String price = listings.get(i).getPrice();
                   	%>
                    
                    <tr>
                        <th scope="row"></th>
                        <td><%=address%></td>
                        <td><%=city%></td>
                        <td><%=province%></td>
                        <td><%=country%></td>
                        <td><%=postalCode%></td>
                        <td><%=numberOfBedrooms%></td>
                        <td><%=numberOfBathrooms%></td>
                        <td><%=homeType%></td>
                        <td><%=price%></td>
                      </tr>
                      <%} %>
                    </tbody>
                  </table>
	
	</div>

</body>
</html>