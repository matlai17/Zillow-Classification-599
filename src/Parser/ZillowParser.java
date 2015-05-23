package Parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Resources.House;
import Resources.HouseAddress;

/**
 *
 * @author Matthew Lai and Arun Singh
 */
public class ZillowParser {

	public static void main(String[] args) throws Exception {

		String csvFile = "src\\data\\ZillowDataTrain.csv";
		String line = null;
		String csvDelimiter = ",";
		Map<String, House> houseMap = new HashMap<String, House>();
		List<House> houseList = new ArrayList<House>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(csvFile));

			while ((line = br.readLine()) != null) {
				String[] houseElements = line.split(csvDelimiter);
				// Below is the order for features in ZillowDataTrain
				// ZId,StreetAddress,City,State,ZipCode,SoldPrice,Bedroom,Bathroom,Area,Age,Zestimate,SchoolElem,SchoolMiddle,SchoolHigh
				House house = new House();
				HouseAddress address = new HouseAddress();
				house.setZid(Long.parseLong(houseElements[0]));
				address.setstreetAddress(houseElements[1]);
				address.setCity(houseElements[2]);
				address.setState(houseElements[3]);
				address.setZip(houseElements[4]);
				house.setPriceSold(Double.parseDouble(houseElements[5]));
				house.setNoOfBedroom(Integer.parseInt(houseElements[6]));
				house.setNoOfBathroom(Double.parseDouble(houseElements[7]));
				house.setArea(Double.parseDouble(houseElements[8]));
				house.setBuiltYear(Integer.parseInt(houseElements[9]));
				house.setZestimate(Double.parseDouble(houseElements[10]));
				house.setSchoolElem(Integer.parseInt(houseElements[11]));
				house.setSchoolMid(Integer.parseInt(houseElements[12]));
				house.setSchoolHigh(Integer.parseInt(houseElements[13]));
				house.setHouseAddress(address);
				houseList.add(house);

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new Exception(
					"Exception Occured in Zillow Parser. File Not Found");
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception(
					"Exception Occured in Zillow Parser. IO Exception");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Exception Occured in Zillow Parser");
		}

	}
}
