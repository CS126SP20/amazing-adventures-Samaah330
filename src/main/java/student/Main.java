package student;

import student.adventure.Game;

import java.io.IOException;

/**
 * Takes in file name of JSON and puts player through an adventure game
 * @author Samaah Khan
 */
public class Main {
    public static void main(String[] arg) throws IOException {
        Game game = new Game();
        game.runGame();
    }
}