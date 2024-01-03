package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a restaurant with a name, environment, cuisine, rating, total expenses (in dollars) and party size
public class Restaurant implements Writable {
    private String name;            // Restaurant name
    private String environment;     // Restaurant Environment (Casual or Upscale)
    private String cuisine;         // Cuisine type
    private double rating;          // Overall restaurant rating
    private double expenses;        // Total spent at the restaurant
    private int party;              // Party size

    // REQUIRES: restaurantName, diningEnvironment, cuisineType all have non-zero length, partySize is > 0, and
    //           overallRating, totalExpenses are both >= 0
    // MODIFIES: this
    // EFFECTS: Constructs a restaurant with given restaurantName, diningEnvironment, cuisineType, overallRating,
    //          totalExpenses, and partySize.
    public Restaurant(String restaurantName, String diningEnvironment, String cuisineType, double overallRating,
                      double totalExpenses, int partySize) {
        this.name = restaurantName;
        this.environment = diningEnvironment;
        this.cuisine = cuisineType;
        this.rating = overallRating;
        this.expenses = totalExpenses;
        this.party = partySize;
    }

    // REQUIRES: newRating >= 0
    // MODIFIES: this
    // EFFECTS: replaces the overall rating of the restaurant with the new rating
    public void changeRating(double newRating) {
        this.rating = newRating;
        EventLog.getInstance().logEvent(new Event("Revised an Existing Restaurant's Rating!"));
    }

    // MODIFIES: this
    // EFFECTS: calculates the price per person to dine at a specific restaurant by dividing totalExpenses
    //          by the partySize
    public double pricePerPerson() {
        return Math.round(getExpenses() / getParty());
    }

    // EFFECTS: gets the name of the restaurant
    public String getName() {
        return this.name;
    }

    // EFFECTS: gets the name of the restaurant environment
    public String getEnvironment() {
        return this.environment;
    }

    // EFFECTS: gets the name of the restaurant cuisine
    public String getCuisine() {
        return this.cuisine;
    }

    // EFFECTS: gets the overall rating of the restaurant
    public double getRating() {
        return this.rating;
    }

    // EFFECTS: gets the total expenses at the restaurant
    public double getExpenses() {
        return this.expenses;
    }

    // EFFECTS: gets the party size that dined at the restaurant
    public int getParty() {
        return this.party;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("restaurantName", name);
        json.put("diningEnvironment", environment);
        json.put("cuisineType", cuisine);
        json.put("overallRating", rating);
        json.put("totalExpenses", expenses);
        json.put("partySize", party);
        return json;
    }
}
