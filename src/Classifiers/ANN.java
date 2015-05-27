package Classifiers;

import java.util.List;
import java.util.Random;

import Resources.House;

/**
 * An implementation of the Artificial Neural Network classification algorithm.
 *
 * @author Matthew Lai and Arun Singh
 */
public class ANN extends Classifier {

	private final String CLASSIFIER_NAME = "Artificial Neural Network";
	public double kernelProportion = 1;
	public double varianceMaxDistanceProportion = 1;
	public double learningRate = .1;
	public int nIterations = 1;
	public double variance = 0;
	public double[][] kernelSet;
	public double[][] weights;

	public ANN(int distribution) {
		super(distribution);
	}

	public ANN(double[] categories) {
		super(categories);
	}

	@Override
	public void train(List<House> houses) {
		double[] houseArray = null;
		double[] housefeature = null;
		double[][] trainingset = new double[houses.size()][9];
		int i = 0;

		for (House house : houses) {
			// TODO
			houseArray = house.getFeaturesArray();
			for (int k = 0; k < houseArray.length - 1; k++) {
				trainingset[i][k] = houseArray[k];
			}
			double truePrice = houseArray[houseArray.length - 1];
			int houseCat = determineCategory(truePrice);
			// Category cat = categories[houseCat];
			trainingset[i][houseArray.length - 1] = houseCat;
			i++;
		}
		Random rng = new Random(0);
		kernelSet = selectKernelSet(trainingset, kernelProportion, rng);
		variance = findMaxDistance(kernelSet) * varianceMaxDistanceProportion;

		weights = trainNetwork(trainingset, kernelSet, variance,
				numberOfCategories, learningRate, nIterations, rng);

	}

	public static double[][] trainNetwork(double[][] trainingSet,
			double[][] kernelSet, double variance, int nClassifications,
			double learningRate, int nIterations, Random rng) {
		int nRow = nClassifications;
		int nCols = kernelSet.length + 1;
		int nRowsTrain = trainingSet.length;
		double weights[][] = new double[nRow][nCols];
		for (int i = 0; i < nRow; i++)
			for (int j = 0; j < nCols; j++) {
				double randnum = rng.nextDouble() * 2 - 1;
				weights[i][j] = randnum;
			}
		double inputs[][] = new double[nRowsTrain][nCols];
		for (int i = 0; i < inputs.length; i++)
			inputs[i] = calcPerceptronInput(trainingSet[i], kernelSet, variance);

		int Idx = trainingSet[0].length - 1;

		// Repeat for the given number of iterations
		for (int it = 0; it < nIterations; it++) {
			// Repeat for each example in the training set
			for (int r = 0; r < nRowsTrain; r++) {
				int perceptron_classification = classifyInput(inputs[r],
						weights);
				// Repeat for each possible_classification: 0 to
				// nClassifications - 1
				for (int cl = 0; cl < nClassifications; cl++) {
					int trainVal = (trainingSet[r][Idx] == cl) ? 1 : -1;
					int outVal = (perceptron_classification == cl) ? 1 : -1;

					// Repeat for each weight of the possible_classification
					for (int wc = 0; wc < nCols; wc++) {
						weights[cl][wc] += learningRate * (trainVal - outVal)
								* inputs[r][wc];
					}
				}
			}
		}
		return weights;
	}

	public static double[][] selectKernelSet(double[][] trainingSet,
			double kernelProportion, Random rng) {
		int nRows = (int) (kernelProportion * trainingSet.length);
		int nCols = trainingSet[0].length;

		double kernelSet[][] = new double[nRows][nCols];
		for (int i = 0; i < nRows; i++)
			kernelSet[i] = trainingSet[rng.nextInt(trainingSet.length)];

		return kernelSet;
	}

	public static double findMaxDistance(double[][] kernelSet) {
		int sizeColumns = kernelSet[0].length;
		int sizeRows = kernelSet.length;
		double[] distance = new double[sizeRows];
		double maxDistance = 0;
		for (int k = 0; k < sizeRows; k++) { // iterating through the no. of
												// rows pressent in 2D array
			for (int i = 0; i < sizeRows; i++) {
				distance[i] = calcDistance(kernelSet[k], kernelSet[i]);
				if (distance[i] > maxDistance) {
					maxDistance = distance[i];
				}
			}
		}
		return maxDistance;
	}

	public static double calcDistance(double[] example1, double[] example2) {
		double sum = 0;
		for (int i = 0; i < example1.length - 1; i++) {
			sum = sum + Math.pow((example2[i] - example1[i]), 2);
		}
		return Math.sqrt(sum);
	}

	public static double[] calcPerceptronInput(double[] example,
			double[][] kernelSet, double variance) {

		double[] perceptronstage = new double[kernelSet.length + 1];
		for (int i = 0; i < kernelSet.length; i++) {
			perceptronstage[i] = calcDistance(example, kernelSet[i]);
			// applying the Gaussian function
			perceptronstage[i] = applyGaussian(perceptronstage[i], variance);
		}

		perceptronstage[kernelSet.length] = 1;

		return perceptronstage;
	}

	public static int classifyInput(double[] perceptronInput, double[][] weights) {

		double output[] = new double[weights.length];

		for (int i = 0; i < weights.length; i++) {
			output[i] = calcWeightedSum(weights[i], perceptronInput); // check
																		// this
		}

		return findMaxIndex(output);
	}

	public static double applyGaussian(double value, double variance) {

		double temp = -(Math.pow(value, 2) / (2 * variance));
		double result = Math.exp(temp);

		return result;
	}

	public static double calcWeightedSum(double[] weights, double[] inputs) {

		// Replace the line below to return result of the weighted sum.
		double sum = 0;
		for (int i = 0; i < weights.length; i++) {
			sum += inputs[i] * weights[i];
		}

		return sum;
	}

	public static int findMaxIndex(double[] values) {
		int indexMax = 0;
		for (int i = 1; i < values.length; i++) {
			double tempIndex = values[i];
			if ((tempIndex > values[indexMax])) {
				indexMax = i;
			}
		}
		// System.out.println("indexMax  :"+ indexMax);
		return indexMax;
	}

	@Override
	public double[] predict(House h) {

		double[] houseArray = null;
		int count = 0;
		int classify = 0;
		houseArray = h.getFeaturesArray();
		double[] perceptrons = new double[houseArray.length];

		perceptrons = calcPerceptronInput(houseArray, kernelSet, variance);
		int col = houseArray.length - 1;
		classify = classifyInput(perceptrons, weights);
		// if (classify == houseArray[col])
		if (classify == determineCategory(houseArray[col]))
			count++;
		return determinePriceRange(classify);
	}

	@Override
	public String getClassifierName() {
		return CLASSIFIER_NAME;
	}

}
