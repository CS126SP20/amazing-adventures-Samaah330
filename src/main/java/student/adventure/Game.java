package student.adventure;

// upper case/ lowercase
// go something
// how to test?
public class Game {

    public static boolean isDirectionValidGo(Adventure adventure, String inputDirection) {
        String inputGo = inputDirection.substring(0,3);
        String inputRest = inputDirection.substring(3,inputDirection.length());
       // boolean directionExists = adventure.getRoomByName();
        if (inputGo.equalsIgnoreCase("go ")) {
            return true;
        }
        return false;
    }

}
