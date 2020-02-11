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

// confused about printStream



public class Main {
    public static void main(String[] args) {
        // TODO: Complete this method.
        Scanner input = new Scanner(System.in);
        String inputFile = input.nextLine();
        File file;
        Adventure adventureExplorer;
       // String startRoom = "SiebelEntry";

        file = new File(inputFile);
        String fileName = "src/main/resources/siebel.json";
        if (file.exists()) {
            fileName =  inputFile;
        }
        try {
            file = new File(fileName);
            adventureExplorer = new ObjectMapper().readValue(file, Adventure.class);
            System.out.println(Game.initializeStartingRoom(adventureExplorer));
            while(true) {
               input = new Scanner(System.in);
               String inputDirection = input.nextLine();
               if (inputDirection.equalsIgnoreCase("QUIT") ||
                       inputDirection.equalsIgnoreCase("EXIT")) {
                   System.exit(0);
               } else {
                   String nextRoomDescription = Game.getNextRoomDescription(adventureExplorer, inputDirection);
                   System.out.println(nextRoomDescription);
                   if (nextRoomDescription.equals("You have reached the final room")) {
                       System.exit(0);
                   }
               }
            }


        } catch(Exception e) {
            System.out.println("cannot load");
            e.printStackTrace();
        }
    }
}