public class MagicWall extends Tile{

    public MagicWall(int x,int y) {
        super(x,y,"MAGIC_WALL.png");
    }

    @Override
    public boolean isTraversable() {
        return false;
    }
}
