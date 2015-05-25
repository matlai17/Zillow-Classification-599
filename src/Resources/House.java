package Resources;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 *
 * @author Matthew Lai and Arun Singh
 */
public class House {

	private HouseAddress houseAddress;
	private long zid;
	private int noOfBedroom;
	private double noOfBathroom;
	private double area;
	private int builtYear;
	private double pricePerSQFT;
	private double priceSold;
	private double zestimate;
	private int schoolElem;
	private int schoolMid;
	private int schoolHigh;
        
        public House()
        {
            houseAddress = null;
            zid = 0;
            noOfBedroom = 0;
            noOfBathroom = 0;
            area = 0;
            builtYear = 0;
            pricePerSQFT = 0;
            priceSold = 0;
            zestimate = 0;
            schoolElem = 0;
            schoolMid = 0;
            schoolHigh = 0;
        }
        
        public House(long zid, String street, String city, String state, String zip, double priceSold, 
                int bedrooms, double bathrooms, double area, int year, double zestimate, int elem, int mid, int high)
        {
            this.houseAddress = new HouseAddress(street, city, state, zip);
            this.zid = zid;
            this.noOfBedroom = bedrooms;
            this.noOfBathroom = bathrooms;
            this.area = area;
            this.builtYear = year;
            this.pricePerSQFT = priceSold/area;
            this.priceSold = priceSold;
            this.zestimate = zestimate;
            this.schoolElem = elem;
            this.schoolMid = mid;
            this.schoolHigh = high;
        }
        
        /**
         * getFeatures returns a Map of the features in Feature Name/Value pairs.
         * Can be iterated through to retrieve both the key and the value, i.e.
         * the feature name and the value.
         * 
         * @return a map of the features in Feature Name/Value pairs.
         */
        public java.util.TreeMap<String, Double> getFeatures()
        {
            TreeMap<String, Double> features = new TreeMap<>();
            features.put("zip", Double.parseDouble(houseAddress.getZip()));
            features.put("bedrooms", (double)noOfBedroom);
            features.put("bathrooms", noOfBathroom);
            features.put("area", area);
            features.put("age", (double)builtYear);
            features.put("elemrating", (double)schoolElem);
            features.put("middlerating", (double)schoolMid);
            features.put("highrating", (double)schoolHigh);
            return features;
        }
        
        /**
         * getFeaturesArray returns an array rather than a map of the features. The final element
         * in the array contains the price. Might be easier to use than the getFeatures method, which 
         * returns only a map of the features and requires a separate call to retrieve the sold price.
         * 
         * @return A double array containing the features.
         */
        public double[] getFeaturesArray()
        {
            return new double[]{Double.parseDouble(houseAddress.getZip()), (double)noOfBedroom,
                noOfBathroom, area, (double)builtYear, (double)schoolElem, (double)schoolMid, (double)schoolHigh, priceSold};
        }
        
        public java.util.List<String[]> getInfo()
        {
            java.util.List<String[]> info = new java.util.ArrayList<>();
            
            info.add(new String[]{"Zillow ID", "" + zid});
            info.add(new String[]{"Street Address", houseAddress.getstreetAddress()});
            info.add(new String[]{"City", houseAddress.getCity()});
            info.add(new String[]{"State", houseAddress.getState()});
            info.add(new String[]{"Zip Code", "" + houseAddress.getZip()});
//            info.add(new String[]{"Price Sold", "" + priceSold});
            info.add(new String[]{"Bedrooms", "" + noOfBedroom});
            info.add(new String[]{"Bathrooms", "" + noOfBathroom});
            info.add(new String[]{"Area", "" + area});
            info.add(new String[]{"Age", "" + builtYear});
            info.add(new String[]{"Elementary School Rating", "" + schoolElem});
            info.add(new String[]{"Middle School Rating", "" + schoolMid});
            info.add(new String[]{"High School rating", "" + schoolHigh});
            info.add(new String[]{"Zillow Zestimate", "" + zestimate});
            
            return info;
        }

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
        
        public void setHouseAddress(String streetAddress, String city, String state, String zipCode)
        {
            this.houseAddress = new HouseAddress(streetAddress, city, state, zipCode);
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
