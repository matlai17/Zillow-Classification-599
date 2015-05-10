
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

	public static void main(String[] args) {
		
		String csvFile = "data\\ZillowDataTrain.csv";
		String line = null;
		String csvDelimiter = ",";
		Map<String, House> houseMap = new HashMap<String, House>();
		List<House> houseList = new ArrayList<House>();
		try {
			BufferedReader br = new  BufferedReader(new FileReader(csvFile));
			
			while ((line = br.readLine()) != null) {
				String[] houseElements = line.split(csvDelimiter);
//No Of Bedroom	No bathroom	Area	Year Built	Lot Size	PricePerSQFT	parkingSpace	noOfStories	Zestimate

				House house = new House();
				HouseAddress address = new HouseAddress();
				address.setStreetNumber(Integer.parseInt(houseElements[0]));
				address.setStreetName(houseElements[1]);
				address.setCity(houseElements[2]);
				address.setState(houseElements[3]);
				address.setZip(houseElements[4]);
				
				house.setNoOfBedroom(Integer.parseInt(houseElements[5]));
				house.setNoOfBathroom(Integer.parseInt(houseElements[6]));
				house.setArea(Double.parseDouble(houseElements[7]));
				house.setBuiltYear(Integer.parseInt(houseElements[8]));
				house.setLotSize(Integer.parseInt(houseElements[9]));
				house.setPricePerSQFT(Double.parseDouble(houseElements[10]));
				house.setParkingSpace(Integer.parseInt(houseElements[11]));
				house.setNoOfStories(Integer.parseInt(houseElements[12]));
				house.setZestimate(Double.parseDouble(houseElements[13]));
				house.setHouseAddress(address);
				
				houseList.add(house);
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
