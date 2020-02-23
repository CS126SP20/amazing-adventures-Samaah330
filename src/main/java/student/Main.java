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

    public static void main(String[] arg) throws MalformedURLException {

        Game game = new Game();
        game.runGame();

        // support adding items
        // support removing items
        // print items with description of room
        // make items teleportable
       /* Scanner input = new Scanner(System.in);
        String inputFile = input.nextLine();
        Adventure adventure;
        Game objGame = new Game();

        File file = new File(inputFile);
        String urlName;
        if (file.exists()) {
            urlName =  inputFile;
        } else {
            urlName = defaultURL;
        }
        try {
            url = new URL(urlName);
            adventure = new ObjectMapper().readValue(url, Adventure.class);
            System.out.println(objGame.initializeStartingRoom(adventure));
            String roomName = adventure.getStartingRoom();
            while(true) {
               input = new Scanner(System.in);
               String inputDirection = input.nextLine();
               if (inputDirection.equalsIgnoreCase("QUIT") ||
                       inputDirection.equalsIgnoreCase("EXIT")) {
                   System.exit(0);
               } else {
                   if (objGame.isValidDirection(inputDirection)) {
                       roomName = objGame.getNextRoom(adventure, roomName, inputDirection);
                       if (roomName.equals(adventure.getEndingRoom())) {
                           System.out.println( "You have reached the final room");
                           System.exit(0);
                       }
                       // get description based off of room
                       if (adventure.getRoomByName(roomName) != null) {
                           String descriptionByRoom = adventure.getRoomByName(roomName).getDescription();
                           String directionsToNextRoom = "From here you can go: " +
                                   adventure.getRoomByName(roomName).getAllDirectionsCommaSeparated();
                           System.out.println(descriptionByRoom + "\n" + directionsToNextRoom);
                       }
                   } else {
                       System.out.println(objGame.isInvalidInput(inputDirection));
                   }
               }
            }
        } catch(IOException exception) {
            System.out.println("cannot load");
            exception.printStackTrace();
        }*/
    }
}