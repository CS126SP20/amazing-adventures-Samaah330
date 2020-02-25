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

    /** file that processes json */
    static File file;

    static URL url;

    /** default file used if user does not input valid file or url */
    final static String siebelFile = "src/main/resources/siebel.json";

    /** url */
    final static String siebelURL = "https://courses.grainger.illinois.edu/cs126/sp2020/resources/siebel.json";

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
        initializeStartingRoom();
        String roomName = adventure.getStartingRoom();
        while(true) {
            input = new Scanner(System.in);
            String inputDirection = input.nextLine();
            exitProgramQuitExit(inputDirection);
            if (isValidAddItem(inputDirection)) {
                addItems(inputDirection, roomName);
            } else if (isValidDirection(inputDirection)) {
                roomName = getNextRoom(adventure, roomName, inputDirection);
                reachedEndingRoom(roomName);
                if (adventure.getRoomByName(roomName) != null) {
                    String descriptionByRoom = adventure.getRoomByName(roomName).getDescription();
                    String directionsToNextRoom = "From here you can go: " +
                            adventure.getRoomByName(roomName).getAllDirectionsCommaSeparated();
                    String items = "Items visible: " + adventure.getRoomByName(roomName).getItemsCommaSeperated();
                    stream.println(descriptionByRoom + "\n" + directionsToNextRoom + "\n" + items);
                }
            } else {
                stream.println(isInvalidInput(inputDirection));
            }
        }
    }

    //psuedocode for items:
    // TODO:   // support adding items
    //        // support removing items
    //        // make items teleportable

    // Items visible: show items ( with the rest of the descriptions)
    // if you type "add laptop" then it redoes description with new items visible
    // remove items too (if item doesnt exist say that)
    // if ("teleport item to roomName") --> remove item and add them to the items in that room name

    public String getFileName() {
        String inputFile = input.nextLine();

        if (inputFile.equals(siebelURL)) {
            return siebelFile;
        }
        file = new File(inputFile);
        if (file.exists()) {
            return inputFile;
        }
        return siebelFile;
    }

    public void addItems(String input, String roomName) {
        String item = input.substring(4);
        adventure.getRoomByName(roomName).getItems().add(item);
        stream.println("Items Visible: " + adventure.getRoomByName(roomName).getItemsCommaSeperated());
    }

    public void reachedEndingRoom(String roomName) {
        if (roomName.equals(adventure.getEndingRoom())) {
            stream.println( "You have reached the final room");
            System.exit(1);
        }
    }

    public void exitProgramQuitExit(String inputDirection) {
        if (inputDirection.equalsIgnoreCase("QUIT") ||
                inputDirection.equalsIgnoreCase("EXIT")) {
            System.exit(1);
        }
    }

    /**
     * Initializes the starting room description based on json file
     * @return returns a string of the starting description
     */
    public void initializeStartingRoom() {
        String startRoom = adventure.getStartingRoom();
        String startRoomDescription = adventure.getRoomByName(startRoom).getDescription();
        String allDirections = adventure.getRoomByName(startRoom).getAllDirectionsCommaSeparated();
        String allItems = adventure.getRoomByName(startRoom).getItemsCommaSeperated();

        stream.println(startRoomDescription + "\n" + "Your journey begins here" +
                "\n" + "From here you can go: " + allDirections + "\n" + "Items Visible: " + allItems);
    }

    /**
     * checks to see if the user input started with "go "
     * @param inputDirection direction that the user inputs
     * @return returns true/false if the user input started with "go "
     */
    public boolean isDirectionValidGo(String inputDirection) {
        String inputGo = inputDirection.substring(0,3);
        return inputGo.equalsIgnoreCase("go ");
    }

    public boolean isValidAddItem(String input) {
        String inputAdd = input.substring(0,4);
        return inputAdd.equalsIgnoreCase("add ");
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
        stream.println(roomName);
        stream.println(directionName);
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
