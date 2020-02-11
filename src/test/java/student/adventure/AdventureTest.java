package student.adventure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import java.io.PrintStream;
import java.io.File;

// how do i make these tests?
// use print stream and test helper methods
public class AdventureTest {
    Adventure a;
    @Before
    public void setUp() {
        // This is run before every test.
        a = new Adventure();
    }
    // testing is isDirectionValidGo() helper method
    @Test
    public void isGoValidRandomLetters() {
        Boolean isDirectionValid = Game.isDirectionValidGo(a, "kjla");
        assertEquals(false, isDirectionValid);
    }
    @Test
    public void isGoValid() {
        Boolean isDirectionValid = Game.isDirectionValidGo(a, "goEast");
        assertEquals(false, isDirectionValid);
    }
    @Test
    public void isGoValid1() {
        Boolean isDirectionValid = Game.isDirectionValidGo(a, "gO East");
        assertEquals(true, isDirectionValid);
    }
    // make more tests like this

    // testing initializeStartingRoom() helper method (do i even have to test this, what is there about this to test)
    @Test
    public void startingRoom() {
        String startingDescription = Game.initializeStartingRoom(a);
        // if file name is for library this happens
        File file = new File("src/main/resources/siebel.json");
        // if file name is for siebel or if there is no file name then this should happen
    }

    // tests for getNextRoomDescription()

    //tests for getRoomByName()

    // tests for getDirectionByName()
    /*@Test
    public void directionName() {
        Direction d =
    }*/



   /* @Test
    public void sanityCheck() {
        // TODO: Remove this unnecessary test case.
        //assertThat("CS 126: Software Design Studio", CoreMatchers.containsString("Software"));
    }

    */
}