package student.adventure;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "startingRoom",
        "endingRoom",
        "rooms"
})

/**
 * Keeps track of starting room, ending room, and array of rooms from json file
 */
public class Adventure {

    @JsonProperty("startingRoom")
    private String startingRoom;
    @JsonProperty("endingRoom")
    private String endingRoom;
    @JsonProperty("rooms")
    private List<Room> rooms = null;

    public Adventure() {

    }

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

    /**
     * returns the room based off of the roomName
     * @param roomName name of the room as a String
     * @return returns a room from a list of Rooms
     */
    public Room getRoomByName(String roomName) {
        for (Room room: rooms) {
            if (room.getName().equals(roomName)) {
                return room;
            }
        }
        return null;
    }

}