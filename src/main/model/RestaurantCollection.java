package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// Represents a collection of restaurants
public class RestaurantCollection implements Writable {
    final List<Restaurant> restaurantList;

    // EFFECTS: Constructs an empty list of restaurants
    public RestaurantCollection() {
        this.restaurantList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a restaurant to the collection (list) of restaurants and return true;
    //          items are maintained in the order in which they were added. If restaurant
    //          is already in the list, then do not add again and return false.
    public Boolean addRestaurant(Restaurant restaurant) {
        if (!isContained(restaurant)) {
            restaurantList.add(restaurant);
            EventLog.getInstance().logEvent(new Event("Added a New Restaurant to the Collection!"));
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: removes a restaurant in the collection (list) of restaurants and return True as that
    //          restaurant is permanently closed for business.
    //          If the restaurant being removed is not contained in the list, then return False.
    public Boolean removeRestaurant(Restaurant restaurant) {
        if (isContained(restaurant)) {
            this.restaurantList.remove(restaurant);
            EventLog.getInstance().logEvent(new Event("Removed a Restaurant from the Collection!"));
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: returns true if the given restaurant is contained in the list; false otherwise.
    public Boolean isContained(Restaurant restaurant) {
        return restaurantList.contains(restaurant);
    }

    // EFFECTS: returns the size of the restaurant list
    public int getRestaurantListSize() {
        return restaurantList.size();
    }

    // EFFECTS: returns all restaurants added to the restaurantList so far
    public List<Restaurant> getRestaurantList() {
        return restaurantList;
    }

    // EFFECTS: return the restaurant of given name if the restaurant is in the list, if restaurant
    //           is not in the list then return null
    public Restaurant getRestaurant(String restaurantName) {
        for (Restaurant restaurant : restaurantList) {
            if (restaurant.getName().equalsIgnoreCase(restaurantName)) {
                return restaurant;
            }
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: returns the top 3 restaurants (in order of highest rating) that match the criteria
    //          (environment, cuisine and price). Specifically, the price range is defined where
    //          restaurantPrice <= price.
    public List<Restaurant> topThreeRecommendations(String environment, String cuisine, double price) {
        List<Restaurant> allRestaurants = getRestaurantList();
        List<Restaurant> matchingRestaurants = new ArrayList<>();

        for (Restaurant nextRestaurant : allRestaurants) {
            if (nextRestaurant.getEnvironment().equalsIgnoreCase(environment)
                    && nextRestaurant.getCuisine().equalsIgnoreCase(cuisine)
                    && nextRestaurant.pricePerPerson() <= price) {
                matchingRestaurants.add(nextRestaurant);
            }
        }

        Collections.sort(matchingRestaurants, new SortByRating());
        Collections.reverse(matchingRestaurants);
        EventLog.getInstance().logEvent(new Event("Gave Some Restaurant Recommendations!"));
        return matchingRestaurants.subList(0, Math.min(3, matchingRestaurants.size()));
    }

    // a custom comparator that sorts a list of restaurants
    static class SortByRating implements Comparator<Restaurant> {
        @Override
        public int compare(Restaurant first, Restaurant second) {
            return Double.compare(first.getRating(), second.getRating());
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Restaurants", restaurantsToJson());
        return json;
    }

    // EFFECTS: returns restaurants in this collection as a JSON array
    private JSONArray restaurantsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Restaurant restaurant : restaurantList) {
            jsonArray.put(restaurant.toJson());
        }

        return jsonArray;
    }
}




