
package Classifiers;

import Resources.House;
import Resources.HouseCategoryMismatchException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Matthew Lai and Arun Singh
 */
public abstract class Classifier {
    
    private static final int MAX_HOUSE_PRICE = 10000000;
    
    private double[] priceRange;
    
    private int numberOfCategories;
    
    public Classifier(List<House> houses, int numberOfCategories, boolean evenDistribution)
    {
        priceRange = generatePriceRanges(houses, numberOfCategories, evenDistribution);
        this.numberOfCategories = numberOfCategories;
    }
    
    public Classifier(int numberOfCategories, boolean evenDistribution)
    {
        this((List<House>)null, numberOfCategories, evenDistribution);
    }
    
    public Classifier(House[] houses, int numberOfCategories, boolean evenDistribution)
    {
        this(Arrays.asList(houses),numberOfCategories,evenDistribution);
    }
    
    public Classifier(int numberOfCategories)
    {
        this((List<House>) null, numberOfCategories, false);
    }
    
    public Classifier(double[] categories)
    {
        priceRange = categories;
        numberOfCategories = priceRange.length;
    }
    
    /**
     * Determines the category of a price given the specified classifier configuration:
     * how many categories there are and how the categories were formed.
     * 
     * @param price The price for which the category will be determined.
     * @return The category for the price
     */
    protected int determineCategory(double price)
    {
        if(price > priceRange[priceRange.length - 1]) return priceRange.length - 1;
        int imin = 0, imax = priceRange.length - 1;
        while(imax - imin != 1)
        {
            // floored
            int imid = (imax+imin)/2;
            if(price == priceRange[imid]) return imid;
            if(price > priceRange[imid]) imin = imid;
            if(price < priceRange[imid]) imax = imid;
        }
        return imin;
    }
    
    /**
     * determinePriceRange returns a double array containing one or two numbers, each representing a bound of the price
     * range that the category represents. If the category is bounded by only one number or, in other words, the price of
     * the house is within the final category that represents any number greater than the final bound, then the function
     * will only return an array of one element.
     * 
     * @param category The category number whose bounds is to be returned
     * @return An array containing one or two numbers representing the price bounds of the category
     */
    protected double[] determinePriceRange(int category)
    {
        double [] priceReturn;
        if(category < priceRange.length) priceReturn = new double[]{ priceRange[category], priceRange[category+1] - 1 };
        else priceReturn = new double[]{ priceRange[category] };
        return priceReturn;
    }
    
    protected String printablePriceRange(int category)
    {
        double[] priceBounds = determinePriceRange(category);
        if(priceBounds.length == 1)
            return "$" + priceBounds[0] + " - ";
        return "$" + priceBounds[0] + " - $" + priceBounds[1];
    }
    
    public int getNumberOfCategories()
    {
        return numberOfCategories;
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
     * the houses into based on the set distribution types. The price range returned is represented by a list of double
     * where each value in the list represents the inclusive start bound to a range until the next value which is the 
     * non-inclusive end bound to the range. Calling this function is identical to calling
     * generatePriceRanges(null, numberOfCategories, false);
     * 
     * @param numberOfCategories  Number of "buckets" or categories that will be created. 150-500 for average. More for finer buckets
     * @return 
     */
    public static double [] generatePriceRanges(int numberOfCategories)
    {
        return generatePriceRanges(null, numberOfCategories, false);
    }
    
    /**
     * Generates the price ranges that the classification algorithm will classify
     * the houses into based on the set distribution types. The price range returned is represented by a list of double
     * where each value in the list represents the inclusive start bound to a range until the next value which is the 
     * non-inclusive end bound to the range.
     * 
     * @param houses  List of houses that will be used when generating even distribution categories
     * @param numberOfCategories  Number of "buckets" or categories that will be created. 150-500 for average. More for finer buckets
     * @param evenDistribution  If the category price distribution should be dynamically generated according to the given house data.
     * @return A list of double where each value represents a price range
     */
    public static double[] generatePriceRanges(List<House> houses, int numberOfCategories, boolean evenDistribution)
    {
        double pricePointsPerCategory = (double)MAX_HOUSE_PRICE/(double)numberOfCategories;
            
        double[] categories = new double[numberOfCategories];
        double pricePoint = 0;

        if(!evenDistribution)
        {
            for (int i = 0; i < categories.length; i++) 
            {
                categories[i] = Math.round(pricePoint);
                pricePoint += pricePointsPerCategory;
            }
            return categories;
        }
        
        if(houses == null) 
            try { throw new HouseCategoryMismatchException("You cannot create categories of an even distribution without providing a list of houses. Please try a different price categorization configuration."); } 
            catch (HouseCategoryMismatchException ex) { Logger.getLogger(Classifier.class.getName()).log(Level.SEVERE, null, ex); }

        Double[] sortedUniquePrices = houses.stream().map(h -> h.getSoldPrice()).distinct().sorted().toArray(Double[] :: new);
        pricePointsPerCategory = (double)(sortedUniquePrices.length - 1)/(double)numberOfCategories;
        
        if(numberOfCategories > sortedUniquePrices.length)
            try {  throw new HouseCategoryMismatchException("There cannot be more categories than unique house prices when performing an even distribution. Please decrease the number of categories to be generated."); } 
            catch (HouseCategoryMismatchException ex) { Logger.getLogger(Classifier.class.getName()).log(Level.SEVERE, null, ex); } 
        
        double index = (double)pricePointsPerCategory;
        for (int i = 1; i < categories.length; i++) {
            categories[i] = sortedUniquePrices[(int)Math.round(index)];
            index += pricePointsPerCategory;
        }

        return categories;
    }
}

//    public static int determineCategory(double[] categories, double price)
//    {
//        if(price > categories[categories.length - 1]) return categories.length - 1;
//        int imin = 0, imax = categories.length - 1;
//        while(imax - imin != 1)
//        {
//            // floored
//            int imid = (imax+imin)/2;
//            if(price == categories[imid]) return imid;
//            if(price > categories[imid]) imin = imid;
//            if(price < categories[imid]) imax = imid;
//        }
//        return imin;
//    }
//    
//    public static void main(String[] args) {
//        List<House> test = new ArrayList<>();
//        Random r = new Random();
//        for(int i = 0; i < 100; i++)
//        {
//            House h = new House();
////            h.setSoldPrice((r.nextGaussian()+2)*r.nextInt(1000));
//            h.setSoldPrice(i);
//            test.add(h);
//        }
//        double []output = generatePriceRanges(test, 18, true);
////        for(double o : output) System.out.println(o);
//        for (int i = 0; i < output.length; i++) {
//            System.out.println(i + " - " + output[i]);
//        }
//        double testNum = 33;
//        System.out.println("\n-----------\n" + determineCategory(output, testNum) +  " - " + testNum);
//    }
