package student.adventure;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


/**
 * Class that holds most of the functionality and helper methods used in Main.java
 */
public class Game {


    PrintStream stream = new PrintStream(System.out);

    /** url that processes json */
    static File file;

    /** default URL used if user does not input valid URL */
    final static String defaultFile = "src/main/resources/siebel.json";

    /** instance of Adventure class*/
    Adventure adventure;

    Scanner input = new Scanner(System.in);

    /**
     * runs game
     */
    public void runGame() throws MalformedURLException {
        try {
            printDescriptionsAndDirections();
        } catch(IOException exception) {
            System.out.println("cannot load");
            exception.printStackTrace();
        }
    }

    public void printDescriptionsAndDirections() throws IOException {
        file = new File(getFileName());
        adventure = new ObjectMapper().readValue(file, Adventure.class);
        stream.println(initializeStartingRoom());
        String roomName = adventure.getStartingRoom();
        while(true) {
            input = new Scanner(System.in);
            String inputDirection = input.nextLine();
            if (inputDirection.equalsIgnoreCase("QUIT") ||
                    inputDirection.equalsIgnoreCase("EXIT")) {
                System.exit(1);
            } else {
                if (isValidDirection(inputDirection)) {
                    roomName = getNextRoom(adventure, roomName, inputDirection);
                    if (roomName.equals(adventure.getEndingRoom())) {
                        stream.println( "You have reached the final room");
                        System.exit(1);
                    }
                    // get description based off of room
                    if (adventure.getRoomByName(roomName) != null) {
                        String descriptionByRoom = adventure.getRoomByName(roomName).getDescription();
                        String directionsToNextRoom = "From here you can go: " +
                                adventure.getRoomByName(roomName).getAllDirectionsCommaSeparated();
                        stream.println(descriptionByRoom + "\n" + directionsToNextRoom);
                    }
                } else {
                   stream.println(isInvalidInput(inputDirection));
                }
            }
        }
    }

    //psuedocode for items:
    // TODO:   // support adding items
    //        // support removing items
    //        // print items with description of room
    //        // make items teleportable

    // Items visible: show items ( with the rest of the descriptions)
    // if you type "add laptop" then it redoes description with new items visible
    // remove items too (if item doesnt exist say that)
    // if ("teleport item to roomName") --> remove item and add them to the items in that room name
    public String getFileName() {
        String inputFile = input.nextLine();
        file = new File(inputFile);
        if (file.exists()) {
            return inputFile;
        }
        return defaultFile;
    }
    /**
     * Initializes the starting room description based on json file
     * @return returns a string of the starting description
     */
    public String initializeStartingRoom() {
        String startRoom = adventure.getStartingRoom();
        String startRoomDescription = adventure.getRoomByName(startRoom).getDescription();
        String allDirections =  adventure.getRoomByName(startRoom).getAllDirectionsCommaSeparated();

        return startRoomDescription + "\n" + "Your journey begins here" +
                "\n" + "From here you can go: " + allDirections;
    }

    /**
     * checks to see if the user input started with "go "
     * @param inputDirection direction that the user inputs
     * @return returns true/false if the user input started with "go "
     */
    public boolean isDirectionValidGo(String inputDirection) {
        String inputGo = inputDirection.substring(0,3);
        if (inputGo.equalsIgnoreCase("go ")) {
            return true;
        }
        return false;
    }

    /**
     * returns a String of the new room based off of the previous room and direction that the user input
     * @param adventure object of either siebel or libray json used to access rooms and directions
     * @param roomName name of the room used to get the next Room
     * @param directionName direction used that the player inputed to get the nextRoom
     * @return returns a String of the new room
     */
    public String getNextRoom(Adventure adventure, String roomName, String directionName) {
        directionName = directionName.substring(3);
        //returns the room of the next room based off of the previous room and direction that the user input
        String newRoomName = adventure.getRoomByName(roomName).getDirectionByName(directionName).getRoom();
        return newRoomName;

    }

    /**
     * checks to see if the user input a valid direction
     * @param directionName name of direction that the user input
     * @return returns false if the direction inputed by the user is not valid
     */
    public boolean isValidDirection(String directionName) {
        if (!(isDirectionValidGo(directionName))){
            return false;
        }
        return true;
    }

    /**
     * returns the statement printed to console when there is an invalid input
     * @param inputDirection the invalid input that the user put in which was supposed to be a direction
     * @return returns a String
     */
    public String isInvalidInput(String inputDirection) {
        return "I don't understand '" + inputDirection + "'";
    }
}
