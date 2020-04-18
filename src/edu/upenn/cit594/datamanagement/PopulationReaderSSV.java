package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.Population;
import edu.upenn.cit594.logging.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Uses a scanner to parse a space separated text file for population data
 *
 * @author Chris Henry + Tim Chung
 */
public class PopulationReaderSSV {
    private static final String SPACE = " ";
    protected File file;

    /**
     * Takes in a filename and stores it for use on a comma separated text file
     *
     * @param file an existing file opened with FileReader
     */
    public PopulationReaderSSV(File file) {
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
            Logger.getInstance().log(String.format("%d %s\n", System.currentTimeMillis(), file.getName()));

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
