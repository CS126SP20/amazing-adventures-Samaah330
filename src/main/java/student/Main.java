package student;

import com.fasterxml.jackson.databind.ObjectMapper;
import student.adventure.Adventure;
import student.adventure.Direction;
import student.adventure.Game;
import student.adventure.Room;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Takes in file name of JSON and puts player through an adventure game
 * @author Samaah Khan
 */
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String inputFile = input.nextLine();
        Adventure adventureExplorer;

        File file = new File(inputFile);
        String fileName = "src/main/resources/siebel.json";
        // if file does not exist, then default file is siebel json
        if (file.exists()) {
            fileName =  inputFile;
        }
        try {
            file = new File(fileName);
            adventureExplorer = new ObjectMapper().readValue(file, Adventure.class);
            Game objGame = new Game();
            System.out.println(objGame.initializeStartingRoom(adventureExplorer));
            String roomName = adventureExplorer.getStartingRoom();
            while(true) {
               input = new Scanner(System.in);
               String inputDirection = input.nextLine();
               if (inputDirection.equalsIgnoreCase("QUIT") ||
                       inputDirection.equalsIgnoreCase("EXIT")) {
                   System.exit(0);
               } else {
                   if (objGame.isValidDirection(inputDirection)) {
                       roomName = objGame.getNextRoom(adventureExplorer, roomName, inputDirection);
                       if (roomName.equals(adventureExplorer.getEndingRoom())) {
                           System.out.println( "You have reached the final room");
                       }
                       // get description based off of room
                       if (adventureExplorer.getRoomByName(roomName) != null) {
                           String descriptionByRoom = adventureExplorer.getRoomByName(roomName).getDescription();
                           String directionsToNextRoom = "From here you can go: " +
                                   adventureExplorer.getRoomByName(roomName).getAllDirectionsCommaSeparated();
                           System.out.println(descriptionByRoom + "\n" + directionsToNextRoom);
                       }
                   } else {
                       System.out.println(objGame.inValidInput(inputDirection));
                   }
               }
            }
        } catch(Exception exception) {
            System.out.println("cannot load");
            exception.printStackTrace();
        }
    }
}