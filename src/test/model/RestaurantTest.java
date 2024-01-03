package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RestaurantTest {
    private Restaurant testRestaurantOne;
    private Restaurant testRestaurantTwo;

    @BeforeEach
    void runBefore() {
        testRestaurantOne = new Restaurant("McDonalds", "Casual", "Fast Food",
                6.8, 19.23, 1);
        testRestaurantTwo = new Restaurant("Published On Main", "Upscale",
                "Contemporary", 9.5, 335.44, 2);
    }

    @Test
    void testConstructor() {
        assertEquals("McDonalds", testRestaurantOne.getName());
        assertEquals("Casual", testRestaurantOne.getEnvironment());
        assertEquals("Fast Food", testRestaurantOne.getCuisine());
        assertEquals(6.8, testRestaurantOne.getRating());
        assertEquals(19.23, testRestaurantOne.getExpenses());
        assertEquals(1, testRestaurantOne.getParty());
    }

    @Test
    void testEditRatingOnce() {
        testRestaurantOne.changeRating(5.7);
        assertEquals(5.7, testRestaurantOne.getRating());
    }

    @Test
    void testEditRatingMultiple() {
        testRestaurantOne.changeRating(5.7);
        assertEquals(5.7, testRestaurantOne.getRating());

        testRestaurantOne.changeRating(7.1);
        assertEquals(7.1, testRestaurantOne.getRating());
    }

    @Test
    void pricePerOnePerson() {
        assertEquals(19.0, testRestaurantOne.pricePerPerson());
    }

    @Test
    void pricePerMultiplePeople() {
        assertEquals(168, testRestaurantTwo.pricePerPerson());
    }


}