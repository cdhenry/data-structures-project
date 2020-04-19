package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Population;
import edu.upenn.cit594.datamanagement.PopulationReaderSSV;

import java.util.List;

/**
 * Stores a list of Population objects and contains methods for performing various operations on the list
 *
 * @author Chris Henry + Tim Chung
 */
public class PopulationProcessor {
    PopulationReaderSSV populationReader;
    List<Population> populations;

    /**
     * Constructs a Population to store a set of Population objects created by the PopulationReader class
     *
     * @param populationReader
     */
    public PopulationProcessor(PopulationReaderSSV populationReader) {
        this.populationReader = populationReader;
        this.populations = populationReader.getAllPopulations();
    }

    /**
     *
     * @return total population
     */
    public long getTotalPopulation() {
        int totalPopulation = 0;
        for (Population pop : populations) {
            totalPopulation += pop.getPopulation();
        }
        return totalPopulation;
    }
}
