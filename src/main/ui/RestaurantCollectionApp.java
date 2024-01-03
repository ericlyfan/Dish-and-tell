package ui;

import model.Restaurant;
import model.RestaurantCollection;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// Restaurant Application
public class RestaurantCollectionApp {
    private RestaurantCollection collection;
    private Scanner userInput;
    private static final String JSON_FILE = "./data/RestaurantCollection.json";
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    // EFFECTS: runs the restaurant application
    public RestaurantCollectionApp() throws FileNotFoundException {
        runRestaurantApp();
    }

    // MODIFIES: this
    // EFFECTS: Processes user input
    private void runRestaurantApp() {
        boolean continueApp = true;
        String command;
        initializes();

        while (continueApp) {
            displayMenuBeginning();
            command = userInput.nextLine();
            command = command.toLowerCase();

            if (command.equals("q")) {
                saveCollection();
                continueApp = false;
            } else {
                userCommand(command);
            }
        }

        System.out.println("I will be waiting for your next review!");
    }

    // MODIFIES: this
    // EFFECTS: initializes the collection (list) of restaurants
    private void initializes() {
        collection = new RestaurantCollection();
        userInput = new Scanner(System.in);
        jsonReader = new JsonReader(JSON_FILE);
        jsonWriter = new JsonWriter(JSON_FILE);
        loadCollection();
    }

    // EFFECTS: displays a menu of options for the user to interact with
    private void displayMenuBeginning() {
        System.out.println("\nSelect From:");
        System.out.println("\tn --> Review a New Restaurant");
        System.out.println("\tv --> View Entire Restaurant Collection");
        System.out.println("\tr --> Recommendations");
        System.out.println("\te --> Edit/Revise a Restaurant's Rating");
        System.out.println("\tc --> Restaurant is Permanently Closed");
        System.out.println("\tq --> Exit Application");
    }

