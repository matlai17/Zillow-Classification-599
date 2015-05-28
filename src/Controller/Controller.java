package Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import Classifiers.ANN;
import Classifiers.Classifier;
import Classifiers.NaiveBayes;
import Classifiers.RandomForest;
import Parser.ZillowParser;
import Resources.House;

/**
 * The main Controller class that will initialize and train the different
 * classification classes. It contains useful methods that can be used in the
 * process of training and testing the classification classes.
 *
 * @author Matthew Lai and Arun Singh
 */
public class Controller {

	List<House> trainingHouses;
	List<House> testHouses;
	ANN ANN;
	NaiveBayes NB;
	RandomForest RF;

	/**
	 * Constructor will initialize the classification objects and train them
	 * using the data that can be retrieved from the ZillowParser. The
	 * classification objects will then each be evaluated so that the different
	 * methods can be compared.
	 * 
	 */
	public Controller() {
		List<House> allHouses = ZillowParser
				.parseZillowDataFile(new java.io.File(
						"data\\ZillowDataTrain.csv"));
		partitionHouses(allHouses);

		double[] categories = Classifier.generatePriceRanges(allHouses, 50,
				false);
		// Dynamic category sizes increases accuracy with fewer number of
		// categories when using Naive-Bayes. My guess is that the outlier
		// categories are having a negative effect on the prediction model for
		// NB.
		ANN = new ANN(categories);
		NB = new NaiveBayes(categories);
		RF = new RandomForest(categories);

		ExecutorService e = Executors.newFixedThreadPool(3);
		e.submit(new TrainingThread(trainingHouses, ANN));
		e.submit(new TrainingThread(trainingHouses, NB));
		// e.submit(new TrainingThread(trainingHouses, RF));

		e.shutdown();

		try {
			e.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException ex) {
		}

		// NB.printCatDetails();
		double averageCatDifference = 0;
		int numberTrue = 0;
		int maxDistance = 0;

		for (House h : testHouses) {
			// System.out.print("House Address: " + h.getHouseCompleteAddress()
			// + "\t");
			System.out.print("Price Sold: " + h.getPriceSold() + "\t");

			int determinedCat_NB = NB.determineCategory(logs(h, NB.predict(h)));
			int determinedCat_ANN = ANN.determineCategory(logs(h,
					ANN.predict(h)));
			System.out.print("Predict ANN :" + logs(h, ANN.predict(h)) + "\t");
			System.out.print("Predict NB :" + logs(h, NB.predict(h)) + "\t");
			System.out.print("Zestimate :" + h.getZestimate() + "\t");
			System.out.print("Difference  :"
					+ (h.getZestimate() - logs(h, ANN.predict(h))) + "\n");
			int trueCat = NB.determineCategory(h.getPriceSold());
			// int trueCat_ANN = ANN.determineCategory(h.getPriceSold());

			averageCatDifference += Math.abs(determinedCat_ANN - trueCat);
			if (Math.abs(determinedCat_ANN - trueCat) > maxDistance)
				maxDistance = Math.abs(determinedCat_ANN - trueCat);
			if (determinedCat_ANN == trueCat)
				numberTrue++;

		}
		// Below Analysis is for ANN we can do similar for other classifier
		System.out.println("Average Difference in Category: "
				+ averageCatDifference / testHouses.size());
		System.out.println("Absolute Correctness: " + numberTrue + "/"
				+ testHouses.size() + " = " + (double) numberTrue
				/ (double) testHouses.size());
		System.out.println("Maximum Difference in Category: " + maxDistance);

	}

	public double logs(House h, double[] prediction) {
		double avg = prediction[0];
		if (prediction.length > 1)
			avg = (avg + prediction[1]) / 2;
		return avg;
	}

	private void partitionHouses(List<House> allHouses) {
		trainingHouses = new ArrayList<>();
		testHouses = new ArrayList<>();

		int numTestHouses = allHouses.size() / 10;
		Random r = new Random(0); // Random r = new Random(13);
		java.util.HashSet<Integer> bag = new java.util.HashSet<>();

		while (bag.size() < numTestHouses)
			bag.add(r.nextInt(allHouses.size()));

		for (int i = 0; i < allHouses.size(); i++)
			if (bag.contains(i))
				testHouses.add(allHouses.get(i));
			else
				trainingHouses.add(allHouses.get(i));
	}

	public static void main(String[] args) {
		new Controller();
	}

}
