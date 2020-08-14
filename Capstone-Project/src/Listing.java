
public class Listing {
	
	private String address;
	private String city;
	private String province;
	private String country;
	private String postalCode;
	private int numberOfBedrooms;
	private int numberOfBathrooms;
	private String homeType;
	private String price;
	
	
	public Listing(String address, String city, String province, String country, String postalCode, int numberOfBedrooms, int numberOfBathrooms, String homeType, String price) {
		this.address = address;
		this.city = city;
		this.province = province;
		this.country = country;
		this.postalCode = postalCode;
		this.numberOfBedrooms = numberOfBedrooms;
		this.numberOfBathrooms = numberOfBathrooms;
		this.homeType = homeType;
		this.price = price;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getPostalCode() {
		return postalCode;
	}


	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}


	public int getNumberOfBedrooms() {
		return numberOfBedrooms;
	}


	public void setNumberOfBedrooms(int numberOfBedrooms) {
		this.numberOfBedrooms = numberOfBedrooms;
	}


	public int getNumberOfBathrooms() {
		return numberOfBathrooms;
	}


	public void setNumberOfBathrooms(int numberOfBathrooms) {
		this.numberOfBathrooms = numberOfBathrooms;
	}


	public String getHomeType() {
		return homeType;
	}


	public void setHomeType(String homeType) {
		this.homeType = homeType;
	}


	public String getPrice() {
		return price;
	}


	public void setPrice(String price) {
		this.price = price;
	}
	
	
	
	
	

}
