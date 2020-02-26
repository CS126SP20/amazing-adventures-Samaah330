package student.adventure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class AdventureTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    Adventure objAdventure;
    Game objGame;
    File file;

    @Before
    public void setUp() throws IOException {
        file = new File("src/main/resources/siebel.json");
        try {
            objAdventure = new ObjectMapper().readValue(file, Adventure.class);
        } catch (Exception exception) {

        }
        System.setIn(new ByteArrayInputStream("src/main/resources/siebel.json".getBytes()));
        objGame = new Game(outContent);
    }

    @Test
    public void testAddItemSiebelEntry() {
        String input = "aDd penny";
        objGame.addItems(input, "SiebelEntry");
        String items = objGame.getAdventure().getRoomByName("SiebelEntry").getItemsCommaSeperated();
        assertEquals("sweatshirt, key and penny", items);
    }

    @Test
    public void testAddItemsTwiceMatthewsStreet() {
        objGame.addItems("add honey", "MatthewsStreet");
        objGame.addItems("add pizza", "MatthewsStreet");
        String items = objGame.getAdventure().getRoomByName("MatthewsStreet").getItemsCommaSeperated();
        assertEquals("coin, honey and pizza", items);
    }

    @Test
    public void testRemoveWhenNoItemsPresentSiebelNorthHallway() {
        objGame.removeItems("remove poster", "SiebelNorthHallway");
        assertTrue(outContent.toString().contains("Cannot remove items from this room because there are no items" ));
    }

    @Test
    public void testRemoveAllItemsAcmOffice() {
        objGame.removeItems("remove swag", "AcmOffice");
        objGame.removeItems("remove pizza", "AcmOffice");

        String items = objGame.getAdventure().getRoomByName("AcmOffice").getItemsCommaSeperated();
        assertEquals("No Items", items);
    }

    @Test
    public void addAndRemoveItemAcmOffice() {
        objGame.addItems("add shoe", "AcmOffice");
        objGame.removeItems("remove swag", "AcmOffice");
        String items = objGame.getAdventure().getRoomByName("AcmOffice").getItemsCommaSeperated();
        assertEquals("pizza and shoe", items);
    }

    @Test
    public void addThenRemoveSameItemSiebel1112()  {
        objGame.addItems("add polish","Siebel1112");
        objGame.removeItems("remove polish","Siebel1112");
        String items = objGame.getAdventure().getRoomByName("Siebel1112").getItemsCommaSeperated();
        assertEquals("USB-C connector and grading rubric", items);
    }

    @Test
    public void testRemoveNonexistentItemSiebelEastHallway() {
        String input = "remove honey";
        objGame.removeItems(input, "SiebelEastHallway");
        assertTrue(outContent.toString().contains("honey does not exist in this room"));
    }

    @Test
    public void testRemoveItemsSiebelBasement() {
        String input = "remove key";
        objGame.removeItems(input, "SiebelBasement");
        String items = objGame.getAdventure().getRoomByName("SiebelBasement").getItemsCommaSeperated();
        assertEquals("pencil", items);
    }

    /*
    @Test
    public void testTeleportAddItemToSiebelEntry() {
        String inputItem = "coin";
        String currentRoom = "MatthewsStreet";
        String inputRoom = "SiebelEntry";
        objGame.teleportItems(currentRoom, inputItem, inputRoom);
        String items = objGame.getAdventure().getRoomByName(inputRoom).getItemsCommaSeperated();
        assertEquals("sweatshirt, key and coin", items);
    }

    @Test
    public void testTeleportRemoveItemFromMatthewsStreet() {
        String inputItem = "coin";
        String currentRoom = "MatthewsStreet";
        String inputRoom = "SiebelEntry";
        objGame.teleportItems(currentRoom, inputItem, inputRoom);
        String items = objGame.getAdventure().getRoomByName(currentRoom).getItemsCommaSeperated();
        assertEquals("No Items", items);
    }

    @Test
    public void testCorrectItemsSiebelEntry() {
        String items =  objAdventure.getRoomByName("SiebelEntry").getItemsCommaSeperated();
        assertEquals("sweatshirt and key", items);
    }

    @Test
    public void testValidItemToTeleportFalse() {
        assertFalse(objGame.isTeleportableItem("MatthewsStreet", "Skateboard"));
    }

    @Test
    public void testValidItemToTeleportTrue() {
        assertTrue(objGame.isTeleportableItem("MatthewsStreet", "coin"));
    }
*/
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
    public void testValidDirectionFromSiebelBasementToUp() {
        String roomName = "SiebelBasement";
        String direction = "UP";
        String nextRoom = objAdventure.getRoomByName(roomName).getDirectionByName(direction).getRoom();
        String nextRoomDescription = objAdventure.getRoomByName(nextRoom).getDescription();

        assertEquals("You are in the east hallway.  You can see Einstein Bros' Bagels and a stairway."
                , nextRoomDescription);
    }

    @Test
    public void testValidDirectionFromMatthewStreetToEast() {
        String roomName = "MatthewsStreet";
        String direction = "East";
        String nextRoom = objAdventure.getRoomByName(roomName).getDirectionByName(direction).getRoom();
        String nextRoomDescription = objAdventure.getRoomByName(nextRoom).getDescription();

        assertEquals("You are in the west entry of Siebel Center. You can see the elevator," +
                " the ACM office, and hallways to the north and east.", nextRoomDescription);
    }

    @Test
    public void testValidDirectionFromSiebelEntryToNorth() {
        String roomName = "SiebelEntry";
        String direction = "north";
        String nextRoom = objAdventure.getRoomByName(roomName).getDirectionByName(direction).getRoom();
        String nextRoomDescription = objAdventure.getRoomByName(nextRoom).getDescription();

        assertEquals("You are in the north hallway.  You can see Siebel 1112 and the door toward NCSA."
                ,nextRoomDescription);
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