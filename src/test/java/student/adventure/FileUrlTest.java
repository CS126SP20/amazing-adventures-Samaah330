package student.adventure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Tests File and Url input.
 */
public class FileUrlTest {

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
    }

    @Test
    public void testRandomFile() throws IOException {
        String input = "kdja" + "\nkdja";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        objGame = new Game(outputStream);
        assertEquals("src/main/resources/siebel.json", objGame.getFileName());
    }

    @Test
    public void testLibraryFile() throws IOException {
        String input = "src/main/resources/library.json" + "\nsrc/main/resources/library.json";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        objGame = new Game(outputStream);
        assertEquals("src/main/resources/library.json", objGame.getFileName());
    }

    @Test
    public void testSiebelFile() throws IOException {
        String input = "src/main/resources/siebel.json" + "\n" + "src/main/resources/siebel.json";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        objGame = new Game(outputStream);
        assertEquals("src/main/resources/siebel.json", objGame.getFileName());
    }

    @Test
    public void testSiebelURL() throws IOException {
        String input = "https://courses.grainger.illinois.edu/cs126/sp2020/resources/siebel.json"
                + "\n" + "https://courses.grainger.illinois.edu/cs126/sp2020/resources/siebel.json";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        objGame = new Game(outputStream);
        assertEquals("src/main/resources/siebel.json", objGame.getFileName());
    }

}
