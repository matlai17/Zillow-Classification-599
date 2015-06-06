
package Classifiers.RFStructures;

import Resources.DecisionTreeMismatchedArgumentsException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The DecisionTreeMatrix contains the attribute information needed to construct a 
 * decision tree. The information is passed to the decision tree which will orient
 * the data in such a way as to make constructing a decision tree easier. It stores
 * the data column-wise as there requires a lot of column calculations. It also allows
 * for the decision tree generation algorithm to designate the valid rows and columns for
 * each next branch to perform its operations on.
 * 
 * @author Matthew Lai
 */
public class DecisionTreeMatrix <D> {
    
    private final boolean queryInput;
    private List<List> verticalAttributes;
    private final List<D> dependentAttribute;
    private final List<String> attributeLabels;
    private final HashSet<Integer> validDataPoints;
    private final HashSet<Integer> validColumns;
    
    public DecisionTreeMatrix(List<String> attributeLabels, List<List> attributes, List<D> dependentAttribute)
    {
        if(attributes.size() != dependentAttribute.size()) 
            try { throw new DecisionTreeMismatchedArgumentsException(); } 
            catch (DecisionTreeMismatchedArgumentsException ex) { Logger.getLogger(DecisionTreeMatrix.class.getName()).log(Level.SEVERE, null, ex); }
        
        this.dependentAttribute = new ArrayList<>(dependentAttribute);
        this.attributeLabels = new ArrayList<>(attributeLabels);
        this.verticalAttributes = new ArrayList<>();
        this.validDataPoints = new HashSet<>();
        this.validColumns = new HashSet<>();
        
        for(int i = 0; i < attributeLabels.size(); i++) verticalAttributes.add(new ArrayList());
        
        attributes.stream().forEachOrdered(L -> {
            if(L.size() != attributeLabels.size()) 
                try { throw new DecisionTreeMismatchedArgumentsException("Mismatch with the input data attribute count. All data points must contain the same number of data points."); }
                catch(DecisionTreeMismatchedArgumentsException ex) { Logger.getLogger(DecisionTreeMatrix.class.getName()).log(Level.SEVERE, null, ex); }
            for(int i = 0; i < L.size(); i++) verticalAttributes.get(i).add(L.get(i));
        });
        queryInput = false;
        
        for(int i = 0; i < dependentAttribute.size(); i++) validDataPoints.add(i);
        for(int i = 0; i < attributeLabels.size(); i++) validColumns.add(i);
    }
    
    public DecisionTreeMatrix(List<String> attributeLabels, List<List> attributes)
    {
        if(attributes.size() != 1) 
            try { throw new DecisionTreeMismatchedArgumentsException("Your query can only contain a single sample."); } 
            catch (DecisionTreeMismatchedArgumentsException ex) { Logger.getLogger(DecisionTreeMatrix.class.getName()).log(Level.SEVERE, null, ex); }
        
        this.dependentAttribute = new ArrayList<>();
        this.attributeLabels = new ArrayList<>(attributeLabels);
        verticalAttributes = new ArrayList<>();
        this.validDataPoints = new HashSet<>();
        this.validColumns = new HashSet<>();
        
        for(int i = 0; i < attributeLabels.size(); i++) verticalAttributes.add(new ArrayList());
        
        attributes.stream().forEachOrdered(L -> {
            if(L.size() != attributeLabels.size()) 
                try { throw new DecisionTreeMismatchedArgumentsException("Mismatch with the input data attribute count. All data points must contain the same number of data points."); }
                catch(DecisionTreeMismatchedArgumentsException ex) { Logger.getLogger(DecisionTreeMatrix.class.getName()).log(Level.SEVERE, null, ex); }
            for(int i = 0; i < L.size(); i++) verticalAttributes.get(i).add(L.get(i));
        });
        queryInput = true;
    }
    
    
    protected DecisionTreeMatrix(DecisionTreeMatrix dTI, int removeColumnIndex, HashSet<Integer> validDataPoints)
    {
        this.attributeLabels = dTI.attributeLabels;
        
        this.dependentAttribute = dTI.dependentAttribute;
        
        this.verticalAttributes = dTI.verticalAttributes;
        
        this.validColumns = new HashSet<>(dTI.validColumns);
        this.validColumns.remove(removeColumnIndex);
        
        this.validDataPoints = validDataPoints;
        
        this.queryInput = dTI.queryInput;
    }
    
    public boolean isQueryInput() { return queryInput; }
    
    public List removeAttributeColumn(int index) 
    { 
        attributeLabels.remove(index);
        return verticalAttributes.remove(index); 
    }
    
    public List getColumn(int index) { return verticalAttributes.get(index); }
    
    public String getColumnName(int index) { return attributeLabels.get(index); }
    
    public D getDependentAttribute(int index) { return dependentAttribute.get(index); }
    
    public int columnCount() { return verticalAttributes.size(); }
    
    public int getDataPointCount() { return dependentAttribute.size(); }
    
    public List<String> getColumnNames() { return attributeLabels; }
    
    public HashSet<Integer> getValidColumns() { return validColumns; }
    
    public HashSet<Integer> getValidDataPoints() { return validDataPoints; }
    
    public int checkHomogenyCount()
    {
        HashSet depSet = new HashSet();   
        for (Integer i = 0; i < getDataPointCount(); i++)
            if(validDataPoints.contains(i))
                depSet.add(getDependentAttribute(i));
        
        return depSet.size();
    }
    
    public List<D> getDependents()
    {
        
        return dependentAttribute;
    }
    
    @Override
    public String toString()
    {
        String ret = "Columns: ";
        
        java.util.Iterator<Integer> it = validColumns.iterator();
        while(it.hasNext()) ret+=" " + it.next();
        ret += "\nRows: ";
        it = validDataPoints.iterator();
        while(it.hasNext()) ret+=" " + it.next();
        
        
        return ret;
    }
    
}
