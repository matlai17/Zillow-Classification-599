
package Classifiers.RFStructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * 
 * A ID3Decision Node. Consists of a node that is identified with an object of type String
 * and children that are identified by an object of type I. The node identity is
 * the "Decision" that the node will make. Specifically implements ID3 version of 
 * Decision Tree
 *
 * @author Matthew Lai and Arun Singh
 * 
 * @param <D> Decision object type
 */
class ID3DecisionNode <D> {
    
    protected HashMap<Object, ID3DecisionNode<D>> children;
    protected String attribute;
//    protected final ID3DecisionTree parentTree;
    

//    private HashMap<Object, ID3DecisionNode<D>> children;
//    private String attribute;
    
    ID3DecisionNode() 
    {
        attribute = null;
        children = new HashMap<>();
    }
    
    ID3DecisionNode(DecisionTreeMatrix dTM)
    {
        attribute = null;
        children = new HashMap<>();
        
        createTree(dTM);
    }
    
    private ID3DecisionNode(String attr, DecisionTreeMatrix dTM)
    {
        attribute = attr;
        children = new HashMap<>();
        
        createTree(dTM);
    }
    
    // Recursive Call
    private void createTree(DecisionTreeMatrix dTM) {
//        System.out.println("New Instance");
        // Perform entropy checks to determine what attribute the children should be looking for.
        int minIndex = -1;
        double minEntropy = Double.MAX_VALUE;
        HashSet<D> dependents = new HashSet<>(dTM.getDependents());
        java.util.Set<Object> branchSet = new HashSet<>();
        int Nt = dTM.getValidDataPoints().size();
        HashMap<Object, HashSet<Integer>> listOfValidDataPoints = new HashMap<>();
        for (int i = 0; i < dTM.columnCount(); i++) {
            if(dTM.getValidColumns().contains(i))
            {
                List column = dTM.getColumn(i);
                // Get Average entropy levels for each column that is valid (has not yet been made a branch)
                SimpleDoubleProperty entropy = new SimpleDoubleProperty(0);
//                int Nt = dTM.getDataPointCount();

                HashMap<Object, Integer> branchCounter = new HashMap<>();
                for(int j = 0; j < column.size(); j++) {
                    if(dTM.getValidDataPoints().contains(j))
                    {
                        Object o = column.get(j);
                        if(!branchCounter.containsKey(o)) branchCounter.put(o, 1);
                        else branchCounter.put(o, branchCounter.remove(o) + 1);
                    }
                }
                
                java.util.Set<Object> bSet = branchCounter.keySet();
                bSet.forEach(k -> {
                    HashSet<Integer> validDataPoints = new HashSet<>();
                    SimpleDoubleProperty branchEntropy = new SimpleDoubleProperty(0);
                    int Nb = branchCounter.get(k);

                    dependents.forEach(d -> {
                        int Nbc = 0;
                        for (int j = 0; j < column.size(); j++)
                            if(k.equals(column.get(j)) && d.equals(dTM.getDependentAttribute(j)) && dTM.getValidDataPoints().contains(j))
                            {
                                validDataPoints.add(j);
                                Nbc++;
                            }

                        double value = ((double)Nbc/(double)Nb);
                        if(value != 0) branchEntropy.set(branchEntropy.get() - (value * (Math.log(value)/Math.log(2))));
                    });
                    double value = ((double)Nb/(double)Nt);
                    entropy.set(entropy.get() + (value * branchEntropy.get()));
                    listOfValidDataPoints.put(k, validDataPoints);
                });
//                System.out.println(dTM.getColumnName(i)+ "\tEntropy: " + entropy.getValue());
                if(entropy.getValue() < minEntropy)
                {
                    minEntropy = entropy.getValue();
                    minIndex = i;
                    branchSet = bSet;
                }
            }
        }
        // For the mimimum entropy among all columns. All children will use this attribute value
        attribute = dTM.getColumnName(minIndex);
        // Create list of all potential child values (i.e. different values for the selected minimum entropy attribute)
        List attributeColumn = dTM.getColumn(minIndex);
        final int minIndexF = minIndex;
        branchSet.forEach(branch -> {
            System.out.println(attribute + " -> " + branch);
            DecisionTreeMatrix<D> newDTM = new DecisionTreeMatrix<>(dTM, minIndexF, listOfValidDataPoints.get(branch));
//            System.out.println("Branch " + branch.toString() + " Size: " + listOfValidDataPoints.get(branch).size());
//            System.out.println("Branch " + branch.toString());
//            System.out.println(newDTM.toString());
        // Check for end cases for each potential child (with value of the selected minimum entropy attribute)
        // (No more attributes        ||        All children have same dependent value)
            // If endcase, then create TerminalDecisionNode with Decision Object assigned. Add to children list with child value.
            
            if(!newDTM.getValidDataPoints().isEmpty())
                if(newDTM.getValidColumns().isEmpty() || newDTM.checkHomogenyCount() == 1)
                {
//                    System.out.println("TERMINATE\t" + dTM.columnCount() + "\t" + dTM.checkHomogenyCount());
                    List<D> decisions = new ArrayList<>();
                    for (int i = 0; i < attributeColumn.size(); i++) 
                        if(attributeColumn.get(i).equals(branch)) decisions.add((D)dTM.getDependents().get(i));
                    children.put(branch, new ID3TerminalDecisionNode<>(decisions));
                }

                // Otherwise, create new child
                else
                {
//                    System.out.println("BRANCH to " + branch.toString() + "\t" + dTM.getValidColumns().size() + "\t" + dTM.checkHomogenyCount());
                    children.put(branch, new ID3DecisionNode<>(newDTM));
                }
        });
    }
    
    public List<D> getDecisions(DecisionTreeMatrix dTM)
    {
        return children.get(dTM.getColumn(dTM.getColumnNames().indexOf(attribute)).get(0)).getDecisions(dTM);
    }
    
    int getDepth() {
        if(children.isEmpty()) return 1;
        
        ArrayList<Integer> childrenSum = new ArrayList<>();
        children.keySet().iterator()
                .forEachRemaining(d -> childrenSum.add(children.get(d).getDepth()));
        
        return (Collections.max(childrenSum) + 1);
    }

    int getSize() {
        if(children.isEmpty()) return 1;
        
        IntegerProperty sum = new SimpleIntegerProperty(0);
        children.keySet().iterator()
                .forEachRemaining(d -> sum.set(sum.intValue() + children.get(d).getSize()));
        
        return (sum.getValue() + 1);
    }
    
    public String getNodeAtribute()
    {
        return attribute;
    }
}
