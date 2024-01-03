package ui;

import model.Event;
import model.EventLog;
import model.Restaurant;
import model.RestaurantCollection;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.swing.*;

// Restaurant App GUI
public class RestaurantCollectionAppGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private static final String JSON_FILE = "./data/RestaurantCollection.json";

    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private RestaurantCollection restaurantCollection;

    private JPanel viewScreen;
    private JScrollPane scrollPane;

    // EFFECTS: constructs the restaurant collection GUI
    public RestaurantCollectionAppGUI() throws FileNotFoundException {
        super("Dish & Tell Application");
        runRestaurantAppGUI();
    }

    // MODIFIES: this
    // EFFECTS: initializes, starts and is responsible for managing the entire GUI
    private void runRestaurantAppGUI() {
        EventLog.getInstance().clear();
        restaurantCollection = new RestaurantCollection();
        this.cardLayout = new CardLayout();
        this.cardPanel = new JPanel(cardLayout);
        this.jsonReader = new JsonReader(JSON_FILE);
        this.jsonWriter = new JsonWriter(JSON_FILE);
        setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        setLocationRelativeTo(null);
        loadData();
        createAndAddAllScreens();
        add(cardPanel);
        executeWhenClosingWindow();
        setVisible(true);
    }

    // EFFECTS: when the user closes the window to exit the program, will first prompt if data should be saved,
    //          then print the log to console before exiting the program.
    private void executeWhenClosingWindow() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveData();
                printLogToConsole();
                System.exit(0);
            }
        });
    }

    // EFFECTS: Prints all the events in the current eventLog to console
    private void printLogToConsole() {
        EventLog eventLog = EventLog.getInstance();
        for (Event next : eventLog) {
            System.out.println(next.getDate() + ": " + next.getDescription());
        }
    }

    // MODIFIES: this
    // EFFECTS: creates and adds all the different screens, that we will switch between in the app, to the cardPanel.
    private void createAndAddAllScreens() {
        this.scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JPanel mainScreen = createMainScreen();
        JPanel addScreen = createAddRestaurantScreen();
        this.viewScreen = createViewCollectionScreen();
        JPanel recommendScreen = createRecommendationScreen();
        JPanel reviseScreen = createReviseRatingScreen();
        JPanel closeScreen = createCloseRestaurantScreen();

        cardPanel.add(mainScreen, "MainScreen");
        cardPanel.add(addScreen, "AddRestaurantScreen");
        cardPanel.add(viewScreen, "ViewCollectionScreen");
        cardPanel.add(recommendScreen, "RecommendationScreen");
        cardPanel.add(reviseScreen, "ReviseRatingScreen");
        cardPanel.add(closeScreen, "CloseRestaurantScreen");
    }

    // MODIFIES: this
    // EFFECTS: Creates the main menu screen
    private JPanel createMainScreen() {
        JPanel mainScreen = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(5, 5, 10, 5);

        JButton addRestaurantButton = createButton("Review a New Restaurant");
        JButton viewCollectionButton = createButton("View Entire Restaurant Collection");
        JButton recommendationsButton = createButton("Recommendations");
        JButton reviseRatingButton = createButton("Revise a Restaurant's Rating");
        JButton removeRestaurantButton = createButton("A Restaurant is Permanently Closed");

        addRestaurantButtonActionListener(addRestaurantButton);
        viewCollectionButtonActionListener(viewCollectionButton);
        recommendationsButtonActionListener(recommendationsButton);
        reviseRatingButtonActionListener(reviseRatingButton);
        closeRestaurantButtonActionListener(removeRestaurantButton);

        addComponentsToPanel(mainScreen, constraints, addRestaurantButton,
                viewCollectionButton, recommendationsButton, reviseRatingButton, removeRestaurantButton);

        return mainScreen;
    }

    // MODIFIES: this
    // EFFECTS: Creates the "AddRestaurantScreen"
    private JPanel createAddRestaurantScreen() {
        JPanel addScreen = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(5, 5, 5, 5);

        JPanel inputRestaurantName = createTextField("Restaurant Name");
        JPanel inputDiningEnvironment = createTextField("Dining Environment");
        JPanel inputCuisine = createTextField("Cuisine Type");
        JPanel inputRating = createTextField("Overall Rating");
        JPanel inputExpenses = createTextField("Total Expenses");
        JPanel inputPartySize = createTextField("Party Size");

        JButton addRestaurantButton = createButton("Add Restaurant to Collection");
        addRestaurantToCollectionActionListener(inputRestaurantName, inputDiningEnvironment, inputCuisine, inputRating,
                inputExpenses, inputPartySize, addRestaurantButton);

        JButton homeButton = createButton("Home");
        returnToMainScreenActionListener(homeButton);

        addComponentsToPanel(addScreen, constraints, inputRestaurantName, inputDiningEnvironment,
                inputCuisine, inputRating, inputExpenses, inputPartySize, addRestaurantButton, homeButton);

        return addScreen;
    }

    // EFFECTS: Switches the screen to the "AddRestaurantScreen" when the addRestaurant button is
    // clicked on the main menu screen.
    private void addRestaurantButtonActionListener(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "AddRestaurantScreen");
            }
        });
    }

    // EFFECTS: Adds the restaurant to the collection when the addRestaurantButton is clicked
    private void addRestaurantToCollectionActionListener(JPanel inputRestaurantName, JPanel inputDiningEnvironment,
                                                         JPanel inputCuisine, JPanel inputRating, JPanel inputExpenses,
                                                         JPanel inputPartySize, JButton addRestaurantButton) {
        addRestaurantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField restaurantNameTextField = (JTextField) inputRestaurantName.getComponent(1);
                JTextField diningEnvironmentTextField = (JTextField) inputDiningEnvironment.getComponent(1);
                JTextField cuisineTextField = (JTextField) inputCuisine.getComponent(1);
                JTextField ratingTextField = (JTextField) inputRating.getComponent(1);
                JTextField expensesTextField = (JTextField) inputExpenses.getComponent(1);
                JTextField partySizeTextField = (JTextField) inputPartySize.getComponent(1);

                String inputtedName = restaurantNameTextField.getText();
                String inputtedEnvironment = diningEnvironmentTextField.getText();
                String inputtedCuisine = cuisineTextField.getText();
                double inputtedRating = Double.parseDouble(ratingTextField.getText());
                double inputtedExpenses = Double.parseDouble(expensesTextField.getText());
                int inputtedPartySize = Integer.parseInt(partySizeTextField.getText());

                executeRestaurantAddition(inputtedName, inputtedEnvironment, inputtedCuisine,
                        inputtedRating, inputtedExpenses, inputtedPartySize);

                resetTextFieldToEmpty(restaurantNameTextField, diningEnvironmentTextField, cuisineTextField,
                        ratingTextField, expensesTextField, partySizeTextField);
            }
        });
    }

    // MODIFIES: restaurantCollection
    // EFFECTS: creates a new restaurant with given inputs and adds it to the collection
    private void executeRestaurantAddition(String inputtedName, String inputtedEnvironment, String inputtedCuisine,
                                           double inputtedRating, double inputtedExpenses, int inputtedPartySize) {
        restaurantCollection.addRestaurant(new Restaurant(inputtedName,
                inputtedEnvironment, inputtedCuisine,
                inputtedRating, inputtedExpenses, inputtedPartySize));

        JOptionPane.showMessageDialog(null, "Restaurant added to collection!", "Success!",
                JOptionPane.INFORMATION_MESSAGE, new ImageIcon("./data/restaurant.png"));
    }

    // EFFECTS: Creates the "ViewCollectionScreen"
    private JPanel createViewCollectionScreen() {
        viewScreen = new JPanel(new BorderLayout());
        viewScreen.add(scrollPane, BorderLayout.CENTER);
        return viewScreen;
    }

    // EFFECTS: Switches the screen to the "ViewCollectionScreen" when the addRestaurant button is
    // clicked on the main menu screen.
    private void viewCollectionButtonActionListener(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GridBagConstraints constraints = new GridBagConstraints();
                constraints.gridx = 0;
                constraints.gridy = 0;
                constraints.insets = new Insets(5, 5, 5, 5);
                cardLayout.show(cardPanel, "ViewCollectionScreen");

                executeCollectionPrinter(constraints);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates a JLabel for each restaurant in the collection
    private void executeCollectionPrinter(GridBagConstraints constraints) {
        JPanel content = new JPanel(new GridBagLayout());
        List<Restaurant> allRestaurants = restaurantCollection.getRestaurantList();

        JButton homeButton = createButton("Home");
        returnToMainScreenActionListener(homeButton);

        if (allRestaurants.isEmpty()) {
            JOptionPane.showMessageDialog(null, "There are currently no restaurants in the collection...",
                    "Error!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("./data/restaurant.png"));
        } else {
            for (Restaurant nextRestaurant : allRestaurants) {
                JLabel nextRestaurantInfo = new JLabel("<html><pre>"
                        + "Restaurant: " + nextRestaurant.getName() + "<br>"
                        + "Environment: " + nextRestaurant.getEnvironment() + "<br>"
                        + "Cuisine: " + nextRestaurant.getCuisine() + "<br>"
                        + "Rating: " + nextRestaurant.getRating() + "<br>"
                        + "Price Per Person: " + nextRestaurant.pricePerPerson() + "</pre></html>");
                nextRestaurantInfo.setFont(new Font("Courier New", Font.PLAIN, 15));
                JLabel spacer = new JLabel("------------------------------------");
                spacer.setFont(new Font("Courier New", Font.PLAIN, 14));
                addComponentsToPanel(content, constraints,  nextRestaurantInfo, spacer);
                scrollPane.setViewportView(content);
            }
        }
        addComponentsToPanel(content, constraints, homeButton);
    }

    // EFFECTS: Creates the "RecommendationScreen"
    private JPanel createRecommendationScreen() {
        JPanel recommendScreen = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(5, 5, 10, 5);

        JLabel mainMessage = new JLabel("Enter your preferences for restaurant recommendations:");
        mainMessage.setFont(new Font("Consolas", Font.PLAIN, 16));
        JLabel spacer = new JLabel("");

        JPanel inputDesiredDiningEnvironment = createTextField("Environment (Casual or Upscale)");
        JPanel inputDesiredCuisine = createTextField("Desired Type of Cuisine");
        JPanel inputPricePerPerson = createTextField("Ideal Price Per Person");

        JButton getRecommendationsButton = createButton("Get Recommendations");
        recommendRestaurantsActionListener(inputDesiredDiningEnvironment, inputDesiredCuisine,
                inputPricePerPerson, getRecommendationsButton);

        JButton homeButton = createButton("Home");
        returnToMainScreenActionListener(homeButton);

        addComponentsToPanel(recommendScreen, constraints, mainMessage, spacer, inputDesiredDiningEnvironment,
                inputDesiredCuisine, inputPricePerPerson, getRecommendationsButton, homeButton);

        return recommendScreen;
    }

    // EFFECTS: Switches the screen to the "RecommendationScreen" when the addRestaurant button is
    // clicked on the main menu screen.
    private void recommendationsButtonActionListener(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "RecommendationScreen");
            }
        });
    }

    //EFFECTS: Generates a maximum of 3 restaurants that match inputted criteria, in descending order (by rating)
    private void recommendRestaurantsActionListener(JPanel inputDesiredDiningEnvironment, JPanel inputDesiredCuisine,
                                                    JPanel inputPricePerPerson, JButton getRecommendationsButton) {
        getRecommendationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField diningEnvironmentTextField = (JTextField) inputDesiredDiningEnvironment.getComponent(1);
                JTextField cuisineTextField = (JTextField) inputDesiredCuisine.getComponent(1);
                JTextField pricePerPersonTextField = (JTextField) inputPricePerPerson.getComponent(1);

                String inputtedEnvironment = diningEnvironmentTextField.getText();
                String inputtedCuisine = cuisineTextField.getText();
                double inputtedExpensePerPerson = Double.parseDouble(pricePerPersonTextField.getText());

                executeRecommendations(inputtedEnvironment, inputtedCuisine, inputtedExpensePerPerson);
                resetTextFieldToEmpty(diningEnvironmentTextField, cuisineTextField, pricePerPersonTextField);
            }
        });
    }

    // EFFECTS: If no restaurants match the criteria then return error message, otherwise, generates a maximum of 3
    //          restaurants that match inputted criteria, in descending order (by rating).
    private void executeRecommendations(String inputtedEnvironment, String inputtedCuisine,
                                        double inputtedExpensePerPerson) {
        List<Restaurant> recommendations = restaurantCollection.topThreeRecommendations(inputtedEnvironment,
                inputtedCuisine, inputtedExpensePerPerson);

        if (recommendations.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "There are currently no restaurants that match your criteria...", "Error!",
                    JOptionPane.INFORMATION_MESSAGE, new ImageIcon("./data/recommend.png"));
        } else {
            JOptionPane.showMessageDialog(null,
                    generateRecommendationsMessage(recommendations),
                    "Your Recommendations!",
                    JOptionPane.INFORMATION_MESSAGE,
                    new ImageIcon("./data/recommend.png"));
        }
    }

    // EFFECTS: Creates the message to be displayed when the recommendations are executed
    private String generateRecommendationsMessage(List<Restaurant> recommendations) {
        StringBuilder message = new StringBuilder();
        for (Restaurant restaurant : recommendations) {
            message.append("\n").append(restaurant.getName()).append(" -- Rating: ").append(restaurant.getRating());
        }
        return message.toString();
    }

    // MODIFIES: this
    // EFFECTS: Creates the "ReviseRatingScreen"
    private JPanel createReviseRatingScreen() {
        JPanel reviseScreen = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(5, 5, 10, 5);

        JLabel mainMessage = new JLabel("Enter the name of the restaurant whose rating you would like to revise:");
        mainMessage.setFont(new Font("Consolas", Font.PLAIN, 16));
        JLabel spacer = new JLabel("");

        JPanel inputRestaurantToBeRevised = createTextField("Restaurant's Name");
        JPanel inputNewDesiredRating = createTextField("New Overall Rating");

        JButton reviseRestaurantRatingButton = createButton("Revise this Restaurant's Rating");
        reviseRatingActionListener(inputRestaurantToBeRevised, inputNewDesiredRating, reviseRestaurantRatingButton);

        JButton homeButton = createButton("Home");
        returnToMainScreenActionListener(homeButton);

        addComponentsToPanel(reviseScreen, constraints, mainMessage, spacer,
                inputRestaurantToBeRevised, inputNewDesiredRating, reviseRestaurantRatingButton, homeButton);

        return reviseScreen;
    }

    // EFFECTS: Switches the screen to the "ReviseRatingScreen" when the addRestaurant button is
    // clicked on the main menu screen.
    private void reviseRatingButtonActionListener(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "ReviseRatingScreen");
            }
        });
    }

    // EFFECTS: When button is pressed, revises the inputted restaurant's rating to the new desired rating
    private void reviseRatingActionListener(JPanel inputRestaurantToBeRevised, JPanel inputNewDesiredRating,
                                            JButton reviseRestaurantRatingButton) {
        reviseRestaurantRatingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField restaurantNameTextField = (JTextField) inputRestaurantToBeRevised.getComponent(1);
                JTextField newRatingTextField = (JTextField) inputNewDesiredRating.getComponent(1);

                String inputtedRestaurantName = restaurantNameTextField.getText();
                double inputtedRevisedRating = Double.parseDouble(newRatingTextField.getText());

                executeRevision(inputtedRestaurantName, inputtedRevisedRating);
                resetTextFieldToEmpty(restaurantNameTextField, newRatingTextField);
            }
        });
    }

    // MODIFIES: restaurant
    // EFFECTS: If selected restaurant is not in the collection then return error message, otherwise, will change
    //          the ratings of the selected restaurant to the newly inputted revised ratings.
    private void executeRevision(String inputtedRestaurantName, double inputtedRevisedRating) {
        Restaurant selectedRestaurant = restaurantCollection.getRestaurant(inputtedRestaurantName);
        if (selectedRestaurant == null) {
            JOptionPane.showMessageDialog(null, "Restaurant is not in the collection!",
                    "Error!", JOptionPane.INFORMATION_MESSAGE,
                    new ImageIcon("./data/restaurant.png"));
        } else {
            selectedRestaurant.changeRating(inputtedRevisedRating);
            JOptionPane.showMessageDialog(null, "Rating has been revised!",
                    "Success!", JOptionPane.INFORMATION_MESSAGE,
                    new ImageIcon("./data/restaurant.png"));
        }
    }

    // MODIFIES: this
    // EFFECTS: Creates the "CloseRestaurantScreen"
    private JPanel createCloseRestaurantScreen() {
        JPanel closeScreen = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(5, 5, 10, 5);

        JLabel mainMessage = new JLabel("Enter the name of the restaurant you wish to remove from the collection:");
        mainMessage.setFont(new Font("Consolas", Font.PLAIN, 16));
        JLabel spacer = new JLabel("");

        JPanel inputRestaurantToBeClosed = createTextField("Permanently Closed Restaurant's Name");

        JButton closeRestaurantButton = createButton("Remove This Restaurant From The Collection");
        closeRestaurantActionListener(inputRestaurantToBeClosed, closeRestaurantButton);

        JButton homeButton = createButton("Home");
        returnToMainScreenActionListener(homeButton);

        addComponentsToPanel(closeScreen, constraints, mainMessage, spacer,
                inputRestaurantToBeClosed, closeRestaurantButton, homeButton);

        return closeScreen;
    }

    // EFFECTS: Switches the screen to the "CloseRestaurantScreen" when the addRestaurant button is
    // clicked on the main menu screen.
    private void closeRestaurantButtonActionListener(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "CloseRestaurantScreen");
            }
        });
    }

    // EFFECTS: When button is pressed, removes the inputted restaurant (by name) from the collection
    private void closeRestaurantActionListener(JPanel inputRestaurantToBeClosed, JButton closeRestaurantButton) {
        closeRestaurantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField restaurantNameToBeClosedTextField = (JTextField) inputRestaurantToBeClosed.getComponent(1);
                String inputtedRestaurantToBeClosed = restaurantNameToBeClosedTextField.getText();

                executeRestaurantRemoval(inputtedRestaurantToBeClosed);
                resetTextFieldToEmpty(restaurantNameToBeClosedTextField);
            }
        });
    }

    // MODIFIES: restaurantCollection
    // EFFECTS: if there are no restaurants in the collection or the inputted restaurant is not in the collection,
    //          return an error message. Otherwise, removes the selected restaurant from the collection.
    private void executeRestaurantRemoval(String inputtedRestaurantToBeClosed) {
        List<Restaurant> allRestaurants = restaurantCollection.getRestaurantList();

        if (allRestaurants.isEmpty()) {
            JOptionPane.showMessageDialog(null, "There are no restaurants in the collection...", "Error!",
                    JOptionPane.INFORMATION_MESSAGE, new ImageIcon("./data/restaurant.png"));
        } else {
            Restaurant closedRestaurant = restaurantCollection.getRestaurant(inputtedRestaurantToBeClosed);

            if (closedRestaurant == null) {
                JOptionPane.showMessageDialog(null, "Restaurant is not in the collection...",
                        "Error!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("./data/restaurant.png"));
            } else {
                restaurantCollection.removeRestaurant(closedRestaurant);
                JOptionPane.showMessageDialog(null, "Restaurant has been removed from the collection!",
                        "Success!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("./data/restaurant.png"));
            }
        }
    }

    // EFFECTS: When pressed, will return the user to the MainScreen of the application
    private void returnToMainScreenActionListener(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "MainScreen");
            }
        });
    }

    // EFFECTS: resets all the textFields that are given to ""
    private void resetTextFieldToEmpty(JTextField... textFields) {
        for (JTextField textField : textFields) {
            textField.setText("");
        }
    }

    // EFFECTS: creates a button with desired text, size, and font
    private JButton createButton(String buttonName) {
        JButton newButton = new JButton(buttonName);
        Dimension buttonSize = new Dimension(400, 60);
        newButton.setPreferredSize(buttonSize);
        newButton.setFont(new Font("Courier New", Font.PLAIN, 14));
        return newButton;
    }

    // EFFECTS: creates a text field with a label in the form of a JPanel
    private JPanel createTextField(String fieldPrompt) {
        JPanel panel = new JPanel(new BorderLayout());

        JTextField newTextField = new JTextField(30);
        newTextField.setPreferredSize(new Dimension(200, 40));
        newTextField.setFont(new Font("Courier New", Font.PLAIN, 14));

        JLabel label = new JLabel(fieldPrompt);
        label.setFont(new Font("Courier New", Font.PLAIN, 14));

        panel.add(label, BorderLayout.BEFORE_FIRST_LINE);
        panel.add(newTextField, BorderLayout.CENTER);

        return panel;
    }

    // EFFECTS: places all components (buttons/textFields) onto the panel, spaced out
    private void addComponentsToPanel(JPanel panel, GridBagConstraints constraints, Component... components) {
        for (Component component : components) {
            panel.add(component, constraints);
            constraints.gridy++;
        }
    }

    // MODIFIES: restaurantCollection
    // EFFECTS: If desired, will load the previously saved restaurant collection file
    private void loadData() {
        ImageIcon customIcon = new ImageIcon("./data/upload.png");
        int option = JOptionPane.showConfirmDialog(null,
                "Would you like to load your previously saved collection?",
                "Load Data",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                customIcon);

        if (option == JOptionPane.YES_OPTION) {
            try {
                restaurantCollection = jsonReader.read();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Load Failed", "Error!",
                        JOptionPane.INFORMATION_MESSAGE, new ImageIcon("./data/warning.png"));
            }
        }
    }

    // EFFECTS: If desired, will save the current restaurant collection data to file
    private void saveData() {
        ImageIcon customIcon = new ImageIcon("./data/download.png");
        int option = JOptionPane.showConfirmDialog(null, "Would you like to save your collection before exiting?",
                "Save Data", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, customIcon);

        if (option == JOptionPane.YES_OPTION) {
            try {
                jsonWriter.open();
                jsonWriter.write(restaurantCollection);
                jsonWriter.close();
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Save Failed", "Error!",
                        JOptionPane.INFORMATION_MESSAGE, new ImageIcon("./data/warning.png"));
            }
        }
    }
}
