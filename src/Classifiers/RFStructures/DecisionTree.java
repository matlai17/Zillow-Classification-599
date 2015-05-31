
package Classifiers.RFStructures;

import Resources.DecisionTreeMismatchedArgumentsException;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Matthew Lai and Arun Singh
 * @param <D>
 */
public class DecisionTree <D> {
    
    ID3DecisionNode root;
    
    public DecisionTree(List<String> attributeNames, List<List> attr, List<D> dep)
    {
        DecisionTreeInput dTI = new DecisionTreeInput(attributeNames, attr, dep);
        root = new ID3DecisionNode(this);
        root.createTree(dTI);
    }
    
    public List<D> getDecisions(List<String> attributeNames, List<List> attr)
    {
        DecisionTreeInput dTI = new DecisionTreeInput(attributeNames, attr);
//        root.getDecisions(dTI);
        return new ArrayList<D>();
    }
    
    public int getSize()
    {
        return 1 + root.getSize();
    }
    
    public int getDepth()
    {
        return 1 + root.getDepth();
    }
    
    public ID3DecisionNode getRoot()
    {
        return root;
    }
    
}

class DecisionTreeInput <D> {
    
    private List<List> verticalAttributes;
    private final List<D> dependentAttribute;
    private final List<String> attributeLabels;
    
    public DecisionTreeInput(List<String> attributeLabels, List<List> attributes, List<D> dependentAttribute)
    {
        if(attributes.size() != dependentAttribute.size()) 
            try { throw new DecisionTreeMismatchedArgumentsException(); } 
            catch (DecisionTreeMismatchedArgumentsException ex) { Logger.getLogger(DecisionTreeInput.class.getName()).log(Level.SEVERE, null, ex); }
        
        this.dependentAttribute = new ArrayList<>(dependentAttribute);
        this.attributeLabels = new ArrayList<>(attributeLabels);
        verticalAttributes = new ArrayList<>();
        
        for(int i = 0; i < attributeLabels.size(); i++) verticalAttributes.add(new ArrayList());
        
        attributes.stream().forEachOrdered(L -> {
            if(L.size() != attributeLabels.size()) 
                try { throw new DecisionTreeMismatchedArgumentsException("Mismatch with the input data attribute count. All data points must contain the same number of data points."); }
                catch(DecisionTreeMismatchedArgumentsException ex) { Logger.getLogger(DecisionTreeInput.class.getName()).log(Level.SEVERE, null, ex); }
            for(int i = 0; i < L.size(); i++) verticalAttributes.get(i).add(L.get(i));
        });
    }
    
    public DecisionTreeInput(List<String> attributeLabels, List<List> attributes)
    {
        this.dependentAttribute = null;
        this.attributeLabels = new ArrayList<>(attributeLabels);
        verticalAttributes = new ArrayList<>();
        
        for(int i = 0; i < attributeLabels.size(); i++) verticalAttributes.add(new ArrayList());
        
        attributes.stream().forEachOrdered(L -> {
            if(L.size() != attributeLabels.size()) 
                try { throw new DecisionTreeMismatchedArgumentsException("Mismatch with the input data attribute count. All data points must contain the same number of data points."); }
                catch(DecisionTreeMismatchedArgumentsException ex) { Logger.getLogger(DecisionTreeInput.class.getName()).log(Level.SEVERE, null, ex); }
            for(int i = 0; i < L.size(); i++) verticalAttributes.get(i).add(L.get(i));
        });
    }
    
    public void removeAttributeColumn(int index) { verticalAttributes.remove(index); }
    
    public List getColumn(int index) { return verticalAttributes.get(index); }
    
    public String getColumnName(int index) { return attributeLabels.get(index); }
    
    public D getDependentAttribute(int index) { return dependentAttribute.get(index); }
    
    public int columnCount() { return verticalAttributes.size(); }
    
    
}
