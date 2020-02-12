package student.adventure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import java.io.File;

public class AdventureTest {
    Adventure objAdventure;
    Game objGame;

    @Before
    public void setUp() {
        File file = new File("src/main/resources/siebel.json");
        try {
            objAdventure = new ObjectMapper().readValue(file, Adventure.class);
        } catch (Exception exception) {

        }
        objGame = new Game();

    }
    @Test
    public void isGoValidRandomLetters() {
        Boolean isDirectionValid = objGame.isDirectionValidGo("kjla");
        assertEquals(false, isDirectionValid);
    }
    @Test
    public void isGoValid() {
        Boolean isDirectionValid = objGame.isDirectionValidGo("goEast");
        assertEquals(false, isDirectionValid);
    }
    @Test
    public void isGoValid1() {
        Boolean isDirectionValid = objGame.isDirectionValidGo("gO East");
        assertEquals(true, isDirectionValid);
    }
    @Test
    public void isRoomDescriptionCorrect() {
       String roomName = "SiebelEntry";
       String roomDescription =  objAdventure.getRoomByName(roomName).getDescription();

       assertEquals("You are in the west entry of Siebel Center. You can see the elevator, " +
               "the ACM office, and hallways to the north and east.", roomDescription);
    }
    @Test
    public void isValidDirectionFromSiebelEntryToEast() {
        String roomName = "SiebelEntry";
        String direction = "eAst";
        String nextRoom = objAdventure.getRoomByName(roomName).getDirectionByName(direction).getRoom();
        String nextRoomDescription = objAdventure.getRoomByName(nextRoom).getDescription();

        assertEquals("You are in the east hallway.  You can see Einstein Bros' Bagels and a stairway."
                ,nextRoomDescription);
    }
    @Test
    public void isValidDirectionFromSiebelBasementToUp() {
        String roomName = "SiebelBasement";
        String direction = "UP";
        String nextRoom = objAdventure.getRoomByName(roomName).getDirectionByName(direction).getRoom();
        String nextRoomDescription = objAdventure.getRoomByName(nextRoom).getDescription();

        assertEquals("You are in the east hallway.  You can see Einstein Bros' Bagels and a stairway."
                , nextRoomDescription);
    }
    @Test
    public void isValidDirectionFromSiebelNorthHallwayToNorthEast() {
        String roomName = "SiebelNorthHallway";
        String direction = "noRThEast";
        String nextRoom = objAdventure.getRoomByName(roomName).getDirectionByName(direction).getRoom();
        String nextRoomDescription = objAdventure.getRoomByName(nextRoom).getDescription();

        assertEquals("You are in Siebel 1112.  There is space for two code reviews in this room."
                , nextRoomDescription);
    }
    @Test
    public void testingRandomInput() {
        String direction = "kdjak";
        assertEquals("I don't understand 'kdjak'", objGame.inValidInput(direction));
    }
}