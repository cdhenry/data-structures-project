package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.PropertyValue;

import java.util.List;

public class MarketValueReducer implements PropertyValueReducer {
    protected List<PropertyValue> properties;

    public MarketValueReducer(List<PropertyValue> properties) {
        this.properties = properties;
    }

    @Override
    public double reduce() {
        double total = 0.0;

        for (PropertyValue property : this.properties) {
            total += property.getMarketValue();
        }

        return total;
    }
}
