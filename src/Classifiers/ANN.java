
package Classifiers;

import Resources.House;

/**
 * An implementation of the Artificial Neural Network classification algorithm.
 *
 * @author Matthew Lai and Arun Singh
 */
public class ANN extends Classifier {

    private final String CLASSIFIER_NAME = "Artificial Neural Network";

    public ANN(int distribution) {
        super(distribution);
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
