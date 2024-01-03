package persistence;

import model.Restaurant;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkRestaurant(String restaurantName, String diningEnvironment, String cuisineType,
                                   double overallRating, double totalExpenses, int partySize, Restaurant restaurant) {
        assertEquals(restaurantName, restaurant.getName());
        assertEquals(diningEnvironment, restaurant.getEnvironment());
        assertEquals(cuisineType, restaurant.getCuisine());
        assertEquals(overallRating, restaurant.getRating());
        assertEquals(totalExpenses, restaurant.getExpenses());
        assertEquals(partySize, restaurant.getParty());
    }
}
