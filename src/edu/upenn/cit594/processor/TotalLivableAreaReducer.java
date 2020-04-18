package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.PropertyValue;

import java.util.LinkedList;
import java.util.List;

public class TotalLivableAreaReducer implements PropertyValueReducer {
    List<PropertyValue> properties;

    public TotalLivableAreaReducer() {
        this.properties = new LinkedList<>();
    }

    @Override
    public double reduce() {
        double total = 0.0;

        for (PropertyValue property : this.properties) {
            total += property.getTotalLivableArea();
        }

        return total;
    }
}