    // MODIFIES: this
    // EFFECTS: processes the user's command and executes the helper code to do the desired action
    private void userCommand(String command) {
        if (command.equals("n")) {
            newReview();
        } else if (command.equals("v")) {
            viewCollection();
        } else if (command.equals("r")) {
            giveRecommendations();
        } else if (command.equals("e")) {
            editRating();
            userInput.nextLine();
        } else if (command.equals("c")) {
            removeRestaurant();
        } else {
            System.out.println("\nDid you mistype? Try again...");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a new restaurant to the collection with the given parameters. Where restaurantName, environment,
    //          cuisine must be non-empty strings, rating, expense, must be double and party must be an int.
    //          Restaurants with the same name (duplicates) are not allowed.
    private void newReview() {
        System.out.println("\nEnter restaurant name: ");
        String restaurantName = userInput.nextLine();
        System.out.println("Enter dining environment (Casual or Upscale): ");
        String environment = userInput.nextLine();
        System.out.println("Enter cuisine type: ");
        String cuisine = userInput.nextLine();
        System.out.println("Enter overall rating (0-10): ");
        double rating = userInput.nextDouble();
        userInput.nextLine();
        System.out.println("Enter total expenses at restaurant: $");
        double expense = userInput.nextDouble();
        userInput.nextLine();
        System.out.println("Enter party size: ");
        int party = userInput.nextInt();
        userInput.nextLine();

        Restaurant restaurant = new Restaurant(restaurantName, environment, cuisine, rating, expense, party);
        collection.addRestaurant(restaurant);
        System.out.println(restaurant.getName() + " has been added to the collection");
    }

    // EFFECTS: shows the names of all the restaurants in the collection. Allows you to select a restaurant
    //          (by name) to be shown details.
    private void viewCollection() {
        List<Restaurant> allRestaurants = collection.getRestaurantList();

        if (allRestaurants.isEmpty()) {
            System.out.println("\nThere are currently no restaurants in the collection...");
        } else {
            System.out.println("\nRestaurant Collection:");
            System.out.println("-----------------------------------");
            for (Restaurant nextRestaurant : allRestaurants) {
                System.out.println("Restaurant Name: " + nextRestaurant.getName());
                System.out.println("-----------------------------------");
            }
            selectRestaurant();
        }
    }

    // EFFECTS: asks the user to specify a restaurant environment, cuisine type and a price range ($ per person)
    //          and returns the top 3 restaurants (max) that match the criteria. Allows you to select a restaurant
    //          (by name) to be shown details.
    private void giveRecommendations() {
        System.out.println("\nEnter your desired restaurant environment (Casual or Upscale): ");
        String desiredEnvironment = userInput.nextLine();
        System.out.println("Enter your desired cuisine type: ");
        String desiredCuisine = userInput.nextLine();
        System.out.println("Enter your approximate price point ($ per person): ");
        double desiredPrice = userInput.nextDouble();
        userInput.nextLine();

        List<Restaurant> recommendations =
                collection.topThreeRecommendations(desiredEnvironment, desiredCuisine, desiredPrice);

        if (recommendations.isEmpty()) {
            System.out.println("\nThere are currently no restaurants that match your critera...");
        } else {
            System.out.println("\nHere are my top recommendations for you:");
            for (Restaurant restaurant : recommendations) {
                System.out.println("\n" + restaurant.getName() + " -- Rating: " + restaurant.getRating());
            }
            selectRestaurant();
        }
    }

    // EFFECTS: Asks if the user wants to select a restaurant, if yes then show selected restaurant's details
    //          if no return false.
    private Boolean selectRestaurant() {
        System.out.println("\nWould you like to view a specific restaurant in detail?");
        System.out.println("\ty --> Yes");
        System.out.println("\tn --> No");
        String option = userInput.nextLine();
        option = option.toLowerCase();

        if (option.equals("y")) {
            System.out.println("\nEnter the name of the restaurant you wish to view in detail:");
            String selectedRestaurantName = userInput.nextLine();
            Restaurant selectedRestaurant = collection.getRestaurant(selectedRestaurantName);

            if (selectedRestaurant == null) {
                System.out.println("Restaurant is not in the collection...");
            } else {
                System.out.println("\nRestaurant Name: " + selectedRestaurant.getName());
                System.out.println("Dining Environment: " + selectedRestaurant.getEnvironment());
                System.out.println("Cuisine Type: " + selectedRestaurant.getCuisine());
                System.out.println("Overall Rating: " + selectedRestaurant.getRating());
                System.out.println("Price Per Person: $"
                        + selectedRestaurant.getExpenses() / selectedRestaurant.getParty());
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: modifies a specific restaurant's rating, restaurant to modify must be in the collection
    private void editRating() {
        List<Restaurant> allRestaurants = collection.getRestaurantList();

        if (allRestaurants.isEmpty()) {
            System.out.println("There are no restaurants in the collection...");
        } else {
            System.out.println("\nEnter the name of the restaurant that you wish to change the overall rating for:");
            String selectedRestaurantName = userInput.nextLine();
            Restaurant selectedRestaurant = collection.getRestaurant(selectedRestaurantName);

            if (selectedRestaurant == null) {
                System.out.println("That restaurant is not in the collection!");
            } else {
                System.out.println("\n" + selectedRestaurant.getName() + " currently has a overall rating of "
                        + selectedRestaurant.getRating());
                System.out.println("Enter the new rating (0-10) you wish to give " + selectedRestaurant.getName());
                selectedRestaurant.changeRating(userInput.nextDouble());
                System.out.println("\n" + selectedRestaurant.getName() + "'s rating has been updated!");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: removes a restaurant from the collection as it is prematurely closed
    private void removeRestaurant() {
        List<Restaurant> allRestaurants = collection.getRestaurantList();

        if (allRestaurants.isEmpty()) {
            System.out.println("There are no restaurants in the collection...");
        } else {
            System.out.println("\nEnter the name of the restaurant that is permanently closed");
            String closedRestaurantName = userInput.nextLine();
            Restaurant closedRestaurant = collection.getRestaurant(closedRestaurantName);

            if (closedRestaurant == null) {
                System.out.println("That restaurant is not in the collection...");
            } else {
                allRestaurants.remove(closedRestaurant);
                System.out.println("\nIt's sad to see " + closedRestaurant.getName() + " go...");
                System.out.println(closedRestaurant.getName() + " has been removed!");
            }
        }
    }

    // EFFECTS: saves the current restaurant collection to file
    private void saveCollection() {
        while (true) {
            System.out.println("\nWould you like to save your restaurant collection before you go?");
            System.out.println("\ty --> Yes");
            System.out.println("\tn --> No");
            String option = userInput.nextLine();
            option = option.toLowerCase();

            if (option.equals("y")) {
                try {
                    jsonWriter.open();
                    jsonWriter.write(collection);
                    jsonWriter.close();
                    System.out.println("Saved your Restaurant Collection to: " + JSON_FILE + "\n");
                } catch (FileNotFoundException e) {
                    System.out.println("Sorry! I was unable to save your collection to file: " + JSON_FILE);
                }
                break;
            } else if (option.equals("n")) {
                break;
            } else {
                System.out.println("\nDid you mistype? Try again...");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the previous restaurant collection from file
    public void loadCollection() {
        while (true) {
            System.out.println("\nWould you like to load your previously saved restaurant collection?");
            System.out.println("\ty --> Yes");
            System.out.println("\tn --> No");
            String option = userInput.nextLine();
            option = option.toLowerCase();

            if (option.equals("y")) {
                try {
                    collection = jsonReader.read();
                    System.out.println("Loaded your Restaurant Collection from: " + JSON_FILE);
                } catch (IOException e) {
                    System.out.println("Sorry! I was unable to read the file from: " + JSON_FILE);
                }
                break;
            } else if (option.equals("n")) {
                break;
            } else {
                System.out.println("\nDid you mistype? Try again...");
            }
        }

    }
}
