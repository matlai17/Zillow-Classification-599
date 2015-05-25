
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
    private final int PRICE_DIVIDER_EXP = 3;
    
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
    
    // Train data. Each feature should be divided by successive numbers 10^N for N= 0 through n, where n is a selected number, and added into 
    // the training data. This will retrieve more data from large numbers because of their uniqueness. 
    @Override
    public void train(House h) 
    {
        totalHouseCount++;
        
        double[] houseArray = h.getFeaturesArray();
        
        double truePrice = houseArray[houseArray.length-1];
        int houseCat = determineCategory(truePrice);
        
        Category cat = categories[houseCat];
        cat.housesInCategory++;
        
        for (int i = 0; i < houseArray.length; i++) 
        {
            double value = houseArray[i];
            for(int j = 0; j <= PRICE_DIVIDER_EXP; j++)
            {
                double reducedValue = Math.floor(value/Math.pow(10, j));
                if(reducedValue == 0) break;
                
                String featureName = "$" + String.valueOf(i) + "$" + String.valueOf(j) + String.valueOf(reducedValue);
                if(featureFrequency.containsKey(featureName)) featureFrequency.put(featureName, featureFrequency.remove(featureName)+1);
                else featureFrequency.put(featureName, 1);
                
                if(cat.featureFrequency.containsKey(featureName)) cat.featureFrequency.put(featureName, cat.featureFrequency.remove(featureName)+1);
                else cat.featureFrequency.put(featureName, 1);
            }
        }
    }

    @Override
    public double[] predict(House h) {
        
        double [] houseArray = h.getFeaturesArray();
        
        int mostLikelyCategory = -1;
        double mostLikelyProbability = Integer.MIN_VALUE;
//        double mostLikelyProbability = Integer.MAX_VALUE;
        
        for (int i = 0; i < categories.length; i++) {
            
            Category cat = categories[i];
            
            double probabilityOfHouseGivenCategory = 1, probabilityOfCategory = 1;//, PD = 1;
            java.util.HashSet<String> cTerms = new java.util.HashSet<>();
            java.util.Iterator<String> cT = cat.featureFrequency.keySet().iterator();
            int totalFeatureCount = 0;
            while(cT.hasNext())
            {
                String featureName = cT.next();
                cTerms.add(featureName);
                totalFeatureCount += cat.featureFrequency.get(featureName);
            }
            
            probabilityOfCategory = Math.log(((double)cat.housesInCategory)/((double)totalHouseCount));
            int dCount = 0;
            for(double d : houseArray)
            {
                for(int j = 0; j <= PRICE_DIVIDER_EXP; j++)
                {
                    double reducedValue = Math.floor(d/Math.pow(10, j));
                    if(reducedValue == 0) break;
                    
                    String featureName = "$" + String.valueOf(dCount++) + "$" + String.valueOf(j) + String.valueOf(reducedValue);
                    
                    Integer numOfFeature = featureFrequency.get(featureName);
                    if(numOfFeature == null) numOfFeature = 0;
                    
                    double IDF = Math.log((double)totalFeatureCount/((double)numOfFeature + 1));
                    
                    Integer cTF = cat.featureFrequency.get(featureName);
                    if(cTF == null) cTF = 0;
                    
                    probabilityOfHouseGivenCategory += Math.log((double)(cTF+1.0)/ ((double)totalFeatureCount + cTerms.size())) * IDF;
                    
//                    PD *= Math.log(((double)numOfFeature + 1)/((double)totalHouseCount + featureFrequency.size()));
                }
            }
            
            double PSRD = (probabilityOfHouseGivenCategory + probabilityOfCategory);// - PD;
            System.out.println(probabilityOfHouseGivenCategory + "\t" + probabilityOfCategory);
            
            System.out.println(PSRD + "\t" + mostLikelyProbability + "\t" + determinePriceRange(i)[0]);
            
            if(PSRD > mostLikelyProbability)
            {
                mostLikelyCategory = i;
                mostLikelyProbability = PSRD;
            }
        }
        System.out.println(mostLikelyCategory);
        return determinePriceRange(mostLikelyCategory);
    }

    @Override
    public String getClassifierName() {
        return CLASSIFIER_NAME;
    }
    
}
