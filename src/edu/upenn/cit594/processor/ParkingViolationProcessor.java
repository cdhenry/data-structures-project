package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.datamanagement.ParkingViolationReader;

import java.util.*;

/**
 * Stores a list of ParkingViolation objects and contains methods for performing various operations on the list
 *
 * @author Chris Henry + Tim Chung
 */
public class ParkingViolationProcessor {
    protected ParkingViolationReader parkingViolationReader;
    protected Map<Integer, List<ParkingViolation>> parkingViolationsMap;
    protected Map<Integer, Double> totalFinesPerZip;

    /**
     * Constructs a ParkingViolationProcessor to store a set of ParkingViolation objects created by the
     * ParkingViolationReader class
     *
     * @param parkingViolationReader reader for parking violation data
     */
    public ParkingViolationProcessor(ParkingViolationReader parkingViolationReader) {
        this.parkingViolationReader = parkingViolationReader;
        this.parkingViolationsMap = parkingViolationReader.getAllParkingViolations();
        this.totalFinesPerZip = new HashMap<>();
    }

    /**
     * @return parking violations by zip code
     */
    public Map<Integer, List<ParkingViolation>> getParkingViolationsByZip() {
        return parkingViolationsMap;
    }

    /**
     * @return total memoized fines for zip code
     */

    public double getTotalFinesPerCapita(int zipCode, int population) {
        Double totalFines = getTotalFinesByZip(zipCode);
        return totalFines / population;
    }

    /**
     * @return total memoized fines for zip code
     */
    private double getTotalFinesByZip(int zipCode) {
        if (!totalFinesPerZip.containsKey(zipCode)) {
            List<ParkingViolation> violations = getParkingViolationsByZip().get(zipCode);
            double totalFines = 0.0;

            if (violations == null) {
                totalFinesPerZip.put(zipCode, totalFines);
                return totalFines;
            }

            for (ParkingViolation violation : violations) {
                if (violation.getVehicleState().equals("PA")) {
                    totalFines += violation.getFineInDollars();
                }
            }
            totalFinesPerZip.put(zipCode, totalFines);
        }
        return totalFinesPerZip.get(zipCode);
    }

    public double getAvgFinePerCapita(int zipCode, int population) {
        double totalFines = getTotalFinesByZip(zipCode);
        return totalFines / population;
    }


}
