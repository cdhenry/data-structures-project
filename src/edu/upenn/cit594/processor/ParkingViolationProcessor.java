package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.datamanagement.MappableByInteger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Stores a list of ParkingViolation objects and contains methods for performing various operations on the list
 *
 * @author Chris Henry + Tim Chung
 */
public class ParkingViolationProcessor implements Runnable {
    protected Map<Integer, List<ParkingViolation>> parkingViolationsMap;
    protected Map<Integer, Double> totalFinesByZip;
    protected MappableByInteger<List<ParkingViolation>> parkingViolationReader;

    /**
     * Constructs a ParkingViolationProcessor to store a set of ParkingViolation objects created by the
     * ParkingViolationReader class
     *
     * @param parkingViolationReader reader for parking violation data
     */
    public ParkingViolationProcessor(MappableByInteger<List<ParkingViolation>> parkingViolationReader) {
        this.parkingViolationReader = parkingViolationReader;
        this.totalFinesByZip = new HashMap<>();
    }

    @Override
    public void run() {
        this.parkingViolationsMap = parkingViolationReader.getIntegerMap();
    }

    /**
     * @return parking violations by zip code
     */
    public Map<Integer, List<ParkingViolation>> getParkingViolationsByZip() {
        return parkingViolationsMap;
    }

    /**
     * @param zipCode         zip code to search for fines
     * @param localPopulation population of the zip code
     * @return total fines per capita by zip
     */
    public double getTotalFinesPerCapita(int zipCode, int localPopulation) {
        if (localPopulation > 0) {
            double totalFines = getTotalFinesByZip(zipCode);
            return totalFines / localPopulation;
        }

        return 0.0;
    }

    /**
     * @param zipCode zip code to aggregate fines
     * @return total fines in a certain zip code
     */
    private double getTotalFinesByZip(int zipCode) {
        if (!totalFinesByZip.containsKey(zipCode)) {
            List<ParkingViolation> violations = getParkingViolationsByZip().get(zipCode);
            double totalFines = 0.0;

            if (violations == null) {
                totalFinesByZip.put(zipCode, totalFines);
                return totalFines;
            }

            for (ParkingViolation violation : violations) {
                if (violation.getVehicleState().equals("PA")) {
                    totalFines += violation.getFineInDollars();
                }
            }
            totalFinesByZip.put(zipCode, totalFines);
        }
        return totalFinesByZip.get(zipCode);
    }

    /**
     * @param zipCode         zip code to seek out aggregated fines
     * @param localPopulation population of the passed in zip code
     * @return average fines in a certain zip code divided by local population
     */
    public double getAvgFinePerCapita(int zipCode, int localPopulation) {
        if (localPopulation > 0) {
            double totalFines = getTotalFinesByZip(zipCode);
            return totalFines / localPopulation;
        }

        return 0.0;
    }
}
