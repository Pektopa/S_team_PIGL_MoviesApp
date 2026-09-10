package moviesapp.model.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import moviesapp.model.movies.Movies;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static moviesapp.model.json.JsonReader.*;

public class JsonWriter {
    private final File jsonFile;
    public static JsonWriter SEARCH_WRITER = new JsonWriter(SEARCH_FILE_PATH);
    public static JsonWriter GENRES_WRITER = new JsonWriter(GENRES_FILE_PATH);
    public static JsonWriter FAVORITES_WRITER = new JsonWriter(FAVORITES_FILE_PATH);

    public JsonWriter(String path){
        jsonFile = new File(path);
    }

    /**
     * Clear the json file by emptying it.
     */
    public void clear(){
        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write("");
        } catch (IOException e) {
            System.err.println("Error truncating file: " + e.getMessage());
        }
    }

    /**
     * Saves the favorite movies to a JSON file.
     * @param favorites the list of favorite movies
     */
    public void saveFavorites(Movies favorites) {
        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write("{\n");
            writer.write(favorites.toJsonFormat());

            writer.write(",\n");
            writer.write("  \"total_pages\" : 1,\n");
            writer.write("  \"total_results\" : " + favorites.size() + "\n");
            writer.write("}\n");
        } catch (IOException e) {
            System.err.println("Error writing JSON file: " + e.getMessage());
        }
    }

    public static void convertJsonToFile(String jsonToWrite, String filePath) {

        ObjectMapper mapper = JsonMapper.builder().build();

        try {
            ObjectNode node = mapper.readValue(jsonToWrite, ObjectNode.class);

            try (FileWriter fileWriter = new FileWriter(filePath, StandardCharsets.UTF_8)) {
                mapper.enable(SerializationFeature.INDENT_OUTPUT);
                mapper.writeValue(fileWriter, node);
            } catch (IOException e) {
                System.err.println("IOException from 'new FileWriter(...)' or 'mapper.writeValue(fileWriter, node)'");
            }
        } catch (JsonProcessingException e){
            System.err.println("JsonProcessingException from 'ObjectNode node = mapper.readValue(searchResult, ObjectNode.class);'");
        }
    }
}
