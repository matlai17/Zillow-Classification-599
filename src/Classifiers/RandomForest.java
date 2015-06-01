package Classifiers;

import Classifiers.RFStructures.ID3DecisionTree;
import Classifiers.RFStructures.RandomForestTrainer;
import java.util.List;

import Resources.House;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Map;
import java.util.HashMap;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * An implementation of the random forest classification ensemble algorithm.
 * 
 * @author Matthew Lai and Arun Singh
 */
public class RandomForest extends Classifier {

    private final String CLASSIFIER_NAME = "Random Forest";
    private final int B;
    private final int N;
    private List<ID3DecisionTree<Integer>> forest;
    public RandomForest(double[] categories, int B, int N) {
        super(categories);
        this.B = B;
        this.N = N;
        this.forest = new ArrayList<>();
    }

    @Override
    public void train(List<House> houses) {
        ExecutorService exec = Executors.newWorkStealingPool();
        List<Future<ID3DecisionTree>> list = new ArrayList<>();
        for(int i = 0; i < B; i++)
        {
            List<House> trainingHouses = new ArrayList<>();
            
            Random r = new Random();
            for (int j = 0; j < N; j++) trainingHouses.add(houses.get(r.nextInt(houses.size())));
            
            list.add(exec.submit(new RandomForestTrainer(this, trainingHouses)));
        }
        
        list.stream().forEach((future) -> {
            try { forest.add(future.get()); } 
            catch (InterruptedException | ExecutionException ex) { Logger.getLogger(RandomForest.class.getName()).log(Level.SEVERE, null, ex); }
        });
        
        exec.shutdown();
    }

    @Override
    public double[] predict(House h) {
        Map<Integer, Integer> syncedCatCount = Collections.synchronizedMap(new HashMap<Integer, Integer>());
        List<String> featureNames = new ArrayList<>();
        List<List> featuresList = new ArrayList<>();
        List features = new ArrayList();
        
        h.getFeatures().keySet().forEach(name -> {
            featureNames.add(name);
            features.add(h.getFeatures().get(name));
        });
        
        featuresList.add(features);
        
//        forest.stream().forEach(tree -> {
        int nullCount = 0;
        for(int i = 0; i < forest.size(); i++)
        {
            ID3DecisionTree<Integer> tree = forest.get(i);
            Integer category = tree.getDecision(featureNames, featuresList);
            if(category == null)
            {
                nullCount++;
                continue;
            }
            if(!syncedCatCount.containsKey(category)) syncedCatCount.put(category, 1);
            else syncedCatCount.put(category, syncedCatCount.remove(category) + 1);
//        });
        }
        int largestValue = Collections.max(syncedCatCount.values());
        SimpleIntegerProperty largestCategory = new SimpleIntegerProperty(-1);
        
        syncedCatCount.keySet().iterator().forEachRemaining(it -> {
            if(syncedCatCount.get(it) == largestValue) largestCategory.set(it);
        });
        
        return determinePriceRange(largestCategory.get());
    }

    @Override
    public String getClassifierName() {
        return CLASSIFIER_NAME;
    }

    @Override
    public double[] predict(String[] h) {
        // h -> zip,bed,bath,area,year,elem,mid,high, 0.0
        //      0   1   2    3    4    5    6   7     8
        // TODO Auto-generated method stub
        return predict(new House(
                0,            //long ZillowID
                null,            //String Street
                null,            //String City
                null,            //String State
                h[0],            //String Zip
                0,         //double priceSold
                Integer.parseInt(h[1]),         //int bedrooms
                Double.parseDouble(h[2]),         //double bathrooms
                Double.parseDouble(h[3]),         //double area
                Integer.parseInt(h[4]),         //int year
                0,         //double zestimate
                Integer.parseInt(h[5]),//int elem
                Integer.parseInt(h[6]),         //int mid
                Integer.parseInt(h[7])));       //int high
    }

}
