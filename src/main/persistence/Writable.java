package persistence;

import org.json.JSONObject;

// EFFECTS: converts the object into a Json object
//          Code influenced by the JsonSerializationDemo
public interface Writable {
    JSONObject toJson();
}
