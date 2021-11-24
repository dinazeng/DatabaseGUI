package foodexpdatestracker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Reads in a json into an ArrayList<foodItem>
 * Returns a filled ArrayList of FoodItem objects
 *
 * writes an ArrayList<foodItem> object into a json file when program ends
 */
public class ReadWriteFiles {
    /**
     * Given assignment code
     */
    private static final Gson myGson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class,
            new TypeAdapter<LocalDateTime>() {
                @Override
                public void write(JsonWriter jsonWriter,
                                  LocalDateTime localDateTime) throws IOException {
                    jsonWriter.value(localDateTime.toString());
                }
                @Override
                public LocalDateTime read(JsonReader jsonReader) throws IOException {
                    return LocalDateTime.parse(jsonReader.nextString());
                }
            }).setPrettyPrinting().create();

    /**
     * reads in a Json file into the foodRecord ArrayList<FoodItem>
     * @return an ArrayList<FoodItem>
     */
    //source: https://attacomsian.com/blog/gson-read-json-file
    public static ArrayList<FoodItem> readJson(){
        ArrayList<FoodItem> foodRecord;
        try {
            Reader fileReader = Files.newBufferedReader(Paths.get("./foodRecord.json"));
            foodRecord = myGson.fromJson(fileReader, new TypeToken<ArrayList<FoodItem>>() {}.getType());
            fileReader.close();
            return foodRecord;
        } catch (IOException error) {
            error.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * writes the ArrayList<FoodItem> inputList into a Json file
     * @param inputList, an ArrayList<FoodItem> generated/updated in program
     */
    public static void writeJson(ArrayList<FoodItem> inputList){
        try {
            Writer jsonFileWriter = new FileWriter("./foodRecord.json");
            myGson.toJson(inputList, jsonFileWriter);
            jsonFileWriter.close();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }
}