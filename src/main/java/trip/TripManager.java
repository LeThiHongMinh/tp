package trip;

import exception.TravelDiaryException;
import exception.IndexOutOfRangeException;

import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;

public class TripManager {
    private static final Logger logger = Logger.getLogger(TripManager.class.getName());
    private final List<Trip> trips = new ArrayList<>();
    private Trip selectedTrip = null;
    private boolean silentMode = false;

    /**
     * Enable or disable silent mode to prevent console output during operations
     */
    public void setSilentMode(boolean silentMode) {
        this.silentMode = silentMode;
    }

    /**
     * Returns current silent mode state
     */
    public boolean isSilentMode() {
        return silentMode;
    }
    /**
     * Adds a trip and optionally displays the updated list
     */
    public void addTrip(String name, String description) throws TravelDiaryException {
        logger.info("Adding a new trip: " + name);
        trips.add(new Trip(name, description));
        logger.info("Trip added successfully: " + name);
        System.out.printf("\tTrip [%s] has been added successfully.\n", name);
    }

    /**
     * Adds a trip silently, without viewing the updated list
     */
    public Trip addTripSilently(String name, String description) throws TravelDiaryException {
        logger.info("Adding a new trip silently: " + name);
        Trip newTrip = new Trip(name, description);
        trips.add(newTrip);
        logger.info("Trip added silently: " + name);
        return newTrip;
    }


    public void setSelectedTrip(Trip selectedTrip) {
        logger.info("Setting selected trip: " + (selectedTrip != null ? selectedTrip.name : "null"));
        this.selectedTrip = selectedTrip;
    }

    public void deleteTrip(int index) throws IndexOutOfRangeException {
        logger.info("Attempting to delete trip at index: " + index);
        if (index < 0 || index >= trips.size()) {
            throw new IndexOutOfRangeException();
        }
        logger.info("Trip deleted: " + trips.get(index).name);
        trips.remove(index);

        if (!silentMode) {
            System.out.println("Trip deleted successfully.");
        }
    }

    public void viewTrips() {
        logger.info("Viewing all trips.");

        if (silentMode) {
            return; // Skip printing in silent mode
        }

        if (trips.isEmpty()) {
            logger.warning("No trips available.");
            System.out.println("\n\tNo trips available. Start adding a new trip now!");
        } else {
            for (int i = 0; i < trips.size(); i++) {
                System.out.println(i + 1 + ": " + trips.get(i)); // Display index with trip details
            }
        }
    }

    public void selectTrip(int index) throws IndexOutOfRangeException {
        logger.info("Selecting trip at index: " + index);
        if (index < 0 || index >= trips.size()) {
            logger.severe("Invalid trip index: " + index);
            throw new IndexOutOfRangeException();
        }
        selectedTrip = trips.get(index);
        logger.info("Selected trip: " + selectedTrip.name);

        if (!silentMode) {
            System.out.println("\tSelected trip: " + selectedTrip);
        }
    }

    public Trip getSelectedTrip() {
        logger.info("Retrieving selected trip.");
        assert selectedTrip != null : "Selected trip should not be null";
        return this.selectedTrip;
    }

    /**
     * Notifies that trips have been fully loaded
     * Call this after loading all trips from storage to trigger a single UI update
     */
    public void notifyTripsLoaded() {
        if (!silentMode) {
            System.out.println("All trips loaded successfully.");
        }
    }

    @Override
    public String toString() {
        StringBuilder tripsDetails = new StringBuilder();
        for (int i = 0; i < trips.size(); i++) {
            tripsDetails.append("\t").append(i + 1).append(") ")
                    .append(trips.get(i).toString());
        }
        return tripsDetails.toString();
    }

    public List<Trip> getTrips() {
        return trips;
    }
}
