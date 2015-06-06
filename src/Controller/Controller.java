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

    public ArrayList<String[]> res;
    public String houseValue_ANN;
    public String houseValue_NB;
    public String houseValue_RF;
    
    String[] dreamHouse;
    
    private List<House> trainingHouses;
    private List<House> testHouses;
    private boolean singleHouse;
    private ANN ANN;
    private NaiveBayes NB;
    private RandomForest RF;
    
    public double avgCatDist_ANN;
    public double accuracy_ANN; 
    public double maxDist_ANN;
    public double avgCatDist_NB;
    public double accuracy_NB; 
    public double maxDist_NB;
    public double avgCatDist_RF;
    public double accuracy_RF; 
    public double maxDist_RF;
    public double avgCatDist_Zest;
    public double accuracy_Zest; 
    public double maxDist_Zest;
    
    private int multiHouseCatNumber;
    private boolean multiHouseDynamic;
    
    private final String zillowDataFile = "data\\ZillowDataTrain.csv";
    

    /**
     * Constructor will initialize the classification objects and train them
     * using the data that can be retrieved from the ZillowParser. The
     * classification objects will then each be evaluated so that the different
     * methods can be compared.
     *
     */
    public Controller(){
        multiHouseDynamic = false;
        multiHouseCatNumber = 125;
        singleHouse = false;
        initialize();
    }
    
    public Controller(int mHCN, boolean mHD)
    {
        multiHouseDynamic = mHD;
        multiHouseCatNumber = mHCN;
        singleHouse = false;
        initialize();
    }
    
    public Controller(String[] dreamHouse)
    {
        singleHouse = true;
        this.dreamHouse = dreamHouse;
        initialize();
    }
    private void initialize() {
        List<House> allHouses = ZillowParser.parseZillowDataFile(new java.io.File(zillowDataFile));
        partitionHouses(allHouses);
        double[] categories;
        if (singleHouse) {
            categories = Classifier.generatePriceRanges(allHouses, 125, true);
        } else {
            categories = Classifier.generatePriceRanges(allHouses, multiHouseCatNumber, multiHouseDynamic);
        }
		// Dynamic category sizes increases accuracy with fewer number of
        // categories when using Naive-Bayes. My guess is that the outlier
        // categories are having a negative effect on the prediction model for
        // NB.

        ANN = new ANN(categories);
        NB = new NaiveBayes(categories);
        RF = new RandomForest(categories, 200, trainingHouses.size()/2);

        ExecutorService e = Executors.newFixedThreadPool(3);
        e.submit(new TrainingThread(trainingHouses, ANN));
        e.submit(new TrainingThread(trainingHouses, NB));
        e.submit(new TrainingThread(trainingHouses, RF));

        e.shutdown();

        try {
            e.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException ex) {
        }

        // NB.printCatDetails();
        int cnt = 0;
        double averageCatDifference_ANN = 0;
        int numberTrue_ANN = 0;
        int maxDistance_ANN = 0;
        double averageCatDifference_NB = 0;
        int numberTrue_NB = 0;
        int maxDistance_NB = 0;
        double averageCatDifference_RF = 0;
        int numberTrue_RF = 0;
        int maxDistance_RF = 0;
        double averageCatDifference_zi = 0;
        int numberTrue_zi = 0;
        int maxDistance_zi = 0;
        res = new ArrayList<>();

        if (singleHouse) {

            housePrediction(dreamHouse);

        } else {
            for (House h : testHouses) {
                System.out.println("---------------------------\nActual Price Sold: " + h.getPriceSold());
//
                double[] NBPred = NB.predict(h);
                double[] ANNPred = ANN.predict(h);
                double[] RFPred = RF.predict(h);
                int determinedCat_NB = NB.determineCategory(logs(NBPred));
                int determinedCat_ANN = ANN.determineCategory(logs(ANNPred));
                int determinedCat_RF = RF.determineCategory(logs(RFPred));
                int determinedCat_zi = RF.determineCategory(h.getZestimate());
//                
                System.out.println("Average of Predicted ANN Price Range :" + logs(ANNPred) + "\t");
                System.out.println("Average of Predicted NB Price Range :" + logs(NBPred) + "\t");
                System.out.println("Average of Predicted RF Price Range :" + logs(RFPred) + "\t");
                System.out.println("Zestimate :" + h.getZestimate() + "\t");
//                System.out.print("Difference  :" + (h.getZestimate() - logs(ANN.predict(h))) + "\n");
                res.add(new String[]{
                    h.getHouseCompleteAddress(),
                    h.getHouseAddress().getCity() + " "
                    + h.getHouseAddress().getState(),
                    String.valueOf(h.getNoOfBedroom()),
                    String.valueOf(h.getNoOfBathroom()),
                    String.valueOf(h.getPricePerSQFT()),
                    String.valueOf(h.getSchoolElem()),
                    String.valueOf(h.getSchoolHigh()),
                    String.valueOf(h.getSchoolMid()),
                    String.valueOf(h.getBuiltYear()),
                    String.valueOf(h.getArea()),
                    String.valueOf(h.getPriceSold()),
                    String.valueOf(h.getZestimate()),
                    String.valueOf(logs(ANNPred)),
                    String.valueOf(logs(NBPred)),
                    String.valueOf(logs(RFPred)),});
//                cnt++;
//
                 int trueCat = NB.determineCategory(h.getPriceSold());
//
                averageCatDifference_ANN += Math.abs(determinedCat_ANN - trueCat);
                if (Math.abs(determinedCat_ANN - trueCat) > maxDistance_ANN) {
                    maxDistance_ANN = Math.abs(determinedCat_ANN - trueCat);
                }
                if (determinedCat_ANN == trueCat) {
                    numberTrue_ANN++;
                }
                
                averageCatDifference_RF += Math.abs(determinedCat_RF - trueCat);
                if (Math.abs(determinedCat_RF - trueCat) > maxDistance_RF) {
                    maxDistance_RF = Math.abs(determinedCat_RF - trueCat);
                }
                if (determinedCat_RF == trueCat) {
                    numberTrue_RF++;
                }
                
                averageCatDifference_NB += Math.abs(determinedCat_NB - trueCat);
                if (Math.abs(determinedCat_NB - trueCat) > maxDistance_NB) {
                    maxDistance_NB = Math.abs(determinedCat_NB - trueCat);
                }
                if (determinedCat_NB == trueCat) {
                    numberTrue_NB++;
                }
                
                averageCatDifference_zi += Math.abs(determinedCat_zi - trueCat);
                if (Math.abs(determinedCat_zi - trueCat) > maxDistance_zi) {
                    maxDistance_zi = Math.abs(determinedCat_zi - trueCat);
                }
                if (determinedCat_zi == trueCat) {
                    numberTrue_zi++;
                }

            }
            avgCatDist_ANN = averageCatDifference_ANN / testHouses.size();
            accuracy_ANN = ((double)numberTrue_ANN/(double) testHouses.size()); 
            maxDist_ANN = maxDistance_ANN;

            // Below Analysis is for ANN we can do similar for other classifier
            System.out.println("\n\n==================================\n       Average Performance\n==================================");
            System.out.println("\n==================================\n\t         ANN\n==================================");
            System.out.println("Average Difference in Category: " + averageCatDifference_ANN / testHouses.size());
            System.out.println("Absolute Correctness: " + numberTrue_ANN + "/" + testHouses.size() + " = " + ((double)numberTrue_ANN/(double) testHouses.size()));
            System.out.println("Maximum Difference in Category: " + maxDistance_ANN);
            
            avgCatDist_NB = averageCatDifference_NB / testHouses.size();
            accuracy_NB = ((double)numberTrue_NB/(double) testHouses.size()); 
            maxDist_NB = maxDistance_NB;
            
            System.out.println("\n==================================\n\t         NB\n==================================");
            System.out.println("Average Difference in Category: "
                    + averageCatDifference_NB / testHouses.size());
            System.out.println("Absolute Correctness: " + numberTrue_NB + "/"
                    + testHouses.size() + " = " + (double) numberTrue_NB
                    / (double) testHouses.size());
            System.out.println("Maximum Difference in Category: " + maxDistance_NB);
            
            avgCatDist_RF = averageCatDifference_RF / testHouses.size();
            accuracy_RF = ((double)numberTrue_RF/(double) testHouses.size()); 
            maxDist_RF = maxDistance_RF;
            
            System.out.println("\n==================================\n\t         RF\n==================================");
            System.out.println("Average Difference in Category: "
                    + averageCatDifference_RF / testHouses.size());
            System.out.println("Absolute Correctness: " + numberTrue_RF + "/"
                    + testHouses.size() + " = " + (double) numberTrue_RF
                    / (double) testHouses.size());
            System.out.println("Maximum Difference in Category: " + maxDistance_RF);
            
            avgCatDist_Zest = averageCatDifference_zi / testHouses.size();
            accuracy_Zest = ((double)numberTrue_zi/(double) testHouses.size()); 
            maxDist_Zest = maxDistance_zi;
            
            System.out.println("\n==================================\n\t       Zillow\n==================================");
            System.out.println("Average Difference in Category: "
                    + averageCatDifference_zi / testHouses.size());
            System.out.println("Absolute Correctness: " + numberTrue_zi + "/"
                    + testHouses.size() + " = " + (double) numberTrue_zi
                    / (double) testHouses.size());
            System.out.println("Maximum Difference in Category: " + maxDistance_zi);
        }

    }
    
    public final void housePrediction(String[] dreamHouse)
    {
        houseValue_ANN = String.valueOf(logs(ANN.predict(dreamHouse)));
        houseValue_NB = String.valueOf(logs(NB.predict(dreamHouse)));
        houseValue_RF = String.valueOf(logs(RF.predict(dreamHouse)));
    }

    public final double logs(double[] prediction) {
        double avg = prediction[0];
        if (prediction.length > 1) {
            avg = (avg + prediction[1]) / 2;
        }
        return avg;
    }

    /**
     * PartitionHouses partitions the given set of houses into a training set
     * and a testing set. 
     * 
     * @param allHouses 
     */
    public final void partitionHouses(List<House> allHouses) {
        trainingHouses = new ArrayList<>();
        testHouses = new ArrayList<>();

        int numTestHouses = allHouses.size() / 10;
        Random r = new Random(); // Random r = new Random(13);
        java.util.HashSet<Integer> bag = new java.util.HashSet<>();

        while (bag.size() < numTestHouses) {
            bag.add(r.nextInt(allHouses.size()));
        }

        for (int i = 0; i < allHouses.size(); i++) {
            if (bag.contains(i)) {
                testHouses.add(allHouses.get(i));
            } else {
                trainingHouses.add(allHouses.get(i));
            }
        }
    }
    
    void runController()
    {
        int cat = 125;
        boolean dyn = true;
        double avgCatTotal_ANN = 0;
        double avgCatTotal_NB = 0;
        double avgCatTotal_RF = 0;
        double avgCatTotal_Zest = 0;
        
        double avgAccuracyTotal_ANN = 0;
        double avgAccuracyTotal_NB = 0;
        double avgAccuracyTotal_RF = 0;
        double avgAccuracyTotal_Zest = 0;
        
        double maxDistTotal_ANN = 0;
        double maxDistTotal_NB = 0;
        double maxDistTotal_RF = 0;
        double maxDistTotal_Zest = 0;
        for(int i = 0; i < 5; i++)
        {
            Controller c = new Controller(cat, dyn);
            
            avgCatTotal_ANN += c.avgCatDist_ANN;
            avgAccuracyTotal_ANN += c.accuracy_ANN;
            maxDistTotal_ANN += c.maxDist_ANN;
            
            avgCatTotal_NB += (c.avgCatDist_NB);
            avgAccuracyTotal_NB +=(c.accuracy_NB);
            maxDistTotal_NB +=(c.maxDist_NB);
            
            avgCatTotal_RF += (c.avgCatDist_RF);
            avgAccuracyTotal_RF += (c.accuracy_RF);
            maxDistTotal_RF += (c.maxDist_RF);
            
            avgCatTotal_Zest += (c.avgCatDist_Zest);
            avgAccuracyTotal_Zest += (c.accuracy_Zest);
            maxDistTotal_Zest += (c.maxDist_Zest);
        }
        
        System.out.println("\nTotals for " + cat + " categories -  Dynamic Setting: " + dyn + ": \n\n");
        
        System.out.println("Artificial Neural Network");
        System.out.println("Average difference in Category: " + avgCatTotal_ANN/5.0);
        System.out.println("Average Accuracy: " + avgAccuracyTotal_ANN/5.0);
        System.out.println(maxDistTotal_ANN/5.0);
        
        System.out.println("Naive Bayes");
        System.out.println("Average difference in Category: " + avgCatTotal_NB/5.0);
        System.out.println("Average Accuracy: " + avgAccuracyTotal_NB/5.0);
        System.out.println(maxDistTotal_NB/5.0);
        
        System.out.println("Random Forest - Average difference in Category: " + avgCatTotal_RF/5.0);
        System.out.println(avgAccuracyTotal_RF/5.0);
        System.out.println(maxDistTotal_RF/5.0);
        
        System.out.println("Zestimate - Average difference in Category: " + avgCatTotal_Zest/5.0);
        System.out.println(avgAccuracyTotal_Zest/5.0);
        System.out.println(maxDistTotal_Zest/5.0);
        
    }

    public static void main(String[] args) {
        
        if(args.length > 0)
            switch(args[0])
            {
                case "1":
                    break;
                case "2":
                    UI.PredictHousePrice.main(new String[0]);
                    System.exit(0);
                    break;
                case "3":
                    UI.DreamHouse.main(new String[0]);
                    System.exit(0);
                    break;
                default:
                    System.out.println("Format: 'java -jar ZillowClassifier.jar [d]'\n\n"
                            + "[d] - a digit representing your output choice:\n"
                            + "    1 - Text output. Generates an average result for each algorithm. \n"
                            + "    2 - Predict House Price UI. Displays testing houses and predictions.\n"
                            + "    3 - Dream House UI. Construct arbitrary house to generate predictions.\n");
                    System.exit(1);
                    break;
            }
        
//        new Controller();
    }

}
