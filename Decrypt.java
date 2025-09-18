import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Decrypt {
    private final WordStore adjectives;
    private final WordStore verbs;
    private final WordStore nouns;
    private final WordStore adverbs;

    //Initialising multiple word stores
    public Decrypt() throws IOException {
        try{
            adjectives = new WordStore( "adjectives.txt" );
            verbs = new WordStore( "verbs.txt" );
            nouns = new WordStore( "nouns.txt" );
            adverbs = new WordStore( "adverbs.txt" );
        }
        catch(IOException e){
            System.out.println("Error loading word files: " + e.getMessage());
            throw e;
        }

    }

    public String decrypt(List<String> encryptedInput){

        if(encryptedInput.isEmpty()){
            System.out.println("Input is unable to be decrypted.");
            return ""; //??
        }

        List<String> input = new ArrayList<>(encryptedInput);
        List<Character> characters = new ArrayList<Character>();

        //Removing noun from input and mapping to letter
        String noun = input.remove(input.size()-1);
        Character nounChar = nouns.getLetter(noun);
        if (nounChar != null){
            characters.add(0, nounChar);
        }

        //Removing adjective from input and mapping to letter
        String adjective = input.remove(input.size()-1);
        Character adjectiveChar = adjectives.getLetter(adjective);
        if (adjectiveChar != null){
            characters.add(0, adjectiveChar);
        }

        //Mapping verb and adverb iteration to letters
        //(characters.size() % 2 == 0)
        boolean adverb = false; //Ensures the last word is always a verb
        while (!input.isEmpty()) {
            String word = input.remove(0);
            //System.out.println(word);
            Character letter = null;

            if (adverb){
                letter = adverbs.getLetter(word);
            }
            else {
                if (word.endsWith("ing")) {
                     word = word.substring(0, word.length() - 3);
                   // word = ogWord + "e";
                }
                //System.out.println(word); //Test to see if "ing" is removed
                letter = verbs.getLetter(word);
                //System.out.println(letter);  //Test to see if corresponding letter is fetched
                if(letter == null){
                    word = word + "e";
                    letter = verbs.getLetter(word);
                }
            }
            if (letter != null){
                characters.add(0,letter);
            }
            adverb = !adverb; //To change between verb and adverb
        }

        StringBuilder decryptedWord = new StringBuilder();
        for(char i : characters){
            decryptedWord.append(i);
        }
        return decryptedWord.toString();
    }

    public static void main(String[] args){
        try {
            if (args.length == 0) {
                System.out.println("Enter the encrypted string for decryption: ");
                return;
            }

            List<String> encryptedWords = new ArrayList<>();
            for(String word : args){
                encryptedWords.add(word.trim()); //Remove excess spaces from the start and end ?
            }

            Decrypt decryption = new Decrypt();
            String password = decryption.decrypt(encryptedWords);

            System.out.println(password);

        } catch (IOException e) {
            System.out.println("Error loading word files: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}