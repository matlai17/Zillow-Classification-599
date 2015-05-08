
package Classifiers;

import Resources.House;

/**
 * An implementation of the random forest classification ensemble algorithm.
 * 
 * @author Matthew Lai and Arun Singh
 */
public class RandomForest extends Classifier {

    private final String CLASSIFIER_NAME = "Random Forest";

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
