
package Classifiers.RFStructures;

/**
 *
 * @author Matthew Lai and Arun Singh
 * 
 * @param <D>
 */
public class ID3TerminalDecisionNode <D> extends ID3DecisionNode {

    private java.util.HashSet<D> decisions;
    ID3TerminalDecisionNode(java.util.HashSet<D> decisions) {
        super();
        this.decisions = decisions;
    }
    
    protected ID3TerminalDecisionNode(java.util.List<D> decisions) {
        super();
        this.decisions = new java.util.HashSet<>(decisions);
    }
    
//    ID3TerminalDecisionNode(DecisionTreeInput<D> dTI, java.util.HashSet<Integer> validDataPoints) {
//        super();
//        this.decisions = new java.util.HashSet<>();
//        for (int i = 0; i < dTI.getDataPointCount(); i++) 
//            if(validDataPoints.contains(i)) decisions.add(dTI.getDependentAttribute(i));
//    }
    
    ID3TerminalDecisionNode(DecisionTreeMatrix<D> dTI) {
        super();
        this.decisions = new java.util.HashSet<>();
        for (int i = 0; i < dTI.getDataPointCount(); i++) decisions.add(dTI.getDependentAttribute(i));
    }
    
    public java.util.List<D> getDecisions()
    {
        return new java.util.ArrayList<>(decisions);
    }
    
}
