public class NormalWall extends Tile{

    public NormalWall(int x, int y){
        super(x,y,"NORMAL_WALL.png");
    }

    public boolean isTraversable(){
        return false;
    }
}
