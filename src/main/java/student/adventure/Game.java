package student.adventure;

// upper case/ lowercase
// go something
// how to test?
public class Game {
    static String directionRoomName;
    static int count = 0;
    public static String initializeStartingRoom(Adventure adventureExplorer) {
        return "";
    }

    public static boolean isDirectionValidGo(Adventure adventure, String inputDirection) {
        String inputGo = inputDirection.substring(0,3);
        String inputRest = inputDirection.substring(3,inputDirection.length());
       // boolean directionExists = adventure.getRoomByName();
        if (inputGo.equalsIgnoreCase("go ")) {
            return true;
        }
        return false;
    }
    public static String getNextRoomDescription(Adventure adventureExplorer, String directionName) {
        return "";
    }
}
