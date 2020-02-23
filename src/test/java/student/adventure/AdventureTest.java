package student.adventure;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


// checking what was printed to console/ printstream
// how to check if a url exists
public class AdventureTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    //private final PrintStream originalOut = System.out;
    //private final PrintStream originalErr = System.err;

   // String inputFile;

    //Scanner input = new Scanner(System.in);

    Adventure objAdventure;
    Game objGame;
    File file;

    @Before
    public void setUp() throws MalformedURLException {
       // inputFile = input.nextLine();

        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        file = new File("src/main/resources/siebel.json");
        try {
            objAdventure = new ObjectMapper().readValue(file, Adventure.class);
        } catch (Exception exception) {

        }
        objGame = new Game();
    }

  /* @Test
    public void testSiebelFile() {
        String input = "kdja";
        System.setIn( new ByteArrayInputStream(input.getBytes()));
         //ByteArrayInputStream inputContent = new ByteArrayInputStream();
        //objGame.getFileName();

        assertEquals("src/main/resources/siebel.json", objGame.getFileName());
        /*assertEquals("You are on Matthews, outside the Siebel Center \n" +
                    "Your journey begins here \n" +
                    "From here you can go: East", outContent.toString());
    }*/

  //  @Rule
   // public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();


    @Test
    public void testRandomFile() {
        String input = "kdja";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        objGame = new Game();
        assertEquals("src/main/resources/siebel.json", objGame.getFileName());
    }

    @Test
    public void testLibraryFile() {
        String input = "src/main/resources/library.json";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        objGame = new Game();
        assertEquals("src/main/resources/library.json", objGame.getFileName());
    }

    @Test
    public void testSiebelFile() {
        String input = "src/main/resources/siebel.json";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        objGame = new Game();
        assertEquals("src/main/resources/siebel.json", objGame.getFileName());
    }

    @Test
    public void testSiebelURL() {
        String input = "https://courses.grainger.illinois.edu/cs126/sp2020/resources/siebel.json";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        objGame = new Game();
        assertEquals("src/main/resources/siebel.json", objGame.getFileName());
    }

    @Test
    public void testCorrectItemsSiebelEntry() {
        String items =  objAdventure.getRoomByName("SiebelEntry").getItemsCommaSeperated();
        assertEquals("sweatshirt and key", items);
    }

    @Test
    public void testCorrecctItemsMathewStreet() {
        String items =  objAdventure.getRoomByName("MatthewsStreet").getItemsCommaSeperated();
        assertEquals("coin", items);
    }

    @Test
    public void testCorrectItemsAcmOffice() {
        String items =  objAdventure.getRoomByName("AcmOffice").getItemsCommaSeperated();
        assertEquals("pizza and swag", items);
    }

    @Test
    public void testNoItemsSiebelNorthHallway() {
        String items =  objAdventure.getRoomByName("SiebelNorthHallway").getItemsCommaSeperated();
        assertEquals("No Items", items);
    }

    @Test
    public void testCorrectItemsSiebel1112() {
        String items =  objAdventure.getRoomByName("Siebel1112").getItemsCommaSeperated();
        assertEquals("USB-C connector and grading rubric", items);
    }

    @Test
    public void testCorrectItemsSiebelEastHallway() {
        String items =  objAdventure.getRoomByName("SiebelEastHallway").getItemsCommaSeperated();
        assertEquals("bagel and coffee", items);
    }

    @Test
    public void testCorrectItemsSiebel1314() {
        String items =  objAdventure.getRoomByName("Siebel1314").getItemsCommaSeperated();
        assertEquals("No Items", items);
    }

    @Test
    public void testCorrectItemsSiebelBasement() {
        String items =  objAdventure.getRoomByName("SiebelBasement").getItemsCommaSeperated();
        assertEquals("pencil", items);
    }

    @Test
    public void testGoValidRandomLettersFalse() {
        Boolean isDirectionValid = objGame.isDirectionValidGo("kjla");
        assertEquals(false, isDirectionValid);
    }

    @Test
    public void testGoValidNoSpaceFalse() {
        Boolean isDirectionValid = objGame.isDirectionValidGo("goEast");
        assertEquals(false, isDirectionValid);
    }

    @Test
    public void testGoValidCaseInsensitiveTrue() {
        Boolean isDirectionValid = objGame.isDirectionValidGo("gO East");
        assertEquals(true, isDirectionValid);
    }

    // test every possible direction
    @Test
    public void testRoomDescriptionSiebelEntry() {
       String roomName = "SiebelEntry";
       String roomDescription =  objAdventure.getRoomByName(roomName).getDescription();

       assertEquals("You are in the west entry of Siebel Center. You can see the elevator, " +
               "the ACM office, and hallways to the north and east.", roomDescription);
    }

    @Test
    public void testValidDirectionFromSiebelEntryToEast() {
        String roomName = "SiebelEntry";
        String direction = "eAst";
        String nextRoom = objAdventure.getRoomByName(roomName).getDirectionByName(direction).getRoom();
        String nextRoomDescription = objAdventure.getRoomByName(nextRoom).getDescription();

        assertEquals("You are in the east hallway.  You can see Einstein Bros' Bagels and a stairway."
                ,nextRoomDescription);
    }

    @Test
    public void testValidDirectionFromSiebelBasementToUp() {
        String roomName = "SiebelBasement";
        String direction = "UP";
        String nextRoom = objAdventure.getRoomByName(roomName).getDirectionByName(direction).getRoom();
        String nextRoomDescription = objAdventure.getRoomByName(nextRoom).getDescription();

        assertEquals("You are in the east hallway.  You can see Einstein Bros' Bagels and a stairway."
                , nextRoomDescription);
    }

    @Test
    public void testValidDirectionFromSiebelNorthHallwayToNorthEast() {
        String roomName = "SiebelNorthHallway";
        String direction = "noRThEast";
        String nextRoom = objAdventure.getRoomByName(roomName).getDirectionByName(direction).getRoom();
        String nextRoomDescription = objAdventure.getRoomByName(nextRoom).getDescription();

        assertEquals("You are in Siebel 1112.  There is space for two code reviews in this room."
                , nextRoomDescription);
    }

    @Test
    public void testRandomInput() {
        String direction = "kdjak";
        assertEquals("I don't understand 'kdjak'", objGame.isInvalidInput(direction));
    }
}