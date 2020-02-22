package student;

import com.fasterxml.jackson.databind.ObjectMapper;
import student.adventure.Adventure;
import student.adventure.Game;

import java.io.File;
import java.net.URL;
import java.util.Scanner;

/**
 * Takes in file name of JSON and puts player through an adventure game
 * @author Samaah Khan
 */
public class Main {
    static URL siebelUrl;
    final static String defaultFileName = "https://courses.grainger.illinois.edu/cs126/sp2020/resources/siebel.json";
    public static void main(String[] args) {
        // support adding items
        // support removing items
        // print items with description of room
        Scanner input = new Scanner(System.in);
        String inputFile = input.nextLine();
        Adventure adventure;

        File file = new File(inputFile);
        String urlName; //= "src/main/resources/siebel.json"; // final
        // if file does not exist, then default file is siebel json
        if (file.exists()) {
            urlName =  inputFile;
        } else {
            urlName = defaultFileName;
        }
        try {
            //file = new File(fileName);
            siebelUrl = new URL(urlName);
            adventure = new ObjectMapper().readValue(siebelUrl, Adventure.class);
            Game objGame = new Game();
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
        } catch(Exception exception) {
            System.out.println("cannot load");
            exception.printStackTrace();
        }
    }
}