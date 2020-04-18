package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.PropertyValue;

import java.util.LinkedList;
import java.util.List;

public class MarketValueReducer implements PropertyValueReducer {
    List<PropertyValue> properties;

    public MarketValueReducer() {
        this.properties = new LinkedList<>();
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
