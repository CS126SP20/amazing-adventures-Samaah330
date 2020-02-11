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
     * No args constructor for use in serialization
     *
     */
    public Direction() {
    }

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

    @JsonProperty("directionName")
    public void setDirectionName(String directionName) {
        this.directionName = directionName;
    }

    public Direction withDirectionName(String directionName) {
        this.directionName = directionName;
        return this;
    }

    @JsonProperty("room")
    public String getRoom() {
        return room;
    }

    @JsonProperty("room")
    public void setRoom(String room) {
        this.room = room;
    }

    public Direction withRoom(String room) {
        this.room = room;
        return this;
    }

}