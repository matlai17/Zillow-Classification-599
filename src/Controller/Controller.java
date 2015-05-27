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

		double[] categories = Classifier.generatePriceRanges(allHouses, 125,
				true);
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
		System.out
				.println("**************************NaiveBayes**************************");
		double averageCatDifference = 0;
		int numberTrue = 0;
		int maxDistance = 0;

		for (House h : testHouses) {
			double[] NBPrediction = NB.predict(h);
			double avg = NBPrediction[0];
			if (NBPrediction.length > 1)
				avg = (avg + NBPrediction[1]) / 2;

			int determinedCat = NB.determineCategory(avg);
			int trueCat = NB.determineCategory(h.getPriceSold());
			averageCatDifference += Math.abs(determinedCat - trueCat);
			if (Math.abs(determinedCat - trueCat) > maxDistance)
				maxDistance = Math.abs(determinedCat - trueCat);
			if (determinedCat == trueCat)
				numberTrue++;

			System.out.print("Price Sold: " + h.getPriceSold() + "\t"
					+ "Predicted: $" + NBPrediction[0] + " - ");
			if (NBPrediction.length > 1)
				System.out.println("$" + NBPrediction[1]);

		}

		System.out.println("Average Difference in Category: "
				+ averageCatDifference / testHouses.size());
		System.out.println("Absolute Correctness: " + numberTrue + "/"
				+ testHouses.size() + " = " + (double) numberTrue
				/ (double) testHouses.size());
		System.out.println("Maximum Difference in Category: " + maxDistance);

		System.out
				.println("**************************ANN**************************");
		// TODO Need to figure our better way to do it double

		int averageCatDifference_ANN = 0;
		int numberTrue_ANN = 0;
		int maxDistance_ANN = 0;
		for (House h : testHouses) {
			double[] ANNPrediction = ANN.predict(h);
			double avg = ANNPrediction[0];
			if (ANNPrediction.length > 1)
				avg = (avg + ANNPrediction[1]) / 2;

			int determinedCat = ANN.determineCategory(avg);
			int trueCat = ANN.determineCategory(h.getPriceSold());
			averageCatDifference_ANN += Math.abs(determinedCat - trueCat);
			if (Math.abs(determinedCat - trueCat) > maxDistance_ANN)
				maxDistance_ANN = Math.abs(determinedCat - trueCat);
			if (determinedCat == trueCat)
				numberTrue_ANN++;

			System.out.print("Price Sold: " + h.getPriceSold() + "\t"
					+ "Predicted: $" + ANNPrediction[0] + " - ");
			if (ANNPrediction.length > 1)
				System.out.println("$" + ANNPrediction[1]);
		}

		System.out.println("Average Difference in Category: "
				+ averageCatDifference_ANN / testHouses.size());
		System.out.println("Absolute Correctness: " + numberTrue_ANN + "/"
				+ testHouses.size() + " = " + (double) numberTrue_ANN
				/ (double) testHouses.size());
		System.out
				.println("Maximum Difference in Category: " + maxDistance_ANN);

	}

	private void partitionHouses(List<House> allHouses) {
		trainingHouses = new ArrayList<>();
		testHouses = new ArrayList<>();

		int numTestHouses = allHouses.size() / 10;
		Random r = new Random();
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
