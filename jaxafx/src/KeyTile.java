public class KeyTile extends Tile {

    private String colour;

    public KeyTile(int x, int y, String colour ){
        super(x,y,getKeyImage(colour));
        this.colour = colour.toLowerCase();
    }

    public String getColour () {
        return colour;
    }

    private static String getKeyImage (String colour) {
        switch (colour.toLowerCase()) {
            case "red":
                return "RED_KEY.png";
            case "blue":
                return "BLUE_KEY.png";
            case "green":
                return "GREEN_KEY.png";
            case "yellow":
                return "YELLOW_KEY.png";

        }
        return colour;
    }

    @Override
    public boolean isTraversable() {
        return true;
    }

}
