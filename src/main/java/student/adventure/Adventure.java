package student.adventure;

import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "startingRoom",
        "endingRoom",
        "rooms"
})
public class Adventure {

    @JsonProperty("startingRoom")
    private String startingRoom;
    @JsonProperty("endingRoom")
    private String endingRoom;
    @JsonProperty("rooms")
    private List<Room> rooms = null;

    /**
     *
     * @param startingRoom
     * @param rooms
     * @param endingRoom
     */
    public Adventure(String startingRoom, String endingRoom, List<Room> rooms) {
        super();
        this.startingRoom = startingRoom;
        this.endingRoom = endingRoom;
        this.rooms = rooms;
    }

    @JsonProperty("startingRoom")
    public String getStartingRoom() {
        return startingRoom;
    }

    @JsonProperty("endingRoom")
    public String getEndingRoom() {
        return endingRoom;
    }

    @JsonProperty("rooms")
    public List<Room> getRooms() {
        return rooms;
    }

    /**
     * returns the room based off of the roomName
     * @param roomName name of the room as a String
     * @return returns a room from a list of Rooms
     */
    public Room getRoomByName(String roomName) {
        for (Room myRoom: this.rooms) {
            if (myRoom.getName().equals(roomName)) {
                return myRoom;
            }
        }
        return null;
    }

}