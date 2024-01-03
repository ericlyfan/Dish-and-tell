package persistence;

import model.Event;
import model.EventLog;
import model.RestaurantCollection;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Writes java objects to a JSON data format and saves it in a file.
// Code influenced by the JsonSerializationDemo
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: Constructs a Json writer to write to the destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: Opens the writer, throws a FileNotFoundException if the destination
    //          cannot be opened or found for writing.
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of restaurant collection to the file
    public void write(RestaurantCollection restaurantCollection) {
        JSONObject json = restaurantCollection.toJson();
        saveToFile(json.toString(TAB));
        EventLog.getInstance().logEvent(new Event("Current Collection Saved!"));
    }

    // MODIFIES: this
    // EFFECTS: closes the writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

}
