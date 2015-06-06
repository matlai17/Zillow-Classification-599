
package Classifiers.RFStructures;

/**
 * The terminal node containing the final decision. This decision will be returned when
 * a decision is requested and the object branches to this node.
 * 
 * @author Matthew Lai
 * 
 * @param <D>
 */
public class ID3TerminalDecisionNode <D> extends ID3DecisionNode {

//    private java.util.HashSet<D> decisions;
    private final D decision;
    ID3TerminalDecisionNode(D decision) {
        super();
        this.decision = decision;
    }
    
    @Override
    public D getDecision(DecisionTreeMatrix dTM)
    {
        return getDecision();
    }
    
    public D getDecision(){
        return decision;
    }
    
}
