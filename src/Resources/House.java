
package Resources;

/**
 *
 * @author Matthew Lai and Arun Singh
 */
public class House {

	HouseAddress houseAddress = null;
    private int noOfBedroom  = 0;
    private int noOfBathroom = 0; 
    private double area  = 0;
    private int builtYear = 0;
    private double LotSize = 0;
    private double  pricePerSQFT = 0;
    private int parkingSpace = 0;
    private int noOfStories = 0;
	private double zestimate = 0;
    
    
    
	public HouseAddress getHouseAddress() {
		return houseAddress;
	}
	public void setHouseAddress(HouseAddress houseAddress) {
		this.houseAddress = houseAddress;
	}
	public int getNoOfBedroom() {
		return noOfBedroom;
	}
	public void setNoOfBedroom(int noOfBedroom) {
		this.noOfBedroom = noOfBedroom;
	}
	public int getNoOfBathroom() {
		return noOfBathroom;
	}
	public void setNoOfBathroom(int noOfBathroom) {
		this.noOfBathroom = noOfBathroom;
	}
	public double getArea() {
		return area;
	}
	public void setArea(double area) {
		this.area = area;
	}
	public int getBuiltYear() {
		return builtYear;
	}
	public void setBuiltYear(int builtYear) {
		this.builtYear = builtYear;
	}
	public double getLotSize() {
		return LotSize;
	}
	public void setLotSize(double lotSize) {
		LotSize = lotSize;
	}
	public double getPricePerSQFT() {
		return pricePerSQFT;
	}
	public void setPricePerSQFT(double pricePerSQFT) {
		this.pricePerSQFT = pricePerSQFT;
	}
	public int getParkingSpace() {
		return parkingSpace;
	}
	public void setParkingSpace(int parkingSpace) {
		this.parkingSpace = parkingSpace;
	}
	public int getNoOfStories() {
		return noOfStories;
	}
	public void setNoOfStories(int noOfStories) {
		this.noOfStories = noOfStories;
	}
	public double getZestimate() {
		return zestimate;
	}
	public void setZestimate(double zestimate) {
		this.zestimate = zestimate;
	}      
        
        // Temporary variable and method for my own use in other methods. Feel free to remove if you have a replacement. I will fix my code later.
        double priceSold;
        public double getSoldPrice(){ return priceSold; }
        public void setSoldPrice(double p){ priceSold = p; }
}
