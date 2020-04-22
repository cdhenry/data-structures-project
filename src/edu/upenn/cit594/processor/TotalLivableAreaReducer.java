package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.PropertyValue;

import java.util.List;

public class TotalLivableAreaReducer implements PropertyValueReducer {
    protected List<PropertyValue> properties;

    public TotalLivableAreaReducer(List<PropertyValue> properties) {
        this.properties = properties;
    }

    @Override
    public double getTotal() {
        double total = 0.0;

        for (PropertyValue property : this.properties) {
            total += property.getTotalLivableArea();
        }

        return total;
    }
}
