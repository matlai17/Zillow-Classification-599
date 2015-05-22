package Controller;

import Classifiers.*;
import Classifiers.Classifier;
import Parser.ZillowParser;
import Resources.House;
import java.util.ArrayList;
import java.util.Arrays;
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
    Classifier ANN;
    Classifier NB;
    Classifier RF;
    
    /**
     * Constructor will initialize the classification objects and train them 
     * using the data that can be retrieved from the ZillowParser. The classification 
     * objects will then each be evaluated so that the different methods can be 
     * compared.
     * 
     * @param p The object from which Zillow training data can be retrieved.
     */
    public Controller(ZillowParser p)
    {
//        houses = Parser.parse(f);
        double[] categories = Classifier.generatePriceRanges(null, 125, true);
        ANN = new ANN(categories);
        NB = new NaiveBayes(categories);
        RF = new RandomForest(categories);
        
        ExecutorService e = Executors.newFixedThreadPool(3);
        
        e.submit(new TrainingThread(trainingHouses, ANN));
        e.submit(new TrainingThread(trainingHouses, NB));
        e.submit(new TrainingThread(trainingHouses, RF));
        
        e.shutdown();
        
        try { e.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS); }
        catch (InterruptedException ex) { }
    }
    public static void main(String[] args) {
        
        
    }
    
}
