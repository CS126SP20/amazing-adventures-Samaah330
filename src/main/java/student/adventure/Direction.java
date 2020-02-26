package student.adventure;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "directionName",
        "room"
})
/**
 *  keeps track of directions and next room in json file
 */
public class Direction {

    @JsonProperty("directionName")
    private String directionName;
    @JsonProperty("room")
    private String room;

    public Direction() {

    }

    /**
     * Constructor for Direction class
     * Assigns value to directionName and room
     * @param directionName The direction that the user wishes to move
     * @param room The current room that the user is in
     */
    public Direction(String directionName, String room) {
        super();
        this.directionName = directionName;
        this.room = room;
    }

    @JsonProperty("directionName")
    public String getDirectionName() {
        return directionName;
    }

    @JsonProperty("room")
    public String getRoom() {
        return room;
    }
}