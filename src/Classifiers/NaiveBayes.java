
package Classifiers;

import Resources.House;
import java.util.List;
import java.util.TreeMap;

/**
 * An implementation of the Naive-Bayes classification algorithm
 *
 * @author Matthew Lai and Arun Singh
 */
class Category {
    
    public int housesInCategory;
    public TreeMap<String, Integer> featureFrequency;
    
    public Category()
    {
        housesInCategory = 0;
        featureFrequency = new TreeMap<>();
    }
}

public class NaiveBayes extends Classifier {

    private final String CLASSIFIER_NAME = "Naive Bayes";
    
    // Frequency of each feature for the entire classifier
    private TreeMap<String, Integer> featureFrequency;
    
    // Data structure(s) containing different counts for each category
    //      - # of houses in each category
    //      - Frequency of each feature within the category
    Category[] categories;
    
    private int totalHouseCount;

    public NaiveBayes(int numberOfCategories) 
    {
        super(numberOfCategories);
        initializeVariables();
    }
    
    public NaiveBayes(double[] categories)
    {
        super(categories);
        initializeVariables();
    }
    
    public NaiveBayes(List<House> houses, int numberOfCategories, boolean evenDistribution)
    {
        super(houses, numberOfCategories, evenDistribution);
        initializeVariables();
    }
    
    private void initializeVariables()
    {
        featureFrequency = new TreeMap<>();
        totalHouseCount = 0;
        categories = new Category[numberOfCategories];
        for (int i = 0; i < categories.length; i++) categories[i] = new Category();
    }
    
    @Override
    public void train(House h) 
    {
        
    }

    @Override
    public double[] predict(House h) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getClassifierName() {
        return CLASSIFIER_NAME;
    }
    
}
