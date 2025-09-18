import java.io.IOException;
    //inheriting methods
public class VerbStore extends WordStore {

    public VerbStore() {
        super(); //calls default WordStore constructor
    }
    public VerbStore(String fileName) throws IOException {
        super(fileName); //calls file reading constructor
    }
    public String getRandomItem(char key){
        String word = super.getRandomItem(key); //fetching verb
        if (word != null){
            if(word.endsWith("e")){
                word = word.substring(0, word.length() - 1) + "ing"; //changing tense
            }
            else{
                word = word + "ing";
            }
        }
        return word;

    }
}


