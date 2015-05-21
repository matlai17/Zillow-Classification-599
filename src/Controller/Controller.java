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
        ANN = new ANN(0);
        NB = new NaiveBayes(0);
        RF = new RandomForest(0);
        
        ExecutorService e = Executors.newFixedThreadPool(3);
        
        e.submit(new TrainingThread(houses, ANN));
        e.submit(new TrainingThread(houses, NB));
        e.submit(new TrainingThread(houses, RF));
        
        e.shutdown();
        
        try { e.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS); }
        catch (InterruptedException ex) { }
    }
    public static void main(String[] args) {
        
//        List<House> test = new ArrayList<>();
//        Random r = new Random();
//        for (int i = 0; i < 10000000; i++) {
//            House h = new House();
//            h.put("price", (double)(r.nextInt(100)+ r.nextDouble()));
//            test.add(h);
//        }
//        
//        DoubleProperty lowerPriceRange = new SimpleDoubleProperty(Long.MAX_VALUE);
//        DoubleProperty upperPriceRange = new SimpleDoubleProperty(Long.MIN_VALUE);
        
        // Retrieve lower/uper price ranges
//        test.stream().map(h -> h.get("price")).filter(d -> (lowerPriceRange.get() > d)).forEach(d -> lowerPriceRange.set(d));
//        test.stream().map(h -> h.get("price")).filter(d -> (upperPriceRange.get() > d)).forEach(d -> upperPriceRange.set(d));
        
//        test.stream().map(h -> h.get("price")).map((d) -> {
//            if(lowerPriceRange.get() > d) lowerPriceRange.set(d);
//            if(upperPriceRange.get() < d) upperPriceRange.set(d);
//            return d;
//        });
//        
//        System.out.println(lowerPriceRange.get());
//        System.out.println(upperPriceRange.get());
//             if(d > upperPriceRange) upperPriceRange = d;
//             if(d < lowerPriceRange) lowerPriceRange = d;});
        
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
    }
    
}
