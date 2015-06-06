
package Classifiers.RFStructures;

import Resources.DecisionTreeMismatchedArgumentsException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleDoubleProperty;
/**
 * ID3 implementation for decision tree generation. Performs checks to ensure a valid
 * tree and decision can be made before performing training and predicting respectively.
 * Creates a root node to begin the process of constructing the tree.
 * 
 * @author Matthew Lai 
 * @param <D> The decision type
 */
public class ID3DecisionTree <D> {
    
    private final ID3DecisionNode<D> root;
    private final List<String> attributeNames;
    
    public ID3DecisionTree(List<String> attributeNames, List<List> attr, List<D> dep, boolean randomSubsetMode)
    {
        this.attributeNames = attributeNames;
        
        DecisionTreeMatrix dTI = new DecisionTreeMatrix(attributeNames, attr, dep);
        
        if(dTI.getValidColumns().isEmpty() || dTI.checkHomogenyCount() == 1 ) root = new ID3TerminalDecisionNode(dTI);
        else root = new ID3DecisionNode(dTI, randomSubsetMode);
    }
    
    /**
     * Queries the tree for a decision based on the list of attribute names and their corresponding
     * attributes. These attributes should match the ones used to construct the tree.
     * 
     * @param attributeNames
     * @param attr
     * @return 
     */
    public D getDecision(List<String> attributeNames, List<List> attr)
    {
        if(!this.attributeNames.containsAll(attributeNames)) 
            try { throw new DecisionTreeMismatchedArgumentsException("Your decision query must contain the same column names as the original training data."); } 
            catch (DecisionTreeMismatchedArgumentsException ex) { Logger.getLogger(ID3DecisionTree.class.getName()).log(Level.SEVERE, null, ex); }
        
        DecisionTreeMatrix dTI = new DecisionTreeMatrix(attributeNames, attr);
        
        return root.getDecision(dTI);
    }
    
    /**
     * Calculates the number of nodes in the tree.
     * 
     * @return the number of nodes in the tree
     */
    public int getSize()
    {
        return 1 + root.getSize();
    }
    
    /**
     * Calculates the maximum depth of the tree.
     * 
     * @return the number of nodes to reach the deepest branch.
     */
    public int getMaxDepth()
    {
        return root.getDepth();
    }
    
    public static void main(String[] args) {
        List<List> attributes = new ArrayList<>();
        List<String> labels = Arrays.asList("Hair", "Height", "Weight", "Lotion");
        List<Integer> deps = Arrays.asList(1,0,0,1,1,0,0,0);
        attributes.add(Arrays.asList("blonde", "average", "light", "no"));
        attributes.add(Arrays.asList("blonde", "tall", "average", "yes")); 
        attributes.add(Arrays.asList("brown", "short", "average", "yes")); 
        attributes.add(Arrays.asList("blonde", "short", "average", "no")); 
        attributes.add(Arrays.asList("red", "average", "heavy", "no")); 
        attributes.add(Arrays.asList("brown", "tall", "heavy", "no")); 
        attributes.add(Arrays.asList("brown", "average", "heavy", "no")); 
        attributes.add(Arrays.asList("blonde", "short", "light", "yes")); 
        
        ID3DecisionTree<Integer> test = new ID3DecisionTree<>(labels, attributes, deps, false);
        
//        System.out.println(test.getDepth());
    }
}