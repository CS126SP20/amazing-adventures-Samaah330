package student.adventure;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;


/**
 * Game displays items, room description, directions,
 * and allows user to navigate through rooms, add/remove items,
 * and teleport.
 */
public class Game {

    /** PrintStream used print to console*/
    private PrintStream stream;

    /** File that processes json */
    private static File file;

    /** Default file used if user does not input valid file or url */
    private final static String siebelFile = "src/main/resources/siebel.json";

    /** Url for Siebel */
    private final static String siebelURL = "https://courses.grainger.illinois.edu/cs126/sp2020/resources/siebel.json";

    /** Instance of Adventure class*/
    private Adventure adventure;

    /** Scanner for user input */
    private Scanner input = new Scanner(System.in);

    /** Word used to signal that the user wants to teleport an item*/
    private String teleport = "teleport";

    /** The index that the item starts when the user inputs "add " item */
    private final int startIndexItemAdd = 4;

    /** The index that the item starts when the user inputs "remove" item */
    private final int startIndexItemRemove = 7;

    /** The index that the direction name from the input starts*/
    private final int startindexDirection = 3;

    /** The start index of entire user input */
    private final int startIndexInput = 0;

    /** Message when there are no items in the room*/
    private final String noItems = "No Items";

    /** A word that is used when user wants to quit/exit game*/
    private final String quit = "QUIT";

    /** Another word that is use when the user wants to quit/exit game*/
    private final String exit = "EXIT";

    /** Word used to indicate user wants to remove an item*/
    private final String remove = "remove ";

    /** Word used to indicate user wants to add an item*/
    private final String add = "add ";

    /** Word used to indicate user wants to go a certain direction*/
    private final String go = "go ";

    public Game(OutputStream stream) throws IOException {
        file = new File(getFileName());
        this.stream = new PrintStream(stream);
        adventure = new ObjectMapper().readValue(file, Adventure.class);
        initializeStartingRoom();
    }

    public Game() throws IOException {
        this(System.out);
    }

    /**
     * Used to access the instance of Adventure in tests
     * @return instance of Adventure
     */
    public Adventure getAdventure() {
        return adventure;
    }

