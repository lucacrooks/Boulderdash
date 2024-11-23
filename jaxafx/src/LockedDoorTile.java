/*public class LockedDoorTile extends Tile{

    private String colour;

    public LockedDoorTile (int x,int y, String colour) {
        super(x,y, getDoorImage(colour));
        this.colour = colour.toLowerCase();
    }

    public String getColour() {
        return colour;
    }

    private static String getDoorImage (String colour) {
        switch (colour.toLowerCase()) {
            case "red":
                return "RED_LOCKED_DOOR.png";
            case "blue":
                return "BLUE_LOCKED_DOOR.png";
            case "green":
                return "GREEN_LOCKED_DOOR.png";
            case "yellow":
                return "YELLOW_LOCKED_DOOR.png";
        }

        return colour;
    }

    @Override
    public boolean isTraversable() {
        return false;
    }

}*/
