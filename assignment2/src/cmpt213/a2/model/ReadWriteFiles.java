package cmpt213.a2.model;

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

import static cmpt213.a2.model.ConsumableFactory.fixType;

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
    private static final Gson GSON_READER = new GsonBuilder().registerTypeAdapter(LocalDateTime.class,
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
     * reads in a Json file into the foodRecord ArrayList<Consumable>
     * @return an ArrayList<Consumable>
     */
    //source: https://attacomsian.com/blog/gson-read-json-file
    public static ArrayList<Consumable> readJson(){
        ArrayList<Consumable> consumableList;
        try {
            Reader fileReader = Files.newBufferedReader(Paths.get("./itemsList.json"));
            consumableList = GSON_READER.fromJson(fileReader, new TypeToken<ArrayList<Consumable>>(){}.getType());
            fileReader.close();
            for (int n = 0; n < consumableList.size(); n++){
                consumableList.set(n, fixType(consumableList.get(n).getType(), consumableList.get(n)));
            }
            return consumableList;
        } catch (IOException error) {
            error.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * writes the ArrayList<Consumable> inputList into a Json file
     * @param inputList, an ArrayList<Consumable> generated/updated in program
     */
    public static void writeJson(ArrayList<Consumable> inputList){
        try {
            Writer jsonFileWriter = new FileWriter("./itemsList.json");
            GSON_READER.toJson(inputList, jsonFileWriter);
            jsonFileWriter.close();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }
}