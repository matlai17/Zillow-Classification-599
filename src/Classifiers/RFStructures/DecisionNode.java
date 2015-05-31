/*
 * Copyright (C) 2015 Matthew Lai
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package Classifiers.RFStructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Matthew Lai
 * @param <D> Decision object type
 */
public abstract class DecisionNode <D> {
    
    protected HashMap<Object, ID3DecisionNode<D>> children;
    protected String attribute;
    protected final DecisionTree superParent;
    
    public DecisionNode(DecisionTree sP)
    {
        superParent = sP;
    }

    abstract void createTree(DecisionTreeInput dTI);
    
    protected int getDepth() {
        if(children.isEmpty()) return 1;
        
        ArrayList<Integer> childrenSum = new ArrayList<>();
        children.keySet().iterator()
                .forEachRemaining(d -> childrenSum.add(children.get(d).getDepth()));
        
        return (Collections.max(childrenSum) + 1);
    }

    protected int getSize() {
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