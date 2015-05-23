package Resources;

/**
 *
 * @author Matthew Lai and Arun Singh
 */
public class House {

	HouseAddress houseAddress = null;
	private long zid = 0;
	private int noOfBedroom = 0;
	private double noOfBathroom = 0;
	private double area = 0;
	private int builtYear = 0;
	private double pricePerSQFT = 0;
	private double priceSold = 0;
	private double zestimate = 0;
	private int schoolElem = 0;
	private int schoolMid = 0;
	private int schoolHigh = 0;

	public long getZid() {
		return zid;
	}

	public void setZid(long zid) {
		this.zid = zid;
	}

	public double getPriceSold() {
		return priceSold;
	}

	public void setPriceSold(double priceSold) {
		this.priceSold = priceSold;
	}

	public int getSchoolElem() {
		return schoolElem;
	}

	public void setSchoolElem(int schoolElem) {
		this.schoolElem = schoolElem;
	}

	public int getSchoolMid() {
		return schoolMid;
	}

	public void setSchoolMid(int schoolMid) {
		this.schoolMid = schoolMid;
	}

	public int getSchoolHigh() {
		return schoolHigh;
	}

	public void setSchoolHigh(int schoolHigh) {
		this.schoolHigh = schoolHigh;
	}

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

	public double getNoOfBathroom() {
		return noOfBathroom;
	}

	public void setNoOfBathroom(double noOfBathroom) {
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

	public double getPricePerSQFT() {
		return pricePerSQFT;
	}

	public void setPricePerSQFT(double pricePerSQFT) {
		this.pricePerSQFT = pricePerSQFT;
	}

	public double getZestimate() {
		return zestimate;
	}

	public void setZestimate(double zestimate) {
		this.zestimate = zestimate;
	}

	// Temporary variable and method for my own use in other methods. Feel free
	// to remove if you have a replacement. I will fix my code later.
	// double priceSold;

	/*
	 * public double getSoldPrice() { return priceSold; }
	 * 
	 * public void setSoldPrice(double p) { priceSold = p; }
	 */
}
