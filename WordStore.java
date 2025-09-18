import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WordStore {
    private final HashMap<Character, ArrayList<String>> store;
    private final HashMap<String, Character> reverse;
    private final Random rand;

    //default constructor
    public WordStore() {
        this.store = new HashMap<>();
        this.reverse = new HashMap<>();
        this.rand = new Random();
    }

    public WordStore(String fileName) throws IOException {
        this(); // calls default constructor to initialise store, reverse and rand

        //File file = new File(fileName);
        //System.out.println("Trying to read file: " + file.getAbsolutePath()); // Debugging

        //Read file by line
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            String line;
            while ((line = reader.readLine()) != null) { // until no more lines in file
                String[] parts = line.split(","); //split into word + character
                if(parts.length == 2){
                    char key = Character.toLowerCase(parts[1].trim().charAt(0));
                    String item = parts[0].trim().toLowerCase(); //remove excess spaces
                    add(key, item);
                }
            }
        }
        catch(IOException e){
            System.out.println("Error loading file: " + e.getMessage());
            throw e;
        }

    }

    //Method to add word and letter to WordStore
    public void add(char key, String item) {
        key = Character.toLowerCase(key);
        item = item.toLowerCase();
        store.putIfAbsent(key, new ArrayList<>());//creates list for letter
        store.get(key).add(item);
        reverse.put(item, key);
    }

    //Used for encrypt class - randomly outputs a word that is associated to the key
    public String getRandomItem(char key) {
        key = Character.toLowerCase(key);
        ArrayList<String> items = store.get(key);
        if(items == null || items.isEmpty()) {
            return null;
        }
        return items.get(rand.nextInt(items.size()));
    }

    //Used for decrypt class
    public Character getLetter(String input) {
        return reverse.get(input.toLowerCase());
    }
}

