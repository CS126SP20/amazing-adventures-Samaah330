package student.adventure;

/**
 * Class that holds most of the functionality and helper methods used in Main.java
 */
public class Game {
    /**
     * Initializes the starting room description based on json file
     * @param adventureExplorer object of either siebel or libray json used to access rooms and directions
     * @return returns a string of the starting description
     */
    public String initializeStartingRoom(Adventure adventureExplorer) {
        String startRoom = adventureExplorer.getStartingRoom();
        String startRoomDescription = adventureExplorer.getRoomByName(startRoom).getDescription();
        String allDirections =  adventureExplorer.getRoomByName(startRoom).getAllDirectionsCommaSeparated();

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
     * @param adventureExplorer object of either siebel or libray json used to access rooms and directions
     * @param roomName name of the room used to get the next Room
     * @param directionName direction used that the player inputed to get the nextRoom
     * @return returns a String of the new room
     */
    public String getNextRoom(Adventure adventureExplorer, String roomName, String directionName) {
        directionName = directionName.substring(3);
        //returns the room of the next room based off of the previous room and direction that the user input
        String newRoomName = adventureExplorer.getRoomByName(roomName).getDirectionByName(directionName).getRoom();
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
