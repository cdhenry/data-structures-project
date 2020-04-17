package edu.upenn.cit594.data;

import java.util.Date;

/**
 * A class for parking violations which includes their date and time, fine in dollars, description,
 * identifying information about (vehicle ID + state), a violation ID, and the zipcode of where the violation
 * occurred
 *
 * @author Chris Henry + Tim Chung
 *
 */
public class ParkingViolation {
    Date timeStamp;
    double fineInDollars;
    String description, vehicleId, vehicleState, id, zipCode;

    /**
     * @param timeStamp
     * @param fineInDollars
     * @param description
     * @param vehicleId
     * @param vehicleState
     * @param id
     * @param zipCode
     */
    public ParkingViolation(Date timeStamp, double fineInDollars, String description, String vehicleId,
                            String vehicleState, String id, String zipCode) {
        this.timeStamp = timeStamp;
        this.fineInDollars = fineInDollars;
        this.description = description;
        this.vehicleId = vehicleId;
        this.vehicleState = vehicleState;
        this.id = id;
        this.zipCode = zipCode;
    }

    /**
     * @return timeStamp
     */
    public Date getTimeStamp() {
        return timeStamp;
    }

    /**
     * @return fineInDollars
     */
    public double getFineInDollars() {
        return fineInDollars;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return vehicleId
     */
    public String getVehicleId() {
        return vehicleId;
    }

    /**
     * @return vehicleState
     */
    public String getVehicleState() {
        return vehicleState;
    }

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @return zipCode
     */
    public String getZipCode() {
        return zipCode;
    }
}
