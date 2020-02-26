package student.adventure;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "description",
        "items",
        "directions"
})
/**
 * keeps track of name, description, items, and directions in json file
 */
public class Room {

    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("items")
    private List<String> items = null;
    @JsonProperty("directions")
    private List<Direction> directions = null;

    public Room() {

    }

    /**
     * Constructor for Room class
     * Gives value for directions, name, description, and items
     * @param directions List of possible direction in the room
     * @param name Name of the room
     * @param description Description of the room
     * @param items List of items present in the room
     */
    public Room(String name, String description, List<String> items, List<Direction> directions) {
        super();
        this.name = name;
        this.description = description;
        this.items = items;
        this.directions = directions;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("items")
    public List<String> getItems() {
        return items;
    }

    /**
     * Iterates through items and puts them in a format with commas and "and"
     * @return String of items with commas and "and" in between them
     * If there are no items, returns "No Items"
     */
    public String getItemsCommaSeperated() {
        int count = 0;
        String allItems = "";
        if (items == null) {
            return "No Items";
        }
        for (String items: items) {
            if (count == 0) {
                allItems += items;
            } else if (count == getItems().size() - 1) {
                allItems += " and " + items;
            } else {
                allItems += ", " + items;
            }
            count++;
        }
        if (allItems.equals("")) {
            return "No Items";
        }
        return allItems;
    }

    /**
     * Gets the direction based off of the directionName that the user input
     * @param directionName Direction name the user input
     * @return null if the directionName does not exist, otherwise returns the direction
     */
    public Direction getDirectionByName(String directionName) {
        for (Direction direction: directions) {
            if (direction.getDirectionName().equalsIgnoreCase((directionName))) {
                return direction;
            }
        }
        return null;
    }

    /**
     * list of all the directions for the user to pick from
     * @return returns a String of a list of all the Directions
     */
    public String getAllDirectionsCommaSeparated() {
        int count = 0;
        String allDirections = "";

        for (Direction direction: directions) {
            if (count == 0) {
                allDirections += direction.getDirectionName();
            } else if (count == directions.size() - 1) {
                allDirections += " or " + direction.getDirectionName();
            } else {
                allDirections += ", " + direction.getDirectionName();
            }
            count++;
        }
        return allDirections;
    }
}