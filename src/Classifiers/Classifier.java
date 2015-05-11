
package Classifiers;

import Resources.House;
import java.util.List;
import java.util.TreeSet;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author Matthew Lai and Arun Singh
 */
public abstract class Classifier {
    
    private static final int MAX_HOUSE_PRICE = 500000000;
    
    /**
     * This option will return price ranges that are $10,000 in range.
     */
    public static final int SMALL_RANGES = 10;
    /**
     * This option will return price ranges that are $100,000 in range.
     */
    public static final int MEDIUM_RANGES = 20;
    /**
     * This option will return price ranges that are $400,000 in range.
     */
    public static final int LARGE_RANGES = 30;
    
    /**
     * This option will return variable price ranges that will distribute the
     * houses into an even and flat distribution such that the more popular price
     * ranges will be smaller
     */
    public static final int EVEN_DISTRIBUTION = 100;
    /**
     * This option will return fixed price ranges that will created a fixed
     * distribution of houses
     */
    public static final int FIXED_DISTRIBUTION = 200;
    
    private double[] priceRange;
    
    public Classifier(int range, int distribution)
    {
        generatePriceRanges(null, range, range);
    }
    
    /**
     * This function trains the classifier using a House object. How the Classifier
     * is trained is dependent on the Classifier used and algorithm that is selected.
     * 
     * @param h A House object that the Zillow Classifier will be trained with
     */
    public abstract void train(House h);
    
    /**
     * This function receives a House object and will attempt to classify the 
     * House object into a price range.
     * 
     * @param h A House object that the Classifier will attempt to classify
     * @return A tuple that predicts the price range of the given House object
     */
    public abstract double[] predict(House h);
    
    /**
     * This method will return the Classifier name. This function is used for
     * Classification Identification purposes.
     * 
     * @return The name of the classification algorithm.
     */
    public abstract String getClassifierName();
    
    /**
     * Generates the price ranges that the classification algorithm will classify
     * the houses into based on the set distribution types.
     * 
     * @param houses
     * @param bucketSize
     * @param distributionType
     * @return 
     */
    private double[] generatePriceRanges(List<House> houses, int rangeSize, int rangeType)
    {
//        DoubleProperty lowerPriceRange = new SimpleDoubleProperty(Long.MAX_VALUE);
//        DoubleProperty upperPriceRange = new SimpleDoubleProperty(Long.MAX_VALUE);
        
        // Retrieve lower/uper price ranges
//         houses.stream().map(h -> h.get("price")).map(d -> {
//             if(d > upperPriceRange.get()) upperPriceRange.set(d);
//             if(d < lowerPriceRange.get()) lowerPriceRange.set(d);
//             return d;
//         });
        
        int small = 10000;
        int medium = 100000;
        int large = 400000;
        
        return null;
    }
    
}
