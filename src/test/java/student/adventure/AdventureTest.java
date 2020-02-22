package student.adventure;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


// checking what was printed to console
//should I test for other url files
public class AdventureTest {
    Adventure objAdventure;
    Game objGame;
    URL url;

    @Before
    public void setUp() throws MalformedURLException {
        url = new URL("https://courses.grainger.illinois.edu/cs126/sp2020/resources/siebel.json");
        try {
            objAdventure = new ObjectMapper().readValue(url, Adventure.class);
        } catch (Exception exception) {
        }

        objGame = new Game();

    }

    @Test
    public void testSiebelURLFile() throws IOException {
        String startingRoomDescription = objAdventure.getStartingRoom();

        assertEquals("MatthewsStreet", startingRoomDescription);
    }

    /*@Test // should you check this
    public void testLibraryURLFile() throws IOException {
        url = new URL("https://courses.grainger.illinois.edu/cs126/sp2020/resources/library.json");
        objAdventure = new ObjectMapper().readValue(url, Adventure.class);

        assertEquals();
    }/*

   /* @Test
    public void testLibraryURLFile() throws IOException {
        url = new URL("https://courses.grainger.illinois.edu/cs126/sp2020/resources/kdajlkfj.json");
        objAdventure = new ObjectMapper().readValue(url, Adventure.class);

        // read what the console says and see if its says "cannot load"

    }*/

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