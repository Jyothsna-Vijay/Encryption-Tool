import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Encrypt {
   private final WordStore adjectives;
   private final WordStore verbs;
   private final WordStore nouns;
   private final WordStore adverbs;

   //Initialising multiple word stores
   public Encrypt() throws IOException {
       try{
           adjectives = new WordStore( "adjectives.txt" );
           verbs = new VerbStore( "verbs.txt" ); //To change tense
           nouns = new WordStore( "nouns.txt" );
           adverbs = new WordStore( "adverbs.txt" );
       }
       catch(IOException e){
           System.out.println("Error loading word files: " + e.getMessage());
           throw e;
       }

   }

   //iterative method
    public List<String> encrypt(String input) {
       List<String> encryptionResult = new ArrayList<>(); // <string?>???
       List<Character> characters = new ArrayList<>();

       //convert all characters to lower case and add to an array, then check if they are letters
       for(char chars : input.toLowerCase().toCharArray()) {
           if(Character.isLetter(chars)) {
               characters.add(chars);
           }
       }

        //Unable to process user input
       if (characters.isEmpty()) {
           System.out.println("Input is unable to be encrypted, only use alphabetical characters.");
           return encryptionResult;
       }

       //Stores the index of the last element in a variable
       int wordIndex = characters.size() - 1;

       // Removes last character from list and maps it to a noun
       char nounChar = characters.remove(wordIndex);
       String noun = nouns.getRandomItem(nounChar);
       if(noun != null) {
           encryptionResult.add(0,noun);
       }

        // Removes last character from list and maps it to an adjective
       if(!characters.isEmpty()) {
           char adjectiveChar = characters.remove(wordIndex - 1);
           String adjective = adjectives.getRandomItem(adjectiveChar);
           if(adjective != null) {
               encryptionResult.add(0,adjective);
           }
       }

       //Verb? (Adverb Verb)* Adjective Noun


       // Iterates between choosing an adverb or verb
        // first word retrieved is always an adverb
       boolean adverb = (characters.size() % 2 == 0); //Ensures the last word is always a verb
       while(!characters.isEmpty()) {
           char adverbChar = characters.remove(0); //to remove first letter
           //if adverb is true retrieve adverb, else retrieve verb
           String string = adverb ? adverbs.getRandomItem(adverbChar) : verbs.getRandomItem(adverbChar);

           if(string != null) {
               encryptionResult.add(0,string);
               adverb = !adverb; //To switch between true and false for iteration
           }

       }
       return encryptionResult;
    }

    public static void main(String[] args) {
       try{
           if(args.length == 0) {
               System.out.println("Enter password for encryption: ");
               return;
           }

           // Remove any spaces from the beginning or end of the word
           String input = args[0].trim();
           Encrypt encryption = new Encrypt(); //instance of Encrypt class
           List<String> encryptionResult = encryption.encrypt(input);

           //iterates over each word in result list
           for(String i : encryptionResult) {
               System.out.println(i);
           }

       }
       //catch file errors
       catch(IOException e) {
           System.out.println("There was an error loading word files: " + e.getMessage());
       }
       //catch unexpected errors
       catch (Exception e) {
           System.out.println("An error has occurred: " + e.getMessage());
           throw e;
       }

    }

}
