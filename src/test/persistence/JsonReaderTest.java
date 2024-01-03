package persistence;

import model.Restaurant;
import model.RestaurantCollection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            RestaurantCollection restaurantCollection = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyRestaurantCollection() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyRestaurantCollection.json");
        try {
            RestaurantCollection restaurantCollection = reader.read();
            assertEquals(0, restaurantCollection.getRestaurantListSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralRestaurantCollection() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralRestaurantCollection.json");
        try {
            RestaurantCollection restaurantCollection = reader.read();
            List<Restaurant> restaurants = restaurantCollection.getRestaurantList();
            assertEquals(2, restaurantCollection.getRestaurantListSize());
            checkRestaurant("Maenam", "Upscale", "Contemporary",
                    8.7, 230.46, 2, restaurants.get(0));
            checkRestaurant("Subway", "Casual", "Fast Food",
                    6.5, 14.79, 1, restaurants.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
