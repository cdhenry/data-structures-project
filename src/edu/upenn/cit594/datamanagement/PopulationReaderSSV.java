package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.Population;
import edu.upenn.cit594.logging.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * Uses a scanner to parse a space separated text file for population data
 *
 * @author Chris Henry + Tim Chung
 */
public class PopulationReaderSSV {
    private static final String FILE_ERR_MSG = "population file must exist and be readable";
    private static final String SPACE = " ";
    protected String filename;

    /**
     * Takes in a filename and stores it for use on a comma separated text file
     *
     * @param filename an SSV filename for a population file
     */
    public PopulationReaderSSV(String filename) {
        this.filename = filename;
    }

    /**
     * Gets a list of populations from a file
     *
     * @return a list of population objects
     */
    public List<Population> getAllPopulations() {
        List<Population> populations = new LinkedList<>();

        try {
            FileReader file = new FileReader(filename);
            Logger.getInstance().log(String.format("%d %s\n", System.currentTimeMillis(), filename));
            Scanner in = new Scanner(file);

            while (in.hasNextLine()) {
                String population = in.nextLine();
                String[] populationArray = population.trim().split(SPACE);

                String zipCode = populationArray[0].trim();
                if (zipCode.length() > 4) {
                    zipCode = zipCode.substring(0, 5);
                } else {
                    continue;
                }

                String populationCount = populationArray[1].trim();
                if (populationCount.length() == 0) {
                    continue;
                }

                populations.add(new Population(Integer.parseInt(zipCode), Integer.parseInt(populationCount)));
            }
        } catch (FileNotFoundException e) {
            System.out.println(FILE_ERR_MSG);
        }

        return populations;
    }
}
