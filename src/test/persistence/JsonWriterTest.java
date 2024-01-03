package persistence;

import model.Restaurant;
import model.RestaurantCollection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest{

    @Test
    void testWriterIllegalFile() {
        try {
            RestaurantCollection restaurantCollection = new RestaurantCollection();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:filename.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testWriterEmptyRestaurantCollection() {
        try {
            RestaurantCollection restaurantCollection = new RestaurantCollection();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyRestaurantCollection.json");
            writer.open();
            writer.write(restaurantCollection);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyRestaurantCollection.json");
            restaurantCollection = reader.read();
            assertEquals(0, restaurantCollection.getRestaurantListSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralRestaurantCollection() {
        try {
            RestaurantCollection restaurantCollection = new RestaurantCollection();
            restaurantCollection.addRestaurant(new Restaurant("Kissa Tanto", "Upscale",
                    "Contemporary", 9.1, 314.99, 2));
            restaurantCollection.addRestaurant(new Restaurant("McDonald's", "Casual",
                    "Fast Food", 6.29, 13.99, 1));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralRestaurantCollection.json");
            writer.open();
            writer.write(restaurantCollection);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralRestaurantCollection.json");
            restaurantCollection = reader.read();
            List<Restaurant> restaurants = restaurantCollection.getRestaurantList();
            assertEquals(2, restaurantCollection.getRestaurantListSize());
            checkRestaurant("Kissa Tanto", "Upscale", "Contemporary",
                    9.1, 314.99, 2, restaurants.get(0));
            checkRestaurant("McDonald's", "Casual", "Fast Food",
                    6.29, 13.99, 1, restaurants.get(1));
        } catch (IOException e) {
            fail ("Exception should not have been thrown");
        }
    }

}