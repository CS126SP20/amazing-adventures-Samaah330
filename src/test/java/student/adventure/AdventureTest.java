package student.adventure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import java.io.PrintStream;
import java.io.File;


// how do i test to see if the file is right


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

   /* @Test
    public void sanityCheck() {
        // TODO: Remove this unnecessary test case.
        //assertThat("CS 126: Software Design Studio", CoreMatchers.containsString("Software"));
    }

    */
}