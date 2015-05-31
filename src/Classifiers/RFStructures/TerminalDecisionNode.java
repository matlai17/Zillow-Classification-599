
package Classifiers.RFStructures;

/**
 *
 * @author Matthew Lai and Arun Singh
 * 
 * @param <D>
 */
public class TerminalDecisionNode <D> extends DecisionNode {

    D decision;
    public TerminalDecisionNode(DecisionTree sP, D decision) {
        super(sP);
        this.decision = decision;
    }
    
    public D decision()
    {
        return decision;
    }

    @Override
    void createTree(DecisionTreeInput dTI) {
        throw new UnsupportedOperationException("Terminal node should not perform createTree operation.");
    }
    
}
