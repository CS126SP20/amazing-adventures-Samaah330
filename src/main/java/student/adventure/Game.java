package student.adventure;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


/**
 * Class that holds most of the functionality and helper methods used in Main.java
 */
public class Game {

    /** PrintStream used print to console*/
    PrintStream stream;

    /** File that processes json */
    static File file;

    /** Url that processes json */
    static URL url;

    /** Default file used if user does not input valid file or url */
    final static String siebelFile = "src/main/resources/siebel.json";

    /** Url for Siebel */
    final static String siebelURL = "https://courses.grainger.illinois.edu/cs126/sp2020/resources/siebel.json";

    /** Instance of Adventure class*/
    Adventure adventure;

    /** Scanner for user input */
    Scanner input = new Scanner(System.in);

    public Game(OutputStream stream) throws IOException {
        file = new File(getFileName());
        this.stream = new PrintStream(stream);
        adventure = new ObjectMapper().readValue(file, Adventure.class);
        initializeStartingRoom();
    }

    public Game() throws IOException {
        this(System.out);
    }

    public Adventure getAdventure() {
        return adventure;
    }

    /**
     *
     * @throws MalformedURLException
     */
    public void runGame() throws MalformedURLException {
        try {
            /*file = new File(getFileName());
            adventure = new ObjectMapper().readValue(file, Adventure.class);
            initializeStartingRoom();*/
            printDescriptionsAndDirections();
        } catch(IOException exception) {
            System.out.println("cannot load");
            exception.printStackTrace();
        }
    }

    /**
     *
     * @throws IOException
     */
    public void printDescriptionsAndDirections() throws IOException {
        String roomName = adventure.getStartingRoom();
        while(true) {
            input = new Scanner(System.in);
            String inputDirection = input.nextLine();
            exitProgramQuitExit(inputDirection);
            if (inputDirection.equalsIgnoreCase("teleport")) {
                teleportItems(roomName);
            } else if (isValidAddItem(inputDirection)) {
                addItems(inputDirection, roomName);
            } else if (isValidRemoveItem(inputDirection)) {
                removeItems(inputDirection, roomName);
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

    /**
     *
     * @return
     */
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

    /**
     *
     * @param input
     * @param roomName
     */
    public void addItems(String input, String roomName) {
        String item = input.substring(4);
        adventure.getRoomByName(roomName).getItems().add(item);
        stream.println("Items Visible: " + adventure.getRoomByName(roomName).getItemsCommaSeperated());
    }

    /**
     *
     * @param input
     * @param roomName
     */
    public void removeItems(String input, String roomName) {
        String item = input.substring(7);
        if (adventure.getRoomByName(roomName).getItems().contains(item)) {
            adventure.getRoomByName(roomName).getItems().remove(item);
            stream.println("Items Visible: " + adventure.getRoomByName(roomName).getItemsCommaSeperated());
        } else {
            stream.println(item + " does not exist in this room");
        }
    }

    /**
     *
     * @param roomName
     */
    public void teleportItems(String roomName) {
        stream.println("which item would you like to teleport?");

        input = new Scanner(System.in);
        String inputItem = input.nextLine();

        while (!adventure.getRoomByName(roomName).getItems().contains(inputItem)) {
            stream.println(inputItem + " does not exist in this room for you to teleport." + "\n" +
                    "Please pick another item.");
            input = new Scanner(System.in);
            inputItem = input.nextLine();
        }

        stream.println("where would you like to teleport " + inputItem + "?");
        input = new Scanner(System.in);
        String inputRoom = input.nextLine();
        stream.println(inputRoom);

        // doesnt work because get rooms returns an object and that object is not equal to the string
        while (adventure.getRoomByName(inputRoom) == null) {
            stream.println(inputRoom + " does not exist. Please teleport " + inputItem + " to another room");
            input = new Scanner(System.in);
            inputRoom = input.nextLine();
        }

        adventure.getRoomByName(roomName).getItems().remove(inputItem);
        adventure.getRoomByName(inputRoom).getItems().add(inputItem);

        stream.println(inputItem + " has been teleported " + "to " + inputRoom);
    }

    /**
     *
     * @param roomName
     */
    public void reachedEndingRoom(String roomName) {
        if (roomName.equals(adventure.getEndingRoom())) {
            stream.println( "You have reached the final room");
            System.exit(1);
        }
    }

    /**
     *
     * @param inputDirection
     */
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

    /**
     *
     * @param input
     * @return
     */
    public boolean isValidAddItem(String input) {
        String inputAdd = input.substring(0,4);
        return inputAdd.equalsIgnoreCase("add ");
    }

    /**
     *
     * @param input
     * @return
     */
    public boolean isValidRemoveItem(String input) {
        String inputRemove = input.substring(0,7);
        return inputRemove.equalsIgnoreCase("remove ");
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
     * Returns the statement printed to console when there is an invalid input
     * @param inputDirection The invalid input that the user put in which is supposed to be
     *                       either teleport, add, remove, or a direction
     * @return Returns a String
     */
    public String isInvalidInput(String inputDirection) {
        return "I don't understand '" + inputDirection + "'";
    }
}
