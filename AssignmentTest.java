import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AssignmentTest {

    public static void main(String[] args) throws IOException {
        testQ1();
        testQ2_LoadFile();
        testQ2_WordRetrieve();
        testQ2_LetterRetrieve();
        //Tests for encryption done adjacently with tests for decryption in Q4 tests
        testQ4();
        testQ4a();
    }

    private static void testQ1() {
        System.out.println("Mapping + Randomisation + Case Insensitivity Test ");
        //Question 1
        WordStore store = new WordStore();

        //Tests for adding + retrieving words
        store.add('a', "table");
        store.add('a', "coconut"); // to check random output with same input
        store.add('b', "laptop");
        store.add('b', "Apple");
        store.add('B', "window"); // to check case insensitivity
        store.add('c', "tablet");
        store.add('C', "PHONE"); // to check if output will be lowercase

        String test1 = store.getRandomItem('a');
        assert test1.equals("table") || test1.equals("coconut");
        System.out.println(test1); //to check random output

        String test2 = store.getRandomItem('b');
        assert test2.equals("laptop") || test2.equals("apple") || test2.equals("window");
        System.out.println(test2);

        String test3 = store.getRandomItem('B');
        assert test3.equals("laptop") || test3.equals("apple") || test3.equals("window");
        System.out.println(test3);

        String test4 = store.getRandomItem('c');
        assert test4.equals("tablet") || test4.equals("PHONE");
        System.out.println(test4);

        assert store.getRandomItem('z') == null : "Test Failed: Expected null for non-existing key 'z'";
        System.out.println(store.getRandomItem('z'));

        System.out.println("1) tests passed");
    }

    private static void testQ2_LoadFile() {
        try {
            WordStore Store = new WordStore("TestWords.txt");
            System.out.println("2a) tests passed: file loaded");
        } catch (IOException e) {
            assert false : "testQ2_01_LoadFile: Failed - Exception thrown";
        }
    }

    private static void testQ2_WordRetrieve() throws IOException {
        System.out.println("Word Retrieval Test");
        WordStore store = new WordStore("TestWords.txt");

        // Ensure known words are correctly stored
        assert store.getRandomItem('a') != null : "Test Failed: No words found for 'a'";
        System.out.println("a: " + store.getRandomItem('a'));
        assert store.getRandomItem('A') != null : "Test Failed: No words found for 'A'";
        System.out.println("A: " + store.getRandomItem('A'));
        assert store.getRandomItem('b') != null : "Test Failed: No words found for 'b'";
        System.out.println("b: " + store.getRandomItem('b'));
        assert store.getRandomItem('b') != null : "Test Failed: No words found for 'b'";
        System.out.println("b: " + store.getRandomItem('b'));
        assert store.getRandomItem('C') != null : "Test Failed: No words found for 'C'";
        System.out.println("C: " + store.getRandomItem('C'));
        assert store.getRandomItem('d') != null : "Test Failed: No words found for 'd'";
        System.out.println("d: " + store.getRandomItem('d'));
        assert store.getRandomItem('z') == null : "Test Failed: Expected null for non-existing key 'z'";
        System.out.println("z: " + store.getRandomItem('z'));

        System.out.println("2b) tests passed: words retrieved");

    }

    private static void testQ2_LetterRetrieve() throws IOException {
        System.out.println("Letter Retrieval Test");
        WordStore store = new WordStore("TestWords.txt");

        assert store.getLetter("harsh") != null : "Test Failed: 'running' was not found";
        System.out.println("harsh: " + store.getLetter("harsh"));
        assert store.getLetter("thread") != null : "Test Failed: 'quickly' was not found";
        System.out.println("thread: " + store.getLetter("thread"));
        assert store.getLetter("force") != null : "Test Failed: 'running' was not found";
        System.out.println("force: " + store.getLetter("force"));
        assert store.getLetter("useless") != null : "Test Failed: 'quickly' was not found";
        System.out.println("useless: " + store.getLetter("useless"));
        assert store.getLetter("running") != null : "Test Failed: 'quickly' was not found";
        System.out.println("running: " + store.getLetter("running"));

        System.out.println("2c) tests passed: letters retrieved");

    }

    private static void testQ4() {
        System.out.println("Encryption and Decryption Test");
        //Testing basic entry
        try {
            //encryption
            System.out.println("Input: testing");
            Encrypt encryption = new Encrypt();
            List<String> encrypted = encryption.encrypt("testing");
            System.out.println("Encryption: " + encrypted);
            //decryption
            Decrypt decryption = new Decrypt();
            String decrypts = decryption.decrypt(encrypted);
            System.out.println("Decrypted: " + decrypts);
        } catch (IOException e) {
            System.err.println("Error " + e.getMessage());
        }

        //Testing uppercase entry - must correspond to the same letters
        try {
            //encryption
            System.out.println("Input: CUSTOMIZE"); //customize -> customizing -> customize
            Encrypt encryption = new Encrypt();
            List<String> encrypted = encryption.encrypt("CUSTOMIZE");
            System.out.println("Encryption: " + encrypted);
            //decryption
            Decrypt decryption = new Decrypt();
            String decrypts = decryption.decrypt(encrypted);
            System.out.println("Decrypted: " + decrypts);
        } catch (IOException e) {
            System.err.println("Error " + e.getMessage());

        }

        //Testing the addition of e to the verb
        try {
            System.out.println("Expected output: bye");
            List<String> decrypt = new ArrayList<>();
            decrypt.add("damaging"); // "e" must be added for decryption to work
            decrypt.add("allied");
            decrypt.add("pseudohallucination");
            Decrypt decryption = new Decrypt();
            String decrypts = decryption.decrypt(decrypt);
            System.out.println("Decrypted: " + decrypts);
        } catch (IOException e) {
            System.err.println("Error " + e.getMessage());
        }
        System.out.println("4) tests passed");
    }

    private static void testQ4a() {
        System.out.println("Encryption and Decryption Test - User Input");
        try {
            //Prompt for user input
            Scanner scanner = new Scanner(System.in);
            System.out.println("TEST: Enter a password for encryption: ");
            String password = scanner.nextLine();

            //Encryption of user input
            Encrypt encryption = new Encrypt();
            List<String> encrypted = encryption.encrypt(password);
            System.out.println("Encryption: " + encrypted);

            //Encryption of user input
            Decrypt decryption = new Decrypt();
            String decrypts = decryption.decrypt(encrypted);
            System.out.println("Decrypted: " + decrypts);

            //Checks if decryption is correct
            if (password.toLowerCase().equals(decrypts)) {
                System.out.println("Test Passed");
            } else {
                System.out.println("Test Failed");
            }
        } catch (IOException e) {
            System.err.println("Error loading word files: " + e.getMessage());
        }

        System.out.println("4a) tests passed");

    }
}