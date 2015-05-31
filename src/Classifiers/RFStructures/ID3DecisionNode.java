
package Classifiers.RFStructures;

import java.util.HashMap;
import java.util.List;

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
public class ID3DecisionNode <D> extends DecisionNode {

//    private HashMap<Object, ID3DecisionNode<D>> children;
//    private String attribute;
    
    public ID3DecisionNode(DecisionTree sP)
    {
        super(sP);
        attribute = null;
        children = new HashMap<>();
    }

    @Override
    void createTree(DecisionTreeInput dTI) {
        
        double [] entropyScores = new double[dTI.columnCount()];
        for (int i = 0; i < entropyScores.length; i++) {
            List column = dTI.getColumn(i);
            
        }
    }
}
