package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.ParkingViolation;

import java.util.List;
import java.util.Map;

/**
 * An interface used to specify the methods for getting parking violations from a file
 *
 * @author Chris Henry + Tim Chung
 */
public interface ParkingViolationReader {
    /**
     * Maps a set of parking violations from a file by their zip codes
     *
     * @return a map of parking violation objects by their zip codes
     */
    Map<Integer, List<ParkingViolation>> getAllParkingViolations();
}
