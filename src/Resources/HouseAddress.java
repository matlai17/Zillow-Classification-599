package Resources;

/**
 * HouseAddress object to represent the address of a House Object
 * 
 * @author Matthew Lai and Arun Singh
 */
public class HouseAddress {

	private String streetAddress;
	private String city;
	private String state;
	private String zip;
        
        public HouseAddress()
        {
            streetAddress = null;
            city = null;
            state = null;
            zip = null;
        }
        
        public HouseAddress(String streetAddress, String city, String state, String zip)
        {
            this.streetAddress = streetAddress;
            this.city = city;
            this.state = state;
            this.zip = zip;
        }

	public String getstreetAddress() {
		return streetAddress;
	}

	public void setstreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
}
