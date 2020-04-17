package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.Population;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Uses a scanner to parse a space separated text file for population data
 *
 * @author Chris Henry + Tim Chung
 *
 */
public class PopulationReaderSSV {
    private static final String SPACE = " ";
    protected FileReader file;

    /**
     * Takes in a filename and stores it for use on a comma separated text file
     *
     * @param file an existing file opened with FileReader
     */
    public PopulationReaderSSV(FileReader file) {
        this.file = file;
    }

    /**
     * Gets a list of populations from a file
     *
     * @return a list of population objects
     */
    public List<Population> getAllPopulations() {
        List<Population> populations = new ArrayList<Population>();

        try (Scanner in = new Scanner(file)) {

            while (in.hasNextLine()) {
                String population = in.nextLine();
                String[] populationArray = population.trim().split(SPACE);

                String zipCode = populationArray[0];
                int populationCount = Integer.parseInt(populationArray[1]);

                populations.add(new Population(zipCode, populationCount));
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

        return populations;
    }
}
