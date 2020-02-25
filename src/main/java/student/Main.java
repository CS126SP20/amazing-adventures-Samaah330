package student;

import com.fasterxml.jackson.databind.ObjectMapper;
import student.adventure.Adventure;
import student.adventure.Game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Takes in file name of JSON and puts player through an adventure game
 * @author Samaah Khan
 */
public class Main {

    public static void main(String[] arg) throws MalformedURLException, IOException {
        Game game = new Game();
        game.runGame();
    }
}