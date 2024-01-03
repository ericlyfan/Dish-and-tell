# Restaurant Recommendations

### Project Function, Targeted Audience & Interest for Application
My goal is to design an application for food fanatics who want a fast and efficient method to store 
and keep track of restaurants that they have visited in the past. The program can also serve a 
secondary function as a restaurant recommendation system for those indecisive food lovers. 

When entering a visited restaurant into the app, you will need to include parameters such as the 
_Restaurant Name_, _Dining Environment_ (Casual, Upscale), _Cuisine Type_ (Eg. Indian, Southeast Asian, Chinese, 
Fast Food, Japanese, Korean, Western, Italian, French, Mexican, Contemporary), _Overall Rating_,
_Total Expenses_, and _Party Size_ (how many people dined).

When asking the application for a recommendation, the user may input a desired _Dining Environment_, 
a _Cuisine Type_ and a _Price Range_ ($ per person). The application will output the names of the top 3
restaurants based on an overall rating (highest rated first).

Currently, I like to keep track of the restaurants I've been to in the past along with a self-determined
overall rating in my phone's notes document. This method is tedious, and I am unable to input detailed
information about each restaurant like this application would. Additionally, there has been a lot of times
when I would struggle to decide on a place to eat,and I'm thinking how useful it would be if this program
had existed to help me choose from my previously top-rated restaurants. 

### User Stories
- As a user, I want to be able to create a new restaurant and add it to my collection.
- As a user, I want to be able to get recommendations (top 3 that match user given criteria) from collection.
- As a user, I want to be able to view all the restaurants in the collection.
- As a user, I want to select a restaurant in the collection and be shown all of it's detailed information.
- As a user, I want to be able to edit my overall rating
- As a user, I want to be able to mark a restaurant as Permanently Closed (remove from collection) if this is the case.
- As a user, when I select "quit" from the menu I want to be given the option to save my entire restaurant collection.
- As a user, when I start the application, I want to be given the option to reload my entire restaurant collection.

# Instructions for Grader
- You can add X to Y by clicking "Review a New Restaurant", filling out all the text fields and clicking 
  "Add Restaurant to Collection" this will create and add a new Restaurant to the collection.

- You can view all Xs that have been added to Y by clicking "View Entire Restaurant Collection", you will then
  be able to scroll through and look at all the Xs (along with their information) that is currently in Y.

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by...
  clicking "Recommendations", filling out all the text fields and clicking "Get Recommendations", this will
  display a popup window with the top 3 restaurants that match your criteria.

- You can generate the second required action related to the user story "adding multiple Xs to a Y" by...
  clicking "Revise a Restaurant's Rating", filling out all the text fields and clicking 
  "Revise this Restaurant's Rating", this will change the inputted restaurant's rating to the new inputted rating.

- You can generate a third action relating to the user story "adding multiple Xs to a Y" by... clicking
  "A Restaurant is Permanently Closed", filling out the text field (with a restaurant in the collection) and clicking
  "Remove This Restaurant From The Collection", this will remove the inputted restaurant from the collection.

- You can locate my visual component in all of my popup windows, such as when loading data, saving data, adding a 
  restaurant to the collection, or getting a recommendation. I have modified the icons by loading a png that is stored
  in the "data" folder. 

- You can save the state of my application by clicking the close window button (red circle on Mac, x on Windows)
  and a popup will appear asking if you would like to save the application or not. 

- You can reload the state of my application by running main (starting the application), a popup window
  will appear asking if you would like to reload or not.

# Phase 4: Task 2
Wed Nov 29 12:26:13 PST 2023: Event log cleared.
Wed Nov 29 12:26:14 PST 2023: Loaded Previously Saved Collection!
Wed Nov 29 12:26:14 PST 2023: Added a New Restaurant to the Collection!
Wed Nov 29 12:26:14 PST 2023: Added a New Restaurant to the Collection!
Wed Nov 29 12:26:14 PST 2023: Added a New Restaurant to the Collection!
Wed Nov 29 12:26:14 PST 2023: Added a New Restaurant to the Collection!
Wed Nov 29 12:26:14 PST 2023: Added a New Restaurant to the Collection!
Wed Nov 29 12:26:14 PST 2023: Added a New Restaurant to the Collection!
Wed Nov 29 12:26:14 PST 2023: Added a New Restaurant to the Collection!
Wed Nov 29 12:26:24 PST 2023: Gave Some Restaurant Recommendations!
Wed Nov 29 12:26:52 PST 2023: Added a New Restaurant to the Collection!
Wed Nov 29 12:27:06 PST 2023: Revised an Existing Restaurant's Rating!
Wed Nov 29 12:27:12 PST 2023: Removed a Restaurant from the Collection!
Wed Nov 29 12:27:17 PST 2023: Current Collection Saved!

# Phase 4: Task 3
I noticed that I could better implement the Single Responsibility Principle (SPR) specifically in my 
RestaurantCollectionApp and RestaurantCollectionAppGUI classes. 
In the RestaurantCollectionApp class, I could refactor the saveCollection and loadCollection methods into a new class 
that is responsible for only loading/saving and dealing with JSON portion. 
In the RestaurantCollectionAppGUI class, I could also do something similar and refactor the save/load methods into a 
separate class to adhere to the Single Responsibility Principle.
Additionally, I could refactor the log printing section into a separate class as well to follow the SPR. 
Furthermore, I could create separate classes for the creation of each screen in my application 
(ex. classes like MainScreen, AddScreen, ViewScreen, etc.). 
Following the same logic, I could also create separate classes for the different action listeners of each screen 
(ex. classes like AddRestaurantButtonListener, ViewCollectionButtonListener, etc.). 
Creating separate classes for each screen and action listener would result in code that is much cleaner and easier to 
change or add new functionality in the future while also making sure each class is responsible for only 
1 behaviour/task (SPR).
Lastly, I noticed that there seems to be a lot of duplicate or similar code in my RestaurantCollectionAppGui class
and if I had more time, I would try to eliminate any code duplication by creating helper methods. I would also go back
to look through and double-check my naming conventions, making any necessary changes to ensure the names are appropriate
and convey the meaning of my methods properly in an easy-to-understand way.




