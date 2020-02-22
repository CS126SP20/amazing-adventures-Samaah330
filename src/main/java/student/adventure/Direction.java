package student.adventure;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "directionName",
        "room"
})
public class Direction {

    @JsonProperty("directionName")
    private String directionName;
    @JsonProperty("room")
    private String room;

    /**
     *
     * @param directionName
     * @param room
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