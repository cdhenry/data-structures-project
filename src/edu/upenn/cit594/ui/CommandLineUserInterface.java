package edu.upenn.cit594.ui;

import edu.upenn.cit594.processor.ParkingViolationProcessor;
import edu.upenn.cit594.processor.PopulationProcessor;
import edu.upenn.cit594.processor.PropertyValueProcessor;

/**
 * A command line interface class for outputting text via the console to the user
 *
 * @author Chris Henry + Tim Chung
 */
public class CommandLineUserInterface {
    protected ParkingViolationProcessor parkingViolationProcessor;
    protected PopulationProcessor populationProcessor;
    protected PropertyValueProcessor propertyValueProcessor;

    /**
     * Constructs a CommandLineUserInterface class for interaction with the user.
     * Uses processor classes to deliver the desired information to the user.
     *
     * @param parkingViolationProcessor a parking violation processor
     * @param populationProcessor a population processor
     * @param propertyValueProcessor a property value processor
     */
    public CommandLineUserInterface(ParkingViolationProcessor parkingViolationProcessor,
                                    PopulationProcessor populationProcessor,
                                    PropertyValueProcessor propertyValueProcessor) {
        this.parkingViolationProcessor = parkingViolationProcessor;
        this.populationProcessor = populationProcessor;
        this.propertyValueProcessor = propertyValueProcessor;
    }

    /**
     * Starts the UI sequence
     */
    public void start() {

    }
}
