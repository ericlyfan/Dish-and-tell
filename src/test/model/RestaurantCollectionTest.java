package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantCollectionTest {

    private RestaurantCollection testRestaurantCollection;
    Restaurant r1 = new Restaurant("Subway", "Casual", "Fast Food",
            7, 15.05, 1);
    Restaurant r2 = new Restaurant("Burgoo", "Casual", "Western",
            8, 35.21, 2);
    Restaurant r3 = new Restaurant("Published On Main", "Upscale",
            "Contemporary", 9.5, 335.44, 2);
    Restaurant r4 = new Restaurant("Mercante", "Casual", "Italian",
            7.2, 24.23, 2);
    Restaurant r5 = new Restaurant("McDonalds", "Casual", "Fast Food",
            6.5, 21.29, 1);
    Restaurant r6 = new Restaurant("Burger King", "Casual", "Fast Food",
            7.5, 25.29, 2);
    Restaurant r7 = new Restaurant("A&W", "Casual", "Fast Food",
            8, 23.23, 2);

    @BeforeEach
    void runBefore() {
        testRestaurantCollection = new RestaurantCollection();
    }

    @Test
    void testConstructor() {
        assertEquals(0, testRestaurantCollection.getRestaurantListSize());
        assertFalse(testRestaurantCollection.isContained(r1));
        assertFalse(testRestaurantCollection.isContained(r2));
    }

    @Test
    void testAddRestaurantOnce() {
        assertTrue(testRestaurantCollection.addRestaurant(r1));
        assertEquals(1, testRestaurantCollection.getRestaurantListSize());
        assertTrue(testRestaurantCollection.isContained(r1));
    }

    @Test
    void testAddRestaurantMultiple() {
        assertTrue(testRestaurantCollection.addRestaurant(r1));
        assertEquals(1, testRestaurantCollection.getRestaurantListSize());
        assertTrue(testRestaurantCollection.isContained(r1));

        assertTrue(testRestaurantCollection.addRestaurant(r2));
        assertEquals(2, testRestaurantCollection.getRestaurantListSize());
        assertTrue(testRestaurantCollection.isContained(r1));
        assertTrue(testRestaurantCollection.isContained(r2));
    }

    @Test
    void testAddRestaurantDuplicate() {
        assertTrue(testRestaurantCollection.addRestaurant(r1));
        assertEquals(1, testRestaurantCollection.getRestaurantListSize());
        assertTrue(testRestaurantCollection.isContained(r1));

        assertFalse(testRestaurantCollection.addRestaurant(r1));
        assertEquals(1, testRestaurantCollection.getRestaurantListSize());
        assertTrue(testRestaurantCollection.isContained(r1));
    }

    @Test
    void testRemoveRestaurantOnce() {
        testRestaurantCollection.addRestaurant(r1);

        assertTrue(testRestaurantCollection.removeRestaurant(r1));
        assertEquals(0, testRestaurantCollection.getRestaurantListSize());
        assertFalse(testRestaurantCollection.isContained(r1));
    }

    @Test
    void testRemoveRestaurantMultiple() {
        testRestaurantCollection.addRestaurant(r1);
        testRestaurantCollection.addRestaurant(r2);

        assertTrue(testRestaurantCollection.removeRestaurant(r1));
        assertEquals(1, testRestaurantCollection.getRestaurantListSize());
        assertFalse(testRestaurantCollection.isContained(r1));
        assertTrue(testRestaurantCollection.isContained(r2));

        assertTrue(testRestaurantCollection.removeRestaurant(r2));
        assertEquals(0, testRestaurantCollection.getRestaurantListSize());
        assertFalse(testRestaurantCollection.isContained(r1));
        assertFalse(testRestaurantCollection.isContained(r2));
    }

    @Test
    void testRemoveFirstRestaurant() {
        testRestaurantCollection.addRestaurant(r1);
        testRestaurantCollection.addRestaurant(r2);
        testRestaurantCollection.addRestaurant(r3);

        assertTrue(testRestaurantCollection.removeRestaurant(r1));
        assertEquals(2, testRestaurantCollection.getRestaurantListSize());
        assertFalse(testRestaurantCollection.isContained(r1));
        assertTrue(testRestaurantCollection.isContained(r2));
        assertTrue(testRestaurantCollection.isContained(r3));
    }

    @Test
    void testRemoveMiddleRestaurant() {
        testRestaurantCollection.addRestaurant(r1);
        testRestaurantCollection.addRestaurant(r2);
        testRestaurantCollection.addRestaurant(r3);

        assertTrue(testRestaurantCollection.removeRestaurant(r2));
        assertEquals(2, testRestaurantCollection.getRestaurantListSize());
        assertTrue(testRestaurantCollection.isContained(r1));
        assertFalse(testRestaurantCollection.isContained(r2));
        assertTrue(testRestaurantCollection.isContained(r3));
    }

    @Test
    void testRemoveLastRestaurant() {
        testRestaurantCollection.addRestaurant(r1);
        testRestaurantCollection.addRestaurant(r2);
        testRestaurantCollection.addRestaurant(r3);

        assertTrue(testRestaurantCollection.removeRestaurant(r3));
        assertEquals(2, testRestaurantCollection.getRestaurantListSize());
        assertTrue(testRestaurantCollection.isContained(r1));
        assertTrue(testRestaurantCollection.isContained(r2));
        assertFalse(testRestaurantCollection.isContained(r3));
    }

    @Test
    void testRemoveRestaurantNotInList() {
        assertFalse(testRestaurantCollection.removeRestaurant(r1));
        assertEquals(0, testRestaurantCollection.getRestaurantListSize());
        assertFalse(testRestaurantCollection.isContained(r1));
    }

    @Test
    void testIsContainedOnceTrue() {
        testRestaurantCollection.addRestaurant(r1);

        assertTrue(testRestaurantCollection.isContained(r1));
    }

    @Test
    void testIsContainedOnceFalse() {
        testRestaurantCollection.addRestaurant(r1);

        assertFalse(testRestaurantCollection.isContained(r2));
    }

    @Test
    void testIsContainedMultiple() {
        testRestaurantCollection.addRestaurant(r1);
        testRestaurantCollection.addRestaurant(r2);

        assertTrue(testRestaurantCollection.isContained(r1));
        assertTrue(testRestaurantCollection.isContained(r2));
    }

    @Test
    void testGetOneRestaurant() {
        testRestaurantCollection.addRestaurant(r1);
        assertEquals(r1, testRestaurantCollection.getRestaurant("Subway"));
        assertNull(testRestaurantCollection.getRestaurant("Burgoo"));
    }

    @Test
    void testGetMultipleRestaurant() {
        testRestaurantCollection.addRestaurant(r1);
        testRestaurantCollection.addRestaurant(r2);
        testRestaurantCollection.addRestaurant(r3);

        assertEquals(r1, testRestaurantCollection.getRestaurant("Subway"));
        assertEquals(r2, testRestaurantCollection.getRestaurant("Burgoo"));
        assertEquals(r3, testRestaurantCollection.getRestaurant("Published On Main"));

        assertNull(testRestaurantCollection.getRestaurant("Published On Mai"));
        assertNull(testRestaurantCollection.getRestaurant("McDonals"));

    }
    @Test
    void testTopThreeRecommendationsOneMatch() {
        testRestaurantCollection.addRestaurant(r1);
        testRestaurantCollection.addRestaurant(r2);
        testRestaurantCollection.addRestaurant(r3);

        List<Restaurant> testRecommendations = testRestaurantCollection.topThreeRecommendations
                ("Casual", "Fast Food", 20);

        assertEquals(1, testRecommendations.size());
        assertEquals(r1, testRecommendations.get(0));
    }

    @Test
    void testTopThreeRecommendationsTwoMatchSorted() {
        testRestaurantCollection.addRestaurant(r5);
        testRestaurantCollection.addRestaurant(r2);
        testRestaurantCollection.addRestaurant(r3);
        testRestaurantCollection.addRestaurant(r1);

        List<Restaurant> testRecommendations = testRestaurantCollection.topThreeRecommendations
                ("Casual", "Fast Food", 20);

        assertEquals(1, testRecommendations.size());
        assertEquals(r1, testRecommendations.get(0));
    }

    @Test
    void testTopThreeRecommendationsThreeMatchInOrder() {
        testRestaurantCollection.addRestaurant(r7);
        testRestaurantCollection.addRestaurant(r2);
        testRestaurantCollection.addRestaurant(r6);
        testRestaurantCollection.addRestaurant(r4);
        testRestaurantCollection.addRestaurant(r1);
        testRestaurantCollection.addRestaurant(r5);
        testRestaurantCollection.addRestaurant(r3);

        List<Restaurant> testRecommendations = testRestaurantCollection.topThreeRecommendations
                ("Casual", "Fast Food", 20);

        assertEquals(3, testRecommendations.size());
        assertEquals(r7, testRecommendations.get(0));
        assertEquals(r6, testRecommendations.get(1));
        assertEquals(r1, testRecommendations.get(2));
    }

    @Test
    void testTopThreeRecommendationsThreeMatchSorted() {
        testRestaurantCollection.addRestaurant(r1);
        testRestaurantCollection.addRestaurant(r2);
        testRestaurantCollection.addRestaurant(r3);
        testRestaurantCollection.addRestaurant(r4);
        testRestaurantCollection.addRestaurant(r5);
        testRestaurantCollection.addRestaurant(r6);
        testRestaurantCollection.addRestaurant(r7);

        List<Restaurant> testRecommendations = testRestaurantCollection.topThreeRecommendations
                ("Casual", "Fast Food", 20);

        assertEquals(3, testRecommendations.size());
        assertEquals(r7, testRecommendations.get(0));
        assertEquals(r6, testRecommendations.get(1));
        assertEquals(r1, testRecommendations.get(2));
    }

}
