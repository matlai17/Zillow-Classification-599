
package Classifiers;

import Resources.House;
import java.util.List;

/**
 * An implementation of the Naive-Bayes classification algorithm
 *
 * @author Matthew Lai and Arun Singh
 */
public class NaiveBayes extends Classifier {

    private final String CLASSIFIER_NAME = "Naive Bayes";

    public NaiveBayes(int numberOfCategories) {
        super(numberOfCategories);
    }
    
    public NaiveBayes(double[] categories)
    {
        super(categories);
    }
    
    public NaiveBayes(List<House> houses, int numberOfCategories, boolean evenDistribution)
    {
        super(houses, numberOfCategories, evenDistribution);
    }
    
    @Override
    public void train(House h) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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