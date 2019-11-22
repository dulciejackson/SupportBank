package training.supportbank;

import com.google.gson.*;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

public class JSONReader implements Reader {
    private Bank currentBank;

    public JSONReader(Bank currentBank) {
        this.currentBank = currentBank;
    }

    public void readFile(String fileName) {
        System.out.println("Attempting to read transactions from JSON file...");
        GsonBuilder gsonBuilder = new GsonBuilder();
        // sets up adapter to deal with non-primitive type LocalDate
        gsonBuilder.registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (jsonElement, type, jsonDeserializationContext) ->
                LocalDate.parse(jsonElement.getAsString()));
        Gson gson = gsonBuilder.create();

        JsonParser parser = new JsonParser();
        JsonArray a = new JsonArray();
        try {
            a = (JsonArray) parser.parse(new FileReader(fileName));

            for(int i=0; i<a.size(); i++) {
                JsonElement jsonObj = a.get(i);
                Transaction fileT = gson.fromJson(jsonObj, Transaction.class);
                Account sender = currentBank.getAccount(fileT.getFromAccount());
                Account receiver = currentBank.getAccount(fileT.getToAccount());
                sender.pay(fileT);
                receiver.earn(fileT);
            }

            System.out.println("JSON file " + fileName + " read successfully.");

        } catch (IOException e) {
            LOGGER.error("JSON read error: Could not load file with name " + fileName);
            e.printStackTrace();
        }
    }
}