    /**
     * runs one game through an adventure based on rooms, directions, and items in a json file
     */
    public void runGame(){
        try {
            String roomName = adventure.getStartingRoom();
            while (true) {
                input = new Scanner(System.in);
                String inputDirection = input.nextLine();
                exitProgramQuitExit(inputDirection);
                if (inputDirection.equalsIgnoreCase(teleport)) {
                    askUserTeleportItems(roomName);
                } else if (doesUserWantAddItem(inputDirection)) {
                    addItems(inputDirection, roomName);
                } else if (doesUserWantRemoveItem(inputDirection)) {
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
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Checks what user input as file or url.
     * If user inputs Siebel URL
     * @return a file that user input, returns default file, or returns siebelFile if
     * input matches siebelURL
     */
    public String getFileName() {
        String inputFileOrUrl = input.nextLine();

        if (inputFileOrUrl.equals(siebelURL)) {
            return siebelFile;
        }

        file = new File(inputFileOrUrl);

        if (file.exists()) {
            return inputFileOrUrl;
        }

        return siebelFile;
    }

    /**
     * Adds item to room if user chooses to add a new item
     * Prints updated items in the room after adding
     * @param input Contains "add" and an item that the user wants to add to a room
     * @param roomName The room that the user wants to add the item to
     */
    public void addItems(String input, String roomName) {
        String item = input.substring(startIndexItemAdd);
        adventure.getRoomByName(roomName).getItems().add(item);
        stream.println("Items Visible: " + adventure.getRoomByName(roomName).getItemsCommaSeperated());
    }

    /**
     * Removes item from the room if user chooses to remove the item
     * If there are no items in the room to remove, prints error message
     * If the item does not exist in the room prints error message
     * Prints updated items in the room after removing
     * @param input Contains "remove" and an item that the user wants to remove from the room
     * @param roomName The room that the user wants to remove the item from
     */
    public void removeItems(String input, String roomName) {
        String item = input.substring(startIndexItemRemove);
        if (adventure.getRoomByName(roomName).getItemsCommaSeperated()
            .equals(noItems))  {
            stream.println("Cannot remove items from this room because there are no items");
            return;
        } else if (adventure.getRoomByName(roomName).getItems().contains(item)) {
            adventure.getRoomByName(roomName).getItems().remove(item);
            stream.println("Items Visible: " + adventure.getRoomByName(roomName).getItemsCommaSeperated());
        } else {
            stream.println(item + " does not exist in this room");
        }
    }

    /**
     * Asks user if they want to teleport an item from the current room to another room
     * after they input "teleport" into the console
     * If the item cannot be teleported because it does not exist in the room, prints error message
     * If the room does not exist, prints error message
     * @param roomName the current room that the user wants to teleport the item from
     */
    public void askUserTeleportItems(String roomName) {
        stream.println("which item would you like to teleport?");

        String inputItem = input.nextLine();

        while(!isTeleportableItem(roomName, inputItem)) {
            stream.println(inputItem + " does not exist in this room for you to teleport." + "\n" +
                    "Please pick another item.");
            inputItem = input.nextLine();
        }

        stream.println("where would you like to teleport " + inputItem + "?");
        String inputRoom = input.nextLine();

        while (!isValidRoom(inputRoom)) {
            stream.println(inputRoom + " does not exist. Please teleport " + inputItem + " to another room");
            inputRoom = input.nextLine();
        }

        teleportItems(roomName, inputItem, inputRoom);
        stream.println(inputItem + " has been teleported " + "to " + inputRoom);
    }

    /**
     * Checks if the item can be teleported by checking if there are any items in
     * the current room
     * Then checks if the item that the user wants to teleport is in the current room
     * @param roomName The current room that the user is at
     * @param inputItem The item that the user wishes to teleport
     * @return False if there are no items in the current room, true otherwise
     */
    public boolean isTeleportableItem(String roomName, String inputItem) {
        if (adventure.getRoomByName(roomName).getItemsCommaSeperated()
            .equals(noItems)) {
            return false;
        }
        return adventure.getRoomByName(roomName).getItems().contains(inputItem);
    }

    /**
     * Checks if the room is valid by seeing if it exists
     * @param inputRoom The current room that the user is at
     * @return True if the room exists, false otherwise
     */
    public boolean isValidRoom(String inputRoom) {
        return adventure.getRoomByName(inputRoom) != null;
    }

    /**
     * Removes item from the current room and adds it to the room that the uesr wants
     * to teleport it to
     * @param roomName the current room that the user is at
     * @param inputItem the item that the user wants to teleport
     * @param inputRoom the room that the user wants to teleport the item to
     */
    public void teleportItems(String roomName, String inputItem, String inputRoom) {
        adventure.getRoomByName(roomName).getItems().remove(inputItem);
        adventure.getRoomByName(inputRoom).getItems().add(inputItem);
    }

    /**
     * Exits the program if the user reaches the final room
     * @param roomName The room that the user is currently at
     */
    public void reachedEndingRoom(String roomName) {
        if (roomName.equals(adventure.getEndingRoom())) {
            stream.println( "You have reached the final room");
            System.exit(1);
        }
    }

    /**
     * Exits the program if the user inputs the words quit or exit into the cosole
     * @param input What the user input into the console
     */
    public void exitProgramQuitExit(String input) {
        if (input.equalsIgnoreCase(quit) ||
                input.equalsIgnoreCase(exit)) {
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
        return inputGo.equalsIgnoreCase(go);
    }

    /**
     * Checks if the user wants to add an item by checking if the first
     * word is "add"
     * @param input What the user input into the console
     * @return returns true if the user wants to add, false otherwise
     */
    public boolean doesUserWantAddItem(String input) {
        String inputAdd = input.substring(0,4);
        return inputAdd.equalsIgnoreCase(add);
    }

    /**
     * Checks if the user wants to remove an item by checking if the first
     * word is "remove "
     * @param input What the input into the console
     * @return returns true if the user wants to remove, false otherwise
     */
    public boolean doesUserWantRemoveItem(String input) {
        String inputRemove = input.substring(startIndexInput, startIndexItemRemove);
        return inputRemove.equalsIgnoreCase( remove);
    }

    /**
     * Returns a String of the new room based off of the previous room and direction that the user input
     * @param adventure object of either siebel or libray json used to access rooms and directions
     * @param roomName name of the room used to get the next Room
     * @param directionName direction used that the player inputed to get the nextRoom
     * @return returns a String of the new room
     */
    public String getNextRoom(Adventure adventure, String roomName, String directionName) {
        directionName = directionName.substring(startindexDirection);
        String newRoomName = adventure.getRoomByName(roomName).getDirectionByName(directionName).getRoom();
        return newRoomName;
    }

    /**
     * Checks to see if the user input a valid direction
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
