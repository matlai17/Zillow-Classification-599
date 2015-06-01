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

import Classifiers.RandomForest;
import Resources.House;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
/**
 *
 * @author Matthew Lai
 */
public class RandomForestTrainer implements Callable<ID3DecisionTree> {
    
    private final List<List> dataPointListOfFeatures;
    private final List<String> featureNames;
    private final List<Integer> dataPointCategories;
    
    public RandomForestTrainer(RandomForest parent, List<House> training)
    {
        dataPointListOfFeatures = new ArrayList<>();
        featureNames = new ArrayList<>(training.get(0).getFeatures().keySet());
        dataPointCategories = new ArrayList<>();
        
        training.forEach(h -> {
            java.util.Map<String, Double> features = h.getFeatures();
            List houseFeatures = new ArrayList();
            
            dataPointCategories.add(parent.determineCategory(h.getPriceSold()));
            
            featureNames.forEach(name -> { houseFeatures.add(features.get(name)); });
            
            dataPointListOfFeatures.add(houseFeatures);
        });
    }

    @Override
    public ID3DecisionTree<Integer> call() throws Exception {
        return new ID3DecisionTree<>(featureNames, dataPointListOfFeatures, dataPointCategories, true);
    }
    
}
