package Controller;

import Classifiers.*;
import Classifiers.Classifier;
import Parser.ZillowParser;
import Resources.House;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * The main Controller class that will initialize and train the different classification
 * classes. It contains useful methods that can be used in the process of training and
 * testing the classification classes.
 *
 * @author Matthew Lai and Arun Singh
 */
public class Controller {
    
    List<House> trainingHouses;
    List<House> testHouses;
    Classifier ANN;
    Classifier NB;
    Classifier RF;
    
    /**
     * Constructor will initialize the classification objects and train them 
     * using the data that can be retrieved from the ZillowParser. The classification 
     * objects will then each be evaluated so that the different methods can be 
     * compared.
     * 
     */
    public Controller()
    {
        List<House> allHouses = ZillowParser.parseZillowDataFile(new java.io.File("data\\ZillowDataTrain.csv"));
        partitionHouses(allHouses);
        
        double[] categories = Classifier.generatePriceRanges(allHouses, 2, true);
        ANN = new ANN(categories);
        NB = new NaiveBayes(categories);
        RF = new RandomForest(categories);
        
        ExecutorService e = Executors.newFixedThreadPool(3);
        
//        e.submit(new TrainingThread(trainingHouses, ANN));
        e.submit(new TrainingThread(trainingHouses, NB));
//        e.submit(new TrainingThread(trainingHouses, RF));
        
        e.shutdown();
        
        try { e.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS); }
        catch (InterruptedException ex) { }
        
//        for(House h : testHouses)
//        {
        House h = testHouses.get(0);
            double[] NBPrediction = NB.predict(h);
            System.out.print("Price Sold: " + h.getPriceSold() + "\t" + "Predicted: $" + NBPrediction[0] + " - ");
            if(NBPrediction.length > 1) System.out.println("$" + NBPrediction[1]);
//        }
        
    }
    
    private void partitionHouses(List<House> allHouses)
    {
        trainingHouses = new ArrayList<>();
        testHouses = new ArrayList<>();
        
        int numTestHouses = allHouses.size()/10;
        Random r = new Random();
        java.util.HashSet<Integer> bag = new java.util.HashSet<>();
        
        while(bag.size() < numTestHouses) bag.add(r.nextInt(allHouses.size()));
        
        for (int i = 0; i < allHouses.size(); i++)
            if(bag.contains(i)) testHouses.add(allHouses.get(i));
            else trainingHouses.add(allHouses.get(i));
    }
    
    public static void main(String[] args) {
        new Controller();
    }
    
}
