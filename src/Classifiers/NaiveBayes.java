
package Classifiers;

import Resources.House;

/**
 * An implementation of the Naive-Bayes classification algorithm
 *
 * @author Matthew Lai and Arun Singh
 */
public class NaiveBayes extends Classifier {

    private final String CLASSIFIER_NAME = "Naive Bayes";

    public NaiveBayes(int range, int distribution) {
        super(range, distribution);
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
