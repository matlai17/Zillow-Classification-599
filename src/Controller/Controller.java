package Controller;

import Classifiers.*;
import Classifiers.Classifier;
import Parser.ZillowParser;
import Resources.House;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * The main Controller class that will initialize and train the different classification
 * classes. It contains useful methods that can be used in the process of training and
 * testing the classification classes.
 *
 * @author Matthew Lai and Arun Singh
 */
public class Controller {
    
    List<House> houses;
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
        ANN = new ANN();
        NB = new NaiveBayes();
        RF = new RandomForest();
        
        ExecutorService e = Executors.newFixedThreadPool(3);
        
        e.submit(new TrainingThread(houses, ANN));
        e.submit(new TrainingThread(houses, NB));
        e.submit(new TrainingThread(houses, RF));
        
        e.shutdown();
        
        try { e.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS); }
        catch (InterruptedException ex) { }
    }
    
//    public static void main(String[] args) {
//        
//        ArrayList<java.util.Map<String, Integer>> test = new ArrayList<>();
//        
//        java.util.Map<String,Integer> testM1 = new HashMap<>();
//        testM1.put("testarg1", 1);
//        testM1.put("testarg2", 2);
//        testM1.put("testarg3", 3);
//        testM1.put("testblarg", 4);
//        java.util.Map<String,Integer> testM2 = new HashMap<>();
//        testM2.put("testarg1", 5);
//        testM2.put("testarg2", 6);
//        testM2.put("testarg3", 7);
//        testM2.put("testblarg", 8);
//        java.util.Map<String,Integer> testM3 = new HashMap<>();
//        testM3.put("testarg1", 9);
//        testM3.put("testarg2", 10);
//        testM3.put("testarg3", 11);
//        testM3.put("testblarg", 12);
//        
//        test.add(testM1);
//        test.add(testM2);
//        test.add(testM3);
//        
//        test.stream().map(m -> m.get("testarg2")).forEach(i -> { 
//            if(i > 8) System.out.println(i);
//            if(i <= 8) System.out.println(i);
//        });
//    }
    
}
