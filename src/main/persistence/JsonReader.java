package persistence;

import model.Event;
import model.EventLog;
import model.Restaurant;
import model.RestaurantCollection;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads restaurant collection from JSON data stored in file
// Code influenced by the JsonSerializationDemo
public class JsonReader {
    private String source;

    // EFFECTS: constructs a new JsonReader to read the source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads restaurant collection from file and returns it; if an error occurs
    //          while attempting to read the data from file, throw a IOException.
    public RestaurantCollection read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        EventLog.getInstance().logEvent(new Event("Loaded Previously Saved Collection!"));
        return parseRestaurantCollection(jsonObject);
    }

    // EFFECTS: reads the source file and returns it as a string
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses the restaurant collection from JSON object and returns the
    //          entire restaurant collection.
    private RestaurantCollection parseRestaurantCollection(JSONObject jsonObject) {
        RestaurantCollection restaurantCollection = new RestaurantCollection();
        addRestaurants(restaurantCollection, jsonObject);
        return restaurantCollection;
    }

    // MODIFIES: restaurantCollection
    // EFFECTS: parses Restaurants from JSON object and adds them all to the collection
    private void addRestaurants(RestaurantCollection restaurantCollection, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Restaurants");
        for (Object json : jsonArray) {
            JSONObject nextRestaurant =  (JSONObject) json;
            addRestaurant(restaurantCollection, nextRestaurant);
        }
    }

    // MODIFIES: restaurantCollection
    // EFFECTS: parses a restaurant from JSON object and adds it to the collection
    private void addRestaurant(RestaurantCollection restaurantCollection, JSONObject jsonObject) {
        String restaurantName = jsonObject.getString("restaurantName");
        String diningEnvironment = jsonObject.getString("diningEnvironment");
        String cuisineType = jsonObject.getString("cuisineType");
        double overallRating = jsonObject.getDouble("overallRating");
        double totalExpenses = jsonObject.getDouble("totalExpenses");
        int partySize = jsonObject.getInt("partySize");

        Restaurant restaurant = new Restaurant(restaurantName, diningEnvironment, cuisineType,
                overallRating, totalExpenses, partySize);
        restaurantCollection.addRestaurant(restaurant);
    }

}
